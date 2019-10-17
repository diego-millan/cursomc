package com.diegomillan.cursomc.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE (1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(1, "Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
		
		EstadoPagamento estadoPagamento = null;
		
		for (EstadoPagamento estado : EstadoPagamento.values()) {
			if(cod.equals(estado.getCod())) {
				estadoPagamento = estado;
				break;
			}
		}
		
		if (estadoPagamento == null) {
			throw new IllegalArgumentException("Id Inválido: " + cod);
		}
		
		return estadoPagamento;
	}
}
