package main.item;

import main.form.ConnectMySQL;

public class Shelf {

	public boolean addData(Book book) {
		ConnectMySQL.insertData(book.title, book.person, 0);
		return true;
	}

	public boolean addData(Cd cd) {
		ConnectMySQL.insertData(cd.title, cd.person, 1);
		return true;
	}

	public boolean deleteAllSQL(int id) {
		ConnectMySQL.deleteAllData(id);
		return true;
	}

	public boolean deleteOne(int index) {
		ConnectMySQL.deleteData(index, index);
		return true;

	}

	public Production get(int index) {
		return ConnectMySQL.getData(100, 100);
	}

	public int getCount() {
		return ConnectMySQL.countData(100, "@@@", "@@@", "@@@");
	}

	public Production searchTitle(int id, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchTitle(100, "@@@", "@@@", 100, "@@@");
	}

	public Production searchPerson(int id, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchTitle(100, "@@@", "@@@", 100, "@@@");
	}
}
