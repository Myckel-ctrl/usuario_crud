# 🚀 User Management API

Uma API REST desenvolvida com **Java + Spring Boot**, focada em boas práticas de arquitetura, autenticação com JWT, autorização baseada em papéis (ROLE_USER e ROLE_ADMIN) e relacionamento entre entidades utilizando Spring Data JPA.

Este projeto foi desenvolvido com o objetivo de consolidar conhecimentos em desenvolvimento Backend com Java e servir como projeto de portfólio.

---

# 📚 Tecnologias

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT (JSON Web Token)
* Gredle
* Lombok
* Hibernate

---

# 🏗️ Arquitetura

O projeto foi organizado seguindo uma arquitetura em camadas.

```
src/main/java/com/usuario
│
├── controller
├── service
├── repository
├── entity
├── dto
├── converter
├── security
└── exception
```

Cada camada possui uma responsabilidade específica:

* **Controller** → Recebe as requisições HTTP.
* **Service** → Contém as regras de negócio.
* **Repository** → Comunicação com o banco de dados.
* **Entity** → Representação das tabelas.
* **DTO** → Objetos utilizados na comunicação da API.
* **Converter** → Conversão entre Entity e DTO.
* **Security** → Autenticação e autorização utilizando JWT.

---

# 🔒 Autenticação

A autenticação é realizada utilizando **JWT (JSON Web Token)**.

Após realizar o login, a API retorna:

```json
{
  "token": "...",
  "type": "Bearer",
  "email": "usuario@email.com",
  "role": "ROLE_USER"
}
```

As demais requisições autenticadas devem enviar o token no Header:

```
Authorization: Bearer seu_token
```

---

# 👤 Permissões

## Usuário

Pode:

* Visualizar seus próprios dados
* Atualizar seus próprios dados
* Excluir sua própria conta
* Gerenciar seus próprios endereços
* Gerenciar seus próprios telefones

Endpoints utilizados:

```
GET    /users/me
PUT    /users/me
DELETE /users/me
```

---

## Administrador

Pode:

* Listar todos os usuários
* Buscar qualquer usuário
* Atualizar qualquer usuário
* Excluir qualquer usuário

Endpoints utilizados:

```
GET    /users
GET    /users/{id}
PUT    /users/{id}
DELETE /users/{id}
```

---

# 🗂️ Relacionamentos

Um usuário pode possuir:

* vários endereços
* vários telefones

```
User
 │
 ├── Address
 └── Phone
```

---

# 🛠️ Configuração

## Banco de Dados

Configure o PostgreSQL e JWT:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/user_db
    username: postgres
    password: 1234
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect

jwt:
  secret-key: SuaChaveSecretaMuitoLonga1234567890ABCDEFGHIJ
  expiration: 86400000
```

---

# ▶️ Executando o projeto

## 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

## 2. Entre na pasta do projeto

```bash
cd seu-repositorio
```

## 3. Configure o banco de dados

Edite o arquivo `application.yml` com suas credenciais do PostgreSQL:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/user_db
    username: postgres
    password: sua_senha
```

## 4. Execute a aplicação

### Windows

```bash
.\gradlew bootRun
```

### Linux/macOS

```bash
./gradlew bootRun
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

# 📌 Principais funcionalidades

* Cadastro de usuários
* Login utilizando JWT
* Criptografia de senhas com BCrypt
* Controle de acesso por Roles
* CRUD completo de usuários
* CRUD completo de endereços
* CRUD completo de telefones
* Relacionamentos OneToMany e ManyToOne
* DTO Pattern
* Conversores Entity ↔ DTO
* Arquitetura em camadas
* Validações utilizando Bean Validation

---

# 📖 Endpoints

## Auth

```
POST /auth/register
POST /auth/login
```

## User

```
GET    /users/me
PUT    /users/me
DELETE /users/me

GET    /users
GET    /users/{id}
PUT    /users/{id}
DELETE /users/{id}
```

## Address

```
POST   /addresses
GET    /addresses
GET    /addresses/{id}
PUT    /addresses/{id}
DELETE /addresses/{id}
```

## Phone

```
POST   /phones
GET    /phones
GET    /phones/{id}
PUT    /phones/{id}
DELETE /phones/{id}
```

---

# 🎯 Objetivo

Este projeto foi desenvolvido para aprofundar conhecimentos em desenvolvimento Backend com Java e Spring Boot, aplicando conceitos utilizados em aplicações reais, como autenticação JWT, autorização baseada em papéis, arquitetura em camadas, boas práticas de organização de código e persistência de dados com Spring Data JPA.

---

# 👨‍💻 Autor

**Myckel de Vasconcelos Mota**

Projeto desenvolvido para fins de estudo e composição de portfólio.
