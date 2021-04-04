# E-commerce

[![Netlify Status](https://api.netlify.com/api/v1/badges/655c89ad-40d4-4860-baec-c7550babce32/deploy-status)](https://app.netlify.com/sites/molo-laben/deploys)

**Objetivos**: Desenvolver uma solução de *e-commerce* usando a arquitetura de microsserviços. Aplicar a integração entre eles orientada a eventos com **Apache Kafka** e garantir a compatibilidade da comunicação dos microsserviços com ***Schema Registry***. Programar em **Java** utilizando a *stack* do ***Spring*** (***Spring Boot***, ***Spring Cloud Streams***, **Spring Cloud Sleuth** e ***Spring Web***) . Desenvolver o ***front-end*** usando ***React***. Colocar em pratica o conceito de **integração continua**.

**Ferramentas Usadas**: **IntelliJ**, **Postman**, **Node**, **PgAdmin4** e **PostgreSQL**, **Visual Studio Code**, **Nodejs**, **React**, **Commerce.js** e **Stripe**.

Para desenvolver o *front-end* foi seguido os passos do video [***ECommerce Web Shop - Build & Deploy an Amazing App | React.js, Commerce.js, Stripe***](https://www.youtube.com/watch?v=377AQ0y6LPA&t=8483s). Um dos problemas que houve foi com o pagamento, porem isso foi concertado ao ativar a conta no **Stripe** (Do qual pede um cartão de credito valido). [Este site](https://molo-laben.netlify.app/), ou melhor o *front-end* é bem simples. O usuario pode adicionar produtos da tela principal ao carrinho, e clicando no icone do carrinho é possivel incrementar, decrementar (até remove-lo do carrinho), remover todos os produtos e realizar o *checkout*. Os produtos foram adicionados usando a **API** **Commerce.js** assim como essa **API**  popula as `ListItems` com os paises, estados e a opção de entrega.   



![](/screenshots/index.png)

![](/screenshots/index-cart.png)

![](/screenshots/cart.png)

![](/screenshots/Checkout-fields.png)

![](/screenshots/payment.png)

![](/screenshots/Checkout.png)

## Versões

***Front-End* 1.0**: O *front-end* faz uso da **API** **commerce.js** para processar os dados do cliente e para adicionar, remover e atualizar os produtos e opções de entrega. Assim como carregar os paises e estados desses onde o *e-commerce* fará a entrega. O *front-end* usa a **API** **stripe** para processar o pagamento e validar o cartão inserido pelo cliente.

**Checkout_API e Payment_API 1.0**: A **API** **Checkout_API** registra o cliente, porém retorna um codigo, similar ao que a **commerce.js** faz com o `checkoutToken_live`, do qual é usada no `Review.jsx` e `PaymentForm.jsx` para poder saber quais foram os produtos comprados e mais importante os dados do cliente. Enquanto que a **Payment_API** embora não processe e valide o método de pagamento, trabalha em sincronia com a **Checkout_API**; buscando o codigo e o *status* retornado pela **Checkout_API** e registrando o pagamento no banco de dados.

## Anotações

- Antes de desenvolver a solução, o passo inicial é coletar os requisitos;

- Definir o domínio da solução: nesta solução será usado dois dominios um chamado *checkout* e outro chamado *payment*.;

- ***Checkout***: guarda informações como dados do cliente, dados de pagamento, etc;

- ***Payment***: faz a cobrança usando os dados de pagamento usado;

- Dentro do dominio de *checkout* haverá duas aplicações: ***checkout-front*** e ***checkout-api***. E dentro do dominio *payment* haverá somente a aplicação chamada ***payment-api***. A ***checkout-api*** comunicará com a ***payment-api*** e vice-versa (Entre a ***checkout-api*** e  ***payment-api*** haverá o ***Kafka***). Enquanto que a *checkout-front* somente manterá comunicação unidirecional com a ***checkout-api***  (Relação ***view-controller***);

  > ***Steaming Data*** - Definição: "*Streaming*" é uma palavra do idioma inglês derivada de *stream* que pode significar corrente, córrego ou riacho, remetendo para o sentido de fluxo; de trasmissão. (...) É um fluxo contínuo de dados oriundo de diversas fontes.

- Em suma, teremos produtores e consumidores e o intermediario entre eles será o ***Message Broker***;

- **Apache Kafka**: é uma plataforma distribuida de mensagens e *streaming*. Uma instância do **Apache Kafka** é chamada de ***Broker***, varias instâncias de ***Brokers*** são chamadas de ***Cluster***.

- O tópico é dividido em três partições e visa ordenar a ordem das mensagens por chegada, do qual será alocada em um *slot* dentro de uma partição.

- Para administrar cada ***Broker*** é usado o **Apache *Zookeeper***. 

- **Apache Avro**: é um sistema de serialização de dados. Similar ao **xml** para definir atributos, só que **Apache Avro** usa o **json** para oferecer uma rica estrutura de dados; de dados binários, compactos e rápidos, sendo um *container* para persistir os dados. 

- ***Schema Registry***: é uma camada distribuída de armazenamento para ***Schemas Avro*** o qual usa o **Kafka** como mecanismo de armazenamento. O **produtor** irá enviar o *schema* para ***schema registry*** e ao mesmo tempo enviar a mensagem para o **kafka**, **consumidor** por sua vez, irá receber a mensagem do **kafka** e irá recurar o *schema* enviado pelo **produtor** ao ***schema registry***; conferindo se o *schema* está de acordo com a mensagem enviada pelo **produtor**.


---

- Parâmetros definidos para cadatrar o cliente: `firstName`, `lastName`, `username`, `email`, `address`, `complement`, `country`, `state`, `postalCode`, `saveAddress`, `saveInfo`, `paymentMethod`, `cardName`, `cardNumber`, `cardDate`, `cardCVV`. 

- Estrutura para organizar o codigo: `config`, `resource` ou `controller` e `streaming`.
- Dentro do pacote `resource` foi criado outro pacote chamado `checkout`, onde se encontrará a classe ***data transfer object***, porém pode nomeada com o prefixo `request`; dentro do pacote `checkout` se encontrará a classe `controller`, porém nomeada como o prefixo `resource`. 

- S.O.L.I.D.: Teve um curso sobre os principios, mas ressaltando: **S - Single Responsibility Principle** principio que diz que a classe deve ter responsabilidade única, **O - Open-Closed Principle** principio que diz que a classe deve estar aberta para ser extendida, mas fechada para ser modificada, **L - Liskov Substitution Principle** principio que diz a classe B pode extender a Interface A sem ter prejuizos no codigo, **I - Interface Segregation Principle** principio que diz ao invez de criar uma interface gigantesca onde cada implementação precisa implementar todos os métodos; a interface deve ser segregada para que cada classe quando implementar esta interface somente implementa o que for necessario para ela e por fim, **D - Dependency Inversion Principle** principio que diz que ao invez de usar a implementação no código usa uma interface intermediadora, pois quando é preciso mudar a implementação, somente a forma como a dependência é injetada será mudada; não sendo necessario mudar todo o código. Neste projeto será usado o **L** e o **D**. 
- A classe de repositorio é criada especificamente para acessar o banco de dados e "olhar" para a entidade.

> Docker is container based technology and containers are just user space  of the operating system. At the low level, a container is just a set of  processes that are isolated from the rest of the system, running from a  distinct image that provides all files necessary to support the  processes. It is built for running applications. In Docker, the  containers running share the host OS kernel. [**Docker vs. VM – where is the difference?**](https://devopscon.io/blog/docker/docker-vs-virtual-machine-where-are-the-differences/)

- `@Builder` do **lombok** constroi o objeto da classe sem precisar fazer do modo tradicional `Entidade entidade = new Entidade()`. 
- `@RequiredArgsConstructor` garante que em tempo de compilação seja feito o contrutor quando em alguma classe tiver: `private final Entidade entidade;` ao fazer a injeção de dependência.
- Não é uma boa pratica colocar a anotação `@Autowired` em atributos somente em construtores, porém atualmente o **Spring Boot** descarta a necessidade de fazer isso. 

## Erros

`Caused by: org.postgresql.util.PSQLException: ERROR: relation "checkout_entity_aud" does not exist`

- **Solução**: Remover as anotações `@Audited` e `@EntityListeners(AuditingEntityListener.class)`

`Unexpected character ('<' (code 60)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false')`

- Mudar no `application.yml`: `checkout-created-output` para `checkout-created`. Na classe `CheckoutCreatedSource` também.

- Não funcionou, voltei para a estaca zero. 

- Aaaa, a porta do **Kafka** estava errada no `application.yml`. Antes estava `http://localhost:8080`.

  ```
  kafka:
    properties:
      schema:
        registry:
          url: http://localhost:8081 
  ```

`Schema being registered is incompatible with an earlier schema for subject "streaming.ecommerce.checkout.created-value"; error code: 409`. Pois quando eu consegui arrumar o ultimo erro, eu deixei que o `schema` aceitasse valores nulos. 

- **Solução**: ` curl -X DELETE http://localhost:8081/subjects/streaming.ecommerce.checkout.created-value/versions/1`.  Depois disso limpar a ***build*** e gerar os **avros** novamente e por fim, **bootRun**.