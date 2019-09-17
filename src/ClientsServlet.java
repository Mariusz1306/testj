import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClientDao;

@WebServlet(name = "ClientServlet", urlPatterns = {"/clients"})
public class ClientsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClientDao dao = new ClientDao();
		request.setAttribute("products", dao.findAll());
		request.getRequestDispatcher("/product.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchPhrase = request.getParameter("search");
		if(searchPhrase == null) {
			doGet(request, response);
		}
		else {
			ClientDao dao = new ClientDao();
			request.setAttribute("products", dao.findByClientId((Long.valueOf(searchPhrase))));
			request.getRequestDispatcher("/product.jsp").forward(request, response);
		}
	}

}
