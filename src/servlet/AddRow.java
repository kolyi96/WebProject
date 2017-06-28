package servlet;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.TableModel;
import controller.JpaController;
import model.Client;
import model.Flat;
import model.IModel;
import model.Manager;
import model.Order;

@WebServlet("/AddRow")
public class AddRow extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddRow() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JpaController controller = SelectTable.getController();
		String className = (String)request.getParameter("className");
		IModel obj = null;
		if(className == "")
			return;
		if(className.equals("Flat")){
			obj = new Flat();
			((Flat)obj).setAddress(request.getParameter("address"));
			((Flat)obj).setSquare(Float.parseFloat(request.getParameter("square")));
			((Flat)obj).setRooms(Integer.parseInt(request.getParameter("countRoom")));
			((Flat)obj).setNumFlat(Integer.parseInt(request.getParameter("number")));
			((Flat)obj).setPrice(Float.parseFloat(request.getParameter("price")));
		}
		else if(className.equals("Client")){
			obj = new Client();
			((Client)obj).setSurname(request.getParameter("surname"));
			((Client)obj).setName(request.getParameter("name"));
			((Client)obj).setEmail(request.getParameter("email"));
			((Client)obj).setPhone(request.getParameter("phone"));
			((Client)obj).setPassport(request.getParameter("passport"));
		}
		else if(className.equals("Order")){
			Flat flat = null;
			Client client = null;
			TableModel model = controller.getModel("Flat");
			int id_flat = Integer.parseInt(request.getParameter("selectFlat"));
			int id_client = Integer.parseInt(request.getParameter("selectClient"));
			for(int i = 0; i < model.getRowCount(); i++){
				if((Integer)model.getValueAt(i, 0) == id_flat){
					flat = new Flat();
					flat.setId((Integer)model.getValueAt(i, 0));
					flat.setAddress((String)model.getValueAt(i, 1));
					flat.setNumFlat((Integer)model.getValueAt(i, 2));
					flat.setSquare((Float)model.getValueAt(i, 3));
					flat.setRooms((Integer)model.getValueAt(i, 4));
					flat.setPrice((Float)model.getValueAt(i, 5));
				}
			}
			model = controller.getModel("Client");
			for(int i = 0; i < model.getRowCount(); i++){
				if((Integer)model.getValueAt(i, 0) == id_client){
					client = new Client();
					client.setId((Integer)model.getValueAt(i, 0));
					client.setSurname((String)model.getValueAt(i, 1));
					client.setName((String)model.getValueAt(i, 2));
					client.setEmail((String)model.getValueAt(i, 3));
					client.setPhone((String)model.getValueAt(i, 4));
					client.setPassport((String)model.getValueAt(i, 5));
				}
			}
			obj = new Order();
			((Order)obj).setClient(client);
			((Order)obj).setFlat(flat);
			((Order)obj).setDate(new Date());
		}
		else if(className.equals("Manager")){
			Flat flat = null;
			TableModel model = controller.getModel("Flat");
			int id_flat = Integer.parseInt(request.getParameter("selectFlat"));
			for(int i = 0; i < model.getRowCount(); i++){
				if((Integer)model.getValueAt(i, 0) == id_flat){
					flat = new Flat();
					flat.setId((Integer)model.getValueAt(i, 0));
					flat.setAddress((String)model.getValueAt(i, 1));
					flat.setNumFlat((Integer)model.getValueAt(i, 2));
					flat.setSquare((Float)model.getValueAt(i, 3));
					flat.setRooms((Integer)model.getValueAt(i, 4));
					flat.setPrice((Float)model.getValueAt(i, 5));
				}
			}
			obj = new Manager();
			((Manager)obj).setSurname(request.getParameter("surname"));
			((Manager)obj).setName(request.getParameter("name"));
			((Manager)obj).setEmail(request.getParameter("email"));
			((Manager)obj).setPhone(request.getParameter("phone"));
			((Manager)obj).setFlat(flat);
		}
		String id = request.getParameter("id");
		if(id != ""){
			request.setAttribute("className", className);
			request.setAttribute("id", id);
			request.setAttribute("obj", obj);
			request.getRequestDispatcher("UpdateRow").include(request, response);
		}
		else{
			controller.add(obj);
			request.setAttribute("className", className);
			request.setAttribute("act", "add");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
