package com.challenge.quinto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.challenge.quinto.entidades.Curso;
import com.challenge.quinto.errores.ErrorServicio;
import com.challenge.quinto.servicios.CursoServicio;
import com.challenge.quinto.servicios.ProfesorServicio;

@Controller
@RequestMapping("/")
public class CursoControlador {
	
	@Autowired
	private CursoServicio cursoServicio;
	
	@Autowired
	private ProfesorServicio profeServicio;
	
	
	
	@PostMapping("/cursoNuevo")
	public String cursoNuevo(ModelMap mapaC, @RequestParam String nombre, @RequestParam String turno, @RequestParam String horario) throws ErrorServicio {
		try {
		
		cursoServicio.crearCurso(nombre, turno, horario);
		
		}catch(ErrorServicio e) {
			throw new ErrorServicio(e.getMessage());
		}
		
		return "redirect:/administracion";
	}
	
	@GetMapping("/eliminarCurso/{id}")
	public String eliminarCurso(@PathVariable String id) {
		
		cursoServicio.eliminarCurso(id);
		
		return "redirect:/administracion";
	}
	
	@GetMapping("/editarCurso/{id}")
	public String editarCurso(ModelMap mapa, @PathVariable String id) {
		
		Curso cursoM = cursoServicio.getOneId(id);
		
		mapa.addAttribute("mapaE" , cursoM);
		
		return "editarCurso.html";
	}
	
	@PostMapping("/cursoEditado")
	public String cursoEditado ( @RequestParam String id, @RequestParam String nombre, @RequestParam String turno, @RequestParam String horario) throws ErrorServicio {
		
		System.out.println(nombre);
		
		//cursoServicio.modificarCurso(id, nombre, profe, turno, alumnos, horario);
		
		return "redirect:/administracion";
	}
	
	
}
