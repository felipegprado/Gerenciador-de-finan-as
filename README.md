# Gerenciador de Finanças Pessoais

**Matéria:** MC322  

**Membros da Equipe:** 
* Arthur Nascimento de Souza (269451) 
* Felipe Garcia Prado (281173) 
* Lucas Pachioni Coltro (285587) 
* Mateus Guerra Canelas (200098)

---


## Visão Geral
Um aplicativo desktop desenvolvido em Java para facilitar o controle e a organização das finanças pessoais. O sistema permite o gerenciamento de múltiplas carteiras, registro de receitas e despesas, visualização de gráficos e a configuração de alertas de gastos.

---

## Funcionalidades

###  Autenticação e Usuários
* **Cadastro e Login:** Criação de contas de usuário e acesso seguro ao sistema.  
* **Persistência Local:** Os dados dos usuários (incluindo credenciais) são salvos e carregados a partir de um arquivo `.json`.

### Gestão de Carteiras
* **Múltiplas Carteiras:** Crie diferentes carteiras (ex: Conta Corrente, Poupança, Viagem) para separar e organizar seu dinheiro de forma independente.
* **Saldo Integrado:** O sistema calcula o saldo individual de cada carteira e consolida o saldo total geral do usuário.  
* **Gerenciamento:** Adicione, visualize e remova carteiras de forma dinâmica através de uma interface intuitiva.

### Controle de Transações
* **Ganhos e Gastos:** Registre entradas e saídas de dinheiro. O sistema abate o saldo automaticamente ao registrar um gasto. 
* **Categorização por Tags:** Atribua tags personalizadas (ex: "Alimentação", "Lazer", "Salário") para classificar suas transações e facilitar buscas. 
* **Filtros e Buscas:** Localize transações específicas buscando por palavras-chave na descrição ou nas tags.

### Sistema de Alertas (Gatilhos)
* **Alerta de Orçamento:** Defina um valor limite para seus gastos; o sistema emite uma notificação automaticamente caso o total gasto ultrapasse o teto definido.  
* **Alerta de Vencimento:** Configure lembretes para contas a pagar e seja notificado quando a data de vencimento estiver próxima ou tiver chegado. 

### Gráficos e Histórico
* **Visualização de Dados:** Acesso rápido ao histórico de movimentações da carteira e visualização gráfica da saúde financeira (acessível via Tela Principal).

---



## Tecnologias Utilizadas

* **Linguagem:** Java 21Interface Gráfica: JavaFX (com uso de arquivos .fxml para estruturação de telas). 
* **Build Tool:** Gradle (com configuração sob demanda).
* **Testes Unitários:** JUnit 5 (Júpiter).Manipulação de JSON: Biblioteca Gson (Google). 
* **Arquitetura:** Orientação a Objetos baseada no padrão MVC (Model-View-Controller) integrado com o padrão Repository para abstração da persistência de arquivos. 

---

## Estrutura do Projeto

O código-fonte está dividido logicamente para garantir a separação de responsabilidades:
* **model:** Contém as classes puras de regra de negócio, entidades financeiras (Carteira, Transação, Ganhos, Gastos) e lógica abstrata de gatilhos.  
* **repository:** Centraliza toda a lógica de salvamento e leitura de dados externos (manipulação de arquivos JSON e TXT).  
* **controller:** Controladores do JavaFX responsáveis por interagir com a interface gráfica (View), capturar eventos de botões e atualizar telas.  

---

## Como Executar o Projeto

**Pré-requisitos:**
* Java Development Kit (JDK) 21 ou superior instalado.
* (Opcional) Extensões para Java e JavaFX no Visual Studio Code, ou uma IDE de sua preferência (como IntelliJ IDEA ou Eclipse).

## Como Executar
Para compilar e rodar a aplicação através do wrapper do Gradle, execute:

```java

./gradlew build
./gradlew run

```


## Como gerar os testes e pegar o HTML com a porcentagem de cobertura.

```java

./gradlew test jacocoTestReport

```

Feito o comando, vá pelo explorador de arquivos até app/build/reports/jacoco/test/html/index.html



