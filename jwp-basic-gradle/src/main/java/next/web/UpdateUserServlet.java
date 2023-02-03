package next.web;


import core.db.DataBase;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import next.model.User;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {

    private User getUser(HttpServletRequest req){
        var user = req.getSession().getAttribute("user");
        if(user == null) {
            return null;
        }
        return (User) user;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var user = this.getUser(req);
        if(user == null){
            resp.sendRedirect("/user/list");
            return ;
        }
        req.setAttribute("user", user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/update.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var user = this.getUser(req);
        if(user == null){
            resp.sendRedirect("/user/list");
            return ;
        }
        user.updateUserInformation(req.getParameter("password"), req.getParameter("name"), req.getParameter("email"));
        resp.sendRedirect("/user/list");
    }
}
