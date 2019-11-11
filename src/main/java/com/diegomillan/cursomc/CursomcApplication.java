package com.diegomillan.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.diegomillan.cursomc.domain.Categoria;
import com.diegomillan.cursomc.domain.Cidade;
import com.diegomillan.cursomc.domain.Cliente;
import com.diegomillan.cursomc.domain.Endereco;
import com.diegomillan.cursomc.domain.Estado;
import com.diegomillan.cursomc.domain.ItemPedido;
import com.diegomillan.cursomc.domain.Pagamento;
import com.diegomillan.cursomc.domain.PagamentoComBoleto;
import com.diegomillan.cursomc.domain.PagamentoComCartao;
import com.diegomillan.cursomc.domain.Pedido;
import com.diegomillan.cursomc.domain.Produto;
import com.diegomillan.cursomc.domain.enums.EstadoPagamento;
import com.diegomillan.cursomc.domain.enums.TipoCliente;
import com.diegomillan.cursomc.repositories.CategoriaRepository;
import com.diegomillan.cursomc.repositories.CidadeRepository;
import com.diegomillan.cursomc.repositories.ClienteRepository;
import com.diegomillan.cursomc.repositories.EnderecoRepository;
import com.diegomillan.cursomc.repositories.EstadoRepository;
import com.diegomillan.cursomc.repositories.ItemPedidoRepository;
import com.diegomillan.cursomc.repositories.PagamentoRepository;
import com.diegomillan.cursomc.repositories.PedidoRepository;
import com.diegomillan.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{


	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {


	}
}
