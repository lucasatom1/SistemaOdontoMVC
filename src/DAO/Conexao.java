/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author PICHAU
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Connection conn = null;

    private Conexao() {
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/odonto_db"; 
       // String driver = "org.mariadb.jdbc.Driver";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(url, userName, password);
            System.out.println("conectado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection conexao() {
        if (conn == null) {
             new Conexao();
        }
            return conn;
        }
    }

