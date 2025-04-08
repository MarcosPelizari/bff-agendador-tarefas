# BFF Agendador de Tarefas

## Descrição
O BFF Agendador de Tarefas é uma aplicação Java com Spring Boot que unifica as APIs de Usuários, Agendador de Tarefas e Notificações, servindo como ponto de entrada para o frontend. Ele gerencia autenticação, tarefas e notificações, integrando as APIs via Feign Clients e executando um cron a cada 5 minutos para enviar notificações de tarefas agendadas.

## Pré-requisitos
- **Java**: 17 ou superior
- **Gradle**: 8.x (usado para gerenciar dependências)
- **API de Usuários**: Deve estar rodando em `http://localhost:8080`
- **API de Agendador de Tarefas**: Deve estar rodando em `http://localhost:8081`
- **API de Notificações**: Deve estar rodando em `http://localhost:8082`
- **IntelliJ IDEA** (opcional, recomendado para desenvolvimento)
- **Postman** (recomendado para testar os endpoints)

## Dependências de Repositórios
O BFF depende das seguintes APIs, que devem ser clonadas e configuradas antes de iniciar o projeto. Use os links abaixo para clonar cada repositório:

- **API de Usuários**  
  Gerencia autenticação e dados de usuários.  
  ```bash
  git clone https://github.com/MarcosPelizari/usuario.git

- **API de Notificações**
   Envia emails como notificações.
  ```bash
   git clone https://github.com/MarcosPelizari/notificacao.git

- **API de Agendador de Tarefas**
   Gerencia o agendamento e manipulação de tarefas.
  ```bash
   git clone https://github.com/MarcosPelizari/agendador-tarefas.git

## Instalação
1. **Clone o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/bff-agendador-tarefas.git
   cd bff-agendador-tarefas
   ```
2. **Configure as URLs das APIs**:
  - Verifique as configurações no arquivo `src/main/resources/application.properties`:
```
    spring.application.name=bff-agendador
    usuario.url=localhost:8080/usuario
    agendador-tarefas.url=localhost:8081/tarefas
    notificacao.url=localhost:8082/email
    server.port=8083
    cron.horario=0 0/5 * * * ?
    usuario.email=admin@admin.com
    usuario.senha=1234
```
## Como Rodar
1. **Inicie as APIs Dependentes**:
   - Certifique-se de que a API de Usuários está rodando em `http://localhost:8080`.
   - Certifique-se de que a API de Agendador de Tarefas está rodando em `http://localhost:8081`.
   - Certifique-se de que a API de Notificações está rodando em `http://localhost:8082`.

2. **Inicie o BFF**:
   - `BffAgendadorTarefasApplication.java`

3. **Acesse o BFF**:
   - O BFF estará disponível em `http://localhost:8083`.

## Como Usar
### 1. Fazer Login e Obter um Token
Use o endpoint `/usuario/login` para autenticar e gerar um token JWT.

- **POST /usuario/login**  
  Autentica um usuário e retorna um token JWT.  
  **Body**:
  ```json
  {
    "email": "admin@admin.com",
    "senha": "1234"
  }
  ```

    - Resposta:
    ```
    Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
    ```

### 2. Criar um Usuário
Use o endpoint `/usuario` para criar um novo usuário.
  -  POST /usuario
  Cria um novo usuário.
```json
{
  "nome": "João Silva",
  "email": "joao.silva@exemplo.com",
  "senha": "senha123",
  "enderecos": [
    {
      "rua": "Rua Exemplo",
      "numero": "123",
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "12345-678"
    }
  ],
  "telefones": [
    {
      "ddd": "11",
      "numero": "987654321"
    }
  ]
}
```

### 3. Buscar Dados do Usuário
Use o endpoint `/usuario` com o parâmetro `email` e o token no header para buscar os dados do usuário.

- **GET /usuario?email=<email>**  
  Busca os dados de um usuário por email.  
  **Parâmetro**:
  - `email`: Email do usuário (ex.: `joao.silva@exemplo.com`).  
  **Header**:
    - `key`: Authorization
    - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

**Nota**: Não inclua o prefixo "Bearer" no token.  
**Resposta**:
```json
{
  "nome": "João Silva",
  "email": "joao.silva@exemplo.com",
  "enderecos": [
    {
      "id": 1,
      "rua": "Rua Exemplo",
      "numero": "123",
      "cidade": "São Paulo",
      "estado": "SP",
      "cep": "12345-678"
    }
  ],
  "telefones": [
    {
      "id": 1,
      "ddd": "11",
      "numero": "987654321"
    }
  ]
}
```

### 3. Criar uma tarefa
Use o endpoint `/tarefas` para criar uma nova tarefa, enviando o token no header.


- **POST /tarefas**  
  Cria uma nova tarefa (requer token).  
  **Header**:
    - `key`: Authorization
    - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
**Nota**: Não inclua o prefixo "Bearer" no token.  
**Resposta**:
```json
{
  "titulo": "Reunião de Equipe",
  "descricao": "Reunião para discutir o projeto X",
  "dataInicio": "2025-04-05T10:00:00",
  "dataFim": "2025-04-05T11:00:00",
  "emailUsuario": "joao.silva@exemplo.com",
  "status": "PENDENTE"
}
```
### 5. Buscar Tarefas por Email
Use o endpoint `/tarefas` com o token no header para buscar as tarefas de um usuário.

- **GET /tarefas**  
  Busca tarefas por email (requer token).  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
**Nota**: Não inclua o prefixo "Bearer" no token.  
**Resposta**:
```json
[
  {
    "id": "12345",
    "titulo": "Reunião de Equipe",
    "descricao": "Reunião para discutir o projeto X",
    "dataInicio": "2025-04-05T10:00:00",
    "dataFim": "2025-04-05T11:00:00",
    "emailUsuario": "joao.silva@exemplo.com",
    "status": "PENDENTE"
  }
]
```
### 6. Buscar Tarefas por Período

Use o endpoint `/tarefas/eventos` para buscar tarefas agendadas em um período específico.

- **GET /tarefas/eventos**  
  Busca tarefas por email (requer token).  
  **Parâmetros**:
  - `dataInicial`: Data inicial (ex.: `2025-04-05T00:00:00`).
  - `dataFinal`: Data final (`ex.: 2025-04-06T23:59:59`).
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

**Nota**: Não inclua o prefixo "Bearer" no token.  
**Resposta**:
```json
[
  {
    "id": "12345",
    "titulo": "Reunião de Equipe",
    "descricao": "Reunião para discutir o projeto X",
    "dataInicio": "2025-04-05T10:00:00",
    "dataFim": "2025-04-05T11:00:00",
    "emailUsuario": "joao.silva@exemplo.com",
    "status": "PENDENTE"
  }
]
```

### 7. Deletar uma Tarefa
Use o endpoint `/tarefas` com o parâmetro `id` para deletar uma tarefa.

- **DELETE /tarefas?id=<id>**  
  Deleta uma tarefa por ID (requer token).  
  **Parâmetro**:
  - `id`: ID da tarefa a ser deletada (ex.: `12345`).  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

  **Nota**: Não inclua o prefixo "Bearer" no token.  
**Resposta**: Status 200 (OK).

### 8. Atualizar uma Tarefa
Use o endpoint `/tarefas` com o parâmetro `id` para atualizar uma tarefa.

- **PUT /tarefas?id=<id>**  
Atualiza uma tarefa (requer token).  
**Parâmetro**:
- `id`: ID da tarefa a ser atualizada (ex.: `12345`).  
**Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

**Nota**: Não inclua o prefixo "Bearer" no token.  
**Body**:
```json
{
  "titulo": "Reunião de Equipe Atualizada",
  "descricao": "Reunião para discutir o projeto Y",
  "dataInicio": "2025-04-05T14:00:00",
  "dataFim": "2025-04-05T15:00:00",
  "emailUsuario": "joao.silva@exemplo.com",
  "status": "PENDENTE"
}
```
### 9. Alterar o Status de uma Tarefa
Use o endpoint `/tarefas` com os parâmetros `status` e `id` para atualizar uma tarefa.

- **PATCH /tarefas?status=<status>&id=<id>**  
  Altera o status de uma tarefa (requer token).
**Parâmetro**:
  - `status`: Novo status (ex.: `NOTIFICADO`).  
  - `id`: ID da tarefa (ex.: `12345`).
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

**Nota**: Não inclua o prefixo "Bearer" no token.  
**Body**:
```json
{
  "id": "12345",
  "titulo": "Reunião de Equipe Atualizada",
  "descricao": "Reunião para discutir o projeto Y",
  "dataInicio": "2025-04-05T14:00:00",
  "dataFim": "2025-04-05T15:00:00",
  "emailUsuario": "joao.silva@exemplo.com",
  "status": "NOTIFICADO"
}
```
### 10. Atualizar Dados do Usuário
Use o endpoint `/usuario` para atualizar os dados do usuário.

- **PUT /usuario**  
  Atualiza os dados do usuário (requer token).  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
**Nota**: Não inclua o prefixo "Bearer" no token.  
**Body**:
```json
{
  "nome": "João Silva Atualizado",
  "email": "joao.silva@exemplo.com",
  "senha": "nova_senha123"
}
```

### 11. Cadastrar um Novo Endereço
Use o endpoint `/usuario/endereco` para cadastrar um novo endereço para o usuário (requer token).
Nota: Este endpoint delega a operação para a API de Usuários.

- **POST /usuario/endereco**   
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
**Nota**: Não inclua o prefixo "Bearer" no token.  
**Body**:
```json
{
  "rua": "Nova Rua",
  "numero": "456",
  "cidade": "Rio de Janeiro",
  "estado": "RJ",
  "cep": "87654-321"
}
```

### 12. Cadastrar um Novo Telefone
Use o endpoint `/usuario/telefone` para cadastrar um novo telefone para o usuário (requer token).
Nota: Este endpoint delega a operação para a API de Usuários.

- **POST /usuario/telefone**   
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
**Nota**: Não inclua o prefixo "Bearer" no token.  
**Body**:
```json
{
  "ddd": "21",
  "numero": "912345678"
}
```

### 13. Atualizar um Endereço
Use o endpoint `/usuario/endereco` para atualizar um endereço existente do usuário (requer token).  
**Nota**: Este endpoint delega a operação para a API de Usuários.

- **PUT /usuario/endereco?id=<id>**  
  **Parâmetro**:
  - `id`: ID do endereço a ser atualizado (ex.: `1`).  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
**Nota**: Não inclua o prefixo "Bearer" no token.  
**Body**:
```json
{
  "rua": "Rua Atualizada",
  "numero": "789",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "12345-678"
}
```

### 14. Atualizar um Telefone
Use o endpoint `/usuario/telefone` para atualizar um endereço existente do usuário (requer token).  
**Nota**: Este endpoint delega a operação para a API de Usuários.

- **PUT /usuario/telefone?id=<id>**  
  **Parâmetro**:
  - `id`: ID do telefone a ser atualizado (ex.: `1`).  
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
**Nota**: Não inclua o prefixo "Bearer" no token.  
**Body**:
```json
{
  "ddd": "11",
  "numero": "998877665"
}
```
### 15. Deletar um Usuário
Use o endpoint `/usuario/{email}` para atualizar um endereço existente do usuário (requer token).  
**Nota**: Este endpoint delega a operação para a API de Usuários.

- **DELETE /usuario/{email}**  
  **Parâmetro**:
  - `email`: Email do usuário a ser deletado (ex.: `joao.silva@exemplo.com`). 
  **Header**:
  - `key`: Authorization
  - `value`: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
**Nota**: Não inclua o prefixo "Bearer" no token.  
**Resposta**: Status 200 (OK).

## Notas
- O BFF executa um cron a cada 5 minutos que busca tarefas agendadas para a próxima hora, envia notificações por email via API de Notificações, e atualiza o status das tarefas para `NOTIFICADO`.
- A autenticação usa JWT. O token gerado no login é válido por 1 hora.
- Para rotas protegidas, inclua o token no header `Authorization` sem o prefixo "Bearer".
- O BFF depende das APIs de Usuários, Agendador de Tarefas e Notificações. Certifique-se de que todas estejam rodando antes de iniciar o BFF.


