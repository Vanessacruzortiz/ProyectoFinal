package com.evelyn.alumnos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evelyn.alumnos.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

}
