

# Development Setup

* Setup Local CA and Certificate --> https://golb.hplar.ch/2019/01/spring-boot-with-tls-localhost.html

# Remote debugging a java application

```shell script
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=y -jar application.jar
```
# Run Spring Aplplication with an active profile
```shell script
java -jar -Dspring.profiles.active=staging  target/demo-0.0.1-SNAPSHOT.jar
```
The application will take configurations from ```application.properties``` file.
All configurations in ```application-staging.properties``` will override any configuration from the default configuration file.

# Generate Self-Signed SSL certificate
```shell script
keytool -genkeypair -alias sample -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore selfsigned.p12 -validity 3650
Enter keystore password: sample123
Re-enter new password: samepl123
What is your first and last name?
  [Unknown]:  John Doe
What is the name of your organizational unit?
  [Unknown]:  security org
What is the name of your organization?
  [Unknown]:  Acme inc
What is the name of your City or Locality?
  [Unknown]:  anytown
What is the name of your State or Province?
  [Unknown]:  CA
What is the two-letter country code for this unit?
  [Unknown]:  US
Is CN=John Doe, OU=security org, O=Acme inc, L=anytown, ST=CA, C=US correct?
  [no]:  yes
``` 

# Use self-signed certificate for environments other than "dev"

create an ```application-staging.properties``` file under ```src/main/resources``` folder.

Add the following values into the ```application-staging.properties``` file 

```properties
server.port=443
server.ssl.enabled=true
server.ssl.protocol=TLS
server.ssl.key-store=./selfsigned,p12
server.ssl.key-store-type=PKCS12
server.ssl.key-store-password=sample123
```