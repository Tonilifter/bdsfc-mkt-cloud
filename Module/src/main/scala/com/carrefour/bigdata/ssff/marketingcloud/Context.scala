package com.carrefour.bigdata.ssff.marketingcloud

import org.apache.spark._
import org.apache.spark.sql.SparkSession
import org.slf4j.{Logger, LoggerFactory}

object Context {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  val conf: SparkConf = new SparkConf()
    .setAppName("DG_Extraction_Contract_SSFF")

  val spark: SparkSession = SparkSession
    .builder()
    .enableHiveSupport()
    .config(conf)
    .config("hive.exec.dynamic.partition.mode", "nonstrict")
    .config("spark.dynamicAllocation.enabled", "false" )
    .config("spark.sql.hive.convertMetastoreParquet", "false")
    .config("hive.exec.max.dynamic.partitions", "100000")
    .config("hive.exec.max.dynamic.partitions.pernode", "20000")
    .config("spark.sql.parquet.compression.codec", "snappy")
    .config("parquet.compression", "snappy")
    .getOrCreate()

  val sc: SparkContext = spark.sparkContext



}