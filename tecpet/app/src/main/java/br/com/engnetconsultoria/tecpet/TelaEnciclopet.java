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
import android.widget.Toast;

public class TelaEnciclopet extends AppCompatActivity {

    private Button btnGato, btnCachorro;
    private String parametrosUsuario = "";
    private String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_enciclopet);

        btnGato = (Button) findViewById(R.id.btnGato);
        btnCachorro = (Button) findViewById(R.id.btnCachorro);

        //clicar no botao gato
        btnGato.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    //codigo
                    url = "http://engnetconsultoria.com.br/app/tecpet/login/recuperaRaca.php";
                    new TelaEnciclopet.SolicitaDadosGato().execute(url);
                }else{
                    //erro ao conectar
                    Toast.makeText(getApplicationContext(), "Nenhuma aplicação foi detectada.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //clicar no botao cachorro
        btnCachorro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    //codigo
                    url = "http://engnetconsultoria.com.br/app/tecpet/login/recuperaRaca.php";
                    new TelaEnciclopet.SolicitaDadosCachorro().execute(url);
                }else{
                    //erro ao conectar
                    Toast.makeText(getApplicationContext(), "Nenhuma aplicação foi detectada.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class SolicitaDadosCachorro extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){
            return Conexao.postDados(urls[0], parametrosUsuario);
        }
        @Override
        protected void onPostExecute(String resultado){
            if(!resultado.contains("no_results")){
                int tamanho = resultado.length();
                String result = resultado.substring(0, tamanho - 2);
                Intent abreAddPet = new Intent(TelaEnciclopet.this, TelaCachorro.class);
                abreAddPet.putExtra("string_pet", result);
                startActivity(abreAddPet);
            }else{
                Toast.makeText(getApplicationContext(), "Nenhum resultado.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class SolicitaDadosGato extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){
            return Conexao.postDados(urls[0], parametrosUsuario);
        }
        @Override
        protected void onPostExecute(String resultado){
            if(!resultado.contains("no_results")){
                int tamanho = resultado.length();
                String result = resultado.substring(0, tamanho - 2);
                Intent abreAddPet = new Intent(TelaEnciclopet.this, TelaGato.class);
                abreAddPet.putExtra("string_pet", result);
                startActivity(abreAddPet);
            }else{
                Toast.makeText(getApplicationContext(), "Nenhum resultado.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
