**UBC IRP QA Test Automation / Github API Test**

# Getting Started

This guide assumes that you have the latest [JDK 11](https://www.oracle.com/java/technologies/downloads/), [Gradle](https://gradle.org/), and [JUnit](https://junit.org/junit5/) testing framework installed.

**Serializing/Deserializing with GSON**

I used GSON to serialize/deserialize JSON to Java object and vice versa. Please include dependencies in the build file:
* `implementation 'com.google.code.gson:gson:2.9.0'`

**Setting the Personal Access Token**

To create a gist, you will need to create a [Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) with gist privileges. 
In this current version, you will need to manually update the stub token `TOKEN` in `/src/test/client`, however it is better to set your token as an environment varaible.

**The Testing Done**

1. Test that you can create a new gist containing a file with the contents "UBC IRP Student QA!"
2. Test that when creating a new gist the file parameter is in fact required. 
3. Test that the "User Agent" header is required when making a request.

## How to Run Tests
In the command line, navigate to the src of your project. Then run `gradle test`. Or you can configure the test settings on your preferred IDE.

## Test 1 Justification
In order to verify that a new gist has been created, we needed to first make sure that the status code of the `POST` request is `Status: 201 Created`.
This can be done by calling `statusCode()` on a returned `HttpResponse<String>` object.

To test that the file contains the contents `UBC IRP Student QA!`, we call a `GET` request on a URL pathway with the created gist id to access the actual file content.
This can then be used to compare with the expected file content.

## Test 2 Justification
To test that a file parameter is in fact required, this test makes sure that tests without a file parameter will not process.
This test if the request fails by setting the file parameter to `null`.
The test checks if the response status code is `Status: 422 Unprocessable Entity`. 

## Test 3 Justification
To test that a "User Agent" header is required, we make sure that request will not process if it is omitted or if it is incorrect.
This test checks that the response status code for a request is `Status: 401 Unauthorized`.
