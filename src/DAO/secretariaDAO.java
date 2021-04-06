/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Secretaria;
import View.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PICHAU
 */
public class SecretariaDAO {

    private String sql;
    private Conexao conexao;
    ArrayList<Secretaria> listaSecAuxDelet = new ArrayList<Secretaria>();

    private static SecretariaDAO instance;

    public static SecretariaDAO getInstance() {
        if (instance == null) {
            instance = new SecretariaDAO();
        }
        return instance;
    }

    public static int salvar(Secretaria secretaria) {
        PreparedStatement ps2;
        int idx = 0;

        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into secretaria (nome_sec, cpf, endereco) values (?,?,?)");
            ps2.setString(1, secretaria.getNome());
            ps2.setString(2, secretaria.getCpf());
            ps2.setString(3, secretaria.getEndereco());
            ps2.executeUpdate();

            ResultSet rs = ps2.getGeneratedKeys();
            if (rs.next()) {
                idx = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idx;

    }

    public static void editar(Secretaria secretaria) throws SQLException {

        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update secretaria set cpf = ?,nome_sec = ?,endereco = ? Where id_sec = ?");
        ps.setString(1, secretaria.getCpf());
        ps.setString(2, secretaria.getNome());
        ps.setString(3, secretaria.getEndereco());
        ps.setInt(4, secretaria.getId());
        ps.executeUpdate();

    }



    public ResultSet selecionar() {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("SELECT * FROM secretaria");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void excluir(int id) {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("DELETE FROM secretaria WHERE id_sec = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
