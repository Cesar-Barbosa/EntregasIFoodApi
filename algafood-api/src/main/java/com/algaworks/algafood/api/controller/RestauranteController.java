package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.model.RestauranteXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RegistoRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private RegistoRestauranteService registoRestaurante;
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.listar();
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteRepository.buscar(restauranteId);

		if(restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}
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
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = registoRestaurante.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurante);
			
		} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest()
						.body(e.getMessage());
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);
			
			if (restauranteAtual != null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
				
				restauranteAtual = registoRestaurante.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual );
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest()
						.body(e.getMessage());
		}
	}
	
	
	//é um método idenpotente, as requisições repetidas não retorna efeito colateral
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId){
		try {
			registoRestaurante.excluir(restauranteId);
			return ResponseEntity.noContent().build();
				
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Restaurante> listar2(){
		System.out.println("LISTAR 2");
		return restauranteRepository.todas();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public RestauranteXmlWrapper listarXml(){
		return new RestauranteXmlWrapper(restauranteRepository.todas());
	}

	

	
}
