package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
//import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

public class BuscarFormaPagamentoMain {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		FormaPagamentoRepository formaPagamento = applicationContext.getBean(FormaPagamentoRepository.class);

		List<FormaPagamento> todasFormaPagamento = formaPagamento.listar();
		
		System.out.println("");
		for(FormaPagamento formasPagamentos : todasFormaPagamento) {
			System.out.println(formasPagamentos.getDescricao());
		}
		System.out.println("");
		
	}
	
}
