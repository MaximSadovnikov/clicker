# Clicker
Это приложение использует SpringBoot, maven, Hibernate, PostgrSQL, javasript.

## Запуск приложения

1. поднять образ базы 
```
docker-compose up -d
```
2. Запустить SpringBoot приложение 

```
cd path/to/project
mvn spring-boot:run
```
3. Открыть 
```
http://localhost:8080
```
4. Кликать на здоровьe.

## Выключение приложения

1. нажать ctrl+c что в консло что бы остановить приложение

2 опустить докер с образом с помощью команды:
```
docker-compose down
```

Так же можно просто запустить 
```
./start_application.sh 
```
оно сразу развернет базу и запустит приложение

После, нужно нажать ctrl+c что бы завершить работу приложения и выполнить 

```
./finish_application.sh
```

это погасит докер с образом базы.
