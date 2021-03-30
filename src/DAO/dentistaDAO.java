/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;


import SistemaOdonto.Dentista;
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
public class dentistaDAO {
    private String sql;
    private Conexao conexao;
    ArrayList<Dentista> listaDentAuxDelet= new ArrayList<Dentista>();
    
    
    private static dentistaDAO instance;

    public static dentistaDAO getInstance() {
        if (instance == null) {
            instance = new dentistaDAO();
        }
        return instance;
    }
    
    public static void  salvar(Dentista dentista){
        PreparedStatement ps2;
        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into dentista (nome_dent, sexo, cpf, endereco, data_nasc, especialidade) values (?,?,?,?,?,?)");
            ps2.setString(1, dentista.getNome());
            ps2.setString(2, dentista.getSexo());
            ps2.setString(3, dentista.getCpf());
            ps2.setString(4, dentista.getEndereco());
            ps2.setString(5, dentista.getNasc());
            ps2.setString(6, dentista.getEspecialidade());
            ps2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(dentistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    
    public static void editar(Dentista dentista, String aux) throws SQLException{

        System.out.println("aux "+dentista.getCpf());
        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update dentista set cpf = ?,nome_dent = ?,sexo = ?,cpf = ?,endereco = ?,data_nasc = ?,especialidade = ? Where cpf = ?" );
                    ps.setString(1, dentista.getNome());
                    ps.setString(2, dentista.getSexo());
                    ps.setString(3, dentista.getCpf());
                    ps.setString(4, dentista.getEndereco());
                    ps.setString(5, dentista.getNasc());
                    ps.setString(6, dentista.getEspecialidade());
                    ps.setString(6, aux);
                    ps.executeUpdate();
        
    }
   /*/ pubclic static void excluir (Paciente paciente){
    }     
   /*/ 
  public static void excluir(Dentista dentista) throws SQLException{
  }
  
  
  public static void cancelar(Dentista dentista) throws SQLException{
  }
}
