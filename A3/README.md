# Rapport de l'Activité Pratique N°2 - Architecture Micro-services avec Spring cloud

**Référence :** https://www.youtube.com/watch?v=8uY7JE_X_Fw \
**Réalisé par :** HAMZA ELCADI - GLSID3

---

## 1 - Création du projet

On Créer un projet java `kafka-spring-cloud-stream` :

![Création du projet](screenShots/1.png)

Avec les dépendances suivantes :
- **Spring Web**
- **Lombok**
- **Spring for Apache Kafka**
- **Spring for Apache Kafka Streams**
- **Cloud Stream** \

Puis on creer un fichier `compose.yml` dans le root directory. \
```yaml
version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.3.0
    container_name: glsid-zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  broker:
    image: confluentinc/cp-kafka:7.3.0
    container_name: glsid-kafka-broker
    ports:
      # To learn about configuring Kafka for access across networks see
      # https://www.confluent.io/blog/kafka-client-cannot-connect-to-broker-on-aws-on-docker-etc/
      - "9092:9092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://broker:29092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
```
puis on demarer docker
```shell
docker compose up
```
puis 
```shell
docker exec --interactive --tty glsid-kafka-broker kafka-console-consumer --bootstrap-server broker:9092 --topic T1
docker exec --interactive --tty glsid-kafka-broker kafka-console-producer --bootstrap-server broker:9092 --topic T1
```
on le teste :
![test de kafka producer and consumer](screenShots/2.png)

## 2 - Un Service Producer KAFKA via un Rest Controler
on creer un record `events.PageEvent`
```java
public record PageEvent(String name, String user, Date date, long duration ) {
}
```
et une class `controllers.PageEventController`
```java
@RestController
public class PageEventController {
    @Autowired
    private StreamBridge streamBridge;
    @Autowired
    private InteractiveQueryService interactiveQueryService;
    @GetMapping("/publish")
    public PageEvent publish(String name, String topic){
        PageEvent event= new PageEvent(
                name,
                Math.random()>0.5?"U1":"U2",
                new Date(),10+new Random().nextInt(10000));
        streamBridge.send(topic, event);
        return event;
    }
}
```
Puis on configurer le port dans `application.properties`
```properties
server.port=8080
```
et on le teste
![Test de producer kafka via rest controller](screenShots/3.png) \

## 3 - Un Service Consumer KAFKA
on creer notre propre kafka consumer baser sur spring. pour ca : \
on creer `handlers.PageEventHandler` un spring component qui contient un `bean` qui permet
de retourner un objet consumer.
```java
@Component
public class PageEventHandler {

    @Bean
    public Consumer<PageEvent> pageEventConsumer(){
        return (input) -> {
            System.out.println("*******************");
            System.out.println(input.toString());
            System.out.println("*****************");
        };
    }
}
```
`input` est de type PageEvent. c-a-d nous avons creer un consumer qui va etre creer au debut 
de l'execution de l'application, et il va faire un subscribe vers une `TOPIC` qui on va specifier
dans `application.properties`
```properties
spring.cloud.stream.bindings.pageEventConsumer-in-0.destination=T2
spring.cloud.function.definition=pageEventConsumer
```
on le teste
![Test de consumer kafka via spring](screenShots/4.png) \


## 4 - Un Service Supplier KAFKA
Pour creer un supplier il se fait de creer une fonction qui retourn un objet de type supplier
dans `PageEventHandler`
```java
@Bean
public Supplier<PageEvent> pageEventSupplier(){
    return ()->{
        return new PageEvent(
                Math.random()>0.5?"P1":"P2",
                Math.random()>0.5?"U1":"U2",
                new Date(),
                10+new Random().nextInt(10000)
        );
    };
}
```
puis on declare qu'il topic de supplier dans 'application.properties'
```properties
spring.cloud.stream.bindings.pageEventSupplier-out-0.destination=T3
spring.cloud.function.definition=pageEventConsumer;pageEventSupplier
```
on le teste \
![Test de suplier kafka via spring](screenShots/5.png) \
la dure par defaut entre les messages est `1 second` mais on peut changer par la propriete suivant :
```properties
spring.cloud.stream.bindings.pageEventSupplier-out-0.producer.poller.fixed-delay=200
```
cette fonctionaliter est bien pour similuer le stream flow.

## 5 - Un Service de Data Analytics Real Time Stream Processing avec Kaflka Streams
pour creer un service de data analytics real time stream processing, on ajoutons un fonction
`FUNCTION` qui a un input et un output dans `PageEventHandler`
```java
@Bean
public Function<KStream<String, PageEvent>, KStream<String, Long>> kStreamFunction() {
    return (input) ->
            input.filter((k,v)->v.duration()>100)
                    .map((k,v)->new KeyValue<>(v.name(), v.duration())); // maping the old key value to new key value where the key is name of the pageEvent and value is the duration of the pageEven
    // and for the filter only the events with duration > 100
}
```
puis on ajoutons la declaration dans `applicatio.properties`
```properties
spring.cloud.stream.bindings.kStreamFunction-in-0.destination=T3
spring.cloud.stream.bindings.kStreamFunction-out-0.destination=T4
```
on le test avec un supplier dans T3 et un consumer dans T4, avec le commande
```shell
#supllier
docker exec --interactive --tty glsid-kafka-broker kafka-console-consumer --bootstrap-server broker:9092 --topic T3
#consumer
docker exec --interactive --tty glsid-kafka-broker kafka-console-consumer --bootstrap-server broker:9092 --topic T4 --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
```
la raison on pas utiliser 
```shell
docker exec --interactive --tty glsid-kafka-broker kafka-console-producer --bootstrap-server broker:9092 --topic T4
```
car le flux Kafka produit des valeurs de type Long sérialisées en binaire, et qu’il faut les désérialiser correctement pour afficher des nombres lisibles, contrairement à la commande classique qui peut afficher des caractères illisibles. \
on le tester
![Test de stream kafka via spring](screenShots/6.png) \
On ajoutans autre atributes dans la fonction `kStreamFunction` dans `PageEventHandler`
```java
@Bean
public Function<KStream<String, PageEvent>, KStream<String, Long>> kStreamFunction() {
    return (input) ->
            input.filter((k,v)->v.duration()>100)
                    .map((k,v)->new KeyValue<>(v.name(), v.duration()))
                    .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                    .count()
                    .toStream();

    // for the filter only the events with duration > 100
    // maping the old key value to new key value where the key is name of the pageEvent and value is the duration of the pageEven
    // for groupBy : it groupBy name and count for each name
    // .toStream to return it to Kstream, because map let it Kstream but groupby make it Ktable, so you need to turn it to kstream

}
```
on le teste \
![Test de stream kafka via spring](screenShots/7.png) \
Remarque : on attende 30 second pour avoir le resultas mais on peut chnange by ajout dans `application.properties`
```properties
spring.cloud.stream.kafka.streams.binder.configuration.commit.interval.ms=5000
```
c-a-d fair des calcule en interfal de supliers de 5 seconds (calculer le count pour 5 second) \
on le teste \
![Test de stream kafka via spring](screenShots/8.png) \
on voir que le nombre augumente c'est pourquoi on ajoutons la proprieter `windowedBy` pour avoir le count de la dernier par exemple 5 seconde et pas accumuler. \
```java
 @Bean
public Function<KStream<String, PageEvent>, KStream<String, Long>> kStreamFunction() {
    return (input) ->
            input.filter((k,v)->v.duration()>100)
                    .map((k,v)->new KeyValue<>(v.name(), v.duration()))
                    .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                    .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(5)))
                    .count()
                    .toStream()
                    .map((k, v) -> new KeyValue<>(k.key(), v));

    // for the filter only the events with duration > 100
    // maping the old key value to new key value where the key is name of the pageEvent and value is the duration of the pageEven
    // for groupBy : it groupBy name and count for each name
    // .toStream to return it to Kstream, because map let it Kstream but groupby make it Ktable, so you need to turn it to kstream
    // .windowed by une propreiter de fenetrage pour reseter la fonction chaque 5 seconds
}
```
on le tester \
![Test de stream kafka via spring](screenShots/9.png) \





[//]: # (note : there is in kafka producer and consumer, in java cloud there is producer and consumer and supplier, supplier is just auto producer 
that send to the topic every 1 second by default, and in spring cloud there is stream [Function...] thats act like consumer and producer after processing the data that consume
in stream we use the long command)

## 6 - Une application Web qui permet d'afficher les résultats du Stream Data Analytics en temps réel


## 3 - Création de micro-service customer-service qui permet de gérer les client
On Créer le micro service `customer-service` : \
![Création du micro service customer-service](screenShots/2.png) \

Avec les dépendances suivantes :
- **Spring Web**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Rest Repository**
- **Eureka Discovery Client**
- **Config Client**
- **Spring Boot Actuator** //pour le monitoring du micro service \