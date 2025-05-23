package com.gs.geradorSenha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gs.geradorSenha.exception.GeradorException;
import com.gs.geradorSenha.model.dto.SenhaDTO;
import com.gs.geradorSenha.model.dto.SenhaListagemDTO;
import com.gs.geradorSenha.model.entity.Senha;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.model.repository.SenhaRepository;

@Service
public class SenhaService {

	@Autowired
	private SenhaRepository senhaRepository;

	public Senha criarSenha(Senha senha) throws GeradorException {
		return senhaRepository.save(senha);
	}

	 public HttpStatus cadastrarSenha(SenhaDTO dto, Usuario usuario) throws GeradorException{

	        if(senhaRepository.findByNome(dto.getNome()).isPresent()) {
	            throw new GeradorException("Nome do item já cadastrado", HttpStatus.CONFLICT);
	        }
	        Senha senha = new Senha();
	        senha.setNome(dto.getNome());
	        senha.setSenha(dto.getSenha());
	        senha.setUsuario(usuario);
	        senhaRepository.save(senha);

	        return HttpStatus.CREATED;
	    }
	
	   public void deletarItem(String itemId) throws GeradorException{
	        if (!senhaRepository.existsById(itemId)) {
	            throw new GeradorException("Item nao encontrado",HttpStatus.NOT_FOUND);
	        }
	        senhaRepository.deleteById(itemId);
	    }
	   
	   public List<SenhaListagemDTO> buscarSenhasDoUsuario(Long idUsuario) {
		   return senhaRepository.findAllByUsuarioId(idUsuario);		}
	

}
