package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IllegalFormatFlagsException;
import java.awt.event.ActionEvent;
import Helper.*;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {
	static Bashekim bashekim = new Bashekim();
	static Doctor doctor = new Doctor();
	Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField fld_dName;
	private JTextField fld_Tcno;
	private JPasswordField fld_dPass;
	private JTextField fld_doktorID;
	private JTable table_doktor;
	private DefaultTableModel doktorModel = null;
	private Object[] doktorData = null;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JTable clinic_table;
	private JPopupMenu clinicMenu;
	private JTable table_worker;
	JComboBox select_doktor = new JComboBox();
	
	private Color c0 = new Color(209, 242, 235);
	private Color c1 = new Color(163, 228, 215);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public BashekimGUI(Bashekim bashekim) throws SQLException {

		doktorModel = new DefaultTableModel();
		Object[] colDoktorName = new Object[4];
		colDoktorName[0] = "ID";
		colDoktorName[1] = "Ad Soyad";
		colDoktorName[2] = "TC NO";
		colDoktorName[3] = "Şifre";
		doktorModel.setColumnIdentifiers(colDoktorName);
		doktorData = new Object[4];
		for (int i = 0; i < bashekim.getDoktorList().size(); i++) {
			doktorData[0] = bashekim.getDoktorList().get(i).getId();
			doktorData[1] = bashekim.getDoktorList().get(i).getName();
			doktorData[2] = bashekim.getDoktorList().get(i).getTcno();
			doktorData[3] = bashekim.getDoktorList().get(i).getPassword();
			doktorModel.addRow(doktorData);

		}
		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Poliklinik Adı";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];

		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);

		}
		// worker model
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];

		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(c1);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın " + bashekim.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 10, 271, 28);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btnNewButton.setBounds(567, 10, 141, 28);
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBackground(Color.WHITE);
		w_tab.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		w_tab.setBounds(10, 77, 716, 376);
		w_pane.add(w_tab);

		JPanel w_doktor = new JPanel();
		w_doktor.setBackground(c0);
		w_tab.addTab("Doktor Yönetimi", null, w_doktor, null);
		w_doktor.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Ad-Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(540, 10, 60, 27);
		w_doktor.add(lblNewLabel_1);

		fld_dName = new JTextField();
		fld_dName.setBounds(540, 34, 150, 25);
		w_doktor.add(fld_dName);
		fld_dName.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("T.C. No");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(540, 65, 60, 27);
		w_doktor.add(lblNewLabel_1_1);

		fld_Tcno = new JTextField();
		fld_Tcno.setColumns(10);
		fld_Tcno.setBounds(540, 89, 150, 25);
		w_doktor.add(fld_Tcno);

		JLabel lblNewLabel_1_2 = new JLabel("Şifre");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1_2.setBounds(540, 120, 60, 27);
		w_doktor.add(lblNewLabel_1_2);

		fld_dPass = new JPasswordField();
		fld_dPass.setBounds(540, 144, 150, 25);
		w_doktor.add(fld_dPass);

		JButton btn_addDoktor = new JButton("Ekle");
		btn_addDoktor.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				if (fld_dName.getText().length() == 0 || fld_dPass.getText().length() == 0
						|| fld_Tcno.getText().length() == 0) {
					Helper.showMsg("fill");

				} else {
					try {
						boolean control = bashekim.addDoktor(fld_Tcno.getText(), fld_dPass.getText(),
								fld_dName.getText());
						
						if (control) {
							Helper.showMsg("success");
							fld_dName.setText(null);
							fld_Tcno.setText(null);
							fld_dPass.setText(null);
							updateDoktorModel();
							updateCombobox(select_doktor);
							

						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
		});
		btn_addDoktor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_addDoktor.setBounds(540, 182, 150, 30);
		w_doktor.add(btn_addDoktor);

		JLabel lblNewLabel_1_2_1 = new JLabel("Kullanıcı ID");
		lblNewLabel_1_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1_2_1.setBounds(540, 252, 70, 27);
		w_doktor.add(lblNewLabel_1_2_1);

		fld_doktorID = new JTextField();
		fld_doktorID.setEnabled(false);
		fld_doktorID.setColumns(10);
		fld_doktorID.setBounds(540, 276, 150, 25);
		w_doktor.add(fld_doktorID);

		JButton btn_delDoktor = new JButton("Sil");
		btn_delDoktor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doktorID.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir doktor seçiniz!");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_doktorID.getText());

						try {
							boolean control = bashekim.deleteDoktor(selectID);
							boolean control1 = bashekim.deleteWorker(selectID);
							boolean control2 = doctor.deleteWhour(selectID);
							if (control == true && control1 == true && control2 == true) {
								Helper.showMsg("success");
								fld_doktorID.setText(null);
								updateDoktorModel();
							
								updateCombobox(select_doktor);
								
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_delDoktor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_delDoktor.setBounds(540, 310, 150, 30);
		w_doktor.add(btn_delDoktor);

		JScrollPane w_scrollDoktor = new JScrollPane();
		w_scrollDoktor.setBounds(10, 10, 519, 329);
		w_doktor.add(w_scrollDoktor);

		table_doktor = new JTable(doktorModel);
		w_scrollDoktor.setViewportView(table_doktor);

		table_doktor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_doktorID.setText(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());

				} catch (Exception ex) {

				}
			}
		});

		table_doktor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer
							.parseInt(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());
					String selectName = table_doktor.getValueAt(table_doktor.getSelectedRow(), 1).toString();
					String selectTcno = table_doktor.getValueAt(table_doktor.getSelectedRow(), 2).toString();
					String selectPass = table_doktor.getValueAt(table_doktor.getSelectedRow(), 3).toString();

					try {
						boolean control = bashekim.updateDoktor(selectID, selectTcno, selectPass, selectName);

					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(c0);
		w_tab.addTab("Poliklinikler", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setEnabled(false);
		w_scrollClinic.setBounds(10, 10, 260, 322);
		w_clinic.add(w_scrollClinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int selID = Integer.parseInt(clinic_table.getValueAt(clinic_table.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {

							e1.printStackTrace();
						}
					}
				});

			}
		});

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selID = Integer.parseInt(clinic_table.getValueAt(clinic_table.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {
							Helper.showMsg("success");
							updateClinicModel();
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		clinic_table = new JTable(clinicModel);
		clinic_table.setComponentPopupMenu(clinicMenu);
		clinic_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = clinic_table.rowAtPoint(point);
				clinic_table.setRowSelectionInterval(selectedRow, selectedRow);

			}
		});
		w_scrollClinic.setViewportView(clinic_table);

		JLabel lblNewLabel_1_3 = new JLabel("Poliklinik Adı");
		lblNewLabel_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(278, 10, 90, 27);
		w_clinic.add(lblNewLabel_1_3);

		fld_clinicName = new JTextField();
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(278, 34, 156, 25);
		w_clinic.add(fld_clinicName);

		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if (clinic.addClinik(fld_clinicName.getText())) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_addClinic.setBounds(278, 69, 156, 30);
		w_clinic.add(btn_addClinic);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(440, 10, 260, 322);
		w_clinic.add(w_scrollWorker);

		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);

	
		select_doktor.setModel(new DefaultComboBoxModel(new String[] {"Poliklinik Seç"}));
		select_doktor.setBounds(278, 255, 156, 35);
		updateCombobox(select_doktor);
		/*for (int i = 0; i < bashekim.getDoktorList().size(); i++) {

			select_doktor.addItem(new Item(bashekim.getDoktorList().get(i).getId(), bashekim.getDoktorList().get(i).getName()));

		}*/
		
		select_doktor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();

			// System.out.println(item.getKey() + " : " + item.getValue());

		});
		w_clinic.add(select_doktor);

		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = clinic_table.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = clinic_table.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					Item doctorItem = (Item) select_doktor.getSelectedItem();
					try {
						boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicID);
						if (control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
							clearModel.setRowCount(0);
							for (int i = 0; i < bashekim.getClinicDoktorList(selClinicID).size(); i++) {
								workerData[0] = bashekim.getClinicDoktorList(selClinicID).get(i).getId();
								workerData[1] = bashekim.getClinicDoktorList(selClinicID).get(i).getName();
								workerModel.addRow(workerData);

							}
							table_worker.setModel(workerModel);

						} else {
							Helper.showMsg("error");

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz!");
				}
			}
		});
		btn_addWorker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_addWorker.setBounds(278, 302, 156, 30);
		w_clinic.add(btn_addWorker);

		JLabel lblNewLabel_1_3_1 = new JLabel("Poliklinik Adı");
		lblNewLabel_1_3_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_3_1.setBounds(278, 129, 90, 27);
		w_clinic.add(lblNewLabel_1_3_1);

		JButton btn_workerSelect = new JButton("Seç");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = clinic_table.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = clinic_table.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < bashekim.getClinicDoktorList(selClinicID).size(); i++) {
							workerData[0] = bashekim.getClinicDoktorList(selClinicID).get(i).getId();
							workerData[1] = bashekim.getClinicDoktorList(selClinicID).get(i).getName();
							workerModel.addRow(workerData);

						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);
				} else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz");
				}
			}
		});
		btn_workerSelect.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_workerSelect.setBounds(278, 153, 156, 30);
		w_clinic.add(btn_workerSelect);

	}

	public void updateDoktorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doktor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoktorList().size(); i++) {
			doktorData[0] = bashekim.getDoktorList().get(i).getId();
			doktorData[1] = bashekim.getDoktorList().get(i).getName();
			doktorData[2] = bashekim.getDoktorList().get(i).getTcno();
			doktorData[3] = bashekim.getDoktorList().get(i).getPassword();
			doktorModel.addRow(doktorData);

		}
	}
public void updateCombobox(JComboBox comboBox) throws SQLException {
		
	comboBox.removeAllItems();
		for (int i = 0; i < bashekim.getDoktorList().size(); i++) {

			comboBox.addItem(new Item(bashekim.getDoktorList().get(i).getId(), bashekim.getDoktorList().get(i).getName()));

		}
	}
	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) clinic_table.getModel();
		clearModel.setRowCount(0);

		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);

		}
	}

}
