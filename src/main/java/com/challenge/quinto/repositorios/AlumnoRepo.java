package com.challenge.quinto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.quinto.entidades.Alumno;

@Repository
public interface AlumnoRepo extends JpaRepository <Alumno, String>{

}

