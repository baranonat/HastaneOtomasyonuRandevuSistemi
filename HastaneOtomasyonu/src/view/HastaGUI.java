package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {
	private JPanel w_pane;
	private static Hasta hasta =new Hasta();
	private Clinic clinic =new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData =null;
	private JTable table_whour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData =null;
	private DefaultTableModel appointModel;
	private int selectDoctorID = 0;
	private String selectDoctorName = null;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();
	private JTable table_appoint;
	private JPopupMenu appointMenu;
	private Color c0=new Color(209, 242, 235);
	private Color c1=new Color(163, 228, 215);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public HastaGUI(Hasta hasta) throws SQLException {
		
		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];
		
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		
		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doktor";
		colAppoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];
		for (int i = 0; i < appoint.getHastaList(hasta.getId()).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta.getId()).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
			
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
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın "+hasta.getName());
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
		w_tab.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		w_tab.setBackground(Color.WHITE);
		w_tab.setBounds(10, 62, 714, 389);
		w_pane.add(w_tab);
		
		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(c0);
		w_tab.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 35, 280, 312);
		w_appointment.add(w_scrollDoctor);
		
		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);
		
		JLabel lblNewLabel_1_2 = new JLabel("Doktor Listesi");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1_2.setBounds(10, 4, 82, 27);
		w_appointment.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Poliklinik Adı");
		lblNewLabel_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(300, 4, 90, 27);
		w_appointment.add(lblNewLabel_1_3);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(300, 35, 150, 35);
		select_clinic.addItem("--Poliklinik Seç--");
		for(int i=0; i< clinic.getList().size();i++) {
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(select_clinic.getSelectedIndex() !=0) {
					JComboBox  c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel =(DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i=0; i<clinic.getClinicDoktorList(item.getKey()).size() ; i++) {
							doctorData[0] =clinic.getClinicDoktorList(item.getKey()).get(i).getId();
							doctorData[1] =clinic.getClinicDoktorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
							
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}else {
					DefaultTableModel clearModel =(DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}
			}
		});
		w_appointment.add(select_clinic);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Doktor Seç");
		lblNewLabel_1_3_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_3_1.setBounds(300, 132, 90, 27);
		w_appointment.add(lblNewLabel_1_3_1);
		
		JButton btn_selDoctor = new JButton("Seç");
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table_doctor.getSelectedRow();
				if(row >= 0) {
					String value= table_doctor.getModel().getValueAt(row, 0).toString();
					int id=Integer.parseInt(value);
					DefaultTableModel clearModel=(DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);
					
					try {
						for(int i=0; i<whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
							

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_whour.setModel(whourModel);
					selectDoctorID = id;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
				}else {
					Helper.showMsg("Lütfen bir doktor seçiniz!");
				}
			}
		});
		btn_selDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_selDoctor.setBounds(300, 159, 156, 30);
		w_appointment.add(btn_selDoctor);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Uygun Saatler");
		lblNewLabel_1_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		lblNewLabel_1_2_1.setBounds(460, 4, 85, 27);
		w_appointment.add(lblNewLabel_1_2_1);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(460, 35, 239, 312);
		w_appointment.add(w_scrollWhour);
		
		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JLabel lblNewLabel_1_3_1_1 = new JLabel("Randevu");
		lblNewLabel_1_3_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_3_1_1.setBounds(300, 249, 90, 27);
		w_appointment.add(lblNewLabel_1_3_1_1);
		
		JButton btn_addAppoint = new JButton("Randevu Al");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, hasta.getId(), selectDoctorName,
								hasta.getName(), date);
						if (control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							updateAppointModel(hasta.getId());

						} else {
							Helper.showMsg("error");
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("Lutfen gecerli bir tarih giriniz!");
				}
			}
		});
		btn_addAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_addAppoint.setBounds(300, 276, 156, 30);
		w_appointment.add(btn_addAppoint);
		
		JPanel w_appoint = new JPanel();
		w_tab.addTab("Randevularım", null, w_appoint, null);
		w_appoint.setLayout(null);
		
		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 10, 689, 335);
		w_appoint.add(w_scrollAppoint);
		
		appointMenu = new JPopupMenu();
		JMenuItem deleteMenu = new JMenuItem("Sil");
		appointMenu.add(deleteMenu);
		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					String selDate = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 2);
					String selDoctorName = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 1);
					appoint.deleteAppoint(selDate, selDoctorName);
					updateAppointModel(hasta.getId());

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		
		table_appoint = new JTable(appointModel);
		table_appoint.setComponentPopupMenu(appointMenu);
		table_appoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				try {
					int selectedRow = table_appoint.rowAtPoint(point);
					table_appoint.setRowSelectionInterval(selectedRow, selectedRow);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		w_scrollAppoint.setViewportView(table_appoint);
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);
	}

		
	
	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
			public void updateAppointModel(int hasta_id) throws SQLException {
				DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
				clearModel.setRowCount(0);
				try {
					for (int i = 0; i < appoint.getHastaList(hasta_id).size(); i++) {
						appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();
						appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorName();
						appointData[2] = appoint.getHastaList(hasta_id).get(i).getAppDate();
						appointModel.addRow(appointData);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}