package com.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.bean.User;
import com.user.helper.ConnectionFactory;

public class UserDao implements IUserDAO {

	// singleton design pattern
	private static UserDao instance = null;

	// default constructor
	public UserDao() {
		// TODO Auto-generated constructor stub
	}

	// get instance method
	public static UserDao getInstance() {

		if (instance == null) {
			instance = new UserDao();
		}
		return instance;
	}

	// Query
	private static final String INSERT_USERS_SQL = "INSERT INTO tblUser(name,email,country) values(?,?,?)";
	private static final String SELECT_USER_BY_ID = "select id,name,email,country from tblUser where id =?";
	private static final String SELECT_ALL_USERS = "select * from tblUser";
	private static final String DELETE_USERS_SQL = "delete from tblUser where id = ?";
	private static final String UPDATE_USERS_SQL = "update tblUser set name = ?,email= ?, country =? where id = ?";
	// creating objects:connection,preparedstatemnt,resultset
	Connection conn = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	// insertUSer method:
	public void insertUser(User objuser) throws Exception {
		// int result = -1;

		System.out.println(INSERT_USERS_SQL);
		// connection
		conn = ConnectionFactory.getConnection();
		// statement query
		statement = conn.prepareStatement(INSERT_USERS_SQL);
		statement.setString(1, objuser.getName());
		statement.setString(2, objuser.getEmail());
		statement.setString(3, objuser.getCountry());

		// execute query
		statement.executeUpdate();

		// closing connection,statement:
		statement.close();
		conn.close();

		// return result;
	}

	// select User by ID method:

	public User selectUser(int id) throws Exception {

		User objuser = null;
		conn = ConnectionFactory.getConnection();
		statement = conn.prepareStatement(SELECT_USER_BY_ID);
		statement.setInt(1, id);
	resultSet = statement.executeQuery();

		while (resultSet.next()) {
			int idNo = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			String country = resultSet.getString("country");
			objuser = new User(id, name, email, country);
		}
		return objuser;
	}

	// Select(list) all users method:
	public List<User> selectAllUsers() throws Exception {

		List<User> objlist = new ArrayList<User>();
		conn = ConnectionFactory.getConnection();
		statement = conn.prepareStatement(SELECT_ALL_USERS);
		System.out.println(statement);

		resultSet = statement.executeQuery();

		// Step 4: Process the ResultSet object.
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			String country = resultSet.getString("country");
			objlist.add(new User(id, name, email, country));
		}

		return objlist;
	}

	// Delete

	public boolean deleteUser(int id) throws Exception {
		boolean rowDeleted;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement statement = connection
				.prepareStatement(DELETE_USERS_SQL);
		statement.setInt(1, id);
		rowDeleted = statement.executeUpdate() > 0;

		return rowDeleted;
	}

	// Update

	public boolean updateUser(User user) throws Exception {
		boolean rowUpdated;

		// connect
		Connection conn = ConnectionFactory.getConnection();
		// preparedstatement
		PreparedStatement statement = conn.prepareStatement(UPDATE_USERS_SQL);
		statement.setString(1, user.getName());
		statement.setString(2, user.getEmail());
		statement.setString(3, user.getCountry());
		statement.setInt(4, user.getId());

		rowUpdated = statement.executeUpdate() > 0;

		return rowUpdated;

	}
}
