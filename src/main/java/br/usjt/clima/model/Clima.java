package br.usjt.clima.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name="clima")
@NamedQuery (name = "Cidade.buscarPelaLatitudeEPelaLongitude",
query = "SELECT c FROM Cidade c WHERE latitude = :latitude AND longitude = :longitude")
public class Clima implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private Semana semana;
	private Date dataHora;
	@JsonAlias("temp_min")
	private Double tempMin;
	@JsonAlias("temp_max")
	private Double tempMax;
	@JsonAlias("humidity")
	private Double umidade;
	private String descricao;
	private String iconName;
	
	public String getIcone() {
		return "http://openweathermap.org/img/w/" + iconName + ".png";
	}
	
	@JsonBackReference
	@ManyToOne
	Cidade cidade;
	
}
