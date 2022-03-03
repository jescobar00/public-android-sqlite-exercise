package com.utn.tp4.utilitario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CrearConexionMySQL {

    /*
    Base de datos: utn_tp4
    Nombre de usuario: utnfrgp2019
    Correo electrónico: utnfrgpintegrador2019@gmail.com
    utnfrgpintegrador2019dos@gmail.com
    Android2019
    */
    private final String host = "db4free.net";
    private final String port = "3306";
    private final String database = "utn_tp4";
    private final String user = "utnfrgp2019";
    private final String password = "utnfrgp2019";

    private final String MySQL = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";
    private final String driver = "com.mysql.jdbc.Driver";

    private Connection connection;

    public CrearConexionMySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(MySQL, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
