package views;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController implements Controller {
	@FXML
	private TextField regID;

	@FXML
	private PasswordField regPW;

	@FXML
	private PasswordField reRegPW;

	@FXML
	private TextField nickname;

	public void RegisterPS() throws Exception {
		String uName = nickname.getText();
		String uId = regID.getText();
		String uPwd = reRegPW.getText();
		String connectionString = "jdbc:mysql://gondr.asuscomm.com/yy_10116" + "?useUnicode=true"
				+ "&characterEncoding=utf8" + "&useSSL=false" + "&serverTimezone=Asia/Seoul";
		// MySQL 계정
		String dbId = "yy_10116";
		// MySQL 계정 비밀번호
		String dbPw = "wkwjsrjxkrl9";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		int num = 0;
		String name = uName;
		String id = uId;
		String pwd = uPwd;
		
		Alert alert = new Alert(AlertType.INFORMATION);
		
		if (regID.getText().equals(null) || regPW.getText().equals(null)) {
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("누락된 항목이 있습니다.");
			alert.show();
			return;
		}

		try {
			if (regID.getText().equals(null) || regPW.getText().equals(null)) {
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("누락된 항목이 있습니다.");
				alert.show();
				return;
			}
			conn = DriverManager.getConnection(connectionString, dbId, dbPw);

			sql = "insert into Hangman values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.executeUpdate();

			

			alert.setTitle("알림");
			alert.setHeaderText(null);
			alert.setContentText("회원가입 완료!");
			alert.show();

			Main.instance.changeScene("Login");

		} catch (Exception e) {

			if (regID.getText().equals(null)) {
				e.printStackTrace();
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("누락된 항목이 있습니다.");
				alert.show();
			} else {
				e.printStackTrace();
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("이미 가입되어 있는 계정입니다.");
				alert.show();
			}

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

	public void back() {
		Main.instance.changeScene("Login");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
}
