FROM openjdk:11-jre 

WORKDIR appDir

ARG JAR_FILE=build/libs/SpringOCPP16J-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} appDir/application.jar
#COPY application.properties application.properties

ENV JVM_OPTS="-Xmx1024m -Xms512m"
EXPOSE 9090

CMD ["bash", "-c", "java -jar $JAVA_OPTS appDir/application.jar"]
