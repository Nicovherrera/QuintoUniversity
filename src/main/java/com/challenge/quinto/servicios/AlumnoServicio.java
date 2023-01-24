package com.challenge.quinto.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.quinto.entidades.Alumno;
import com.challenge.quinto.entidades.Curso;
import com.challenge.quinto.errores.ErrorServicio;
import com.challenge.quinto.repositorios.AlumnoRepo;
import com.challenge.quinto.repositorios.CursoRepo;

@Service
public class AlumnoServicio {
	
	@Autowired
	private CursoRepo cursoRepo;
	
	@Autowired
	private AlumnoRepo alumnoRepo;
	
	@Transactional
	public void registrarAlumno(String nombre, Integer edad, Date fechaDeNacimiento, String historia, List<String> cursoId) throws ErrorServicio {
		
		List<Curso>cursosL=new ArrayList();
		for (String idC : cursoId) {
			Curso av = cursoRepo.buscarxId(idC);
			cursosL.add(av);
		}
		
		
		validar(nombre, edad, historia);
		
		Alumno alumno = new Alumno();
		
		alumno.setFechaAlta(new Date());
		alumno.setNombre(nombre);
		alumno.setEdad(edad);
		alumno.setFechaDeNacimiento(fechaDeNacimiento);
		alumno.setHistoria(historia);
		alumno.setCurso(cursosL);
		
		alumnoRepo.save(alumno);
		
	}
	
	public void modificarAlumno(String id, String nombre, Integer edad, Date fechaDeNacimiento, String historia) throws ErrorServicio {
		
		
		Optional <Alumno> alumnoP = alumnoRepo.findById(id);
		if(alumnoP.isPresent()) {
			
			validar(nombre, edad, historia);
			
			Alumno alumnoM = alumnoP.get();
			alumnoM.setNombre(nombre);
			alumnoM.setEdad(edad);
			alumnoM.setFechaDeNacimiento(fechaDeNacimiento);
			alumnoM.setHistoria(historia);
			
			
			alumnoRepo.save(alumnoM);
				
		}
	}
	
	public void eliminarAlumno(String id) {
		
		alumnoRepo.deleteById(id);

	}
	
	public List<Alumno> alumnosActivos(){
		return alumnoRepo.findAll();
	}
	
	public Alumno buscarAlumnoPorId(String id) {

		Optional<Alumno> respuesta = alumnoRepo.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		}
		return null;
	}
	
	public void validar (String nombre, Integer edad, String historia) throws ErrorServicio  {
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre no puede estar vacio o ser nulo");
		}
		
		if (edad == null || edad.MAX_VALUE < 90 || edad.MIN_VALUE > 18) {
			throw new ErrorServicio("La edad debe ser entre 18 y 90 años y no puede ser nula");
		}
		
		if (historia == null || historia.isEmpty()) {
			throw new ErrorServicio("La historia no puede quedar vacia o nula");
		}
		
	}

}

