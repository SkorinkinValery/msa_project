# Журнал путешествий

Простой учебный CRUD на Spring Boot по лабораторным работам №1-6.

## Что есть в проекте

- сущность `Trip`;
- репозиторий Spring Data JPA;
- DTO для создания, ответа и частичного обновления;
- маппинг через MapStruct;
- сервисный слой;
- REST API;
- интеграционные тесты;
- юнит-тесты репозитория, контроллера и сервиса на JUnit 5 и Mockito;
- встроенная H2, поэтому отдельную базу данных для первой лабораторной поднимать не нужно.
- Dockerfile для сборки контейнера приложения;
- Docker Compose для совместного запуска приложения и MySQL.
- Flyway для версионированного создания и заполнения схемы MySQL.
- Swagger UI и OpenAPI-описание REST API.
- декларативный Feign-клиент для получения справочной информации о стране.

## Запуск

Нужна Java 17+. Maven отдельно устанавливать не требуется: в проект добавлен Maven Wrapper.

```powershell
.\mvnw.cmd spring-boot:run
```

Приложение запускается на `http://localhost:8080`.

## API

| Метод | URL | Действие |
|---|---|---|
| `GET` | `/api/trips` | получить все путешествия |
| `GET` | `/api/trips/{id}` | получить одно путешествие |
| `POST` | `/api/trips` | добавить путешествие |
| `PUT` | `/api/trips/{id}` | частично изменить путешествие |
| `DELETE` | `/api/trips/{id}` | удалить путешествие |
| `GET` | `/api/trips/{id}/guide` | получить путешествие и справку о стране через Feign |
| `GET` | `/internal/destination-guides/{country}` | учебный REST-сервис справочника стран |

Пример создания:

```json
{
  "title": "Летний отпуск",
  "country": "Италия",
  "city": "Рим",
  "description": "Колизей и прогулка по центру",
  "travelDate": "2026-07-15"
}
```

При обновлении можно передать только изменяемые поля:

```json
{
  "city": "Милан"
}
```

## Проверка

```powershell
.\mvnw.cmd test
```

## Запуск в Docker с MySQL

Для лабораторной работы №3 приложение и MySQL запускаются одной командой:

```powershell
docker compose up --build
```

После появления сообщения `Started TravelJournalApplication` API доступно по адресу
`http://localhost:8080/api/trips`, а MySQL — на порту `3306`.

Остановить контейнеры:

```powershell
docker compose down
```

Остановить контейнеры и удалить учебную базу данных:

```powershell
docker compose down -v
```

## Миграции Flyway

В Docker-профиле Hibernate только проверяет схему (`ddl-auto: validate`), а изменениями
базы данных управляет Flyway. При первом запуске последовательно выполняются:

1. `V1__create_trips_table.sql` — создание таблицы `trips`;
2. `V2__insert_initial_trips.sql` — добавление двух учебных путешествий.

Проверить историю миграций и данные можно командами:

```powershell
docker compose exec mysql mysql -utravel_user -ptravel_password travel_journal -e "SELECT version, description, success FROM flyway_schema_history;"
docker compose exec mysql mysql -utravel_user -ptravel_password travel_journal -e "SELECT * FROM trips;"
```

## Документация API

После запуска приложения доступны:

- Swagger UI: `http://localhost:8080/swagger-ui.html`;
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`;
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`.

В Swagger UI можно раскрыть раздел «Путешествия» и выполнить все пять CRUD-операций.

## Feign-клиент

Маршрут `GET /api/trips/{id}/guide` сначала получает путешествие из базы данных,
после чего `DestinationGuideClient` выполняет HTTP-запрос к учебному сервису
`/internal/destination-guides/{country}`. URL сервиса можно изменить переменной
окружения `DESTINATION_GUIDE_URL`.

Пример проверки:

```powershell
curl http://localhost:8080/api/trips/1/guide
```

В логах приложения выводится однострочная информация о Feign-запросе и ответе.
