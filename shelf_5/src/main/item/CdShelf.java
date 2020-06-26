package main.item;

import main.form.ConnectMySQL;

public class CdShelf extends Shelf {

	public static final String SONG_CAPTION = "曲名";
	public static final String SINGER_CAPTION = "歌手";
	public static final String NAME = "CD";

	public boolean add(Cd cd) {
		return addData("INSERT INTO cd (song,singer) VALUES ('" + cd.title + "','" + cd.person + "');");
	}

	public boolean deleteAll() {
		String sql1 = "DELETE FROM cd;";
		String sql2 = "TRUNCATE TABLE cd;";
		return ConnectMySQL.deleteAllData(sql1,sql2);
	}

	@Override
	public boolean deleteOne(int i) {
		ConnectMySQL.deleteData(i, "cd");
		return true;
	}

	@Override
	public Production get(int index) {
		products = ConnectMySQL.getData("cd", index);
		return products[index];
	}

	@Override
	public int getCount() {
		return ConnectMySQL.countData("cd", "normal", "", "@@@");
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
