/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import SistemaOdonto.Paciente;
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
public class pacienteDAO {
    private String sql;
    private Conexao conexao;
    ArrayList<Paciente> listaPacAuxDelet= new ArrayList<Paciente>();
    
    
    private static pacienteDAO instance;

    public static pacienteDAO getInstance() {
        if (instance == null) {
            instance = new pacienteDAO();
        }
        return instance;
    }
    
    public static void  salvar(Paciente paciente){
        PreparedStatement ps2;
        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into paciente (nome_pac, data_nasc, cpf, sexo, endereco) values (?,?,?,?,?)");
            ps2.setString(1, paciente.getNome());
            ps2.setString(3, paciente.getCpf());
            ps2.setString(4, paciente.getSexo());
            ps2.setString(2, paciente.getNasc());
            ps2.setString(5, paciente.getEndereco());
            ps2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(pacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    
    public static void editar(Paciente paciente, String aux) throws SQLException{

        System.out.println("aux "+paciente.getCpf());
        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update paciente set cpf = ?,nome_pac = ?,sexo = ?,endereco = ?,data_nasc = ? Where cpf = ?" );
                    ps.setString(1, paciente.getCpf());
                    ps.setString(2, paciente.getNome());
                    ps.setString(3, paciente.getSexo());
                    ps.setString(4, paciente.getEndereco());
                    ps.setString(5, paciente.getNasc());
                    ps.setString(6, aux);
                    ps.executeUpdate();
        
    }
   /*/ pubclic static void excluir (Paciente paciente){
    }     
   /*/ 
  public static void excluir(Paciente paciente) throws SQLException{
  }
  
  
  public static void cancelar(Paciente paciente) throws SQLException{
  }
}
    

