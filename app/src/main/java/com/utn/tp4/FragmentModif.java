package com.utn.tp4;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
public class FragmentModif extends Fragment {
	private View view;
	public static final String TITULO = "MODIFICAR";
    private int idArticuloAModif = 0;


    public FragmentModif() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_modif, container, false);
        cargarSpnCategoriaArticulo();
        mostrarControlesModif(false);

        ((Button) view.findViewById(R.id.btnBuscar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	btnBuscarArticulo();
            }
		});

        ((Button) view.findViewById(R.id.btnGuardar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGuardarModificaciones();
            }
        });
        return view;
    }

    private void btnBuscarArticulo(){
        String mensaje = null;
        try {
            // 1) Obtener datos cargados en el layout
                String etIdArticuloABuscar  = ((EditText) view.findViewById(R.id.etIdArticuloABuscar)).getText().toString();
                if(etIdArticuloABuscar.trim().isEmpty())
                    throw new ValidacionException("Complete el campo de búsqueda");
                int idArticulo = Integer.parseInt(etIdArticuloABuscar);
            // 2) Obtener registros del id a buscar
                ArticuloDAO articuloDAO = new ArticuloDAO(ConstantesDB.idSelectBy, idArticulo);
                articuloDAO.execute().get();
                Articulo objArticulo = articuloDAO.getObject();

                if(objArticulo == null){
                	mostrarControlesModif(false);
                    mensaje = String.format("No se encontraron registros del articulo con ID: %d", idArticulo);
                    throw new ValidacionException(mensaje);
                }
            // 3) Mostrar los valores del objeto encontrado
                String etNombre     = objArticulo.getNombre().toString();
                String etStock      = String.valueOf(objArticulo.getStock());
                final int idCategoriaArticulo  = objArticulo.getIdCategoria();

                ((EditText) view.findViewById(R.id.etNombre)).setText(etNombre);
                ((EditText) view.findViewById(R.id.etStock)).setText(etStock);

                final Spinner spnCategoriaArticulo = (Spinner) view.findViewById(R.id.spnCategoriaArticulo);
                spnCategoriaArticulo.post(new Runnable(){
                    @Override
                    public void run(){
                        spnCategoriaArticulo.setSelection(idCategoriaArticulo-1);
                    }
                });
                mensaje = String.format("Se cargaron los registros del articulo con ID: %d", idArticulo);
                idArticuloAModif = idArticulo;
                mostrarControlesModif(true);
        } catch (Exception e) {
            mensaje = e.getMessage();
        } finally{
            Toast.makeText(getContext().getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        }                
    }
    

    private void btnGuardarModificaciones(){
        String mensaje = null;
        try {
            // 1) Obtener datos cargados en el layout
                String etNombre  = ((EditText) view.findViewById(R.id.etNombre)).getText().toString();
                String etStock   = ((EditText) view.findViewById(R.id.etStock)).getText().toString();
                Spinner spnCategoriaArticulo = (Spinner) view.findViewById(R.id.spnCategoriaArticulo);

            // 2) Validar los datos cargados en el layout
                if(etNombre.isEmpty() || etStock.isEmpty())
                    throw new ValidacionException("Debe completar todos los campos del formulario de alta");

                if(!Utilitario.esSoloNros(etStock))
                    throw new ValidacionException("Ingrese un valor válido para el stock");

                if(!(Integer.parseInt(etStock) >= 0))
                    throw new ValidacionException("El stock debe ser mayor o igual a cero");


                Categoria objCategoria = (Categoria) spnCategoriaArticulo.getSelectedItem();
                int idCategoria = objCategoria.getIdCategoria();
                
                int stock = Integer.parseInt(etStock);
            // 3) Crear objeto ya validado
                Articulo objArticulo = new Articulo();
                objArticulo.setIdArticulo(idArticuloAModif);
                objArticulo.setNombre(etNombre);
                objArticulo.setStock(stock);
                objArticulo.setIdCategoria(idCategoria);
            // 4) Guardar objeto en BBDD y verificar transacción
            ArticuloDAO articuloDAO = new ArticuloDAO(ConstantesDB.idUpdate, objArticulo);
            articuloDAO.execute().get();
                int cantFilasAfectadas = articuloDAO.getCantFilasAfectadas();
                if (!(cantFilasAfectadas > 0)) {
                    throw new Exception("SQL: Ocurrió un error al guardar el registro modificado");
                }
            // 5) Informar resultados obtenidos
                mensaje = String.format("Éxito: El articulo fue modificado con éxito. nroID: %d", idArticuloAModif);
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


    private void mostrarControlesModif(boolean isMostrar){
        ScrollView frmModifScrollView = (ScrollView) view.findViewById(R.id.frmModifScrollView);
        EditText obj_etNombre   = (EditText) view.findViewById(R.id.etNombre);
        EditText obj_etStock    = (EditText) view.findViewById(R.id.etStock);
        Spinner obj_spnCategoriaArticulo = (Spinner) view.findViewById(R.id.spnCategoriaArticulo);
        Button obj_btnGuardar    = ((Button) view.findViewById(R.id.btnGuardar));
        if(isMostrar){
            frmModifScrollView.setVisibility(View.VISIBLE);
            obj_etNombre.setVisibility(View.VISIBLE);
            obj_etStock.setVisibility(View.VISIBLE);
            obj_spnCategoriaArticulo.setVisibility(View.VISIBLE);
            obj_btnGuardar.setVisibility(View.VISIBLE);
        } else{
            frmModifScrollView.setVisibility(View.INVISIBLE);
            obj_etNombre.setVisibility(View.INVISIBLE);
            obj_etStock.setVisibility(View.INVISIBLE);
            obj_spnCategoriaArticulo.setVisibility(View.INVISIBLE);
            obj_btnGuardar.setVisibility(View.INVISIBLE);
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
