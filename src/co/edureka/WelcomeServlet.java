package co.edureka;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		/*
		 * out.println("<html><head><title>MAYUKH | HOME</title>"); out.
		 * print("<link rel='stylesheet' type='text/css' href='css/mystyle.css'></head>"
		 * ); out.println("<body>"); out.println("<div id=wrapper>");
		 * out.println("<h1>BANKING SYSTEM</h1><br><br><br>\r\n" +
		 * "<p><h2>WELCOME USER<h2></p>\r\n" + "<table>\r\n" + "<tr>\r\n" +
		 * "<td><input type=submit action =\"/CreateAccount\" value=\"CREATE ACCOUNT\"></td>\r\n"
		 * + "</tr>\r\n" + "<tr>\r\n" +
		 * "<td><input type=submit value=\"TRANSACTIONS\"></td>\r\n" + "</tr>\r\n" +
		 * "<tr>\r\n" +
		 * "<td><input type=submit value=\"DISPLAY BANK STATEMENT\"></td>\r\n" +
		 * "</tr>\r\n" + "<tr>\r\n" +
		 * "<td><input type=submit value=\"AUTHORIZE CREDIT CARD TRANSACTIONS\"></td>\r\n"
		 * + "</tr>\r\n" + "</table>"); out.println("</div></body></html>");
		 */

		RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
		rd.include(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
