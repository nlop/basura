package com.desco.camiones.db;

import com.desco.camiones.models.Vehiculo;

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
     * @param id Identificador asociado al vehículo
     * @param tipoRegistro Entero que denota el tipo de ID del veihculo (num. económico,placas,etc)
     * @param marca Marca del vehículo
     * @param submarca Submarca del vehículo(opc)
     * @param tipo Tipo de vehículo(camioneta o camión)
     * @param modelo Modelo del vehículo
     * @param año Año de fabricación del vehículo
     * @param numSerie Número de serie (VIN) del vehículo
     * @param capacidad Capacidad de carga del vehículo
     * @param numCompart Número de compartimientos del vehículo(opc)
     * @param capCompart Capácidad de carga de cada compartimiento del vehículo(opc)
     * @param aseguradora Nombre de la compañia aseguradora del vehiculo(opc)
     * @param numPoliza Número de la póliza de seguro del vehículo(opc)
     * @param vencPoliza Fecha de vencimiento de la póliza de seguro
     * @param municipio Municipio de residencia del vehículo
     * @param estado Estado de residencia del vehículo
     * @param numCorralon Número del corralón donde se almacena el vehículo
     * @param munCorralon Municipio del corralón donde se almacena el vehículo
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
     * Genéra un ArrayList de objetos Vehiculo a partir de todos los registros
     * que se tengan en la base de datos
     * @return ArrayList de objetos Vehiculo
     */
    public static ArrayList<Vehiculo> getAllCamiones(){
        ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
        try{
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from camiones");
            while(rs.next()){
                vehiculos.add(new Vehiculo(
                        rs.getString("id"),
                        rs.getInt("tipo_registro"),
                        rs.getString("marca"),
                        rs.getString("submarca"),
                        rs.getString("modelo"),
                        rs.getString("tipo"),
                        rs.getInt("año"),
                        rs.getString("num_serie"),
                        rs.getInt("capacidad"),
                        rs.getInt("num_compart"),
                        rs.getInt("cap_compart"),
                        rs.getString("aseguradora"),
                        rs.getString("num_poliza"),
                        rs.getString("vencimiento_poliza"),
                        rs.getString("municipio"),
                        rs.getString("estado"),
                        rs.getString("num_corralon"),
                        rs.getString("municipio_corralon")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return vehiculos;
    }

    /**
     * Ejecuta la orden para eliminar un registro de vehículo dado el identificador y el número de serie
     * @param numSerie Número de serie del vehículo
     * @param id Identificador del vehículo (núm. placas, núm temporal, núm. económico)
     * @return Estatus de la transacción
     */
    public static int delVehiculo(String numSerie,String id){
        try{
            PreparedStatement pt = conn.prepareStatement("DELETE FROM camiones WHERE camiones.id=? AND camiones.num_serie=?");
            pt.setString(1,id);
            pt.setString(2,numSerie);
            pt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return e.getErrorCode();
        }
        return 0;
    }

    /**
     *  Ejecuta la orden para encontrar un registro de vehículo dado el número de serie e identificador del vehículo
     * @param numSerie Número de serie del vehículo
     * @param id Identificador del vehículo (núm. placas, núm temporal, núm. económico)
     * @return Estatus de la transacción
     */
    public static Vehiculo getVehiculo(String numSerie,String id){
        Vehiculo v;
        try{
            PreparedStatement pt = conn.prepareStatement("SELECT * FROM camiones WHERE camiones.id=? AND camiones.num_serie=?");
            pt.setString(1,id);
            pt.setString(2,numSerie);
            ResultSet rs = pt.executeQuery();
            rs.next();
            v = new Vehiculo(
                    rs.getString("id"),
                    rs.getInt("tipo_registro"),
                    rs.getString("marca"),
                    rs.getString("submarca"),
                    rs.getString("modelo"),
                    rs.getString("tipo"),
                    rs.getInt("año"),
                    rs.getString("num_serie"),
                    rs.getInt("capacidad"),
                    rs.getInt("num_compart"),
                    rs.getInt("cap_compart"),
                    rs.getString("aseguradora"),
                    rs.getString("num_poliza"),
                    rs.getString("vencimiento_poliza"),
                    rs.getString("municipio"),
                    rs.getString("estado"),
                    rs.getString("num_corralon"),
                    rs.getString("municipio_corralon"));
        }catch (SQLException e){
            e.printStackTrace();
            return new Vehiculo();
        }
        return v;
    }

    /**
     * Actualia los datos de un registro de vehículo utilizando las llaves primarias(núm. de serie e identificador), en caso de una inserción
     * exitosa devuelve un código 0, en caso contrario, regresa el número de error de la base de datos
     * @param id Identificador asociado al vehículo
     * @param tipoRegistro Entero que denota el tipo de ID del veihculo (num. económico,placas,etc)
     * @param marca Marca del vehículo
     * @param submarca Submarca del vehículo(opc)
     * @param tipo Tipo de vehículo(camioneta o camión)
     * @param modelo Modelo del vehículo
     * @param año Año de fabricación del vehículo
     * @param numSerie Número de serie (VIN) del vehículo
     * @param capacidad Capacidad de carga del vehículo
     * @param numCompart Número de compartimientos del vehículo(opc)
     * @param capCompart Capácidad de carga de cada compartimiento del vehículo(opc)
     * @param aseguradora Nombre de la compañia aseguradora del vehiculo(opc)
     * @param numPoliza Número de la póliza de seguro del vehículo(opc)
     * @param vencPoliza Fecha de vencimiento de la póliza de seguro
     * @param municipio Municipio de residencia del vehículo
     * @param estado Estado de residencia del vehículo
     * @param numCorralon Número del corralón donde se almacena el vehículo
     * @param munCorralon Municipio del corralón donde se almacena el vehículo
     * @return Código de estátus de la inserción
     */
    public static int updateCamion(
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
            PreparedStatement st = conn.prepareStatement("UPDATE camiones SET marca=?,submarca=?,modelo=?,tipo=?,año=?,capacidad=?,num_compart=?,cap_compart=?,aseguradora=?,num_poliza=?,vencimiento_poliza=?,municipio=?,estado=?,num_corralon=?,municipio_corralon=? WHERE id=? AND num_serie=?");
            st.setString(1,marca);
            st.setString(2,submarca);
            st.setString(3,modelo);
            st.setString(4,tipo);
            st.setInt(5,año);
            st.setInt(6,capacidad);
            st.setInt(7,numCompart);
            st.setInt(8,capCompart);
            st.setString(9,aseguradora);
            st.setString(10,numPoliza);
            st.setString(11,vencPoliza);
            st.setString(12,municipio);
            st.setString(13,estado);
            st.setString(14,numCorralon);
            st.setString(15,munCorralon);
            st.setString(16,id);
            st.setString(17,numSerie);
            st.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            return e.getErrorCode();
        }
        return 0;
    }
}
