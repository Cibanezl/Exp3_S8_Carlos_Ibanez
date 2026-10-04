package com.duoc.banco.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TransaccionEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idTransaccion;
    private String cuentaId;
    private Double monto;
    private String tipo;
    private String fecha;

    public TransaccionEvent() {}

    public TransaccionEvent(String idTransaccion, String cuentaId, Double monto, String tipo) {
        this.idTransaccion = idTransaccion;
        this.cuentaId = cuentaId;
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = LocalDateTime.now().toString();
    }

    public String getIdTransaccion() { return idTransaccion; }
    public void setIdTransaccion(String idTransaccion) { this.idTransaccion = idTransaccion; }

    public String getCuentaId() { return cuentaId; }
    public void setCuentaId(String cuentaId) { this.cuentaId = cuentaId; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
