# Learning Kotlin

There are four modules so far, in suggested reading order:

| Module                    | Target | Description |
|---------------------------|--------|-------------|
| kotlin-intro-jvm          | JVM    | Simple examples of Kotlin language features.
| kotlin-dropwizard-example | JVM    | Simple Dropwizard contacts application showing how easy it is to use familiar Java libraries from Kotlin.
| kotlin-intro-js           | JS     | For downloading images of Cats :-)  Demonstrates async code and co-routines.
| kotlin-http4k-example     | JVM    | Dropwizard example re-written using http4k to show how a different approach using function composition in a library built with Kotlin in mind.

## Starting JS example

There are runners for the JVM examples in IntelliJ.  To start the JS example, 
drop to a command prompt with 'npm' in the path and run '../gradlew run' in
the directory containing the example.

You can stop the server with '../gradlew stop'.

## NPM 5+

You may see issues running the JS example(s) using NPM v5+.  You
can downgrade to NPM 4 by running npm install -g npm@4.6.1. 
See https://github.com/Kotlin/kotlin-frontend-plugin/issues/16