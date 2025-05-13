package com.evelyn.alumnos.controller;

import java.util.List;
import com.evelyn.alumnos.model.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evelyn.alumnos.AlumnoRepository;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @GetMapping("/traerAlumnos")
    public List<Alumno> TraerAlumnos(){
        return alumnoRepository.findAll();


    }


}
