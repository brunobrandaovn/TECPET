package engnetconsultoria.tecpet;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TelaLogin extends AppCompatActivity {

    EditText editEmail1, editSenha1;
    Button btnLogar, btnCadastro, btnBruno;


    String url = "";
    String parametrosUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        editEmail1 = (EditText)findViewById(R.id.editEmail1);
        editSenha1 = (EditText)findViewById(R.id.editSenha1);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        btnCadastro = (Button) findViewById(R.id.btnCadastro);


        //botão logar
        btnLogar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    //codigo
                    String email = editEmail1.getText().toString();
                    String senha = editSenha1.getText().toString();

                    if(email.isEmpty() || senha.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio.", Toast.LENGTH_LONG).show();
                    }else{
                        url = "http://engnetconsultoria.com.br/app/tecpet/login/login.php";
                        parametrosUsuario = "email="+ email + "&senha=" + senha;
                        new SolicitaDados().execute(url);
                    }
                }else{
                    //erro ao conectar
                    Toast.makeText(getApplicationContext(), "Nenhuma aplicação foi detectada.", Toast.LENGTH_LONG).show();
                }

            }
        });


        btnCadastro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent abreCadastro = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(abreCadastro);
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
            if(resultado.contains("login_ok")){

            //editEmail1.setText(resultado);
            String[] dados = resultado.split(",");
                Intent abreInicio = new Intent(TelaLogin.this, TecPet.class);
                abreInicio.putExtra("id_usuario", dados[1]);
                abreInicio.putExtra("nome_usuario", dados[2]);
                startActivity(abreInicio);
            }else{
                Toast.makeText(getApplicationContext(), "Usuário ou senha incorretos.", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}

