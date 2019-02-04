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
 USER="batch_comcua_dg";
 KEYTABUSER="batch_comcua_dg";
fi;
if [[ $HOSTNAME = *"lep"* ]];
then
 USER="batch_comprod_dg";
 KEYTABUSER="batch_comprod_dg";
fi;

echo ${HOSTNAME}
echo ${KEYTABUSER}

KEYTAB=${HOME}/${KEYTABUSER}.keytab
KEYTAB_COPY=${HOME}/${KEYTABUSER}_copy.keytab
PRINCIPAL=${USER}@ES.WCORP.CARREFOUR.COM
ASPECT_LIBRARY=$(find /proyectos/dg/aspectos/data-governance-agents*.jar)

loadConf(){
   while read -r line
do
   echo "  --"$line
done < "$FILE"
}

loadJars(){
  echo "--jars "${ASPECT_LIBRARY}','$(ls ../lib/*.jar | tr '\n' ',')
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
  --files ${KEYTAB_COPY},../conf/log4j.xml,../conf/krb5.conf#krb5.conf,../conf/application.conf,../conf/kafka_jaas.conf \
  --conf spark.driver.extraJavaOptions="-Djava.security.auth.login.config=./kafka_jaas.conf -Dlog4j.configuration=./log4j.xml -javaagent:aspectjweaver-1.8.13.jar" \
  --conf spark.driver.extraClassPath="conf/:/opt/cloudera/parcels/CDH/lib/flume-ng/lib/flume-ng-log4jappender-1.6.0-cdh5.14.2.jar" \
  --conf spark.executor.extraClassPath="conf/:/opt/cloudera/parcels/CDH/lib/flume-ng/lib/flume-ng-log4jappender-1.6.0-cdh5.14.2.jar" \
  --conf spark.executor.extraJavaOptions="-Djava.security.auth.login.config=./kafka_jaas.conf -Dlog4j.configuration=log4j.xml -javaagent:aspectjweaver-1.8.13.jar" \
$(loadKerberosConf) \
$(loadConf) \
  --class ${CLASS} \
$(loadJars) \
  ${JAR}
