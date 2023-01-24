package com.challenge.quinto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.quinto.entidades.Curso;


@Repository
public interface CursoRepo extends JpaRepository <Curso, String> {
	
	@Query("SELECT c FROM Curso c WHERE c.estado=1 ORDER BY c.fechaAlta DESC")
    public List<Curso> listaDeActivos ();
	
	@Query("SELECT c FROM Curso c WHERE c.id= :id")
    public Curso buscarxId (@Param("id") String id);

}