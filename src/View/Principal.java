package View;

import Controller.DentistaController;
import Controller.PacienteController;
import Controller.PagamentoController;
import Controller.ProcedimentoController;
import Controller.SecretariaController;
import Model.Secretaria;
import Model.Procedimento;
import Model.Pagamento;
import Model.Dentista;
import Model.Paciente;
import DAO.Conexao;
import DAO.DentistaDAO;
import DAO.PacienteDAO;
import DAO.PagamentoDAO;
import DAO.ProcedimentoDAO;
import DAO.SecretariaDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Principal extends javax.swing.JFrame {

    ArrayList<Paciente> listaPac;
    ArrayList<Procedimento> listaProc;
    ArrayList<Dentista> listaDent;
    ArrayList<Secretaria> listaSec;
    ArrayList<Pagamento> listaPag;
    ArrayList<Paciente> listaPacAuxDelet = new ArrayList<Paciente>();
    ArrayList<Procedimento> listaProcAuxDelet = new ArrayList<Procedimento>();
    ArrayList<Secretaria> listaSecAuxDelet = new ArrayList<Secretaria>();
    ArrayList<Dentista> listaDentAuxDelet = new ArrayList<Dentista>();
    ArrayList<Pagamento> listaPagAuxDelet = new ArrayList<Pagamento>();

    DefaultTableModel modeloPaciente = new DefaultTableModel(new Object[]{"id", "Nome", "CPF", "Endereço"}, 0);
    DefaultTableModel modeloDentista = new DefaultTableModel(new Object[]{"id", "Nome", "Cpf", "Sexo", "Endereco", "Nascimento", "Especialidade"}, 0);
    DefaultTableModel modeloSecretaria = new DefaultTableModel(new Object[]{"id", "Nome", "Cpf", "Endereco"}, 0);
    DefaultTableModel modeloProcedimento = new DefaultTableModel(new Object[]{"id", "Descricao", "NomePaciente"}, 0);
    DefaultTableModel modeloPagamento = new DefaultTableModel(new Object[]{"id", "Forma", "Valor", "Paciente"}, 0);

    String modoPac;
    String modoProc;
    String modoDent;
    String modoSec;
    String modoPag;
    Conexao conn;

    public ArrayList returnListaPacAuxDelet() {
        return listaPacAuxDelet;
    }

    public void updateLocalPacienteTable(Paciente paciente) {
        //pega o antigo e adiciona esse novo
        Object linha[] = new Object[]{
            paciente.getId(),
            paciente.getNome(),
            paciente.getCpf(),
            paciente.getSexo(),
            paciente.getEndereco(),
            paciente.getNasc()};

        modeloPaciente.addRow(linha);

        tbl_pac.setModel(modeloPaciente);

    }

    public void updateLocalProcedimentoTable(Procedimento procedimento) {

        Object linha[] = new Object[]{
            procedimento.getId(),
            procedimento.getDescricao(),
            procedimento.getDep().getNome()};

        modeloProcedimento.addRow(linha);

        tbl_proc.setModel(modeloProcedimento);
    }

    public void LoadCBPac() throws SQLException {

        PacienteController controller = new PacienteController();
        ResultSet data = controller.index();

        while (data.next()) {
            Paciente D = new Paciente(data.getInt("id_pac"), data.getString("nome_pac"), data.getString("cpf"), data.getString("sexo"), data.getString("endereco"), data.getString("data_nasc"));
            listaPac.add(D);
        }

        cb_paciente.removeAllItems();
        cb_paciente.addItem("Selecione");
        cb_pac1.removeAllItems();
        cb_pac1.addItem("Selecione");
        for (int i = 0; i < listaPac.size(); i++) {
            cb_paciente.addItem(listaPac.get(i).getNome());
            cb_pac1.addItem(listaPac.get(i).getNome());
        }
    }

    public void updateLocalDentistaTable(Dentista dentista) {

        Object linha[] = new Object[]{
            dentista.getId(),
            dentista.getNome(),
            dentista.getSexo(),
            dentista.getCpf(),
            dentista.getEndereco(),
            dentista.getNasc(),
            dentista.getEspecialidade()};

        modeloDentista.addRow(linha);

        tbl_dent.setModel(modeloDentista);

    }

    public void updateLocalSecretariaTable(Secretaria secretaria) {

        Object linha[] = new Object[]{
            secretaria.getId(),
            secretaria.getNome(),
            secretaria.getCpf(),
            secretaria.getEndereco()};

        modeloSecretaria.addRow(linha);

        tbl_sec.setModel(modeloSecretaria);

    }

    public void updateLocalPagamentoTable(Pagamento pagamento) {

        Object linha[] = new Object[]{
            pagamento.getId(),
            pagamento.getForma(),
            pagamento.getValor(),
            pagamento.getDep().getNome()};

        modeloPagamento.addRow(linha);

        tbl_pag.setModel(modeloPagamento);

    }

    /**
     * Creates new form Principal
     */
    public Principal() throws SQLException {
        listaPac = new ArrayList();
        listaProc = new ArrayList();
        listaDent = new ArrayList();
        listaSec = new ArrayList();
        listaPag = new ArrayList();
        initComponents();
        initTableData();
        setLocationRelativeTo(null);

        modoPac = "Navegar";
        modoProc = "Navegar";
        modoDent = "Navegar";
        modoSec = "Navegar";
        modoPag = "Navegar";
        ManipulaInterfacePac();
        ManipulaInterfaceProc();
        ManipulaInterfaceDent();
        ManipulaInterfaceSec();
        ManipulaInterfacePag();
    }

    public void initTableData() throws SQLException {

        initDataPaciente();
        initDataDentista();
        initDataSecretaria();
        initDataProcedimento();
        initDataPagamento();
        LoadCBPac();
    }

    public void initDataPaciente() throws SQLException {
        PacienteController controller = new PacienteController();

        //LOAD DATA PACIENTE
        ResultSet data = controller.index();

        while (data.next()) {
            modeloPaciente.addRow(new Object[]{data.getInt("id_pac"), data.getString("nome_pac"), data.getString("cpf"), data.getString("endereco")});
            Paciente paciente = new Paciente(data.getInt("id_pac"), data.getString("nome_pac"), data.getString("cpf"), data.getString("sexo"), data.getString("endereco"));
            listaPac.add(paciente);
        }

        tbl_pac.setModel(modeloPaciente);
    }

    public void initDataDentista() throws SQLException {
        DentistaController controller = new DentistaController();

        //LOAD DATA DENTISTA
        ResultSet data = controller.index();

        while (data.next()) {
            modeloDentista.addRow(new Object[]{data.getInt("id_dent"), data.getString("nome_dent"), data.getString("sexo"), data.getString("cpf"), data.getString("endereco"), data.getString("data_nasc"), data.getString("especialidade")});
            Dentista dentista = new Dentista(data.getInt("id_dent"), data.getString("nome_dent"), data.getString("sexo"), data.getString("cpf"), data.getString("endereco"), data.getString("data_nasc"), data.getString("especialidade"));
            listaDent.add(dentista);
        }

        tbl_dent.setModel(modeloDentista);
    }

    public void initDataSecretaria() throws SQLException {
        SecretariaController controller = new SecretariaController();

        //LOAD DATA SECRETARIA
        ResultSet data = controller.index();

        while (data.next()) {
            modeloSecretaria.addRow(new Object[]{data.getInt("id_sec"), data.getString("nome_sec"), data.getString("cpf"), data.getString("endereco")});
            Secretaria secretaria = new Secretaria(data.getInt("id_sec"), data.getString("nome_sec"), data.getString("cpf"), data.getString("endereco"));
            listaSec.add(secretaria);
        }

        tbl_sec.setModel(modeloSecretaria);
    }

    public void initDataProcedimento() throws SQLException {
        ProcedimentoController controller = new ProcedimentoController();

        //LOAD DATA SECRETARIA
        ResultSet data = controller.index();

        while (data.next()) {
            modeloProcedimento.addRow(new Object[]{data.getInt("id_proc"), data.getString("descricao_proc"), data.getString("nome_pac")});
            Procedimento procedimento = new Procedimento(data.getInt("id_proc"), data.getString("descricao_proc"), data.getString("nome_pac"));
            listaProc.add(procedimento);
        }

        tbl_proc.setModel(modeloProcedimento);
    }

    public void initDataPagamento() throws SQLException {
        PagamentoController controller = new PagamentoController();

        //LOAD DATA SECRETARIA
        ResultSet data = controller.index();

        while (data.next()) {
            modeloPagamento.addRow(new Object[]{data.getInt("Cod_pag"), data.getString("forma"), data.getString("valor"), data.getString("nome_pac")});
            Pagamento pagamento = new Pagamento(data.getInt("Cod_pag"), data.getString("forma"), data.getString("valor"));
            listaPag.add(pagamento);
        }

        tbl_pag.setModel(modeloPagamento);
    }

    public void ManipulaInterfacePac() {
        switch (modoPac) {
            case "Navegar":
                btn_pac_salvar.setEnabled(false);
                btn_pac_cancelar.setEnabled(false);
                //       c_pac_codigo.setEnabled(false);
                c_pac_nome.setEnabled(false);
                c_pac_cpf.setEnabled(false);
                c_pac_sexo.setEnabled(false);
                c_pac_endereco.setEnabled(false);
                c_pac_nasc.setEnabled(false);
                btn_pac_novo.setEnabled(true);
                btn_pac_editar.setEnabled(false);
                btn_pac_excluir.setEnabled(false);
                break;

            case "Novo":
                btn_pac_salvar.setEnabled(true);
                btn_pac_cancelar.setEnabled(true);
                //      c_pac_codigo.setEnabled(true);
                c_pac_nome.setEnabled(true);
                c_pac_cpf.setEnabled(true);
                c_pac_endereco.setEnabled(true);
                c_pac_sexo.setEnabled(true);
                c_pac_nasc.setEnabled(true);
                btn_pac_novo.setEnabled(false);
                btn_pac_editar.setEnabled(false);
                btn_pac_excluir.setEnabled(false);
                break;

            case "Editar":
                btn_pac_salvar.setEnabled(false);
                btn_pac_cancelar.setEnabled(false);
                //     c_pac_codigo.setEnabled(true);
                c_pac_nome.setEnabled(true);
                c_pac_cpf.setEnabled(false);
                c_pac_sexo.setEnabled(true);
                c_pac_endereco.setEnabled(true);
                c_pac_nasc.setEnabled(true);
                btn_pac_novo.setEnabled(true);
                btn_pac_editar.setEnabled(true);
                btn_pac_excluir.setEnabled(true);

                /*/  btn_pac_salvar.setEnabled(true);
                btn_pac_cancelar.setEnabled(true);
                c_pac_codigo.setEnabled(true);
                c_pac_nome.setEnabled(true);
                c_pac_cpf.setEnabled(true);
                c_pac_sexo.setEnabled(true);
                c_pac_endereco.setEnabled(true);
                c_pac_nasc.setEnabled(true);
                btn_pac_novo.setEnabled(true);
                btn_pac_editar.setEnabled(true);
               btn_pac_excluir.setEnabled(false); /*/
                break;

            case "Excluir":
                btn_pac_salvar.setEnabled(false);
                btn_pac_cancelar.setEnabled(false);
                //    c_pac_codigo.setEnabled(false);
                c_pac_nome.setEnabled(false);
                c_pac_cpf.setEnabled(false);
                c_pac_endereco.setEnabled(false);
                c_pac_sexo.setEnabled(false);
                c_pac_nasc.setEnabled(false);
                btn_pac_novo.setEnabled(true);
                btn_pac_editar.setEnabled(false);
                btn_pac_excluir.setEnabled(false);
                break;

            case "Selecao":
                btn_pac_salvar.setEnabled(false);
                btn_pac_cancelar.setEnabled(false);
                //    c_pac_codigo.setEnabled(false);
                c_pac_nome.setEnabled(false);
                c_pac_cpf.setEnabled(false);
                c_pac_endereco.setEnabled(false);
                c_pac_sexo.setEnabled(false);
                c_pac_nasc.setEnabled(false);
                btn_pac_novo.setEnabled(true);
                btn_pac_editar.setEnabled(true);
                btn_pac_excluir.setEnabled(true);
                break;
            case "Cancelar":
                btn_pac_salvar.setEnabled(false);
                btn_pac_cancelar.setEnabled(false);
                //     c_pac_codigo.setEnabled(false);
                c_pac_nome.setEnabled(false);
                c_pac_cpf.setEnabled(false);
                c_pac_endereco.setEnabled(false);
                c_pac_sexo.setEnabled(false);
                c_pac_nasc.setEnabled(false);
                btn_pac_novo.setEnabled(true);
                btn_pac_editar.setEnabled(true);
                btn_pac_excluir.setEnabled(true);
                break;
            default:
                System.out.println("Modo inválido");
        }
    }

    public void ManipulaInterfaceProc() {
        switch (modoProc) {
            case "Navegar":
                btn_proc_salvar.setEnabled(false);
                btn_proc_cancelar.setEnabled(false);
                //      c_proc_cod.setEnabled(false);
                c_desc_proc.setEnabled(false);
                btn_proc_novo.setEnabled(true);
                btn_proc_editar.setEnabled(false);
                btn_proc_excluir.setEnabled(false);
                cb_paciente.setEnabled(false);
                break;

            case "Novo":
                btn_proc_salvar.setEnabled(true);
                btn_proc_cancelar.setEnabled(true);
                //     c_proc_cod.setEnabled(true);
                c_desc_proc.setEnabled(true);
                cb_paciente.setEnabled(true);
                btn_proc_novo.setEnabled(false);
                btn_proc_editar.setEnabled(false);
                btn_proc_excluir.setEnabled(false);
                break;

            case "Editar":
                btn_proc_salvar.setEnabled(true);
                btn_proc_cancelar.setEnabled(true);
                //     c_proc_cod.setEnabled(true);
                c_desc_proc.setEnabled(true);
                cb_paciente.setEnabled(true);
                btn_proc_novo.setEnabled(true);
                btn_proc_editar.setEnabled(true);
                btn_proc_excluir.setEnabled(true);
                break;

            case "Excluir":
                btn_proc_salvar.setEnabled(false);
                btn_proc_cancelar.setEnabled(false);
                //     c_proc_cod.setEnabled(false);
                c_desc_proc.setEnabled(false);
                cb_paciente.setEnabled(false);
                btn_proc_novo.setEnabled(true);
                btn_proc_editar.setEnabled(false);
                btn_proc_excluir.setEnabled(false);
                break;

            case "Selecao":
                btn_proc_salvar.setEnabled(false);
                btn_proc_cancelar.setEnabled(false);
                //     c_proc_cod.setEnabled(false);
                c_desc_proc.setEnabled(false);
                cb_paciente.setEnabled(false);
                btn_proc_novo.setEnabled(true);
                btn_proc_editar.setEnabled(true);
                btn_proc_excluir.setEnabled(true);
                break;
            default:
                System.out.println("Modo inválido");
        }
    }

    public void ManipulaInterfaceDent() {
        switch (modoDent) {
            case "Navegar":
                btn_dent_salvar.setEnabled(false);
                btn_dent_cancela.setEnabled(false);
                //       c_dent_codigo.setEnabled(false);
                c_dent_nome.setEnabled(false);
                c_dent_cpf.setEnabled(false);
                c_dent_sexo.setEnabled(false);
                c_dent_endereco.setEnabled(false);
                c_dent_nasc.setEnabled(false);
                c_dent_esp.setEnabled(false);
                btn_dent_novo.setEnabled(true);
                btn_dent_edit.setEnabled(false);
                btn_dent_exc.setEnabled(false);
                break;

            case "Novo":
                btn_dent_salvar.setEnabled(true);
                btn_dent_cancela.setEnabled(true);
                //     c_dent_codigo.setEnabled(true);
                c_dent_nome.setEnabled(true);
                c_dent_cpf.setEnabled(true);
                c_dent_sexo.setEnabled(true);
                c_dent_endereco.setEnabled(true);
                c_dent_nasc.setEnabled(true);
                c_dent_esp.setEnabled(true);
                btn_dent_novo.setEnabled(false);
                btn_dent_edit.setEnabled(false);
                btn_dent_exc.setEnabled(false);
                break;

            case "Editar":
                btn_dent_salvar.setEnabled(true);
                btn_dent_cancela.setEnabled(true);
                //     c_dent_codigo.setEnabled(true);
                c_dent_nome.setEnabled(true);
                c_dent_cpf.setEnabled(false);
                c_dent_sexo.setEnabled(true);
                c_dent_endereco.setEnabled(true);
                c_dent_nasc.setEnabled(true);
                c_dent_esp.setEnabled(true);
                btn_dent_novo.setEnabled(true);
                btn_dent_edit.setEnabled(true);
                btn_dent_exc.setEnabled(true);
                break;

            case "Excluir":
                btn_dent_salvar.setEnabled(false);
                btn_dent_cancela.setEnabled(false);
                //      c_dent_codigo.setEnabled(false);
                c_dent_nome.setEnabled(false);
                c_dent_cpf.setEnabled(false);
                c_dent_sexo.setEnabled(false);
                c_dent_endereco.setEnabled(false);
                c_dent_nasc.setEnabled(false);
                c_dent_esp.setEnabled(false);
                btn_dent_novo.setEnabled(true);
                btn_dent_edit.setEnabled(false);
                btn_dent_exc.setEnabled(false);
                break;

            case "Selecao":
                btn_dent_salvar.setEnabled(false);
                btn_dent_cancela.setEnabled(false);
                //      c_dent_codigo.setEnabled(false);
                c_dent_nome.setEnabled(false);
                c_dent_cpf.setEnabled(false);
                c_dent_sexo.setEnabled(false);
                c_dent_endereco.setEnabled(false);
                c_dent_nasc.setEnabled(false);
                c_dent_esp.setEnabled(false);
                btn_dent_novo.setEnabled(true);
                btn_dent_edit.setEnabled(true);
                btn_dent_exc.setEnabled(true);
                break;
            case "Cancelar":
                btn_dent_salvar.setEnabled(false);
                btn_dent_cancela.setEnabled(false);
                //     c_dent_codigo.setEnabled(false);
                c_dent_nome.setEnabled(false);
                c_dent_cpf.setEnabled(false);
                c_dent_sexo.setEnabled(false);
                c_dent_endereco.setEnabled(false);
                c_dent_nasc.setEnabled(false);
                c_dent_esp.setEnabled(false);
                btn_dent_novo.setEnabled(true);
                btn_dent_edit.setEnabled(true);
                btn_dent_exc.setEnabled(true);
                break;
            default:
                System.out.println("Modo inválido");
        }
    }

    public void ManipulaInterfaceSec() {
        switch (modoSec) {
            case "Navegar":
                btn_sec_salvar.setEnabled(false);
                btn_sec_cancelar.setEnabled(false);
                //   c_sec_cod.setEnabled(false);
                c_sec_nome.setEnabled(false);
                c_sec_cpf.setEnabled(false);
                c_sec_endereco.setEnabled(false);
                btn_sec_novo.setEnabled(true);
                btn_sec_edit.setEnabled(false);
                btn_sec_exc.setEnabled(false);
                break;

            case "Novo":
                btn_sec_salvar.setEnabled(true);
                btn_sec_cancelar.setEnabled(true);
                //     c_sec_cod.setEnabled(true);
                c_sec_nome.setEnabled(true);
                c_sec_cpf.setEnabled(true);
                c_sec_endereco.setEnabled(true);
                btn_sec_novo.setEnabled(false);
                btn_sec_edit.setEnabled(false);
                btn_sec_exc.setEnabled(false);
                break;

            case "Editar":
                btn_sec_salvar.setEnabled(false);
                btn_sec_cancelar.setEnabled(false);
                //    c_sec_cod.setEnabled(true);
                c_sec_nome.setEnabled(true);
                c_sec_cpf.setEnabled(false);
                c_sec_endereco.setEnabled(true);
                btn_sec_novo.setEnabled(true);
                btn_sec_edit.setEnabled(true);
                btn_sec_exc.setEnabled(true);
                break;

            case "Excluir":
                btn_sec_salvar.setEnabled(false);
                btn_sec_cancelar.setEnabled(false);
                //     c_sec_cod.setEnabled(false);
                c_sec_nome.setEnabled(false);
                c_sec_cpf.setEnabled(false);
                c_sec_endereco.setEnabled(false);
                btn_sec_novo.setEnabled(true);
                btn_sec_edit.setEnabled(false);
                btn_sec_exc.setEnabled(false);
                break;

            case "Selecao":
                btn_sec_salvar.setEnabled(false);
                btn_sec_cancelar.setEnabled(false);
                //     c_sec_cod.setEnabled(false);
                c_sec_nome.setEnabled(false);
                c_sec_cpf.setEnabled(false);
                c_sec_endereco.setEnabled(false);
                btn_sec_novo.setEnabled(true);
                btn_sec_edit.setEnabled(true);
                btn_sec_exc.setEnabled(true);
                break;
            case "Cancelar":
                btn_sec_salvar.setEnabled(false);
                btn_sec_cancelar.setEnabled(false);
                //     c_sec_cod.setEnabled(false);
                c_sec_nome.setEnabled(false);
                c_sec_cpf.setEnabled(false);
                c_sec_endereco.setEnabled(false);
                btn_sec_novo.setEnabled(true);
                btn_sec_edit.setEnabled(true);
                btn_sec_exc.setEnabled(true);
                break;
            default:
                System.out.println("Modo inválido");
        }
    }

    public void ManipulaInterfacePag() {
        switch (modoPag) {
            case "Navegar":
                btn_pag_salvar.setEnabled(false);
                btn_pag_cancelar.setEnabled(false);
                //   c_pag_cod.setEnabled(false);
                c_pag_form.setEnabled(false);
                c_pag_valor.setEnabled(false);
                cb_pac1.setEnabled(false);
                btn_pag_novo.setEnabled(true);
                btn_pag_editar.setEnabled(false);
                btn_pag_exc.setEnabled(false);
                break;

            case "Novo":
                btn_pag_salvar.setEnabled(true);
                btn_pag_cancelar.setEnabled(true);
                //      c_pag_cod.setEnabled(true);
                c_pag_form.setEnabled(true);
                c_pag_valor.setEnabled(true);
                cb_pac1.setEnabled(true);
                btn_pag_novo.setEnabled(false);
                btn_pag_editar.setEnabled(false);
                btn_pag_exc.setEnabled(false);
                break;

            case "Editar":
                btn_pag_salvar.setEnabled(true);
                btn_pag_cancelar.setEnabled(true);
                //    c_pag_cod.setEnabled(true);
                c_pag_form.setEnabled(true);
                c_pag_valor.setEnabled(true);
                cb_pac1.setEnabled(true);
                btn_pag_novo.setEnabled(true);
                btn_pag_editar.setEnabled(true);
                btn_pag_exc.setEnabled(false);
                break;

            case "Excluir":
                btn_pag_salvar.setEnabled(false);
                btn_pag_cancelar.setEnabled(false);
                //     c_pag_cod.setEnabled(false);
                c_pag_form.setEnabled(false);
                c_pag_valor.setEnabled(false);
                cb_pac1.setEnabled(false);
                btn_pag_novo.setEnabled(true);
                btn_pag_editar.setEnabled(false);
                btn_pag_exc.setEnabled(false);
                break;

            case "Selecao":
                btn_pag_salvar.setEnabled(false);
                btn_pag_cancelar.setEnabled(false);
                //     c_pag_cod.setEnabled(false);
                c_pag_form.setEnabled(false);
                c_pag_valor.setEnabled(false);
                cb_pac1.setEnabled(false);
                btn_pag_novo.setEnabled(true);
                btn_pag_editar.setEnabled(true);
                btn_pag_exc.setEnabled(true);
                break;
            case "Cancelar":
                btn_pag_salvar.setEnabled(false);
                btn_pag_cancelar.setEnabled(false);
                //    c_pag_cod.setEnabled(false);
                c_pag_form.setEnabled(false);
                c_pag_valor.setEnabled(false);
                cb_pac1.setEnabled(false);
                btn_pag_novo.setEnabled(true);
                btn_pag_editar.setEnabled(true);
                btn_pag_exc.setEnabled(true);
                break;

            default:
                System.out.println("Modo inválido");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pac = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        c_pac_nome = new javax.swing.JTextField();
        btn_pac_salvar = new javax.swing.JButton();
        btn_pac_cancelar = new javax.swing.JButton();
        btn_pac_novo = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        c_pac_cpf = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        c_pac_sexo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        c_pac_endereco = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        c_pac_nasc = new javax.swing.JTextField();
        btn_pac_editar = new javax.swing.JButton();
        btn_pac_excluir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btn_proc_editar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_proc = new javax.swing.JTable();
        btn_proc_excluir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        c_desc_proc = new javax.swing.JTextField();
        btn_proc_salvar = new javax.swing.JButton();
        btn_proc_cancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cb_paciente = new javax.swing.JComboBox();
        btn_proc_novo = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        c_dent_nome = new javax.swing.JTextField();
        btn_dent_salvar = new javax.swing.JButton();
        btn_dent_cancela = new javax.swing.JButton();
        btn_dent_novo = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        c_dent_cpf = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        c_dent_sexo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        c_dent_endereco = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        c_dent_nasc = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        c_dent_esp = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_dent = new javax.swing.JTable();
        btn_dent_edit = new javax.swing.JButton();
        btn_dent_exc = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        c_sec_nome = new javax.swing.JTextField();
        btn_sec_salvar = new javax.swing.JButton();
        btn_sec_cancelar = new javax.swing.JButton();
        btn_sec_novo = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        c_sec_cpf = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        c_sec_endereco = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_sec = new javax.swing.JTable();
        btn_sec_edit = new javax.swing.JButton();
        btn_sec_exc = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        c_pag_form = new javax.swing.JTextField();
        btn_pag_salvar = new javax.swing.JButton();
        btn_pag_cancelar = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        cb_pac1 = new javax.swing.JComboBox();
        btn_pag_novo = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        c_pag_valor = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_pag = new javax.swing.JTable();
        btn_pag_editar = new javax.swing.JButton();
        btn_pag_exc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbl_pac.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "CPF", "Sexo", "Endereço", "Nascimento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_pac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbl_pacFocusGained(evt);
            }
        });
        tbl_pac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pacMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_pac);
        if (tbl_pac.getColumnModel().getColumnCount() > 0) {
            tbl_pac.getColumnModel().getColumn(0).setResizable(false);
            tbl_pac.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbl_pac.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Paciente"));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel2.setText("Nome:");

        c_pac_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_pac_nomeActionPerformed(evt);
            }
        });

        btn_pac_salvar.setText("Salvar");
        btn_pac_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pac_salvarActionPerformed(evt);
            }
        });

        btn_pac_cancelar.setText("Cancelar");
        btn_pac_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pac_cancelarActionPerformed(evt);
            }
        });

        btn_pac_novo.setText("Novo");
        btn_pac_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pac_novoActionPerformed(evt);
            }
        });

        jLabel6.setText("CPF:");

        c_pac_cpf.setText("   .   .   -");

        jLabel7.setText("Sexo :");

        c_pac_sexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_pac_sexoActionPerformed(evt);
            }
        });

        jLabel8.setText("Endereço :");

        jLabel9.setText("Dat. de Nasc :");

        c_pac_nasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_pac_nascActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_pac_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_pac_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_pac_cancelar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel2))
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(c_pac_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(c_pac_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(c_pac_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(c_pac_nasc))
                                .addComponent(c_pac_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(376, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(c_pac_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_pac_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(c_pac_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(c_pac_nasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_pac_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_pac_novo)
                    .addComponent(btn_pac_salvar)
                    .addComponent(btn_pac_cancelar))
                .addContainerGap())
        );

        btn_pac_editar.setText("Editar");
        btn_pac_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pac_editarActionPerformed(evt);
            }
        });

        btn_pac_excluir.setText("Excluir");
        btn_pac_excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pac_excluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(btn_pac_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btn_pac_excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_pac_editar)
                    .addComponent(btn_pac_excluir))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Paciente", jPanel1);

        btn_proc_editar.setText("Editar");
        btn_proc_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proc_editarActionPerformed(evt);
            }
        });

        tbl_proc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "código", "Descrição", "Paciente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_proc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_procMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_proc);
        if (tbl_proc.getColumnModel().getColumnCount() > 0) {
            tbl_proc.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbl_proc.getColumnModel().getColumn(1).setPreferredWidth(150);
            tbl_proc.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        btn_proc_excluir.setText("Excluir");
        btn_proc_excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proc_excluirActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Procedimento"));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel4.setText("Descrição:");

        btn_proc_salvar.setText("Salvar");
        btn_proc_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proc_salvarActionPerformed(evt);
            }
        });

        btn_proc_cancelar.setText("Cancelar");
        btn_proc_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proc_cancelarActionPerformed(evt);
            }
        });

        jLabel5.setText("Paciente:");

        cb_paciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_pacienteActionPerformed(evt);
            }
        });

        btn_proc_novo.setText("Novo");
        btn_proc_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proc_novoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btn_proc_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btn_proc_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btn_proc_cancelar)
                        .addGap(0, 738, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(c_desc_proc)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cb_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(c_desc_proc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cb_paciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proc_salvar)
                    .addComponent(btn_proc_cancelar)
                    .addComponent(btn_proc_novo))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(btn_proc_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_proc_excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proc_excluir)
                    .addComponent(btn_proc_editar))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Procedimento", jPanel2);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Dentista"));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        jLabel11.setText("Nome:");

        c_dent_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_dent_nomeActionPerformed(evt);
            }
        });

        btn_dent_salvar.setText("Salvar");
        btn_dent_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dent_salvarActionPerformed(evt);
            }
        });

        btn_dent_cancela.setText("Cancelar");
        btn_dent_cancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dent_cancelaActionPerformed(evt);
            }
        });

        btn_dent_novo.setText("Novo");
        btn_dent_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dent_novoActionPerformed(evt);
            }
        });

        jLabel12.setText("CPF:");

        c_dent_cpf.setText("   .   .   -");

        jLabel13.setText("Sexo :");

        c_dent_sexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_dent_sexoActionPerformed(evt);
            }
        });

        jLabel14.setText("Endereço :");

        jLabel15.setText("Nasc :");

        c_dent_nasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_dent_nascActionPerformed(evt);
            }
        });

        jLabel16.setText("Especialidade:");

        c_dent_esp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_dent_espActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_dent_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_dent_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_dent_cancela))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel11))
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(c_dent_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(c_dent_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(c_dent_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(c_dent_nasc))
                                .addComponent(c_dent_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(c_dent_esp, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(356, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(c_dent_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_dent_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(c_dent_sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(c_dent_nasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_dent_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(c_dent_esp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_dent_novo)
                    .addComponent(btn_dent_salvar)
                    .addComponent(btn_dent_cancela))
                .addContainerGap())
        );

        tbl_dent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "CPF", "Sexo", "Endereço", "Nascimento", "Especialidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbl_dent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_dentMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_dent);
        if (tbl_dent.getColumnModel().getColumnCount() > 0) {
            tbl_dent.getColumnModel().getColumn(0).setResizable(false);
            tbl_dent.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbl_dent.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        btn_dent_edit.setText("Editar");
        btn_dent_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dent_editActionPerformed(evt);
            }
        });

        btn_dent_exc.setText("Excluir");
        btn_dent_exc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dent_excActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addComponent(btn_dent_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_dent_exc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_dent_edit)
                    .addComponent(btn_dent_exc))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dentista", jPanel5);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Secretária"));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        jLabel18.setText("Nome:");

        c_sec_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_sec_nomeActionPerformed(evt);
            }
        });

        btn_sec_salvar.setText("Salvar");
        btn_sec_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sec_salvarActionPerformed(evt);
            }
        });

        btn_sec_cancelar.setText("Cancelar");
        btn_sec_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sec_cancelarActionPerformed(evt);
            }
        });

        btn_sec_novo.setText("Novo");
        btn_sec_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sec_novoActionPerformed(evt);
            }
        });

        jLabel19.setText("CPF:");

        c_sec_cpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_sec_cpfActionPerformed(evt);
            }
        });

        jLabel21.setText("Endereço :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_sec_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_sec_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_sec_cancelar))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel18))
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(c_sec_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(c_sec_cpf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(c_sec_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(356, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(c_sec_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_sec_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_sec_endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_sec_novo)
                    .addComponent(btn_sec_salvar)
                    .addComponent(btn_sec_cancelar))
                .addContainerGap())
        );

        tbl_sec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "CPF", "Endereço"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_sec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_secMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_sec);
        if (tbl_sec.getColumnModel().getColumnCount() > 0) {
            tbl_sec.getColumnModel().getColumn(0).setResizable(false);
            tbl_sec.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbl_sec.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        btn_sec_edit.setText("Editar");
        btn_sec_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sec_editActionPerformed(evt);
            }
        });

        btn_sec_exc.setText("Excluir");
        btn_sec_exc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sec_excActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(329, 329, 329)
                .addComponent(btn_sec_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btn_sec_exc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_sec_exc)
                    .addComponent(btn_sec_edit))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Secretaria", jPanel6);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Pagamento"));

        jLabel22.setText("Forma de pagamento:");

        btn_pag_salvar.setText("Salvar");
        btn_pag_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pag_salvarActionPerformed(evt);
            }
        });

        btn_pag_cancelar.setText("Cancelar");
        btn_pag_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pag_cancelarActionPerformed(evt);
            }
        });

        jLabel23.setText("Paciente:");

        cb_pac1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_pac1ActionPerformed(evt);
            }
        });

        btn_pag_novo.setText("Novo");
        btn_pag_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pag_novoActionPerformed(evt);
            }
        });

        jLabel24.setText("Valor :");

        c_pag_valor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_pag_valorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btn_pag_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_pag_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_pag_cancelar))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cb_pac1, 0, 185, Short.MAX_VALUE)
                            .addComponent(c_pag_form)
                            .addComponent(c_pag_valor))))
                .addContainerGap(731, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(c_pag_form, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(c_pag_valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cb_pac1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_pag_cancelar)
                    .addComponent(btn_pag_salvar)
                    .addComponent(btn_pag_novo))
                .addGap(22, 22, 22))
        );

        tbl_pag.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "código", "Forma de pagamento", "Valor", "Paciente"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_pag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pagMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_pag);
        if (tbl_pag.getColumnModel().getColumnCount() > 0) {
            tbl_pag.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbl_pag.getColumnModel().getColumn(1).setPreferredWidth(150);
            tbl_pag.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        btn_pag_editar.setText("Editar");
        btn_pag_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pag_editarActionPerformed(evt);
            }
        });

        btn_pag_exc.setText("Excluir");
        btn_pag_exc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pag_excActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1027, Short.MAX_VALUE)
                        .addGap(32, 32, 32))))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(btn_pag_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_pag_exc, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_pag_editar)
                    .addComponent(btn_pag_exc))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pagamento", jPanel9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sec_excActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sec_excActionPerformed

        SecretariaController secretariaController = new SecretariaController();
        int row = tbl_sec.getSelectedRow();

        if (row >= 0) {
            try {
                int id = (int) modeloSecretaria.getValueAt(row, 0);
                secretariaController.deletar(id);
                int[] rows = tbl_sec.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    modeloSecretaria.removeRow(rows[i] - i);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showMessageDialog(this, "Nenhum registro selecionado");
        }

    }//GEN-LAST:event_btn_sec_excActionPerformed

    private void btn_sec_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sec_editActionPerformed
        modoSec = "Editar";
        ManipulaInterfaceSec();

        SecretariaController secretariaController = new SecretariaController();
        int id = 0;
        //   int cod = Integer.parseInt(c_pac_codigo.getText());

        int row = tbl_sec.getSelectedRow();
        if (row >= 0) {
            id = (int) modeloSecretaria.getValueAt(row, 0);
        } else {
            showMessageDialog(this, "Nenhum registro selecionado!");
        }

        try {
            secretariaController.editar(id, c_sec_nome.getText(), c_sec_cpf.getText(), c_sec_endereco.getText());

            //atualizar tabela local
            tbl_sec.setValueAt(c_sec_nome.getText(), row, 1);
            tbl_sec.setValueAt(c_sec_cpf.getText(), row, 2);
            tbl_sec.setValueAt(c_sec_endereco.getText(), row, 3);

        } catch (SQLException e) {
            System.out.println("Erro ao executar o comando SQL" + e);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_sec_editActionPerformed

    private void tbl_secMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_secMouseClicked
        int index = tbl_sec.getSelectedRow();
        // 
        if (index >= 0 && index < listaSec.size()) {
            Secretaria S = listaSec.get(index);
            //   c_sec_cod.setText(String.valueOf(S.getCodigo()));
            c_sec_nome.setText(S.getNome());
            c_sec_cpf.setText(S.getCpf());
            c_sec_endereco.setText(S.getEndereco());

            modoSec = "Editar";

            listaSecAuxDelet.add(0, S);

            System.out.println("a: " + listaSecAuxDelet.get(0));
            ManipulaInterfaceSec();
        }
    }//GEN-LAST:event_tbl_secMouseClicked

    private void c_sec_cpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_sec_cpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_sec_cpfActionPerformed

    private void btn_sec_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sec_novoActionPerformed
        //   c_sec_cod.setText("");
        c_sec_nome.setText("");
        c_sec_cpf.setText("");
        c_sec_endereco.setText("");

        modoSec = "Novo";
        ManipulaInterfaceSec();
    }//GEN-LAST:event_btn_sec_novoActionPerformed

    private void btn_sec_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sec_cancelarActionPerformed
        //    c_sec_cod.setText("");
        c_sec_nome.setText("");
        c_sec_cpf.setText("");
        c_sec_endereco.setText("");
        ;
        modoSec = "Navegar";
        ManipulaInterfaceSec();

    }//GEN-LAST:event_btn_sec_cancelarActionPerformed

    private void btn_sec_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sec_salvarActionPerformed
        Secretaria S = null;
        PreparedStatement ps2 = null;
        SecretariaController secretariaController = new SecretariaController();
        //    int cod = Integer.parseInt(c_sec_cod.getText());

        if (verificarCpf(c_sec_cpf.getText()) == true) {
            if (modoSec.equals("Novo")) {

                try {
                    int id = secretariaController.salvar(c_sec_nome.getText(), c_sec_cpf.getText(), c_sec_endereco.getText());
                    S = new Secretaria(id, c_sec_nome.getText(), c_sec_cpf.getText(), c_sec_endereco.getText());
                    updateLocalSecretariaTable(S);
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
                listaSec.add(S);

            } else if (modoSec.equals("Editar")) {
                int index = tbl_sec.getSelectedRow();
                //    listaSec.get(index).setCodigo(cod);
                listaSec.get(index).setNome(c_sec_nome.getText());
                listaSec.get(index).setCpf(c_sec_cpf.getText());
                listaSec.get(index).setEndereco(c_sec_endereco.getText());
            }

            modoSec = "Navegar";
            ManipulaInterfaceSec();
            //     c_sec_cod.setText("");
            c_sec_nome.setText("");
            c_sec_cpf.setText("");
            c_sec_endereco.setText("");

        } else {
            JOptionPane.showMessageDialog(rootPane, "CPF Negado, tente outra vez!");

        }
    }//GEN-LAST:event_btn_sec_salvarActionPerformed

    private void c_sec_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_sec_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_sec_nomeActionPerformed

    private void btn_dent_excActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dent_excActionPerformed

        modoDent = "Navegar";
        ManipulaInterfaceDent();

        DentistaController dentistaController = new DentistaController();
        int row = tbl_dent.getSelectedRow();

        if (row >= 0) {
            try {
                int id = (int) modeloDentista.getValueAt(row, 0);
                dentistaController.deletar(id);
                int[] rows = tbl_dent.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    modeloDentista.removeRow(rows[i] - i);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showMessageDialog(this, "Nenhum registro selecionado");
        }


    }//GEN-LAST:event_btn_dent_excActionPerformed

    private void btn_dent_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dent_editActionPerformed
        modoDent = "Editar";
        ManipulaInterfaceDent();
        DentistaController dentistaController = new DentistaController();
        PreparedStatement ps = null;
        //    int cod = Integer.parseInt(c_dent_codigo.getText());

        int id = 0;
        //   int cod = Integer.parseInt(c_pac_codigo.getText());

        int row = tbl_dent.getSelectedRow();
        if (row >= 0) {
            id = (int) modeloDentista.getValueAt(row, 0);
        } else {
            showMessageDialog(this, "Nenhum registro selecionado!");
        }

        try {

            TableModel tabela = tbl_dent.getModel();
            dentistaController.editar(id, c_dent_nome.getText(), c_dent_sexo.getText(), c_dent_cpf.getText(), c_dent_endereco.getText(), c_dent_nasc.getText(), c_dent_esp.getText());

            //atualizar tabela local
            tbl_dent.setValueAt(c_dent_nome.getText(), row, 1);
            tbl_dent.setValueAt(c_dent_cpf.getText(), row, 2);
            tbl_dent.setValueAt(c_dent_sexo.getText(), row, 3);
            tbl_dent.setValueAt(c_dent_endereco.getText(), row, 4);
            tbl_dent.setValueAt(c_dent_nasc.getText(), row, 5);
            tbl_dent.setValueAt(c_dent_esp.getText(), row, 6);

        } catch (SQLException e) {
            System.out.println("Erro ao executar o comando SQL" + e);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_dent_editActionPerformed

    private void tbl_dentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_dentMouseClicked
        int index = tbl_dent.getSelectedRow();
        // 
        if (index >= 0 && index < listaDent.size()) {
            Dentista DD = listaDent.get(index);
            //     c_dent_codigo.setText(String.valueOf(DD.getCodigo()));
            c_dent_nome.setText(DD.getNome());
            c_dent_cpf.setText(DD.getCpf());
            c_dent_nasc.setText(DD.getNasc());
            c_dent_sexo.setText(DD.getSexo());
            c_dent_endereco.setText(DD.getEndereco());
            c_dent_esp.setText(DD.getEspecialidade());

            modoDent = "Editar";

            listaDentAuxDelet.add(0, DD);

            System.out.println("a: " + listaDentAuxDelet.get(0));
            ManipulaInterfaceDent();

        }
    }//GEN-LAST:event_tbl_dentMouseClicked

    private void c_dent_espActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_dent_espActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_dent_espActionPerformed

    private void c_dent_nascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_dent_nascActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_dent_nascActionPerformed

    private void c_dent_sexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_dent_sexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_dent_sexoActionPerformed

    private void btn_dent_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dent_novoActionPerformed
        //   c_dent_codigo.setText("");
        c_dent_nome.setText("");
        c_dent_cpf.setText("");
        c_dent_endereco.setText("");
        c_dent_sexo.setText("");

        modoDent = "Novo";
        ManipulaInterfaceDent();
    }//GEN-LAST:event_btn_dent_novoActionPerformed

    private void btn_dent_cancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dent_cancelaActionPerformed
        //   c_dent_codigo.setText("");
        c_dent_nome.setText("");
        c_dent_cpf.setText("");
        c_dent_sexo.setText("");
        c_dent_endereco.setText("");
        c_dent_nasc.setText("");
        c_dent_esp.setText("");
        modoDent = "Navegar";
        ManipulaInterfaceDent();
    }//GEN-LAST:event_btn_dent_cancelaActionPerformed

    private void btn_dent_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dent_salvarActionPerformed
        PreparedStatement ps2 = null;
        DentistaController dentistaController = new DentistaController();
        //   int cod = Integer.parseInt(c_dent_codigo.getText());
        Dentista DD = new Dentista();
        if (verificarCpf(c_dent_cpf.getText()) == true) {
            if (modoDent.equals("Novo")) {
                Date nasc = null;
                String nascTexto = new String(c_dent_nasc.getText());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    format.setLenient(false);
                    nasc = format.parse(nascTexto);

                    int id = dentistaController.salvar(c_dent_nome.getText(), c_dent_sexo.getText(), c_dent_cpf.getText(), c_dent_endereco.getText(), c_dent_nasc.getText(), c_dent_esp.getText());
                    DD = new Dentista(id, c_dent_nome.getText(), c_dent_cpf.getText(), c_dent_sexo.getText(), c_dent_endereco.getText(), c_dent_nasc.getText(), c_dent_esp.getText());
                    listaDent.add(DD);
                    updateLocalDentistaTable(DD);
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null,
                            "Data inválida. Tente novamente!",
                            "AVISO",
                            JOptionPane.WARNING_MESSAGE);

                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } else {
            JOptionPane.showMessageDialog(rootPane, "CPF Negado, tente outra vez!");

        }
    }//GEN-LAST:event_btn_dent_salvarActionPerformed

    private void c_dent_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_dent_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_dent_nomeActionPerformed

    private void btn_proc_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proc_novoActionPerformed
        modoProc = "Novo";
        ManipulaInterfaceProc();
    }//GEN-LAST:event_btn_proc_novoActionPerformed

    private void cb_pacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_pacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_pacienteActionPerformed

    private void btn_proc_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proc_cancelarActionPerformed
        modoProc = "Navegar";
        ManipulaInterfaceProc();
    }//GEN-LAST:event_btn_proc_cancelarActionPerformed

    private void btn_proc_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proc_salvarActionPerformed
        PreparedStatement ps2 = null;
        ProcedimentoController procedimentoController = new ProcedimentoController();
        Procedimento F = new Procedimento();
        int index = cb_paciente.getSelectedIndex();
        if (index == 0) {
            JOptionPane.showMessageDialog(this, "Você deve selecionar um Paciente");
        } else {
            //      F.setMatricula(Integer.parseInt(c_proc_cod.getText()));
            F.setDescricao(c_desc_proc.getText());
            F.setDep(listaPac.get(index - 1));

            try {
                int id = procedimentoController.salvar(c_desc_proc.getText(), listaPac.get(index - 1));
                F.setId(id);
                listaProc.add(F);
                updateLocalProcedimentoTable(F);
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        modoProc = "Navegar";
        //    c_proc_cod.setText("");
        c_desc_proc.setText("");
        ManipulaInterfaceProc();
    }//GEN-LAST:event_btn_proc_salvarActionPerformed

    private void btn_proc_excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proc_excluirActionPerformed
        modoProc = "Navegar";
        ProcedimentoController procedimentoController = new ProcedimentoController();
        int row = tbl_proc.getSelectedRow();

        if (row >= 0) {
            try {
                int id = (int) modeloProcedimento.getValueAt(row, 0);
                procedimentoController.deletar(id);
                int[] rows = tbl_proc.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    modeloProcedimento.removeRow(rows[i] - i);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showMessageDialog(this, "Nenhum registro selecionado");
        }

        ManipulaInterfaceProc();
    }//GEN-LAST:event_btn_proc_excluirActionPerformed

    private void tbl_procMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_procMouseClicked
        int index = tbl_proc.getSelectedRow();
        // 
        if (index >= 0 && index < listaProc.size()) {
            Procedimento DD = listaProc.get(index);
            //     c_dent_codigo.setText(String.valueOf(DD.getCodigo()));
            c_desc_proc.setText(DD.getDescricao());

            modoProc = "Editar";

            listaProcAuxDelet.add(0, DD);

            System.out.println("a: " + listaProcAuxDelet.get(0));
            ManipulaInterfaceProc();

        }
    }//GEN-LAST:event_tbl_procMouseClicked

    private void btn_proc_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proc_editarActionPerformed
        modoProc = "Editar";

        //   int cod = Integer.parseInt(c_pac_codigo.getText());
        ProcedimentoController procedimentoController = new ProcedimentoController();
        int id = 0;
        //   int cod = Integer.parseInt(c_pac_codigo.getText());

        try {
            int row = tbl_proc.getSelectedRow();
            if (row >= 0) {
                id = (int) modeloProcedimento.getValueAt(row, 0);
                int index = cb_paciente.getSelectedIndex();
                procedimentoController.editar(id, c_desc_proc.getText(), listaPac.get(index - 1).getNome());

                //atualizar tabela local
                tbl_proc.setValueAt(c_desc_proc.getText(), row, 1);
                tbl_proc.setValueAt(listaPac.get(index - 1).getNome(), row, 2);
            } else {
                showMessageDialog(this, "Nenhum registro selecionado!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar o comando SQL" + e);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        ManipulaInterfaceProc();
    }//GEN-LAST:event_btn_proc_editarActionPerformed

    private void btn_pac_excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pac_excluirActionPerformed
        PacienteController pacienteController = new PacienteController();

        //LoadTablePac();
        modoPac = "Navegar";
        ManipulaInterfacePac();

        int row = tbl_pac.getSelectedRow();
        int id = (int) modeloPaciente.getValueAt(row, 0);

        try {
            if (row >= 0) {
                pacienteController.deletar(id);
                int[] rows = tbl_pac.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    modeloPaciente.removeRow(rows[i] - i);
                }
            } else {
                showMessageDialog(this, "Nenhum registro selecionado");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao executar o comando SQL" + e);
        }


    }//GEN-LAST:event_btn_pac_excluirActionPerformed
//
    private void btn_pac_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pac_editarActionPerformed
        modoPac = "Editar";
        ManipulaInterfacePac();
        PacienteController pacienteController = new PacienteController();
        int id = 0;
        //   int cod = Integer.parseInt(c_pac_codigo.getText());

        int row = tbl_pac.getSelectedRow();
        if (row >= 0) {
            id = (int) modeloPaciente.getValueAt(row, 0);
        } else {
            showMessageDialog(this, "Nenhum registro selecionado!");
        }

        try {
            pacienteController.editar(id, c_pac_nome.getText(), c_pac_sexo.getText(), c_pac_cpf.getText(), c_pac_endereco.getText(), c_pac_nasc.getText());

            DefaultTableModel model = (DefaultTableModel) tbl_pac.getModel();

            //atualizar tabela local
            tbl_pac.setValueAt(c_pac_nome.getText(), row, 1);
            tbl_pac.setValueAt(c_pac_cpf.getText(), row, 2);
            tbl_pac.setValueAt(c_pac_endereco.getText(), row, 3);

        } catch (SQLException e) {
            System.out.println("Erro ao executar o comando SQL" + e);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btn_pac_editarActionPerformed

    private void c_pac_nascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_pac_nascActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_pac_nascActionPerformed

    private void c_pac_sexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_pac_sexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_pac_sexoActionPerformed

    private void btn_pac_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pac_novoActionPerformed
        //  c_pac_codigo.setText("");
        c_pac_nome.setText("");
        c_pac_cpf.setText("");
        c_pac_endereco.setText("");
        c_pac_nasc.setText("");
        c_pac_sexo.setText("");

        modoPac = "Novo";
        ManipulaInterfacePac();
    }//GEN-LAST:event_btn_pac_novoActionPerformed

    private void btn_pac_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pac_cancelarActionPerformed
        //   c_pac_codigo.setText("");
        c_pac_nome.setText("");
        c_pac_cpf.setText("");
        c_pac_sexo.setText("");
        c_pac_endereco.setText("");
        modoPac = "Navegar";
        ManipulaInterfacePac();

    }//GEN-LAST:event_btn_pac_cancelarActionPerformed

    private void btn_pac_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pac_salvarActionPerformed
        PreparedStatement ps2 = null;
        //   int cod = Integer.parseInt(c_pac_codigo.getText());
        Paciente D = new Paciente();
        PacienteController pacienteController = new PacienteController();
        if (verificarCpf(c_pac_cpf.getText()) == true) {
            if (modoPac.equals("Novo")) {
                Date nasc = null;
                String nascTexto = new String(c_pac_nasc.getText());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    format.setLenient(false);
                    nasc = format.parse(nascTexto);
                    int id = pacienteController.salvar(c_pac_nome.getText(), c_pac_sexo.getText(), c_pac_cpf.getText(), c_pac_endereco.getText(), c_pac_nasc.getText());
                    D = new Paciente(id, c_pac_nome.getText(), c_pac_cpf.getText(), c_pac_sexo.getText(), c_pac_endereco.getText(), c_pac_nasc.getText());
                    this.updateLocalPacienteTable(D);

                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null,
                            "Data inválida. Tente novamente!",
                            "AVISO",
                            JOptionPane.WARNING_MESSAGE);

                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (modoPac.equals("Editar")) {
                int index = tbl_pac.getSelectedRow();
                //       listaPac.get(index).setCodigo(cod);
                listaPac.get(index).setNome(c_pac_nome.getText());
                listaPac.get(index).setCpf(c_pac_nome.getText());
                listaPac.get(index).setSexo(c_pac_sexo.getText());
                listaPac.get(index).setEndereco(c_pac_endereco.getText());
                listaPac.get(index).setEndereco(c_pac_nasc.getText());
            }

            modoPac = "Navegar";
            ManipulaInterfacePac();
            //     c_pac_codigo.setText("");
            c_pac_nome.setText("");
            c_pac_cpf.setText("");
            c_pac_sexo.setText("");
            c_pac_endereco.setText("");
            c_pac_nasc.setText("");

        } else {
            JOptionPane.showMessageDialog(rootPane, "CPF Negado, tente outra vez!");

        }


    }//GEN-LAST:event_btn_pac_salvarActionPerformed

    private void c_pac_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_pac_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_pac_nomeActionPerformed

    private void tbl_pacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pacMouseClicked
        int index = tbl_pac.getSelectedRow();
        // 
        if (index >= 0 && index < listaPac.size()) {
            Paciente D = listaPac.get(index);
            //     c_pac_codigo.setText(String.valueOf(D.getCodigo()));
            c_pac_nome.setText(D.getNome());
            c_pac_cpf.setText(D.getCpf());
            c_pac_nasc.setText(D.getNasc());
            c_pac_sexo.setText(D.getSexo());
            c_pac_endereco.setText(D.getEndereco());

            modoPac = "Editar";

            listaPacAuxDelet.add(0, D);

            System.out.println("a: " + listaPacAuxDelet.get(0));
            ManipulaInterfacePac();

        }
    }//GEN-LAST:event_tbl_pacMouseClicked

    private void btn_pag_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pag_salvarActionPerformed
        PreparedStatement ps2 = null;
        Pagamento pp = new Pagamento();
        PagamentoController pagamentoController = new PagamentoController();
        int index = cb_pac1.getSelectedIndex();
        System.out.println("index eh" + index);
        if (index == 0) {
            JOptionPane.showMessageDialog(this, "Você deve selecionar um Paciente");
        } else {

            try {
                int id = pagamentoController.salvar(c_pag_form.getText(), c_pag_valor.getText(), listaPac.get(index - 1));
                pp.setForma(c_pag_form.getText());
                pp.setValor(c_pag_valor.getText());
                pp.setDep(listaPac.get(index - 1));
                pp.setId(id);
                listaPag.add(pp);
                updateLocalPagamentoTable(pp);

            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        modoPag = "Navegar";
        //   c_pag_cod.setText("");
        c_pag_valor.setText("");
        c_pag_form.setText("");
        ManipulaInterfacePag();
    }//GEN-LAST:event_btn_pag_salvarActionPerformed

    private void btn_pag_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pag_cancelarActionPerformed
        modoPag = "Navegar";
        ManipulaInterfacePag();
    }//GEN-LAST:event_btn_pag_cancelarActionPerformed

    private void cb_pac1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_pac1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_pac1ActionPerformed

    private void btn_pag_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pag_novoActionPerformed
        modoPag = "Novo";
        ManipulaInterfacePag();
    }//GEN-LAST:event_btn_pag_novoActionPerformed

    private void c_pag_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_pag_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_c_pag_valorActionPerformed

    private void tbl_pagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pagMouseClicked
        int index = tbl_pag.getSelectedRow();
        modoPag = "Editar";

        // 
        if (index >= 0 && index < listaPag.size()) {
            Pagamento S = listaPag.get(index);
            //   c_sec_cod.setText(String.valueOf(S.getCodigo()));
            c_pag_form.setText(S.getForma());
            c_pag_valor.setText(S.getValor());

            listaPagAuxDelet.add(0, S);

            ManipulaInterfacePag();
        }


    }//GEN-LAST:event_tbl_pagMouseClicked

    private void btn_pag_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pag_editarActionPerformed
        int index = cb_pac1.getSelectedIndex();
        modoPag = "Editar";
        PagamentoController pagamentoController = new PagamentoController();
        int id = 0;
        //   int cod = Integer.parseInt(c_pac_codigo.getText());

        int row = tbl_pag.getSelectedRow();
        if (row >= 0) {
            id = (int) modeloPagamento.getValueAt(row, 0);
            try {
                pagamentoController.editar(id, c_pag_valor.getText(), c_pag_form.getText(), listaPac.get(index - 1));

                //atualizar tabela local
                tbl_pag.setValueAt(c_pag_form.getText(), row, 1);
                tbl_pag.setValueAt(c_pag_valor.getText(), row, 2);
                tbl_pag.setValueAt(listaPac.get(index - 1).getNome(), row, 3);

            } catch (SQLException e) {
                System.out.println("Erro ao executar o comando SQL" + e);
            } catch (ParseException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showMessageDialog(this, "Nenhum registro selecionado!");
        }
        ManipulaInterfacePag();
    }//GEN-LAST:event_btn_pag_editarActionPerformed

    private void btn_pag_excActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pag_excActionPerformed
        modoPag = "Navegar";
        PagamentoController pagamentoController = new PagamentoController();
        int row = tbl_pag.getSelectedRow();

        if (row >= 0) {
            try {
                int id = (int) modeloPagamento.getValueAt(row, 0);
                pagamentoController.deletar(id);
                int[] rows = tbl_pag.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    modeloPagamento.removeRow(rows[i] - i);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showMessageDialog(this, "Nenhum registro selecionado");
        }

        ManipulaInterfacePag();
    }//GEN-LAST:event_btn_pag_excActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void tbl_pacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbl_pacFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_pacFocusGained

    public boolean verificarCpf(String cpf) {
        int dig1 = 0, dig2 = 0, calc1 = 0, calc2 = 0, aux1 = 10, aux2 = 11;
        int[] arrayCpf;
        boolean repetido = true;
        arrayCpf = new int[9];
        dig1 = Integer.parseInt(cpf.substring(12, 13));
        dig2 = Integer.parseInt(cpf.substring(13, 14));
        cpf = cpf.substring(0, 3) + cpf.substring(4, 7) + cpf.substring(8, 11);
        for (int i = 0; i < arrayCpf.length; i++) {
            arrayCpf[i] = Integer.parseInt(cpf.substring(i, i + 1));

            calc1 += aux1 * arrayCpf[i];
            aux1--;
            calc2 += aux2 * arrayCpf[i];
            aux2--;

            if (arrayCpf[0] != arrayCpf[i] && repetido) {
                repetido = false;
            }
        }
        calc2 += dig1 * aux2;

        calc1 = (calc1 * 10) % 11;
        calc2 = (calc2 * 10) % 11;

        if (calc1 == 10) {
            calc1 = 0;
        }

        if (calc2 == 10) {
            calc2 = 0;
        }

        if (calc1 == dig1 && calc2 == dig2 && !repetido) {
            return true;
        } else {
            return false;
        }
    }//fim função verifica CPF

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_dent_cancela;
    private javax.swing.JButton btn_dent_edit;
    private javax.swing.JButton btn_dent_exc;
    private javax.swing.JButton btn_dent_novo;
    private javax.swing.JButton btn_dent_salvar;
    private javax.swing.JButton btn_pac_cancelar;
    private javax.swing.JButton btn_pac_editar;
    private javax.swing.JButton btn_pac_excluir;
    private javax.swing.JButton btn_pac_novo;
    private javax.swing.JButton btn_pac_salvar;
    private javax.swing.JButton btn_pag_cancelar;
    private javax.swing.JButton btn_pag_editar;
    private javax.swing.JButton btn_pag_exc;
    private javax.swing.JButton btn_pag_novo;
    private javax.swing.JButton btn_pag_salvar;
    private javax.swing.JButton btn_proc_cancelar;
    private javax.swing.JButton btn_proc_editar;
    private javax.swing.JButton btn_proc_excluir;
    private javax.swing.JButton btn_proc_novo;
    private javax.swing.JButton btn_proc_salvar;
    private javax.swing.JButton btn_sec_cancelar;
    private javax.swing.JButton btn_sec_edit;
    private javax.swing.JButton btn_sec_exc;
    private javax.swing.JButton btn_sec_novo;
    private javax.swing.JButton btn_sec_salvar;
    private javax.swing.JTextField c_dent_cpf;
    private javax.swing.JTextField c_dent_endereco;
    private javax.swing.JTextField c_dent_esp;
    private javax.swing.JTextField c_dent_nasc;
    private javax.swing.JTextField c_dent_nome;
    private javax.swing.JTextField c_dent_sexo;
    private javax.swing.JTextField c_desc_proc;
    private javax.swing.JTextField c_pac_cpf;
    private javax.swing.JTextField c_pac_endereco;
    private javax.swing.JTextField c_pac_nasc;
    private javax.swing.JTextField c_pac_nome;
    private javax.swing.JTextField c_pac_sexo;
    private javax.swing.JTextField c_pag_form;
    private javax.swing.JTextField c_pag_valor;
    private javax.swing.JTextField c_sec_cpf;
    private javax.swing.JTextField c_sec_endereco;
    private javax.swing.JTextField c_sec_nome;
    private javax.swing.JComboBox cb_pac1;
    private javax.swing.JComboBox cb_paciente;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbl_dent;
    private javax.swing.JTable tbl_pac;
    private javax.swing.JTable tbl_pag;
    private javax.swing.JTable tbl_proc;
    private javax.swing.JTable tbl_sec;
    // End of variables declaration//GEN-END:variables
}
