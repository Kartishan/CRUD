# Проект CRUD API

## Описание

Это тестовое задание для MEDIASOFT представляет собой CRUD API для управления товарами.

## Функциональности

### Что сделано:
- Основное задание
- Дополнительные задания, за исключением пункта 7.

## Запуск проекта
Для запуска проекта надо ввести команду в консоль:
```bash
docker-compose up -d
```

## SWAGGER
Swagger находится в папке main\\resources\\openapi.yaml </br>
[Открыть swagger](src\main\resources\openapi.yaml)

## Коллекция запросов PostMan
Заросы находятся в файле: CRUD.postman_collection.json </br>
[Открыть коллекцию запросов](CRUD.postman_collection.json)

## UNIT TESTS
Юнит тесты находятся в src\test\java\com\kartishan\crud\
Для их запуска надо ввести mvn test в корневой директории проекта.

### Запросы:

#### Запрос на **получение** товара по его id
```yaml
    http://localhost:8080/api/product/{id}
```

#### Запрос на **получение всех** товаров по его id
```yaml
    http://localhost:8080/api/product/all 
```

#### Запрос на **удаление** товара по его id
```yaml 
    http://localhost:8080/api/product/delete/{id} 
```
#### Запрос на **создание** товара по его id
```yaml
http://localhost:8080/api/product/update/{id}
```
Пример тела запроса:
```json
{
  "name": "testName",
  "articular": "testArticular",
  "description": "testDescription",
  "category": "testCategory",
  "cost": 10,
  "amount": 10
}
```

#### Запрос на **обновление** товара по его id
```yaml
http://localhost:8080/api/product/update/{id}
```
Пример тела запроса:
```json
{
"name": "newTestName",
"articular": "newTestArticular",
"description": "newTestDescription",
"category": "newTestCategory",
"cost": 5,
"amount": 55
}
```


