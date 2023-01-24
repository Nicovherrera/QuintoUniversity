package com.challenge.quinto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.quinto.entidades.Curso;
import com.challenge.quinto.entidades.Profesor;

@Repository
public interface ProfeRepo extends JpaRepository <Profesor , String> {
	
	@Query("SELECT p FROM Profesor p WHERE p.nombre= :nombre")
    public Profesor buscarXnombre (@Param("nombre") String nombre);
	
	@Query("SELECT p FROM Profesor p WHERE p.estado=1 ORDER BY p.fechaDeAlra DESC")
    public List<Profesor> listaDeProfesActivos ();

}

