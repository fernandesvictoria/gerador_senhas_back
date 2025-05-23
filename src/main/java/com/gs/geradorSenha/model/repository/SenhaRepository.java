package com.gs.geradorSenha.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gs.geradorSenha.model.dto.SenhaListagemDTO;
import com.gs.geradorSenha.model.entity.Senha;

@Repository
public interface SenhaRepository extends JpaRepository<Senha, String>, JpaSpecificationExecutor<Senha> {
	
	@Query("SELECT new com.gs.geradorSenha.model.dto.SenhaListagemDTO(s.id, s.nome, s.senha) FROM Senha s WHERE s.usuario.id = :usuarioId")
	List<SenhaListagemDTO> findAllByUsuarioId(@Param("usuarioId") Long usuarioId);

	Optional<Senha> findByNome(String nome);

	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Senha s WHERE s.nome = :nome AND s.usuario.id = :usuarioId")
	boolean existsByNomeAndUsuarioId(String nome, Long usuarioId);
}