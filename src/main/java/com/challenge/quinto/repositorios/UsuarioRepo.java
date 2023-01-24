package com.challenge.quinto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.quinto.entidades.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario,String> {
	
	@Query ("SELECT u FROM Usuario u WHERE u.email = :email")
	public Usuario buscarPorMail (@Param("email")String mail);

}
