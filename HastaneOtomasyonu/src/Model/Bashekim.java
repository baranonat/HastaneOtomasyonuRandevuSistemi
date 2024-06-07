package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import view.BashekimGUI;

public class Bashekim extends User {

	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.connDb();
	PreparedStatement preparedStatement = null;

	public Bashekim(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
	}

	public Bashekim() {
	}

	public ArrayList<User> getDoktorList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();

		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type= 'doktor'");
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"),
						rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<User> getClinicDoktorList(int clinic_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();

		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"SELECT u.id,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id = u.id WHERE clinic_id ="
							+ clinic_id);
			while (rs.next()) {
				obj = new User(rs.getInt("u.id"), rs.getString("u.tcno"), rs.getString("u.name"),
						rs.getString("u.password"), rs.getString("u.type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean addDoktor(String tcno, String password, String name) throws SQLException {
		int key = 0;
		boolean duplicate = false;
		String query = "INSERT INTO `hospital`.`user` ( `tcno`, `password`, `name`, `type`) VALUES ( ?, ?,?,?)";
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM  `hospital`.`user` WHERE `type`='doktor' AND `tcno`='" + tcno + "'");

			while (rs.next()) {
				duplicate = true;
				Helper.showMsg("Bu TC numarasına ait bir kayıt bulunmaktadır");
				break;
			}
			if (!duplicate) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, tcno);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, "doktor");
				preparedStatement.executeUpdate();
				key = 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}

	public boolean deleteDoktor(int id) throws SQLException {
		String query = "DELETE FROM user WHERE id =?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else {
			return false;
		}
	}

	public boolean updateDoktor(int id, String tcno, String password, String name) throws SQLException {
		String query = "UPDATE user SET name = ?,tcno= ?, password = ? WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, tcno);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else {
			return false;
		}
	}

	public boolean addWorker(int user_id, int clinic_id) throws SQLException {
		String query = "INSERT INTO worker(clinic_id,user_id) VALUES(?,?)";
		boolean key = false;
		int count = 0;
		try {
			PreparedStatement checkStatement = con
					.prepareStatement("SELECT COUNT(*) FROM worker WHERE clinic_id = ? AND user_id = ?");
			checkStatement.setInt(1, clinic_id);
			checkStatement.setInt(2, user_id);
			ResultSet countResult = checkStatement.executeQuery();
			if (countResult.next()) {
				count = countResult.getInt(1);
			}
			if (count == 0) {
				PreparedStatement insertStatement = con.prepareStatement(query);
				insertStatement.setInt(1, clinic_id);
				insertStatement.setInt(2, user_id);
				insertStatement.executeUpdate();
			}
			key = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}

	public boolean deleteWorker(int user_id) throws SQLException {
		String query = "DELETE FROM worker WHERE user_id =?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, user_id);

			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else {
			return false;
		}
	}

	
}
