package edu.nur.edd.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ConexionPostgreSQL {
    private static String URL_CONEXION =
            "jdbc:postgresql://localhost:5432/parcial1";
    private static String USER = "postgres";
    private static String PASSWORD = "root";
    private Connection conexion;

    private static ConexionPostgreSQL instancia;

    public static ConexionPostgreSQL getOrCreate() throws SQLException {
        if (instancia == null)
            instancia = new ConexionPostgreSQL();
        System.out.println("Conexion PosrgreSQL - exitosa");

        return instancia;
    }

    private ConexionPostgreSQL() throws SQLException {
        conexion =
                DriverManager.getConnection(
                        URL_CONEXION, USER, PASSWORD);
    }

    public Connection getConnection(){
        return conexion;
    }

    public ResultSet ejecutarConsulta(String sql) throws SQLException {
        PreparedStatement ps =
                conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet ejecutarInsert(String sql) throws SQLException {
        PreparedStatement ps =
                conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        return ps.getGeneratedKeys();
    }

    public void ejecutarUpdate(String sql) throws SQLException {
        PreparedStatement ps =
                conexion.prepareStatement(sql);
        ps.executeUpdate();
    }

    public void ejecutarDelete(String sql) throws SQLException{
        PreparedStatement ps =
                conexion.prepareStatement(sql);
        ps.executeUpdate();
    }
}
