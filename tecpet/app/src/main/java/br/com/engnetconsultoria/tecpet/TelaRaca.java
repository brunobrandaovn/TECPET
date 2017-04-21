package engnetconsultoria.tecpet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class TelaRaca extends AppCompatActivity {

    TextView descricaoRaca;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_raca);

        descricaoRaca = (TextView) findViewById(R.id.txtDescricao);

        String nomeRaca = getIntent().getExtras().getString("raca_nome");
        String descricaoRacaString = getIntent().getExtras().getString("raca_descricao");

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Bulldog74.jpg/200px-Bulldog74.jpg");


        setTitle(nomeRaca);
        descricaoRaca.setText(descricaoRacaString);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
