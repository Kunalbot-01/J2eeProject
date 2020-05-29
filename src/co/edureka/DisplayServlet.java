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

@WebServlet("/display")
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static PreparedStatement pst = null;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/banking", "root", "password");

			pst = con.prepareStatement("SELECT * FROM Display WHERE Date between ? and ?");
		} catch (Exception ex) {
			System.out.println("Error:" + ex.toString());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String first = request.getParameter("fdate");
		String last = request.getParameter("ldate");
		
		try {
			pst.setString(1, first);
			pst.setString(2, last);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
			{
				RequestDispatcher rd = request.getRequestDispatcher("DisplayTables.html");
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println("<!DOCTYPE html>\r\n" + 
						"<html>\r\n" + 
						"<head>\r\n" + 
						"<meta charset=\"ISO-8859-1\">\r\n" + 
						"<title>Display</title>\r\n" + 
						"<link rel='stylesheet' type='text/css' href='css/mystyle.css'>\r\n" + 
						"</head>\r\n" + 
						"<body>\r\n" + 
						"	<div id=wrapper>\r\n" + 
						"		<h1>DISPLAY SCREEN</h1>\r\n" + 
						"		<p>\r\n" + 
						"		<h2>TRANSACTIONS WITHIN THE GIVEN DATES</h2>\r\n" + 
						"		</p>\r\n" + 
						"		<table style=\"width:100%\" text-color: white>\r\n" + 
						"  <tr>\r\n" + 
						"    <th>Sl </th>\r\n" + 
						"    <th>Date</th>\r\n" + 
						"     <th>Withdraw </th>\r\n" + 
						"      <th>Deposit</th>\r\n" + 
						"       <th>Account_ballance</th>\r\n" + 
						"  </tr>\r\n" + 
						"  ");
				while(rs.next())
				{
					out.println("<tr>\r\n" + 
							"    <td>"+rs.getString(1)+"</td>\r\n" + 
							"    <td>"+rs.getString(2)+"</td>\r\n" + 
							"    <td>"+rs.getString(3)+"</td>\r\n" + 
							"    <td>"+rs.getString(4)+"</td>\r\n" + 
							"    <td>"+rs.getString(5)+"</td>\r\n" + 
							"  </tr>");
				}
				
				
				out.println("  </table>\r\n" + 
						"	</div>\r\n" + 
						"</body>\r\n" + 
						"</html>");
				
				rd.include(request, response);
				
			}
			else
			{
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println(
						"<p style=font-size:30px;color:darkblue;text-align:center>Result set empty correct</p>");
				RequestDispatcher rd = request.getRequestDispatcher("Display1.html");
				rd.include(request, response);
			}
			
			
		} 
		
		catch (Exception ex) {
			System.out.println("Error:" + ex.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}
