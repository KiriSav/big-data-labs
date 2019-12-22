# Lab_3 - Проектирование и реализация клиент-серверного приложения, взаимодействующего по HTTPS протоколу с использованием ключей шифрования для SSL/TLS (Two-way TLS)
## Описание программы
### Дирректория /client/
Название | Описание 
---------------|------------------------------------
ClientController.java | Класс контроллера для клиента.
DemoApplication.java  | Главный класс запускающий программу - сервер.
application.yml  | Файл хранящий настройки для Spring (клиент).
geteway.jks  | Файл jks содержащий сертификат клиента.

### Дирректория /server/ 
Название | Описание 
---------------|------------------------------------
ServerController.java | Класс контроллера для сервера.
DemoApplication.java  | Главный класс запускающий программу - клиент.
application.yml  | Файл хранящий настройки для Spring (сервер).
server.jks  | Файл jks содержащий сертификат сервера..

## **Инструкции для запуска**
1. Запустить `DemoApplication.java` находящийся в `\lab_3\server\src\main\java\com\example\demo\`.
2. Запустить `DemoApplication.java` находящийся в `\lab_3\client\src\main\java\com\example\demo\`.
3. Перейти в браузер и открыть url - `https://localhost:8080/gateway/server-data `.
