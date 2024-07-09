package com.example.list_view_get_http;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private ListView listView;
  private PersonAdapter adapter;
  private List<Personas> personasList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    listView = findViewById(R.id.listView);
    personasList = new ArrayList<>();
    adapter = new PersonAdapter(this, personasList);
    listView.setAdapter(adapter);

    new GetPersonasTask().execute("http://10.0.2.2/programacion-movil-1-php-crud-parcial-2/peticiones-http/GetPersons.php");
  }

  private class GetPersonasTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
      StringBuilder result = new StringBuilder();

      try {
        URL url = new URL(urls[0]);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
          BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

          String line;

          while ((line = reader.readLine()) != null) {
            result.append(line);
          }
        } finally {
          urlConnection.disconnect();
        }

      } catch (Exception error) {
        Log.d("Error Get Personas", error.toString());
      }
      Log.d("Error Get Personas", result.toString());

      return result.toString();
    }
  }

  protected void onPostExecute(String result) {
    try {
      JSONArray jsonArray = new JSONArray(result);
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);

        String nombres = jsonObject.getString("nombres");
        String apellidos = jsonObject.getString("apellidos");
        String direccion = jsonObject.getString("direccion");
        String telefono = jsonObject.getString("telefono");
        String foto = jsonObject.getString("foto");

        Personas persona = new Personas(nombres, apellidos, direccion, telefono, foto);

        personasList.add(persona);
      }

    } catch (Exception error) {
      Log.d("Error on post execute", error.toString());
    }
  }
}