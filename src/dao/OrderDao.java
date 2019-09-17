package dao;
import model.Order;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class OrderDao {

	private List<Order> orders;
	
	public OrderDao() {
		orders = new ArrayList<>();
		init();
	}
	
	public void init() {
		try {
			//InitialContext ctx = new InitialContext();
			//DataSource ds = (DataSource) ctx.lookup("jdbc/SQLServer");
			
			//Connection conn = ds.getConnection();
			Connection conn=DriverManager.getConnection(  
					"jdbc:oracle:thin:@localhost:1521:xe","system","Misia123");  
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT order_id, client_id, book_id FROM xe.orders");
			while (rs.next()) {
				orders.add(new Order(
						Long.parseLong(rs.getString("order_id")),
						Long.parseLong(rs.getString("client_id")),
						Long.parseLong(rs.getString("book_id"))
				));
			}
			
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Order> findAll() {
		return orders;
	}
	
	public Order findByOrderId(Long orderId) {
		ListIterator<Order> iterator = orders.listIterator();
		
		 while (iterator.hasNext()) {
			 if(iterator.next().getOrderId()==orderId)
				 return iterator.next();   
		 }
		 return null;
	}
	
	public void save(Order order) {
		// TODO: init JDBC connection once/move create somewhere else
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("jdbc/SQLServer");
			
			//Connection conn = ds.getConnection();
			Connection conn=DriverManager.getConnection(  
					"jdbc:oracle:thin:@localhost:1521:xe","system","Misia123");  
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO orders (order_id) VALUES (?)");
			
			
			pstmt.setLong(1, order.getOrderId());
			
			if(pstmt.executeUpdate() != 1) { // shouldn't insert more than 1 row
				pstmt.close();
				conn.close();
				throw new Exception("Error adding order");
			}
			
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
