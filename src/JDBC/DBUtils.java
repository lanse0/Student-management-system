package JDBC;

import java.sql.*;

public class DBUtils {
	//加载驱动，并建立数据库连接
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver"); // mysql8 驱动改名
		String url = "jdbc:mysql://localhost:3306/stumansys?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
		//连接字符串serverTimezone=Asia/Shanghai 设置详细的地区 解决时间错乱
		//https://blog.csdn.net/weixin_29040367/article/details/114889477
		String username = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	// 公共的增删改方法
	public static boolean executeUpdate(String sql, Object params[]) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.release(null, pstmt, conn);
		}
		return result;
	}

	// 关闭数据库连接，释放资源
	public static void release(PreparedStatement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public static void release(ResultSet rs, PreparedStatement stmt, Connection conn){
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		release(stmt, conn);
	}

}
