FROM bellsoft/liberica-openjdk-alpine:17
LABEL authors="juliozittei"

EXPOSE 8080

# Adicionar wait-for-it.sh
COPY /wait-for-mysql.sh /wait-for-mysql.sh
RUN chmod +x /wait-for-mysql.sh

ADD /target/forum-0.0.1-SNAPSHOT.jar forum.jar

ENTRYPOINT ["java", "-jar", "forum.jar"]