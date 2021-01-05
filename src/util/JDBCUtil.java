package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("드라이버 파일이 존재하지 않습니다.");
			return null;
		}
		
		String connectionString = "jdbc:mysql://gondr.asuscomm.com/yy_10116"
			+ "?useUnicode=true"
			+ "&characterEncoding=utf8"
			+ "&useSSL=false"
			+ "&serverTimezone=Asia/Seoul";
		
		String userId = "yy_10116";
		String password = "wkwjsrjxkrl9";
		
		Connection con = null;
		try {
			con= DriverManager.getConnection(connectionString, userId, password);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB 연결중 오류 발생");
		}
		return con;
	}
}