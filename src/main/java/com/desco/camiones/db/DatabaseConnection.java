package com.desco.camiones.db;

import com.desco.camiones.models.Camion;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private static Connection conn;

    /**
     * Método estático que crea una conexión a la base de datos contenida en el archivo <it>database</it>
     * @param database Archivo de la base de datos
     */
    public static void connect(String database){
        if(conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:" + database);
                Statement statement = conn.createStatement();
            }catch (java.sql.SQLException e){
                System.out.println("Problema al conectar con base de datos:");
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    /**
     * Método para generar el esquema de las bases de datos
     */
    public static void createSchema(){
        try {
            Statement st = conn.createStatement();
            st.executeUpdate("CREATE TABLE camiones(id text,marca text,modelo text,año integer)");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Método para insertar un camion en la base de datos, en caso de una inserción exitosa devuelve un código 0
     * en caso contrario, regresa el número de error de la base de datos
     * @param id Identificador del camión
     * @param marca Marca del camión
     * @param modelo Módelo del camión
     * @param año Año de fabricación del camión
     * @return Código de estátus de la inserción
     */
    public static int insertCamion(String id,String marca,String modelo,int año){
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO camiones values(?,?,?,?)");
            st.setString(1,id);
            st.setString(2,marca);
            st.setString(3,modelo);
            st.setInt(4,año);
            st.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return e.getErrorCode();
        }
        return 0;
    }

    /**
     * Genéra un <it>ArrayList</it> de objetos <it>Camion</it> a partir de todos los registros
     * que se tengan en la base de datos
     * @return <it>ArrayList</it> de objetos <it>Camion</it>
     */
    public static ArrayList<Camion> getAllCamiones(){
        ArrayList<Camion> camiones = new ArrayList<Camion>();
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from camiones");
            while(rs.next()){
                camiones.add(new Camion(
                        rs.getString("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("año")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return camiones;
    }
}
