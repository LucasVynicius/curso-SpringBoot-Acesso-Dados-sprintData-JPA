<h1 align="center" Projeto de API de Biblioteca com Spring Boot, Spring Data JPA e PostgreSQL</h1>
<h3>Este projeto é uma API desenvolvida com Spring Boot que utiliza Spring Data JPA para acesso ao banco de dados e PostgreSQL como sistema de banco de dados. O ambiente de banco de dados é configurado no Docker, proporcionando uma solução rápida e eficiente para gerenciamento da base de dados.</h3>

<h2 align="center">Funcionalidades do Projeto</h2>
- `Cadastro de Autores e Livros:` O sistema permite o cadastro de autores e livros, com relacionamento entre eles.
- `Spring Data JPA:` Utilizado para realizar o acesso aos dados, com repositórios para entidades de Autor e Livro.
- `Pool de Conexões com HikariCP:` Configuração de um pool de conexões para otimizar a interação com o banco de dados.
- `Docker:` Ambiente configurado para rodar o PostgreSQL e o PgAdmin, com containers Docker para facilitar o setup.
<h2 align="center">Tecnologias Utilizadas</h2>
- `Spring Boot`: Framework para desenvolvimento da API.
- `Spring Data JPA`: Para mapeamento objeto-relacional e simplificação de consultas ao banco de dados.
- `PostgreSQL`: Sistema de gerenciamento de banco de dados relacional.
- `Docker`: Para criar um ambiente de banco de dados isolado e facilitar o setup do PostgreSQL e PgAdmin.
- `Lombok`: Para reduzir a quantidade de código boilerplate, gerando automaticamente getters, setters, construtores, etc.

<h2 align="center">Passos para Execução do Projeto</h2>
<h3 align="center">1. Criação e Configuração do Projeto</h3>
Este projeto foi criado utilizando o Spring Initializr para gerar um esqueleto básico da aplicação, com as dependências de Spring Web e Spring Data JPA.

* O pacote `config` foi utilizado para configurar a conexão com o banco de dados. A classe `DatabaseConfiguration` define o uso do HikariCP para configurar o DataSource e o pool de conexões.

@Configuration
public class DatabaseConfiguration {
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setMaximumPoolSize(10); // Máximo de conexões
        config.setConnectionTimeout(60000); // Timeout para conexão
        return new HikariDataSource(config);
    }
}

<h2 align="center">2. Ambiente de Banco de Dados no Docker</h2>
<h3 align="center">Criando o banco de dados PostgreSQL no Docker</h3>
Para criar o banco de dados PostgreSQL, execute o comando abaixo:

- `docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=****** -e POSTGRES_USER=****** -e POSTGRES_DB=library postgres:17.2`
  
*Subindo o PgAdmin no Docker
O PgAdmin4, ferramenta de administração do PostgreSQL, também pode ser rodado em um container Docker:
- `docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin --network library-network dpage/pgadmin4`
  
*Criando uma Network para Comunicação dos Containers
Para que os containers do PostgreSQL e PgAdmin se comuniquem, crie uma rede Docker e conecte os containers a ela:
- `docker network create library-network`

*Depois, conecte os containers à rede criada:
- `docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=***** -e POSTGRES_USER=***** -e POSTGRES_DB=library --network library-network -d postgres:17.2`
- `docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=***** -e PGADMIN_DEFAULT_PASSWORD=**** --network library-network -d dpage/pgadmin4`
  
<h2 align="center">3. Modelagem do Banco de Dados</h2>
As tabelas `autor` e `livro` foram modeladas e mapeadas para entidades JPA, com as seguintes definições:

*Entidade Autor
@Entity
@Table(name = "autor", schema = "public")
@ToString
@Getter
@Setter
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate data_nascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;
}

*Entidade Livro
@Entity
@Table(name = "livro")
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate data_publicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco")
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
<h2 align="center">4. Criando e Executando os Scripts no Banco de Dados</h2>
*Após rodar o PostgreSQL no Docker, você pode executar os seguintes scripts SQL para criar as tabelas autor e livro:

create table autor(
    id uuid not null primary key,
    nome varchar(100) not null,
    data_nascimento date not null,
    nacionalidade varchar(50) not null
);

create table livro (
    id uuid not null primary key,
    isbn varchar(20) not null,
    titulo varchar(150) not null,
    data_publicacao date not null,
    genero varchar(30) not null,
    preco numeric(18, 2),
    id_autor uuid not null references autor(id)
);
5. Configuração de Conexão com o Banco de Dados
No arquivo application.yml, configure a conexão com o banco de dados PostgreSQL:

yaml
Copiar código
spring:
  application:
    name: API Library
  datasource:
    url: jdbc:postgresql://localhost:5432/library
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
      
<h2 align="center">6. Como Mapear Entidades JPA</h2>
*As entidades Autor e Livro são mapeadas com anotações JPA para criar o relacionamento entre elas. O Spring Data JPA facilita a persistência dessas entidades sem a necessidade de escrever SQL diretamente.

Exemplo de repositório para a entidade Autor:

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
7. Configuração de Pool de Conexões
A configuração do pool de conexões é feita utilizando HikariCP, que já é a implementação padrão do Spring Boot para gerenciamento de conexões com banco de dados. A configuração detalhada está no arquivo DatabaseConfiguration.java.

Conclusão
Este projeto fornece uma boa base para um sistema de gerenciamento de livros e autores, com um ambiente configurado no Docker e acesso a dados via Spring Data JPA. Ele pode ser expandido com outras funcionalidades, como CRUD para livros e autores, validações de dados, autenticação e autorização, e mais.
