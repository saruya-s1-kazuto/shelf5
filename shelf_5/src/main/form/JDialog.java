package main.form;


import java.awt.Frame;

import javax.swing.JOptionPane;

public class JDialog extends Frame {
	private String title;
	private String person;
	public void showDialog() {
		title = JOptionPane.showInputDialog("タイトル", "");
		person = JOptionPane.showInputDialog("人物", "");
		if (title == null) {
			title = "";
		}
		if (person == null) {
			person = "";
		}
	}
	
	public String getTitle() {
		return title;
	}
	public String getPerson() {
		return person;
	}
	
	public void updateData(int id,String title,String person,int index) {
		ConnectMySQL.updateData(id, title, person, index);
	}
}