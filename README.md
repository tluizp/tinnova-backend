# Veículos API

Uma API RESTful desenvolvida em Java para gerenciar veículos, com suporte para operações de CRUD e filtros avançados.

## Endpoints

### **GET /veiculos**
Retorna todos os veículos.

### **GET /veiculos?marca={marca}&ano={ano}&cor={cor}**
Retorna todos os veículos de acordo com os parâmetros passados.

### **GET /veiculos/{id}**
Retorna os detalhes do veículo com o ID fornecido.

### **POST /veiculos**
Adiciona um novo veículo.

### **PUT /veiculos/{id}**
Atualiza todos os dados de um veículo.

### **PATCH /veiculos/{id}**
Atualiza apenas alguns dados do veículo.

### **DELETE /veiculos/{id}**
Apaga o veículo.

---

## Requisitos

Certifique-se de ter os seguintes softwares instalados:
- **Java 8 ou superior**
- **Gradle** (para gerenciar dependências e construir o projeto)
- **IntelliJ IDEA Community** (ou qualquer IDE de sua escolha)

## Como Configurar e Executar o Projeto

### **1. Clone o repositório**
Use o seguinte comando no terminal para clonar o projeto:
```bash
git clone <URL_DO_REPOSITORIO>
cd <NOME_DA_PASTA_DO_PROJETO>
```

### Abra o projeto no IntelliJ IDEA
- **Abra o IntelliJ IDEA.**
- **Vá para File > Open e selecione o diretório do projeto.**
- **O IntelliJ detectará automaticamente o Gradle ou Maven. Aguarde o carregamento das dependências.**

### Compile e Execute

Use o Gradle para compilar e executar a aplicação.
```bash
./gradlew bootRun.
```
