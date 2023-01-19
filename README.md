# Desafio Sicredi Backend
## Objetivo
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. Imagine que você deve criar uma solução para dispositivos móveis para gerenciar e participar dessas sessões de votação. 

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST:

    • Cadastrar uma nova pauta
    • Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)
    • Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta)
    • Contabilizar os votos e dar o resultado da votação na pauta

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A solução deve ser construída em java, usando Spring-boot, mas os frameworks e bibliotecas são de livre escolha (desde que não infrinja direitos de uso).

É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

O foco dessa avaliação é a comunicação entre o backend e o aplicativo mobile. Essa comunicação é feita através de mensagens no formato JSON, onde essas mensagens serão interpretadas pelo cliente para montar as telas onde o usuário vai interagir com o sistema. A aplicação cliente não faz parte da avaliação, apenas os componentes do servidor. O formato padrão dessas mensagens será detalhado no anexo 1.

## Tarefas bônus
#### Tarefa Bônus 1 - Integração com sistemas externos
    
        ◦ Integrar com um sistema que verifique, a partir do CPF do associado, se ele pode votar
            ▪ GET https://user-info.herokuapp.com/users/{cpf}
            ▪ Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos
            ▪ Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação. Essa operação retorna resultados aleatórios, portanto um mesmo CPF pode funcionar em um teste e não funcionar no outro.
#### Tarefa Bônus 2 - Performance
        ◦ Imagine que sua aplicação possa ser usada em cenários que existam centenas de milhares de votos. Ela deve se comportar de maneira performática nesses cenários
        ◦ Testes de performance são uma boa maneira de garantir e observar como sua aplicação se comporta
#### Tarefa Bônus 3 - Versionamento da API
        ◦ Como você versionaria a API da sua aplicação? Que estratégia usar?

## Ferramentas e tecnologias utilizadas

* Java 17
* Spring Boot 3.0.1
* Spring Data
* Spring Doc OpenApi
* Spring Hateoas
* JPA
* Junit 5
* MapStruct
* Lombok
* Postgresql 15
* Gradle
* Jacoco
* Docker
* Postman

## Instruções para executar a aplicação localmente
Ter instalado o Docker e Docker Compose. Executar os seguintes comandos via terminal na raiz do projeto:
>docker compose -f .\misc\docker\docker-compose.yml up --build

Utilizar o Postman para testar os endpoints da aplicação. Importar para ele a collection da pasta misc\postman que está na raiz do projeto

## Considerações sobre a implementação
Não foi possível implementar o código para integração do serviço https://user-info.herokuapp.com/users/{cpf} por ele não estar mais funcionando. Então resolvi implementar endpoints para genrenciar os Associados pela aplicação. 
