/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.PacienteDAO;
import Model.Paciente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author PICHAU
 */
public class PacienteController {
    public int salvar(String nome, String sexo, String cpf, String endereco, String nascimento) throws SQLException, ParseException {
        Paciente paciente = new Paciente();
        paciente.setNome(nome);
        paciente.setSexo(sexo);
        paciente.setCpf(cpf);
        paciente.setEndereco(endereco);
        paciente.setNasc(nascimento);

        return PacienteDAO.getInstance().salvar(paciente);
    }

    public void editar(int id, String nome, String sexo, String cpf, String endereco, String nascimento) throws SQLException, ParseException {
        Paciente paciente = new Paciente();
        paciente.setNome(nome);
        paciente.setSexo(sexo);
        paciente.setCpf(cpf);
        paciente.setEndereco(endereco);
        paciente.setNasc(nascimento); 
        paciente.setId(id);

         PacienteDAO.getInstance().editar(paciente);
    }

    public void deletar(int id) throws SQLException {
        PacienteDAO.getInstance().excluir(id);

    }
 
    public ResultSet selecionar() throws SQLException {
        return PacienteDAO.getInstance().selecionar();
    }

     public ResultSet index() throws SQLException {
        return PacienteDAO.getInstance().selecionar();
    }
}
