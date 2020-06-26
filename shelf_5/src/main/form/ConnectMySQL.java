package main.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.item.Book;
import main.item.Cd;
import main.item.Production;

public class ConnectMySQL {
	static final String URL = "jdbc:mysql://localhost:3306/shelf?useSSL=false&characterEncoding=UTF-8&serverTimezone=JST";
	static final String USERNAME = "root";
	static final String PASSWORD = "root";//tech2020

	protected static String title;
	protected static String person;

	public static boolean insertData(String sql) {
		boolean connect_ok = false;
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();

			connection.close();
			connect_ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect_ok;
	}

	public static Production[] getData(String production, int index) {
		Production[] getProduct = new Production[countData(production, "normal", "", "@")];
		int i = 0;
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM " + production + ";";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (production == "book") {
				while (rs.next()) {
					Book book = new Book();
					title = rs.getString("title");
					person = rs.getString("author");
					book.setTitle(title);
					book.setPerson(person);
					getProduct[i] = book;
					i++;
				}
			} else {
				while (rs.next()) {
					Cd cd = new Cd();
					title = rs.getString("song");
					person = rs.getString("singer");
					cd.setTitle(title);
					cd.setPerson(person);
					getProduct[i] = cd;
					i++;
				}
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getProduct;
	}

	public static boolean deleteData(int index, String production) {
		boolean connect_ok = false;
		int i = 0;
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			Statement statement = connection.createStatement();
			String sql = "select id from " + production + " limit 1 offset " + index + ";";
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				String count = rs.getString("id");
				i = Integer.parseInt(count);
			}
			sql = "DELETE FROM " + production + " where id = " + i + ";";
			statement.executeUpdate(sql);
			statement.close();
			connection.close();
			connect_ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect_ok;
	}

	public static boolean deleteAllData(String sql1,String sql2) {
		boolean connect_ok = false;
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql1);
			statement.executeUpdate(sql2);
			statement.close();
			connection.close();
			connect_ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect_ok;

	}

	public static int countData(String production, String terms, String choice, String text) {
		int data_size = 0;
		String sql = "";
		if (production == "book") {
			if (terms == "normal") {//全部count
				sql = "select count(*) as CNT from book;";
			} else if (terms == "all") {//全文
				if (choice == "title") {//title
					sql = "select count(*) as CNT from book where title = '" + text + "';";
				} else {//author				
					sql = "select count(*) as CNT from book where author = '" + text + "';";
				}
			} else {//部分
				if (choice == "title") {//title					
					sql = "select count(*) as CNT from book where title like '%" + text + "%';";
				} else {//author
					sql = "select count(*) as CNT from book where author like '%" + text + "%';";
				}
			}
		} else if (production == "cd") {//cd
			if (terms == "normal") {//普通
				sql = "select count(*) as CNT from cd;";
			} else if (terms == "all") {//全文
				if (choice == "title") {//song
					sql = "select count(*) as CNT from cd where song = '" + text + "';";
				} else {//singer
					sql = "select count(*) as CNT from cd where singer = '" + text + "';";
				}
			} else {//部分
				if (choice == "title") {//title
					sql = "select count(*) as CNT from cd where song like '%" + text + "%';";
				} else {//author
					sql = "select count(*) as CNT from cd where singer like '%" + text + "%';";
				}
			}
		}
		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				String count = rs.getString("CNT"); //個数取得
				data_size = Integer.parseInt(count);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data_size;
	}

	public static Production searchTitle(String production, String terms, String choice, int index, String text) {
		//(book or cd,全文or部分,タイトルor人物,行,検索内容)
		Production[] getProduct = new Production[countData(production, terms, choice, text)];
		int i = 0;
		String sql = "";
		if (production == "book") {
			if (terms == "all") {
				sql = "SELECT * FROM book where title = " + "'" + text + "'" + ";";
			} else {
				sql = "SELECT * FROM book where title like '%" + text + "%';";
			}
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Book book = new Book();
					title = rs.getString("title");
					person = rs.getString("author");
					book.setTitle(title);
					book.setPerson(person);
					getProduct[i] = book;
					i++;
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (production == "cd") {
			if (terms == "all") {
				sql = "SELECT * FROM cd where song = " + "'" + text + "'" + ";";
			} else {
				sql = "SELECT * FROM cd where song like '%" + text + "%';";
			}
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Cd cd = new Cd();
					title = rs.getString("song");
					person = rs.getString("singer");
					cd.setTitle(title);
					cd.setPerson(person);
					getProduct[i] = cd;
					i++;
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return getProduct[index];
	}

	public static Production searchPerson(String production, String terms, String choice, int index, String text) {
		Production[] getProduct = new Production[countData(production, terms, choice, text)];
		int i = 0;
		String sql = "";
		if (production == "book") {
			if (terms == "all") {
				sql = "SELECT * FROM book where author = " + "'" + text + "'" + ";";
			} else {
				sql = "SELECT * FROM book where author like '%" + text + "%';";
			}
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Book book = new Book();
					title = rs.getString("title");
					person = rs.getString("author");
					book.setTitle(title);
					book.setPerson(person);
					getProduct[i] = book;
					i++;
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (production == "cd") {
			if (terms == "all") {
				sql = "SELECT * FROM cd where singer = " + "'" + text + "'" + ";";
			} else {
				sql = "SELECT * FROM cd where singer like '%" + text + "%';";
			}
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();

				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Cd cd = new Cd();
					title = rs.getString("song");
					person = rs.getString("singer");
					cd.setTitle(title);
					cd.setPerson(person);
					getProduct[i] = cd;
					i++;
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return getProduct[index];
	}

	public static boolean updateData(String production, String title, String person, int index) {
		boolean connect_ok = false;
		int i = 0;

		try {
			Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();
			String sql = "select id from " + production + " limit 1 offset " + index + ";";
			ResultSet rs = statement.executeQuery(sql);
			if (production == "book") {
				if (rs.next()) {
					String count = rs.getString("id");
					i = Integer.parseInt(count);
				}
				if (title.equals("") == false) {
					sql = "update book set title = '" + title + "' where id = " + i + ";";
					statement.executeUpdate(sql);
				}
				if (person.equals("") == false) {
					sql = "update book set author = '" + person + "' where id = " + i + ";";
					statement.executeUpdate(sql);
				}
			} else {
				if (rs.next()) {
					String count = rs.getString("id");
					i = Integer.parseInt(count);
				}
				if (title.equals("") == false) {
					sql = "update cd set song = '" + title + "' where id = " + i + ";";
					statement.executeUpdate(sql);
				}
				if (person.equals("") == false) {
					sql = "update cd set singer = '" + person + "' where id = " + i + ";";
					statement.executeUpdate(sql);
				}
			}
			statement.close();
			connection.close();
			connect_ok = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connect_ok;

	}
}
