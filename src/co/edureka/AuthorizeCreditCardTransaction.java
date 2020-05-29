package co.edureka;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/authorize1")
public class AuthorizeCreditCardTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static PreparedStatement pst = null;

	private static Statement statement;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/banking", "root", "password");

			pst = con.prepareStatement("select * from credit_card where Name=? and Card_no=? and Cvv2=?");
		} catch (Exception ex) {
			System.out.println("Error:" + ex.toString());
		}

		String name = request.getParameter("crname");
		String card = request.getParameter("crcard");
		String cv = request.getParameter("crcvv2");
		String amt1 = request.getParameter("cramt");
		float amt = Float.parseFloat(amt1);

		try {
			pst.setString(1, name);
			pst.setString(2, card);
			pst.setString(3, cv);

			ResultSet rs = pst.executeQuery();

			if (rs.next() && (amt < 10000)) {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println("<p style=font-size:30px;color:darkblue;text-align:center>CREDIT CARD AUTHORIZED</p>");
				RequestDispatcher rd = request.getRequestDispatcher("Authorize.html");
				rd.include(request, response);
			} else {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println("<p style=font-size:30px;color:red;text-align:center>CREDIT CARD NOT AUTHORIZED</p>");
				RequestDispatcher rd = request.getRequestDispatcher("Authorize.html");
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
