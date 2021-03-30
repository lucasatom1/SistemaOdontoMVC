/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaOdonto;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author PICHAU
 */
public class Dentista {
    private int codigo;
    private String nome;
    private String sexo;
    private String cpf;
    private String endereco;
    private String nasc;
    private String especialidade;

    public Dentista(int codigo, String nome, String sexo, String cpf, String endereco, String nasc, String especialidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.sexo = sexo;
        this.cpf = cpf;
        this.endereco = endereco;
        this.nasc = nasc;
        this.especialidade = especialidade;
    }
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
    

    
}
