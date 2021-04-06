/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DentistaDAO;
import Model.Dentista;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author PICHAU
 */
public class DentistaController {

    public int salvar(String nome, String sexo, String cpf, String endereco, String nascimento, String especialidade) throws SQLException, ParseException {
        Dentista dentista = new Dentista();
        dentista.setNome(nome);
        dentista.setSexo(sexo);
        dentista.setCpf(cpf);
        dentista.setEndereco(endereco);
        dentista.setNasc(nascimento);
        dentista.setEspecialidade(especialidade);

        return DentistaDAO.getInstance().salvar(dentista);
    }

    public void editar(int id, String nome, String sexo, String cpf, String endereco, String nascimento, String especialidade) throws SQLException, ParseException {
        Dentista dentista = new Dentista();
        dentista.setNome(nome);
        dentista.setSexo(sexo);
        dentista.setCpf(cpf);
        dentista.setEndereco(endereco);
        dentista.setNasc(nascimento);
        dentista.setEspecialidade(especialidade);
        dentista.setId(id);

        DentistaDAO.getInstance().editar(dentista);
    }

    public void deletar(int id) throws SQLException {
        DentistaDAO.getInstance().excluir(id);

    }

   public ResultSet index() throws SQLException {
        return DentistaDAO.getInstance().selecionar();
    }
}
