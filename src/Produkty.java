import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class Produkty
 */
@WebServlet("/Produkty")
public class Produkty extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Produkty() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//InitialContext ctx = new InitialContext();
			//DataSource ds = (DataSource) ctx.lookup("jdbc");
			
			//Connection conn = ds.getConnection();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn=DriverManager.getConnection(  
					"jdbc:oracle:thin:@85.89.183.106:1521:xe","system","haslo");  
			System.out.println(conn.toString());
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT productid, productname FROM products");
			
			response.setContentType("text/html");
			
			response.getWriter().println("<html><body>");
			while(rs.next()) {
				response.getWriter().println(rs.getString("productid") + " " + rs.getString("productname") + "<br />");
			}
			response.getWriter().println("</body></html>");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
