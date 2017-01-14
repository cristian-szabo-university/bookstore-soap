# Bookstore SOAP #

Bookstore server application is a web service consisting of 3 service endpoint interfaces (BookService, PersonService and RentService). The communication protocol is established using the Simple Object Access Protocol (SOAP) and Extensible Markup Language (XML) for serializing client data.

# Server Setup #

The server acts as an enterprise application which runs inside the WebLogic Server and can be called by external clients. Oracle WebLogic is a middle tier application which runs between database browser-based clients.

The list below shows each step to deploy the server and access the server’s endpoints over internet or local network (only for development stage). 

Note: This instructions are platform independent which means that it will work on both Linux and Windows operating systems.

1.	Download and install Java EE 7 SDK from this link: www.orcale.com
2.	Download GlassFish 4.1 archive from this link: www.glassfish.java.net
3.	Extract archive and navigate to the location where it unpacked and access the following folder: glassfish/bin
4.	Change current password for the administrator account. Run command: asadmin change-password [ACCOUNT_NAME]. Default password is an empty text and the account name is admin.
5.	To access the admin web interface we need to make the connection secure because anyone can access it. Run command: asadmin enable-secure-admin
6.	Start the domain to listen for connection on the server. Run command: asadmin start-domain [DOMAIN_NAME]. The name of domain can be set to domain1
7.	Download JDBC Driver required for connecting to MySQL Database from this link: www.dev.mysql.com
8.	Copy driver JAR file to [GLASSFISH_DIR]/glassfish/domains/[DOMAIN_NAME]/lib
9.	Restart server to load the new driver. Run command: asadmin restart-domain [DOMAIN_NAME]
10.	Create the Connection Pool which uses the MySQL driver installed to create a physical database connection. In other words, the application will borrow a connection from the pool, uses it and then returns it by closing it. Run command: asadmin create-jdbc-connection-pool --datasourceclassname=com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype=javax.sql.DataSource --property="user=[USER_NAME]:password=[PASSWORD]:url=jdbc\\:mysql\\://[HOST_NAME]\\:[PORT]/[DATABASE_NAME]" bookstore-mysql-pool. 
11.	Create the Data Resource which is needed by the enterprise application to access the connection pool created above. Run command: asadmin create-jdbc-resource --connectionpoolid bookstore-mysql-pool jdbc/bookstore-mysql-datasource
12.	Install our application to the server. Run command: asadmin deploy [WAR_FILE]

The application should be running on http://[HOST_NAME]:8080/[APP_NAME]/[SERVICE_NAME]?WSDL

The current available Bookstore services are locate at:

- http://videogamelab.co.uk:8080/Bookstore/BookService?WSDL
- http://videogamelab.co.uk:8080/Bookstore/PersonService?WSDL
- http://videogamelab.co.uk:8080/Bookstore/RentService?WSDL
