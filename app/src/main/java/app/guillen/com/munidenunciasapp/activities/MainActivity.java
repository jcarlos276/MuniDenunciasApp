package app.guillen.com.munidenunciasapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.guillen.com.munidenunciasapp.ApiService;
import app.guillen.com.munidenunciasapp.R;
import app.guillen.com.munidenunciasapp.models.ResponseMessage;
import app.guillen.com.munidenunciasapp.models.Usuario;
import app.guillen.com.munidenunciasapp.services.ApiServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    EditText username;
    EditText password;
    Button btnLogin;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.etUsuario);
        password = (EditText) findViewById(R.id.etClave);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // username remember
        String userName = sharedPreferences.getString("username", null);
        if(username != null){
            username.setText(userName);
            password.requestFocus();
        }

        // islogged remember
        if(sharedPreferences.getBoolean("islogged", false)){
            // Go to Dashboard
            goHome();
        }
    }

    public void login(View view){
        final String user = username.getText().toString();
        String pass = password.getText().toString();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Debe completar todo los campos", Toast.LENGTH_SHORT).show();
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<ResponseMessage> call = null;
        call = service.validateUser(user, pass);

        call.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                try {
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);
                    if (response.isSuccessful()) {
                        ResponseMessage responseMessage = response.body();
                        Log.d(TAG, "responseMessage: " + responseMessage);
                        Toast.makeText(MainActivity.this, "Bienvenido "+ user, Toast.LENGTH_LONG).show();

                        // Save to SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        boolean success = editor
                                .putString("username", user)
                                .putBoolean("islogged", true)
                                .commit();

                        // Go to Dashboard
                        goHome();


                    } else {
                        //progressDialog.dismiss();
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos!", Toast.LENGTH_SHORT).show();
                        //throw new Exception();
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void goHome(){
        Intent intent = new Intent(MainActivity.this,ListDenunciasActivity.class);
        startActivity(intent);
        finish();
    }

}
