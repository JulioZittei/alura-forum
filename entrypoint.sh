#!/bin/sh

# Esperar o MySQL estar pronto
/wait-for-mysql.sh

# Executar a aplicação Java
exec java $JAVA_OPTS -XX:+UseContainerSupport -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dserver.port=$PORT -Dspring.profiles.active=prod -jar forum.jar
