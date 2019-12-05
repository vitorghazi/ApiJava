package br.usjt.clima.controller;

import java.net.URI;
import java.util.List;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.usjt.clima.model.Cidade;
import br.usjt.clima.repository.CidadeRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {
	@Autowired
	private CidadeRepository cidadeRepo;


	@GetMapping("/lista")
	public List<Cidade> todas() {
		return cidadeRepo.findAll();
	}

	@GetMapping("/{id}")
	public Cidade buscarPeloId(@PathVariable Long id) {
		return cidadeRepo.getOne(id);
	}

	@GetMapping(value= {"/{cidade}"})
	public Future<List<Cidade>> todasCidades(@PathVariable String cidade) {
		return cidadeRepo.findByNome(cidade);
	}

	// TODO: Listar todas as cidades cujo nome começa com uma letra específica
	@GetMapping("/letra")
	public List<Cidade> todasCidadesIgnore(String letra) {
		return cidadeRepo.findByNomeContainingIgnoreCase(letra);
	}


	// TODO: Obter uma cidade por sua latitude e longitude
	@GetMapping(value = {"/{latitude}/{longitude}"})
	public Cidade findOneByLatitudeAndLongitude(@PathVariable Double latitude, Double longitude) {
		return cidadeRepo.findOneByLatitudeAndLongitude(latitude, longitude);
	}


	@PostMapping("/salvar")
	public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade, HttpServletResponse response) {
		Cidade c = cidadeRepo.save(cidade);
		URI uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/{id}")
				.buildAndExpand(c.getId())
				.toUri();
		return ResponseEntity.created(uri).body(c);
	}

}
