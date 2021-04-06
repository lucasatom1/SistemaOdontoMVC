/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ProcedimentoDAO;
import Model.Paciente;
import Model.Procedimento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author PICHAU
 */
public class ProcedimentoController {

    public int salvar(String descricao, Paciente paciente) throws SQLException, ParseException {
        Procedimento pagamento = new Procedimento();
        pagamento.setDescricao(descricao);
        pagamento.setDep(paciente);

        return ProcedimentoDAO.getInstance().salvar(pagamento);
    }

    public void editar(int id, String descricao, String paciente) throws SQLException, ParseException {
        Procedimento procedimento = new Procedimento();
        procedimento.setDescricao(descricao);
        procedimento.setId(id);
        ProcedimentoDAO.getInstance().editar(procedimento, paciente);
    }

    public void deletar(int id) throws SQLException {
        ProcedimentoDAO.getInstance().excluir(id);

    }

    public ResultSet index() throws SQLException {
        return ProcedimentoDAO.getInstance().selecionar();
    }
}
