package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDao;
import dao.SellerDao;
import model.Customer;
import model.Seller;

/**
 * Servlet implementation class CustomerController
 */
@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		if(action.equalsIgnoreCase("register")) {
			Customer c = new Customer();
			c.setName(request.getParameter("name"));
			c.setContact(Long.parseLong(request.getParameter("contact")));
			c.setAddress(request.getParameter("address"));
			c.setEmail(request.getParameter("email"));
			c.setPassword(request.getParameter("password"));
			System.out.println(c);
			CustomerDao.insertCustomer(c);
			request.setAttribute("msg", "data inserted successfully");
			request.getRequestDispatcher("customer-login.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("login")) {
			Customer c  =new Customer();
			c.setEmail(request.getParameter("email"));
			c.setPassword(request.getParameter("password"));
			Customer c1 = CustomerDao.checkCustomerLogin(c);
			if(c1 == null) {
				request.setAttribute("login", "email or password is incorrect");
				request.getRequestDispatcher("customer-login.jsp").forward(request, response);
			}
			else{
				HttpSession session = request.getSession();
				session.setAttribute("data", c1);
				request.getRequestDispatcher("customer-index.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("update")) {
			Customer c = new Customer();
			c.setId(Integer.parseInt(request.getParameter("id")));
			c.setName(request.getParameter("name"));
			c.setContact(Long.parseLong(request.getParameter("contact")));
			c.setAddress(request.getParameter("address"));
			c.setEmail(request.getParameter("email"));
			CustomerDao.updateCustomerProfile(c);
			HttpSession session = request.getSession();
			session.setAttribute("data", c);
			request.getRequestDispatcher("customer-profile.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("change password")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String op = request.getParameter("op");
			String np = request.getParameter("np");
			String cnp = request.getParameter("cnp");
			boolean flag = CustomerDao.checkOlPassword(id, op);
			if(flag == true) {
				if(np.equals(cnp)) {
					CustomerDao.udpatePassword(np, id);
					response.sendRedirect("customer-index.jsp");
				}
				else {
					request.setAttribute("msg1", "new password and confirm new password not matched");
					request.getRequestDispatcher("customer-change-password.jsp").forward(request, response);
				}
			}
			else {
				request.setAttribute("msg", "old password is not correct");
				request.getRequestDispatcher("customer-change-password.jsp").forward(request, response);
			}
		}
	}

}
