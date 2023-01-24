package com.challenge.quinto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.challenge.quinto.entidades.Curso;
import com.challenge.quinto.entidades.Profesor;
import com.challenge.quinto.errores.ErrorServicio;
import com.challenge.quinto.repositorios.CursoRepo;
import com.challenge.quinto.repositorios.ProfeRepo;
import com.challenge.quinto.servicios.ProfesorServicio;

@Controller
@RequestMapping("/")
public class ProfesorControlador {
	
	@Autowired
	ProfesorServicio profeServicio;
	
	@Autowired
	CursoRepo cursoRepo;
	
	@Autowired
	ProfeRepo profeRepo;
	
	
	@GetMapping("/profes")
	public String pfores() {
		return "profesores.html";
	}
	
	@GetMapping("/registroProfes")
	public String registroProfes(ModelMap mapaP) {
		
		return "registroProfesor.html";
	}
	
	@GetMapping("/perfilProfes/{nombre}")
	public String perfilProfes(ModelMap perfilProfe, @PathVariable String nombre) {

		Profesor perfilP=profeRepo.buscarXnombre(nombre);
		
		perfilProfe.addAttribute("prefilP", perfilP);
		
		return "perfilProfesor.html"; 
	}
	
	@PostMapping("/profeRegistrado")
	public String profeRegistrado(ModelMap mapaProfes, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail) throws ErrorServicio {
		
		try {
			
			profeServicio.registrarProfesor(nombre, apellido, mail);
			
		}catch(ErrorServicio e) {
			throw new ErrorServicio (e.getMessage());
		}
		
		return "registroProfesor.html";
	}
	
	@GetMapping("/editarProfesor/{id}") 
	public String editarProfesor (ModelMap modelo, @PathVariable String id ) {
		
		Optional<Profesor> respuesta=profeRepo.findById(id);
		
		if (respuesta.isPresent()){
			Profesor perfilP = respuesta.get();
			modelo.put("prefilP", perfilP);
		}
		
		List<Curso> cursosL= cursoRepo.listaDeActivos();
		
		modelo.put("cursosL", cursosL);
		
		return "editarProfesor.html";
	}
	
	@PostMapping("/editarProfesor")
	public String profesorEditado (ModelMap modelo, @RequestParam String id,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String mail,@RequestParam List<String> idCurso ) throws ErrorServicio {
		
		profeServicio.modificarProfe( id,  nombre,  apellido, mail, idCurso);
		
		return "redirect:/administracion";
	}
	
	@GetMapping("/eliminarProfesor/{id}")
	public String eliminarProfesor (ModelMap modelo, @PathVariable String id) {
		
		profeServicio.eliminarProfe(id);
		
				
		return "redirect:/administracion";
	}
	
	@GetMapping("/eliminarCursoProfe")
	public String eliminarCursProfesor (ModelMap modelo, @RequestParam String id, @RequestParam String idCurso) {
		
		profeServicio.eliminarCursProfe(id, idCurso);
		
				
		return "redirect:/administracion";
	}
	
	}
