package com.challenge.quinto.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.quinto.entidades.Usuario;
import com.challenge.quinto.enumeraciones.Rol;
import com.challenge.quinto.errores.ErrorServicio;
import com.challenge.quinto.repositorios.UsuarioRepo;

import jakarta.transaction.Transactional;

@Service
public class UsuarioSevicio {
	
	@Autowired
	private UsuarioRepo usuarioRepo;
	
	@Transactional
	public void registrarUsuario (String nombre, String mail, String clave, String clave2) throws ErrorServicio {
		
		validar (nombre, mail, clave, clave2);
		
		Usuario usuario = new Usuario();
		
		usuario.setNombre(nombre);
		usuario.setMail(mail);
		usuario.setClave(new BCryptPasswordEncoder().encode(clave));
		
		usuario.setRol(Rol.admin);
		
		usuarioRepo.save(usuario);
		
	}
	
	public void validar (String nombre, String mail, String clave, String clave2) throws ErrorServicio {
		
		if (nombre.isEmpty()|| nombre==null) {
			throw new ErrorServicio ("El nombre no puede ser vacio o nulo");
		}
		if (mail.isEmpty()|| mail==null) {
			throw new ErrorServicio ("El mail no puede ser vacio o nulo");
		}
		if (clave.isEmpty()|| clave==null || clave.length() <5) {
			throw new ErrorServicio ("La clave debe tener al menos 5 digitos");
		}
		if (!clave.equals(clave2)) {
			throw new ErrorServicio ("Las claves ingresadas deben ser iguales");
		}
		
	}

//	@Override
//	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
//		Usuario usuario = usuarioRepo.buscarPorMail(mail);
//		if(usuario!=null) {
//			
//			List <GrantedAuthority> permisos= new ArrayList();
//			
//			GrantedAuthority p= new SimpleGrantedAuthority("ROLE_"+ usuario.getRol());
//			
//			permisos.add(p);
//			
//			return new User(usuario.getMail(), usuario.getClave(),permisos);
//		}else {
//			return null;
//		}
//	}

}
