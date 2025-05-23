# 🔐 Gerador de Senhas - Backend

Este é o backend de um gerenciador de senhas desenvolvido com Java e Spring Boot. Ele permite o cadastro, listagem e remoção de senhas seguras associadas a usuários autenticados.

---

## 🚀 Tecnologias

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- MySql
- Maven

---

## ⚙️ Pré-requisitos

- Java 17+
- Maven
- Git

- Deve existir um folder de resources com app.key, app.pub e application.properties.
---


## 🧪 Testes no Postman

Após rodar o projeto localmente, você pode testar os endpoints.

### 🔐 Autenticação

O sistema utiliza autenticação JWT. Para acessar as rotas protegidas, siga os passos:

1. **Cadastrar um novo usuário**
POST gs/auth/novo

{
"email": "usuario@teste.com",
"senha": "senhaSegura123"
}

2. **Login**
   Basic Auth
   POST gs/auth/login

email: "usuario@teste.com",
senha: "senhaSegura123"

3. **Listar senhas**
   GET gs/senha/senhas
   
5. **Cadastrar senha**
    POST gs/senha
   
7. **Deletar senha**
   DEL gs/senha/{idSenha}
---
## 🛠️ Como rodar o projeto localmente

1. **Criar banco**
   - Criar banco senhas_db

3. **Clone o repositório**
```bash
git clone https://github.com/fernandesvictoria/gerador_senhas_back.git
cd gerador_senhas_back

