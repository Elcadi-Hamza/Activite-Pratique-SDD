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
```
on le teste :
![test de kafka producer and consumer](screenShots/2.png)

## 2 - Un Service Producer KAFKA via un Rest Controler
on creer la class `controllers.PageEventController`
```java

```
et `events.PageEvent`

## 3 - Un Service Consumer KAFKA
## 4 - Un Service Supplier KAFKA
## 5 - Un Service de Data Analytics Real Time Stream Processing avec Kaflka Streams
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