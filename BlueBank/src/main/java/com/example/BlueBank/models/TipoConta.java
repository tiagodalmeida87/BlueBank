package com.example.BlueBank.models;



public enum TipoConta {
	
	CORRENTE(1),POUPANCA(2), SALARIO(3);

	public int id;
	
	
	private TipoConta(int id) {
		this.id = id;
	}
	
	
}
