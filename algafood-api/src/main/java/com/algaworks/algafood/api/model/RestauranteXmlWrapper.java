package com.algaworks.algafood.api.model;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;
import lombok.NonNull;

/*
 * @JacksonXmlRootElement é uma alternativa à @JsonRootName e 
 * @JacksonXmlProperty à @JsonProperty.
 * 
 * A diferença é que as anotações iniciadas com @JacksonXml só afetam
 * a serialização em XML. Já as anotações iniciadas com @Json
 * afetam tanto a serialização JSON como também XML.
 */

@JsonRootName("restaurante")
//@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class RestauranteXmlWrapper {

	@JsonProperty("restaurante")
//	@JacksonXmlProperty(localName = "cozinha")
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	private List<Restaurante> restaurantes;
	
}