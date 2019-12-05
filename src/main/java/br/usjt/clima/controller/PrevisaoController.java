package br.usjt.clima.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.usjt.clima.core.Forecast;
import br.usjt.clima.model.Cidade;
import br.usjt.clima.model.Clima;
import br.usjt.clima.repository.CidadeRepository;
import br.usjt.clima.service.PrevisaoService;

@Controller
public class PrevisaoController {
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private PrevisaoService previsaoService;
	
	
	@GetMapping("/previsoes")
	public ModelAndView listarPrevisoes() {
		ModelAndView mv = new ModelAndView("listar_previsoes");
		List<Cidade> cidades = cidadeRepo.findAll();
		mv.addObject("cidades", cidades);
		return mv;
	}
	
	@GetMapping(value = "/buscarPrev/localizacao/{Latitude}/{Longitude}")
	public ModelAndView listarlocalizacao(@PathVariable double Latitude, @PathVariable double Longitude) {
		ModelAndView mv = new ModelAndView("listar_previsoes");
		Cidade cidades = cidadeRepo.findOneByLatitudeAndLongitude(Latitude, Longitude);
		mv.addObject("cidades", cidades);
		return mv;
	}
	
	@GetMapping(value = "/buscarPrev/nome/{cidade}")
	public ModelAndView listarnome(@PathVariable String cidade) throws InterruptedException, ExecutionException {
		ModelAndView mv = new ModelAndView("listar_previsoes");
		Future<List<Cidade>> cidades = cidadeRepo.findByNome(cidade);
		mv.addObject("cidades", cidades.get());
		return mv;
	}
	
	@GetMapping(value = "/buscarPrev/letra/{cidade}")
	public ModelAndView listarnomeCase(@PathVariable String cidade) throws InterruptedException, ExecutionException {
		ModelAndView mv = new ModelAndView("listar_previsoes");
		List<Cidade> cidades = cidadeRepo.findByNomeContainingIgnoreCase(cidade);
		mv.addObject("cidades", cidades);
		return mv;
	}
	
	@GetMapping(value = "/buscarPrev/cidade/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String consume(@PathVariable long cidadeId) throws IOException {
		//TODO: O que fazer se for inválido? throws IOException	
		Cidade cidade = cidadeRepo.findById(cidadeId).get();
		Forecast forecast = previsaoService.obter(cidade);
		List<Clima> climas = previsaoService.toClimaList(forecast, cidade);
		previsaoService.saveClimas(climas);		
		return "redirect:/previsoes";
	}
}
