package SistemaOdonto;


public class Procedimento {
    private int codProcedimento;
    private String descricao;
    private Paciente Dep;

    public Procedimento() {
    }

    public Procedimento(int Matricula, String Nome) {
        this.codProcedimento = Matricula;
        this.descricao = descricao;
    }

    public int getMatricula() {
        return codProcedimento;
    }

    public void setMatricula(int Matricula) {
        this.codProcedimento = Matricula;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Paciente getDep() {
        return Dep;
    }

    public void setDep(Paciente Dep) {
        this.Dep = Dep;
    }

    
    
    
}
