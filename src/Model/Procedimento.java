package Model;


public class Procedimento {
    
    private String descricao;
    private String nome;
    private Paciente Dep;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Procedimento() {
    }

    public Procedimento(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
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
