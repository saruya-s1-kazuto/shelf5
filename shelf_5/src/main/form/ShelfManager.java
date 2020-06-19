package main.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.form.Common.Mode;
import main.item.Book;
import main.item.BookShelf;
import main.item.Cd;
import main.item.CdShelf;
import main.item.Shelf;

/*
 * 本棚管理クラス
 */
public class ShelfManager extends JPanel {

	Shelf[] shelf = { new BookShelf(), new CdShelf() };
	private ShelfMain shelfMain = null; // メインクラス
	private BookShelf bookshelf = new BookShelf(); // 本棚クラス
	private CdShelf cdshelf = new CdShelf();
	private DefaultTableModel Bookmodel;
	private DefaultTableModel Cdmodel;
	private JTable Booktbl;
	private JTable Cdtbl;
	private JScrollPane bsp;
	private JScrollPane csp;
	int flag = 0;

	JTextField[] texts = null; // 入力欄
	JTabbedPane tabPane = null; // タブパネル

	/*
	 * コンストラクタ
	 *
	 * @param mainFrame メインフレーム情報
	 */
	public ShelfManager(ShelfMain mainFrame) {
		shelfMain = mainFrame;

	}

	/*
	 * モード設定
	 *
	 * @param mode 選択したメニュー情報
	 */
	public void SetMode(Mode mode) {
		// 全ての要素削除
		this.removeAll();

		// メニューへ戻る
		JButton btn = new JButton("メニューへ戻る");
		btn.setBounds(400, 15, 130, 30);
		btn.addActionListener(new ActionListener() {
			// メニューボタンイベント
			public void actionPerformed(ActionEvent e) {
				// メイン画面遷移
				TopMenu();
			}
		});
		this.add(btn);

		// 画面作成
		JLabel paneltitle = null;
		setLayout(null);
		paneltitle = new JLabel(mode.getName() + "画面");
		paneltitle.setBounds(250, 10, 243, 39);
		paneltitle.setFont(new Font("Meiryo UI", Font.BOLD, 30));
		paneltitle.setForeground(Color.white);
		this.add(paneltitle);

		switch (mode) {
		case DISP: // 表示画面
			CreateDispDeleteForm(mode);
			flag = 0;
			viewBooks();
			this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
			this.add(tabPane);

			break;

		case REGIST: // 登録画面
			CreateRegistForm();
			this.setBackground(Color.getHSBColor(0.66f, 0.5f, 0.8f));
			this.add(tabPane);
			break;

		case DELETE: // 削除画面
			CreateDispDeleteForm(mode);
			flag = 1;
			viewBooks();
			this.setBackground(Color.getHSBColor(0.33f, 0.5f, 0.8f));
			this.add(tabPane);
			break;

		default: // 定義外
			// 本来であれば、エラーメッセージを通知など。
			break;
		}
	}

	/*
	 * 表示・削除画面作成
	 *
	 * @param mode 選択したメニュー情報
	 */
	private void CreateDispDeleteForm(Mode mode) {
		// タブが既に存在していれば削除
		if (tabPane != null) {
			this.remove(tabPane);
		}

		// タブ準備
		tabPane = new JTabbedPane();
		tabPane.setBounds(10, 50, 600, 300);

		// 削除の場合に削除ボタン追加
		if (mode == main.form.Common.Mode.DELETE) {
			JButton btn1 = new JButton("全て削除");
			btn1.setBounds(280, 380, 150, 40);
			JButton btn2 = new JButton("選択削除");
			btn2.setBounds(450, 380, 150, 40);
			btn1.addActionListener(new ActionListener() {
				// 削除ボタンイベント
				public void actionPerformed(ActionEvent e) {

					int option = JOptionPane.showConfirmDialog(null, "本当に削除しますか？", "", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION && tabPane.getSelectedIndex() == 0) {
						// 全削除
						if (deleteAllBooks()) {
							JOptionPane.showMessageDialog(null, "全削除しました。");

							// 再描画
							SetMode(main.form.Common.Mode.DELETE);
						} else {
							JOptionPane.showMessageDialog(null, "削除に失敗しました。");
						}
					}
					if (option == JOptionPane.YES_OPTION && tabPane.getSelectedIndex() == 1) {
						// 全削除
						if (deleteAllCds()) {
							JOptionPane.showMessageDialog(null, "全削除しました。");

							// 再描画
							SetMode(main.form.Common.Mode.DELETE);
							tabPane.setSelectedIndex(1);
						} else {
							JOptionPane.showMessageDialog(null, "削除に失敗しました。");
						}
					}

				}
			});

			btn2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int option = JOptionPane.showConfirmDialog(null, "本当に削除しますか？", "", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION && tabPane.getSelectedIndex() == 0) {
						int index = Booktbl.getSelectedRow();
						if (index != -1) {
							if (deleteOneBook(index)) {
								JOptionPane.showMessageDialog(null, "削除しました。");
								SetMode(main.form.Common.Mode.DELETE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "削除対象を選択してください。");
						}
					}
					if (option == JOptionPane.YES_OPTION && tabPane.getSelectedIndex() == 1) {
						int index = Cdtbl.getSelectedRow();
						if (index != -1) {
							if (deleteOneCd(index)) {
								JOptionPane.showMessageDialog(null, "削除しました。");
								SetMode(main.form.Common.Mode.DELETE);
								tabPane.setSelectedIndex(1);
							}
						} else {
							JOptionPane.showMessageDialog(null, "削除対象を選択してください。");
						}
					}
				}
			});
			this.add(btn1);
			//this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
			this.add(btn2);
			//this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
		}

	}

	/*
	 * 登録画面フォーム作成
	 */
	private void CreateRegistForm() {

		// タブが既に存在していれば削除
		if (tabPane != null) {
			this.remove(tabPane);
		}

		// タブ
		tabPane = new JTabbedPane();

		// 本パネル
		JPanel tabPanel1 = new JPanel();
		JPanel tabPanel2 = new JPanel();

		// 入力項目
		JLabel[] booklabels = new JLabel[2];
		booklabels[0] = new JLabel(BookShelf.TITLE_CAPTION); // タイトル
		booklabels[1] = new JLabel(BookShelf.PERSON_CAPTION); // 作者

		JLabel[] cdlabels = new JLabel[2];
		cdlabels[0] = new JLabel(CdShelf.SONG_CAPTION); // タイトル
		cdlabels[1] = new JLabel(CdShelf.SINGER_CAPTION);

		// 入力欄
		texts = new JTextField[4];
		texts[0] = new JTextField("", 0);
		texts[1] = new JTextField("", 0);
		texts[2] = new JTextField("", 0);
		texts[3] = new JTextField("", 0);

		// 登録ボタン
		JButton tab1btn = new JButton("登録");
		JButton tab2btn = new JButton("登録");
		tab1btn.addActionListener(new ActionListener() {
			// 登録ボタンイベント
			public void actionPerformed(ActionEvent e) {

				// 本の登録処理
				if (addBook()) {
					JOptionPane.showMessageDialog(null, "登録しました。");
				} else {
					JOptionPane.showMessageDialog(null, "登録に失敗しました。");
				}

			}
		});

		tab2btn.addActionListener(new ActionListener() {
			// 登録ボタンイベント
			public void actionPerformed(ActionEvent e) {

				// 本の登録処理
				if (addCd()) {

					JOptionPane.showMessageDialog(null, "登録しました。");
				} else {
					JOptionPane.showMessageDialog(null, "登録に失敗しました。");
				}

			}
		});

		// グループレイアウト
		GroupLayout Booklayout = new GroupLayout(tabPanel1);
		Booklayout.setAutoCreateGaps(true);
		Booklayout.setAutoCreateContainerGaps(true);
		GroupLayout Cdlayout = new GroupLayout(tabPanel2);
		Cdlayout.setAutoCreateGaps(true);
		Cdlayout.setAutoCreateContainerGaps(true);

		// 題名と作者をグルーピング設定
		GroupLayout.SequentialGroup bookhGroup = Booklayout.createSequentialGroup();
		bookhGroup.addGroup(Booklayout.createParallelGroup()
				.addComponent(booklabels[0]).addComponent(booklabels[1]));
		bookhGroup.addGroup(Booklayout.createParallelGroup()
				.addComponent(texts[0]).addComponent(texts[1]));
		bookhGroup.addGroup(Booklayout.createParallelGroup()
				.addComponent(tab1btn));
		Booklayout.setHorizontalGroup(bookhGroup);

		GroupLayout.SequentialGroup bookvGroup = Booklayout.createSequentialGroup();
		bookvGroup.addGroup(Booklayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(booklabels[0]).addComponent(texts[0]));
		bookvGroup.addGroup(Booklayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(booklabels[1]).addComponent(texts[1]));
		bookvGroup.addGroup(Booklayout.createParallelGroup(Alignment.CENTER)
				.addComponent(tab1btn));
		Booklayout.setVerticalGroup(bookvGroup);

		GroupLayout.SequentialGroup cdhGroup = Cdlayout.createSequentialGroup();
		cdhGroup.addGroup(Cdlayout.createParallelGroup()
				.addComponent(cdlabels[0]).addComponent(cdlabels[1]));
		cdhGroup.addGroup(Cdlayout.createParallelGroup()
				.addComponent(texts[2]).addComponent(texts[3]));
		cdhGroup.addGroup(Cdlayout.createParallelGroup()
				.addComponent(tab2btn));
		Cdlayout.setHorizontalGroup(cdhGroup);

		GroupLayout.SequentialGroup cdvGroup = Cdlayout.createSequentialGroup();
		cdvGroup.addGroup(Cdlayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(cdlabels[0]).addComponent(texts[2]));
		cdvGroup.addGroup(Cdlayout.createParallelGroup(Alignment.BASELINE)
				.addComponent(cdlabels[1]).addComponent(texts[3]));
		cdvGroup.addGroup(Cdlayout.createParallelGroup(Alignment.CENTER)
				.addComponent(tab2btn));
		Cdlayout.setVerticalGroup(cdvGroup);

		tabPanel1.setLayout(Booklayout);
		tabPanel2.setLayout(Cdlayout);

		// タブ追加
		tabPane.addTab("本", tabPanel1);
		tabPane.setBounds(35, 50, 550, 150);
		tabPane.addTab("CD", tabPanel2);
		tabPane.setBounds(35, 50, 550, 150);

	}

	/*
	 * メイン画面へ遷移
	 */
	public void TopMenu() {
		shelfMain.changePanel();
	}

	/*
	 * 本追加
	 */
	private boolean addBook() {

		// 題名、作者の入力チェック
		boolean inputChk = true;
		if (texts[0].getText().length() > Book.TITLE_LENGTH) {
			JOptionPane.showMessageDialog(null,
					"題名は" + String.valueOf(Book.TITLE_LENGTH) + "文字以内で入力してください。");
			inputChk = false;
		} else if (texts[0].getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "題名が未入力です。");
			inputChk = false;
		}

		if (texts[1].getText().length() > Book.CREATER_LENGTH) {
			JOptionPane.showMessageDialog(null,
					"作者は" + String.valueOf(Book.CREATER_LENGTH) + "文字以内で入力してください。");
			inputChk = false;
		} else if (texts[1].getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "作者が未入力です。");
			inputChk = false;
		}

		if (inputChk) {
			// 本の生成
			Book book = new Book();

			if (book.setTitle(texts[0].getText()) && book.setPerson(texts[1].getText())) {
				// 本棚への登録
				if (bookshelf.add(book)) {
					return true;
				} else {
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"登録に失敗しました。未入力の項目もしくは、" +
								"題名は" + String.valueOf(Book.TITLE_LENGTH) + "文字以内" +
								"作者は" + String.valueOf(Book.CREATER_LENGTH) + "文字以内で入力してください。");
			}
		} else {
			return false;
		}

		return false;

	}

	private boolean addCd() {

		// 題名、作者の入力チェック
		boolean inputChk = true;
		if (texts[2].getText().length() > Cd.SONG_LENGTH) {
			JOptionPane.showMessageDialog(null,
					"曲名は" + String.valueOf(Cd.SONG_LENGTH) + "文字以内で入力してください。");
			inputChk = false;
		} else if (texts[2].getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "曲名が未入力です。");
			inputChk = false;
		}

		if (texts[3].getText().length() > Cd.SINGER_LENGTH) {
			JOptionPane.showMessageDialog(null,
					"歌手は" + String.valueOf(Cd.SINGER_LENGTH) + "文字以内で入力してください。");
			inputChk = false;
		} else if (texts[3].getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "歌手が未入力です。");
			inputChk = false;
		}

		if (inputChk) {
			// 本の生成
			Cd cd = new Cd();

			if (cd.setTitle(texts[2].getText()) && cd.setPerson(texts[3].getText())) {
				// 本棚への登録
				if (cdshelf.add(cd)) {
					return true;
				} else {
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"登録に失敗しました。未入力の項目もしくは、" +
								"曲名は" + String.valueOf(Cd.SONG_LENGTH) + "文字以内" +
								"歌手は" + String.valueOf(Cd.SINGER_LENGTH) + "文字以内で入力してください。");
			}
		} else {
			return false;
		}

		return false;

	}

	/*
	 * 全ての本削除
	 */
	private boolean deleteAllBooks() {
		if (bookshelf.deleteAll()) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "削除できる本はありません。");
			return false;
		}
	}

	private boolean deleteOneBook(int index) {
		if (bookshelf.deleteOne(index)) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "削除できる本はありません。");
			return false;
		}
	}

	private boolean deleteAllCds() {
		if (cdshelf.deleteAll()) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "削除できる本はありません。");
			return false;
		}
	}

	private boolean deleteOneCd(int index) {
		if (cdshelf.deleteOne(index)) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "削除できる本はありません。");
			return false;
		}
	}

	/*
	 * 本表示
	 */
	private void viewBooks() {

		// テーブルの列名及び設定用の値作成
		String[] BookcolumnNames = { "No", "題名", "作者" };
		String[] CdcolumnNames = { "No", "曲名", "歌手" };
		int bookCount = bookshelf.getCount();
		int cdCount = cdshelf.getCount();
		String BookTableData[][] = new String[bookCount][BookcolumnNames.length];
		String CdTableData[][] = new String[cdCount][CdcolumnNames.length];

		// 登録されている本の数
		//int bookCount = bookshelf.getBookCount();
		for (int cnt = 0; cnt < bookCount; cnt++) {
			BookTableData[cnt][0] = String.valueOf(cnt + 1); // No
			BookTableData[cnt][1] = bookshelf.get(cnt).getTitle(); // タイトル
			BookTableData[cnt][2] = bookshelf.get(cnt).getPerson(); // 作者
		}
		//int cdCount = cdshelf.getCdCount();
		for (int cnt = 0; cnt < cdCount; cnt++) {
			CdTableData[cnt][0] = String.valueOf(cnt + 1); // No
			CdTableData[cnt][1] = cdshelf.get(cnt).getTitle(); // タイトル
			CdTableData[cnt][2] = cdshelf.get(cnt).getPerson(); // 作者
		}

		// 情報作成
		Bookmodel = new DefaultTableModel(BookTableData, BookcolumnNames);
		Cdmodel = new DefaultTableModel(CdTableData, CdcolumnNames);

		// テーブル作成
		Booktbl = new JTable(Bookmodel);
		Booktbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

		Cdtbl = new JTable(Cdmodel);
		Cdtbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

		// スクロールパネルに設定してタブに追加
		bsp = new JScrollPane(Booktbl);
		csp = new JScrollPane(Cdtbl);
		tabPane.add(BookShelf.NAME, bsp);
		tabPane.add(CdShelf.NAME, csp);

		if (flag == 0) {
			JButton searchbtn = new JButton("検索");
			searchbtn.setBounds(490, 380, 75, 30);
			String[] Choice = { "タイトル", "人物" };
			JComboBox choiceBox = new JComboBox(Choice);
			choiceBox.setBounds(160, 380, 100, 30);
			String[] Terms = { "全文一致", "部分一致" };
			JComboBox termBox = new JComboBox(Terms);
			termBox.setBounds(40, 380, 100, 30);
			JTextField searchText = new JTextField();
			searchText.setBounds(290, 380, 170, 30);
			JButton editbtn = new JButton("編集");
			editbtn.setBounds(550, 15, 60, 30);

			this.add(searchbtn);
			//this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
			this.add(choiceBox);
			//this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
			this.add(termBox);
			//this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
			this.add(searchText);
			//this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));
			this.add(editbtn);
			//this.setBackground(Color.getHSBColor(0.16f, 0.7f, 0.8f));

			searchbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (searchText.getText().equals("") == false) {
						if (termBox.getSelectedIndex() == 0 && tabPane.getSelectedIndex() == 0) {//book全文
							if (choiceBox.getSelectedIndex() == 0) {//title
								int SearchedBookCount = ConnectMySQL.countData(0, "all", "title", searchText.getText());
								String BookTableData[][] = new String[SearchedBookCount][BookcolumnNames.length];
								for (int cnt = 0; cnt < ConnectMySQL.countData(0, "all", "title",
										searchText.getText()); cnt++) {
									BookTableData[cnt][0] = String.valueOf(cnt + 1); // No
									BookTableData[cnt][1] = bookshelf
											.searchTitle(0, "all", "title", cnt, searchText.getText())
											.getTitle(); // タイトル
									BookTableData[cnt][2] = bookshelf
											.searchTitle(0, "all", "title", cnt, searchText.getText())
											.getPerson(); // 作者
								}
								//System.out.println("BOOK全文検索タイトル");
								bsp.removeAll();
								Bookmodel = new DefaultTableModel(BookTableData, BookcolumnNames);
								Booktbl = new JTable(Bookmodel);
								Booktbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
								bsp = new JScrollPane(Booktbl);
								tabPane.setComponentAt(0, bsp);

							} else {//person
								int SearchedBookCount = ConnectMySQL.countData(0, "all", "person",
										searchText.getText());
								String BookTableData[][] = new String[SearchedBookCount][BookcolumnNames.length];
								for (int cnt = 0; cnt < ConnectMySQL.countData(0, "all", "person",
										searchText.getText()); cnt++) {
									BookTableData[cnt][0] = String.valueOf(cnt + 1); // No
									BookTableData[cnt][1] = bookshelf
											.searchPerson(0, "all", "person", cnt, searchText.getText()).getTitle(); // タイトル
									BookTableData[cnt][2] = bookshelf
											.searchPerson(0, "all", "person", cnt, searchText.getText()).getPerson(); // 作者
								}
								//System.out.println("BOOK全文検索人物");
								bsp.removeAll();
								Bookmodel = new DefaultTableModel(BookTableData, BookcolumnNames);
								Booktbl = new JTable(Bookmodel);
								Booktbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
								bsp = new JScrollPane(Booktbl);
								tabPane.setComponentAt(0, bsp);
							}
						} else if (termBox.getSelectedIndex() == 0 && tabPane.getSelectedIndex() == 1) {//cd全文
							if (choiceBox.getSelectedIndex() == 0) {//song
								int SearchedCdCount = ConnectMySQL.countData(1, "all", "person",
										searchText.getText());
								String CdTableData[][] = new String[SearchedCdCount][CdcolumnNames.length];
								for (int cnt = 0; cnt < ConnectMySQL.countData(1, "all", "title",
										searchText.getText()); cnt++) {
									CdTableData[cnt][0] = String.valueOf(cnt + 1); // No
									CdTableData[cnt][1] = cdshelf
											.searchTitle(1, "all", "title", cnt, searchText.getText())
											.getTitle(); // タイトル
									CdTableData[cnt][2] = cdshelf
											.searchTitle(1, "all", "title", cnt, searchText.getText())
											.getPerson(); // 作者
								}
								//System.out.println("CD全文検索タイトル");
								csp.removeAll();
								Cdmodel = new DefaultTableModel(CdTableData, CdcolumnNames);
								Cdtbl = new JTable(Cdmodel);
								Cdtbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
								csp = new JScrollPane(Cdtbl);
								tabPane.setComponentAt(1, csp);

							} else {//singer
								int SearchedCdCount = ConnectMySQL.countData(1, "all", "person",
										searchText.getText());
								String CdTableData[][] = new String[SearchedCdCount][CdcolumnNames.length];
								for (int cnt = 0; cnt < ConnectMySQL.countData(1, "all", "person",
										searchText.getText()); cnt++) {
									CdTableData[cnt][0] = String.valueOf(cnt + 1); // No
									CdTableData[cnt][1] = cdshelf
											.searchPerson(1, "all", "person", cnt, searchText.getText())
											.getTitle(); // タイトル
									CdTableData[cnt][2] = cdshelf
											.searchPerson(1, "all", "person", cnt, searchText.getText())
											.getPerson(); // 作者
								}
								//System.out.println("CD全文検索人物");
								csp.removeAll();
								Cdmodel = new DefaultTableModel(CdTableData, CdcolumnNames);
								Cdtbl = new JTable(Cdmodel);
								Cdtbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
								csp = new JScrollPane(Cdtbl);
								tabPane.setComponentAt(1, csp);
							}
						} else if (termBox.getSelectedIndex() == 1 && tabPane.getSelectedIndex() == 0) {//book部分
							if (choiceBox.getSelectedIndex() == 0) {//title
								int SearchedBookCount = ConnectMySQL.countData(0, "parts", "title",
										searchText.getText());
								String BookTableData[][] = new String[SearchedBookCount][BookcolumnNames.length];
								for (int cnt = 0; cnt < ConnectMySQL.countData(0, "parts", "title",
										searchText.getText()); cnt++) {
									BookTableData[cnt][0] = String.valueOf(cnt + 1); // No
									BookTableData[cnt][1] = bookshelf
											.searchTitle(0, "parts", "title", cnt, searchText.getText()).getTitle(); // タイトル
									BookTableData[cnt][2] = bookshelf
											.searchTitle(0, "parts", "title", cnt, searchText.getText()).getPerson(); // 作者
								}
								//System.out.println("BOOK部分検索タイトル");
								bsp.removeAll();
								Bookmodel = new DefaultTableModel(BookTableData, BookcolumnNames);
								Booktbl = new JTable(Bookmodel);
								Booktbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
								bsp = new JScrollPane(Booktbl);
								tabPane.setComponentAt(0, bsp);

							} else {//author
								int SearchedBookCount = ConnectMySQL.countData(0, "parts", "person",
										searchText.getText());
								String BookTableData[][] = new String[SearchedBookCount][BookcolumnNames.length];
								for (int cnt = 0; cnt < ConnectMySQL.countData(0, "parts", "person",
										searchText.getText()); cnt++) {
									BookTableData[cnt][0] = String.valueOf(cnt + 1); // No
									BookTableData[cnt][1] = bookshelf
											.searchPerson(0, "parts", "person", cnt, searchText.getText()).getTitle(); // タイトル
									BookTableData[cnt][2] = bookshelf
											.searchPerson(0, "parts", "person", cnt, searchText.getText()).getPerson(); // 作者
								}
								//System.out.println("BOOK部分検索人物");
								bsp.removeAll();
								Bookmodel = new DefaultTableModel(BookTableData, BookcolumnNames);
								Booktbl = new JTable(Bookmodel);
								Booktbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
								bsp = new JScrollPane(Booktbl);
								tabPane.setComponentAt(0, bsp);
							}
						} else if (termBox.getSelectedIndex() == 1 && tabPane.getSelectedIndex() == 1) {//cd部分
							if (choiceBox.getSelectedIndex() == 0) {//song
								int SearchedCdCount = ConnectMySQL.countData(1, "parts", "title",
										searchText.getText());
								String CdTableData[][] = new String[SearchedCdCount][CdcolumnNames.length];
								for (int cnt = 0; cnt < ConnectMySQL.countData(1, "parts", "title",
										searchText.getText()); cnt++) {
									CdTableData[cnt][0] = String.valueOf(cnt + 1); // No
									CdTableData[cnt][1] = cdshelf
											.searchTitle(1, "parts", "title", cnt, searchText.getText())
											.getTitle(); // タイトル
									CdTableData[cnt][2] = cdshelf
											.searchTitle(1, "parts", "title", cnt, searchText.getText())
											.getPerson(); // 作者
								}
								//System.out.println("CD部分検索タイトル");
								csp.removeAll();
								Cdmodel = new DefaultTableModel(CdTableData, CdcolumnNames);
								Cdtbl = new JTable(Cdmodel);
								Cdtbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
								csp = new JScrollPane(Cdtbl);
								tabPane.setComponentAt(1, csp);
							} else {//singer
								int SearchedCdCount = ConnectMySQL.countData(1, "parts", "person",
										searchText.getText());
								String CdTableData[][] = new String[SearchedCdCount][CdcolumnNames.length];
								for (int cnt = 0; cnt < ConnectMySQL.countData(1, "parts", "person",
										searchText.getText()); cnt++) {
									CdTableData[cnt][0] = String.valueOf(cnt + 1); // No
									CdTableData[cnt][1] = cdshelf
											.searchPerson(1, "parts", "person", cnt, searchText.getText())
											.getTitle(); // タイトル
									CdTableData[cnt][2] = cdshelf
											.searchPerson(1, "parts", "person", cnt, searchText.getText())
											.getPerson(); // 作者
								}
								//System.out.println("CD部分検索人物");
								csp.removeAll();
								Cdmodel = new DefaultTableModel(CdTableData, CdcolumnNames);
								Cdtbl = new JTable(Cdmodel);
								Cdtbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
								csp = new JScrollPane(Cdtbl);
								tabPane.setComponentAt(1, csp);
							}
						}
					} else if (searchText.getText().equals("") && tabPane.getSelectedIndex() == 0) {
						for (int cnt = 0; cnt < bookCount; cnt++) {
							BookTableData[cnt][0] = String.valueOf(cnt + 1); // No
							BookTableData[cnt][1] = bookshelf.get(cnt).getTitle(); // タイトル
							BookTableData[cnt][2] = bookshelf.get(cnt).getPerson(); // 作者
						}
						System.out.println("book全部表示");
						bsp.removeAll();
						Bookmodel = new DefaultTableModel(BookTableData, BookcolumnNames);
						Booktbl = new JTable(Bookmodel);
						Booktbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
						bsp = new JScrollPane(Booktbl);
						tabPane.setComponentAt(0, bsp);
					} else if (searchText.getText().equals("") && tabPane.getSelectedIndex() == 1) {
						System.out.println("cd全部表示");
						for (int cnt = 0; cnt < cdCount; cnt++) {
							CdTableData[cnt][0] = String.valueOf(cnt + 1); // No
							CdTableData[cnt][1] = cdshelf.get(cnt).getTitle(); // タイトル
							CdTableData[cnt][2] = cdshelf.get(cnt).getPerson(); // 作者
						}
						csp.removeAll();
						Cdmodel = new DefaultTableModel(CdTableData, CdcolumnNames);
						Cdtbl = new JTable(Cdmodel);
						Cdtbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
						csp = new JScrollPane(Cdtbl);
						tabPane.setComponentAt(1, csp);
					}
				}
			});

			editbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/*JDialog dialog = new JDialog(shelfMain);
					dialog.setBounds(400, 200, 400, 300);
					JTextField editTitle = new JTextField();
					JTextField editPerson = new JTextField();
					JButton updatebtn = new JButton("更新");
					editTitle.setText("title");
					editPerson.setText("person");
					editTitle.setBounds(0, 0, 400, 50);
					editPerson.setBounds(0, 200, 400, 50);
					updatebtn.setBounds(150, 230, 70, 30);
					dialog.add(updatebtn);
					dialog.add(editTitle);
					dialog.add(editPerson);
					
					//updatebtn.setBounds(150, 200, 70, 30);
					
					dialog.setVisible(true);*/

					if (tabPane.getSelectedIndex() == 0 && Booktbl.getSelectedRow() != -1) {
						JDialog dialog = new JDialog();
						dialog.showDialog();
						dialog.updateData(0, dialog.getTitle(), dialog.getPerson(), Booktbl.getSelectedRow());
						for (int cnt = 0; cnt < bookCount; cnt++) {
							BookTableData[cnt][0] = String.valueOf(cnt + 1); // No
							BookTableData[cnt][1] = bookshelf.get(cnt).getTitle(); // タイトル
							BookTableData[cnt][2] = bookshelf.get(cnt).getPerson(); // 作者
						}
						bsp.removeAll();
						Bookmodel = new DefaultTableModel(BookTableData, BookcolumnNames);
						Booktbl = new JTable(Bookmodel);
						Booktbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
						bsp = new JScrollPane(Booktbl);
						tabPane.setComponentAt(0, bsp);
						tabPane.setSelectedIndex(0);
					} else if (tabPane.getSelectedIndex() == 0 && Booktbl.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "編集対象を選択してください。");
					}
					if (tabPane.getSelectedIndex() == 1 && Cdtbl.getSelectedRow() != -1) {
						JDialog dialog = new JDialog();
						dialog.showDialog();
						dialog.updateData(1, dialog.getTitle(), dialog.getPerson(), Cdtbl.getSelectedRow());
						for (int cnt = 0; cnt < cdCount; cnt++) {
							CdTableData[cnt][0] = String.valueOf(cnt + 1); // No
							CdTableData[cnt][1] = cdshelf.get(cnt).getTitle(); // タイトル
							CdTableData[cnt][2] = cdshelf.get(cnt).getPerson(); // 作者
						}
						csp.removeAll();
						Cdmodel = new DefaultTableModel(CdTableData, CdcolumnNames);
						Cdtbl = new JTable(Cdmodel);
						Cdtbl.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
						csp = new JScrollPane(Cdtbl);
						tabPane.setComponentAt(1, csp);
						tabPane.setSelectedIndex(1);
					} else if (tabPane.getSelectedIndex() == 1 && Cdtbl.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "編集対象を選択してください。");
					}
				}
			});
		}
	}

}
