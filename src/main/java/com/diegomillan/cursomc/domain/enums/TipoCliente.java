package com.diegomillan.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA (2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		
		TipoCliente tipoCliente = null;
		
		if (cod != null) {
			for(TipoCliente tipo : TipoCliente.values()) {
				if (cod.equals(tipo.getCod())) {
					tipoCliente = tipo;
				}
			}
		}
		if (tipoCliente == null) {
			throw new IllegalArgumentException("Id Inválido: " + cod);
		}
		
		return tipoCliente;
	}
}
