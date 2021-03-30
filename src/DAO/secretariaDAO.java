/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import SistemaOdonto.Secretaria;
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
public class secretariaDAO {
    private String sql;
    private Conexao conexao;
    ArrayList<Secretaria> listaSecAuxDelet= new ArrayList<Secretaria>();
    
    
    private static secretariaDAO instance;

    public static secretariaDAO getInstance() {
        if (instance == null) {
            instance = new secretariaDAO();
        }
        return instance;
    }
    
    public static void  salvar(Secretaria secretaria){
        PreparedStatement ps2;
        try {
            ps2 = Conexao.conexao().prepareStatement("Insert into secretaria (nome_sec, cpf, endereco) values (?,?,?)");
            ps2.setString(1, secretaria.getNome());
            ps2.setString(2, secretaria.getCpf());
            ps2.setString(3, secretaria.getEndereco());
            ps2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(secretariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
    
    
    public static void editar(Secretaria secretaria, String aux) throws SQLException{

        System.out.println("aux "+secretaria.getCpf());
        PreparedStatement ps = null;
        ps = Conexao.conexao().prepareStatement("Update secretaria set cpf = ?,nome_sec = ?,endereco = ? Where cpf = ?" );
                    ps.setString(1, secretaria.getCpf());
                    ps.setString(2, secretaria.getNome());
                    ps.setString(3, secretaria.getEndereco());
                    ps.setString(4, aux);
                    ps.executeUpdate();
        
    }
   
  public static void excluir() throws SQLException{
  }
  
  
  public static void cancelar() throws SQLException{
  }
}
