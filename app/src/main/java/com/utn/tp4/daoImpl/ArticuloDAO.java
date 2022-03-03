package com.utn.tp4.daoImpl;

import com.utn.tp4.entidades.Articulo;
import com.utn.tp4.utilitario.ConstantesDB;
import com.utn.tp4.utilitario.CrearConexionMySQL;
import com.utn.tp4.utilitario.IConnectable;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

public class ArticuloDAO extends AsyncTask<String, Void, String> implements IConnectable<Articulo> {

    private int idArticulo = 0;
    private int tipoTransaccion;
    private int cantFilasAfectadas = 0;
    private String TABLA = "articulo";
    private String SELECT_ALL = String.format("SELECT * FROM %s", TABLA);
    private ArrayList<Articulo> lista;
    private Articulo objArticulo;

    public ArticuloDAO() {
    }

    public ArticuloDAO(int _tipoTransaccion) {
        tipoTransaccion = _tipoTransaccion;
    }

    public ArticuloDAO(int _tipoTransaccion, Articulo _objArticulo) {
        tipoTransaccion = _tipoTransaccion;
        objArticulo = _objArticulo;
    }

    public ArticuloDAO(int _tipoTransaccion, int _idArticulo) {
        tipoTransaccion = _tipoTransaccion;
        idArticulo = _idArticulo;
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
                    objArticulo = selectObjectByID(idArticulo);
                    break;
                case ConstantesDB.idInsert:
                    cantFilasAfectadas = insert();
                    break;
                case ConstantesDB.idUpdate:
                	cantFilasAfectadas = update();
                    break;
                case ConstantesDB.idDelete:
                    break;
                case ConstantesDB.idVerificarExistencia:
                    if (existeRegistro(idArticulo))
                        return ConstantesDB.existe;
                    else
                        return ConstantesDB.noExiste;
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
    public ArrayList<Articulo> getAll() throws Exception {
        return lista;
    }

    @Override
    public Articulo get(int id) throws Exception {
        return null;
    }

    @Override
    public int getCount() throws Exception {
        return 0;
    }

    @Override
    public int insert(Articulo obj) throws Exception {
        return 0;
    }


    public int insert() throws Exception {
        Articulo obj = objArticulo;
        String sql = "{ call articuloInsert(?, ?, ?, ?, ?) }";
        Connection connection = new CrearConexionMySQL().getConnection();
        CallableStatement cs = connection.prepareCall(sql);
        cs.setInt("id", obj.getIdArticulo());
        cs.setString("nombre", obj.getNombre());
        cs.setInt("stock", obj.getStock());
        cs.setInt("idCategoria", obj.getIdCategoria());

        cs.registerOutParameter("idGenerado", Types.INTEGER);
        cs.execute();   //cs.executeUpdate();

        int idGenerado = cs.getInt("idGenerado");

        connection.close();

        return idGenerado;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectAll() throws Exception {
        Connection connection = new CrearConexionMySQL().getConnection();
        ResultSet rs = connection.createStatement().executeQuery(SELECT_ALL);
        lista = new ArrayList<Articulo>();
        while (rs.next()) {
            Articulo obj = new Articulo();
            obj.setIdArticulo(rs.getInt("id"));
            obj.setNombre(rs.getString("nombre"));
            obj.setStock(rs.getInt("stock"));
            obj.setIdCategoria(rs.getInt("idCategoria"));

            /*CategoriaDAO categoriaDAO = new CategoriaDAO(ConstantesDB.idSelectBy, obj.getIdCategoria());
            categoriaDAO.execute().get();
            String descripcionCategoria = categoriaDAO.getObject().getDescripcion();
            obj.setDescripcionCategoria(descripcionCategoria);*/

            lista.add(obj);
        }

        connection.close();
    }

    private int update() throws Exception {
        /*String sql = String.format(" UPDATE articulo" +
                        " SET nombre      = '%s'" +
                        " ,   stock       = %d" +
                        " ,   idCategoria = %d" +
                        " WHERE id        = %d" +
                , objArticulo.getNombre(), objArticulo.getStock()
                , objArticulo.getIdCategoria(), objArticulo.getIdArticulo());
         */
    	String sql = String.format(" UPDATE articulo" +
                " SET nombre      = ?" +
                " ,   stock       = ?" +
                " ,   idCategoria = ?" +
                " WHERE id        = ?" );
        Connection connection = new CrearConexionMySQL().getConnection();
        
        PreparedStatement ps = connection.prepareStatement(sql);
        //CallableStatement cs = connection.prepareCall(sql);
        //cs.registerOutParameter(1, Types.INTEGER);
        //cantFilasAfectadas = cs.executeUpdate();
        ps.setString(1, objArticulo.getNombre());
        ps.setInt(2, objArticulo.getStock());
        ps.setInt(3, objArticulo.getIdCategoria());
        ps.setInt(4, objArticulo.getIdArticulo());
        int rowsUpdated = ps.executeUpdate();
        connection.close();

        return rowsUpdated;
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private Articulo selectObjectByID(int idArticulo) throws Exception {
        String query = SELECT_ALL +
                " WHERE id= " + idArticulo;

        Connection connection = new CrearConexionMySQL().getConnection();
        ResultSet rs = connection.createStatement().executeQuery(query);
        //rs.next();

        Articulo obj = new Articulo();
        while (rs.next()) {
            obj.setIdArticulo(rs.getInt("id"));
            obj.setNombre(rs.getString("nombre"));
            obj.setStock(rs.getInt("stock"));
            obj.setIdCategoria(rs.getInt("idCategoria"));
        }

        connection.close();

        return obj;
    }
    public Articulo getObject() throws Exception {
        return objArticulo;
    }


}
