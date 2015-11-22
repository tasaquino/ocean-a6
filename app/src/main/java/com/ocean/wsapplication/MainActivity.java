package com.ocean.wsapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.ocean.wsapplication.bean.Previsao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    public static final String URL_CONTENT = "http://api.openweathermap.org/data/2.5/weather?units=metric&appid=2de143494c0b295cca9337e1e96b00e0";
    public static final String URL_IMAGES = "http://openweathermap.org/img/w/";
    private TextView textResultado;
    private Button botaoBuscar;
    private ImageView imagem;
    private EditText editCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VolleySingletown.init(this);

        textResultado = (TextView) findViewById(R.id.text_resultado);
        botaoBuscar = (Button) findViewById(R.id.botao_buscar);
        imagem = (ImageView) findViewById(R.id.imagem);
        editCidade = (EditText) findViewById(R.id.edit_buscar);

        botaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editCidade.getText().toString())) {
                    requestContent();
                }
            }
        });
    }

    private void requestContent() {
        String cidade = "";
        try {
            cidade = URLEncoder.encode(editCidade.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("WSApplication", e.getMessage());
        }

        String query = "&q=" + cidade;

        StringRequest request = new StringRequest(Request.Method.GET, URL_CONTENT + query, onSuccess(), onError());
        RequestQueue queue = VolleySingletown.getRequestQueue();
        queue.add(request);
    }

    private Response.Listener<String> onSuccess() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("WSApplication", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Previsao previsao = new Previsao();
                    JSONObject jsonMain = jsonObject.getJSONObject("main");
                    previsao.setTemperatura(jsonMain.getInt("temp"));
                    JSONArray jsonWeather = jsonObject.getJSONArray("weather");
                    previsao.setCondicao(jsonWeather.getJSONObject(0).getString("description"));
                    previsao.setIcone(jsonWeather.getJSONObject(0).optString("icon"));

                    textResultado.setText("Temperatura: " + previsao.getTemperatura() + "\n\nCondição: " + previsao.getCondicao());

                    VolleySingletown.getImageLoader().get(URL_IMAGES + previsao.getIcone() + ".png", ImageLoader
                            .getImageListener(imagem, 0,
                                    0));
                } catch (JSONException e) {
                    Log.e("WSApplication", e.getMessage());
                }
            }
        };
    }

    private Response.ErrorListener onError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Houve um erro :(", Toast.LENGTH_LONG).show();
                Log.e("WSApplication", error.getMessage());
            }
        };
    }
}