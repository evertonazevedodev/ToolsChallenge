# ToolsChallenge - API de Pagamentos

## 📄 Objetivo do Projeto

Implementar uma **API de Pagamentos** para a área de cartões de crédito de um Banco, com a possibilidade de três operações: **Pagamento**, **Estorno** e **Consulta**, conforme o cenário proposto no desafio. 

A API é construída seguindo os conceitos **REST**, utiliza o formato **JSON** para comunicação e o protocolo padrão **HTTP** de retorno.

## ⚙️ Tecnologias

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.x
* **Servidor:** Tomcat (embutido no Spring Boot)
* **Persistência:** Spring Data JPA
* **Banco de Dados:** H2 Database (em memória)
* **Gerenciamento de dependências:** Maven
* **Testes:** JUnit 5, Mockito

## 🛠️ Como Executar o Projeto

### Pré-requisitos

* Java Development Kit (JDK) 17 ou superior
* Maven
* Uma IDE (IntelliJ IDEA, Eclipse, VS Code)

### Passos de Execução

1.  **Clonar o Repositório:**
    ```bash
    git clone [https://github.com/evertonazevedodev/ToolsChallenge.git]
    cd ToolsChallenge
    ```

2.  **Compilar e Executar com Maven:**
    ```bash
    ./mvnw spring-boot:run
    ```
    O servidor esta configurado na porta padrão `8080`.

## 📌 Endpoints da API

| Operação | Método HTTP | URI | Retorno |
| :--- | :--- | :--- | :--- |
| **Pagamento** | `POST` | `/pagamentos` | JSON de retorno de pagamento |
| **Estorno** | `GET` | `/estornos/{id}` | JSON de retorno de estorno |
| **Consulta** | `GET` | `/transacoes` ou `/transacoes/{id}` | Lista de transações (se não houver ID) ou transação única (se houver ID) na requisição |

### Exemplo de Requisição (Pagamento)

**`POST http://localhost:8080/pagamentos`**

```json
{
  "transacao": {
    "id": "100023568900001",
    "cartao": "4444 1234 5678 9010",
    "descricao": {
      "valor": "500.50",
      "dataHora": "01/05/2021 18:30:00",
      "estabelecimento": "PetShop Mundo cão"
    },
    "formaPagamento": {
      "tipo": "AVISTA",
      "parcelas": "1"
    }
  }
}
