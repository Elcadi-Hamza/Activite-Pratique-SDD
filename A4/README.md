# Rapport de l'Activité Pratique N°4 - CQRS et Event Sourcing avec AXON et Spring Cloud

**Référence :** https://www.youtube.com/watch?v=ZxE9mbvu0og \
**Réalisé par :** HAMZA ELCADI - GLSID3

---

## 1 - Création du projet

On crée un projet Java `account-service-axon` :

![Création du projet](screenShots/0.png)

Avec les dépendances suivantes :
- **Spring Web**
- **Spring Data JPA**
- **Lombok**
- **H2 Database**
- **PostgresSQL Driver**\
En modifier la version de `lombok` on ajoutons la version
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.34</version>
    <optional>true</optional>
</dependency>
 <annotationProcessorPaths>
    <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.34</version>
    </path>
</annotationProcessorPaths>
```
on ajoutons la dependances `AXON` framework :
```xml
<dependency>
    <groupId>org.axonframework</groupId>
    <artifactId>axon-spring-boot-starter</artifactId>
    <version>4.6.1</version>
    <exclusions>
        <exclusion>
            <groupId>org.axonframework</groupId>
            <artifactId>axon-server-connector</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
exclusion donne un serveur au memoire. \
On ajoutons la dependance de documentation
```xml
<dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>3.2.0</version>
</dependency>
```
## 2 - Développements du Côté Commande
On creer le package `commands` (ecrire) et un sous package `controllers`. \
la class `AccountCommandController`
```java

```

## 3 - Implémentation des Événements

## 4 - Persistance avec PostgreSQL et Docker Compose

## 5 - Développements du Côté Lecture
On creer le package `query` (ecriture)

## 6 - Streaming des événements en temps réel (SSE)

## 7 - Intégration d'Axon Server
