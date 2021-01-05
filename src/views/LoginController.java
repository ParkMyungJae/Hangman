package views;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import util.JDBCUtil;

public class LoginController implements Controller {

	JDBCUtil db = new JDBCUtil();
	Connection con = db.getConnection(); // db연결

	@FXML
	private TextField idField;

	@FXML
	private PasswordField pwField;

	public void Login() {
		String uId = idField.getText();
		String uPwd = pwField.getText();
		String connectionString = "jdbc:mysql://gondr.asuscomm.com/yy_10116"
				+ "?useUnicode=true"
				+ "&characterEncoding=utf8"
				+ "&useSSL=false"
				+ "&serverTimezone=Asia/Seoul";
		// MySQL 계정
		String dbId = "yy_10116";
		// MySQL 계정 비밀번호
		String dbPw = "wkwjsrjxkrl9";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			// 디비 연결
			con = DriverManager.getConnection(connectionString, dbId, dbPw);

			sql = "select id,password from Hangman where id = ? and password = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setString(2, uPwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id").equals(uId) && rs.getString("password").equals(uPwd)) {
					Main.instance.changeScene("Main");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
	}

	public void Register() {
		Main.instance.changeScene("Register");
	}

	public void ENTER(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.ENTER) {
			Login();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
}
