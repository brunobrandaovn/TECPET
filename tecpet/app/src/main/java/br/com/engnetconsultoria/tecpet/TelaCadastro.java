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

public class TelaCadastro extends AppCompatActivity {

    EditText editEmail2, editSenha2, editConfirmaSenha, editNome;
    Button btnCadastrar;
    TextView textTelaCadastro;

    String url = "";
    String parametrosUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);

        editEmail2 = (EditText)findViewById(R.id.editEmail2);
        editSenha2 = (EditText)findViewById(R.id.editSenha2);
        editNome = (EditText) findViewById(R.id.editNome);
        editConfirmaSenha = (EditText)findViewById(R.id.editConfirmaSenha);
        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        textTelaCadastro = (TextView)findViewById(R.id.textTelaCadastro);


        //botão logar
        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){
                    //codigo

                    String email = editEmail2.getText().toString();
                    String senha = editSenha2.getText().toString();
                    String nome =  editNome.getText().toString();
                    String confirmaSenha = editConfirmaSenha.getText().toString();


                    if(email.isEmpty() || senha.isEmpty() || nome.isEmpty() || confirmaSenha.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        url = "http://engnetconsultoria.com.br/app/tecpet/login/cadastro.php";

                        parametrosUsuario = "nome="+ nome +"&email="+ email + "&senha=" + senha;

                        new TelaCadastro.SolicitaDados().execute(url);
                    }
                }else{
                    //erro ao conectar
                    Toast.makeText(getApplicationContext(), "Nenhuma aplicação foi detectada.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //clicar p/ voltar
        textTelaCadastro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent abreLogin = new Intent(TelaCadastro.this, TelaLogin.class);
                startActivity(abreLogin);
            }
        });
    }
    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls){
            return Conexao.postDados(urls[0], parametrosUsuario );
        }
        @Override
        protected void onPostExecute(String resultado){
            if(resultado.contains("cadastro_ok")){
                Intent abreInicio = new Intent(TelaCadastro.this, TelaLogin.class);
                Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                startActivity(abreInicio);
            }else{
                Toast.makeText(getApplicationContext(), "Tente outro nome de usuário.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
