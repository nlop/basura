package com.desco.camiones.controllers;

import com.desco.camiones.db.DatabaseConnection;
import com.desco.camiones.models.Vehiculo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;


@Controller
public class RegistrosController {
    /**
     * Ruta asociada al método que registra camiones en la base de datos. El parámetro tipoId denota
     * el tipo de identificación que usa el vehícula, dichos enteros representan lo siguiente:
     *  1 - Número económico
     *  2 - Número temporal
     *  3 - Número de placas
     *
     * @param id Identificador asociado al vehículo
     * @param tipoId Entero que denota el tipo de ID del veihculo (num. económico,placas,etc)
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
     * @param model Modelo de datos
     * @return Vista y modelo de datos que denotan el estado de la inserción
     */
    @PostMapping("/registroCamion")
    public String registrarCamion(
            @RequestParam (required = true)String id,
            @RequestParam int tipoId,
            @RequestParam String marca,
            @RequestParam(required = false,defaultValue = "") String submarca,
            @RequestParam String tipo,
            @RequestParam String modelo,
            @RequestParam(defaultValue = "1999")int año,
            @RequestParam String numSerie,
            @RequestParam(defaultValue = "-1")@NotNull int capacidad,
            @RequestParam(required = false,defaultValue = "0") int numCompart,
            @RequestParam(required = false,defaultValue = "0") int capCompart,
            @RequestParam(required = false) String aseguradora,
            @RequestParam(required = false) String numPoliza,
            @RequestParam(required = false) String vencPoliza,
            @RequestParam String municipio,
            @RequestParam String estado,
            @RequestParam String numCorralon,
            @RequestParam String munCorralon,Model model){
        //En esta parte se podrían validar los datos antes de ingresarlos a la base de datos

        if(capacidad <= 0){
            //Código de estátus diferente de 0 para que pueda marcarlo como error en la plantilla
            model.addAttribute("status",-1);
           //Texto que se muestra al regresar la vista
            model.addAttribute("error","Se debe tener una capacidad de carga y esta debe de ser mayor a 0");
            return "insertStatus";
        }
        else{
            if (capacidad==-1){
                model.addAttribute("error","La capacidad debe de ser mayor a 0");
            }
        }
        //Validar año
        if(año<1886){
            model.addAttribute("status",-1);
            model.addAttribute("error"," Karl Friedrich Benz inventó el primer automóvil con motor en 1886, el año defabricación debe de ser posterior a este.");
            return "insertStatus";
        }else{
            if(año>2019){
                model.addAttribute("status",-1);
                model.addAttribute("error","El año de fabricación debe de ser inferior al de la actualidad.");
                return "insertStatus";
            }
        }
        if (id.length()<1 || id.equals("")){
            model.addAttribute("status",-1);
            model.addAttribute("error","El identificador no puede estar vacio");
            return "insertStatus";
        }

        if (marca.equals("")){
            model.addAttribute("status",-1);
            model.addAttribute("error","Se debe especificar un modelo");
            return "insertStatus";
        }

        if (modelo.equals("")){
            model.addAttribute("status",-1);
            model.addAttribute("error","Se debe especificar un modelo");
            return "insertStatus";
        }

        if (tipo.equals("")){
            model.addAttribute("status",-1);
            model.addAttribute("error","Se debe especificar un tipo");
            return "insertStatus";
        }

        if (estado.equals("")){
            model.addAttribute("status",-1);
            model.addAttribute("error","Se debe especificar un estado");
            return "insertStatus";
        }
        if (numCorralon.equals("")){
            model.addAttribute("status",-1);
            model.addAttribute("error","Se debe especificar un número de corralon");
            return "insertStatus";
        }
        if (munCorralon.equals("")){
            model.addAttribute("status",-1);
            model.addAttribute("error","Se debe especificar un el municipio donde su ubica el corralon");
            return "insertStatus";
        }

        int status = DatabaseConnection.insertCamion(
                id.toUpperCase(),
                tipoId,
                marca.toUpperCase(),
                submarca.toUpperCase(),
                modelo.toUpperCase(),
                tipo,
                año,
                numSerie.toUpperCase(),
                capacidad,
                numCompart,
                capCompart,
                aseguradora.toUpperCase(),
                numPoliza.toUpperCase(),
                vencPoliza,
                municipio.toUpperCase(),
                estado.toUpperCase(),
                numCorralon.toUpperCase(),
                munCorralon.toUpperCase());
        model.addAttribute("status",status);
        return "insertStatus";
    }

    /**
     * Manda a buscar todos los registros de vehiculos y los añade al modelo de datos de la vista para que los interprete
     * @param model Modelo de datos de la vista
     * @return Vista que describe todos los registros de vehículos
     */
    @GetMapping("/camiones")
    public String allCamiones(Model model){
        ArrayList<Vehiculo> vehiculos = DatabaseConnection.getAllCamiones();
        model.addAttribute("vehiculos",vehiculos);
        return "camiones";
    }

    /**
     * Elimina el registro de un vehículo dado su núm. de serie y su identificador
     * @param numSerie Número de serie del vehículo
     * @param id Identificador del vehículo (núm. placas, núm temporal, núm. económico)
     * @param model Modelo de datos de la vista
     * @return Vista que indica el estado de la eliminación del registro
     */
    @GetMapping("/delVehiculo")
    public String delCamion(@RequestParam String numSerie,@RequestParam String id,Model model){
        int status = DatabaseConnection.delVehiculo(numSerie,id);
        model.addAttribute("status",status);
        return "delStatus";
    }

    /**
     * Busca el registro de un vehículo dado su número de serie e identificador para posteriormente regresar una vista
     * con todos los datos y cuyos campos pueden ser modificados o no, dependiento del parametro edit.
     * @param numSerie Número de serie del vehículo
     * @param id Identificador del vehículo (núm. placas, núm temporal, núm. económico)
     * @param edit Indica el modo en el que se encontraran los campos de datos (1 = editar, 0 = leer)
     * @param model Modelo de datos de la vista
     * @return Vista con datos del vehículo que pueden o no ser editables
     */
    @GetMapping("/viewVehiculo")
    public String updVehiculo(@RequestParam String numSerie,@RequestParam String id,@RequestParam int edit,Model model) {
        Vehiculo veh = DatabaseConnection.getVehiculo(numSerie, id);
        model.addAttribute("v",veh);
        model.addAttribute("edit",edit);
        return "viewVehiculo";
    }

    /**
     * Actualiza el registro de un camión dado su identificador(id) y su número de serie, regresa una vista que describe sí la
     * transacción fue exitosa o no
     * @param id Identificador asociado al vehículo
     * @param tipoId Entero que denota el tipo de ID del veihculo (num. económico,placas,etc)
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
     * @param model Modelo de datos
     * @return Vista y modelo de datos que denotan el estado de la actualización
     */
    @PostMapping("/updVehiculo")
    public String updVehiculo(
    @RequestParam String id,
    @RequestParam int tipoId,
    @RequestParam String marca,
    @RequestParam(required = false,defaultValue = "") String submarca,
    @RequestParam String tipo,
    @RequestParam String modelo,
    @RequestParam int año,
    @RequestParam String numSerie,
    @RequestParam int capacidad,
    @RequestParam(required = false,defaultValue = "0") int numCompart,
    @RequestParam(required = false,defaultValue = "0") int capCompart,
    @RequestParam(required = false) String aseguradora,
    @RequestParam(required = false) String numPoliza,
    @RequestParam(required = false) String vencPoliza,
    @RequestParam String municipio,
    @RequestParam String estado,
    @RequestParam String numCorralon,
    @RequestParam String munCorralon,Model model) {
        int status = DatabaseConnection.updateCamion(id,tipoId,marca,submarca,modelo,tipo,año,numSerie,capacidad,numCompart,capCompart,aseguradora,numPoliza,vencPoliza,municipio,estado,numCorralon,munCorralon);
        //Atributo de estatus que se envía a la vista para describir el estado de la transacción
        model.addAttribute("status",status);
        return "updStatus";
    }
}


