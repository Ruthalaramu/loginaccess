package com.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		 String uemail=request.getParameter("username");
		 String upwd=request.getParameter("password");
		 HttpSession session =request.getSession();
		 RequestDispatcher dispacther=null;
		 Connection con=null;
		 if(uemail==null|| uemail.equals(""))
		 {
			 request.setAttribute("status", "invalidemail");
			 dispacther=request.getRequestDispatcher("login.jsp");
			 dispacther.forward(request, response);
		 }
		 
		 if(upwd==null|| upwd.equals(""))
		 {
			 request.setAttribute("status", "invalidpassword");
			 dispacther=request.getRequestDispatcher("login.jsp");
			 dispacther.forward(request, response);
		 }
		 
		 
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				 con =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ammulu","root","ramu");
				PreparedStatement pst=con.prepareStatement("select * from signup_table1 where uemail=? and upwd=?");
				pst.setString(1, uemail);
				pst.setString(2, upwd);
				ResultSet rs=pst.executeQuery();
				if(rs.next())
				{
					session.setAttribute("name", rs.getString("uemail"));
					 dispacther=request.getRequestDispatcher("index.jsp");
					 
				}
				else{
					request.setAttribute("status", "failed");
					 dispacther=request.getRequestDispatcher("login.jsp");
				}
				 dispacther.forward(request, response);
				
	}catch(Exception e){
		e.printStackTrace();
	}
}
}
