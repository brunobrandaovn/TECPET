package engnetconsultoria.tecpet;

/**
 * Created by Brandão on 18/04/2017.
 */

public class Usuario {
    private int id;
    private String nome;
    private String user;

    //construtor do usuário
    public Usuario(int id, String nome, String usuario){
        this.setId(id);
        this.setNome(nome);
        this.setUser(usuario);
    }

    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getUser(){
        return this.user;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setUser(String user){
        this.user = user;
    }

}
