# Vitt-be README

# Clone repo 
git clone https://gitlab.com/osiride/vitt-be.git

# Starting SonarQube 
set JAVA_HOME=D:\Software\Java_17
set PATH=%JAVA_HOME%\bin;%PATH%

# check if "java -version" is jdk-17
D:/Software/sonarqube/bin/windows-x86-64/StartSonar.bat

"username" : "admin"
"password" : "root"

# OpenAPI Swagger 
path "http://localhost:8080/swagger-ui/index.html#/"

#Start Application with Scaffolding data

go in src/main/resources/application.yml

change ddl-auto: update with ddl-auto: create-drop

this will load every Insert wrote in src/main/resourses/import.sql