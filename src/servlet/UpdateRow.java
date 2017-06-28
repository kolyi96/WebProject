package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.JpaController;
import model.IModel;

@WebServlet("/UpdateRow")
public class UpdateRow extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UpdateRow() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JpaController controller = SelectTable.getController();
		String className = request.getParameter("className");
		IModel obj = (IModel)request.getAttribute("obj");
		controller.edit(obj);
		request.setAttribute("className", className);
		request.setAttribute("act", "update");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
