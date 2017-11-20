package app.guillen.com.munidenunciasapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.guillen.com.munidenunciasapp.ApiService;
import app.guillen.com.munidenunciasapp.R;
import app.guillen.com.munidenunciasapp.models.Denuncia;
import app.guillen.com.munidenunciasapp.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private Integer id;

    private ImageView fotoImage;
    private TextView titulo;
    private TextView comentario;
    private TextView latitud;
    private TextView longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fotoImage = (ImageView)findViewById(R.id.image);
        titulo = (TextView)findViewById(R.id.titulo_text);
        comentario = (TextView)findViewById(R.id.comentario_text);
        latitud = (TextView)findViewById(R.id.latitud_text);
        longitud = (TextView)findViewById(R.id.longitud_text);

        id = getIntent().getExtras().getInt("ID");
        Log.e(TAG, "id:" + id);

        initialize();
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Denuncia> call = service.showDenuncia(id);

        call.enqueue(new Callback<Denuncia>() {
            @Override
            public void onResponse(Call<Denuncia> call, Response<Denuncia> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Denuncia denuncia = response.body();
                        Log.d(TAG, "Denuncia: " + denuncia);

                        String url = ApiService.API_BASE_URL + "/images/" + denuncia.getImagen();
                        Picasso.with(DetailActivity.this).load(url).into(fotoImage);

                        titulo.setText(denuncia.getTitulo());
                        comentario.setText(denuncia.getComentario());
                        latitud.setText("Latitud: " + denuncia.getLatitud());
                        longitud.setText("Longitud: " + denuncia.getLongitud());

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<Denuncia> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}