# simple data api to store and retrieve the discogs value

YOur discogs collection has three values

    minimum: £2,822.02
    
    median: £5,349.86
    
    maximum: £10,438.66
    
The api will store interact initially with postgres to store and retrieve this data

## Docker

to run the app manually

docker build -t mconnors/discogs-spring-vanilla-data-api .

## Build a Docker Image with Maven

```dockerfile
$ ./mvnw com.google.cloud.tools:jib-maven-plugin:dockerBuild -Dimage=mconnors/discogs-spring-vanilla-data-api
```


./mvnw com.google.cloud.tools:jib-maven-plugin:build -Dimage=mconnors/discogs-spring-vanilla-data-api

## running wth profiles

docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 -t mconnors/discogs-spring-vanilla-data-api# spring-vanilla-discogs-data-api
