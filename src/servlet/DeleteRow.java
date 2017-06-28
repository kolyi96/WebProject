package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.JpaController;

@WebServlet("/DeleteRow")
public class DeleteRow extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteRow() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JpaController controller = SelectTable.getController();
		String className = request.getParameter("className");
		int id = Integer.parseInt(request.getParameter("value"));
		controller.delete(id, className);
		request.setAttribute("className", className);
		request.setAttribute("act", "delete");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
