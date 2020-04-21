package top.mine.website.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import top.mine.website.dao.tsputil.EdgeWeightedDiGraph;
import top.mine.website.dao.tsputil.Helpers;
import top.mine.website.dao.tsputil.WeightedDirectedEdge;
import top.mine.website.util.LogStateCheck;

@WebServlet("/graph")
public class GraphServlet extends HttpServlet {
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
    private static final long serialVersionUID = 7443979068048359168L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!LogStateCheck.checkLogState(req))
            throw new IllegalArgumentException();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String file = req.getParameter("file");
        int[] best = { -1 };
        EdgeWeightedDiGraph graph = Helpers.parseJSON(new File(tspfilesRoot, file), best);
        JSONObject res = new JSONObject();
        int V = graph.V();
        List<JSONObject> nodes = new ArrayList<>(V);
        List<JSONObject> edges = new ArrayList<>(graph.E());
        for (int i = 0; i < V; i++) {
            nodes.add(JSONObject.parseObject("{name:'" + i + "'}"));
        }
        for (WeightedDirectedEdge edge : graph.edges()) {
            edges.add(JSONObject.parseObject(
                    "{source:'" + edge.from() + "',target:'" + edge.to() + "',value:'" + edge.weight() + "'}"));
        }
        res.put("nodes", nodes);
        res.put("edges", edges);
        resp.getWriter().write(res.toJSONString());
    }
}