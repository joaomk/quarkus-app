# altbank

## Português

Este projeto utiliza o **Quarkus**, o framework Java Supersônico e Subatômico.

Se quiser aprender mais sobre o Quarkus, visite o seu site: [https://quarkus.io/](https://quarkus.io/).

### Tecnologias

Este projeto é construído com as seguintes tecnologias:

- **Java 17**
- **Quarkus**
- **Maven**
- **MySQL**
- **Jakarta Persistence (JPA)**
- **Hibernate ORM**
- **RESTful APIs**
- **Flyway**
- **JUnit5**
- **Mockito**

### Instalação e Configuração

Para instalar e rodar este projeto, siga os seguintes passos:

1. Clone o repositório:
    ```bash
    git clone https://github.com/joaomk/quarkus-app.git
    ```
2. Navegue até o diretório do projeto:
    ```bash
    cd your-repository
    ```

3. Compile o projeto usando o Maven:
    ```bash
    mvn clean install
    ```

Antes de rodar o projeto, será necessário o Docker para rodar o banco de dados MySQL. No diretório raiz do projeto, há um arquivo de configuração Docker. Você pode iniciar o container MySQL usando o seguinte comando:

```shell script
docker-compose up
```

Isso irá configurar e rodar o banco de dados MySQL, que será acessado pela aplicação.

### Rodando a aplicação no modo dev

Você pode rodar a aplicação no modo dev, que habilita live coding, com o seguinte comando:

```shell script
./mvnw quarkus:dev
```

### Acessando o Swagger UI

Uma vez que a aplicação esteja rodando, você pode acessar o Swagger UI para interagir com a API REST e ver os endpoints disponíveis. O Swagger UI estará disponível em:

```bash
http://localhost:8080/q/swagger-ui/
```

Isso permitirá que você explore e teste os endpoints da API de maneira amigável.

### Rodando os Testes

Para rodar os testes, use o seguinte comando:

```bash
mvn test
```

### Migrations do Banco de Dados

Este projeto utiliza o **Flyway** para as migrations do banco de dados. Os scripts de migration do Flyway podem ser encontrados na pasta `src/main/resources/db/migration` dentro do repositório.

![DiagramaERAltbank](https://github.com/user-attachments/assets/9ceda7a5-ded6-4da2-b2d3-9cadeadf902d)

