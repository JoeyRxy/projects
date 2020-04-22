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
import java.util.Random;

import mine.learn.graphtheory.FloydWarshall;
import mine.learn.graphtheory.bean.Coordination;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.util.VRPFileInfo;
import mine.learn.graphtheory.util.VRPProcessor;

public class VRPSA {

    private int V;
    public static double[][] g;
    public static HashMap<SubPath, DistAssociatedWithPath> memo;
    private FloydWarshall spt;
    public static double[] demands;
    public static double vihicleCapacity;
    // private Instance bestInstance;
    private Random r = new Random(System.currentTimeMillis());
    private double[] vehicleLoads;
    private List<Double> log;

    private final int GROUP_NUMBER;
    private int threadNum = 2;
    private EdgeWeightedDiGraph graph;
    private int[] depots;
    private BufferedWriter logger;
    private ArrayList<Integer>[] groups;
    private double a;
    private double t0;
    private double tf;
    private int markov;

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
    public VRPSA(EdgeWeightedDiGraph graph, int[] depots, double[] demands, double vihicleCapacity) throws IOException {
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

        if (depots.length > 380) {
            GROUP_NUMBER = 8;
        } else if (depots.length > 180) {
            GROUP_NUMBER = 4;
        } else if (depots.length > 80) {
            GROUP_NUMBER = 2;
        } else {
            GROUP_NUMBER = 1;
        }

        split();
    }

    private void split() {
        if (GROUP_NUMBER == 1)
            return;
        Coordination[] coordinations = new Coordination[V];
        for (int i = 0; i < V; i++) {
            coordinations[i] = graph.coordinationOf(depots[i]);
        }
        Arrays.sort(coordinations, new Comparator<Coordination>() {

            @Override
            public int compare(Coordination o1, Coordination o2) {
                return Double.compare(o1.getPhi(), o2.getPhi());
            }

        });
        int groupSize = depots.length / GROUP_NUMBER;
        groups = new ArrayList[GROUP_NUMBER];
        int idx = 0, end;
        for (int i = 0; i < GROUP_NUMBER; i++) {
            groups[i] = new ArrayList<>(groupSize);
            end = groupSize * (i + 1);
            while (idx != end) {
                groups[i].add(graph.vertexOf(coordinations[idx++]));
            }
        }
        while (idx < V) {
            groups[GROUP_NUMBER - 1].add(graph.vertexOf(coordinations[idx++]));
        }
        for (int i = 0; i < V; i++) {
            coordinations[i] = null;
        }
        coordinations = null;
    }

    /**
     * @param logger the logger to set
     */
    public void setLogger(BufferedWriter logger) {
        this.logger = logger;
    }

    public void calculate(double a, double t0, double tf, int markov) {
        this.a = a;
        this.t0 = t0;
        this.tf = tf;
        this.markov = markov;
        // TODO:改为多线程计算
        for (int i = 0; i < GROUP_NUMBER; i++) {

        }
    }

    private Instance cal(int[] order) {
        long start = System.currentTimeMillis();
        Instance instance = new Instance(order);
        Instance bestInstance = instance;
        int count = 0;
        LinkedList<Double> record = new LinkedList<>();
        log = new ArrayList<>((int) (Math.log(tf / t0) / Math.log1p(a - 1)) + 1);
        if (logger != null)
            try {
                logger.write("Count,Dist,Path,Time\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        for (double t = t0; t > tf; t *= a) {
            count++;
            if (logger != null) {
                try {
                    logger.write(String.format("%d,%12.7f,\"%s\",%d\n", count, bestInstance.getDist(),
                            Arrays.toString(bestInstance.getPath()), (System.currentTimeMillis() - start)));
                    logger.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            log.add(bestInstance.getDist());
            assert bestInstance.check();
            record.addLast(bestInstance.getDist());
            if (record.size() == 10) {
                if (record.removeFirst() - bestInstance.getDist() < 1e-7)
                    break;
            }
            for (int i = 0; i < markov; i++) {
                double dist = instance.getDist();
                Instance newInstance = genNewInstance(instance);
                double new_dist = newInstance.getDist();
                if (new_dist < dist) {
                    instance = newInstance;
                    if (bestInstance.getDist() > new_dist) {
                        bestInstance = newInstance;
                    }
                } else if (Math.random() < Math.expm1((dist - new_dist) / t)) {
                    instance = newInstance;
                }
            }
        }
        assert check() : "路径距离和与BestDist不匹配!";
        System.out.println("total : " + count);
        return bestInstance;
    }

    private Instance genNewInstance(Instance instance) {
        int[] seg = new int[3];
        seg[0] = 1 + r.nextInt(V - 1);
        seg[1] = 1 + r.nextInt(V - 1);
        while (seg[1] == seg[0]) {
            seg[1] = 1 + r.nextInt(V - 1);
        }
        int[] order = instance.getOrder();
        if (Math.random() < 0.5) {// 交换两点
            swap(order, seg[0], seg[1]);
        } else {// 交换两段
            seg[2] = 1 + r.nextInt(V - 1);
            while (seg[2] == seg[0] || seg[2] == seg[1])
                seg[2] = 1 + r.nextInt(V - 1);
            sort3(seg);
            swap(order, seg[0], seg[1], seg[2]);
            swap(order, V - seg[2], V - seg[1], V - seg[0]);
        }
        return new Instance(order);
    }

    private void sort3(int[] seg) {
        int j = seg[1] < seg[2] ? 1 : 2;
        int t;
        if (seg[0] > seg[j]) {
            t = seg[0];
            seg[0] = seg[j];
            seg[j] = t;
        }
        if (seg[1] > seg[2]) {
            t = seg[1];
            seg[1] = seg[2];
            seg[2] = t;
        }
    }

    private void swap(int[] order, int i, int j, int k) {
        int[] tmp = Arrays.copyOfRange(order, i, j);
        while (j < k)
            order[i++] = order[j++];
        j = 0;
        while (i < k)
            order[i++] = tmp[j++];
    }

    private void swap(int[] order, int v, int w) {
        int t = order[v];
        order[v] = order[w];
        order[w] = t;
    }

    public double getBestDist() {
        return -1;
    }

    /**
     * @return the log
     */
    public List<Double> getLog() {
        return log;
    }

    public List<List<WeightedDirectedEdge>> getBestPaths() {
        return null;
    }

    private List<List<WeightedDirectedEdge>> getBestPaths(Instance bestInstance) {
        List<int[]> decodePath = bestInstance.getDecodePath();
        List<List<WeightedDirectedEdge>> list = new ArrayList<>(decodePath.size() + 1);
        vehicleLoads = new double[decodePath.size()];
        int idx = 0;
        for (int[] subPath : decodePath) {
            assert subPath[0] == 0 : "子路径应以源节点为起点";
            List<WeightedDirectedEdge> tmp = new LinkedList<>();
            for (int i = 1; i < subPath.length; i++) {
                tmp.addAll(spt.path(subPath[i - 1], subPath[i]));
                vehicleLoads[idx] += demands[subPath[i]];
            }
            idx++;
            tmp.addAll(spt.path(subPath[subPath.length - 1], subPath[0]));
            list.add(tmp);
        }
        return list;
    }

    public boolean check() {
        List<List<WeightedDirectedEdge>> bestPaths = getBestPaths();
        double s = 0;
        for (List<WeightedDirectedEdge> list : bestPaths) {
            for (WeightedDirectedEdge sub : list) {
                s += sub.weight();
            }
        }
        return Math.abs(s - getBestDist()) < 1e-7;
    }

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
    public double[] getVehicleLoads() {
        return vehicleLoads;
    }

    public static void main(String[] args) throws IOException {
        File vrpFile = new File("src/main/resources/M-n101-k10.vrp");
        // File vrpFile = new File("src/main/resources/M-n200-k17.vrp");
        // File vrpFile = new File("src/main/resources/A-n80-k10.vrp");
        // File vrpFile = new File("src/main/resources/A-n48-k7.vrp");

        VRPFileInfo info = VRPProcessor.process(vrpFile);
        int[] depots = new int[info.demands.length];
        for (int i = 0; i < depots.length; i++) {
            depots[i] = i;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(
                new File("tmpdir/vrptestres/VRPSA-" + vrpFile.getName() + "-" + System.currentTimeMillis() + ".csv")));
        VRPSA vrpsa = new VRPSA(info.graph, depots, info.demands, info.capacity);
        vrpsa.setLogger(writer);
        long start = System.currentTimeMillis();
        vrpsa.calculate(0.993, 100, 1, 500);
        long end = System.currentTimeMillis();
        System.out.println(vrpsa.getBestDist());
        System.out.println("duration : " + (end - start) + " ms.");
        writer.write("\n,\n,\nBest Dist," + vrpsa.getBestDist() + "\n");
        writer.write("Best Path:\n,subRouteDist,subRoute,vehicleLoad\n");
        int i = 0;
        List<List<WeightedDirectedEdge>> finalBestPaths = vrpsa.getBestPaths();
        double[] vehicleLoads = vrpsa.getVehicleLoads();
        for (List<WeightedDirectedEdge> subPath : finalBestPaths) {
            writer.write("Route " + i + "," + VRPSA.helperForSubPathDist(subPath) + ",\"" + subPath + "\","
                    + vehicleLoads[i] + "\n");
            i++;
        }
        writer.write(",\nHistory Best Dist,trucks\n");
        writer.write(info.bestDist + "," + info.trucks);
        writer.close();
    }
}