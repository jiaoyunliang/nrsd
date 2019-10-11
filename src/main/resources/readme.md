## Build Setup

log4j2.xml 
    修改 LOG_HOME 

upload.properties 
    修改 global_static_file_location
    修改 ueditorFileUrlPerfix    
    
static/lib/UE/ueditor.config.js
    修改 serverUrl
    
application-**.yml
    修改 port
    修改 datasource

application.yml
    修改  active


mvn clean

mvn package spring-boot:repackage

## run
java -jar nbnz.jar