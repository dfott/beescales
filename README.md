# Beescales

This repository contains the source code for the backend and frontend of Beescales.


## Backend
When pulling the backend, multiple changes have to be made before starting the application.

### application.properties

Change the following properties:
```
spring.datasource.url=jdbc:mariadb://host:port/bee_scales
spring.datasource.username=username
spring.datasource.password=password
```

### BeescalesApplication.java

Change the default username and password if needed.

### EmailService.java

Change the receiver mail if needed

```
helper.setTo()
```

## Frontend

### environment.ts and environment.prod.ts

Change apiUrl if needed
