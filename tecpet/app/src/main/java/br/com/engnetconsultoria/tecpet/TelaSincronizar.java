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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TelaSincronizar extends AppCompatActivity {

    EditText editSincronizar;
    Button btnSincronizarMaquina;

    String parametrosUsuario = "";
    String url = "";
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_sincronizar);

        editSincronizar = (EditText)findViewById(R.id.editSincronizar);
        btnSincronizarMaquina = (Button)findViewById(R.id.btnSincronizarMaquina);

        //botão logar
        btnSincronizarMaquina.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    //codigo

                    String sincronia = editSincronizar.getText().toString();

                    if(sincronia.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Digite o código da máquina.", Toast.LENGTH_LONG).show();
                    }else{
                        url = "http://engnetconsultoria.com.br/app/tecpet/login/sincronizar.php";

                        idUsuario = getIntent().getExtras().getString("usuario_id");
                        parametrosUsuario = "id=" + idUsuario + "&maquina=" + sincronia;

                        new TelaSincronizar.SolicitaDados().execute(url);
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
            if(resultado.contains("sincronizar_ok")){
                Toast.makeText(getApplicationContext(), "Sincronizado com sucesso!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Coloque um código válido.", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
