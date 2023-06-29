# Cards API Service

Cards RESTful API service. 
It allows to create, read, update and delete cards.

## Compile and build artifact
    Maven : mvn clean package
    URL    : http://localhost:8080/cards

### Test locally
To test this locally follow these steps:

- Install docker
- Install docker-compose
- run.sh

### Authentication
In order to use property this API you need to login first to this endpoint:
- http://localhost:8080/auth/login
providing username (valid email address) and password, please use these ones to test:
```
     username         password
- admin@example.com    admin
- user2@example.com    user2
- user3@example.com    user3
- user4@example.com    user4 
```

### API Documentation
Please follow this link

- http://localhost:8080/swagger-ui/index.html#/


With these we are ready to go
Enjoy!