package Model;

import java.util.ArrayList;
import java.util.Date;

public class Paciente {

    private int id;
    private String nome;
    private String sexo;
    private String cpf;
    private String endereco;
    private String nasc;
    ArrayList<Procedimento> listaPac;    // ListaFunc = new ArrayList(); //

    public Paciente() {
        listaPac = new ArrayList();
    }

    public Paciente(int id, String nome, String cpf, String sexo, String endereco) {
        this.id = id;

        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.endereco = endereco;
        listaPac = new ArrayList();
    }

    public Paciente(int id, String nome, String cpf, String sexo, String endereco, String nasc) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.sexo = sexo;
        this.endereco = endereco;
        this.nasc = nasc;
        listaPac = new ArrayList();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
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

    public void setNasc(String nasc) {
        this.nasc = nasc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Procedimento> getListaFunc() {
        return listaPac;
    }

    public void setListaFunc(ArrayList<Procedimento> ListaFunc) {
        this.listaPac = ListaFunc;
    }

    public void addFunc(Procedimento F) {
        F.setDep(this);
        listaPac.add(F);
    }

    public Object getDep() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
