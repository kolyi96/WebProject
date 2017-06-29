package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.table.TableModel;
import controller.JpaController;

@WebServlet("/ChangeRow")
public class ChangeRow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChangeRow() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		HttpSession session = request.getSession();
		JpaController controller = (JpaController)session.getAttribute("controller");
		String className = (String)session.getAttribute("className");
		int id = Integer.parseInt(request.getParameter("id"));
		TableModel model = controller.getModel(className);
		StringBuilder str = new StringBuilder();
		str.append("[");
		for (int i = 0; i < model.getRowCount(); i++) {
			if (id == (Integer) model.getValueAt(i, 0)) {
				for (int j = 0; j < model.getColumnCount(); j++) 
					str.append("\"" + model.getValueAt(i, j) + "\",");
				break;
			}
		}
		str.replace(str.length()-1, str.length(), "]");
		response.setContentType("text/plain");
		response.getWriter().write(str.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
