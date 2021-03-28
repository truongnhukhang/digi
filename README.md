# Digi Hotel Management
A hotel management application
## What does the application do?
- Booking a room on a date by a user
- Find available rooms on a date
- Get booking information of a user
## Does it thread safe?
- Yes, he does 
## What do we have in the next release?
- Validate user can not book a date in the pass
- Currently, the database will be locked when we make a booking. We can reduce the lock level from database to table locking or row locking. 
## Requirement
Please make sure the below libraries have existed in your machine 
- Jdk 1.8
- Maven
## How to run
- Navigate to the root folder ( same level with pom.xml)
- run the build command
```
mvn clean install
```
- a new folder namely `target` will be created after run build command, navigate to that folder and run the application ( replace 56 by your number of rooms )
```
java -jar SilentiumIO-1.0-SNAPSHOT.jar 56
```
