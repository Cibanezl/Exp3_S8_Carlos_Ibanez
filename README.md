# Evaluación Sumativa 3 - Arquitectura de Microservicios

**Asignatura:** Desarrollo Backend III (PBY2203)
**Estudiante:** Carlos Ibáñez
**Proyecto:** Exp3_S8_Carlos_Ibanez

---

## 📌 Descripción del Proyecto

Sistema distribuido basado en arquitectura de microservicios con **Spring Boot 3** y **Java 17**, preparado para entornos de alta disponibilidad con resiliencia, mensajería asíncrona, seguridad JWT y orquestación con **Docker Compose**.

### Componentes de la Arquitectura
* **`config-server` (Puerto 8888):** Servidor de configuración centralizada.
* **`eureka-server` (Puerto 8761):** Registro y descubrimiento de servicios (Service Discovery).
* **`microservicio-banco` (Puerto 8081):** Servicio del negocio bancario con seguridad, resiliencia y cola de mensajes.

---

## 🛠️ Tecnologías Utilizadas

* **Java 17** & **Spring Boot 3.3.3**
* **Spring Cloud Config** & **Spring Cloud Netflix Eureka**
* **Spring Security + OAuth2 Resource Server (JWT)**
* **Resilience4j:** Patrón Circuit Breaker y mecanismos de Fallback.
* **JMS (Apache ActiveMQ Artemis):** Procesamiento de mensajes en la cola `cola.transacciones.banco`.
* **Docker & Docker Compose:** Contenerización y despliegue multi-contenedor.

---

## 🚀 Pasos para Ejecutar el Proyecto

### 1. Compilación de artefactos Java
Desde la terminal en la raíz del proyecto:

```powershell
cd microservicio-banco
mvn clean package -DskipTests
cd ..

docker compose up --build

## 🌐 Documentación de Endpoints (Postman)

| Método | Endpoint | Autenticación | Descripción |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/banco/publico` | Pública (`No Auth`) | Consulta pública de bienvenida. |
| `GET` | `/api/v1/banco/cuenta/{id}` | Basic Auth (`user_banco` / `banco123`) | Obtención de detalles de cuenta. |
| `POST` | `/api/v1/banco/transaccion` | Basic Auth (`user_banco` / `banco123`) | Registro y publicación de transacción en la cola JMS. |


🔐 Credenciales de Prueba
Usuario: user_banco

Contraseña: banco123


