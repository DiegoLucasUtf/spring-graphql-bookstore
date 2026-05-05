# 📚 Bookstore GraphQL API

API de gerenciamento de livraria construída com **Spring Boot** e **GraphQL**, demonstrando os principais conceitos de consulta e manipulação de dados via GraphQL.

---

## 🛠️ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

| Ferramenta | Versão mínima | Download |
|---|---|---|
| Java (JDK) | 21 | [adoptium.net](https://adoptium.net) |
| Maven | 3.8+ | Embutido via `mvnw` |
| VS Code | Qualquer | [code.visualstudio.com](https://code.visualstudio.com) |

### Extensões recomendadas no VS Code

- **Extension Pack for Java** — suporte completo ao Java
- **Spring Boot Extension Pack** — ferramentas para Spring
- **GraphQL** (GraphQL Foundation) — syntax highlight para `.graphqls`

> **Nota:** O projeto usa o banco de dados **H2 em memória**, então não é necessário instalar nenhum banco de dados externo.

---

## 📁 Estrutura do Projeto

```
bookstore/
├── src/
│   ├── main/
│   │   ├── java/com/exemplo/bookstore/
│   │   │   ├── BookstoreApplication.java   # Classe principal
│   │   │   ├── model/
│   │   │   │   ├── Author.java             # Entidade Autor
│   │   │   │   └── Book.java               # Entidade Livro
│   │   │   ├── repository/
│   │   │   │   ├── AuthorRepository.java
│   │   │   │   └── BookRepository.java
│   │   │   ├── service/
│   │   │   │   ├── AuthorService.java
│   │   │   │   └── BookService.java
│   │   │   └── controller/
│   │   │       └── BookController.java     # Resolvers GraphQL
│   │   └── resources/
│   │       ├── graphql/
│   │       │   └── schema.graphqls         # Schema GraphQL
│   │       └── application.properties     # Configurações
└── pom.xml
```

---

## ⚙️ Configuração

### 1. Clonar ou criar o projeto

Se estiver criando via **Spring Initializr no VS Code**:

1. Pressione `Ctrl + Shift + P`
2. Selecione `Spring Initializr: Create a Maven Project`
3. Preencha:
   - **Group:** `com.exemplo`
   - **Artifact:** `bookstore`
   - **Java version:** `17`
4. Adicione as dependências: `Spring Web`, `Spring for GraphQL`, `Spring Data JPA`, `H2 Database`

### 2. Configurar o `application.properties`

O arquivo `src/main/resources/application.properties` já está configurado para rodar sem alterações:

```properties
# Banco H2 em memória (reinicia a cada execução)
spring.datasource.url=jdbc:h2:mem:bookstore
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Habilita o GraphiQL — interface visual para testes
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
```

> ⚠️ Por usar banco em memória, **todos os dados são perdidos** ao reiniciar a aplicação. Para persistência, substitua o H2 por PostgreSQL ou MySQL e ajuste as propriedades acima.

---

## ▶️ Executando a Aplicação

### Via terminal (Maven Wrapper)

Na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

### Via VS Code

1. Abra o arquivo `BookstoreApplication.java`
2. Clique em **Run** acima do método `main`, ou pressione `F5`

### Confirmando que está rodando

Aguarde a mensagem no console:

```
Started BookstoreApplication in X.XXX seconds (JVM running for X.XXX)
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 🧪 Testando a API

### Acessando o GraphiQL

Com a aplicação rodando, abra no navegador:

```
http://localhost:8080/graphiql
```

O GraphiQL é uma interface visual onde você escreve as queries na esquerda e vê os resultados na direita.

---

### Sequência de testes recomendada

#### 1️⃣ Criar um Autor

```graphql
mutation {
  createAuthor(name: "Machado de Assis", nationality: "Brasileiro") {
    id
    name
    nationality
  }
}
```

Resposta esperada:

```json
{
  "data": {
    "createAuthor": {
      "id": "1",
      "name": "Machado de Assis",
      "nationality": "Brasileiro"
    }
  }
}
```

#### 2️⃣ Criar um Livro

Use o `id` do autor retornado no passo anterior:

```graphql
mutation {
  createBook(
    title: "Dom Casmurro"
    genre: "Romance"
    publication_year: 1899
    authorId: 1
  ) {
    id
    title
    genre
    publication_year
    author {
      name
    }
  }
}
```

#### 3️⃣ Listar todos os Livros

```graphql
query {
  allBooks {
    id
    title
    genre
    publication_year
    author {
      name
      nationality
    }
  }
}
```

#### 4️⃣ Buscar Livro por ID

```graphql
query {
  bookById(id: 1) {
    title
    genre
    author {
      name
    }
  }
}
```

#### 5️⃣ Listar todos os Autores

```graphql
query {
  allAuthors {
    id
    name
    nationality
    books {
      title
    }
  }
}
```

#### 6️⃣ Testar busca com campos seletivos (poder do GraphQL)

```graphql
# Apenas títulos — sem nenhum dado extra
query {
  allBooks {
    title
  }
}
```

#### 7️⃣ Testar erro com ID inexistente

```graphql
query {
  bookById(id: 999) {
    title
  }
}
```

Resposta esperada:

```json
{
  "errors": [
    {
      "message": "Livro não encontrado: 999"
    }
  ],
  "data": {
    "bookById": null
  }
}
```

---



## 📋 Referência do Schema GraphQL

```graphql
type Author {
  id: ID!
  name: String!
  nationality: String
  books: [Book]
}

type Book {
  id: ID!
  title: String!
  genre: String
  year: Int
  author: Author
}

type Query {
  allBooks: [Book]
  bookById(id: ID!): Book
  allAuthors: [Author]
  authorById(id: ID!): Author
}

type Mutation {
  createAuthor(name: String!, nationality: String): Author
  createBook(title: String!, genre: String, year: Int, authorId: ID!): Book
}
```

---

## 🔗 Dependências principais

| Dependência | Finalidade |
|---|---|
| `spring-boot-starter-web` | Servidor HTTP (Tomcat embutido) |
| `spring-boot-starter-graphql` | Integração GraphQL ao Spring |
| `spring-boot-starter-data-jpa` | Persistência com JPA/Hibernate |
| `h2` | Banco de dados em memória |
| `lombok` | Redução de boilerplate (opcional) |

---
