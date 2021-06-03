package tabelaDeFrutas.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FabricaConexao {

private static String DATABASE = "frutaria";
	
	private static String URL = "jdbc:mysql://localhost:3306/" + DATABASE;
	
	private static String USER = "root";
	
	private static String PASSWORD = "PQ@n@17";
	
	
	
	public static Connection getConexao() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			return conn;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void fechaConexao(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void fechaConexao(Connection conn, PreparedStatement ps) {
		fechaConexao(conn);
		try {
			if(ps != null) {
				ps.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void fechaConexao(Connection conn, PreparedStatement ps, ResultSet rs) {
		fechaConexao(conn, ps);
		try {
			if(rs != null) {
				rs.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
