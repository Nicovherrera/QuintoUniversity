package com.challenge.quinto.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.challenge.quinto.entidades.Alumno;
import com.challenge.quinto.entidades.Curso;
import com.challenge.quinto.errores.ErrorServicio;
import com.challenge.quinto.servicios.AlumnoServicio;
import com.challenge.quinto.servicios.CursoServicio;

@Controller
@RequestMapping("/")
public class AlumnoControlador {
	
	@Autowired
	private CursoServicio cursoServicio;
	
	@Autowired
	private AlumnoServicio alumnoServicio;
	
	@GetMapping("/registroAlumno")
	public String registrarAlumno(ModelMap modeloC) {
		
		List<Curso> cursosL = cursoServicio.listaCursos();
		
		modeloC.addAttribute("cursosL", cursosL);
		
		return "registroAlumno.html"; 
	}
	
	@PostMapping("/alumnoRegistrado")
	public String alumnoCreado(Model mapaA, @RequestParam String nombre, @RequestParam Integer edad,@RequestParam String fecha,@RequestParam String historia, @RequestParam List<String> cursoId ) throws ParseException, ErrorServicio {
		
		try {
			SimpleDateFormat fechaFormateada = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaA = fechaFormateada.parse(fecha);
			
			alumnoServicio.registrarAlumno(nombre, edad, fechaA, historia, cursoId);
			
			return "redirect:/administracion";
			
		}catch(ErrorServicio e){
			throw new ErrorServicio(e.getMessage());	
		} 
	}
	
	@GetMapping("/perfilAlumno")
	public String perfilAlumnoLogueado(ModelMap modelo, @RequestParam String id) {
		
		List<Curso> cursosL=cursoServicio.listaCursos();
		Alumno alumno = alumnoServicio.buscarAlumnoPorId(id);
		
		modelo.addAttribute("cursosL", cursosL);
		modelo.addAttribute("alumno", alumno);
		 
		return "alumnos.html";
	}
	
	@GetMapping("/modificarAlumno/{id}")
	public String modificarAlumno(ModelMap modeloA, @PathVariable String id) {
		
		List<Curso> cursosL = cursoServicio.listaCursos();
		
		Alumno alumnoE=alumnoServicio.buscarAlumnoPorId(id);
		
		modeloA.addAttribute("cursosL", cursosL);
		modeloA.addAttribute("alumnoE", alumnoE);
		
		return "editarAlumno.html";
	}
	
	@PostMapping("/alumnoModificado")
	public String alumnoModificado(ModelMap modeloA, @RequestParam String id, @RequestParam String nombre, @RequestParam Integer edad, @RequestParam String fecha, @RequestParam String historia) throws ParseException {
		
		
		try {
			
			SimpleDateFormat fechaFormateada = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaA = fechaFormateada.parse(fecha);
			
			alumnoServicio.modificarAlumno(id, nombre, edad, fechaA, historia);
			return "redirect:/administracion";
		} catch (ErrorServicio e) {
			e.printStackTrace();
		}
		
		return "editarAlumno.html";
	}
	
	@GetMapping("eliminarAlumno/{id}")
	public String eliminarAlumno(@PathVariable String id) {
		
		alumnoServicio.eliminarAlumno(id);
		
		return "redirect:/administracion";
	}
}
