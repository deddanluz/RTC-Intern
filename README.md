# RTC-Intern
Консольное приложение для получения данных об учениках и статистической информации, а также поиска по списку учеников.
## Ветки
- main-ветка. Здесь публикуется итоговый вариант приложения.
- restapi-ветка. Здесь публикуется приложение с классом-сервлетом.
- working-ветка. Здесь публикуются промежуточные коммиты, содержащие промежуточные результаты работы.

## Запуск приложения
Работа с приложением осуществляется в консольном режиме путем запуска приложения на JVM с указанием консольной команды и ряда аргументов. Подробнее о наборе команд и аргументов можно узнать по команде `help` и в разделе "Список команд".
При работе с сервлетом используется apache-tomcat-9.0.82.

## Методы сервлета
Спроектированы в https://app.swaggerhub.com/ в виде следующей структуры <https://app.swaggerhub.com/apis/DEDUCSHKINDANLUZ/RTCIntern-1/1.0.0>:

```
openapi: 3.0.0
components:
  schemas:
    Person:
      type: object
      properties:
        family:
          type: string
        name:
          type: string
        age:
          type: integer
          format: int64
        group:
          type: integer
          format: int64
        grades:
          type: object
        averageGrade:
          type: number
          format: double
    Request:
      type: object
      properties:
        family:
          type: string
        name:
          type: string
        subject:
          type: string
        group:
          type: integer
          format: int64
        grade:
          type: integer
          format: int64
    404:
      title: Student not found
      type: string
      example: Not found
info:
  title: "RTCIntern"
  version: 1.0.0
  description: Rest API Project for internship at RTC
  termsOfService: "http://localhost:8080/rtc-intern"
servers: 
  - url: http://localhost:8080/rtc-intern
    description: Production server
paths:
  /:
    get:
      tags:
      - Specified Student
      summary: "Calling a student by parameter"
      description: "Get access to all students and their groups, grades and curricula"
      operationId: "SpecifiedStudent"
      parameters:
      - name: group
        in: query
        description: "**Student group**. *Example: 11*. You can get Student by requesting a list by group."
        schema:
          type: integer
          format: int64
      responses:
        200:
          description: Successful response
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Person'
        404:
          description: Not found response
          content:
            text/plain:
              schema:
                $ref: '#/components/schemas/404'
    put:
      tags:
      - Specified Request
      summary: "Put a student by new grade"
      description: "Change the grade by full name, group and subject"
      operationId: "SpecifiedRequest"
      parameters:
      - name: family
        in: header
        description: "**Student family**. *Example: Попов*."
        schema:
          $ref: '#/components/schemas/Request'
      - name: name
        in: header
        description: "**Student name**. *Example: Илья*."
        schema:
          $ref: '#/components/schemas/Request'
      - name: subject
        in: header
        description: "**Student subjects**. *Example: rus*."
        schema:
          $ref: '#/components/schemas/Request'
      - name: group
        in: header
        description: "**Student group**. *Example: 10*."
        schema:
          $ref: '#/components/schemas/Request'
      - name: grade
        in: header
        description: "**A new student's assessment of the subject**. *Example: 2*."
        schema:
          $ref: '#/components/schemas/Request'
      responses:
        200:
          description: Successful response
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Person'
        404:
          description: Not found response
          content:
            text/plain:
              schema:
                $ref: '#/components/schemas/404'
```
## Список методов RestAPI
- GET `http:\\localhost:8080\rtc-intern?group=10` - вернет в формате JSON всех студентов группы 10 (например).
- PUT `http:\\localhost:8080\rtc-intern` - изменит оценку по выбранному предмету на указанную у данного ученика, заданного по ФИО и номеру группы. Headers (example):

```
{
    "family":"Попов",
    "name":"Илья",
    "group":10,
    "subject":"rus",
    "grade":5
}
```

## Список консольных команд
- `help` - получение списка команд и полной информации о том, как вводить данные для получения правильного результата.
- `calcAverageGrade` - вычисляет среднюю оценку в 10-11 классах на основе информации, полученной из файла формата csv.
- `calcHonorsPerson` - получает учеников старше 14 лет на основе информации, полученной из файла CSV.
- `searchByFamily` - поиск по фамилии по данным, полученным из CSV-файла.
#### Общий вид:
`java -jar rtc-intern-X.X-SNAPSHOT.jar calcAverageGrade -p students.csv`
#### Аргументы:
`-p path`
        путь к файлу с данными по ученикам.
        Например: `-p students.csv`

`-f family`
        фамилия для поиска при вызове команды searchByFamily.
        Например: `-f Попов`
        
Аргументы могут следовать в любом порядке, однако каждый аргумент должен **сначала** предваряться **указателем на аргумент**,
за которым должно **следовать его значение**. Перед аргументами должно быть указано **имя команды**.

## Версии
### 3.0
Реализовано:
- класс сервлета,
- структура методов,
- метод GET,
- метод PUT.
### 2.1
Исправлено:
- консольные команды теперь можно вводить в любом порядке,
- путь к файлу теперь указывается в аргументе '-p',
- на каждую отдельную команду создан отдельный класс имплементирующий Command,
- в StudentService реализована логика получения поставщика данных и поставщика команд,
- в CommandBuilder реализована логика получения команды на основе консольной команды,
- упрощен Main-Class,
- классы разложены по пакетам,
- улучшена обработка Runtime-исключений.

### 2.0
Реализовано:
- принципы OCP.

### 1.0
Реализовано:
- чтение CSV-файла,
- сортировка (с обоснованием методов по O-нотации),
- вывод требуемых значений в консоль.

