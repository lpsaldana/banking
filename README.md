# 🏦 Microservicio Bancario - Proyecto Modular con Spring Boot y Docker

Este proyecto es una arquitectura de microservicios desarrollada en Java 17 con Spring Boot 3.4.3, organizada en módulos independientes:

- `eureka-server`: Registro de servicios (Service Discovery)
- `config-server`: Servidor de configuración centralizada
- `api-gateway`: Gateway API para enrutar solicitudes
- `auth-service`: Servicio de autenticación
- `account-service`: Servicio de cuentas bancarias

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3.4.3
- Spring Cloud Netflix Eureka
- Spring Cloud Config
- WebFlux (programación reactiva)
- Docker & Docker Compose

---

## 🚀 Cómo levantar el proyecto

### 1. Compilar los servicios

Desde la raíz del proyecto:

```bash
mvn clean package -DskipTests
```

### 2. Levantar los contenedores

```bash
docker-compose up --build
```

Esto levantará los servicios en el siguiente orden:

1. `eureka-server` (puerto `8761`)
2. `config-server` (puerto `8888`)
3. `api-gateway` (puerto `8080`)
4. `auth-service` (puerto `8081`)
5. `account-service` (puerto `8082`)

---

## 📦 Estructura del Proyecto

```
/project-root
│
├── eureka-server/
├── config-server/
├── api-gateway/
├── auth-service/
├── account-service/
├── docker-compose.yml
└── README.md
```

---

## 📌 Endpoints útiles

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Config Server**: http://localhost:8888

---

## 📄 Licencia

Este proyecto es de uso libre para fines educativos o como base para sistemas bancarios modulares.
