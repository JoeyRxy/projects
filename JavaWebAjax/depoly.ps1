cd C:\Users\Rxy\Documents\MYCODE\projects\JavaWebAjax
mvn clean package -DskipTests
cd C:\'Program Files'\apache-tomcat-9.0.30\webapps
rm javawebajax.war
rm -r javawebajax
cp C:\Users\Rxy\Documents\MYCODE\projects\JavaWebAjax\target\javawebajax.war C:\'Program Files'\apache-tomcat-9.0.30\webapps
shutdown.bat
startup.bat