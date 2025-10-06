## How to run

Open environment -> Run
> docker-compose -f .csit318_environment/docker-compose-dev.yml up

The above cmd will auto create db with these metrics: \
```bash
MYSQL_DATABSE: auth_db
MYSQL_ROOT: root
MYSQL_ROOT_PASSWORD: root1234
MYSQL_USERNAME: auth
MYSQL_PASSWORD: auth123 
```
```bash
MYSQL_DATABSE: shop_db
MYSQL_ROOT: root
MYSQL_ROOT_PASSWORD: root1234
```
```bash
MYSQL_DATABSE: product_db
MYSQL_ROOT: root
MYSQL_ROOT_PASSWORD: root1234
```
```bash
MYSQL_DATABSE: order_db
MYSQL_ROOT: root
MYSQL_ROOT_PASSWORD: root1234
```

Run Kafka:
```bash
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties
```
```bash
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties
```
