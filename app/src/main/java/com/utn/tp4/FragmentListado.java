package com.utn.tp4;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.utn.tp4.adapters.ArticuloAdapter;
import com.utn.tp4.daoImpl.ArticuloDAO;
import com.utn.tp4.entidades.Articulo;
import com.utn.tp4.utilitario.ConstantesDB;
import com.utn.tp4.utilitario.ValidacionException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListado extends Fragment {
	private View view;
	public static final String TITULO = "LISTADO";

	public FragmentListado() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_listado, container, false);
		cargarListadoDeArticulos();
		return view;
	}


	private void cargarListadoDeArticulos(){
		String mensaje=null;
		try{
			ArticuloDAO articuloDAO = new ArticuloDAO(ConstantesDB.idSelectAll);
			articuloDAO.execute().get();
			ArrayList<Articulo> listaArticulos = articuloDAO.getAll();
			if (listaArticulos == null || listaArticulos.size() == 0)
				throw new ValidacionException("Aún no tiene Articulos cargados");

			ArticuloAdapter adapter = new ArticuloAdapter(getActivity().getApplicationContext(), listaArticulos);
			GridView gridView = (GridView) view.findViewById(R.id.grdListadoArticulos);
			gridView.setAdapter(adapter);
			mensaje = String.format("Listado cargado con éxito");
		} catch (Exception e) {
			mensaje = e.getMessage();
		} finally{
			Toast.makeText(getContext().getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
		}
	}


}
