package mine.learn.graphtheory.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.json.XML;

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
        // int E = Integer.parseInt(reader.readLine());
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

    public static EdgeWeightedDiGraph parseJSON(File file, int[] best) throws IOException {
        FileInputStream in = new FileInputStream(file);
        StringBuilder builder = new StringBuilder();
        byte[] b = new byte[102400];
        int len;
        while ((len = in.read(b)) != -1) {
            builder.append(new String(b, 0, len));
        }
        JSONObject graphJSON = JSONObject.parseObject(builder.toString());
        builder = null;
        in.close();
        JSONObject root = graphJSON.getJSONObject("travellingSalesmanProblemInstance");
        // System.out.println("best score : " + root.getString("best"));
        best[0] = Integer.parseInt(root.getString("best"));
        JSONArray vertex = root.getJSONObject("graph").getJSONArray("vertex");
        root = null;
        int V = vertex.size();
        EdgeWeightedDiGraph g = new EdgeWeightedDiGraph(V);
        for (int v = 0; v < V; v++) {
            JSONArray edge = vertex.getJSONObject(v).getJSONArray("edge");
            int size = edge.size();
            for (int i = 0; i < size; i++) {
                JSONObject x = edge.getJSONObject(i);
                int w = x.getIntValue("content");
                double cost = x.getDoubleValue("cost");
                g.addEdge(new WeightedDirectedEdge(v, w, cost));
            }
        }
        return g;
    }

    public static void xml2json(String filePath, String jsonfile) throws IOException {
        FileInputStream in = new FileInputStream(new File(filePath));
        StringBuilder builder = new StringBuilder();
        byte[] b = new byte[10240];
        int len;
        while ((len = in.read(b)) != -1) {
            builder.append(new String(b, 0, len));
        }
        in.close();
        String xml = builder.toString();
        org.json.JSONObject json = XML.toJSONObject(xml);
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(jsonfile)));
        writer.write(json.toString());
        writer.close();
    }

    public static EdgeWeightedDiGraph parseGraph(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        int V = Integer.parseInt(line);
        EdgeWeightedDiGraph g = new EdgeWeightedDiGraph(V);
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            if (!(line.charAt(0) >= '0' && line.charAt(0) <= '9')) {
                String[] split = line.split(" +");
                int v = Integer.parseInt(split[0].substring(1));
                int w = Integer.parseInt(split[2]);
                double cost = Double.parseDouble(split[4]);
                g.addEdge(new WeightedDirectedEdge(v, w, cost));
            }
        }
        reader.close();
        return g;
    }

}