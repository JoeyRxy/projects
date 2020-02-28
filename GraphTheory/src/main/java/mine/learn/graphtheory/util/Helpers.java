package mine.learn.graphtheory.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.bean.EdgeWeightedGraph;
import mine.learn.graphtheory.bean.WeightedDirectedEdge;
import mine.learn.graphtheory.bean.WeightedEdge;

/**
 * Helpers
 */
public class Helpers {

    public static BufferedReader getResouses(String path) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(Helpers.class.getClassLoader().getResourceAsStream(path)));
        return reader;
    }

    public static Object getGraph(String path, Class<?> graph) throws NumberFormatException, IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(Helpers.class.getClassLoader().getResourceAsStream(path)));
        int V = Integer.parseInt(reader.readLine());
        int E = Integer.parseInt(reader.readLine());
        Object instance;
        try {
            instance = graph.getConstructor(int.class).newInstance(V);
            if (instance instanceof EdgeWeightedGraph) {
                EdgeWeightedGraph g = (EdgeWeightedGraph) instance;
                String line = reader.readLine();
                while (line != null) {
                    String[] split = (" " + line).split(" +");
                    g.addEdge(new WeightedEdge(Integer.parseInt(split[1]), Integer.parseInt(split[2]),
                            Double.parseDouble(split[3])));
                    line = reader.readLine();
                }
                // assert g.E() == E;
                return g;
            } else if (instance instanceof EdgeWeightedDiGraph) {
                EdgeWeightedDiGraph g = (EdgeWeightedDiGraph) instance;
                String line = reader.readLine();
                while (line != null) {
                    String[] split = (" " + line).split(" +");
                    g.addEdge(new WeightedDirectedEdge(Integer.parseInt(split[1]), Integer.parseInt(split[2]),
                            Double.parseDouble(split[3])));
                    line = reader.readLine();
                }
                // assert g.E() == E;
                return g;
            } else
                throw new IllegalArgumentException("Type " + graph.getName() + " is not allowed.");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }

    }
}