package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import connection.DBConnection;
import model.Product;

public class ProductDao {
	public static void insertProduct(Product p) {
		try {
			Connection conn = DBConnection.createConnection();
			String sql = "insert into product(sid,image,pname,pprice,pcategory) values(?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, p.getSid());
			pst.setString(2, p.getImage());
			pst.setString(3, p.getPname());
			pst.setDouble(4, p.getPprice());
			pst.setString(5, p.getPcategory());
			pst.executeUpdate();
			System.out.println("data inserted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
