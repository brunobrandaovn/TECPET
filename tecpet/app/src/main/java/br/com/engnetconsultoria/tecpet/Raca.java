package engnetconsultoria.tecpet;

import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 19/04/2017.
 */

public class Raca{

    private String nomeRaca;
    private String descricaoRaca;
    private String imagemRaca;
    private int refeicaoRecomendada;
    private int id;

    public Raca(){

    }

    public Raca(int id, String nomeRaca, String descricaoRaca, String imagemRaca, int refeicaoRecomendada) {
        this.id = id;
        this.nomeRaca = nomeRaca;
        this.descricaoRaca = descricaoRaca;
        this.imagemRaca = imagemRaca;
        this.refeicaoRecomendada = refeicaoRecomendada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomeRaca(String nomeRaca){
        this.nomeRaca = nomeRaca;
    }

    public String getNomeRaca() {
        return nomeRaca;
    }

    public String getDescricaoRaca() {
        return descricaoRaca;
    }

    public void setDescricaoRaca(String descricaoRaca) {
        this.descricaoRaca = descricaoRaca;
    }

    public String getImagemRaca() {
        return imagemRaca;
    }

    public void setImagemRaca(String imagemRaca) {
        this.imagemRaca = imagemRaca;
    }

    public int getRefeicaoRecomendada() {
        return refeicaoRecomendada;
    }

    public void setRefeicaoRecomendada(int refeicaoRecomendada) {
        this.refeicaoRecomendada = refeicaoRecomendada;
    }

    public ArrayList<Raca> populaRaca(String resultado){
        ArrayList<Raca> racas = new ArrayList();

        String[] racaString = resultado.split("~");

        for (int j = 0; j < racaString.length; j = j + 5){
            int id = Integer.parseInt(racaString[j]);
            int refeicao = Integer.parseInt(racaString[j+4]);
            Raca raca = new Raca(id, racaString[j+1], racaString[j+2], racaString[j+3], refeicao);
            racas.add(raca);
        }
        return racas;
    }

    public Raca recuperaRaca(String nomeRaca, ArrayList<Raca> racas){
        Raca raca = new Raca();
        for(int i = 0; i < racas.size(); i++){
            if(racas.get(i).getNomeRaca().toString().equals(nomeRaca)){
                raca = racas.get(i);
                return raca;
            }
        }
        Raca erro = new Raca(1, "bruno", "c", "c", 1);
        return erro;
    }



}
