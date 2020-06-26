package main.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import main.form.Common.Mode;


public class ShelfMain extends JFrame {

	private JPanel contentPane; // メインパネル
	private ShelfManager manager; // 本棚管理クラス

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShelfMain frame = new ShelfMain();

					// ウインドウを表示
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Create the frame.
     */
    public ShelfMain() {

        setTitle("本棚管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 640, 480);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 51, 102));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("本棚管理");
        label.setBounds(5, 10, 614, 39);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(new Color(255, 255, 255));
        label.setFont(new Font("Meiryo UI", Font.BOLD, 30));
        contentPane.add(label);

        manager = new ShelfManager(this);

        JButton button = new JButton("1.本棚表示");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manager.SetMode(Mode.DISP);
                contentPane.setVisible(false);
                setContentPane(manager);
            }
        });
        button.setBounds(100, 100, 400, 40);
        contentPane.add(button);

        JButton button_1 = new JButton("2.本棚に登録");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manager.SetMode(Mode.REGIST);
                contentPane.setVisible(false);
                setContentPane(manager);
            }
        });
        button_1.setBounds(100, 160, 400, 40);
        contentPane.add(button_1);

        JButton button_2 = new JButton("3.本棚から削除");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manager.SetMode(Mode.DELETE);
                contentPane.setVisible(false);
                setContentPane(manager);
            }
        });
        button_2.setBounds(100, 220, 400, 40);
        contentPane.add(button_2);

        JButton button_3 = new JButton("4.閉じる");
        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ウインドウを閉じる
                Component c = (Component)e.getSource();
                Window w = SwingUtilities.getWindowAncestor(c);
                w.dispose();
            }
        });
        button_3.setBounds(100, 280, 400, 40);
        contentPane.add(button_3);
    }

	public void changePanel() {
        // サブパネルを破棄して、メインパネルを表示
        remove(manager);
        setContentPane(contentPane);
        contentPane.setVisible(true);
    }
}
