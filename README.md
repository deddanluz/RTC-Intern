# RTC-Intern
Консольное приложение для получения данных об учениках и статистической информации, а также поиска по списку учеников.
## Ветки
- main-ветка. Здесь публикуется итоговый вариант приложения.
- postgresql-ветка. Здесь публикуется вариант приложения с методами для работы с PostgeSQL.
- working-ветка. Здесь публикуются промежуточные коммиты, содержащие промежуточные результаты работы.

## Запуск приложения
Приложение разработано на версии JDK **20**. Работа с приложением осуществляется в консольном режиме путем запуска приложения на JVM с указанием консольной команды и ряда аргументов. Подробнее о наборе команд и аргументов можно узнать по команде `help` и в разделе "Список команд".

## Список команд
- `help` - получение списка команд и полной информации о том, как вводить данные для получения правильного результата.
- `calcAverageGrade` - вычисляет среднюю оценку в 10-11 классах на основе информации, полученной из файла формата csv.
- `calcHonorsPerson` - получает учеников старше 14 лет на основе информации, полученной из файла CSV.
- `searchByFamily` - поиск по фамилии по данным, полученным из CSV-файла.
- `uploadByPgSQL` - загрузка из CSV в PostgreSQL.
- `searchByFamilyFromPgSQL` - поиск по фамилии в базе данных.
- `calcAverageGradeFromPgSQL` - получить среднюю оценку в 10-11 классах.
- `calcHonorsPersonFromPgSQL` - получить отличников старше 14 лет.
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
- Структура таблиц БД,
- Инициализирующий скрипт SQL,
- Transaction Script для работы с PostgreSQl,
- Data transfer object,
- Команды для поиска:
  * по фамилии,
  * всех отличников старше 14 лет,
  * средней оценки в 10-11 классах.
- Команда для загрузки в БД. 

### 2.0
Реализовано:
- принципы OCP.

### 1.0
Реализовано:
- чтение CSV-файла,
- сортировка (с обоснованием методов по O-нотации),
- вывод требуемых значений в консоль.

