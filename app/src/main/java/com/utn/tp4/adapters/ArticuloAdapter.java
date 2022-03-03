package com.utn.tp4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.utn.tp4.R;
import com.utn.tp4.entidades.Articulo;

import java.util.ArrayList;

public class ArticuloAdapter extends BaseAdapter {

    private ArrayList<Articulo> lista;
    private Context context;

    public ArticuloAdapter(Context context, ArrayList<Articulo> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Articulo getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());//context
        View view = convertView;
        if (convertView == null){
            view = inflater.inflate(R.layout.list_item_cell_articulo, null);
        }

        String tvIdArticulo = String.valueOf(getItem(position).getIdArticulo());
        String tvNombre     = getItem(position).getNombre().toString();
        String tvStock      = String.valueOf(getItem(position).getStock());
        String tvCategoriaArticulo  = String.valueOf(getItem(position).getIdCategoria());
        //String tvCategoriaArticulo  = String.valueOf(getItem(position).getDescripcionCategoria());

        ((TextView) view.findViewById(R.id.tvIdArticulo)).setText(tvIdArticulo);
        ((TextView) view.findViewById(R.id.tvNombre)).setText(tvNombre);
        ((TextView) view.findViewById(R.id.tvStock)).setText(tvStock);
        ((TextView) view.findViewById(R.id.tvCategoriaArticulo)).setText(tvCategoriaArticulo);

        return view;
    }
}
