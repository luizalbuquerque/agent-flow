# Sistema Web de Upload e Processamento de Arquivos XML

Este é um projeto que consiste em um sistema web desenvolvido com Angular e Spring Boot. O objetivo é permitir o upload e processamento de arquivos XML contendo informações de agentes e regiões. O sistema exibirá os códigos de agentes recebidos e permitirá a recuperação de dados consolidados por região.

## Tecnologias Utilizadas

- Frontend:
    - Angular 12+
    - Angular Material 12+
    - TypeScript 4+
    - RxJS 6+
    - Node.js 14+

- Backend:
    - Spring Boot 2
    - Maven 3
    - JPA
    - Hibernate

## Funcionalidades

### Frontend (Angular)

- Interface Web para upload de arquivos XML.
- Utilização do tema Indigo do Angular Material para uma experiência visual agradável.
- Exibição de um loader durante o envio de arquivos para indicar o processamento.
- Exibição dos códigos de agentes após o processamento do arquivo.

### Backend (Spring Boot)

- Aceita arquivos XML enviados pelo frontend e processa suas informações.
- Imprime os códigos de agentes recebidos na saída padrão (System.out).
- Armazena os dados dos agentes e regiões no banco de dados.

## Explorando Swagger e Endpoints da aplicação.

#### Você pode visualizar todos os endPoints da aplicação no link abaixo.

```http
  http://localhost:8080/swagger-ui/index.html
```

```http
  http://localhost:8080/api-docs
```

#### Você ver se a aplicação está de pé usando Health Checker.

```http
  http://localhost:8080/api/health-check
```

#### Você ver se a aplicação está de pé usando Health Checker.

```http
  http://localhost:8080/api-yoursupplierapp/health-check
```

## Requisitos e Instruções

### Requisitos

- Desenvolver a interface web com Angular Material e realizar o upload de arquivos XML.
- Exibir um loader durante o processamento dos arquivos.
- Implementar o processamento dos arquivos XML no backend e imprimir os códigos de agentes.
- Salvar os dados dos agentes e regiões no banco de dados.
- Implementar a recuperação de dados consolidados por região.

### Instruções

1. Clone este repositório para o seu ambiente local.
2. Certifique-se de ter as versões recomendadas das tecnologias instaladas.
3. Configure a conexão com o banco de dados no backend (Spring Boot).
4. No diretório do frontend (Angular), execute `npm install` para instalar as dependências.
5. No diretório do frontend, execute `ng serve` para iniciar o servidor de desenvolvimento do Angular.
6. No diretório do backend (Spring Boot), execute a aplicação Spring Boot para iniciar o servidor.
7. Acesse a aplicação Angular no navegador através da URL `http://localhost:4200`.

## Contribuição

Este projeto foi desenvolvido com base em um desafio específico. Contribuições são bem-vindas para melhorias, correções de bugs ou novas funcionalidades. Basta seguir as diretrizes de contribuição e abrir um pull request.


# Checklist de Requisitos

Aqui está um checklist que resume os requisitos do projeto:

## Frontend (Angular)

- [x] Criar uma interface web para upload de arquivos XML.
- [x] Utilizar o tema Indigo do Angular Material para o desenvolvimento da interface.
- [x] Exibir um loader durante o envio de arquivos para informar ao usuário que estão sendo processados.

## Backend (Spring Boot)

- [x] Aceitar arquivos XML enviados pelo frontend e processar suas informações.
- [x] Imprimir os códigos de agentes recebidos na saída padrão (System.out).
- [x] Salvar os dados dos agentes e regiões no banco de dados.
- [x] Implementar a recuperação de dados consolidados por região.

## Requisitos Gerais

- [x] Os arquivos XML podem conter uma lista de agentes com código e data, bem como informações sobre quatro regiões (SE, S, NE, N) com valores numéricos de geração, compra e preço médio.
- [x] Os arquivos XML seguem o formato especificado nos exemplos fornecidos.
- [x] Não é necessário validar os dados dos arquivos, considera-se que eles estão sempre corretos e no formato especificado.
- [x] Os arquivos XML devem ser processados um a um, sequencialmente.
- [x] Os dados de preço médio (/agentes/agente[]/submercado[]/precoMedio) são confidenciais e devem ser removidos ou substituídos por valores em branco antes de serem enviados.

## Tecnologias Utilizadas

- [x] Front-end:
  - [x] Angular 12+
  - [x] Angular Material 12+
  - [x] TypeScript 4+
  - [x] RxJS 6+
  - [x] Node.js 14+

- [x] Back-end:
  - [x] Spring Boot 2
  - [x] Maven 3
  - [x] JPA
  - [x] Hibernate

## Outros Requisitos

- [x] Disponibilizar o projeto em um repositório Git público (por exemplo, GitHub ou Bitbucket).
- [x] Implementar a documentação interativa da API com Swagger.

Este checklist resume os principais requisitos do projeto e pode ser usado para verificar o progresso da implementação.
