package com.duoc.banco.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.banco.model.TransaccionEvent;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/api/v1/banco")
public class BancoController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${mensaje.bienvenida:Mensaje por defecto}")
    private String mensajeBienvenida;

    @GetMapping("/publico")
    public ResponseEntity<Map<String, Object>> publico() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "200 OK");
        response.put("config_server_message", mensajeBienvenida);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cuenta/{id}")
    @CircuitBreaker(name = "bancoService", fallbackMethod = "fallbackCuenta")
    public ResponseEntity<Map<String, Object>> obtenerCuenta(@PathVariable String id) {
        if ("error".equalsIgnoreCase(id)) {
            throw new RuntimeException("Fallo simulado para probar Circuit Breaker");
        }

        Map<String, Object> cuenta = new HashMap<>();
        cuenta.put("id", id);
        cuenta.put("titular", "Carlos Ibáñez");
        cuenta.put("tipoCuenta", "Cuenta Corriente");
        cuenta.put("saldoDisponible", 1550000.00);
        cuenta.put("estado", "ACTIVA");

        return ResponseEntity.ok(cuenta);
    }

    @PostMapping("/transaccion")
    @CircuitBreaker(name = "bancoService", fallbackMethod = "fallbackTransaccion")
    public ResponseEntity<Map<String, Object>> registrarTransaccion(@RequestBody Map<String, Object> request) {
        String cuentaId = (String) request.get("cuentaId");
        Double monto = Double.parseDouble(request.get("monto").toString());
        String tipo = (String) request.get("tipo");
        String idTx = "TX-" + System.currentTimeMillis();

        // 1. Creación evento
        TransaccionEvent evento = new TransaccionEvent(idTx, cuentaId, monto, tipo);

        // 2. Enviar a la cola asíncrona de ActiveMQ
        jmsTemplate.convertAndSend("cola.transacciones.banco", evento);

        // 3. Responde al cliente
        Map<String, Object> response = new HashMap<>();
        response.put("status", "201 CREATED");
        response.put("mensaje", "Transaccion registrada y enviada a la cola JMS");
        response.put("idTransaccion", idTx);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<Map<String, Object>> fallbackCuenta(String id, Throwable t) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "CIRCUIT_BREAKER_ACTIVE");
        response.put("mensaje", "El servicio de cuentas presenta degradacion.");
        return ResponseEntity.status(503).body(response);
    }

    public ResponseEntity<Map<String, Object>> fallbackTransaccion(Map<String, Object> request, Throwable t) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "CIRCUIT_BREAKER_ACTIVE");
        response.put("mensaje", "No se pudo procesar la transaccion. Canal degradado.");
        return ResponseEntity.status(503).body(response);
    }
}