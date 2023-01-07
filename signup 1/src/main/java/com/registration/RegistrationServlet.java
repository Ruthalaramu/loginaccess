package com.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String  uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String repwd=request.getParameter("re_pass");
		String umobile=request.getParameter("contact");
		RequestDispatcher dispacther=null;
		Connection con=null;
		if(uname==null||uname.equals(""))
		{
			request.setAttribute("status", "invaliduser");
			 dispacther=request.getRequestDispatcher("registration.jsp");
			 dispacther.forward(request, response);
		}
		if(uemail==null||uemail.equals(""))
		{
			request.setAttribute("status", "invaliemail");
			 dispacther=request.getRequestDispatcher("registration.jsp");
			 dispacther.forward(request, response);
		}
		if(upwd==null||upwd.equals(""))
		{
			request.setAttribute("status", "invalidpwd");
			 dispacther=request.getRequestDispatcher("registration.jsp");
			 dispacther.forward(request, response);
			 
			}
		else if(!upwd.equals(repwd))
			{
				
			 request.setAttribute("status", "invalidre_pass");
			 dispacther=request.getRequestDispatcher("registration.jsp");
          	 dispacther.forward(request, response);
			}
	
			
		if(umobile==null||umobile.equals(""))
		{
			request.setAttribute("status", "invalidmobile");
			 dispacther=request.getRequestDispatcher("registration.jsp");
			 dispacther.forward(request, response);
		}
		else if(umobile.length()>10)
		{
			request.setAttribute("status", "invalidlength");
			 dispacther=request.getRequestDispatcher("registration.jsp");
			 dispacther.forward(request, response);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			 con =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ammulu?useSSL=false","root","ramu");
			PreparedStatement pst=con.prepareStatement("insert into signup_table1(uname,uemail,upwd,umobile) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, uemail);
			pst.setString(3, upwd);
			pst.setString(4, umobile);
			 int rowCount=pst.executeUpdate();
			 dispacther=request.getRequestDispatcher("registration.jsp");
			 if(rowCount >0)
			 {
				request.setAttribute("status", "success");
			
				
			 }
			 else
			 {
				 request.setAttribute("status", "failed");
			 }
			 dispacther.forward(request, response);	 	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
	

