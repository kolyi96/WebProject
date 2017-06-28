package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.TableModel;
import controller.JpaController;

@WebServlet("/SelectTable")
public class SelectTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static JpaController controller ;
	public static String className;
	
	public SelectTable() {
        super();
    }
	public static JpaController getController() {
		if(controller==null)
			controller = new JpaController();
		return controller;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String className = request.getParameter("className");
		String action = request.getParameter("act");
		TableModel model = getController().getModel(className);
		StringBuilder str = new StringBuilder(); 		
		//Формируем таблицу для вывода
		str.append("<table><tr>");
		if(action != null && action.equals("DeleteAction"))//Таблица на удаление
			str.append("<th>Удалить</th>");
		else if(action != null && action.equals("ChangeAction"))//Таблица на изменение
			str.append("<th>Редагувати</th>");
		for(int i = 0; i < model.getColumnCount(); i++)
			str.append("<th>"+model.getColumnName(i)+"</th>");
		str.append("</tr>");
		for(int i = 0; i < model.getRowCount(); i++){
			str.append("<tr>");
			if(action != null && action.equals("DeleteAction"))
				str.append("<td><a href='DeleteRow?className="+className+"&value="+model.getValueAt(i,0)+"'><img src='delete.png' width='20px' height='20px'></a></td>");
			else if(action != null && action.equals("ChangeAction"))
				str.append("<td><a href='ChangeRow?className="+className+"&value="+model.getValueAt(i,0)+"'><img src='change.png' width='20px' height='20px'></a></td>");
			for(int j = 0; j < model.getColumnCount(); j++)
				str.append("<td>"+model.getValueAt(i,j)+"</td>");
			str.append("</tr>");
		}
		str.append("</table>");
		request.setAttribute("className", className);
		request.setAttribute("table", str.toString());
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
