/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import SistemaOdonto.Paciente;
import SistemaOdonto.Pagamento;
import SistemaOdonto.Principal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class pagamentoDAO {
    private String sql;
    private Conexao conexao;
    ArrayList<Pagamento> listaPagAuxDelet= new ArrayList<Pagamento>();
    private static pagamentoDAO instance;

    public static pagamentoDAO getInstance() {
        if (instance == null) {
            instance = new pagamentoDAO();
        }
        return instance;
    }
    
    public static void  salvar(Pagamento pagamento){
        PreparedStatement ps2;
        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into procedimento (forma, valor, nome_pac) values (?,?,?)");
            ps2.setString(1, pagamento.getForma());
            ps2.setString(2, pagamento.getValor());
            ps2.setString(3, pagamento.getDep().getNome());
            ps2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(pagamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    
    public static void editar(Pagamento pagamento, Paciente paciente, String aux) throws SQLException{

        System.out.println("aux "+paciente.getNome());
        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update procedimento nome_pac = ?,forma = ?, valor = ? Where nome = ?" );
                    ps.setString(1, pagamento.getDep().getNome()); 
                    ps.setString(2, pagamento.getForma());
                    ps.setString(3, pagamento.getValor());
                    ps.setString(4, aux);
                    ps.executeUpdate();
        
    }
   
  public static void excluir() throws SQLException{
  }
  
  
  public static void cancelar() throws SQLException{
  }
}