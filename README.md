# Coffee-Shop-Order-System
A JavaFX desktop coffee shop order management system with login, menu ordering, pending orders, completed orders, and order logging.



# Coffee Shop Order System

Coffee Shop Order System is a JavaFX desktop application designed for managing coffee shop orders. The system allows users to log in, browse the menu, place orders, view pending orders, manage completed orders, and store completed order logs.

## Project Description

This project was created as a desktop-based coffee shop ordering system using Java and JavaFX. It uses FXML for the user interface, Java controller classes for system logic, and MySQL connectivity for database-related functions. The system is intended to help organize customer orders and make basic coffee shop order handling easier.

## Features

* User login and signup
* Coffee menu interface
* Order selection and order listing
* Pending orders management
* Completed orders management
* Order queue handling
* Completed order log saving
* JavaFX-based graphical user interface
* MySQL database connection support

## Technologies Used

* Java 21
* JavaFX SDK 21.0.11
* FXML
* CSS
* MySQL
* MySQL Connector/J
* Gson
* FontAwesomeFX
* VS Code
* NetBeans Ant project structure

## Project Structure

```text
TomoCoffee
├── .vscode
│   ├── launch.json
│   └── settings.json
├── javafx-sdk-21.0.11
├── library
│   ├── fontawesomefx-8.2.jar
│   ├── gson-2.10.1.jar
│   ├── mysql-connector-j-9.1.0.jar
│   └── javafx
├── nbproject
├── src
│   ├── controllers
│   ├── database
│   ├── images
│   ├── layout
│   ├── logs
│   ├── main
│   └── styles
├── build.xml
├── manifest.mf
└── README.md
```

## Requirements

Before running the project, make sure the following are installed:

* Java JDK 21
* JavaFX SDK 21.0.11
* MySQL Server
* VS Code or NetBeans
* Java Extension Pack for VS Code

## Required Libraries

The project uses the following libraries:

```text
fontawesomefx-8.2.jar
gson-2.10.1.jar
mysql-connector-j-9.1.0.jar
JavaFX SDK 21.0.11
```

The JavaFX SDK folder is included in the project as:

```text
javafx-sdk-21.0.11
```

The other external libraries are stored in:

```text
library
```

## How to Run in VS Code

Open the project folder directly:

```text
TomoCoffee
```

Do not open only a single Java file or the parent folder.

Make sure `.vscode/settings.json` includes the project libraries:

```json
{
  "java.project.sourcePaths": [
    "src"
  ],
  "java.project.outputPath": "build/classes",
  "java.project.referencedLibraries": [
    "library/**/*.jar",
    "javafx-sdk-21.0.11/lib/*.jar"
  ],
  "java.compile.nullAnalysis.mode": "disabled",
  "java.errors.incompleteClasspath.severity": "ignore",
  "java.autobuild.enabled": false
}
```

Run the project using the VS Code Run and Debug configuration named:

```text
Run TomoCoffee
```

## Manual Compile Command

From the project root folder, run:

```bat
"C:\Program Files\Microsoft\jdk-21.0.10.7-hotspot\bin\javac.exe" --module-path "javafx-sdk-21.0.11\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.web -cp "library/fontawesomefx-8.2.jar;library/gson-2.10.1.jar;library/mysql-connector-j-9.1.0.jar" -d build/classes src/main/Main.java src/controllers/*.java src/database/*.java
```

## Copy Resources

After compiling, copy the FXML, image, style, and log files:

```bat
xcopy src\layout build\classes\layout /E /I /Y
xcopy src\images build\classes\images /E /I /Y
xcopy src\styles build\classes\styles /E /I /Y
xcopy src\logs build\classes\logs /E /I /Y
```

## Manual Run Command

From the project root folder, run:

```bat
set PATH=%CD%\javafx-sdk-21.0.11\bin;%PATH%

"C:\Program Files\Microsoft\jdk-21.0.10.7-hotspot\bin\java.exe" --module-path "javafx-sdk-21.0.11\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.web --enable-native-access=javafx.graphics -cp "build/classes;library/fontawesomefx-8.2.jar;library/gson-2.10.1.jar;library/mysql-connector-j-9.1.0.jar" main.Main
```

## Database Setup

The project uses MySQL connectivity through `mysql-connector-j-9.1.0.jar`.

Before running the system with database features, make sure that:

1. MySQL Server is running.
2. The required database is created.
3. The database credentials in the project match your local MySQL setup.
4. The database connection code inside the `database` package is updated if needed.

Check the files inside:

```text
src/database
```

especially the database utility and login handler files.

## Notes

* The application runs as a JavaFX desktop system.
* JavaFX is required because the interface uses FXML and JavaFX controls.
* The `nbproject` folder is part of the NetBeans Ant project structure.
* Do not edit generated files such as `nbproject/build-impl.xml` and `nbproject/jfx-impl.xml`.
* If VS Code shows red JavaFX import warnings but the terminal compile and runtime work, it is only a VS Code Java language server indexing issue.

## Main Class

```text
main.Main
```

## Author

Created for a Coffee Shop Order System project.
