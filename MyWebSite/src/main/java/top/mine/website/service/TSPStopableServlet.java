package top.mine.website.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.TSP5;
import top.mine.website.dao.tsputil.EdgeWeightedDiGraph;
import top.mine.website.dao.tsputil.Helpers;
import top.mine.website.util.LogStateCheck;
import top.mine.website.util.ThreadListener;

@WebServlet("/tsp3")
public class TSPStopableServlet extends HttpServlet {
    static File tspfilesRoot;
    static {
        try {
            InputStreamReader reader = new InputStreamReader(
                    TSPStopableServlet.class.getClassLoader().getResourceAsStream("upload.json"), "UTF-8");
            char[] str = new char[10240];
            int len;
            StringBuilder builder = new StringBuilder();
            while ((len = reader.read(str)) != -1) {
                builder.append(str, 0, len);
            }
            reader.close();
            String path = JSONObject.parseObject(builder.toString()).getString("tspfiles");
            builder = null;
            tspfilesRoot = new File(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     */
    private static final long serialVersionUID = -2588940671982418309L;
    static ConcurrentHashMap<String, Thread> threadMap = new ConcurrentHashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!LogStateCheck.checkLogState(req))
            throw new IllegalArgumentException();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String threadName = req.getParameter("threadName");
        threadName = req.getSession().getAttribute("name") + threadName;
        // TSPRunner tsprunner = new TSPRunner(new File(tspfilesRoot,
        // req.getParameter("file")),
        // Double.parseDouble(req.getParameter("t0")),
        // Double.parseDouble(req.getParameter("tf")),
        // Double.parseDouble(req.getParameter("a")),
        // Integer.parseInt(req.getParameter("markov")), threadName);

        // threadMap.put(threadName, tsprunner);
        // tsprunner.registerListener(new ThreadListener(){

        // @Override
        // public void afterStop() {

        // }

        // @Override
        // public void afterInterrupt() {
        // }

        // @Override
        // public void afterDone() {
        // }
        // });
        // tsprunner.start();
        // while (tsprunner.isAlive())
        // ;
        // if (tsprunner.isInterrupted())
        // resp.getWriter().write("{\"data\":interrupted}");
        // else
        // resp.getWriter().write(tsprunner.getData().toJSONString());

        // tsprunner.start();
        // try {
        // tsprunner.wait();
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        TSPCallable tspcallable = new TSPCallable(new File(tspfilesRoot, req.getParameter("file")),
                Double.parseDouble(req.getParameter("t0")), Double.parseDouble(req.getParameter("tf")),
                Double.parseDouble(req.getParameter("a")), Integer.parseInt(req.getParameter("markov")));
        FutureTask<JSONObject> task = new FutureTask<>(tspcallable);
        Thread thread = new Thread(task, threadName);
        threadMap.put(threadName, thread);
        thread.start();
        try {
            JSONObject data = task.get();
            resp.getWriter().write(data.toJSONString());
        } catch (InterruptedException | ExecutionException e) {
            JSONObject json = new JSONObject();
            json.put("data", "interrupted");
            resp.getWriter().write(json.toJSONString());
            e.printStackTrace();
        }
        threadMap.remove(threadName);
    }

    class TSPCallable implements Callable<JSONObject> {

        private File graphFile;
        private double t0;
        private double tf;
        private double a;
        private int markov;
        private JSONObject data;

        TSPCallable(File graphFile, double t0, double tf, double a, int markov) {
            this.graphFile = graphFile;
            this.t0 = t0;
            this.tf = tf;
            this.a = a;
            this.markov = markov;
            data = new JSONObject();
        }

        @Override
        public JSONObject call() throws IOException {
            int[] best = { -1 };
            EdgeWeightedDiGraph graph = Helpers.parseJSON(graphFile, best);
            int[] set = new int[graph.V()];
            for (int i = 0; i < set.length; i++) {
                set[i] = i;
            }
            long start = System.currentTimeMillis();
            TSP5 tsp5 = new TSP5(graph, set, t0, tf, a, markov, 0.5);
            long end = System.currentTimeMillis();
            data.put("xAxisData", tsp5.getXAxisData());
            data.put("seriesData", tsp5.getSeries1Data());
            data.put("best", best[0]);
            data.put("path", tsp5.getPath());
            data.put("duration", (end - start));
            return data;

        }

    }

    class TSPRunner extends Thread {
        private File graphFile;
        private double t0;
        private double tf;
        private double a;
        private int markov;
        private JSONObject data;
        private ThreadListener listener;
        private String threadName;

        TSPRunner(File graphFile, double t0, double tf, double a, int markov, String threadName) {
            super(threadName);
            this.graphFile = graphFile;
            this.t0 = t0;
            this.tf = tf;
            this.a = a;
            this.markov = markov;
            this.threadName = threadName;
            data = new JSONObject();
        }

        void registerListener(ThreadListener listener) {
            this.listener = listener;
        }

        /**
         * @return the data
         */
        public JSONObject getData() {
            return data;
        }

        @Override
        public void run() {
            int[] best = { -1 };
            try {
                EdgeWeightedDiGraph graph = Helpers.parseJSON(graphFile, best);
                int[] set = new int[graph.V()];
                for (int i = 0; i < set.length; i++) {
                    set[i] = i;
                }
                long start = System.currentTimeMillis();
                TSP5 tsp5 = new TSP5(graph, set, t0, tf, a, markov, 0.5);
                long end = System.currentTimeMillis();
                data.put("xAxisData", tsp5.getXAxisData());
                data.put("seriesData", tsp5.getSeries1Data());
                data.put("best", best[0]);
                data.put("path", tsp5.getPath());
                data.put("duration", (end - start));
                TSPStopableServlet.threadMap.remove(threadName);
                listener.afterDone();
            } catch (IOException e) {
                e.printStackTrace();
                TSPStopableServlet.threadMap.remove(threadName);
            }
        }
    }
}