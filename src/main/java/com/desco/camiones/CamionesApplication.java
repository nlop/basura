package com.desco.camiones;

import com.desco.camiones.db.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class CamionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamionesApplication.class, args);
        /*Verificar si existe el archivo de la base de datos, si no existe
        genera el esquema de la base de datos en la nueva base de datos*/
		File f = new File("camiones.db");
        if(f.exists()){
            DatabaseConnection.connect("camiones.db");
        }else{
            DatabaseConnection.connect("camiones.db");
            DatabaseConnection.createSchema();
        }
	}

}

