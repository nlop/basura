package com.desco.camiones.db;

import com.desco.camiones.models.Camion;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private static Connection conn;

    /**
     * Método estático que crea una conexión a la base de datos contenida en el archivo database
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
            st.executeUpdate("CREATE TABLE camiones(\n" +
                    "    id TEXT," +
                    "    tipo_registro TEXT," +
                    "    marca TEXT NOT NULL," +
                    "    submarca TEXT," +
                    "    modelo TEXT NOT NULL," +
                    "    tipo TEXT NOT NULL," +
                    "    año INTEGER NOT NULL," +
                    "    num_serie TEXT NOT NULL," +
                    "    capacidad INTEGER NOT NULL," +
                    "    num_compart INTEGER," +
                    "    cap_compart INTEGER," +
                    "    aseguradora TEXT," +
                    "    num_poliza TEXT," +
                    "    vencimiento_poliza TEXT," +
                    "    municipio TEXT NOT NULL," +
                    "    estado TEXT NOT NULL," +
                    "    num_corralon TEXT NOT NULL," +
                    "    municipio_corralon TEXT NOT NULL," +
                    "    PRIMARY KEY (id,num_serie)" +
                    ");");
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
    public static int insertCamion(
            String id,
            int tipoRegistro,
            String marca,
            String submarca,
            String modelo,
            String tipo,
            int año,
            String numSerie,
            int capacidad,
            int numCompart,
            int capCompart,
            String aseguradora,
            String numPoliza,
            String vencPoliza,
            String municipio,
            String estado,
            String numCorralon,
            String munCorralon){
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO camiones values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            st.setString(1,id);
            st.setInt(2,tipoRegistro);
            st.setString(3,marca);
            st.setString(4,submarca);
            st.setString(5,modelo);
            st.setString(6,tipo);
            st.setInt(7,año);
            st.setString(8,numSerie);
            st.setInt(9,capacidad);
            st.setInt(10,numCompart);
            st.setInt(11,capCompart);
            st.setString(12,aseguradora);
            st.setString(13,numPoliza);
            st.setString(14,vencPoliza);
            st.setString(15,municipio);
            st.setString(16,estado);
            st.setString(17,numCorralon);
            st.setString(18,munCorralon);
            st.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("This error " + e.getErrorCode());
            return e.getErrorCode();
        }
        return 0;
    }

    /**
     * Genéra un ArrayList de objetos Camion a partir de todos los registros
     * que se tengan en la base de datos
     * @return ArrayList de objetos Camion
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
