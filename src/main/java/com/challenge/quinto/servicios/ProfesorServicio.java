package com.challenge.quinto.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.quinto.entidades.Curso;
import com.challenge.quinto.entidades.Profesor;
import com.challenge.quinto.errores.ErrorServicio;
import com.challenge.quinto.repositorios.CursoRepo;
import com.challenge.quinto.repositorios.ProfeRepo;


@Service
public class ProfesorServicio {
	
	@Autowired
	private ProfeRepo repoProfe;
	
	@Autowired 
	private CursoRepo cursoRepo;
	
	@Transactional
	public void registrarProfesor (String nombre, String apellido, String mail) throws ErrorServicio {
		
		validarProfe(nombre, apellido);
		
		Profesor profe = new Profesor();
		
		profe.setFechaDeAlra(new Date());
		profe.setNombre(nombre);
		profe.setApellido(apellido);
		profe.setMail(mail);
		profe.setEstado(true);
				
		repoProfe.save(profe);
	}
	
	@Transactional
	public void modificarProfe (String id, String nombre, String apellido, String mail, List<String> cursoId) throws ErrorServicio {
		
		List<Curso>cursosL=new ArrayList();
		for (String idC : cursoId) {
			Curso av = cursoRepo.buscarxId(idC);
			cursosL.add(av);
					}
		
		Optional<Profesor> respuesta= repoProfe.findById(id);
		
		if(respuesta.isPresent()) {
			
			validarProfe(nombre, apellido);	
			
			Profesor profeM = respuesta.get();
			profeM.setFechaDeAlra(new Date());
			profeM.setNombre(nombre);
			profeM.setApellido(apellido);
			profeM.setMail(mail);
			profeM.setCursos(cursosL);
			profeM.setEstado(true);
			
			repoProfe.save(profeM);
		}
		
	}
	public void eliminarCursProfe( String id,String cursoId) {
		
		Optional<Profesor>respuestaP=repoProfe.findById(id);
		if(respuestaP.isPresent()) {
			Profesor profe=respuestaP.get();
			List<Curso> cursosProfe=profe.getCursos();
			for (Curso idC : cursosProfe) {
	            
				Curso cursoE=cursoRepo.buscarxId(cursoId);
				cursosProfe.remove(cursoE);
				cursoRepo.save(cursoE);
	        }
		}
		  
	}
	public void eliminarProfe(String id) {
		
		Optional<Profesor> respuestaE = repoProfe.findById(id);
		
		if (respuestaE.isPresent()) {
			Profesor profeE = respuestaE.get();
			profeE.setEstado(false);
			
			repoProfe.save(profeE);
		}
		
	}
	
	public List <Profesor> listarProfes () {
		return repoProfe.listaDeProfesActivos();
	}
	
	public Profesor buscarProfeXnombre(String nombre) {
		return repoProfe.buscarXnombre(nombre);
	}
	
	public void validarProfe(String nombre, String apellido) throws ErrorServicio {
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio ("El nombre no puede ser nulo ni estar vacio");
		}
		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio ("El apellido no puede ser nulo ni estar vacio");
		}
	}

}
