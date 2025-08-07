# Prueba Jikkosft

Proyecto de ejemplo para gestión de pedidos utilizando Spring Boot.

## Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- JWT (JSON Web Token) para autenticación
- Gradle
- Base de datos relacional (ej: PostgreSQL, MySQL)
- Mockito y JUnit 5 para pruebas unitarias

## Endpoints principales

### 1. Login de usuario

curl --location 'http://localhost:8080/user/login' \
--header 'Content-Type: application/json' \
--data '{
    "usuario": "admin",
    "clave": "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4"
}'


### 2. Crear Pedido

El token devuelto al consumir el servicio /user/login se debe agregar en el curl

curl --location 'http://localhost:8080/api/pedidos/crear' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1NDYwNzExNSwiZXhwIjoxNzU0NjEwNzE1fQ.8YH8WUlVUnqeStadjXPX_u2nlLSIW8kMgomqA0kbBdM' \
--data '{
    "id_cliente": 1,
    "productos": [
      { "idProducto": 10, "cantidad": 2 },
      { "idProducto": 2, "cantidad": 1 }
    ],
    "estrato": 3,
    "ciudad": "Bogotá"
  }'
