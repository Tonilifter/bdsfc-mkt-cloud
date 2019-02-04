package com.carrefour.bigdata.ssff.marketingcloud

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.slf4j.LoggerFactory
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.functions._
import java.util.Properties

object App {
  def main(args: Array[String]): Unit = {

    val Logger = LoggerFactory.getLogger(getClass)

    val df=Context.spark.sql(Queries.getInfoContracts())

    val  props = new Properties()
    props.put("bootstrap.servers", Utils.bootstrapServers)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("security.protocol", "SASL_PLAINTEXT")
    props.put("sasl.kerberos.service.name", "kafka")
    props.put("linger.ms", 10.asInstanceOf[Integer])
    props.put("acks", "all")

    System.setProperty("java.security.krb5.conf", "/etc/krb5.conf")

    val kafkaUserDF: DataFrame = df.select(df.col("ido_contrato").alias("key"),to_json(struct(df.columns.map(column):_*)).alias("value"))


    kafkaUserDF.foreachPartition(row => {
      val producer = new KafkaProducer[String, String](props)
      row.foreach(x=> {
        producer.send(new ProducerRecord[String,String](Utils.topic,x.getString(0),x.getString(1)))
        producer.flush()
        Logger.info("RECORD ENVIADO")

      })
      producer.close()

    })

    /*val row = listDf(0)
    val key=row.getAs[String]("ido_contrato")
    val value=row.getAs[String]("ido_vendedor")*/


    //producer.close()

    /*val kafkaUserDF: DataFrame = df.select(df.col("ido_contrato").alias("key"),to_json(struct(df.columns.map(column):_*)).alias("value"))

    kafkaUserDF.selectExpr("CAST(key AS STRING)","CAST(value AS STRING)")
      .write
      .format("kafka")
      .option("kafka.bootstrap.servers", "led1mk01.es.wcorp.carrefour.com:9092,led1mk02.es.wcorp.carrefour.com:9092,led1mk03.es.wcorp.carrefour.com:9092")
      .option("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      .option("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      .option("security.protocol", "SASL_PLAINTEXT")
      .option("sasl.kerberos.service.name", "kafka")
      .option("topic","carlos_dg")
      .save()*/

    /*df.selectExpr("CAST(ido_contrato AS STRING) as key","CAST(ido_vendedor AS STRING) as value")
      .write
      .format("kafka")
      .option("kafka.bootstrap.servers", "led1mk01.es.wcorp.carrefour.com:9092,led1mk02.es.wcorp.carrefour.com:9092,led1mk03.es.wcorp.carrefour.com:9092")
      .option("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      .option("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
      .option("security.protocol", "SASL_PLAINTEXT")
      .option("sasl.kerberos.service.name", "kafka")
      .option("topic","topic_contracts")
      .save()*/

  }

}
