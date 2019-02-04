package com.carrefour.bigdata.ssff.marketingcloud

import com.typesafe.config.{Config, ConfigFactory}

object PropertiesLoader {

  val conf = ConfigFactory.load("application.conf")


  def loadConfMap(keys: Array[String]): Map[String, String] = {
    if (keys.isEmpty) {
      Map()
    } else {
      keys.map { key =>
        key -> conf.getString(key)
      }.toMap
    }
  }

  def loadString(key: String): String= {
    conf.getString(key)
  }

  def loadConf(key: String): Config = {
    conf.getConfig(key)
  }
}
