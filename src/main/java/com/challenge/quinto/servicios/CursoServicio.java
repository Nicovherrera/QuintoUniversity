package com.challenge.quinto.servicios;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.quinto.entidades.Alumno;
import com.challenge.quinto.entidades.Curso;
import com.challenge.quinto.entidades.Profesor;
import com.challenge.quinto.errores.ErrorServicio;
import com.challenge.quinto.repositorios.CursoRepo;

@Service
public class CursoServicio {
	
	@Autowired
	private CursoRepo cursoRepo;
	
	public void crearCurso (String nombre, String turno, String horarios) throws ErrorServicio {
		
		validarCurso(nombre,turno,horarios);
		
		Curso cursoN = new Curso();
		
		cursoN.setFechaAlta(new Date());
		cursoN.setNombre(nombre);
		cursoN.setTurno(turno);
		cursoN.setHorarios(horarios);
		cursoN.setEstado(true);
		
		
		cursoRepo.save(cursoN);
	}
	
public void modificarCurso (String id, String nombre, List<Profesor> profesor, String turno, String horarios, List<Alumno> alumnos) throws ErrorServicio {
		
		Optional<Curso> respuesta = cursoRepo.findById(id);
		
		if(respuesta.isPresent()) {
		
		validarCurso(nombre,turno,horarios);
		
		Curso cursoM = respuesta.get();
		
		cursoM.setFechaAlta(new Date());
		cursoM.setNombre(nombre);
		cursoM.setProfesor(profesor);
		cursoM.setTurno(turno);
		cursoM.setHorarios(horarios);
		cursoM.setAlumnos(alumnos);
		cursoM.setEstado(true);
		
		cursoRepo.save(cursoM);
		}
	}

	public void eliminarCurso (String id) {
		
		Optional<Curso>respuesta=cursoRepo.findById(id);
		if(respuesta.isPresent()) {
			
			Curso cursoEliminado = respuesta.get();
			cursoEliminado.setEstado(false);
			
			cursoRepo.save(cursoEliminado);
		}
		
	}
	
	public List<Curso> listaCursos (){
		return cursoRepo.listaDeActivos();
	}
	
	public Curso getOneId (String id) {
		return cursoRepo.getOne(id);
	}
	
	public void validarCurso(String nombre, String turno, String horarios) throws ErrorServicio{
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre no puede estar vacio o ser nulo");
		}
		
		if (turno == null) {
			throw new ErrorServicio("El turno del curso no puede ser nulo o vacio");
		}
		
		if (horarios == null || horarios.isEmpty()) {
			throw new ErrorServicio("Los horarios del curso no pueden ser nulos o estar vacios");
		}
		
	}

}
