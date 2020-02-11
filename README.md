# WigWS

O projeto foi criado e executado na IDE NetBeans.
* É necessario inserir os .JAR das seguintes bibliotecas:
* GSON 2.3.1
* MSSQL JDBC 7.2.2 JR8

URL DA API:
http://localhost:8080/WigWS/webresources/
* o termo localhost deve ser trocado pelo ip da maquina de execução caso deseja-se consumir a api em maquinas virtuais, emuladores ou outros dispositivos

# URL DOS METODOS:  
---- **usuario**  
usuario/get/{login} - buscar usuario  
usuario/login/{login}/{senha} - validar login  
usuario/Listar - listar usuarios  
usuario/Excluir/{login} = excluir usuario - method not allowed - não funcionando  
usuario/Inserir - inserir usuario  
usuario/Alterar - alterar usuario  
  
  
---- **cliente**  
cliente/Cadastrar - cadastro de cliente  
cliente/Listar - listar clientes cadastrados  
cliente/get/{login} - busca um cliente especifico pelo login  
cliente/Alterar - alterar cadastro do cliente - POST  
cliente/atualizarIndex/{login} - atualiza a pagina principal do app  
- alteração e exclusão de clientes serão feitos pelo caminho de usuario  
  
  
---- **avaliação**  
cliente/Avaliacao/Listar - listar avaliações  
cliente/Avaliacao/Inserir - fazer avaliação  
cliente/Avaliacao/get/{conteudo} - busca uma avalilação pelo seu conteudo  
cliente/Avaliacao/minhas/{idcliente} - busca as avaliações de um cliente pelo seu id  
cliente/Avaliacao/Listar/{idempresa} - busca as avaliações de um determinada empresa pelo seu idempresa  
cliente/Avaliacao/Responder - responder avaliação - sera utilizado pelo cliente e pela emprssa  
  
  
---- **empresa**  
empresa/Inserir - inserir uma empresa  
empresa/Listar - listar todas as empresas  
empresa//get/{idempresa} - buscar uma empresa pelo seu idempresa  
empresa/pesquisa/{empresa} - buscar uma empresa pelo seu nome - usada para fazer a barra de pesquisa do app  
empresa/get/{CNPJ} - busca empresa pelo CNPJ  
empresa/get/nome/{nome} - busca empresa pelo seu nome  
  
  
----------------------------------------------------------------------------------------------------------------------------------------

Web Service em Java

It was necessary to create a Web Service to query and record data in an external database (SQL Server), and consume the data in an Android application.

Feito por um estudante para fins de entrega de trablho na faculdade

# Wig - Where I Go

Its purpose is the evaluation of establishments and problem solving between company and customer. Also enabling positive ratings
