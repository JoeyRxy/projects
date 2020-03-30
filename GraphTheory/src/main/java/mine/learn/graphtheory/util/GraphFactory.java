package mine.learn.graphtheory.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * GraphFactory
 */
public class GraphFactory {

    public static Object produce(String resource) throws NoSuchMethodException, SecurityException,
            NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, IOException, ClassNotFoundException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(GraphFactory.class.getClassLoader().getResourceAsStream(resource)));
        Class<?> graphClazz = Class.forName(reader.readLine());
        Class<?> edgeClass = Class.forName(reader.readLine());
        Constructor<?> constructor = graphClazz.getConstructor(int.class);
        Object graph = constructor.newInstance(Integer.parseInt(reader.readLine()));
        Method addEdge = graphClazz.getDeclaredMethod("addEdge", edgeClass);
        Constructor<?> edge = edgeClass.getConstructor(int.class, int.class, double.class);
        reader.readLine();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = (" " + line).split(" +");
            addEdge.invoke(graph, edge.newInstance(Integer.parseInt(split[1]), Integer.parseInt(split[2]),
                    Double.parseDouble(split[3])));
        }
        reader.close();
        return graph;
    }

    public static void main(String[] args) throws NumberFormatException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            IOException, ClassNotFoundException {
        Object graph = GraphFactory.produce("mediumEWG.txt");
        System.out.println(graph);
    }
}