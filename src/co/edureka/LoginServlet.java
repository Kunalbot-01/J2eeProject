package co.edureka;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static PreparedStatement pst = null;

	private static Statement statement;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/banking", "root", "password");

			pst = con.prepareStatement("select * from user_pass where username=? and password=?");
		} catch (Exception ex) {
			System.out.println("Error:" + ex.toString());
		}

		String user = request.getParameter("uid");
		String pass = request.getParameter("pwd");

		try {
			pst.setString(1, user);
			pst.setString(2, pass);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("/welcome");
				rd.forward(request, response);
			} else {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println("<p style=font-size:30px;color:red;text-align:center>INVALID USERNAME/PASSWORD</p>");
				RequestDispatcher rd = request.getRequestDispatcher("Login.html");
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
