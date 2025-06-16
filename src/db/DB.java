package db;

import java.sql.*;
import java.util.Properties;

public class DB {

    public static final String URL = "jdbc:postgresql://localhost:5432/coursejdbc";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";

    private static Connection con = null;


    public static Connection getConnection() throws SQLException {
        try{
            if (con == null){
                con = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        }catch(SQLException e){
            throw new SQLException("Erro ao conectar ao Banco de Dados"+ e.getMessage());
        }
        return con;

    }
    public static void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatenebt(Statement stmt) {

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public static void closeResultSet(ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
