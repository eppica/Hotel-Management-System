# Hotel Management System

## About


Hotel Management System is a web application developed to Programming Language class in order to supply the basics necessities of a real hotel while we improve our knowledge


## Technologies

- Java
- Maven
- MySQL
- JSP
- HTML
- CSS
- JavaScript

## Development Enviroment
- Intellij Idea 2020.1 (Student Subscription)
- Apache Server 2.4.41 (provided by WAMPP)
- MySQL Server 5.7.28 (provided by WAMPP)
- TomCat 8.5.58
- OpenJDK 15
- JSTL 1.2 (provided by Maven)
- MySQL Connector Java 5.1.38 (provided by Maven)

## Documentation

To read the entire documentation, go to [Wiki](https://github.com/LBeghini/Hotel-Management-System/wiki)

## [Releases](https://github.com/LBeghini/Hotel-Management-System/releases)

Each release of this project implements a semester of the Programming Language class.


|  CLASS                  |      TAG                                                                            |
| :------------           | :---------:                                                                         |
| Programming Language I  |     [v1.0](https://github.com/LBeghini/Hotel-Management-System/releases/tag/1.0)    |


> Access the release page for a better description of what the release implements 


## Requirements

To run and edit this project locally, it's required that you have installed the following programs:
>This tutorial will be based on IntelliJ Idea configs
- IntelliJ 
- WAMPP
- Tomcat
- OpenJDK 15

After that, you'll need to clone this repo:
```
git clone https://github.com/LBeghini/Hotel-Management-System.git
```

## Setup Environment

### Environment
- Open the project on IntelliJ
- Install Maven dependencies
- Set Run/Debug configurations to Tomcat Server
- Config Project SDK to OpenJDK 15

### Database
- Run WAMPP
- Create a database ```hms```
- Connect to database
    - Go to ```HMS/src/main/java/dao/DB```
    - Change credentials to your database
>A ```script.sql``` model is provided for creating the database

### How to run

To run this project without and IDE, you can download the ```.WAR``` file provided in the release.  
Remember that you'll still going to need to run a database.  
However, the credentials to that database have match the one as follows:   

```
URL: jdbc:mysql://localhost:3306/hms
USER: root
PASSWORD: 
``` 

Run tomcat 
>This can be done with ```cmd``` 

Open your browser in ```localhost:8080```. You should see a page provided by tomcat.  
On the manager menu, search for the option to select the WAR file and deploy.

>You might have to set user and password for tomcat at this step

After that, you will be able to connect to the application from ```localhost:8080```


## License

GPL-2.0 License
