/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.PagamentoDAO;
import DAO.ProcedimentoDAO;
import Model.Paciente;
import Model.Pagamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author PICHAU
 */
public class PagamentoController {

    public int salvar(String valor, String forma, Paciente paciente) throws SQLException, ParseException {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(valor);
        pagamento.setForma(forma);
        pagamento.setDep(paciente);

        return PagamentoDAO.getInstance().salvar(pagamento);
    }

    public void editar(int id, String valor, String forma, Paciente paciente) throws SQLException, ParseException {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(valor);
        pagamento.setForma(forma);
        pagamento.setId(id);
        pagamento.setDep(paciente);

        PagamentoDAO.getInstance().editar(pagamento);
    }

    public void deletar(int id) throws SQLException {
        PagamentoDAO.getInstance().excluir(id);

    }

    public ResultSet index() throws SQLException {
        return PagamentoDAO.getInstance().selecionar();
    }
}
