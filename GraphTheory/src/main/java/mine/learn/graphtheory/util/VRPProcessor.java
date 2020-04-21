package mine.learn.graphtheory.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import mine.learn.graphtheory.bean.Coordination;
import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;

public class VRPProcessor {

    public static VRPFileInfo process(File vrpFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(vrpFile));
        reader.readLine();
        reader.readLine();
        reader.readLine();
        reader.readLine();
        String dimension = reader.readLine();
        int i = 0;
        while (!(dimension.charAt(i) >= '0' && dimension.charAt(i) <= '9'))
            ;
        int V = Integer.parseInt(dimension.substring(i));
        EdgeWeightedDiGraph graph = new EdgeWeightedDiGraph(V);
        reader.readLine();
        String line = reader.readLine();
        i = 0;
        while (!(line.charAt(i) >= '0' && line.charAt(i) <= '9'))
            ;
        double capacity = Double.parseDouble(line.substring(i));
        reader.readLine();
        Coordination[] coordinations = new Coordination[V];
        for (int j = 0; j < V; j++) {
            String splits[] = (" " + reader.readLine()).split(" +");
            coordinations[j] = new Coordination(Double.parseDouble(splits[1]), Double.parseDouble(splits[2]));
        }
        for (int j = 0; j < V; j++) {
            for (int k = 0; k < V; k++) {
                if (k == j)
                    continue;
                graph.addEdge(new WeightedDirectedEdge(j, k, coordinations[j].dist(coordinations[k])));
            }
        }
        reader.readLine();
        double[] demands = new double[V];
        for (int j = 0; j < V; j++) {
            String[] split = reader.readLine().split(" ");
            demands[j] = Double.parseDouble(split[split.length - 1]);
        }
        reader.close();
        return new VRPFileInfo(graph, capacity, demands);
    }
}