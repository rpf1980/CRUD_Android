package com.example.solucioncrud_david_android;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.IOException;
        import java.util.concurrent.TimeUnit;

        import okhttp3.Call;
        import okhttp3.MultipartBody;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.RequestBody;
        import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{

    EditText edtName, edtEmail, edtPassword;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asociamos los id
        edtName = findViewById(R.id.idEdtName);
        edtEmail = findViewById(R.id.idEdtEmail);
        edtPassword = findViewById(R.id.idEdtPassword);
        btn = findViewById(R.id.idBtn);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String pass = edtPassword.getText().toString();

                Log.d("MainActivity", name + email + pass);

                //Los datos que le enviamos al servidor (como los parámetros pueden ir cambiando debe ir dentro de la lógica dinámica)
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("name", name)
                        .addFormDataPart("email", email)
                        .addFormDataPart("password", pass)
                        .build();

                // Objeto call que sería nuestra llamada y la tenemos que lanzar en la clase asíncrona
                Call call = HTTPRequests.POST("addLogin.php", requestBody);

                new createLogin(call).execute();
            }
        });
    }

    //Debemos usar una clase asíncrona para realizar la petición
    //Crearemos cada clase asíncrona correspondiente a las peticiones (GET, POST, ...)
    class createLogin extends AsyncTask<String, Void, String>
    {

        Call call;

        // Constructor en el que inicializamos los parámetros (asignando el call de la clase)
        createLogin(Call call) {
            Log.d("MainActivity", "Sending Login to API");
            this.call = call;
        }

        // Aquí es donde se realiza la petición HTTP
        @Override
        protected String doInBackground(String... params) {
            try {
                Response res = call.execute(); //Lanza la petición http

                String response = res.body().string(); //Obtenemos la respuesta

                res.close();

                return response; // y la devolvemos

            } catch (IOException e) {

                e.printStackTrace();

                return "error";
            }
        }


        // Método encargado de interaccionar con la interfaz
        @Override
        protected void onPostExecute(String res) {

            Log.d("MainActivity", res);
        }
    }
}
