package com.desco.camiones.controllers;

import com.desco.camiones.db.DatabaseConnection;
import com.desco.camiones.models.Vehiculo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            @RequestParam String munCorralon,Model model){
        //En esta parte se podrían validar los datos antes de ingresarlos a la base de datos
        if(capacidad <= 0){
            //Código de estátus diferente de 0 para que pueda marcarlo como error en la plantilla
            model.addAttribute("status",-1);
           //Texto que se muestra al regresar la vista
            model.addAttribute("error","La capacidad del camión debe de ser mayor a 0");
            return "insertStatus";
        }
        int status = DatabaseConnection.insertCamion(id,tipoId,marca,submarca,modelo,tipo,año,numSerie,capacidad,numCompart,capCompart,aseguradora,numPoliza,vencPoliza,municipio,estado,numCorralon,munCorralon);
        /**System.out.print("id:"+id+"\nmarca:"+marca+"\nsubmarca:"+submarca+"\ntipo:"+tipo+"\nmodelo:"+modelo+"\naño:"+año+"\nnumSerie:"+numSerie
        +"\ncapacidad:"+capacidad+"\nnumCompart:"+numCompart+"\ncapCompart:"+capCompart+"\naseguradora:"+aseguradora+"\nnumPoliza:"+numPoliza
                +"\nvencPoliza:"+vencPoliza+"\nmunicipio:"+municipio+"\nestado:"+estado+"\ncorralon:"+corralon+"\nmunCorralon:"+munCorralon);**/
        model.addAttribute("status",status);
        return "insertStatus";
    }

    @GetMapping("/camiones")
    public String allCamiones(Model model){
        ArrayList<Vehiculo> vehiculos = DatabaseConnection.getAllCamiones();
        model.addAttribute("vehiculos",vehiculos);
        return "camiones";
    }
    @GetMapping("/delVehiculo")
    public String delCamion(@RequestParam String numSerie,@RequestParam String id,Model model){
        int status = DatabaseConnection.delVehiculo(numSerie,id);
        model.addAttribute("status",status);
        return "delStatus";
    }
}
