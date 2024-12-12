# Big Data Homework 3
# Expense-Management-With-Spark

Sample web application that is an application of spring framework, hadoop, spark, kafka, cassandra db.

./big-data-aws ./big_data_hw_spring are not used in the last version.

## Setup

### Run Docker Compose

```bash
cd docker-hadoop
docker compose up
```

### Setup Cassandra

#### Access Cassandra Command Prompt

```bash
docker exec -it cassandra-container cqlsh
```

##### DB Creation Query

```
CREATE KEYSPACE IF NOT EXISTS bigdatadbcassandra WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };
```

##### Use Keyspace

```
USE bigdatadbcassandra ;
```

##### Table Creation Query

```
CREATE TABLE bigdatadbcassandra.user_data (
    id UUID PRIMARY KEY,
    userid int,
    count int,
    datetime text,
    description text,
    payment float,
    type text
);
```

### Setup Spark

#### Run spark-shell

Access spark master container command prompt.

```bash
docker exec -it spark-master /bin/bash
```

Run following command in spark contatiner.

```bash
sh /spark/data/spark-shell-with-packages.sh
```

#### Load Scala Script

```
:load /spark/data/kafkaToCassandra.scala
```

### Run Total Expense Calculator Spring App

```bash
cd total-expense-api
JAVA_HOME=/usr/lib/jvm/jre-11-openjdk ./mvnw spring-boot:run
```

### Run Dummy Expense Generator Spring App

```bash
cd expense-generator
./mvnw spring-boot:run
```

### Run The Main Spring App

```bash
cd big-data-hadoop-spring
./mvnw spring-boot:run
```
