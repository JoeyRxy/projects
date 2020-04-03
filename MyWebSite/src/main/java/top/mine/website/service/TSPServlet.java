package top.mine.website.service;

import java.io.File;
import java.io.IOException;

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
        if (!LogStateCheck.checkLogState(req))
            throw new IllegalArgumentException();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String file = req.getParameter("file");
        int[] best = { -1 };
        EdgeWeightedDiGraph graph = Helpers.parseJSON(new File("/home/rxy/files/.tsp", file), best);
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
        data.put("path", tsp5.getPath());
        data.put("duration", (end - start));
        resp.getWriter().write(data.toJSONString());
    }
}