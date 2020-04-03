package mine.learn.graphtheory.gui;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import mine.learn.graphtheory.bean.EdgeWeightedDiGraph;
import mine.learn.graphtheory.computational_optimization.heuristic.TSP5;
import mine.learn.graphtheory.util.Helpers;

/**
 * TSPServlet
 */
@WebServlet("/tsp")
public class TSPServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -8966950252121145421L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String file = req.getParameter("file");
        int[] best = { -1 };
        EdgeWeightedDiGraph graph = Helpers.parseJSON(
                new File("C:/Users/Rxy/Documents/MYCODE/projects/GraphTheory/src/main/resources", file), best);
        int[] set = new int[graph.V()];
        for (int i = 0; i < set.length; i++) {
            set[i] = i;
        }
        long start = System.currentTimeMillis();
        TSP5 tsp5 = new TSP5(graph, set, Double.parseDouble(req.getParameter("t0")),
                Double.parseDouble(req.getParameter("tf")), Double.parseDouble(req.getParameter("a")),
                Integer.parseInt(req.getParameter("markov")), 0.5);
        long end = System.currentTimeMillis();
        JSONObject data = new JSONObject();
        data.put("xAxisData", tsp5.getXAxisData());
        data.put("seriesData", tsp5.getSeries1Data());
        data.put("best", best[0]);
        data.put("duration", (end - start));
        data.put("path", tsp5.getPath());
        resp.getWriter().write(data.toJSONString());
    }
}