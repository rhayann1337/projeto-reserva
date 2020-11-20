package com.projeto.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.projeto.reserva.model.Reserva;
import com.projeto.reserva.repository.Reservas;
import com.projeto.reserva.repository.ReservasFilter;

@Service
public class ReservaService {
	
	@Autowired
	private Reservas reservas;
	
	public void salvar(Reserva reserva) {
		try {
			reservas.save(reserva);
		}catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido.");
		}
	}

	public void excluir(Long codigo) {
		reservas.deleteById(codigo);
	}
	
	public List<Reserva> filtrar(ReservasFilter filtro){
		String nome = filtro.getNome() == null ? "" : filtro.getNome();
 		 return reservas.findByNomeContaining(nome);
	}

}
