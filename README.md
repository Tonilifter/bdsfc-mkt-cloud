# Carrefour Big Data

Tras generar el proyecto a partir del arquetipo, serán necesarias unas modificaciones para adaptarlo a las necesidades especificas de cada caso:
- En caso de mantener las clases scala proporcionadas, se deberá incluir su package de manera manual.
- Modificación del Jenkinsfile:
  - config{
    + Modificación del repoUrl para que apunte al repositorio en BitBucket asociado al proyecto.
    + Modificación del deployableArtifactId.
  }
- Modificación del número de módulos del proyecto. El arquetipo proporciona dos ejemplos de módulos de código similares. En caso de necesitar más o menos módulos se deberá reajustar su número manualmente. Una modificación en el número de módulos o en el nombre de los mismos impacta en los siguientes puntos:
  - Assembly > src > main > assembly > assembly{1,2..}.xml:
     + Apartado <fileSets>: En dicho apartado se especifican los directorios origen del módulo assembly.
       Se incluye una configuración para generar distintos desplegables (tar.gz); más concretamente uno por módulo.
       Si se desea generar un único desplegable que incluya todos los módulos, se deberá dejar un único fichero assembly.xml, con un <fileSet> por cada uno de los módulos.
  - Nombre de los directorios donde se encuentran los submódulos, en caso de que se desee.
  - pom.xml (padre):
    + project > build > plugins > plugin (build-helper-maven-plugin) > executions > execution (generate-sources) > configuration > sources:
      Se deberá incluir un tag <source> por cada módulo de código en el que se especificará el directorio raiz donde se encuentran los ficheros fuente
    + Ajuste del apartado <modules> del pom padre del proyecto
- Modificación del README.md
- Añadir el fichero .gitignore
- En caso de tratarse de un proyecto que genera librería (Es decir, no es un proyecto desplegable) se deberá excluir el módulo assembly