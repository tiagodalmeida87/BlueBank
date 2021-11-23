package com.example.BlueBank.service;

import java.util.List;

import com.example.BlueBank.DTO.TransacaoDTO;
import com.example.BlueBank.exceptions.ContaNaoEncontradaException;
import com.example.BlueBank.exceptions.SaldoInsuficienteException;
import com.example.BlueBank.models.Conta;
import com.example.BlueBank.models.Transacoes;

public interface TransacaoInterfaceService {

	Transacoes obterPorCod(Integer id);
	
	List<Transacoes> obterTodos();
	
	Transacoes transferenciaContas(Conta origem, Conta destino, Double valor) throws SaldoInsuficienteException, ContaNaoEncontradaException;
	
	Transacoes deposito(Conta conta, Double valor) throws ContaNaoEncontradaException;
	
	Transacoes saque(Conta conta, Double valor) throws SaldoInsuficienteException, ContaNaoEncontradaException;

	TransacaoDTO obterPorCodDTO(Integer id);

	List<TransacaoDTO> obterTodosDTO();
	
}
