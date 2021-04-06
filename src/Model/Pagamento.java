/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;


public class Pagamento {
    
    private String valor;
    private int id;
    private String forma;
    private Paciente Dep;
    ArrayList<Pagamento> listaPag; 
    
    
    
    public Pagamento() {
        listaPag = new ArrayList();
    }

    public Pagamento(int id, String valor, String forma) {
        this.id = id;
        this.valor = valor;
        this.forma = forma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public Paciente getDep() {
        return Dep;
    }

    public void setDep(Paciente Dep) {
        this.Dep = Dep;
    }

    
    
    public void addFunc(Pagamento pp){
        listaPag.add(pp);
    }
    
    
}
