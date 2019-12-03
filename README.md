

# Development Setup

* Setup Local CA and Certificate --> https://golb.hplar.ch/2019/01/spring-boot-with-tls-localhost.html

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

create an ```application-staging.properties``` file under ```src/main/resources``` folder 