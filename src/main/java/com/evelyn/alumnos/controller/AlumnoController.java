package com.evelyn.alumnos.controller;

import java.util.List;
import com.evelyn.alumnos.model.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.alumnos.AlumnoRepository;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;


    //metodo get para traer todos los alumnos
    @GetMapping("/traerAlumnos")
    public List<Alumno> TraerAlumnos(){
        return alumnoRepository.findAll();


    }
    // metodo para ingresar un alumno a la base de datos 
    @PostMapping("/insertar-alumnos")
    public Alumno insertarAlumno(@RequestBody Alumno alumno){
        return alumnoRepository.save(alumno);

    }
    // metodo para editar un alumno a la base de datos 
    @PutMapping("/editar-alumnos/{id}")
    public ResponseEntity<Alumno>  actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumno){
        return alumnoRepository.findById(id).map(alumnoExistente -> {
        alumnoExistente.setNombre(alumno.getNombre());
        alumnoExistente.setApellido(alumno.getApellido());
        alumnoExistente.setEmail(alumno.getEmail());
        alumnoExistente.setNumeroControl(alumno.getNumeroControl());
        alumnoExistente.setTelefono(alumno.getTelefono());
        alumnoExistente.setCarrera(alumno.getCarrera());
        alumnoExistente.setImagenURL(alumno.getImagenURL());

        Alumno actualizado = alumnoRepository.save(alumnoExistente);
        return ResponseEntity.ok(actualizado);
    }).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
