package br.usjt.clima.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
@Table(name="semana")
public class Semana {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String dia;
	
	//Apenas para ilustrar o OneToOne, o correto seria fazer uma enumeração,
	//já que os dias da semana são fixos.
	@OneToOne(optional=true, cascade = {CascadeType.ALL})
	@JoinColumn (name="clima")
	private Clima clima;
}
