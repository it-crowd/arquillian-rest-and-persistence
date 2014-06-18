#Prerequisites

Unlike in many other tutorials using JBoss AS7 we cannot use default data source, as it uses in-memory database and we need real database
that we can access outside of application server. This example uses PostgreSQL 9.

##DB Setup

Create `arquillian_rest_and_persistence` user with password `arquillian_rest_and_persistence`:

    CREATE ROLE arquillian_rest_and_persistence LOGIN ENCRYPTED PASSWORD 'md582db4824950dd12707aacc4e017d01a8'
       VALID UNTIL 'infinity';

Create `arquillian_rest_and_persistence` owned by `arquillian_rest_and_persistence`:

    CREATE DATABASE arquillian_rest_and_persistence
      WITH ENCODING='UTF8'
           OWNER=arquillian_rest_and_persistence
           CONNECTION LIMIT=-1;

##AS7 Setup

###PostgreSQL driver setup
Create directory jboss-as-7.1.1.Final/modules/org/postgresql/main.
Put [postgresql-9.1-901.jdbc4.jar] and module.xml in that directory

    <?xml version="1.0" encoding="UTF-8"?>
    <module xmlns="urn:jboss:module:1.0" name="org.postgresql">
        <resources>
            <resource-root path="postgresql-9.1-901.jdbc4.jar"/>
        </resources>
        <dependencies>
            <module name="javax.api"/>
            <module name="javax.transaction.api"/>
        </dependencies>
    </module>

Register driver in jboss-cli.sh

    /subsystem=datasources/jdbc-driver=postgresql-jdbc4:add(driver-name=postgresql-jdbc4,driver-module-name=org.postgresql)

###DataSource setup
Add datasource using jboss-cli.sh

    /subsystem=datasources/data-source=arquillian_rest_and_persistenceDS:add(jndi-name=java:jboss/datasources/arquillian_rest_and_persistenceDS,jta=true,driver-name=postgresql-jdbc4,user-name=arquillian_rest_and_persistence,password=arquillian_rest_and_persistence,connection-url=jdbc:postgresql:arquillian_rest_and_persistence)
    /subsystem=datasources/data-source=arquillian_rest_and_persistenceDS:enable

[postgresql-9.1-901.jdbc4.jar]: http://central.maven.org/maven2/postgresql/postgresql/9.1-901.jdbc4/postgresql-9.1-901.jdbc4.jar