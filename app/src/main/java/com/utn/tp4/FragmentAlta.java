package com.utn.tp4;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.utn.tp4.daoImpl.ArticuloDAO;
import com.utn.tp4.daoImpl.CategoriaDAO;
import com.utn.tp4.entidades.Articulo;
import com.utn.tp4.entidades.Categoria;
import com.utn.tp4.utilitario.ConstantesDB;
import com.utn.tp4.utilitario.Utilitario;
import com.utn.tp4.utilitario.ValidacionException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAlta extends Fragment {
	private View view;
	public static final String TITULO = "ALTA";

    public FragmentAlta() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alta, container, false);
        cargarSpnCategoriaArticulo();
        ((Button) view.findViewById(R.id.btnGuardar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	btnGuardar();
            }
		});
        return view;
    }

    private void btnGuardar(){
    	String mensaje = null;
    	try {
	    	// 1) Obtener datos cargados en el layout
		    	String etIdArticulo	 = ((EditText) view.findViewById(R.id.etIdArticulo)).getText().toString();
				String etNombre	 = ((EditText) view.findViewById(R.id.etNombre)).getText().toString();
				String etStock	 = ((EditText) view.findViewById(R.id.etStock)).getText().toString();
				Spinner spnCategoriaArticulo = (Spinner) view.findViewById(R.id.spnCategoriaArticulo);
			// 2) Validar los datos cargados en el layout
				if(etIdArticulo.isEmpty() || etNombre.isEmpty() || etStock.isEmpty())
					throw new ValidacionException("Debe completar todos los campos del formulario de alta");

				if(!Utilitario.esSoloNros(etIdArticulo))
				    throw new ValidacionException("Ingrese un valor válido para el ID de articulo");

				if(!(Integer.parseInt(etIdArticulo) > 0))
				    throw new ValidacionException("debe ser mayor a cero");

				if(!Utilitario.esSoloNros(etStock))
				    throw new ValidacionException("Ingrese un valor válido para el stock");

				if(!(Integer.parseInt(etStock) >= 0))
				    throw new ValidacionException("El stock debe ser mayor o igual a cero");

				int idArticulo = Integer.parseInt(etIdArticulo);

				String existeRegistro = new ArticuloDAO(ConstantesDB.idVerificarExistencia, idArticulo).execute().get();
		        if (existeRegistro.equals(ConstantesDB.existe)) {
		            mensaje = String.format("Ya se encuentra registrado el articulo con ID: %d", idArticulo);
		            throw new ValidacionException(mensaje);
		        }

				Categoria objCategoria = (Categoria) spnCategoriaArticulo.getSelectedItem();
		        int idCategoria = objCategoria.getIdCategoria();
				
				int stock = Integer.parseInt(etStock);
	        // 3) Crear objeto ya validado
				Articulo objArticulo = new Articulo();
				objArticulo.setIdArticulo(idArticulo);
				objArticulo.setNombre(etNombre);
				objArticulo.setStock(stock);
				objArticulo.setIdCategoria(idCategoria);
			// 4) Guardar objeto en BBDD y verificar transacción
		        ArticuloDAO articuloDAO = new ArticuloDAO(ConstantesDB.idInsert, objArticulo);
				articuloDAO.execute().get();
		        int idGenerado = articuloDAO.getCantFilasAfectadas();
	            if (!(idGenerado > 0)) {
	                throw new Exception("SQL: Ocurrió un error al guardar el registro");
	            }
			// 5) Informar resultados obtenidos
	            mensaje = String.format("Éxito: El articulo fue registrado con éxito con el nroID: %d", idGenerado);
        } catch (Exception e) {
            mensaje = e.getMessage();
        } finally{
            //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(getContext().getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
            //Toast toast = Toast.makeText(getContext().getApplicationContext(), mensaje, Toast.LENGTH_LONG);
            //toast.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);
            //toast.show();
        }

    }


    private void cargarSpnCategoriaArticulo(){
        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO(ConstantesDB.idSelectAll);
			categoriaDAO.execute().get();
            ArrayList<Categoria> listaCategoria = categoriaDAO.getAll();

            ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getContext(), android.R.layout.simple_spinner_item, listaCategoria);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spnCategoriaArticulo = (Spinner) view.findViewById(R.id.spnCategoriaArticulo);
            //Spinner spnCategoriaArticulo =  view.findViewById(R.id.spnCategoriaArticulo);
            spnCategoriaArticulo.setAdapter(adapter);
        } catch (Exception e) {
            //e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
