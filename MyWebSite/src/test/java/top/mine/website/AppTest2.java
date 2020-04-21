package top.mine.website;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import top.mine.website.dao.TSP5;
import top.mine.website.dao.tsputil.EdgeWeightedDiGraph;
import top.mine.website.dao.tsputil.Helpers;

public class AppTest2 {

    /**
     * Inner
     */
    public class Inner implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("start time : " + System.currentTimeMillis());
                EdgeWeightedDiGraph graph = Helpers.parseJSON(
                        new File("C:/Users/Rxy/Documents/MYCODE/projects/GraphTheory/src/main/resources/rat783.json"),
                        new int[] { -1 });
                int[] set = new int[graph.V()];
                for (int i = 0; i < set.length; i++) {
                    set[i] = i;
                }
                TSP5 tsp5 = new TSP5(graph, set, 100, 1, 0.9995, 4000, 0.5);
                System.out.println(tsp5.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("正常退出");
        }
    }

    @Test
    public void testKillProcessStart() throws InterruptedException, IOException {
        Thread thread = new Thread(new Inner(), "Inner");
        thread.start();
        System.out.println("start time : " + System.currentTimeMillis());
        Thread.sleep(100);
        thread.interrupt();
        // thread.stop();
        Thread.sleep(20000);
        // long start = System.currentTimeMillis();
        // System.out.println("interrupt at : " + start + " ms");
        // while (thread.isAlive())
        //     ;
        // long end = System.currentTimeMillis();
        // System.out.println("thread dead : " + end + " ms");
        // System.out.println("thread duration : " + (end - start) + " ms");
    }

    @Test
    public void testIntegerReference() {
        JSONObject jsob = JSONObject.parseObject("{name:'" + 4 + "'}");
        System.out.println(jsob.toJSONString());
    }
}