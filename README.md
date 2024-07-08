## Веб-приложение с использованием сервлетов и JDBC с базой данных PostgreSQL ##

Для запуска приложения необходимо в корней директории создать `docker-compose.yaml` с следующим содержимым:

```
services:
  db_auth: 
    container_name: CONTAINER_NAME
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_PASSWORD=YOUR_PASSWORD
      - POSTGRES_USER=YOUR_USER
    image: postgres:12.19-alpine3.20
```
Замените данные в примере на свои

Далее в папке `demo\src\main\resources` создате файл `application.properties` и запишите следующую информацию:

```
application.url=YOUR_POSTGRES_URL (или если вы запускаете локально, то jdbc:postgresql://localhost:5432)
application.dbname=YOUR_DB_NAME
application.username=YOUR_USERNAME
application.password=YOUR_PASSWORD
application.driver=org.postgresql.Driver
application.pool.size=10
```

Также замените данные на свои

Для запуска приложения в корневой папке необходимо запустить контейнер через команду `docker-compose up`
И далее остается подключить текущий проект к tomcat серверу и загрузить в него `Название_проекта.war`
