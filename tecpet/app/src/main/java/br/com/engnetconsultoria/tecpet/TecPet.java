package engnetconsultoria.tecpet;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TecPet extends AppCompatActivity {

    TextView txtNome;
    Button btnSincronizar, btnProgramar, btnHistorico, btnDadosPet, btnAddPet, btnDadosUsuario;

    String apresentacao, nomeUsuario, idUsuario;
    Raca raca;
    ArrayList racas;

    String url = "";
    String parametrosUsuario = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tec_pet);


        txtNome = (TextView) findViewById(R.id.txtNomeUsuario);
        btnSincronizar = (Button) findViewById(R.id.btnSincronizar);
        btnProgramar = (Button) findViewById(R.id.btnProgramar);
        btnHistorico = (Button) findViewById(R.id.btnHistorico);
        btnDadosPet = (Button) findViewById(R.id.btnDadosPet);
        btnAddPet = (Button) findViewById(R.id.btnAddPet);
        btnDadosUsuario = (Button) findViewById(R.id.btnDadosPessoais);

        nomeUsuario = getIntent().getExtras().getString("nome_usuario");
        idUsuario = getIntent().getExtras().getString("id_usuario");

        apresentacao = "Olá, " + nomeUsuario;
        //escreve o nome de usuário na indrodução do aplicativo.
        txtNome.setText(apresentacao);





        //clicar no botao sincronizar
        btnSincronizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent abreSincronizar = new Intent(TecPet.this, TelaSincronizar.class);
                abreSincronizar.putExtra("usuario_id", idUsuario);
                startActivity(abreSincronizar);
            }
        });

        //clicar no botao programar
        btnProgramar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent abreProgramar = new Intent(TecPet.this, TelaProgramar.class);
                startActivity(abreProgramar);
            }
        });

        //clicar no botao historico
        btnHistorico.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent abreHistorico = new Intent(TecPet.this, TelaHistorico.class);
                startActivity(abreHistorico);
            }
        });

        //clicar no botao dados pet
        btnDadosPet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent abreDadosPet = new Intent(TecPet.this, TelaEnciclopet.class);
                startActivity(abreDadosPet);
            }
        });

        //clicar no botao addPet
        btnAddPet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    //codigo

                        url = "http://engnetconsultoria.com.br/app/tecpet/login/recuperaRaca.php";
                        new TecPet.SolicitaDados().execute(url);
                }else{
                    //erro ao conectar
                    Toast.makeText(getApplicationContext(), "Nenhuma aplicação foi detectada.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //clicar no botao dadosUsuario
        btnDadosUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent abreDadosUsuario = new Intent(TecPet.this, TelaDadosUsuario.class);
                startActivity(abreDadosUsuario);
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
            if(!resultado.contains("no_results")){
                int tamanho = resultado.length();
                String result = resultado.substring(0, tamanho - 2);
                Intent abreAddPet = new Intent(TecPet.this, TelaAddPet.class);
                abreAddPet.putExtra("id_usuario", idUsuario);
                abreAddPet.putExtra("string_pet", result);
                startActivity(abreAddPet);
            }else{
                Toast.makeText(getApplicationContext(), "Nenhum resultado.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
