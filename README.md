# simple data api to store and retrieve the discogs value from postgres database

Your discogs collection has three values e.g.

    minimum: £10,000
    
    median: £11,000
    
    maximum: £12,000
    
The api will store interact initially with postgres to store and retrieve this data


[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mcmikeyy_spring-vanilla-discogs-data-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=mcmikeyy_spring-vanilla-discogs-data-api)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mcmikeyy_spring-vanilla-discogs-data-api&metric=coverage)](https://sonarcloud.io/dashboard?id=mcmikeyy_spring-vanilla-discogs-data-api)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=mcmikeyy_spring-vanilla-discogs-data-api&metric=sqale_index)](https://sonarcloud.io/dashboard?id=mcmikeyy_spring-vanilla-discogs-data-api)

## DOCKER

### get ip address of running docker-compose

docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_id

## GITHUB PACKAGE REPO


