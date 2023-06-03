package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Jdbc2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	  
		/*
		 * String url = "jdbc:mysql://localhost:3307/employee"; String uname = "root";
		 * String pwd = "swap1234"; String query = "Insert into emp value (?,?)";
		 * Class.forName("com.mysql.jdbc.Driver");
		 * 
		 * Connection con = DriverManager.getConnection(url,uname,pwd);
		 * PreparedStatement ps = con.prepareStatement(query); ps.setInt(1,5);
		 * ps.setString(2,"Ganesh");
		 * 
		 * int count = ps.executeUpdate();
		 * 
		 * System.out.println(count+" rows affected");
		 */
		
		
		DB db = new DB();
		
		String c ="";
		do {
			System.out.println("----------------------------------------------------------------------");
			System.out.println("1. Add a vehicle\n 2. Sell a vehicle \n 3. Show Buy \n 4. Show Sold \n 5. Exit press e/E ");
			System.out.println(">> ");
			Scanner sc = new Scanner(System.in);
			
			c = sc.next();
			
			if(c.equalsIgnoreCase("1")) {
				
				System.out.println("Enter Prod Type: ");
				sc.nextLine();
				String ptype = sc.nextLine();
				System.out.println("Enter Prod Name :");
				String pname = sc.nextLine();
				try {
				int rows = db.addVehicleBuy(ptype,pname);
				if(rows >0)
				System.out.println("Successfully Added Vehicle");
				}
				catch(SQLException e) {
					System.out.println("DB Error");
				}
				
			}
			else if(c.equalsIgnoreCase("2")) {
				System.out.println("Enter Sell Vehincle Name: ");
				sc.nextLine();
				String sName = sc.nextLine();
				//try {
					String type = db.deleteVehicleBuy(sName);
					if(type!=null) {
						System.out.println("Successfully Deleted Vehicle from Buy");
					}
					int rows = db.addVehicleSell(type,sName);
//				}catch(SQLException e) {
//					System.out.println("DB Error");
//				}
			}
            else if(c.equalsIgnoreCase("3")) {
				System.out.println("Vehicles bought: ");
				System.out.println("Type : Name");
				db.showVehicleBuy();
			}
            else if(c.equalsIgnoreCase("4")) {
            	System.out.println("Vehicles sold: ");
				System.out.println("Type : Name");
				db.showVehicleSold();
			}
            else if(c.equalsIgnoreCase("e")) {
            	System.out.println("Thank you. For easy Buy and Sell Always Choose SellEz!!");
            }
            else {
            	System.out.println("Please enter correct input");
            }
			
		}while(!c.equalsIgnoreCase("e"));
		
	}

}

class DB{
	
	public int addVehicleBuy(String ptype, String pname) throws SQLException, ClassNotFoundException {
		
		String query = "Insert into buy (ProductType, ProductName) value(?,?)";
		
		DB db = new DB();
		Connection con = db.connection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1,ptype);
		ps.setString(2,pname);
		
		int count = ps.executeUpdate();
		con.close();
		ps.close();
		return count;
	}
	
	public String deleteVehicleBuy(String sName) throws ClassNotFoundException, SQLException {
		
		System.out.println("Vehicle name : "+sName);
		DB db = new DB();
		Connection con = db.connection();
		String query1 = "Delete from buy where ProductName='"+sName+"'";
		String query = "Select ProductType from buy where ProductName='"+sName+"'";
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		rs.next();
		String prodType = rs.getString(1);
		System.out.println("Vehicle Type: "+prodType);
		
		 st.executeUpdate(query1);
		 con.close();
		 rs.close();
		 
		 return prodType;
			
	}
	
	public int addVehicleSell(String ptype, String pname) throws ClassNotFoundException, SQLException {
		DB db = new DB();
		Connection con = db.connection();
		String query = "Insert into sold(ProductType, ProductName) values (?,?)";
		
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1,ptype);
		ps.setString(2,pname);
		
		int count = ps.executeUpdate();
		con.close();
		ps.close();
		
		return count;
		
	}
	
	public void showVehicleBuy() throws ClassNotFoundException, SQLException {
		DB db = new DB();
		Connection con = db.connection();
		String query = "Select * from buy";
		
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			System.out.println(rs.getString(2)+" : "+rs.getString(3));
		}
		con.close();
		rs.close();
	}
	
	public void showVehicleSold() throws ClassNotFoundException, SQLException {
		DB db = new DB();
		Connection con = db.connection();
		String query = "Select * from sold";
		
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			System.out.println(rs.getString(2)+" : "+rs.getString(3));
		}
		con.close();
		rs.close();
	}
	
	public Connection connection() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3307/sellez";
		String uname = "root";
		String pwd = "swap1234";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url,uname,pwd);
		return con;
	}
	
}