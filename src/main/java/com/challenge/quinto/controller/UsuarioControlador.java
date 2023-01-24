package com.challenge.quinto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.challenge.quinto.entidades.Alumno;
import com.challenge.quinto.entidades.Curso;
import com.challenge.quinto.entidades.Profesor;
import com.challenge.quinto.errores.ErrorServicio;
import com.challenge.quinto.repositorios.AlumnoRepo;
import com.challenge.quinto.repositorios.CursoRepo;
import com.challenge.quinto.repositorios.ProfeRepo;
import com.challenge.quinto.servicios.UsuarioSevicio;

@Controller
@RequestMapping("/")
public class UsuarioControlador {
	
	@Autowired
	private CursoRepo cursoRepo;
	
	@Autowired
	private ProfeRepo profeRepo;
	
	@Autowired
	private AlumnoRepo alumnoRepo;
	
	@Autowired
	private UsuarioSevicio usuarioServicio;
	
	
	@GetMapping("/registroAdmin")
	public String registroAdmin() {
		return "registroAdmin.html";
	}
	
	@PostMapping("/adminRegistrado")
	public String adminRegistrado(ModelMap mapa, @RequestParam String nombre, @RequestParam String email, @RequestParam String clave, @RequestParam String clave2) throws ErrorServicio {
			
		try {
		 usuarioServicio.registrarUsuario(nombre, email, clave, clave2);
		 
		 return "login.html"; 
		 
		}catch(ErrorServicio e) {
			
			mapa.put("error", "Hubo un error vuelva a intentarlo");
			mapa.put("nombre", nombre);
			mapa.put("email", email);
			mapa.put("clave", clave);
			mapa.put("clave2", clave2);
			
			return "registroAdmin.html";
		}
	}

	@GetMapping("/loguin")
	public String loguin(@RequestParam(required = false) String error, ModelMap modelo ) {
		
		if(error!=null) {
			modelo.put("error", "Usuario o Clave Incorrectos");
		}
		
		return "login.html";
	}
	
	@GetMapping("/administracion")
	public String panelAdmin (ModelMap modelo) {
		
		List<Curso> cursosL= cursoRepo.listaDeActivos();
		List<Profesor> profesL= profeRepo.listaDeProfesActivos();
		List<Alumno> alumnosL= alumnoRepo.findAll();
		
		modelo.addAttribute("cursosL", cursosL);
		modelo.addAttribute("profesL", profesL);
		modelo.addAttribute("alumnosL", alumnosL);
		
		return "administracion.html";
	}
	
}
