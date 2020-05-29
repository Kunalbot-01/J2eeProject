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

@WebServlet("/Testing")
public class Testing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static PreparedStatement pst3 = null;
	private static PreparedStatement pst4 = null;

	public void init(ServletConfig config) throws ServletException {
		System.out.println("first");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/banking", "root", "password");
			System.out.println("second");

			
			pst4 = con.prepareStatement("select * from user_pass;");
			System.out.println("third");
			try {
			
				
				ResultSet rsx = pst4.executeQuery(); //select * from user_pass

				System.out.println("fourth");
				
				while(rsx.next())
				{
					System.out.println(rsx);
					String jcb=rsx.getString(2);
					System.out.println(jcb);
					
				}
				System.out.println("outside the loop");
				
				
				
				
					
			} catch (Exception e) {
				System.out.println("Error:" + e.toString());
			}

		} catch (Exception ex) {
			System.out.println("Error:" + ex.toString());
		}
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
	}
 
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
		doGet(request, response);
	}

}