package com.utn.tp4.daoImpl;

import com.utn.tp4.entidades.Categoria;
import com.utn.tp4.utilitario.ConstantesDB;
import com.utn.tp4.utilitario.CrearConexionMySQL;
import com.utn.tp4.utilitario.IConnectable;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class CategoriaDAO extends AsyncTask<String, Void, String> implements IConnectable<Categoria> {

    private int idCategoria = 0;
    private int tipoTransaccion;
    private int cantFilasAfectadas = 0;
    private String TABLA = "categoria";
    private String SELECT_ALL = String.format("SELECT * FROM %s", TABLA);
    private ArrayList<Categoria> lista;
    private Categoria objCategoria;

    public CategoriaDAO() {
    }

    public CategoriaDAO(int _tipoTransaccion) {
        tipoTransaccion = _tipoTransaccion;
    }

    public CategoriaDAO(int _tipoTransaccion, Categoria _objCategoria) {
        tipoTransaccion = _tipoTransaccion;
        objCategoria = _objCategoria;
    }

    public CategoriaDAO(int _tipoTransaccion, int _idCategoria) {
        tipoTransaccion = _tipoTransaccion;
        idCategoria = _idCategoria;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String doInBackground(String... strings) {
        //Boolean pudoCompletarTransaccionDB = true;
        String mensajeHilo = "OK";
        try {
            switch (tipoTransaccion) {
                case ConstantesDB.idSelectAll:
                    selectAll();
                    break;
                case ConstantesDB.idSelectBy:
                    objCategoria = selectAllByID(idCategoria);
                    break;
                case ConstantesDB.idInsert:
                    cantFilasAfectadas = insert();
                    break;
                case ConstantesDB.idUpdate:
                    break;
                case ConstantesDB.idDelete:
                    break;
                /*case ConstantesDB.idVerificarExistencia:
                    if (existeRegistro(idArticulo))
                        return ConstantesDB.existe;
                    else
                        return ConstantesDB.noExiste;
                    */
                default:
                    mensajeHilo = "tipoTransaccion No reconocida";
                    break;
            }

        } catch (Exception e) {
            mensajeHilo = e.getMessage();
            //e.printStackTrace();
        }

        return mensajeHilo;
    }


    @Override
    public ArrayList<Categoria> getAll() throws Exception {
        return lista;
    }

    @Override
    public Categoria get(int id) throws Exception {
        return null;
    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public int insert(Categoria obj) throws Exception {
        return 0;
    }


    public int insert() throws Exception {
        Categoria obj = objCategoria;
        String sql = "{ call CategoriaInsert(?, ?, ?, ?, ?) }";
        Connection connection = new CrearConexionMySQL().getConnection();
        CallableStatement cs = connection.prepareCall(sql);
        cs.setInt("id", obj.getIdCategoria());
        cs.setString("descripcion", obj.getDescripcion());

        cs.registerOutParameter(1, Types.INTEGER);
        cs.execute();   //cs.executeUpdate();

        int idGenerado = cs.getInt(1);
        cs.getInt("idGenerado");
        connection.close();

        return idGenerado;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectAll() throws Exception {
        Connection connection = new CrearConexionMySQL().getConnection();
        ResultSet rs = connection.createStatement().executeQuery(SELECT_ALL);
        lista = new ArrayList<Categoria>();
        while (rs.next()) {
            Categoria obj = new Categoria();
            obj.setIdCategoria(rs.getInt("id"));
            obj.setDescripcion(rs.getString("descripcion"));

            lista.add(obj);
        }

        connection.close();
    }
/*
    private int update() throws Exception {
        String sql = String.format(" UPDATE categoria" +
                        " SET nombre      = %s" +
                        " ,   stock       = %d" +
                        " ,   idCategoria = %d" +
                        " WHERE id        = %d" +
                        " RETURNING id INTO ?"
                , objArticulo.getNombre(), objArticulo.getStock()
                , objArticulo.getIdCategoria(), objArticulo.getIdArticulo());

        Connection connection = new CrearConexionMySQL().getConnection();
        CallableStatement cs = connection.prepareCall(sql);
        cs.registerOutParameter(1, Types.INTEGER);
        cantFilasAfectadas = cs.executeUpdate();
        connection.close();

        return cantFilasAfectadas;
    }

    public int getCantFilasAfectadas() {
        return cantFilasAfectadas;
    }


    private boolean existeRegistro(int idArticulo) throws Exception {
        boolean existeRegistro = false;
        String query = " SELECT COUNT(*)" +
                " FROM articulo " +
                " WHERE id= " + idArticulo;

        Connection connection = new CrearConexionMySQL().getConnection();
        ResultSet rs = connection.createStatement().executeQuery(query);
        rs.next();
        int cantRegistrosEncontrados = rs.getInt(1);
        if (cantRegistrosEncontrados > 0)
            existeRegistro = true;
        else
            existeRegistro = false;

        connection.close();

        return existeRegistro;
    }

*/
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Categoria selectAllByID(int idCategoria) throws Exception {
        String query = SELECT_ALL +
                " WHERE id= " + idCategoria;

        Connection connection = new CrearConexionMySQL().getConnection();
        ResultSet rs = connection.createStatement().executeQuery(query);
        //rs.next();

        Categoria obj = new Categoria();
        while (rs.next()) {
            obj.setIdCategoria(rs.getInt("id"));
            obj.setDescripcion(rs.getString("descripcion"));
        }

        connection.close();

        return obj;
    }
    public Categoria getObject() throws Exception {
        return objCategoria;
    }


}
