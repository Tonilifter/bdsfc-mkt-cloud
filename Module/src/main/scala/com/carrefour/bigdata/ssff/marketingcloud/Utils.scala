package com.carrefour.bigdata.ssff.marketingcloud

object Utils {

  val bootstrapServers: String = PropertiesLoader.loadConf("kafka.bootstrap_servers").toString

  val kerberosUser: String = PropertiesLoader.loadConf("kerberos.user").toString
  val kerberosKeytab : String = PropertiesLoader.loadConf("kerberos.keytab").toString

  val topic: String = PropertiesLoader.loadConf("kafka.topic").toString
}
