# Mobilium

Aplicação desktop desenvolvida em **JavaFX** para gerenciamento simples de ordens de serviço de uma oficina mecânica.

## Funcionalidades

- Cadastro de ordens de serviço
- Edição e exclusão de registros
- Cadastro de cliente, veículo, serviço e valor
- Validação do campo de valor
- Formatação monetária em Real (R$)
- Interface estilizada com CSS

## Tecnologias

- Java 17
- JavaFX 21
- Maven
- CSS

## Como executar

Com o Maven instalado, execute na pasta do projeto:

```bash
mvn javafx:run
```

Também é possível executar pelo Eclipse utilizando **Run As → Maven build...** com o goal:

```text
javafx:run
```

## Estrutura principal

```text
src/main/java/br/edu/ifma/oficina/
├── App.java
└── OrdemServico.java

src/main/resources/
└── styles.css
```

---

Projeto acadêmico desenvolvido para prática de programação desktop com JavaFX.

## Equipe: 
- Felipe da Costa
- Luan Lucas
- Luís Felype
- Vitor Sâmile 
