# Search engine optimisation project for Introductory Programming at IT University of Copenhagen 2022

This repository contains code for a search engine that has been modified to support the following tasks:

1. If no web pages match the query, it outputs "No web page contains the query word." 🔍
2. A web page is only created if both the title is set and the word list contains at least one word. 📃
3. An inverted index has been implemented to improve the efficiency of search queries. 🔄
4. The search engine supports refined queries, including handling multiple words, merging via "OR", and nesting queries with parentheses. 📜

The code includes an implementation of an inverted index using a `java.util.Map`, which can be populated from a specified data file and tested and benchmarked using a separate method for looking up a given search term. The search engine also uses regular expressions to parse and process complex queries.

The code has been thoroughly unit tested to ensure that it is correct and reliable. 🧪

In addition, the code includes Javadoc documentation to help users understand how to use the various classes and methods. 📚

This project makes use of the following programming tools and techniques that were covered in class:

1. Version control using Git and ITU's GitHub.
2. Unit testing with JUnit.
3. Debugging in the programming language/with IDE support.
4. Simple web services.
5. Code documentation using Javadoc.
6. Build tools such as Gradle.
7. Benchmarking code (optional).

The goal of the project was not to develop a search engine, but rather to become familiar with these tools and techniques through practical application. External libraries that implement parts of the functionality were not allowed, with the exception of Java's standard libraries. The code in this repository was written by the authors, with the exception of code in the project template.
