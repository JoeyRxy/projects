cd C:\Users\Rxy\Documents\MYCODE\project\MyWebSite
mv src/main/resources/upload.json .
mv other/upload.json src/main/resources/
mvn clean package -DskipTests
scp target/rxy.war root@212.129.242.233:/usr/share/tomcat9/webapps
ssh root@212.129.242.233 "cd /usr/share/tomcat9/webapps;rm -rf rxy;echo '--------------'"
ssh root@212.129.242.233 "source /etc/profile;startup.sh"
mv src/main/resources/upload.json other/
mv upload.json src/main/resources/