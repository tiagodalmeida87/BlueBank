package com.example.BlueBank.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.BlueBank.DTO.ClienteDTO;
import com.example.BlueBank.DTO.ContaDTO;
import com.example.BlueBank.DTO.TransacaoDTO;
import com.example.BlueBank.exceptions.ClienteJaPossuiContaException;
import com.example.BlueBank.exceptions.ClienteNaoEncontradaException;
import com.example.BlueBank.exceptions.ContaNaoEncontradaException;
import com.example.BlueBank.exceptions.PossuiSaldoException;
import com.example.BlueBank.models.Conta;
import com.example.BlueBank.models.Transacoes;
import com.example.BlueBank.repositories.ContaRepository;

@Service
public class ContaService implements ContaInterfaceService {

	@Autowired
	ContaRepository contaRepository;

	@Autowired
	ClienteService clienteService;

	@Autowired
	TransacaoService transacaoService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ContaDTO obterPorCod(Integer id) throws ContaNaoEncontradaException {
		Conta obj = this.contaRepository.findById(id).orElseThrow(ContaNaoEncontradaException::new);

		return contaDTO(obj);
	}

	public Page<TransacaoDTO> obterContasPaginadas(Integer id, Pageable page) throws ContaNaoEncontradaException {
		Conta obj = this.contaRepository.findById(id).orElseThrow(ContaNaoEncontradaException::new);
		ContaDTO objDTO = contaDTO(obj);

		Page<Transacoes> personPage = contaRepository.findAllByConta(objDTO.getId(), page);
		int totalElements = (int) personPage.getTotalElements();
		return new PageImpl<TransacaoDTO>(
				personPage.getContent().stream().map(this::transacaoDTO).collect(Collectors.toList()), page,
				totalElements);
	}

	@Override
	public List<ContaDTO> obterTodos() {
		return this.contaRepository.findAll().stream().map(this::contaDTO).collect(Collectors.toList());
	}

	@Override
	public ContaDTO criar(Conta conta) throws ClienteNaoEncontradaException, ClienteJaPossuiContaException {
		ClienteDTO cliente = clienteService.obterPorCod(conta.getCliente().getId());
		Conta clienteConta = contaRepository.findByClienteConta(conta.getCliente().getId());
		conta.getCliente().setNome(cliente.getNome());

		String corpo = "Olá " + cliente.getNome() + ", segue os dados de sua conta\n\nConta: " + conta.getNumeroConta()
				+ "\nAgência: " + conta.getAgencia() + "\nTipo da conta: " + conta.getTipoConta() + "\nSaldo: R$ "
				+ conta.getSaldo() + "\n\n Obrigado - Teste - Undefined Coders";

		ContaDTO contaDTO = contaDTO(conta);
		if (clienteConta == null) {
			// AwsSNSClient.sendToTopic("Bem Vindo ao BlueBank", corpo);
			this.contaRepository.save(conta);
			return contaDTO;
		}
		throw new ClienteJaPossuiContaException();
	}

	public ContaDTO atualizar(Integer id, ContaDTO obj) throws ContaNaoEncontradaException {
		// ContaDTO newObj = obterPorCod(id);
		Conta conta = obterContaPorCod(id);
		conta.setTipoConta(obj.getTipoConta());
		conta.setNumeroConta(obj.getNumeroConta());
		conta.setAgencia(obj.getAgencia());
		// newObj.setSaldo(obj.getSaldo());
		contaRepository.save(conta);
		return contaDTO(conta);
	}

	public ContaDTO atualizarStatus(Integer id, ContaDTO obj) throws ContaNaoEncontradaException, PossuiSaldoException {
//		ContaDTO newObj = obterPorCod(id);
		Conta conta = obterContaPorCod(id);
		conta.setStatus(obj.isStatus());
		contaRepository.save(conta);
		return contaDTO(conta);
	}

	@Override
	public void deletar(Integer id) throws ContaNaoEncontradaException {
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
	public Conta obterContaPorCod(Integer id) throws ContaNaoEncontradaException {
		Optional<Conta> obj = this.contaRepository.findById(id);
		return obj.orElseThrow(() -> new ContaNaoEncontradaException());

	}

	public TransacaoDTO transacaoDTO(Transacoes transacao) {
		return modelMapper.map(transacao, TransacaoDTO.class);
	}

}
