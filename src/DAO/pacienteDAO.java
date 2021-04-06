/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Paciente;
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
public class PacienteDAO {

    private String sql;
    private Conexao conexao;
    ArrayList<Paciente> listaPacAuxDelet = new ArrayList<Paciente>();

    private static PacienteDAO instance;

    public static PacienteDAO getInstance() {
        if (instance == null) {
            instance = new PacienteDAO();
        }
        return instance;
    }

    public static int salvar(Paciente paciente) {
        PreparedStatement ps2;
        int idx = 0;

        System.out.println(paciente.getNasc());
        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into paciente (nome_pac, data_nasc, cpf, sexo, endereco) values (?,?,?,?,?)");
            ps2.setString(1, paciente.getNome());
            ps2.setString(2, paciente.getNasc());
            ps2.setString(3, paciente.getCpf());
            ps2.setString(4, paciente.getSexo());
            ps2.setString(5, paciente.getEndereco());
            ps2.executeUpdate();

            ResultSet rs = ps2.getGeneratedKeys();
            if (rs.next()) {
                idx = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idx;
    }

    public static void editar(Paciente paciente) throws SQLException {

        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update paciente set cpf = ?,nome_pac = ?,sexo = ?,endereco = ?,data_nasc = ? Where id_pac = ?");
        ps.setString(1, paciente.getCpf());
        ps.setString(2, paciente.getNome());
        ps.setString(3, paciente.getSexo());
        ps.setString(4, paciente.getEndereco());
        ps.setString(5, paciente.getNasc());
        ps.setInt(6, paciente.getId());
        ps.executeUpdate();

    }

    /*/ pubclic static void excluir (Paciente paciente){
    }     
   /*/
    public static void excluir(Paciente paciente) throws SQLException {
    }

    public static void cancelar(Paciente paciente) throws SQLException {
    }

    public ResultSet selecionar() {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("SELECT * FROM paciente");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void excluir(int id) {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("DELETE FROM paciente WHERE id_pac = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
