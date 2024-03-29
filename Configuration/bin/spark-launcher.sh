#!/bin/bash
# INPUT VARS
JAR=$1
CLASS=$2
FILE=$3
NAME=$4

if [[ $HOSTNAME = *"led"* ]];
then
 USER="k7029984";
 KEYTABUSER="k7029984";
fi;
if [[ $HOSTNAME = *"lec"* ]];
then
 USER="batch_comcua_ssffsc";
 KEYTABUSER="batch_comcua_ssffsc";
fi;
if [[ $HOSTNAME = *"lep"* ]];
then
 USER="batch_comprod_ssffsc";
 KEYTABUSER="batch_comprod_ssffsc";
fi;

echo ${HOSTNAME}
echo ${KEYTABUSER}

KEYTAB=${HOME}/${KEYTABUSER}.keytab
KEYTAB_COPY=${HOME}/${KEYTABUSER}_copy.keytab
PRINCIPAL=${USER}@ES.WCORP.CARREFOUR.COM
ASPECT_LIBRARY=$(find /proyectos/dg/aspectos/data-governance-agents*.jar)
WEAVER_RESOURCE=$(find /proyectos/dg/aspectos/aspectjweaver*.jar)
WEAVER_LIBRARY=$(ls /proyectos/dg/aspectos/aspectjweaver*.jar | xargs -l basename)

loadConf(){
   while read -r line
do
   echo "  --"$line
done < "$FILE"
}

loadJars(){
  echo "--jars "${ASPECT_LIBRARY}','${WEAVER_RESOURCE}','$(ls ../lib/*.jar | tr '\n' ',')
}

loadKerberosConf(){
if [ -n "${KEYTAB}" ] && [ -n "${PRINCIPAL}" ];
  then
    echo "  --principal ${PRINCIPAL}";
    echo "  --keytab ${KEYTAB}";
fi
}

spark2-submit \
  --master yarn \
  --deploy-mode cluster \
  --name ${NAME} \
  --files ${KEYTAB_COPY},../conf/log4j.xml,../conf/application.conf,../conf/kafka_jaas.conf \
  --conf spark.driver.extraJavaOptions="-Djava.security.auth.login.config=./kafka_jaas.conf -Dlog4j.configuration=./log4j.xml -javaagent:${WEAVER_LIBRARY}" \
  --conf spark.driver.extraClassPath="conf/:/opt/cloudera/parcels/CDH/lib/flume-ng/lib/flume-ng-log4jappender-1.6.0-cdh5.14.2.jar" \
  --conf spark.executor.extraClassPath="conf/:/opt/cloudera/parcels/CDH/lib/flume-ng/lib/flume-ng-log4jappender-1.6.0-cdh5.14.2.jar" \
  --conf spark.executor.extraJavaOptions="-Djava.security.auth.login.config=./kafka_jaas.conf -Dlog4j.configuration=log4j.xml -javaagent:${WEAVER_LIBRARY}" \
$(loadKerberosConf) \
$(loadConf) \
  --class ${CLASS} \
$(loadJars) \
  ${JAR}
