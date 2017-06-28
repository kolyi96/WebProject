package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		JpaController controller = SelectTable.getController();
		String className = request.getParameter("className");
		int id = Integer.parseInt(request.getParameter("value"));
		TableModel model = controller.getModel(className);
		StringBuilder str = new StringBuilder();
		if (className.equals("Flat")) {
			str.append("var selectedElements = $('#flatBlock input');");
			for (int i = 0; i < model.getRowCount(); i++) {
				if (id == (Integer) model.getValueAt(i, 0)) {
					str.append("selectedElements[" + 0 + "].value = " + model.getValueAt(i, 0) + ";");
					str.append("selectedElements[" + 1 + "].value = '" + model.getValueAt(i, 1) + "';");
					for (int j = 2; j < model.getColumnCount(); j++) {
						str.append("selectedElements[" + j + "].value = " + model.getValueAt(i, j) + ";");
					}
					break;
				}
			}
			str.append("$('#flatBlock').dialog('open');");
		}
		else if (className.equals("Client")) {
			str.append("var selectedElements = $('#clientBlock input');");
			for (int i = 0; i < model.getRowCount(); i++) {
				if (id == (Integer) model.getValueAt(i, 0)) {
					str.append("selectedElements[" + 0 + "].value = " + model.getValueAt(i, 0) + ";");
					for (int j = 1; j < model.getColumnCount(); j++) {
						str.append("selectedElements[" + j + "].value = '" + model.getValueAt(i, j) + "';");
					}
					break;
				}
			}
			str.append("$('#clientBlock').dialog('open');");
		}
		else if (className.equals("Manager")) {
			str.append("var selectedElements = $('#managerBlock input');");
			for (int i = 0; i < model.getRowCount(); i++) {
				if (id == (Integer) model.getValueAt(i, 0)) {
					str.append("selectedElements[" + 0 + "].value = " + model.getValueAt(i, 0) + ";");
					for (int j = 1; j < 5; j++) {
						str.append("selectedElements[" + j + "].value = '" + model.getValueAt(i, j) + "';");
					}
					request.setAttribute("id_flat", String.valueOf(model.getValueAt(i, 5)));
					break;
				}
			}
			str.append("$('#managerBlock').dialog('open');");
		}
		else if (className.equals("Order")) {
			str.append("var selectedElements = $('#orderBlock input');");
			for (int i = 0; i < model.getRowCount(); i++) {
				if (id == (Integer) model.getValueAt(i, 0)) {
					str.append("selectedElements[" + 0 + "].value = " + model.getValueAt(i, 0) + ";");
					request.setAttribute("id_flat", String.valueOf(model.getValueAt(i, 2)));
					request.setAttribute("id_client", String.valueOf(model.getValueAt(i, 8)));
					break;
				}
			}
			str.append("$('#orderBlock').dialog('open');");
		}
		request.setAttribute("str", str.toString());
		request.setAttribute("className", className);
		request.setAttribute("act", "change");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
