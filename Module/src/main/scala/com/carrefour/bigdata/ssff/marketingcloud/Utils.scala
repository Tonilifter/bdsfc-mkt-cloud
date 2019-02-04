package com.carrefour.bigdata.ssff.marketingcloud

object Utils {

  val bootstrapServers: String = PropertiesLoader.loadString("kafka.bootstrap_servers")
  val topic: String = PropertiesLoader.loadString("kafka.topic")
}
