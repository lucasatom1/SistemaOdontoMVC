/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaOdonto;

import java.util.ArrayList;


public class Pagamento {
    
    private String valor;
    private int codPag;
    private String forma;
    private Paciente Dep;
    ArrayList<Pagamento> listaPag; 
    
    
    
    public Pagamento() {
        listaPag = new ArrayList();
    }

    public Pagamento(String valor, String forma) {
        this.valor = valor;
        this.forma = forma;
    }

    

    

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getCodPag() {
        return codPag;
    }

    public void setCodPag(int codPag) {
        this.codPag = codPag;
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
