package com.projeto.reserva.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.projeto.reserva.model.Reserva;
import com.projeto.reserva.model.Sala;
import com.projeto.reserva.repository.Reservas;
import com.projeto.reserva.repository.ReservasFilter;
import com.projeto.reserva.repository.Salas;
import com.projeto.reserva.service.ReservaService;


@Controller
@RequestMapping("/reservas")
public class ReservaController {
private static final String CADASTRO_VIEW = "CadastroReserva";

	@Autowired
	private Reservas reservas;
	
	@Autowired
	private Salas salas;
	
	@Autowired
	private ReservaService reservaService;

	@RequestMapping("/novo")
	public ModelAndView novo() {

		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Reserva());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Reserva reserva, Errors errors, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		if (errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		try {
			reservaService.salvar(reserva);
			attributes.addFlashAttribute("mensagem", "Reserva salva com sucesso!");
			return "redirect:/reservas";	
		}
		catch(IllegalArgumentException e) {
			errors.rejectValue("data", null, e.getMessage());
			return CADASTRO_VIEW;
		}
		
	}

	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") ReservasFilter filtro) {
		List<Reserva> todasReservas = reservaService.filtrar(filtro);
		ModelAndView mv = new ModelAndView("PesquisaReservas");
		mv.addObject("reservas", todasReservas);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Long codigoReserva) {
		Reserva reserva = reservas.getOne(codigoReserva);
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(reserva);
		return mv;
	}

	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
	reservaService.excluir(codigo);
	attributes.addFlashAttribute("mensagem", "Reserva excluída com sucesso!");
	return "redirect:/reservas";
	}
	
	
	@ModelAttribute("listaSalas")
	public List<Sala> listaSalas(){
		List<Sala> listaSalas = new ArrayList<Sala>();
		salas.findAll().forEach(e -> listaSalas.add(e));
		return listaSalas;
	}

}
