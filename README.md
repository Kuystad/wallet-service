Wallet Service

REST API сервис для управления балансом кошелька.  
Проект реализован на Spring Boot с использованием PostgreSQL, Liquibase и Docker.  
Основная цель — корректная обработка транзакций и работа в конкурентной среде.

Технологический стек

Java 17
Spring Boot
Spring Web
Spring Data JPA
PostgreSQL
Liquibase
Docker / Docker Compose
JUnit

Архитектура проекта

controller → service → repository → database

Структура пакетов:

controller  — REST endpoints
service     — бизнес логика
repository  — доступ к базе данных
model       — JPA сущности и enum
dto         — входящие запросы API
exception   — кастомные ошибки и глобальный обработчик

API

POST /api/v1/wallet

{
  "walletId": "UUID",
  "operationType": "DEPOSIT | WITHDRAW",
  "amount": 1000
}

GET /api/v1/wallets/{id}

Liquibase

src/main/resources/db/changelog
db.changelog-master.yaml

Docker

docker-compose up --build

Локальный запуск

docker run -p 5432:5432 -e POSTGRES_DB=wallet -e POSTGRES_USER=wallet -e POSTGRES_PASSWORD=wallet postgres:15
./mvnw spring-boot:run

Тесты

./mvnw test

