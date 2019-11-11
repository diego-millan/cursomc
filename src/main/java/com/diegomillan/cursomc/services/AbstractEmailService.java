package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService{

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmation(Pedido pedido){
        SimpleMailMessage smm = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(smm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(pedido.getCliente().getEmail());

        smm.setFrom(sender);
        smm.setSubject("Pedido Confirmado - Código: " + pedido.getId());
        smm.setSentDate(new Date(System.currentTimeMillis()));
        smm.setText(pedido.toString());
        return smm;
    }
}
