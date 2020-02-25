package com.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.user.bean.User;

public interface IUserDAO {

	public abstract void insertUser(User objuser) throws SQLException, Exception;

	// select User by ID method:
	public abstract User selectUser(int id) throws Exception;

	public abstract List<User> selectAllUsers() throws SQLException, Exception;

	public abstract boolean deleteUser(int id) throws Exception;

	public abstract boolean updateUser(User user) throws Exception;

}