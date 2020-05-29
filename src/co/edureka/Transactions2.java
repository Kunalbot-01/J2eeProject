package co.edureka;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Transactions")
public class Transactions2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static PreparedStatement pst1 = null;
	private static PreparedStatement pst2 = null;
	private static PreparedStatement pst3 = null;
	private static PreparedStatement pst4 = null;
	private static PreparedStatement pst5 = null;
	String Ballance1;
	
	private static PreparedStatement psta = null;
	private static PreparedStatement pstb = null;

	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/banking", "root", "password");

			pst1 = con.prepareStatement("select * from available_accounts where accno=?;");
			pst2 = con.prepareStatement("update available_accounts set amt=? where accno =?;");
			pst4 = con.prepareStatement("select * from user_pass;");
			pst5 = con.prepareStatement("update user_pass set Login_Ballance=?;");
			
			psta = con.prepareStatement("insert into display (Date, Deposit, Account_ballance) values(curdate(),?,?);");
			pstb = con.prepareStatement("insert into display (Date, Withdraw, Account_ballance) values(curdate(),?,?);");

		} catch (Exception ex) {
			System.out.println("Error:" + ex.toString());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String Account_no = request.getParameter("accno");
		String am = request.getParameter("accamt");		
		String str1 = request.getParameter("a1"); 
		String str2 = request.getParameter("a2");
		
		int Amount = Integer.parseInt(am);

		try {
			pst1.setString(1, Account_no);
			ResultSet rs = pst1.executeQuery();
			
			ResultSet rs11 = pst4.executeQuery(); //select * from user_pass
			if(rs11.next())
			{
			  Ballance1=rs11.getString(3);//////////HERE IS THE MAIN CHAP to get value from result set we have to pass it in loop if(rs11.next())
			}
			
			int Ballance=Integer.parseInt(Ballance1);
			
			if (rs.next()) 
			{
				String a = rs.getString(2);
				int b = Integer.parseInt(a);// amount present in available_accounts
											
				 if(str1 != null) 
				{
					 if (b > Amount) {
							int c = b - Amount;
							int d = Ballance + Amount;

							pst2.setInt(1, c);
							pst2.setString(2, Account_no);
							
							pst5.setInt(1, d);//Prepared statement to update user_pass table
							//psta = con.prepareStatement("insert into display (Date, Deposit, Account_ballance) values(curdate(),?,?);");
							psta.setInt(1, Amount);
							psta.setInt(2, d);
							
							int rows_affected= pst2.executeUpdate();
							int rows_affected1 = pst5.executeUpdate();
							int rows_affected2 = psta.executeUpdate();
							
							if ((rows_affected == 1) && (rows_affected1==1) &&(rows_affected2==1)) {
								PrintWriter out = response.getWriter();
								response.setContentType("text/html");
								out.println("<p style=font-size:30px;color:darkblue;text-align:center>ACCOUNT DEBITED SUCCESSFULLY AND ADDED TO THE LOGGED IN ACCOUNT</p>");
								out.println(Ballance1);
								
								RequestDispatcher rd = request.getRequestDispatcher("Transactions.html");
								rd.include(request, response);
							} else {
								PrintWriter out = response.getWriter();
								response.setContentType("text/html");
								out.println("<p style=font-size:30px;color:red;text-align:center>not done</p>");
								RequestDispatcher rd = request.getRequestDispatcher("Transactions.html");
								rd.include(request, response);
							}

										} 
						else {
							PrintWriter out = response.getWriter();
							response.setContentType("text/html");
							out.println("<p style=font-size:30px;color:red;text-align:center>INSUFFICIENT AMOUNTS</p>");
							RequestDispatcher rd = request.getRequestDispatcher("Transactions.html");
							rd.include(request, response);
						}


				}
				 				 
				 else if(str2 != null)
				 {
					 	System.out.println(Ballance);
					 	System.out.println(Amount);
					 if (Ballance > Amount) {//Amount is the Money entered in html Ballance is the money in logged in account
							int c = b + Amount;
							int d = Ballance - Amount;

							pst2.setInt(1, c);
							pst2.setString(2, Account_no);
							
							pst5.setInt(1, d);//Prepared statement to update user_pass table
							pstb.setInt(1, Amount);
							pstb.setInt(2, d);

							int rows_affected= pst2.executeUpdate();
							int rows_affected1 = pst5.executeUpdate();
							int rows_affected2 = pstb.executeUpdate();
							
							if ((rows_affected == 1) && (rows_affected1==1) && (rows_affected2 == 1)) {
								PrintWriter out = response.getWriter();
								response.setContentType("text/html");
								out.println("<p style=font-size:30px;color:darkblue;text-align:center>ACCOUNT CREDITED SUCCESSFULLY AFTER DEDUCTING FROM THE LOGGED IN ACCOUNT</p>");
								out.println(Ballance1);
								
								RequestDispatcher rd = request.getRequestDispatcher("Transactions.html");
								rd.include(request, response);
							} else {
								PrintWriter out = response.getWriter();
								response.setContentType("text/html");
								out.println("<p style=font-size:30px;color:red;text-align:center>not done some problem occured</p>");
								RequestDispatcher rd = request.getRequestDispatcher("Transactions.html");
								rd.include(request, response);
							}

										} 
						else {
							PrintWriter out = response.getWriter();
							response.setContentType("text/html");
							out.println("<p style=font-size:30px;color:red;text-align:center>INSUFFICIENT AMOUNTS IN MAIN LOGGED IN ACCOUNT</p>");
							RequestDispatcher rd = request.getRequestDispatcher("Transactions.html");
							rd.include(request, response);
						}
				 }
				 

				
			}
			else {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println("<p style=font-size:30px;color:red;text-align:center>INVALID ACCOUNT</p>");
				RequestDispatcher rd = request.getRequestDispatcher("Transactions.html");
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
