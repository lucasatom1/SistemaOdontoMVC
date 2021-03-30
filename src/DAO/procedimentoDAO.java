/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import SistemaOdonto.Paciente;
import SistemaOdonto.Procedimento;
import SistemaOdonto.Principal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PICHAU
 */
public class procedimentoDAO {
    private String sql;
    private Conexao conexao;
    ArrayList<Procedimento> listaProcAuxDelet= new ArrayList<Procedimento>();
    private static procedimentoDAO instance;

    public static procedimentoDAO getInstance() {
        if (instance == null) {
            instance = new procedimentoDAO();
        }
        return instance;
    }
    
    public static void  salvar(Procedimento procedimento){
        PreparedStatement ps2;
        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into procedimento (descricao_proc, nome_pac) values (?,?)");
            ps2.setString(1, procedimento.getDescricao());
            ps2.setString(2, procedimento.getDep().getNome());
            ps2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(secretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    
    public static void editar(Procedimento procedimento, Paciente paciente, String aux) throws SQLException{

        System.out.println("aux "+paciente.getNome());
        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update procedimento nome_pac = ?,descricao_proc = ? Where nome = ?" );
                    ps.setString(1, procedimento.getDep().getNome()); 
                    ps.setString(1, procedimento.getDescricao());
                    ps.setString(2, aux);
                    ps.executeUpdate();
        
    }
   
  public static void excluir() throws SQLException{
  }
  
  
  public static void cancelar() throws SQLException{
  }
}
