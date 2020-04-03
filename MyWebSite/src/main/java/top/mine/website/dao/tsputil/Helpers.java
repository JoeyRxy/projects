package top.mine.website.dao.tsputil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Helpers
 */
public class Helpers {

    public static BufferedReader getResouses(String path) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(Helpers.class.getClassLoader().getResourceAsStream(path)));
        return reader;
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

}