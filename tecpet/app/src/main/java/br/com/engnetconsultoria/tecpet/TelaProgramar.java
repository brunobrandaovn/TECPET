package engnetconsultoria.tecpet;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class TelaProgramar extends AppCompatActivity {

    TimePicker programar;
    Button btnProgramar;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_programar);

        TimePicker programar = (TimePicker) findViewById(R.id.tmProgramar);

        Button btnProgramar = (Button) findViewById(R.id.btnProgramarHorario);

        btnProgramar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TimePicker programar = (TimePicker) findViewById(R.id.tmProgramar);
                String hora = String.valueOf(programar.getHour());
                String minuto = String.valueOf(programar.getMinute());

                String todo = "A horá escolhida é: " + hora + ":" + minuto;
                Toast.makeText(getApplicationContext(), todo , Toast.LENGTH_LONG).show();
            }
        });

    }
}
