package main.item;

import main.form.ConnectMySQL;

public class BookShelf extends Shelf {

	public static final String TITLE_CAPTION = "題名";
	public static final String PERSON_CAPTION = "作者";
	public static final String NAME = "本";

	public boolean add(Book book) {
		return addData("INSERT INTO book (title,author) VALUES ('" + book.title + "','" + book.person + "');");
	}
	
	public boolean deleteAll() {
		String sql1 = "DELETE FROM book;";
		String sql2 = "TRUNCATE TABLE book;";
		return ConnectMySQL.deleteAllData(sql1,sql2);
	}

	@Override
	public boolean deleteOne(int index) {
		ConnectMySQL.deleteData(index, "book");
		return true;
	}

	@Override
	public Production get(int index) {
		products = ConnectMySQL.getData("book", index);
		return products[index];
	}

	@Override
	public int getCount() {
		return ConnectMySQL.countData("book", "normal", "", "@@@");
	}

	@Override
	public Production searchTitle(String production, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchTitle(production, terms, choice, index, text);
	}

	@Override
	public Production searchPerson(String production, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchPerson(production, terms, choice, index, text);
	}
}
