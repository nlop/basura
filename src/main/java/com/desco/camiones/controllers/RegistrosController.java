package com.desco.camiones.controllers;

import com.desco.camiones.db.DatabaseConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegistrosController {
    /**
     * Ruta asociada al método que registra camiones en la base de datos
     * @param id Identificador del camión
     * @param marca Marca del camión
     * @param modelo Modelo del camión
     * @param año Año de fabricación del camión
     * @param model Objeto que guarda los datos para el sistema de plantillas
     * @return Vista que señala sí la inserción fue ejecutada correctamente (insertStatus)
     */
    @PostMapping("/registroCamion")
    public String registrarCamion(@RequestParam  String id, @RequestParam String marca, @RequestParam String modelo, @RequestParam int año, Model model){
        //En esta parte se podrían validar los datos antes de ingresarlos a la base de datos
        int status = DatabaseConnection.insertCamion(id, marca, modelo, año);
        model.addAttribute("status",status);
        return "insertStatus";
    }
}
