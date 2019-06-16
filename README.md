# Robotic Cleaner

[![Build Status](https://travis-ci.com/prasadus92/robocleaner.svg?branch=master)](https://travis-ci.com/prasadus92/robocleaner)

## Problem Statement

Your task is to write a Java based web service that navigates an imaginary robotic cleaner through an oil spill in the sea.

### Context
* Sea area dimensions as [X and Y coordinates](https://en.wikipedia.org/wiki/Cartesian_coordinate_system), identifying the top right corner of the area rectangle. This area is divided up into a grid using these dimensions; an area that has dimensions `X: 5` and `Y: 5` has 5 columns and 5 rows, so 25 possible cleaner positions. The bottom left corner is the point of origin for our coordinate system, so the bottom left corner of the area is defined by `X: 0` and `Y: 0`.
* Locations of patches of oil, also defined by `X` and `Y` coordinates identifying the bottom left corner of those grid positions.
* The initial cleaner position (X and Y coordinates like patches of oil)
* Navigation instructions (as [cardinal directions](https://en.wikipedia.org/wiki/Cardinal_direction) where e.g. `N` and `E` mean "go north" and "go east" respectively)
* The tide does not impact this simulation - the patches of oil remain in the same place throughout the execution of the program.
* The area will be rectangular, has no obstacles and all locations in the area will be clean (cleaning has no effect) except for the locations of the patches of oil presented in the program input.
* Navigating the cleaner onto a patch of oil removes the oil so that patch is then clean for the remainder of the program run. The cleaner is always on - there is no need to enable it.
* In the program input attempts to navigate the cleaner outside of the boundary of the defined area an appropriate error should be returned.

### Goal

The goal of the service is to take the area dimensions, the locations of the oil patches, the initial location of the cleaner and the navigation instructions as input and to then output the following:

* The final cleaner position (X, Y)
* The number of patches of oil the robot cleaned up

### Input

Program input will be received in a JSON payload with the format described here.

Example:

```json
{
  "areaSize" : [5, 5],
  "startingPosition" : [1, 2],
  "oilPatches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "navigationInstructions" : "NNESEESWNWW"
}
```

### Output

Service output should be returned as JSON.

Example (matching the input above):

```json
{
  "finalPosition" : [1, 3],
  "oilPatchesCleaned" : 1
}
```
Where `coords` are the final coordinates of the cleaner and `patches` is the number of cleaned patches.

### Deliverable

The service:

* is a web service
* must run on Mac OS X or Linux (x86-64)
* must be written using Java 8 or later using Spring Boot
* can make use of any existing open source libraries that don't directly address the problem statement (use your best judgement).

We require:

* The full source code, including any code written which is not part of the normal program run (scripts, tests).
* Clear instructions on how to build and run the program.
* Please provide any deliverables and instructions using a public Github (or similar) repository rather than ZIP files, emails and so on.

### Evaluation

We will especially consider:

* Code organisation
* Quality
* Readability
* Actually solving of the problem

This test is based on the following gist https://gist.github.com/alirussell/9a519e07128b7eafcb50

## Solution Overview

- The Area is being represented as a Grid of X and Y and the oil patches and cleaner points are marked accordingly.
- The Robotic Cleaner is made to move on this Grid as per the navigation instructions.
- Before each move, the resultant X and Y coordinates are checked for validity (being inside the area).
- If the Robotic Cleaner encounters an Oil Patch upon a move, it will be cleaned from the Area. Meanwhile, we keep track of the number of Oil Patches cleaned by the Robotic Cleaner.

## Building the Project

Maven is used as the build tool. Use the below command to build the project -

```bash
mvn clean install
```

`Dockerfile` is included in the project and hence to build the Docker image, just run -

```bash
mvn dockerfile:build
```

## Running the Project

### Using Maven

It's very easy with Maven here as well, just run the below command -

```bash
mvn spring-boot:run
```

### Using Docker Compose

If you have built the Docker image already, use the Docker Compose to run the application. Here is the command to use -

```bash
docker-compose up
```

## Running Tests

In case if you like to run the tests alone, use the below Maven command -

```bash
mvn clean test
```
