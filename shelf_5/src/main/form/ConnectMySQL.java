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

	public static boolean insertData(String title, String person, int id) {
		boolean connect_ok = false;
		if (id == 0) {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "INSERT INTO book (title,author) VALUES ('" + title + "','" + person + "');";
				statement.executeUpdate(sql);
				statement.close();

				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connect_ok;
		} else if (id == 1) {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "INSERT INTO cd (song,singer) VALUES ('" + title + "','" + person + "');";
				statement.executeUpdate(sql);
				statement.close();

				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connect_ok;
		}
		return connect_ok;
	}

	public static Production getData(int id, int index) {
		if (id == 0) {
			Production[] getbook = new Production[countData(0, "normal", "", "@")];

			int i = 0;
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM book;";
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Book book = new Book();
					title = rs.getString("title");
					person = rs.getString("author");
					book.setTitle(title);
					book.setPerson(person);
					getbook[i] = book;
					i++;
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return getbook[index];
		} else if (id == 1) {
			Production[] getcd = new Production[countData(1, "normal", "", "@")];
			int i = 0;
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM cd;";
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Cd cd = new Cd();
					title = rs.getString("song");
					person = rs.getString("singer");
					cd.setTitle(title);
					cd.setPerson(person);
					getcd[i] = cd;
					i++;
				}
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return getcd[index];
		} else {
			return null;
		}
	}

	public static boolean deleteData(int index, int id) {//id=tabPane
		boolean connect_ok = false;
		int i = 0;
		if (id == 0) {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "select id from book limit 1 offset " + index + ";";
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) {
					String count = rs.getString("id");
					i = Integer.parseInt(count);
				}
				sql = "DELETE FROM book where id = " + i + ";";
				statement.executeUpdate(sql);
				statement.close();
				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (id == 1) {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "select id from cd limit 1 offset " + index + ";";
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) {
					String count = rs.getString("id");
					i = Integer.parseInt(count);
				}
				sql = "DELETE FROM cd where id = " + i + ";";
				statement.executeUpdate(sql);
				statement.close();
				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connect_ok;
	}

	public static boolean deleteAllData(int id) {
		boolean connect_ok = false;
		if (id == 0) {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "DELETE FROM book;";
				statement.executeUpdate(sql);
				sql = "TRUNCATE TABLE book;";
				statement.executeUpdate(sql);
				statement.close();

				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connect_ok;
		} else if (id == 1) {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "DELETE FROM cd;";
				statement.executeUpdate(sql);
				sql = "TRUNCATE TABLE cd;";
				statement.executeUpdate(sql);
				statement.close();

				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connect_ok;
		} else {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "DELETE FROM book;";
				statement.executeUpdate(sql);
				sql = "TRUNCATE TABLE book;";
				statement.executeUpdate(sql);
				sql = "DELETE FROM cd;";
				statement.executeUpdate(sql);
				sql = "TRUNCATE TABLE cd;";
				statement.executeUpdate(sql);
				statement.close();

				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return connect_ok;
		}
	}

	public static int countData(int id, String terms, String choice, String text) {
		int data_size = 0;
		if (id == 0) {
			if (terms == "normal") {//全部count
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

					Statement statement = connection.createStatement();
					String sql = "select count(*) as CNT from book;";
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
			} else if (terms == "all") {//全文
				if (choice == "title") {//title
					try {
						Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

						Statement statement = connection.createStatement();
						String sql = "select count(*) as CNT from book where title = '" + text + "';";
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
				} else {//author
					try {
						Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

						Statement statement = connection.createStatement();
						String sql = "select count(*) as CNT from book where author = '" + text + "';";
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
			} else {//部分
				if (choice == "title") {//title
					try {
						Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

						Statement statement = connection.createStatement();
						String sql = "select count(*) as CNT from book where title like '%" + text + "%';";
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
				} else {//author
					try {
						Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

						Statement statement = connection.createStatement();
						String sql = "select count(*) as CNT from book where author like '%" + text + "%';";
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
			}
		} else if (id == 1) {//cd
			if (terms == "normal") {//普通
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

					Statement statement = connection.createStatement();
					String sql = "select count(*) as CNT from cd;";
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
			} else if (terms == "all") {//全文
				if (choice == "title") {//song
					try {
						Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

						Statement statement = connection.createStatement();
						String sql = "select count(*) as CNT from cd where song = '" + text + "';";
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
				} else {//singer
					try {
						Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

						Statement statement = connection.createStatement();
						String sql = "select count(*) as CNT from cd where singer = '" + text + "';";
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
			} else {//部分
				if (choice == "title") {//title
					try {
						Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

						Statement statement = connection.createStatement();
						String sql = "select count(*) as CNT from cd where song like '%" + text + "%';";
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
				} else {//author
					try {
						Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

						Statement statement = connection.createStatement();
						String sql = "select count(*) as CNT from cd where singer like '%" + text + "%';";
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
			}
		} else {
			return data_size;
		}
	}

	public static Production searchTitle(int id, String terms, String choice, int index, String text) {
		//(book or cd,全文or部分,タイトルor人物,行,検索内容)
		if (id == 0) {
			if (terms == "all") {//全文一致
				Production[] getbook = new Production[countData(0, terms, choice, text)];
				int i = 0;
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM book where title = " + "'" + text + "'" + ";";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Book book = new Book();
						title = rs.getString("title");
						person = rs.getString("author");
						book.setTitle(title);
						book.setPerson(person);
						getbook[i] = book;
						i++;
					}
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return getbook[index];
			} else if (terms == "parts") {//部分一致
				Production[] getbook = new Production[countData(0, terms, choice, text)];
				int i = 0;
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM book where title like '%" + text + "%';";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Book book = new Book();
						title = rs.getString("title");
						person = rs.getString("author");
						book.setTitle(title);
						book.setPerson(person);
						getbook[i] = book;
						i++;
					}
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return getbook[index];
			} else {
				return null;
			}
		} else if (id == 1) {
			if (terms == "all") {
				Production[] getcd = new Production[countData(1, terms, choice, text)];
				int i = 0;
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM cd where song = " + "'" + text + "'" + ";";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Cd cd = new Cd();
						title = rs.getString("song");
						person = rs.getString("singer");
						cd.setTitle(title);
						cd.setPerson(person);
						getcd[i] = cd;
						i++;
					}
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return getcd[index];
			} else if (terms == "parts") {
				Production[] getcd = new Production[countData(1, terms, choice, text)];
				int i = 0;
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM cd where song like '%" + text + "%';";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Cd cd = new Cd();
						title = rs.getString("song");
						person = rs.getString("singer");
						cd.setTitle(title);
						cd.setPerson(person);
						getcd[i] = cd;
						i++;
					}
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return getcd[index];
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static Production searchPerson(int id, String terms, String choice, int index, String text) {
		if (id == 0) {
			if (terms == "all") {
				Production[] getbook = new Production[countData(0, terms, choice, text)];
				int i = 0;
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM book where author = " + "'" + text + "'" + ";";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Book book = new Book();
						title = rs.getString("title");
						person = rs.getString("author");
						book.setTitle(title);
						book.setPerson(person);
						getbook[i] = book;
						i++;
					}
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return getbook[index];
			} else {
				Production[] getbook = new Production[countData(0, terms, choice, text)];
				int i = 0;
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM book where author like '%" + text + "%';";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Book book = new Book();
						title = rs.getString("title");
						person = rs.getString("author");
						book.setTitle(title);
						book.setPerson(person);
						getbook[i] = book;
						i++;
					}
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return getbook[index];
			}
		} else if (id == 1) {
			if (terms == "all") {
				Production[] getcd = new Production[countData(1, terms, choice, text)];
				int i = 0;
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM cd where singer = " + "'" + text + "'" + ";";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Cd cd = new Cd();
						title = rs.getString("song");
						person = rs.getString("singer");
						cd.setTitle(title);
						cd.setPerson(person);
						getcd[i] = cd;
						i++;
					}
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return getcd[index];
			} else {
				Production[] getcd = new Production[countData(1, terms, choice, text)];
				int i = 0;
				try {
					Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM cd where singer like '%" + text + "%';";
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						Cd cd = new Cd();
						title = rs.getString("song");
						person = rs.getString("singer");
						cd.setTitle(title);
						cd.setPerson(person);
						getcd[i] = cd;
						i++;
					}
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return getcd[index];
			}
		} else {
			return null;
		}
	}

	public static boolean updateData(int id, String title, String person, int index) {
		boolean connect_ok = false;
		int i = 0;
		if (id == 0) {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "select id from book limit 1 offset " + index + ";";
				ResultSet rs = statement.executeQuery(sql);
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
				statement.close();
				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (id == 1) {
			try {
				Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

				Statement statement = connection.createStatement();
				String sql = "select id from cd limit 1 offset " + index + ";";
				ResultSet rs = statement.executeQuery(sql);
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
				statement.close();
				connection.close();
				connect_ok = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connect_ok;

	}
}
