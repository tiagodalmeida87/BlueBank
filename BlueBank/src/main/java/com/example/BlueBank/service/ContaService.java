package com.example.BlueBank.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BlueBank.DTO.ContaDTO;
import com.example.BlueBank.models.Conta;
import com.example.BlueBank.repositories.ContaRepository;

@Service
public class ContaService implements ContaInterfaceService {

	@Autowired
	ContaRepository contaRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ContaDTO obterPorCod(Integer id) {
		Optional<Conta> obj = this.contaRepository.findById(id);
		return contaDTO(obj.get());
	}

	@Override
	public List<ContaDTO> obterTodos() {
		return this.contaRepository.findAll().stream().map(this::contaDTO).collect(Collectors.toList());
	}

	@Override
	public Conta criar(Conta conta) {
		return this.contaRepository.save(conta);
	}

	public Conta atualizar(Integer id, ContaDTO obj) {
		ContaDTO newObj = obterPorCod(id);
		Conta conta = conta(newObj);
		conta.setTipoConta(obj.getTipoConta());
		conta.setNumeroConta(obj.getNumeroConta());
		conta.setAgencia(obj.getAgencia());
		// newObj.setSaldo(obj.getSaldo());
		return contaRepository.save(conta);
	}

	public Conta atualizarStatus(Integer id, ContaDTO obj) {
		ContaDTO newObj = obterPorCod(id);
		Conta conta = conta(newObj);
		conta.setStatus(obj.isStatus());
		return contaRepository.save(conta);
	}

	@Override
	public void deletar(Integer id) {
		obterPorCod(id);
		this.contaRepository.deleteById(id);
	}

	private ContaDTO contaDTO(Conta conta) {
		return modelMapper.map(conta, ContaDTO.class);
	}

	private Conta conta(ContaDTO contaDTO) {
		return modelMapper.map(contaDTO, Conta.class);
	}

	@Override
	public Conta obterContaPorCod(Integer id) {
		Optional<Conta> obj = this.contaRepository.findById(id);
		return obj.orElse(null);

	}

}
