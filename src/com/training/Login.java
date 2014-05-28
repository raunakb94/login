package com.training;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Login() {
        super();
    }

	/**
	 * Servlet Init Configuration
	 */
	public void init(ServletConfig config) throws ServletException {
		
	}

	/**
	 * Get Post Handles Username and Password and Calls a Validate Function
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try{
			String name = request.getParameter("Username");
		String passKey = request.getParameter("Password");
		boolean test =validateUser(name,passKey);
		if(test == true){
			out.println("<h1>Welcome </h1>"+name);
		}
		else{
			out.println("Login Failure");
		}
		}
		finally {
			out.close();
		}
	}
	/*
	 * validateUser validates user on creating connection with a Database Specified
	 * in the "connect" class
	 */
	protected boolean validateUser(String userName,String passKey)
	{
		connect userAuthenticationConnection = new connect();
		ResultSet rs = null ;
		boolean test = userAuthenticationConnection.doConnection();
		if(test == true)
		{
			Connection con = userAuthenticationConnection.getConnect();
			PreparedStatement stmt= null;
			String sql= "select password from users where user_name = ?";
			try {
				
				stmt = con.prepareStatement(sql);
				stmt.setString(1,userName);
				rs = stmt.executeQuery();
				if(!rs.first())
				{
					return false;
				}
			}
			
			catch (SQLException e1) {
				e1.printStackTrace();
			}			
			String passWord=null;
			try {
				
					passWord = rs.getString("password");				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(passWord.equals(passKey))
			{
				userAuthenticationConnection.closeConnection();
				return true;
			}
		}
		return false;
		}
		
	

}
