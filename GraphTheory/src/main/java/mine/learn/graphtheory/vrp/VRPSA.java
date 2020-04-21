package mine.learn.graphtheory.vrp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import mine.learn.graphtheory.FloydWarshall;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;

public class VRPSA {

    private int V;
    public static double[][] g;
    public static HashMap<SubPath, DistAssociatedWithPath> memo;
    private FloydWarshall spt;
    public static double[] demands;
    public static double vihicleCapacity;
    private Instance bestInstance;
    private Random r = new Random(System.currentTimeMillis());

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
     * @throws CloneNotSupportedException
     */
    public VRPSA(EdgeWeightedDiGraph graph, int[] depots, double[] demands, double vihicleCapacity, double a, double t0,
            double tf, double markov) {
        VRPSA.demands = demands;
        VRPSA.vihicleCapacity = vihicleCapacity;
        if (demands[0] > 0)
            throw new IllegalArgumentException("源节点（仓库）需求应为0！当前为：" + demands[0]);
        this.V = depots.length;
        spt = new FloydWarshall(graph);
        g = new double[V][V];
        memo = new HashMap<>();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                g[i][j] = spt.dist(depots[i], depots[j]);
            }
        }
        Instance instance = init();
        bestInstance = instance;
        int count = 0;
        for (double t = t0; t > tf; t *= a) {
            count++;
            System.out.println(count + " : " + bestInstance);
            for (int i = 0; i < markov; i++) {
                double dist = instance.getDist();
                Instance newInstance = genNewInstance(instance);
                // DistAssociatedWithPath newDistAndPath = genNewInstance(distAndPath);
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
        System.out.println("total :" + count);
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

    private Instance init() {
        int[] order = new int[V];
        for (int i = 0; i < V; i++) {
            order[i] = i;
        }
        return new Instance(order);
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
        return bestInstance.getDist();
    }

    // public List<WeightedDirectedEdge> getBestPath() {
    // bestInstance.getPath();
    // }
}