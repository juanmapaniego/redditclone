# Reddit clone with spring and angular

It a spring boot backend app integrated with angular for frontend.

## First step
You need a mysql instance, add user, password and path of mysql database. 
After creating database you can alter the property named *spring.jpa.hibernate.ddl-auto* to *create* and run the backend so, the app create al tables.
After that change to original value. 
Also, this app use MailTrap service as SMTP server, so you have to create ann account to continue. And with this change respective values on *application.properties*

## Build

Clone the repository, move to 'bacend' folder and buil 
```bash
git clone repository.git
mvn build
```

## Run
After that you can run without problem

```bash
java -jar target/RedditClone-0.0.1-SNAPSHOT.jar
```
