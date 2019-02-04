#!/bin/bash

loadJar(){
	jar=$(ls ../lib/bdsfc-mkt-cloud*.jar)
	echo ${jar}
}

JAR=$(loadJar)
CLASS="com.carrefour.bigdata.ssff.marketingcloud"
FILE="../conf/bard0001.cfg"
NAME="Contracts_Extractor_Confluent"

./spark-launcher.sh ${JAR} ${CLASS} ${FILE} ${NAME}