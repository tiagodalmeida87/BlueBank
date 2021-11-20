package com.example.BlueBank.service;

import java.util.List;

import com.example.BlueBank.DTO.ContaDTO;
import com.example.BlueBank.models.Conta;

public interface ContaInterfaceService {

	Conta obterPorCod(Integer id);
	
	List<ContaDTO> obterTodos();	
	
	Conta criar(Conta conta);
	
	Conta atualizar(Integer id, Conta obj);
	
	void deletar(Integer id);
	
}
