# :bellhop_bell: Hotel Management System

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
- Intellij Idea 2020.3.1 (Student Subscription)
- MySQL Server 5.7.28 (provided by XAMPP)
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
- IntelliJ
- XAMPP
- OpenJDK 15
- Maven

After that, you'll need to clone this repo:
```
git clone https://github.com/LBeghini/Hotel-Management-System.git
```

## Setup Environment

On IntelliJ, open the project cloned from GitHub.

### Maven Dependencies

Anywhere inside the IDE, type `shift+shift` and when the search input appears, type `reload project` and hit enter.
This will install all Maven dependencies.

### Run Configuration

Anywhere inside the IDE, type `shift+shift` and when the search input appears, type `run/debug configurations` and hit enter.
This will open the Run/Debug Configurations dialog.

Add a New Configuration clicking on the `+` button or type `alt+insert`. Choose Maven.
In the Parameters tab, at the Command line input, type:
```
package cargo:run
```

### Project SDK

On IntelliJ, go to `File > Project Structure`.
On the Project Structure dialog, there's an input to place the Project SDK. 

You can choose one that is alrealdy installed on your machine, or let IntelliJ download if for you.

### Database
- Run XAMPP
- On XAMPP Dashboard, run MySQL
- At any browser, access http://localhost/phpmyadmin.
- On Database tab, enter and create a new database called `hms` 

> Remember that the user credentials to the project to access this database is username:`root` and password:` `

## :balance_scale: License

GPL-2.0 License
