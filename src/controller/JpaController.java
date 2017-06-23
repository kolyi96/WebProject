package controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Client;
import model.Flat;
import model.IModel;
import model.Manager;
import model.Order;

public class JpaController {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebProject");

	public List getObjectList(Class clazz) {
		EntityManager em = emf.createEntityManager();
		String queryName = clazz.getSimpleName() + "." + "findAll";
		List<Flat> list = em.createNamedQuery(queryName).getResultList();
		em.close();
		return list;
	}

	public TableModel getModel(String className) {
		try {
			Class clazz = Class.forName("model." + className);
			IModel obj = (IModel) clazz.newInstance();
			String[] header = obj.getTableHeaders();
			List list = getObjectList(clazz);
			if (list == null || list.size() == 0)
				return new DefaultTableModel(null, header);
			Object[][] array = new Object[list.size()][header.length];
			int i = 0;
			for (Object s : list)
				array[i++] = ((IModel) s).getTableRowData();
			return new DefaultTableModel(array, header);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void add(Object obj) {
		if (exist((IModel) obj))
			return;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
	}

	public void edit(int id, Object obj) {
		Class clazz = obj.getClass();
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			IModel m = null;
			if (obj instanceof Flat)
				m = em.find(Flat.class, id);
			else if (obj instanceof Order)
				m = em.find(Order.class, id);
			else if (obj instanceof Client)
				m = em.find(Client.class, id);
			else if (obj instanceof Manager)
				m = em.find(Manager.class, id);
			m.updateWith(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

	public void delete(int id, String className) {
		EntityManager em = emf.createEntityManager();
		try {
			Class clazz = Class.forName("model." + className);
			Object obj = em.find(clazz, id);
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
		} catch (ClassNotFoundException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

	}

	public boolean exist(IModel obj) {
		Class clazz = obj.getClass();
		List list = getObjectList(clazz);
		if (list != null && list.size() != 0)
			for (Object current : list)
				if (current.equals(obj))
					return true;
		return false;
	}

	public TableModel doQuery() {
		EntityManager em = emf.createEntityManager();
		Query q = em.createQuery("SELECT f FROM Flat f WHERE f.price > ?1");
		q.setParameter(1, 100000);
		List<Flat> list = q.getResultList();
		String[][] arr = new String[list.size()][6];
		System.out.println(list.size());
		int i = 0;
		for (Flat f : list) {
			arr[i][0] = String.valueOf(f.getId());
			arr[i][1] = String.valueOf(f.getAddress());
			arr[i][2] = String.valueOf(f.getNumFlat());
			arr[i][3] = String.valueOf(f.getPrice());
			arr[i][4] = String.valueOf(f.getRooms());
			arr[i][5] = String.valueOf(f.getSquare());
			i++;
		}
		return new DefaultTableModel(arr, new String[] { "id", "address", "numFlat", "price", "rooms", "square" });
	}
}
