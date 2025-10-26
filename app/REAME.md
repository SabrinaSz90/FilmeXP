# Documentação do Projeto FilmeXP

## Visão Geral

O **FilmeXP** é um aplicativo Android que permite aos usuários explorar uma vasta biblioteca de filmes. 
Ele fornece funcionalidades para filtrar filmes por gênero, visualizar detalhes e adicionar filmes aos favoritos. 
O aplicativo é construído utilizando a API do The Movie Database (TMDb) para buscar dados sobre filmes e gêneros.

## Estrutura do Projeto

O projeto é organizado em pacotes, cada um com responsabilidades específicas:

### 1. `adapter`
Contém a classe `Filmes_Adapter`, que gerencia a exibição de uma lista de filmes em um `RecyclerView`.

- **Filmes_Adapter.java**
    - Responsável por criar e vincular as visualizações dos filmes.
    - Implementa um `ViewHolder` para otimizar o desempenho do `RecyclerView`.

### 2. `api`
Inclui a configuração da API e a interface de comunicação com a API para obter dados sobre filmes e gêneros.

- **ApiConfig.java**
    - Configura o cliente Retrofit com logging e adiciona a chave da API em cada requisição.
    - Define a URL base da API do TMDb.

- **TmdbApi.java**
    - Define os métodos para as chamadas à API:
        - `getGenres()`: Obtém a lista de gêneros de filmes.
        - `getMoviesByGenre(int genreId)`: Obtém filmes filtrados por gênero.

### 3. `filmexp`
Contém as atividades principais do aplicativo que gerenciam a interface do usuário.

- **MainActivity.java**
    - Tela inicial onde os usuários podem selecionar um gênero de filme e buscar.
    - Configura um `Spinner` para a seleção de gêneros e um `Button` para iniciar a busca.

- **FilmesActivity.java**
    - Exibe os filmes de acordo com o gênero selecionado na `MainActivity`.
    - Utiliza um `RecyclerView` para mostrar a lista de filmes e permitir a interação do usuário.

### 4. `model`
Define as classes de modelo que representam os dados dos filmes e gêneros.

- **Filme.java**
    - Representa um filme com atributos como `id`, `title`, `overview`, `posterPath`, `releaseDate`, e `voteAverage`.
    - Inclui métodos getter e uma função para gerar a URL completa do poster.

- **Filme_Resposta.java**
    - Contém uma lista de filmes retornados pela API.

- **Genre.java**
    - Representa um gênero de filme com atributos `id` e `name`.

- **GenreResponse.java**
    - Contém uma lista de gêneros retornados pela API.

### 5. `utils`
Inclui a classe `PrefsManager`, que gerencia as preferências do usuário.

- **PrefsManager.java**
    - Gerencia a persistência de dados utilizando `SharedPreferences`.
    - Métodos para salvar a última pesquisa, adicionar e remover filmes dos favoritos e verificar se um filme é favorito.

## Funcionalidades

1. **Busca de Filmes por Gênero**:
    - O usuário pode selecionar um gênero de filme em um `Spinner`.
    - Ao clicar no botão de busca, o aplicativo faz uma chamada à API para obter filmes desse gênero.

2. **Favoritar Filmes**:
    - O usuário pode marcar filmes como favoritos.
    - O estado dos favoritos é salvo nas preferências do aplicativo, permitindo que o usuário acesse seus favoritos mais tarde.

3. **Exibição de Detalhes do Filme**:
    - Ao clicar em um filme, uma mensagem é exibida com o título do filme.
    - Futuramente, uma tela de detalhes pode ser implementada para mostrar mais informações sobre o filme.

4. **Persistência de Última Pesquisa**:
    - O aplicativo armazena a última pesquisa realizada pelo usuário.
    - Essa informação é exibida na tela inicial, permitindo que os usuários vejam rapidamente sua última interação.

## Tecnologias Utilizadas

- **Android SDK**: Para o desenvolvimento do aplicativo.
- **Retrofit**: Para fazer chamadas à API do TMDb de forma simples e eficiente.
- **Picasso**: Para carregar e exibir imagens de forma otimizada.
- **SharedPreferences**: Para armazenar dados simples, como favoritos e últimas pesquisas.

## Como Usar

1. **Configurar Chave da API**:
   ## API do The Movie Database (TMDb)

**The Movie Database (TMDb)** 
é uma API pública que fornece acesso a uma vasta biblioteca de dados sobre filmes, séries de TV, atores e muito mais. 
A API permite que desenvolvedores integrem informações sobre filmes em seus aplicativos, oferecendo recursos como busca de filmes, obtenção de detalhes,
listas de gêneros, e imagens de pôsteres.

### Funcionalidades da API

- **Busca de Filmes**: Permite pesquisar filmes por título, gênero, data de lançamento, etc.
- **Obtenção de Detalhes**: Fornece informações detalhadas sobre um filme específico, incluindo resumo, elenco, avaliações e imagens.
- **Listas de Gêneros**: Permite obter uma lista de todos os gêneros de filmes disponíveis.
- **Imagens**: Fornece URLs para imagens de pôsteres e cenas de filmes.

### Como Configurar a API no Filmexp

Para utilizar a API do TMDb no projeto Filmexp, siga as etapas abaixo:

#### 1. Criação de uma Conta no TMDb

1. Acesse o site do TMDb: [https://www.themoviedb.org/](https://www.themoviedb.org/).
2. Clique em "Sign Up" e preencha as informações necessárias para criar uma conta.
3. Após criar a conta, faça login.

#### 2. Obtenção da Chave da API

1. Após fazer login, vá para a seção de configurações do usuário (ícone de perfil no canto superior direito).
2. Selecione "Settings" e, em seguida, vá para a aba "API".
3. Clique em "Create" para solicitar uma nova chave de API.
4. Preencha as informações necessárias e siga as instruções para gerar sua chave.

#### 3. Configuração da Chave da API no Projeto

1. Abra o projeto FilmExp em seu ambiente de desenvolvimento (IDE).
2. Navegue até o arquivo `ApiConfig.java`, localizado no pacote `api`.
3. Localize a constante `API_KEY` e substitua o valor `"35f1daaa1452ca768ad97aa17bae1f3b"` pela sua chave de API gerada.

```java
private static final String API_KEY = "SUA_CHAVE_AQUI"; // Substitua pela sua chave
```

#### 4. Teste a Conexão com a API

Após configurar a chave da API, você pode testar a conexão fazendo uma chamada para um dos endpoints disponíveis.
Por exemplo, você pode executar o método `getGenres()` para obter a lista de gêneros e verificar se os dados são retornados corretamente.

#### 5. Restrições e Limitações

- **Limites de Taxa**: A API possui limites de taxa que restringem o número de requisições que podem ser feitas em um determinado período.
Consulte a documentação do TMDb para detalhes sobre limites específicos.
- **Uso Responsável**: Certifique-se de seguir as diretrizes de uso da API e não sobrecarregar os servidores com requisições excessivas.

### Referências

Para mais informações sobre a API do TMDb, consulte a documentação oficial: [TMDb API Documentation](https://developers.themoviedb.org/3).
Esta documentação fornece detalhes sobre todos os endpoints disponíveis, parâmetros necessários e exemplos de requisições.

2. **Executar o Aplicativo**:
    - Compile e execute o aplicativo em um dispositivo Android ou em um emulador.
    - A tela inicial permitirá que você selecione um gênero e busque filmes.

3. **Interação do Usuário**:
    - Selecione um gênero no `Spinner` e clique em "Buscar".
    - Navegue pela lista de filmes e clique em um filme para ver seu título.
    - Use o ícone de favorito para adicionar ou remover filmes da lista de favoritos.

## Estrutura de Diretórios

```
com.example.filmexp
│
├── adapter
│   └── Filmes_Adapter.java
│
├── api
│   ├── ApiConfig.java
│   └── TmdbApi.java
│
├── filmexp
│   ├── MainActivity.java
│   └── FilmesActivity.java
│
├── model
│   ├── Filme.java
│   ├── Filme_Resposta.java
│   ├── Genre.java
│   └── GenreResponse.java
│
└── utils
    └── PrefsManager.java
```

## Contribuição

Se você deseja contribuir para o projeto, siga estas etapas:

1. **Fork o Repositório**: Crie um fork do repositório para sua conta.
2. **Crie uma Branch**: Crie uma nova branch para suas alterações (`git checkout -b minha-feature`).
3. **Faça suas Alterações**: Implemente suas alterações ou melhorias.
4. **Commit suas Alterações**: Faça um commit com uma mensagem descritiva (`git commit -m "Adicionando nova funcionalidade"`).
5. **Envie um Pull Request**: Envie um pull request para o repositório original.

## Licença

Este projeto é licenciado sob a [MIT License](LICENSE). Sinta-se à vontade para usar, modificar e distribuir o código, desde que a atribuição adequada seja dada.