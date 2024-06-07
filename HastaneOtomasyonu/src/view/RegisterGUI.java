package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_pass;
	private Hasta hasta =new Hasta();
	private Color c0=new Color(209, 242, 235);
	private Color c1=new Color(163, 228, 215);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		w_pane = new JPanel();
		w_pane.setBackground(c0);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ad-Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 10, 60, 27);
		w_pane.add(lblNewLabel_1);
		
		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBounds(10, 34, 264, 27);
		w_pane.add(fld_name);
		
		JLabel lblNewLabel_1_1 = new JLabel("T.C. Numarası");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(10, 72, 100, 20);
		w_pane.add(lblNewLabel_1_1);
		
		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 95, 264, 27);
		w_pane.add(fld_tcno);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Şifre");
		lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1_1_1.setBounds(10, 132, 100, 20);
		w_pane.add(lblNewLabel_1_1_1);
		
		fld_pass = new JPasswordField();
		fld_pass.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 13));
		fld_pass.setBounds(10, 155, 264, 27);
		w_pane.add(fld_pass);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_tcno.getText().length() ==0 || fld_pass.getText().length() == 0 || fld_pass.getText().length() ==0) {
					Helper.showMsg("fill");
				}else {
					try {
						boolean control=hasta.register(fld_tcno.getText(),fld_pass.getText(), fld_name.getText());
						if(control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				}
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_register.setBounds(10, 206, 264, 35);
		w_pane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri Dön");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_backto.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_backto.setBounds(10, 252, 264, 35);
		w_pane.add(btn_backto);
	}
}
