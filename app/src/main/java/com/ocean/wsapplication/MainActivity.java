package com.ocean.wsapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    public static final String URL_CONTENT = "http://api.openweathermap.org/data/2.5/weather?q=sao%20paulo&appid=2de143494c0b295cca9337e1e96b00e0";

    private TextView textResultado;
    private Button botaoBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResultado = (TextView)findViewById(R.id.text_resultado);
        botaoBuscar = (Button)findViewById(R.id.botao_buscar);

        botaoBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestContent();
            }
        });
    }

    private void requestContent() {
        StringRequest request = new StringRequest(Request.Method.GET, URL_CONTENT, onSuccess(), onError());

        RequestQueue queue = getQueue(this);
        queue.add(request);
    }

    private Response.Listener<String> onSuccess() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("WSApplication", response);

                textResultado.setText(response);
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

    private RequestQueue getQueue(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        return requestQueue;
    }
}