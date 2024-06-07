package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.mariadb.jdbc.client.tls.MariaDbX509TrustingManager;

import Model.Appointment;
import Model.Doctor;
import Model.Hasta;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame {

	private JPanel w_pane;
	private static Doctor doctor = new Doctor();

	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private JTable table_doctorAppoint;
	private DefaultTableModel d_appointModel;

	private Object[] d_appointData = null;
	private Appointment appoint = new Appointment();
	private JPopupMenu d_appointMenu;
	private Color c0 = new Color(209, 242, 235);
	private Color c1 = new Color(163, 228, 215);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doctor doctor) throws SQLException {
		setBackground(Color.WHITE);
		d_appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Hasta";
		colAppoint[2] = "Tarih";
		d_appointModel.setColumnIdentifiers(colAppoint);
		d_appointData = new Object[3];
		try {
			for (int i = 0; i < appoint.getRandevuList(doctor.getId()).size(); i++) {
				d_appointData[0] = appoint.getRandevuList(doctor.getId()).get(i).getId();
				d_appointData[1] = appoint.getRandevuList(doctor.getId()).get(i).getHastaName();
				d_appointData[2] = appoint.getRandevuList(doctor.getId()).get(i).getAppDate();
				d_appointModel.addRow(d_appointData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);

		}

		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(c1);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın " + doctor.getName());
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

		JPanel w_whour = new JPanel();
		w_whour.setBackground(c0);
		w_tab.addTab("Çalışma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 11, 130, 22);
		w_whour.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setModel(new DefaultComboBoxModel(new String[] { "09:00", "09:15", "09:30", "09:45", "10:00",
				"10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45",
				"13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30" }));
		select_time.setBounds(150, 11, 65, 22);
		w_whour.add(select_time);

		JButton btn_addWhour = new JButton("Ekle");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
				}
				if (date.length() == 0) {
					Helper.showMsg("Lütfen geçerli bir tarih giriniz !");

				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		btn_addWhour.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btn_addWhour.setBounds(225, 11, 130, 22);
		w_whour.add(btn_addWhour);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(0, 44, 709, 311);
		w_whour.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);    

		JButton btn_deleteWhour = new JButton("Sil");
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String selectRow = table_whour.getModel().getValueAt(selRow, 0).toString();
					int selID = Integer.parseInt(selectRow);
					boolean control;
					try {
						control = doctor.deleteWhour(selID);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);

						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Lütfen bir tarih seçiniz !");
				}
			}
		});
		btn_deleteWhour.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btn_deleteWhour.setBounds(571, 11, 130, 22);
		w_whour.add(btn_deleteWhour);

		JPanel w_appointments = new JPanel();
		w_tab.addTab("Randevular", null, w_appointments, null);
		w_appointments.setLayout(null);

		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 649, 296);
		w_appointments.add(w_scrollAppoint);

		d_appointMenu = new JPopupMenu();
		JMenuItem deleteMenu = new JMenuItem("İptal Et");
		d_appointMenu.add(deleteMenu);
		deleteMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String selDate = (String) table_doctorAppoint.getValueAt(table_doctorAppoint.getSelectedRow(), 2);
					String selHastaName = (String) table_doctorAppoint.getValueAt(table_doctorAppoint.getSelectedRow(),
							1);
					appoint.deleteAppoint(selDate, selHastaName);
					updateDAppointModel(doctor.getId());

				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		table_doctorAppoint = new JTable(d_appointModel);
		table_doctorAppoint.setComponentPopupMenu(d_appointMenu);
		table_doctorAppoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				try {
					int selectedRow = table_doctorAppoint.rowAtPoint(point);
					table_doctorAppoint.setRowSelectionInterval(selectedRow, selectedRow);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		w_scrollAppoint.setViewportView(table_doctorAppoint);
	}

	public void updateWhourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);

		}
	}

	public void updateDAppointModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctorAppoint.getModel();
		clearModel.setRowCount(0);
		try {
			for (int i = 0; i < appoint.getRandevuList(doctor_id).size(); i++) {
				d_appointData[0] = appoint.getRandevuList(doctor_id).get(i).getId();
				d_appointData[1] = appoint.getRandevuList(doctor_id).get(i).getHastaName();
				d_appointData[2] = appoint.getRandevuList(doctor_id).get(i).getAppDate();
				d_appointModel.addRow(d_appointData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
