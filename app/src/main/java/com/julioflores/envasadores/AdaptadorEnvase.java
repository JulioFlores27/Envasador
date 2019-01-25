package com.julioflores.envasadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorEnvase extends ArrayAdapter<Envases> {

    private Context contexto;
    private ArrayList<Envases> listitems;

    public AdaptadorEnvase(Activity context, ArrayList<Envases> pedido_envases) {
        super(context, 0, pedido_envases);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adaptador_envase, parent, false);
        }
        Envases envases = getItem(position);
        TextView t1 = (TextView) listItemView.findViewById(R.id.NoPedido_envase);
        t1.setText(String.valueOf(envases.getNopedidos()));
        TextView t2 = (TextView) listItemView.findViewById(R.id.Fechacap_envase);
        t2.setText(envases.getFechaCapturas());
        TextView ta = (TextView) listItemView.findViewById(R.id.No_Lote);
        ta.setText(String.valueOf(envases.getLote()));
        TextView t3 = (TextView) listItemView.findViewById(R.id.Producto_envase);
        t3.setText(envases.getProductos());
        TextView t4 = (TextView) listItemView.findViewById(R.id.Tipoenvase_envase);
        t4.setText(envases.getTipoenvases());
        TextView tb = (TextView) listItemView.findViewById(R.id.Cantidad_envase);
        tb.setText(String.valueOf(envases.getCantidades()));
        TextView t6 = (TextView) listItemView.findViewById(R.id.Persona_envase);
        t6.setText(envases.getPersonaasignadas());
        TextView t7 = (TextView) listItemView.findViewById(R.id.Etapa_envase);
        t7.setText(envases.getEtapa1s());
        TextView t8 = (TextView) listItemView.findViewById(R.id.Fechasi_envase);
        TextView t9 = (TextView) listItemView.findViewById(R.id.Fechaen_envase);
        t8.setText(envases.getFechaasignadas());
        t9.setText(envases.getFechaenvases());
        return listItemView;
    }
}
