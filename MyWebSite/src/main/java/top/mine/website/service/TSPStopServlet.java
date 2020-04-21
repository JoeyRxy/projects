package top.mine.website.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import top.mine.website.util.LogStateCheck;

@WebServlet("/tspstop")
public class TSPStopServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -6676242586452602658L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!LogStateCheck.checkLogState(req))
            throw new IllegalArgumentException();
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String threadName = req.getParameter("threadName");
        threadName = req.getSession().getAttribute("name") + threadName;
        Thread thread = TSPStopableServlet.threadMap.remove(threadName);
        // thread.interrupt();
        thread.stop();
    }
}