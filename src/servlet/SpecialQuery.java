package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.TableModel;

import controller.JpaController;


@WebServlet("/SpecialQuery")
public class SpecialQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SpecialQuery() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JpaController controller = SelectTable.getController();
		TableModel model = controller.doQuery();
		StringBuilder str = new StringBuilder(); 
		str.append("<p></br>Список квартир ціна яких > 100 000</p>");
		str.append("<table><tr>");
		for(int i = 0; i < model.getColumnCount(); i++)
			str.append("<th>"+model.getColumnName(i)+"</th>");
		str.append("</tr>");
		for(int i = 0; i < model.getRowCount(); i++){
			str.append("<tr>");
			for(int j = 0; j < model.getColumnCount(); j++)
				str.append("<td>"+model.getValueAt(i,j)+"</td>");
			str.append("</tr>");
		}
		str.append("</table>");
		request.setAttribute("table", str.toString());
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
