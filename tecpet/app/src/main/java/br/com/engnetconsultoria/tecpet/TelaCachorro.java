package engnetconsultoria.tecpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaCachorro extends AppCompatActivity {

    private AutoCompleteTextView racasCachorro;
    private ArrayAdapter<String> adpOpcoes;
    private ArrayList<Raca> racas;
    private String resultado;


    private Button btnPesquisar, btnTodasRacas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cachorro);


        racasCachorro = (AutoCompleteTextView) findViewById(R.id.actvCachorro);
        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        btnTodasRacas = (Button) findViewById(R.id.btnTodasRacas);

        racas = new ArrayList<Raca>();

        resultado = getIntent().getExtras().getString("string_pet");

        if(!resultado.isEmpty()){
            String[] racaString = resultado.split("~");

            for (int j = 0; j < racaString.length; j = j + 5){
                int id = Integer.parseInt(racaString[j]);
                int refeicao = Integer.parseInt(racaString[j+4]);
                Raca raca = new Raca(id, racaString[j+1], racaString[j+2], racaString[j+3], refeicao);
                racas.add(raca);
            }

            adpOpcoes = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
            racasCachorro.setAdapter(adpOpcoes);

            for(int i = 0; i < racas.size(); i++){
                Raca raca1 = (Raca) racas.get(i);
                String nomeRaca = raca1.getNomeRaca();
                adpOpcoes.add(nomeRaca);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Nenhuma raça no sistema.", Toast.LENGTH_LONG).show();
        }

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeRaca = racasCachorro.getText().toString();
                Raca racaSelecionada = new Raca();

                racaSelecionada = racaSelecionada.recuperaRaca(nomeRaca, racas);

                Intent paginaRaca = new Intent(TelaCachorro.this, TelaRaca.class);
                paginaRaca.putExtra("raca_id", racaSelecionada.getId());
                paginaRaca.putExtra("raca_nome", racaSelecionada.getNomeRaca());
                paginaRaca.putExtra("raca_descricao", racaSelecionada.getDescricaoRaca());
                paginaRaca.putExtra("raca_imagem", racaSelecionada.getImagemRaca());

                startActivity(paginaRaca);
            }
        });

        btnTodasRacas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
