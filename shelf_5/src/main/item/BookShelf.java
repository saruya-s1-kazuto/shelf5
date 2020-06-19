package main.item;

import main.form.ConnectMySQL;

public class BookShelf extends Shelf {

	public static final String TITLE_CAPTION = "題名";
	public static final String PERSON_CAPTION = "作者";
	public static final String NAME = "本";

	public boolean add(Book book) {
		return addData(book);
	}

	public boolean deleteAll() {
		deleteAllSQL(0);
		return true;
	}

	@Override
	public boolean deleteOne(int index) {
		ConnectMySQL.deleteData(index, 0);
		return true;
	}

	@Override
	public Production get(int index) {
		return ConnectMySQL.getData(0, index);
	}

	@Override
	public int getCount() {
		return ConnectMySQL.countData(0, "normal", "", "@@@");
	}

	@Override
	public Production searchTitle(int id, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchTitle(0, terms, choice, index, text);
	}

	@Override
	public Production searchPerson(int id, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchPerson(0, terms, choice, index, text);
	}
}
