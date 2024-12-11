# assingment 3

## cassandra

CREATE TABLE bigdatadbcassandra.user_data (
    id UUID PRIMARY KEY,
    userid int,
    count int,
    datetime text,
    description text,
    payment float,
    type text
);


## spark

sh /spark/data/spark-shell-with-packages.sh

:load /spark/data/kafkaToCassandra.scala

