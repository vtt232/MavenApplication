package org.example;

import jdk.internal.net.http.frame.DataFrame;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.*;
import com.mysql.jdbc.Driver;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.streaming.Trigger;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import static org.apache.spark.sql.functions.*;

public class Main {


    public static String serverName = "jdbc:mysql://localhost:3306";
    public static String databaseName = "sparkdb";

    public static String jdbcurl = serverName + "/" + databaseName + "?createDatabaseIfNotExist=true";

    public static String tableName = "streamTable";

    public static String username = "root";

    public static String password = "2322000@Vtt";

    public static class CustomLit implements UDF1<String, String> {
        @Override
        public String call(String value) throws Exception {

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            return "2024-04-11 09:25:20"; // Add custom logic here
        }
    }

    public static void main(String[] args) throws TimeoutException, StreamingQueryException, ClassNotFoundException {

        SparkSession spark = SparkSession.builder()
                .master("local[*]")
                .appName("hello-spark")
                .config("spark.sql.session.timeZone", "Asia/Bangkok")
                .getOrCreate();



       /*String user = System.getenv().getOrDefault("KAFKA_USER", "user1");
       String pass = System.getenv().getOrDefault("KAFKA_PASS", "WckiLlCI3m");


        Map<String, String> kafkaOpts = new HashMap<>();

        kafkaOpts.put("kafka.bootstrap.servers", "kafka:9092");
        kafkaOpts.put("subscribe", "test");
        kafkaOpts.put("kafka.sasl.mechanism", "PLAIN");
        kafkaOpts.put("kafka.security.protocol", "SASL_PLAINTEXT");
        kafkaOpts.put("kafka.sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                "username=\"" + user + "\" " +
                "password=\"" + pass + "\";");

        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .options(kafkaOpts)
                .load();*/
     /*   Dataset<Row> ds = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");

        ds.writeStream()
                .format("console")
                .outputMode("append")
                .start()
                .awaitTermination();*/



        //-----------------------------------------------------------//

       /* Map<String, String> kafkaOpts = new HashMap<>();

        kafkaOpts.put("kafka.bootstrap.servers", "localhost:9092");
        kafkaOpts.put("subscribe", "admin");


        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .options(kafkaOpts)
                .load();

        StructType schema = DataTypes.createStructType(new StructField[] {
                DataTypes.createStructField("key", DataTypes.StringType, false),
                DataTypes.createStructField("messages", DataTypes.StringType, false)});

        Dataset<SparkTestDTO> dfClassLoaded = df.selectExpr("CAST(value AS STRING) as message")
                                            .select(functions.from_json(functions.col("message"), schema).as("json"))
                                            .select("json.*")
                                            .as(Encoders.bean(SparkTestDTO.class));

        Dataset<String> result = dfClassLoaded.filter((FilterFunction<SparkTestDTO>) value -> value.getKey().contains("ha"))
                .map((MapFunction<SparkTestDTO, String>) value -> value.getMessages().toUpperCase(), Encoders.STRING());


        result.writeStream()
                .format("console")
                .outputMode("append")
                .start()
                .awaitTermination();*/

        //-----------------------------------------------------------//

        Map<String, String> kafkaAdminOpts = new HashMap<>();

        kafkaAdminOpts.put("kafka.bootstrap.servers", "localhost:9092");
        kafkaAdminOpts.put("subscribe", "admin");

        Dataset<Row> dfAdmin = spark
                .readStream()
                .format("kafka")
                .options(kafkaAdminOpts)
                .load();

        Map<String, String> kafkaEventObserverOpts = new HashMap<>();

        kafkaEventObserverOpts.put("kafka.bootstrap.servers", "localhost:9092");
        kafkaEventObserverOpts.put("subscribe", "event-observer");

        Dataset<Row> dfEventObserver = spark
                .readStream()
                .format("kafka")
                .options(kafkaEventObserverOpts)
                .load();

        StructType schemaAdmin = DataTypes.createStructType(new StructField[] {
                DataTypes.createStructField("timeStampAdmin", DataTypes.TimestampType, false),
                DataTypes.createStructField("keyAdmin", DataTypes.StringType, false),
                DataTypes.createStructField("messagesAdmin", DataTypes.StringType, false)});

        StructType schemaEventObserver = DataTypes.createStructType(new StructField[] {
                DataTypes.createStructField("timeStampEventObserver", DataTypes.TimestampType, false),
                DataTypes.createStructField("keyEventObserver", DataTypes.StringType, false),
                DataTypes.createStructField("messagesEventObserver", DataTypes.StringType, false)});

        Dataset<SparkTestAdminDTO> dfAdminClassLoaded = dfAdmin.selectExpr("CAST(value AS STRING) as message")
                .select(functions.from_json(functions.col("message"), schemaAdmin).as("json"))
                .select("json.*")
                .as(Encoders.bean(SparkTestAdminDTO.class));

        Dataset<SparkTestEventObserverDTO> dfEventObserverClassLoaded = dfEventObserver.selectExpr("CAST(value AS STRING) as message")
                .select(functions.from_json(functions.col("message"), schemaEventObserver).as("json"))
                .select("json.*")
                .as(Encoders.bean(SparkTestEventObserverDTO.class));

        Dataset<SparkTestAdminDTO> dfAdminClassLoadedWithWatermark = dfAdminClassLoaded.withWatermark("timeStampAdmin", "1 minute");
        Dataset<SparkTestEventObserverDTO> dfEventObserverClassLoadedWithWatermark = dfEventObserverClassLoaded.withWatermark("timeStampEventObserver", "1 minute");

        Dataset<Row> joinedResult = dfAdminClassLoadedWithWatermark.join(dfEventObserverClassLoadedWithWatermark, expr("keyAdmin = keyEventObserver AND " +
                "timeStampAdmin >= timeStampEventObserver AND " +
                "timeStampAdmin <= timeStampEventObserver + interval 1 minutes"), "inner");

        Dataset<ResultAggregation> result = joinedResult.groupBy(col("messagesAdmin"), window(col("timeStampAdmin"), "1 minute")).count()
                .select("messagesAdmin", "count")
                .as(Encoders.bean(ResultAggregation.class));
                /*.select("messagesAdmin", "count");*/

        /*result.writeStream()
                .format("csv")
                .option("checkpointLocation", "C:\\Users\\vtthinh\\Desktop\\SparkResult1\\checkpoint")
                .option("path", "C:\\Users\\vtthinh\\Desktop\\SparkResult1")
                .outputMode("append")
                .start()
                .awaitTermination();*/
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);

        VoidFunction2<Dataset<ResultAggregation>, Long> eachBatchFunction = new VoidFunction2<Dataset<ResultAggregation>, Long>() {
            @Override
            public void call(Dataset<ResultAggregation> resultDataset, Long aLong) throws Exception {
                Properties properties = new Properties();
                properties.put("user", username);
                properties.put("password", password);
                properties.put("driver", "com.mysql.cj.jdbc.Driver");

                resultDataset.write().mode("append").jdbc(jdbcurl, tableName, properties);
            }
        };



        result.writeStream().outputMode("append").foreachBatch(eachBatchFunction).start().awaitTermination();


        //joinedResult.show();

    }
}