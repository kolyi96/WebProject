package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the flat database table.
 * 
 */
@Entity
@NamedQuery(name="Flat.findAll", query="SELECT f FROM Flat f ORDER BY f.address")
public class Flat implements Serializable, IModel{
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String address;
	private int numFlat;
	private float price;
	private int rooms;
	private float square;

	@OneToMany(mappedBy="flat")
	private List<Manager> managers;

	@OneToMany(mappedBy="flat")
	private List<Order> orders;

	public Flat() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getNumFlat() {
		return this.numFlat;
	}

	public void setNumFlat(int numFlat) {
		this.numFlat = numFlat;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getRooms() {
		return this.rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public float getSquare() {
		return this.square;
	}

	public void setSquare(float square) {
		this.square = square;
	}

	public List<Manager> getManagers() {
		return this.managers;
	}

	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}

	public Manager addManager(Manager manager) {
		getManagers().add(manager);
		manager.setFlat(this);

		return manager;
	}

	public Manager removeManager(Manager manager) {
		getManagers().remove(manager);
		manager.setFlat(null);

		return manager;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setFlat(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setFlat(null);

		return order;
	}

	@Override
	public String[] getTableHeaders() {
		return new String[]{"id","address","numFlat","square","rooms","price"};
	}

	@Override
	public Object[] getTableRowData() {
		return new Object[]{id,address,numFlat,square,rooms,price};
	}

	@Override
	public void updateWith(Object mask) {
		address = ((Flat)mask).getAddress();
		numFlat = ((Flat)mask).getNumFlat();
		price = ((Flat)mask).getPrice();
		rooms = ((Flat)mask).getRooms();
		square = ((Flat)mask).getSquare();	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + numFlat;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flat other = (Flat) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (numFlat != other.numFlat)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Адрес:" + address + ", Номер кв.:" + numFlat + ", Площа:" + square + ", Кіль.кімнат:" + rooms
				+ ", Ціна:" + price;
	}
}