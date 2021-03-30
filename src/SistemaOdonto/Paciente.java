package SistemaOdonto;

import java.util.ArrayList;
import java.util.Date;


public class Paciente {
    private int codigo;
    private String nome;
    private String sexo;
    private String cpf;
    private String endereco;
    private String nasc;
    ArrayList<Procedimento> listaPac;    // ListaFunc = new ArrayList(); //

        public Paciente() {
        listaPac = new ArrayList();
    }
        
    public Paciente(int codigo, String nome, String cpf, String sexo, String endereco) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.endereco = endereco;
        listaPac = new ArrayList();
    }

    public Paciente(int Codigo, String nome, String cpf, String sexo, String endereco, String nasc) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.endereco = endereco;
        this.nasc=nasc;
        listaPac = new ArrayList();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int Codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String Nome) {
        this.nome = Nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String Sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String Cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNasc() {
        return nasc;
    }

    public void setNasc(String Nasc) {
        this.nasc = nasc;
    }
    
    
    

    public ArrayList<Procedimento> getListaFunc() {
        return listaPac;
    }

    public void setListaFunc(ArrayList<Procedimento> ListaFunc) {
        this.listaPac = ListaFunc;
    }
    
    public void addFunc(Procedimento F){
        F.setDep(this);
        listaPac.add(F);
    }

    public Object getDep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
