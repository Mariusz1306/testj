import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/Books")
public class Books extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Books() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("jdbc/SQLServer");
			
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT book_id, title, author_name, availability FROM books;");
			
			response.setContentType("text/html");
			
			response.getWriter().println("<html><body>");
			while(rs.next()) {
				response.getWriter().println(rs.getString("book_id") + " " + rs.getString("author_name")
				+ " " + rs.getString("title")+ " " + rs.getString("avilability")+ "<br />");
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
