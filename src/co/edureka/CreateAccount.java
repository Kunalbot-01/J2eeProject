package co.edureka;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static PreparedStatement pst = null;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/banking", "root", "password");

			pst = con.prepareStatement("insert into account_details values(?,?,?,?,?,10000);");
		} catch (Exception ex) {
			System.out.println("Error:" + ex.toString());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String name = request.getParameter("cname");
		String dob = request.getParameter("cdob");
		String address = request.getParameter("caddress");
		String email = request.getParameter("cemail");
		String account_type = request.getParameter("my_selected_value");
		System.out.println(account_type);

		try {
			pst.setString(1, name);
			pst.setString(2, dob);
			pst.setString(3, address);
			pst.setString(4, email);
			pst.setString(5, account_type);

			int rows_affected = pst.executeUpdate();

			if (rows_affected == 1) {
				response.setContentType("text/html");
				out.println(
						"<p style=font-size:30px;color:darkblue;text-align:center>ACCOUNT CREATED SUCCESSFULLY</p>");
				RequestDispatcher rd = request.getRequestDispatcher("CreateAccount.html");
				rd.include(request, response);
			} else {
				response.setContentType("text/html");
				out.println("<p style=font-size:30px;color:red;text-align:center>ACCOUNT CREATION FAILED</p>");
				RequestDispatcher rd = request.getRequestDispatcher("CreateAccount.html");
				rd.include(request, response);
			}
			

		} catch (Exception e) {
			System.out.println("Error:" + e.toString());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
