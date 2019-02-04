#!/bin/bash

loadJar(){
	jar=$(ls ../lib/*poc-contractsToKafka*.jar)
	echo ${jar}
}

JAR=$(loadJar)
CLASS="com.carrefour.bigdata.App"
FILE="../conf/bard0001.cfg"
NAME="Contracts_Extractor_Confluent"

./spark-launcher.sh ${JAR} ${CLASS} ${FILE} ${NAME}