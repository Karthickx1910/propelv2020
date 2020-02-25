package com.user.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.security.jca.GetInstance;

import com.user.bean.User;
import com.user.dao.IUserDAO;
import com.user.dao.UserDao;

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserDao objUserDao;

	public void init() {
		objUserDao = objUserDao.getInstance();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getServletPath();
		
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				System.out.println("new");
				break;
			case "/insert":
				insertUserNew(request, response);
				System.out.println("insert");
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showNewForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request
				.getRequestDispatcher("JSP/user-form.jsp");
		rd.forward(request, response);
	}

	private void insertUserNew(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");

		User newUser = new User(name, email, country);

		objUserDao.insertUser(newUser);

		response.sendRedirect("list");

	}

	private void listUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<User> listUser = objUserDao.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("JSP/userlist.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("id"));

		User existingUser = objUserDao.selectUser(id);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("JSP/user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);

	}

	private void updateUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");

		User objUser = new User(id, name, email, country);
		objUserDao.updateUser(objUser);
		response.sendRedirect("list");
	}

	private void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		objUserDao.deleteUser(id);
		response.sendRedirect("list");

	}

}
