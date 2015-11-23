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

import adapters.SolicitudPrestamoAdapter;
import models.SolicitudPrestamo;

public class SolicitudesPrestamosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_prestamos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //para los servicios que se tiene que llamar con metodos aincronos
        if (Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setListContent();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void setListContent(){
        //PostAdapter adapter=new PostAdapter(this, R.layout.simple_list_item_post, GetPosts());
        SolicitudPrestamoAdapter adapter=new SolicitudPrestamoAdapter(this, R.layout.simple_list_item_solicitud_prestamo, GetPostsFromWebService());
        ListView list=(ListView)findViewById(R.id.lvSolicitudPrestamos);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SolicitudPrestamoDetailActivity.class);
                intent.putExtra("CurrentPostUsuario", ((TextView) view.findViewById(R.id.txtIdCliente)).getText().toString());
                startActivity(intent);
            }
        });
    }

    private List<SolicitudPrestamo> GetPostsFromWebService(){
        List<SolicitudPrestamo> posts=new ArrayList<>();
        //URLConnection
        HttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet("http://192.168.56.1/Triunfadores.Api/api/SolicitudesPrestamos");

        try{
            HttpResponse httpResponse=httpClient.execute(httpGet);
            String stringResponse= EntityUtils.toString(httpResponse.getEntity());

            JSONArray jsonResponse=new JSONArray(stringResponse);
            for (int i=0; i<jsonResponse.length(); i++){
                SolicitudPrestamo solicitudPrestamo=new SolicitudPrestamo();
                JSONObject obj=jsonResponse.getJSONObject(i);

                solicitudPrestamo.Cantidad=obj.getString("Cantidad");
                solicitudPrestamo.Comentario=obj.getString("Comentario");
                solicitudPrestamo.FechSolicitud=obj.getString("FechSolicitud");
                solicitudPrestamo.TasaInteres=obj.getString("TasaInteres");
                solicitudPrestamo.TipoPago=obj.getString("TipoPago");

                posts.add(solicitudPrestamo);
            }
            //JsonObject - JsonArray
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return posts;
    }


}
