package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Clinic;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class UpdateClinicGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fld_clinikName;
	private static 	Clinic clinic;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateClinicGUI frame = new UpdateClinicGUI(clinic);
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
	public UpdateClinicGUI(Clinic clinic) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("Poliklinik Adı");
		lblNewLabel_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(10, 10, 90, 27);
		contentPane.add(lblNewLabel_1_3);
		
		fld_clinikName = new JTextField();
		fld_clinikName.setColumns(10);
		fld_clinikName.setBounds(10, 34, 216, 25);
		fld_clinikName.setText(clinic.getName());
		contentPane.add(fld_clinikName);
		
		JButton btn_updateClinic =  new JButton("Düzenle");
		btn_updateClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
					try {
						clinic.updateClinic(clinic.getId(), fld_clinikName.getText());
						Helper.showMsg("success");
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			}
		});
		btn_updateClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 13));
		btn_updateClinic.setBounds(10, 61, 216, 30);
		contentPane.add(btn_updateClinic);
	}

}
