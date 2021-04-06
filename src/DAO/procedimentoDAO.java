/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Paciente;
import Model.Procedimento;
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
public class ProcedimentoDAO {

    private String sql;
    private Conexao conexao;
    ArrayList<Procedimento> listaProcAuxDelet = new ArrayList<Procedimento>();
    private static ProcedimentoDAO instance;

    public static ProcedimentoDAO getInstance() {
        if (instance == null) {
            instance = new ProcedimentoDAO();
        }
        return instance;
    }

    public static int salvar(Procedimento procedimento) {
        PreparedStatement ps2;
        int idx = 0;

        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into procedimento (descricao_proc, nome_pac) values (?,?)");
            ps2.setString(1, procedimento.getDescricao());
            ps2.setString(2, procedimento.getDep().getNome());
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

    public static void editar(Procedimento procedimento, String aux) throws SQLException {

        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update procedimento SET nome_pac = ?,descricao_proc = ? Where id_proc = ?");
        ps.setString(1, aux);
        ps.setString(2, procedimento.getDescricao());
        ps.setInt(3, procedimento.getId());
        ps.executeUpdate();

    }

    public static void excluir(int id) throws SQLException {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("DELETE FROM procedimento WHERE id_proc = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ResultSet selecionar() {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("SELECT * FROM procedimento");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

}
