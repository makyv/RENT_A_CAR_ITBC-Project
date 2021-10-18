package com.example.Project;

import connection.DatabaseConnection;
import model.CarModel;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RentACarApplication {
	static final Connection conn = DatabaseConnection.getConnection();


	public static void main(String[] args) {
		//SpringApplication.run(RentACarApplication.class, args);

		List<CarModel> allCars = new ArrayList<>();

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM cars");
			if(rs.next()) {
				CarModel newCar = new CarModel (
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getInt(5),
						rs.getInt(6),
						rs.getString(7),
						rs.getDouble(8),
						rs.getInt(9),
						rs.getString(10),
						rs.getInt(11),
						rs.getBoolean(12),
						rs.getString(13),
						rs.getString(14));

				allCars.add(newCar);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(allCars.toString());
	}


	}




