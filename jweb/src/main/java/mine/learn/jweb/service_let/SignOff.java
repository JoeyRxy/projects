package mine.learn.jweb.service_let;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * SignOff
 */
@WebServlet("/signoff")
public class SignOff extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Helper.isSignIn(req))
            return;
        HttpSession session = req.getSession();
        session.setAttribute("signin", false);

    }
}