package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c ORDER BY c.id")
public class Client implements Serializable, IModel{
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String email;
	private String name;
	private String passport;
	private String phone;
	private String surname;

	@OneToMany(mappedBy="client")
	private List<Order> orders;

	public Client() {}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassport() {
		return this.passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setClient(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setClient(null);

		return order;
	}

	@Override
	public String[] getTableHeaders() {
		return new String[]{"id","surname","name","email", "phone","passport"};
	}

	@Override
	public Object[] getTableRowData() {
		return new Object[]{id,surname,name,email,phone,passport};
	}

	@Override
	public void updateWith(Object mask) {
		surname = ((Client)mask).getSurname();
		name = ((Client)mask).getName();
		email = ((Client)mask).getEmail();
		phone = ((Client)mask).getPhone();
		passport = ((Client)mask).getPassport();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passport == null) ? 0 : passport.hashCode());
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
		Client other = (Client) obj;
		if (passport == null) {
			if (other.passport != null)
				return false;
		} else if (!passport.equals(other.passport))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Прізвище:" + surname + ", Ім'я" + name + ", Ел.пошта:" + email + ", Серія паспорта:"
				+ passport + ", Телефон:" + phone;
	}
}