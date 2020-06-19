package main.item;

import main.form.ConnectMySQL;

public class CdShelf extends Shelf {

	public static final String SONG_CAPTION = "曲名";
	public static final String SINGER_CAPTION = "歌手";
	public static final String NAME = "CD";

	public boolean add(Cd cd) {
		return addData(cd);
	}

	public boolean deleteAll() {
		deleteAllSQL(1);
		return true;
	}

	@Override
	public boolean deleteOne(int i) {
		ConnectMySQL.deleteData(i, 1);
		return true;
	}

	@Override
	public Production get(int index) {
		return ConnectMySQL.getData(1, index);
	}

	@Override
	public int getCount() {
		return ConnectMySQL.countData(1, "normal", "", "@@@");
	}

	@Override
	public Production searchTitle(int id, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchTitle(1, terms, choice, index, text);
	}

	@Override
	public Production searchPerson(int id, String terms, String choice, int index, String text) {
		return ConnectMySQL.searchPerson(1, terms, choice, index, text);
	}
}
