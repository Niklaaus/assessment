## Assesment

Requirements to run the application :

* Java 17
* internet connectivity.

API used to get conversion rates : `https://v6.exchangerate-api.com/v6/2c83f9fab3b91105b416cf04/latest/` (takes the original/base currency code as path param. so for USD, the URL shall be `https://v6.exchangerate-api.com/v6/2c83f9fab3b91105b416cf04/latest/USD`

Steps to run the application :

1. Clone the repo; navigate inside the <b>assesment</b> folder
2. run the following command :
> Windows :
* `gradlew buildAndTest` : to run the tests, generate the coverage report and build the executable jar.
* `gradlew bootJar` : to build the executable jar

> Linux :
* `./gradlew buildAndTest` : to run the tests, generate the coverage report and build the executable jar.
* `./gradlew bootJar` : to build the executable jar

3. run the application :
* navigate to `build\libs`  folder
* run <b>`java -jar assessment-0.0.1-SNAPSHOT.jar`</b> and the application will start listening on port 8080 on you machine.
* if port 8080 is busy, provide the port while executing the jar like this: <b>`java -jar assessment-0.0.1-SNAPSHOT.jar --server.port=8081` </b>.

4. Test the API :
    * API URL ->  localhost:\<port>/api/calculate
    * method -> post
    * Authentication : Basic. Make sure to add `Authorization` header with any of the user credentials mentioned in the data.sql. For example : `Authorization: Basic dXNlcjpwYXNzd29yZA==`
    * sample request body: 
        > {
    "items": [
        {"name": "Item 1", "category": "ELECTRONICS", "amount": 150.0},
        {"name": "Item 2", "category": "GROCERY", "amount": 50.0}
    ],
    "userType": "EMPLOYEE",
    "customerTenure": 3,
    "originalCurrency": "USD",
    "targetCurrency": "EUR"
}

5. Sample response :
   > {
    "amount": 134.325,
    "currency": "EUR"
}

6. if coverage report is generated in Step 2 , it would be found in the <b>reports/index.html</b> folder in the project root directory.

7. logs are generated inside <b>logs</b> folder in the same directory where the jar is executed

