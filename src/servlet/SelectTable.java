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

@WebServlet("/SelectTable")
public class SelectTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	public SelectTable() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JpaController controller = (JpaController)session.getAttribute("controller");
		String className = request.getParameter("className");
		String action = request.getParameter("action");
		TableModel model = controller.getModel(className);
		StringBuilder str = new StringBuilder(); 	
		
		session.setAttribute("className", className);
		session.setAttribute("action", action);
		str.append("<table><tr>");
		if(action.equals("Delete"))
			str.append("<th>Видалити</th>");
		else if(action.equals("Change"))
			str.append("<th>Редактувати</th>");
		for(int i = 0; i < model.getColumnCount(); i++)
			str.append("<th>"+model.getColumnName(i)+"</th>");
		str.append("</tr>");
		for(int i = 0; i < model.getRowCount(); i++){
			str.append("<tr>");
			if(action != null && action.equals("Delete"))
				str.append("<td><a  href='DeleteRow?id="+model.getValueAt(i,0)+"'><img src='delete.png' width='20px' height='20px'></a></td>");
			else if(action != null && action.equals("Change"))
				str.append("<td><a onclick='changeRow("+model.getValueAt(i,0)+");' href='#'><img src='change.png' width='20px' height='20px'></a></td>");
			for(int j = 0; j < model.getColumnCount(); j++)
				str.append("<td>"+model.getValueAt(i,j)+"</td>");
			str.append("</tr>");
		}
		str.append("</table>");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(str.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
