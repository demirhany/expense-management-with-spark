### .env

create a .env file in the root directory of the project and add the following variables.
<br>
Do not modify the `application.properties` file instead of adding `.env` file.

``` dotenv
DB_URL=jdbc:postgresql://localhost:5432/BigDataDb
DB_PASSWORD=1234
DB_USERNAME=postgres

HADOOP_URL=hdfs://localhost:9000
HADOOP_DATANODE_HOSTNAME=localhost

```