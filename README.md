# PRINTI - Casa San'Giovanni | Adega de Bebidas

Projeto referente à disciplina de Projeto Integrador I (PRINTI).

## 1. Sobre o projeto

Este projeto visa o desenvolvimento de uma aplicação desktop de gerenciamento de uma adega de bebidas chamada Casa San'Giovanni. Nela, o administrador pode gerir informações acerca de estoque de produtos, seus fornecedores, clientes, vendas, entre outras funcionalidades. Apresentando uma interface clara e amigável para o usuário.

## 2. Tecnologias utilizadas

<img src="https://cdn-icons-png.flaticon.com/128/5968/5968282.png" width="50" height="50">

## 3. Dependências necessárias

As dependências a seguir são utilizadas para garantir o funcionamento completo da aplicação:

- **Hibernate Validator** `8.0.1.Final`
- **Spring Boot Starter Web**
- **Spring Boot Starter Data JPA**
- **PostgreSQL Driver**
- **Flyway Core** `11.0.0`
- **Flyway PostgreSQL**
- **Lombok**
- **Jakarta Validation API** `3.0.2`

Além disso, bibliotecas externas adicionais utilizadas:

- `google.code.gson`
- `io.github.cdimascio.dotenv.java`
- `apache.xmlgraphics.batik.all`
- `toedter.jcalendar`
- `google.zxing.core`
- `google.zxing.javase`
- `itextpdf`

## 4. Como rodar a aplicação

### Pré-requisitos

Antes de iniciar, certifique-se de que você possui instalado:

- Java 21+
- Maven
- PostgreSQL
- IDE preferencialmente (IntelliJ) 

### Configuração do banco de dados

1. Crie um banco de dados PostgreSQL com o nome `winecellar`.
2. Configure o usuário e senha como:
   - **Usuário:** `postgres`
   - **Senha:** `sua senha`
   - **Porta:** `8080` (de preferência)

> ⚠️ Esses dados estão configurados no plugin Flyway no `pom.xml` e `aplication.properties`. Altere conforme sua necessidade.

### Passos para executar a aplicação

1. **Clone o repositório:**
```bash
git clone https://github.com/DevGuiBan/Wine-Cellar-Management.git
```

2. **Execute o seguinte arquivo .java localizado na partição abaixo:**
```bash
src/main/java/com/ggnarp/winecellarmanagement/WineCellarManagementApplication.java
```

3. **Abra separadamente a pasta Teste_Sangiovanni e execute o seguinte arquivo .java localizado na partição abaixo:**
```bash
Teste_Sangiovanni/src/resources/interface_card/Login.java
```

3. **Certifique que o .env em " Teste_Sangiovanni" está com a porta correta:**
```bash
Teste_Sangiovanni/src/.env
```

> 💡 Certifique-se de que sua IDE ou linha de comando está configurada para usar Java 21.

---

## 5. Funcionalidades

As funcionalidades do sistema estão de acordo com as competências dos atores. Os atores deste sistema estão organizados da seguinte forma:    

* **Administrador:** administrador geral da aplicação desktop Casa San'Giovanni. Gestão de estoque de produtos, fornecedores, clientes, vendas, funcionários, etc.
* **Funcionário:** usuário da aplicação desktop Casa San'Giovanni. Gestão de estoque de produtos, fornecedores, clientes, vendas, etc.

[🎥 Clique aqui para ver o vídeo de demonstração](https://github.com/DevGuiBan/Wine-Cellar-Management/blob/main/explainer-video/casa-sanGiovanni.mp4)

## 7. Colaboradores

🍺 André Casimiro da Silva  (https://github.com/Andre-nemesis)  
🍹 Francisca Geovanna de Lima da Silva  (https://github.com/FranciscaGeovanna)  
🍸 Guilherme Bandeira Dias  (https://github.com/DevGuiBan)  
🍷 Nickolas Davi Vieira Lima  (https://github.com/niickol4s)  
🍾 Raimundo Gabriel Pereira Ferreira  (https://github.com/thegabriew)

### Link do protótipo no Figma:
[Protótipo Casa San'Giovanni](https://www.figma.com/design/JbmlVKAGLE1rgJCDOVfqbZ/Sistema-Adega---PRINTI?node-id=0-1&t=AhQDesWIjSlnnrH6-1)
