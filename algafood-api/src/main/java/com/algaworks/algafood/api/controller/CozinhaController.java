package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.RegistoCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RegistoCozinhaService registoCozinha;
	
	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		HttpHeaders headers = new HttpHeaders();

		if(cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cozinha);
		return ResponseEntity.notFound().build();
	}

	

	/*
	 	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		HttpHeaders headers = new HttpHeaders();

		if(cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cozinha);
		return ResponseEntity.notFound().build();
	}
	 */
	
	
	/*
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
		
		
	//		return ResponseEntity
	//				.status(HttpStatus.FOUND)
	//				.headers(headers)
	//				.body(cozinha);
		
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.headers(headers)
				.build();
	}
	*/
	
	/*	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		HttpHeaders headers = new HttpHeaders();
		
		return ResponseEntity.ok(cozinha);
	}
	*/
	
	/*
	//vem sem o corpo da resposta
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
 	*/
	
	
	/*
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

		return ResponseEntity.status(HttpStatus.OK).body(cozinha);
	}
	*/
	

/*	
	//eg @ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		HttpHeaders headers = new HttpHeaders();
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		//headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
		
		//return ResponseEntity.status(HttpStatus.OK).build();
		//return ResponseEntity.ok(cozinha);
		//		return ResponseEntity
		//				.status(HttpStatus.FOUND)
		//				.headers(headers)
		//				.build();
		
		if(cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();
	}
	
*/
	
	
	/*
	//teste de HttpStatus //4.19
	//@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable Long cozinhaId){
		return cozinhaRepository.buscar(cozinhaId);
	}
	*/

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return registoCozinha.salvar(cozinha);
	}
	 
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
			@RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);
		
		if (cozinhaAtual != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			cozinhaAtual = registoCozinha.salvar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	//é um método idenpotente, as requisições repetidas não retorna efeito colateral
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){
		try {
			registoCozinha.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
				
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Cozinha> listar2(){
		System.out.println("LISTAR 2");
		return cozinhaRepository.todas();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(cozinhaRepository.todas());
	}

	

	
}
