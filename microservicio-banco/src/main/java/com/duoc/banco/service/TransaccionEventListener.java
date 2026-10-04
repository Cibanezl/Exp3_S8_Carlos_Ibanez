package com.duoc.banco.service;

import com.duoc.banco.model.TransaccionEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class TransaccionEventListener {

    @JmsListener(destination = "cola.transacciones.banco")
    public void escucharTransaccion(TransaccionEvent evento) {
        System.out.println("----------------------------------------");
        System.out.println("[JMS] Evento recibido en la cola:");
        System.out.println("ID Transaccion: " + evento.getIdTransaccion());
        System.out.println("Cuenta: " + evento.getCuentaId());
        System.out.println("Monto: $" + evento.getMonto());
        System.out.println("Tipo: " + evento.getTipo());
        System.out.println("Fecha: " + evento.getFecha());
        System.out.println("----------------------------------------");
    }
}