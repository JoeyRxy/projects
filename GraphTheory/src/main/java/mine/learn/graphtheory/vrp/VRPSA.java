package mine.learn.graphtheory.vrp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import mine.learn.graphtheory.FloydWarshall;
import mine.learn.graphtheory.bean.Coordination;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.util.LogInfo;
import mine.learn.graphtheory.util.VRPFileInfo;
import mine.learn.graphtheory.util.VRPProcessor;

public class VRPSA {

    /** 连续多少个相同数据则退出计算 , 0 则不退出知道计算结束 */
    public static int breakNum = 15;
    private int GROUP_NUMBER = 4;
    private int offset = 0;
    private int coreThreadNum = 4;

    int V;
    public static double[][] g;
    public static HashMap<SubPath, DistAssociatedWithPath> memo;
    private FloydWarshall spt;
    public static double[] demands;
    public static double vihicleCapacity;
    private ArrayList<Double> vehicleLoads;

    private EdgeWeightedDiGraph graph;
    private int[] depots;
    private ArrayList<Integer>[] groups;
    public static double a;
    public static double t0;
    public static double tf;
    public static int markov;
    private double bestDist;
    private List<List<WeightedDirectedEdge>> bestPaths;
    private LogInfo[] logInfos;

    /**
     * <p>
     * path : 包括0号的一整条可行路径
     * <p>
     * order : 不包括0号的未通过装载容量划分的访问“顺序”
     * 
     * @param graph           一个有向图
     * @param depots          一系列仓库节点，其中0号仓库所对应的节点为配送站
     * @param demands         与depots对应，表示每个仓库的需求量，其中0号仓库（配送站）需求为0
     * @param vihicleCapacity 运输工具的容量
     * @param a               SA参数
     * @param t0              SA参数
     * @param tf              SA参数
     * @param markov          SA参数
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public VRPSA(EdgeWeightedDiGraph graph, int[] depots, double[] demands, double vihicleCapacity, Object... params)
            throws IOException {
        switch (params.length) {
            case 1:
                GROUP_NUMBER = (int) params[0];
                break;
            case 2:
                GROUP_NUMBER = (int) params[0];
                coreThreadNum = (int) params[1];
                break;
            case 3:
                GROUP_NUMBER = (int) params[0];
                coreThreadNum = (int) params[1];
                offset = (int) params[2];
            case 4:
                GROUP_NUMBER = (int) params[0];
                coreThreadNum = (int) params[1];
                offset = (int) params[2];
                breakNum = (int) params[3];
            default:
                break;
        }
        if (coreThreadNum > GROUP_NUMBER)
            coreThreadNum = GROUP_NUMBER;
        this.graph = graph;
        this.depots = depots;
        VRPSA.demands = demands;
        VRPSA.vihicleCapacity = vihicleCapacity;
        if (demands[0] > 0)
            throw new IllegalArgumentException("源节点（仓库）需求应为0！当前为：" + demands[0]);
        this.V = depots.length;
        spt = new FloydWarshall(graph);
        g = new double[V][V];
        memo = new HashMap<>();
        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                g[i][j] = spt.dist(depots[i], depots[j]);

        split();
    }

    /** 以源节点为相对坐标系的原点，按照角度划分 */
    @SuppressWarnings("unchecked")
    private void split() {
        // 源节点外的所有节点
        Coordination[] coordinations = new Coordination[V - 1];
        Coordination o = graph.coordinationOf(depots[0]);
        HashMap<Coordination, Coordination> _map = new HashMap<>(V);
        for (int i = 0; i < coordinations.length; i++) {
            Coordination graphCoordination = graph.coordinationOf(depots[i + 1]);
            coordinations[i] = graphCoordination.relativeCoordinationOf(o);
            // graph.setCoordinationOf(depots[i + 1], coordinations[i]);
            _map.put(coordinations[i], graphCoordination);
        }
        Arrays.sort(coordinations, new Comparator<Coordination>() {

            @Override
            public int compare(Coordination o1, Coordination o2) {
                return Double.compare(o1.getPhi(), o2.getPhi());
            }

        });
        int groupSize = coordinations.length / GROUP_NUMBER;
        groups = new ArrayList[GROUP_NUMBER];
        int idx = 0, end;
        for (int i = 0; i < GROUP_NUMBER; i++) {
            groups[i] = new ArrayList<>(groupSize + 2);
            groups[i].add(depots[0]);
            end = groupSize * (i + 1);
            while (idx != end) {
                groups[i].add(graph.vertexOf(_map.get(coordinations[(idx + offset) % coordinations.length])));
                idx++;
            }
        }
        while (idx < coordinations.length) {
            groups[GROUP_NUMBER - 1]
                    .add(graph.vertexOf(_map.get(coordinations[(idx + offset) % coordinations.length])));
            idx++;
        }
        for (int i = 0; i < coordinations.length; i++) {
            coordinations[i] = null;
        }
        coordinations = null;
        _map = null;
    }

    @SuppressWarnings("unchecked")
    public void calculate(double a, double t0, double tf, int markov) {
        VRPSA.a = a;
        VRPSA.t0 = t0;
        VRPSA.tf = tf;
        VRPSA.markov = markov;
        ExecutorService threadPool = Executors.newFixedThreadPool(coreThreadNum);
        Future<Instance>[] tasks = new Future[GROUP_NUMBER];
        SubVRP[] subVRPs = new SubVRP[GROUP_NUMBER];
        for (int i = 0; i < GROUP_NUMBER; i++) {
            SubVRP subVRP = new SubVRP(Arrays.copyOf(groups[i].toArray(), groups[i].size(), Integer[].class), a, t0, tf,
                    markov);
            subVRPs[i] = subVRP;
            tasks[i] = threadPool.submit(subVRP);
        }
        threadPool.shutdown();
        Instance[] partInstances = new Instance[GROUP_NUMBER];
        try {
            for (int i = 0; i < GROUP_NUMBER; i++)
                partInstances[i] = tasks[i].get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        logInfos = new LogInfo[GROUP_NUMBER];
        for (int i = 0; i < GROUP_NUMBER; i++) {
            logInfos[i] = new LogInfo();
            logInfos[i].memoSize = subVRPs[i].getHashMapSize();
            logInfos[i].calcData = subVRPs[i].getLog();
        }
        bestPaths = new LinkedList<>();
        bestDist = 0;
        vehicleLoads = new ArrayList<>(depots.length + 1);
        for (Instance instance : partInstances) {
            bestDist += instance.getDist();
            bestPaths.addAll(getBestPaths(instance));
        }
    }

    public double getBestDist() {
        return bestDist;
    }

    public List<List<WeightedDirectedEdge>> getBestPaths() {
        return bestPaths;
    }

    private List<List<WeightedDirectedEdge>> getBestPaths(Instance bestInstance) {
        List<int[]> decodePath = bestInstance.getDecodePath();
        List<List<WeightedDirectedEdge>> list = new ArrayList<>(decodePath.size() + 1);
        for (int[] subPath : decodePath) {
            assert subPath[0] == 0 : "子路径应以源节点为起点";
            List<WeightedDirectedEdge> tmp = new LinkedList<>();
            double load = 0;
            for (int i = 1; i < subPath.length; i++) {
                tmp.addAll(spt.path(subPath[i - 1], subPath[i]));
                load += demands[subPath[i]];
            }
            vehicleLoads.add(load);
            tmp.addAll(spt.path(subPath[subPath.length - 1], subPath[0]));
            list.add(tmp);
        }
        return list;
    }

    // public boolean check(Instance instance) {
    // List<List<WeightedDirectedEdge>> bestPaths = getBestPaths(instance);
    // double s = 0;
    // for (List<WeightedDirectedEdge> list : bestPaths) {
    // for (WeightedDirectedEdge sub : list) {
    // s += sub.weight();
    // }
    // }
    // return Math.abs(s - getBestDist()) < 1e-7;
    // }

    public static double helperForSubPathDist(List<WeightedDirectedEdge> list) {
        double s = 0;
        for (WeightedDirectedEdge edge : list) {
            s += edge.weight();
        }
        return s;
    }

    /**
     * @return the vehicleLoads
     */
    public Double[] getVehicleLoads() {
        return Arrays.copyOf(vehicleLoads.toArray(), vehicleLoads.size(), Double[].class);
    }

    /**
     * @return the logInfos
     */
    public LogInfo[] getLogInfos() {
        return logInfos;
    }

    public static void main(String[] args) throws IOException {
        File[] vrpFiles = new File[] { new File("vrp/A-n48-k7.vrp"), new File("vrp/A-n80-k10.vrp"),
                new File("vrp/M-n101-k10.vrp"), new File("vrp/E076-07s.dat"), new File("vrp/E101-10c.dat"),
                new File("vrp/E200-17b.dat") };

        for (File vrpFile : vrpFiles) {
            VRPFileInfo info = VRPProcessor.process(vrpFile);
            int[] depots = new int[info.demands.length];
            for (int i = 0; i < depots.length; i++) {
                depots[i] = i;
            }
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File("vrp/VRPSA-" + vrpFile.getName() + System.currentTimeMillis() + ".csv")));
            VRPSA vrpsa = new VRPSA(info.graph, depots, info.demands, info.capacity, 4, 4);
            long start = System.currentTimeMillis();
            vrpsa.calculate(0.993, 10, 1, 1000);
            long end = System.currentTimeMillis();
            // writer.write("best dist," + vrpsa.getBestDist());
            writer.write("calculating duration," + (end - start) + " ms\n");
            int i = 0;
            List<List<WeightedDirectedEdge>> finalBestPaths = vrpsa.getBestPaths();
            Double[] vehicleLoads = vrpsa.getVehicleLoads();

            writer.write("Best Dist," + vrpsa.getBestDist() + "\n");
            writer.write("Best Path:\n,subRouteDist,subRoute,vehicleLoad\n");
            for (List<WeightedDirectedEdge> subPath : finalBestPaths) {
                writer.write("Route #" + i + "," + VRPSA.helperForSubPathDist(subPath) + ",\"" + subPath + "\","
                        + vehicleLoads[i] + "\n");
                i++;
            }
            writer.write(",\nHistory Best Dist,trucks\n");
            writer.write(info.bestDist + "," + info.trucks);
            writer.flush();
            writer.write("\n,\n,\nCalculating Log in Each Thread\n");
            LogInfo[] logInfos = vrpsa.getLogInfos();

            writer.write("\nMemoMapSize,");
            for (int j = 0; j < logInfos.length; j++) {
                writer.write(logInfos[j].memoSize + ",");
            }
            writer.write("\n,\n");
            int maxLineNum = 0;
            for (int j = 0; j < logInfos.length; j++) {
                int size = logInfos[j].calcData.size();
                if (maxLineNum < size) {
                    maxLineNum = size;
                }
            }
            writer.flush();
            writer.write("count");
            for (int j = 0; j < logInfos.length; j++) {
                writer.write(",thread" + j);
            }
            writer.write(",Sum\n");
            double[] cache = new double[logInfos.length];
            for (int j = 0; j < maxLineNum; j++) {
                writer.write(j + ",");
                double s = 0;
                for (int k = 0; k < logInfos.length; k++) {
                    if (logInfos[k].calcData.size() > j) {
                        Double x = logInfos[k].calcData.get(j);
                        s += x;
                        writer.write(String.format("%10.5f,", x));
                        cache[k] = x;
                    } else {
                        writer.write(String.format("%10.5f,", cache[k]));
                        s += cache[k];
                    }
                }
                writer.write(String.format("%10.5f\n", s));
            }
            writer.flush();
            writer.close();
        }
    }
}