package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o ORDER BY o.date")
public class Order implements Serializable, IModel {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	// bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name = "id_client")
	private Client client;

	// bi-directional many-to-one association to Flat
	@ManyToOne
	@JoinColumn(name = "id_flat")
	private Flat flat;

	public Order() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Flat getFlat() {
		return this.flat;
	}

	public void setFlat(Flat flat) {
		this.flat = flat;
	}

	@Override
	public String[] getTableHeaders() {
		return new String[] { "id", "date", "id_flat", "address", "numFlat", "price", "rooms", "square", "id_client",
				"surname", "name", "email", "phone", "passport" };
	}

	@Override
	public Object[] getTableRowData() {
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
		return new Object[] { id, sqlDate, flat.getId(), flat.getAddress(), flat.getNumFlat(), flat.getPrice(),
				flat.getRooms(), flat.getSquare(), client.getId(), client.getSurname(), client.getName(),
				client.getEmail(), client.getPhone(), client.getPassport() };
	}

	@Override
	public void updateWith(Object mask) {
		date = ((Order) mask).getDate();
		flat = ((Order) mask).getFlat();
		client = ((Order) mask).getClient();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((flat == null) ? 0 : flat.hashCode());
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
		Order other = (Order) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (flat == null) {
			if (other.flat != null)
				return false;
		} else if (!flat.equals(other.flat))
			return false;
		return true;
	}

}