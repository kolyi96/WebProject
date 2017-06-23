package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQuery(name="Manager.findAll", query="SELECT m FROM Manager m ORDER BY m.id")
public class Manager implements Serializable, IModel{
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String email;
	private String name;
	private String phone;
	private String surname;

	//bi-directional many-to-one association to Flat
	@ManyToOne
	@JoinColumn(name="id_flat")
	private Flat flat;

	public Manager() {}

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

	public Flat getFlat() {
		return this.flat;
	}

	public void setFlat(Flat flat) {
		this.flat = flat;
	}

	@Override
	public String[] getTableHeaders() {
		return new String[]{"id","surname","name","email", "phone","id_flat","address","numFlat","price", "rooms","square"};
	}

	@Override
	public Object[] getTableRowData() {
		return new Object[]{id,surname,name,email,phone,flat.getId(),flat.getAddress(),flat.getNumFlat(),flat.getPrice(),flat.getRooms(),flat.getSquare()};
	}

	@Override
	public void updateWith(Object mask) {
		surname = ((Manager)mask).getSurname();
		name = ((Manager)mask).getName();
		email = ((Manager)mask).getEmail();
		phone = ((Manager)mask).getPhone();
		flat = ((Manager)mask).getFlat();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		Manager other = (Manager) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

}