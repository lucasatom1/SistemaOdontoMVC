/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Dentista;
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
public class DentistaDAO {

    private String sql;
    private Conexao conexao;
    ArrayList<Dentista> listaDentAuxDelet = new ArrayList<Dentista>();

    private static DentistaDAO instance;

    public static DentistaDAO getInstance() {
        if (instance == null) {
            instance = new DentistaDAO();
        }
        return instance;
    }

    public static int salvar(Dentista dentista) {
        PreparedStatement ps2;
        int idx = 0;
        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into dentista (nome_dent, sexo, cpf, endereco, data_nasc, especialidade) values (?,?,?,?,?,?)");
            ps2.setString(1, dentista.getNome());
            ps2.setString(2, dentista.getSexo());
            ps2.setString(3, dentista.getCpf());
            ps2.setString(4, dentista.getEndereco());
            ps2.setString(5, dentista.getNasc());
            ps2.setString(6, dentista.getEspecialidade());
            ps2.executeUpdate();
            ResultSet rs = ps2.getGeneratedKeys();
            if (rs.next()) {
                idx = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DentistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idx;
    }

    public static void editar(Dentista dentista) throws SQLException {

        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update dentista set cpf = ?,nome_dent = ?,sexo = ?,cpf = ?,endereco = ?,data_nasc = ?,especialidade = ? Where id_dent = ?");
        ps.setString(1, dentista.getCpf());
        ps.setString(2, dentista.getNome());
        ps.setString(3, dentista.getSexo());
        ps.setString(4, dentista.getCpf());
        ps.setString(5, dentista.getEndereco());
        ps.setString(6, dentista.getNasc());
        ps.setString(7, dentista.getEspecialidade());
        ps.setInt(8, dentista.getId());
        ps.executeUpdate();

    }

    public void excluir(int id) {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("DELETE FROM dentista WHERE id_dent = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ResultSet selecionar() {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("SELECT * FROM dentista");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

}
