   package view;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder; 
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.Statement;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;


public class LoginGUI extends JFrame {
	

	private JTextField fld_hastaTc;
	private JTextField fld_doktorTc;
	private JPasswordField fld_doktorPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_hastaPass;
	private JTextField fld_bashekimTc;
	private JPasswordField fld_bashekimPass;
	private Color c0 = new Color(209, 242, 235);
	private Color c1 = new Color(163, 204, 255);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
	
		setBackground(Color.WHITE);
		getContentPane().setForeground(c0);
		getContentPane().setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		getContentPane().setBackground(c0);
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		getContentPane().setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon("C:\\Users\\VOLKAN\\Desktop\\medicine.jpeg"));
		lbl_logo.setBounds(227, 24, 82, 55);
		getContentPane().add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Yönetim Sistemine Hoşgeldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNewLabel.setBounds(102, 71, 344, 71);
		getContentPane().add(lblNewLabel);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		w_tabpane.setBounds(10, 152, 566, 287);
		getContentPane().add(w_tabpane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(c1);
		w_tabpane.addTab("Hasta Girişi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		JLabel lblTcNumaranz = new JLabel("T.C. Numaranız:");
		lblTcNumaranz.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblTcNumaranz.setBounds(40, 21, 164, 42);
		w_hastaLogin.add(lblTcNumaranz);
		
		JLabel lblifre = new JLabel("Şifre:");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblifre.setBounds(40, 91, 45, 42);
		w_hastaLogin.add(lblifre);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_hastaTc.setBounds(178, 24, 232, 37);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGuI =new RegisterGUI();
				rGuI.setVisible(true);
				dispose();
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		btn_register.setBounds(40, 161, 240, 47);
		w_hastaLogin.add(btn_register);
		
		JButton btn_hastaLogin = new JButton("Giriş Yap");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if (fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
						Helper.showMsg("fill");
					} else {
						boolean key = true;

						try {
							Connection con = conn.connDb();
							Statement st = con.createStatement();
							ResultSet rs = st.executeQuery("SELECT *FROM user");
							while (rs.next()) {
								if (fld_hastaTc.getText().equals(rs.getString("tcno"))
										&& fld_hastaPass.getText().equalsIgnoreCase(rs.getString("password"))) {
									if (rs.getString("type").equals("hasta")) {
										Hasta hasta = new Hasta();
										hasta.setId(rs.getInt("id"));
										hasta.setPassword("password");
										hasta.setTcno(rs.getString("tcno"));
										hasta.setName(rs.getString("name"));
										hasta.setType(rs.getString("type"));
										HastaGUI hGUI = new HastaGUI(hasta);
										hGUI.setVisible(true);
										dispose();
										key = false;
									}
								}
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (key)
							Helper.showMsg("Böyle bir hasta bulunamadı lütfen kayıt olunuz.");

					}
				}
			});
		btn_hastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		btn_hastaLogin.setBounds(311, 161, 240, 47);
		w_hastaLogin.add(btn_hastaLogin);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(178, 91, 232, 37);
		w_hastaLogin.add(fld_hastaPass);
		
		JPanel w_doktorLogin = new JPanel();
		w_doktorLogin.setBackground(c1);
		w_tabpane.addTab("Doktor Girişi", null, w_doktorLogin, null);
		w_doktorLogin.setLayout(null);
		
		JLabel lblTcNumaranz_1 = new JLabel("T.C. Numaranız:");
		lblTcNumaranz_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblTcNumaranz_1.setBounds(40, 21, 164, 42);
		w_doktorLogin.add(lblTcNumaranz_1);
		
		fld_doktorTc = new JTextField();
		fld_doktorTc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_doktorTc.setColumns(10);
		fld_doktorTc.setBounds(178, 24, 344, 37);
		w_doktorLogin.add(fld_doktorTc);
		
		JLabel lblifre_1 = new JLabel("Şifre:");
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblifre_1.setBounds(40, 91, 45, 42);
		w_doktorLogin.add(lblifre_1);
		
		JButton btn_doktorLogin =  new JButton("Giriş Yap");
		btn_doktorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {       
				if (fld_doktorTc.getText().length() == 0 || fld_doktorPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean key = true;

					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT *FROM user");
						while (rs.next()) {
							if (fld_doktorTc.getText().equals(rs.getString("tcno"))
									&& fld_doktorPass.getText().equalsIgnoreCase(rs.getString("password"))) {
								if (rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
									key = false;
								}
								
								
							}
							
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (key)
						Helper.showMsg("Böyle bir kullanıcı girişi yoktur!!!");

				}
			}
		});
		btn_doktorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		btn_doktorLogin.setBounds(40, 167, 482, 47);
		w_doktorLogin.add(btn_doktorLogin);
		
		fld_doktorPass = new JPasswordField();
		fld_doktorPass.setBounds(178, 91, 344, 37);
		w_doktorLogin.add(fld_doktorPass);
		
		JPanel w_bashekimLogin = new JPanel();
		w_bashekimLogin.setBackground(c1);
		w_tabpane.addTab("Başhekim Girişi", null, w_bashekimLogin, null);
		w_bashekimLogin.setLayout(null);
		
		JLabel lblTcNumaranz_1_1 = new JLabel("T.C. Numaranız:");
		lblTcNumaranz_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblTcNumaranz_1_1.setBounds(40, 21, 164, 42);
		w_bashekimLogin.add(lblTcNumaranz_1_1);
		
		fld_bashekimTc = new JTextField();
		fld_bashekimTc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_bashekimTc.setColumns(10);
		fld_bashekimTc.setBounds(178, 24, 344, 37);
		w_bashekimLogin.add(fld_bashekimTc);
		
		JLabel lblifre_1_1 = new JLabel("Şifre:");
		lblifre_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblifre_1_1.setBounds(40, 91, 45, 42);
		w_bashekimLogin.add(lblifre_1_1);
		
		fld_bashekimPass = new JPasswordField();
		fld_bashekimPass.setBounds(178, 91, 344, 37);
		w_bashekimLogin.add(fld_bashekimPass);
		
		JButton btn_bashekimLogin = new JButton("Giriş Yap");
		btn_bashekimLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_bashekimTc.getText().length() == 0 || fld_bashekimPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean key = true;

					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT *FROM user");
						while (rs.next()) {
							if (fld_bashekimTc.getText().equals(rs.getString("tcno"))
									&& fld_bashekimPass.getText().equalsIgnoreCase(rs.getString("password"))) {
								if (rs.getString("type").equals("basHekim")) {
									Bashekim bhekim = new Bashekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword("password");
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bhekim);
									bGUI.setVisible(true);
									dispose();
									key = false;
								}
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (key)
						Helper.showMsg("Böyle bir kullanıcı girişi yoktur!!!");

				}
			}
		});
		btn_bashekimLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		btn_bashekimLogin.setBounds(40, 167, 482, 47);
		w_bashekimLogin.add(btn_bashekimLogin);
	}
}
