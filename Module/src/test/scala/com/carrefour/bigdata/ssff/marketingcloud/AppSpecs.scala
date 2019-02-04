package com.carrefour.bigdata.ssff.marketingcloud

import org.scalatest.FlatSpec

class AppSpecs extends FlatSpec {


  "PropertiesLoader.loadConfMap" should "return an empty Map if no keys are provided" in {
    assert(PropertiesLoader.loadConfMap(Array()).isEmpty)
  }


  it should "throw a com.typesafe.config.ConfigException if the keys contain an invalid key" in {
    intercept[com.typesafe.config.ConfigException] {
      PropertiesLoader.loadConfMap(Array("invalidKey"))
    }
  }

  "PropertiesLoader.loadConf" should "throw a com.typesafe.config.ConfigException if the key provided is invalid" in {

    assertThrows[com.typesafe.config.ConfigException] {
      PropertiesLoader.loadConf("invalidKey")
    }

    //    intercept[com.typesafe.config.ConfigException] {
    //      PropertiesLoader.loadConf("invalidKey")
    //    }
  }


}
