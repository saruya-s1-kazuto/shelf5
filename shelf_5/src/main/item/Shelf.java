package main.item;

import main.form.ConnectMySQL;

public class Shelf {
	protected Production[] products;

	public boolean addData(String sql) {
		ConnectMySQL.insertData(sql);
		return true;
	}

	public boolean deleteAll() {
		String sql1 = "@@@";
		String sql2 = "@@@";
		return ConnectMySQL.deleteAllData(sql1,sql2);
	}

	public boolean deleteOne(int index) {
		ConnectMySQL.deleteData(index, "@@@");
		return true;

	}

	public Production get(int index) {
		ConnectMySQL.getData("@@@", 100);
		return products[index];
	}

	public int getCount() {
		return ConnectMySQL.countData("@@@", "@@?", "@_@;", "@ @!");
	}

	public Production searchTitle(String production, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchTitle("@@@", "@@@", "@@@", 100, "@@@");
	}

	public Production searchPerson(String production, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchTitle("@@@", "@@@", "@@@", 100, "@@@");
	}
}
