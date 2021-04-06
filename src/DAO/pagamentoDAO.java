/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Paciente;
import Model.Pagamento;
import View.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoDAO {

    private String sql;
    private Conexao conexao;
    ArrayList<Pagamento> listaPagAuxDelet = new ArrayList<Pagamento>();
    private static PagamentoDAO instance;

    public static PagamentoDAO getInstance() {
        if (instance == null) {
            instance = new PagamentoDAO();
        }
        return instance;
    }

    public static int salvar(Pagamento pagamento) {
        PreparedStatement ps2;
        int idx = 0;

        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into pagamento (forma, valor, nome_pac) values (?,?,?)");
            ps2.setString(1, pagamento.getForma());
            ps2.setString(2, pagamento.getValor());
            ps2.setString(3, pagamento.getDep().getNome());
            ps2.executeUpdate();

            ResultSet rs = ps2.getGeneratedKeys();
            if (rs.next()) {
                idx = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PagamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idx;
    }

    public static void editar(Pagamento pagamento) throws SQLException {

        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update pagamento SET forma = ?, nome_pac = ?, valor = ? Where Cod_pag = ?");

        ps.setString(1, pagamento.getForma());
        ps.setString(2, pagamento.getDep().getNome());
        ps.setString(3, pagamento.getValor());
        ps.setInt(4, pagamento.getId());
        ps.executeUpdate();
        //
    }

    public void excluir(int id) {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("DELETE FROM pagamento WHERE Cod_pag = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ResultSet selecionar() {
        try {
            PreparedStatement ps = Conexao.conexao().prepareStatement("SELECT * FROM pagamento");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
