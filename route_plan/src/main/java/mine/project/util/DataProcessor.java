package mine.project.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import mine.project.bean.DirectedEdge;
import mine.project.bean.EdgeWeightedDiGraph;

/**
 * DataProcessor
 * <p>
 * Data Format:
 * <p>
 * vertex_num
 * <p>
 * edge_num
 * <p>
 * n1 n2 dist
 * <p>
 * ...
 */
public class DataProcessor {

    public static EdgeWeightedDiGraph process(String resource) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(DataProcessor.class.getClassLoader().getResourceAsStream(resource)));
        String vertex_num = bufferedReader.readLine();
        EdgeWeightedDiGraph g = new EdgeWeightedDiGraph(Integer.parseInt(vertex_num));
        Integer edge_num = Integer.parseInt(bufferedReader.readLine());
        for (int i = 0; i < edge_num; i++) {
            String line = bufferedReader.readLine() + " ";
            String[] splits = new String[3];
            int j = 0;
            for (int k = 0; k < splits.length; k++) {
                while (line.charAt(j) == ' ')
                    j++;
                int s = j;
                while (line.charAt(j) != ' ')
                    j++;
                int e = j;
                splits[k] = line.substring(s, e);
            }
            g.addEdge(new DirectedEdge(splits[0], splits[1], Double.parseDouble(splits[2])));
        }
        return g;
    }
}