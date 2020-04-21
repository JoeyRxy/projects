cd C:\Users\Rxy\Documents\MYCODE\projects\MyWebSite
mv src/main/resources/upload.json .
mv other/upload.json src/main/resources/
mvn clean package -DskipTests
scp target/rxy.war root@120.53.7.211:/usr/local/tomcat9/webapps
ssh root@120.53.7.211 "cd /usr/local/tomcat9/webapps;rm -rf rxy;echo '--------------'"
ssh root@120.53.7.211 "source /etc/profile;startup.sh"
mv src/main/resources/upload.json other/
mv upload.json src/main/resources/