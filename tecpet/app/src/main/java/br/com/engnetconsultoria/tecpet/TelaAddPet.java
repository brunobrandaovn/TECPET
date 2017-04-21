package engnetconsultoria.tecpet;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TelaAddPet extends AppCompatActivity {

    Spinner selRacas;
    String nomeRaca = "Batata";
    ArrayAdapter<String> adpOpcoes;
    ArrayList<Raca> racas;

    String url = "";
    String parametrosUsuario = "";
    String resultado = "";
    String idUsuario = "";

    EditText editNomePet, editIdadePet, editPesoText;
    Button btnAddPet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_add_pet);

        selRacas = (Spinner) findViewById(R.id.selRacas);
        editNomePet = (EditText) findViewById(R.id.edtPetNome);
        editIdadePet = (EditText) findViewById(R.id.edtPetIdade);
        editPesoText = (EditText) findViewById(R.id.edtPetPeso);
        btnAddPet = (Button) findViewById(R.id.btnAddPet);
        idUsuario = getIntent().getExtras().getString("id_usuario");
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
            adpOpcoes = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
            adpOpcoes.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
            selRacas.setAdapter(adpOpcoes);

            for(int i = 0; i < racas.size(); i++){
                Raca raca1 = (Raca) racas.get(i);
                String nomeRaca = raca1.getNomeRaca();
                adpOpcoes.add(nomeRaca);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Nenhuma raça no sistema.", Toast.LENGTH_LONG).show();
        }

        //botão logar
        btnAddPet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    //codigo
                    String nome = editNomePet.getText().toString();
                    String idade = editIdadePet.getText().toString();
                    String peso =  editPesoText.getText().toString();
                    String nomeRaca = selRacas.getSelectedItem().toString();
                    String altura = "1";
                    String porte = "1";

                    Raca raca = new Raca();

                    raca = raca.recuperaRaca(nomeRaca, racas);

                    int idRaca = raca.getId();

                    String idString = Integer.toString(idRaca);

                    if(nome.isEmpty() || idade.isEmpty() || peso.isEmpty() || nomeRaca.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio.", Toast.LENGTH_LONG).show();
                    }
                    else{

                        url = "http://engnetconsultoria.com.br/app/tecpet/login/inserirPet.php";

                        parametrosUsuario = "nomePet="+ nome +"&raca="+ idString + "&idade=" + idade + "&peso=" + peso + "&altura=" + altura + "&porte=" + porte + "&usuario=" + idUsuario;
                        new TelaAddPet.SolicitaDados().execute(url);
                    }
                }else{
                    //erro ao conectar
                    Toast.makeText(getApplicationContext(), "Nenhuma aplicação foi detectada.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){
            return Conexao.postDados(urls[0], parametrosUsuario);
        }
        @Override
        protected void onPostExecute(String resultado){
            if(resultado.contains("pet_ok")){
    //            Intent abreTela = new Intent(TelaAddPet.this, TecPet.class);
                Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
      //          startActivity(abreTela);
            }else{
                Toast.makeText(getApplicationContext(), "Erro ao adicionar.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}
