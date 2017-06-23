package test;

import controller.JpaController;
import model.Flat;

public class test {
	public static void main(String[] args) {
		JpaController jc = new JpaController();
		Flat f = new Flat();
		f.setAddress("Gusavka");
		f.setNumFlat(1);
		f.setPrice(500000);
		f.setRooms(5);
		f.setSquare(144);
		jc.add(f);
	}
}
