import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

// Kafka parameters
val kafkaBootstrapServers = "kafka:9092"
val kafkaTopic = ".*"

val schema = StructType(Seq(
  StructField("id", StringType, nullable = false),
  StructField("userId", IntegerType, nullable = false),
  StructField("count", IntegerType, nullable = true),
  StructField("dateTime", StringType, nullable = true),
  StructField("description", StringType, nullable = true),
  StructField("payment", FloatType, nullable = true),
  StructField("type", StringType, nullable = true)
))

try {
  // Read data from Kafka
  val newDF: DataFrame = spark.readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", kafkaBootstrapServers)
    .option("subscribePattern", kafkaTopic)
    .load()

  // Extract and process the value field from Kafka messages
  val valueDF = newDF.selectExpr("CAST(value AS STRING) as message")

  // Further processing (optional)
  val processedDF = valueDF
    .select(from_json(col("message"), schema).alias("data"))
    .select(
      col("data.id").cast("string").alias("id"),
      col("data.userid").cast("int").alias("userid"),
      col("data.count").cast("int").alias("count"),
      col("data.datetime").alias("datetime"),
      col("data.description").alias("description"),
      col("data.payment").cast("float").alias("payment"),
      col("data.type").alias("type")
    )

  // Filter out rows with null 'id' or replace it with a UUID
  val filteredDF = processedDF.withColumn(
    "id",
    when($"id".isNull, expr("uuid()")).otherwise($"id")
  )

  // Write the processed data to Cassandra
  val query = filteredDF.writeStream
    .foreachBatch { (batchDF: DataFrame, batchId: Long) =>
      // Writing each batch to Cassandra
      batchDF.write
        .format("org.apache.spark.sql.cassandra")
        .options(Map("keyspace" -> "bigdatadbcassandra", "table" -> "user_data")) // Replace with your keyspace and table
        .mode("append")
        .save()
    }
    .outputMode("update") // Update mode ensures only new data is processed
    .start()

  // Keep the stream running
  query.awaitTermination()

} catch {
  case e: Exception =>
    println("Error occurred: " + e.getMessage)
    e.printStackTrace()
}

