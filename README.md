# Gfights
Gflights is a RESTful Web Service built using SpringBoot with the following functionalities:- 
###### Admin Login
1.) Add Flight <br/>
2.) Get Flight <br/>
3.) Update Flight <br/>
4.) Remove Flight <br/>
###### User SignUp <br/>
###### User SignIn <br/>
1.) User Details along with All Bookings(with Passangers Details) <br/>
2.) Update User Name <br/>
3.) Update User Password <br/>
4.) Recharge User Wallet <br/>
5.) Search Flight <br/>
6.) Book Flight (amount debited from User Wallet) <br/>
7.) Update Booking (amount debited/credited from User Wallet) <br/>
8.) Cancel Booking (amount credited to User Wallet) <br/>

# Start the project from Scratch
Go to https://start.spring.io/  generate, extract the downloaded zip file and import it into Eclipse. <br/>

Your installed JDK should be at least Java8 Version and establish database connection on eclipse. <br/>

Go to pom.xml and add all required dependencies.

# Modify these according to your database credential
Go to application.properties file
###### MYSQL settings
spring.datasource.url=jdbc:mysql://localhost:3306/spring_project <br/>
spring.datasource.username=root <br/>
spring.datasource.password=abcdef
###### Oracle settings
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe <br/>
spring.datasource.username=system <br/>
spring.datasource.password=oracle
###### Tomcat Server
Default server port used by tomcat is 8080 <br/>
You can manually set the server port in the application.properties file- server.port=8091


# Logging using Log4j
I have provided logging logic in LoggingExpect.java class i.e followed the Spring AOP concept for all the methods of DAO and Service class after throwing an exception on error level. <br/>

If you want to get the log details into a file then create a folder(named as logs) and add a file(error.log) into that folder and define the path of this log file in the application.properties file- logging.file=logs/error.log
