/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DentistaDAO;
import DAO.SecretariaDAO;
import Model.Secretaria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author PICHAU
 */
public class SecretariaController {

    public int salvar(String nome, String cpf, String endereco) throws SQLException, ParseException {
        Secretaria secretaria = new Secretaria(0, nome, cpf, endereco);

        return SecretariaDAO.getInstance().salvar(secretaria);
    }

    public void editar(int id, String nome, String cpf, String endereco) throws SQLException, ParseException {
        Secretaria secretaria = new Secretaria(id, nome, cpf, endereco);
        SecretariaDAO.getInstance().editar(secretaria);
    }

    public void deletar(int id) throws SQLException {
        SecretariaDAO.getInstance().excluir(id);

    }

    public ResultSet index() throws SQLException {
        return SecretariaDAO.getInstance().selecionar();
    }
}
