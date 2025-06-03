@echo off
REM Sets up JDK 24 environment and runs the Spring Boot application
setx JAVA_HOME "C:\Program Files\Java\jdk-24"
set JAVA_HOME=C:\Program Files\Java\jdk-24
set PATH=%PATH%;%JAVA_HOME%\bin
echo Running application with JDK 24...
call .\mvnw.cmd spring-boot:run
pause
