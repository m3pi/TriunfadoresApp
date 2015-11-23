package imerosa.triunfadoresapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.AccionesAdapter;
import adapters.SolicitudPrestamoAdapter;
import models.SolicitudPrestamo;
import models.VentaAccion;

public class ListAccionesCompradasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_acciones_compradas);

        if (Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setListContent();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setListContent(){
        //PostAdapter adapter=new PostAdapter(this, R.layout.simple_list_item_post, GetPosts());
        AccionesAdapter adapter=new AccionesAdapter(this, R.layout.simple_list_item_acciones, GetAccionesCompradas());
        ListView list=(ListView)findViewById(R.id.lv_AccionesCompradas);
        list.setAdapter(adapter);

    }

    private List<VentaAccion> GetAccionesCompradas(){
        List<VentaAccion> acciones=new ArrayList<>();
        //URLConnection
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet("http://192.168.43.91/TriunfadoresApi/api/Acciones");

        try{
            HttpResponse httpResponse=httpClient.execute(httpGet);
            String stringResponse= EntityUtils.toString(httpResponse.getEntity());

            JSONArray jsonResponse=new JSONArray(stringResponse);
            for (int i=0; i<jsonResponse.length(); i++){
                VentaAccion ventaAccion=new VentaAccion();
                JSONObject obj=jsonResponse.getJSONObject(i);

                ventaAccion.FechaVenta=obj.getString("FechaVenta");
                ventaAccion.Estado=obj.getString("Estado");
                ventaAccion.Total=obj.getString("Total");

                acciones.add(ventaAccion);
            }
            //JsonObject - JsonArray
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return acciones;
    }



}
