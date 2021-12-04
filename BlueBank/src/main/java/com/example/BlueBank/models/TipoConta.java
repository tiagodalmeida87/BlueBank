package com.example.BlueBank.models;


public enum TipoConta {
	
	CORRENTE(1),POUPANCA(2), SALARIO(3);

	public int id;
	
	TipoConta(int id) {
		
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
