#!/bin/bash
PID=$(lsof -t -i:8088)
if [ -n "$PID" ]; then
  echo "Killing process $PID"
  kill $PID
fi
nohup ./mvnw spring-boot:run -D server.port=8088
