# Счетчик символов

## Описание
Программа считывает обрабатываемый файл построчно в основном потоке, а считает количество символов в строках и суммы символов между строками в отдельных потоках, используя ForkJoinPool.

## Сборка приложения 
```shell script
# загружает gradle wrapper 6.6
./gradlew wrapper

# сборка проекта, прогон тестов
./gradlew clean build
```
