# SENG201 25S1 Group 50 Project
*98% grade*
This project's repository can be found on git here:
https://eng-git.canterbury.ac.nz/seng201-2025/team-050

# Drift Fever
This is a game made in JavaFX where you are an aspiring racer where you compete against other races in various tracks, upgrading your vehicle as you go until
the season is over, acruing as much prize money as possible.


# Authors
- Ben Leighs
- Dwyane Ramos

## Prerequisites
- JDK >= 21 [click here to get the latest stable OpenJDK release (as of writing this README)](https://jdk.java.net/21/)
- *(optional)* Gradle [Download](https://gradle.org/releases/) and [Install](https://gradle.org/install/)


# Building the application through a jar
Open the `team-050` in IntelliJ and run this command in the terminal './gradlew jar'

# Running the Application via Jar
To run application, you can simply double click the file `ble73_dra131_driftfever.jar`. However, if that does not work, to run it on a linux terminal, open a terminal in the directory as the jar file (it should be in the build/libs directory) and run the following command: `java -jar ble73_dra131_driftfever.jar`


# Running the Application inside the Project Directory
Simply open a terminal interface inside the project directory and run ./gradlew run.

## Importing Project (Using IntelliJ)
IntelliJ has built-in support for Gradle. To import your project:

- Launch IntelliJ and choose `Open` from the start-up window.
- Select the project and click open
- At this point in the bottom right notifications you may be prompted to 'load gradle scripts', If so, click load

**Note:** *If you run into dependency issues when running the app or the Gradle pop up doesn't appear then open the Gradle sidebar and click the Refresh icon.*


## Run Tests
1. Open a command line interface inside the project directory and run `./gradlew test` to run the tests.
2. Test results should be printed to the command line

# Dependencies:
* JUnit 5

# Use of AI Declaration
We have used ChatGPT to generate some background images for our project. We’ve also used it to generate inspiration for our game (such as for creating upgrades) and have it create random values for the stats of cars.
