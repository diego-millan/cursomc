package com.diegomillan.cursomc.services;

import com.diegomillan.cursomc.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendOrderConfirmation(Pedido pedido);
    void sendEmail(SimpleMailMessage message);
}
