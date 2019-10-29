# WigWS

O projeto deve ser executado na IDE NetBeans.

URL DA API:
http://localhost:8080/WigWS/webresources/
* o termo localhost deve ser trocado pelo ip da maquina de execução caso deseja-se consumir a api em maquinas virtuais, emuladores ou outros dispositivos

URL DOS METODOS:
---- usuario
usuario/get/{login} - buscar usuario
usuario/login/{login}/{senha} - validar login
usuario/Listar - listar usuarios
usuario/Excluir/{login} = excluir usuario - method not allowed - não funcionando 
usuario/Inserir - inserir usuario
usuario/Alterar - alterar usuario

---- cliente
cliente/Cadastrar - cadastro de cliente
cliente/Listar - listar clientes cadastrados
- alteração e exclusão de clientes serão feitos pelo caminho de usuario

---- avaliação
cliente/Avaliacao/Listar - listar avaliações
cliente/Avaliacao/Inserir - fazer avaliação
cliente/Avaliacao/Responder - responder avaliação - sera utilizado pelo cliente e pela emprssa // por motivo de preguiça do desenvolvedor

----------------------------------------------------------------------------------------------------------------------------------------

Web Service em Java

It was necessary to create a Web Service to query and record data in an external database (SQL Server), and consume the data in an Android application.

Feito por um estudante para fins de entrega de trablho na faculdade

# Wig - Where I Go

Its purpose is the evaluation of establishments and problem solving between company and customer. Also enabling positive ratings
