package com.julioflores.envasadores;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class Envasador_Joseluis extends Fragment {
    View v;
    private AdaptadorEnvase adaptadores;
    SwipeRefreshLayout swipeRefreshLayout;
    SwipeMenuListView listanombres;
    AsyncHttpClient cliente;
    Calendar calendariocompleto;

    Handler customHandler = new Handler();

    public class contar extends CountDownTimer {
        public contar(long milienfuturo, long countdowninterval){
            super(milienfuturo,countdowninterval);
        }
        @Override
        public void onTick(long millisUntilFinished) { }
        public void onFinish(){
            ObtenerEnvases3();
            //Toast.makeText(getActivity(), "Actualizado",Toast.LENGTH_SHORT).show();
        }
    }
    private Runnable actualizartimer = new Runnable() {
        @Override
        public void run() {
            Envasador_Joseluis.contar tiempo = new Envasador_Joseluis.contar(45000, 45000);
            tiempo.start();
            customHandler.postDelayed(this, 45000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        listanombres = (SwipeMenuListView) v.findViewById(R.id.listan);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefreshlayout);
        cliente = new AsyncHttpClient();
        customHandler.postDelayed(actualizartimer, 45000);
        ObtenerEnvases3();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager conectividad = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo lanet = conectividad.getActiveNetworkInfo();
                if(lanet != null && lanet.isConnected()){
                    ObtenerEnvases3();
                    listanombres.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                    listanombres.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return v;
    }
    private void ObtenerEnvases3(){
        String jose = "Jose Luis";
        String url = "https://appsionmovil.000webhostapp.com/consultar_envase_envasador.php?PersonaAsignada="+ jose.replaceAll(" ", "%20");
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    listarEnvases3(new String(responseBody));
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    public void listarEnvases3(String respuesta){
        final ArrayList<Envases> lista = new ArrayList<Envases>();

        try{
            final JSONArray jsonarreglo = new JSONArray(respuesta);
            for (int i=0; i<jsonarreglo.length(); i++){
                Envases t = new Envases();
                t.setIds(jsonarreglo.getJSONObject(i).getInt("ID"));
                t.setNopedidos(jsonarreglo.getJSONObject(i).getInt("NoPedidos"));
                t.setProductos(jsonarreglo.getJSONObject(i).getString("Producto"));
                t.setCantidades(jsonarreglo.getJSONObject(i).getInt("Cantidad"));
                t.setEtapa1s(jsonarreglo.getJSONObject(i).getString("Etapa1"));
                t.setFechaCapturas(jsonarreglo.getJSONObject(i).getString("FechaCaptura"));
                t.setFechaaprobaciones(jsonarreglo.getJSONObject(i).getString("FechaAprobacion"));
                t.setFechaasignadas(jsonarreglo.getJSONObject(i).getString("FechaAsignacion"));
                t.setFechaenvases(jsonarreglo.getJSONObject(i).getString("FechaEnvase"));
                t.setPersonaasignadas(jsonarreglo.getJSONObject(i).getString("PersonaAsignada"));
                t.setTipoenvases(jsonarreglo.getJSONObject(i).getString("TipoEnvase"));
                t.setLote(jsonarreglo.getJSONObject(i).getInt("Lote"));
                t.setCantidades2(jsonarreglo.getJSONObject(i).getInt("Cantidad2"));
                t.setLote2(jsonarreglo.getJSONObject(i).getInt("Lote2"));
                t.setCantidades3(jsonarreglo.getJSONObject(i).getInt("Cantidad3"));
                t.setLote3(jsonarreglo.getJSONObject(i).getInt("Lote3"));
                lista.add(t);
            }
            adaptadores = new AdaptadorEnvase(getActivity(), lista);
            //ArrayAdapter<Envases> a = new ArrayAdapter<Envases>(this,android.R.layout.simple_list_item_1, lista);
            listanombres.setAdapter(adaptadores);
            listanombres.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    Date fechahora = calendariocompleto.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    final String dias = dateFormat.format(fechahora);
                    final Envases esteenvase = (Envases) listanombres.getItemAtPosition(position);
                    final AlertDialog.Builder mibuild1 = new AlertDialog.Builder(getActivity());
                    final View mviewd = getLayoutInflater().inflate(R.layout.opcion_dialogo, null);
                    final Button botonter = (Button) mviewd.findViewById(R.id.terminadodia);
                    Button botonpro = (Button) mviewd.findViewById(R.id.problemadia);
                    mibuild1.setTitle("Seleccione Opción:");
                    botonter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String ids = String.valueOf(esteenvase.getIds());
                            ConnectivityManager conectividad = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo lanet = conectividad.getActiveNetworkInfo();
                            if(lanet != null && lanet.isConnected()){
                                String cant = String.valueOf(esteenvase.getCantidades());
                                String cant2 = String.valueOf(esteenvase.getCantidades2());
                                String cant3 = String.valueOf(esteenvase.getCantidades3());
                                String lots = String.valueOf(esteenvase.getLote());
                                String lots2 = String.valueOf(esteenvase.getLote2());
                                String lots3 = String.valueOf(esteenvase.getLote3());
                                final int c11 = Integer.parseInt(cant);
                                final int l11 = Integer.parseInt(lots);
                                final int c22 = Integer.parseInt(cant2);
                                final int l22 = Integer.parseInt(lots2);
                                int c33 = Integer.parseInt(cant3);
                                int l33 = Integer.parseInt(lots3);
                                if(c11 == 0 || l11 == 0) {
                                    View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
                                    mibuild1.setTitle("Terminado");
                                    final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
                                    t1.setText(cant);
                                    final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
                                    t2.setText(lots);
                                    mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                            if(lanet1 != null && lanet1.isConnected()){
                                                String vacio = "";
                                                String lt1 = t1.getText().toString();
                                                t1.setText(lt1);
                                                String rlots = t2.getText().toString();
                                                t2.setText(rlots);
                                                int cant1 = Integer.parseInt(lt1);
                                                int lote1 = Integer.parseInt(rlots);
                                                if(cant1 != 0 && lote1 != 0){
                                                    String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
                                                            "&Etapa1=Terminado&Cantidad=" + lt1 +
                                                            "&DetalleProblema=" + vacio.replaceAll("", "%20") +
                                                            "&Lote=" + rlots + "&Cantidad2=0&Lote2=0&Cantidad3=0&Lote3=0" +
                                                            "&ID=" + ids;
                                                    Toast.makeText(getActivity(), "Terminado", Toast.LENGTH_SHORT).show();
                                                    cliente.post(url, new AsyncHttpResponseHandler() {
                                                        @Override
                                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
                                                        @Override
                                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
                                                    });
                                                }else{
                                                    Toast.makeText(getActivity(), "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                                listanombres.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });
                                    mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                            if(lanet1 != null && lanet1.isConnected()) {
                                                dialog.cancel();
                                            }else{
                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                                listanombres.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });
                                    mibuild1.setView(mview1);
                                    AlertDialog dialog = mibuild1.create();
                                    dialog.show();
                                } else if (c22 == 0 || l22 == 0){
                                    View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
                                    mibuild1.setTitle("Terminado");
                                    final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
                                    t1.setText(cant);
                                    final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
                                    t2.setText(lots);
                                    mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                            if(lanet1 != null && lanet1.isConnected()){
                                                String vacio = "";
                                                String lt2 = t1.getText().toString();
                                                t1.setText(lt2);
                                                String rlots2 = t2.getText().toString();
                                                t2.setText(rlots2);
                                                int cant1 = Integer.parseInt(lt2);
                                                int lote1 = Integer.parseInt(rlots2);
                                                if(cant1 != 0 && lote1 != 0){
                                                    String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
                                                            "&Etapa1=Terminado&Cantidad="+ c11 +
                                                            "&DetalleProblema=" + vacio.replaceAll("","%20") +
                                                            "&Lote=" + l11 + "&Cantidad2="+ lt2 + "&Lote2="+ rlots2 +
                                                            "&Cantidad3=0&Lote3=0"+
                                                            "&ID=" + ids;
                                                    Toast.makeText(getActivity(), "Terminado", Toast.LENGTH_SHORT).show();
                                                    cliente.post(url, new AsyncHttpResponseHandler() {
                                                        @Override
                                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
                                                        @Override
                                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
                                                    });
                                                }else{
                                                    Toast.makeText(getActivity(), "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                                listanombres.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });
                                    mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    mibuild1.setView(mview1);
                                    AlertDialog dialog = mibuild1.create();
                                    dialog.show();
                                }else if (c33 == 0 || l33 == 0){
                                    View mview1 = getLayoutInflater().inflate(R.layout.terminado_dialogo, null);
                                    mibuild1.setTitle("Terminado");
                                    final EditText t1 = (EditText) mview1.findViewById(R.id.cantis);
                                    t1.setText(cant2);
                                    final EditText t2 = (EditText) mview1.findViewById(R.id.lotes);
                                    t2.setText(lots2);
                                    mibuild1.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                            if(lanet1 != null && lanet1.isConnected()){
                                                String vacio = "";
                                                String lt3 = t1.getText().toString();
                                                t1.setText(lt3);
                                                String rlots3 = t2.getText().toString();
                                                t2.setText(rlots3);
                                                int cant1 = Integer.parseInt(lt3);
                                                int lote1 = Integer.parseInt(rlots3);
                                                if(cant1 != 0 && lote1 != 0){
                                                    String url = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
                                                            "&Etapa1=Terminado&Cantidad="+ c11 + "&DetalleProblema=" + vacio.replaceAll("","%20") +
                                                            "&Lote=" + l11 + "&Cantidad2="+ c22 + "&Lote2="+ l22 + "&Cantidad3="+ lt3 +"&Lote3="+ rlots3 +
                                                            "&ID=" + ids;
                                                    Toast.makeText(getActivity(), "Terminado", Toast.LENGTH_SHORT).show();
                                                    cliente.post(url, new AsyncHttpResponseHandler() {
                                                        @Override
                                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
                                                        @Override
                                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
                                                    });
                                                }else{
                                                    Toast.makeText(getActivity(), "No se guardo, ingrese un valor mayor que 0",Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                                listanombres.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });
                                    mibuild1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                            NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                            if(lanet1 != null && lanet1.isConnected()){
                                                dialog.cancel();
                                            }else{
                                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                                listanombres.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });
                                    mibuild1.setView(mview1);
                                    AlertDialog dialog = mibuild1.create();
                                    dialog.show();
                                }else if(c33 != 0 && l33 != 0 && c33 != 0 && l33 != 0 && c33 != 0 && l33 != 0){
                                    Toast.makeText(getActivity(), "Ha llego al limite de Intentos de Cantidad/Lote", Toast.LENGTH_SHORT).show();
                                } else { }
                            }else{
                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                listanombres.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    botonpro.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String ids = String.valueOf(esteenvase.getIds());
                            ConnectivityManager conectividad = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo lanet = conectividad.getActiveNetworkInfo();
                            if(lanet != null && lanet.isConnected()){
                                final String cant1 = String.valueOf(esteenvase.getCantidades());
                                final String cant21 = String.valueOf(esteenvase.getCantidades2());
                                final String cant31 = String.valueOf(esteenvase.getCantidades3());
                                final String lots1 = String.valueOf(esteenvase.getLote());
                                final String lots21 = String.valueOf(esteenvase.getLote2());
                                final String lots31 = String.valueOf(esteenvase.getLote3());
                                //Toast.makeText(getActivity(), cant1+cant21+cant31,Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder mibuild = new AlertDialog.Builder(getActivity());
                                final View mview = getLayoutInflater().inflate(R.layout.problema_dialogo, null);
                                mibuild.setTitle("Problema");
                                mibuild.setMessage("Anota su problema:");
                                final AlertDialog.Builder builder = mibuild.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                        NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                        if(lanet1 != null && lanet1.isConnected()) {
                                            EditText p1 = (EditText) mview.findViewById(R.id.problemas);
                                            Date fechahora = Calendar.getInstance().getTime();
                                            String pr1 = p1.getText().toString();
                                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                                            if(pr1.isEmpty()){
                                                Toast.makeText(getActivity(), "No se guardo, favor de escribir sus problema", Toast.LENGTH_SHORT).show();
                                            }else {
                                                String url2 = "https://appsionmovil.000webhostapp.com/asignar_pedidoenvasar.php?FechaAprobacion=" + dias.replaceAll(" ", "%20") +
                                                        "&Etapa1=Terminado&Cantidad="+ cant1 + "&DetalleProblema=" + pr1.replaceAll(" ","%20") +
                                                        "&Lote=" + lots1 + "&Cantidad2="+ cant21 + "&Lote2="+ lots21 + "&Cantidad3="+ cant31 +"&Lote3="+ lots31 +
                                                        "&ID=" + ids;
                                                Toast.makeText(getActivity(), "Problema Enviada", Toast.LENGTH_SHORT).show();
                                                cliente.post(url2, new AsyncHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) { if (statusCode == 200) { } }
                                                    @Override
                                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) { }
                                                });
                                            }
                                        }else {
                                            Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                            listanombres.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                                mibuild.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ConnectivityManager conectividad1 = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                        NetworkInfo lanet1 = conectividad1.getActiveNetworkInfo();
                                        if(lanet1 != null && lanet1.isConnected()) {
                                            dialog.cancel();
                                        } else {
                                            Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                            listanombres.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                                mibuild.setView(mview);
                                AlertDialog dialog1 = mibuild.create();
                                dialog1.show();
                            } else{
                                Toast.makeText(getActivity(), "No hay Internet, intentarlo más tarde o verifica su conexión",Toast.LENGTH_SHORT).show();
                                listanombres.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    mibuild1.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
                    });
                    mibuild1.setView(mviewd).create().show();
                }
            });
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }

}
