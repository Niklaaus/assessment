
# The Retail Store Discounts

## Overview
This is a SpringBoot application designed to calculate the final amount of an invoice by applying discounts and converting it into the requested currency. It considers multiple discount rules based on user-type, item-type and the tenure or user''s relationship with the store and  applies the highest applicable discount on it (except on grocery items). Then on the top of this, applies a flat discount of $5 per $100 on the discounted total. After that, the net payable is converted from the original currency to the target currency using the retrieved exchange rates (updated hourly).

## Technologies Used
 - **Java** 17
- **Gradle** for dependency management and project build
- **JaCoCo** for code coverage report
- **Spring Boot** as application framework
- **JUnit5** for unit testing

## Installation & Setup
#### Prerequisites
Ensure you have the following installed:
 - Java 17
 - working internet connection

## Steps to Set Up:
#### Step 1: Clone the repository

 - git clone https://github.com/Niklaaus/assessment.git
 - cd assessment

#### Step 2: Build the project
 Windows

 - `gradlew buildAndTest` : to run the tests, generate the coverage report and build the executable jar.
 - `gradlew bootJar` : to build the executable jar

 Linux

  - `./gradlew buildAndTest` : to run the tests, generate the coverage report and build the executable jar.
  - `./gradlew bootJar` : to build the executable jar

#### Step 3: Run the Application: 
 - `java -jar build/libs/assessment-0.0.1-SNAPSHOT.jar`
 - The application will start on http://localhost:8080/

if port 8080 is busy, provide a different port in the command like this : <b>`java -jar build/libs/assessment-0.0.1-SNAPSHOT.jar --server.port=8081` </b>.

### Code Coverage report:
Code coverage report, if generated in step 2, can be found in `reports` folder inside the project root directory. Open the `index.html` in any browser.

### Application Logs :
Application logs will be generated in the `logs` directory inside the project root.


## Key Features:
#### Percentage-Based Discounts:
The application calculates the discount on the total amount by applying highest of the following discounts :
 - 30% discount for employees of the store.
 - 10% discount for affiliates of the store.
 - 5% discount for customers who have been with the store for more than 2 years.
 ##### Note: Only one percentage-based discount can be applied per bill, and these are not applicable to groceries.

#### Fixed Discount:
 - A $5 discount is applied for every $100 in the amount calculated after applying the percentage discount ,if any, on the applicable items.


#### Strategy design pattern:
 - Each discount logic is encapsulated in its own class, separate from the others. The billing logic doesn't depend on any specific discount implementation but instead uses two common interfaces to apply the discounts.

#### Open-Closed principle :
 - Application follows the Open-Closed principle for maintaining different types of discounts. Discounts can be added or removed from the application without changing any other piece of code.

#### Unit Testing:
 - Comprehensive test coverage using JUnit to validate various discount scenarios.
      
## Project Structure

```md
├───main
│   ├───java
│   │   └───com
│   │       └───nagarro
│   │           └───assessment
│   │               │   CurrencyExchangerApplication.java  *Springboot application main class*
│   │               │
│   │               ├───advice
│   │               │       GlobalExceptionHandler.java     *To handle exceptions globally*
│   │               │
│   │               ├───config
│   │               │       AppConfig.java                  *Provides reusable beans (eg. RestTemplate)*
│   │               │       SecurityConfig.java             *Implements the authentication for the API endpoints*
│   │               │
│   │               ├───constants
│   │               │       CommonConstants.java            *Provides common constants to be reused in the application*
│   │               │       ErrorMessages.java              *Provides error messages to be reused in the applicaiton*
│   │               │
│   │               ├───controller
│   │               │       ExchangeController.java         *REST API endpoint controller*
│   │               │
│   │               ├───dto
│   │               │       BillRequestDTO.java             *Used as API Request body*
│   │               │       BillResponseDTO.java            *Used as API response body*
│   │               │       CurrencyResponseDTO.java        *Used as a container for the response recieved from Currency Conversion Rate API*
│   │               │
│   │               ├───model
│   │               │   ├───entity
│   │               │   │       User.java                   *Used by Spring Security to authenticate*
│   │               │   │
│   │               │   └───enums
│   │               │           ItemType.java               *The allowed list of item categories*
│   │               │           UserType.java               *The allowed list of user categories*
│   │               │
│   │               ├───repository
│   │               │       UserRepository.java             *Used by Spring Security to load user from DB for authentication*
│   │               │
│   │               ├───rules
│   │               │       AffiliateDiscountRule.java      *Provides the rule to calculate Affiliate discount*
│   │               │       DiscountRule.java               *Base interface to define discount rules*
│   │               │       DiscountRules.java              *Populates and provides the list of various available discount rules*
│   │               │       EmployeeDiscountRule.java       *Provides the rule to calculate Employee discount*
│   │               │       FlatDiscountRule.java           *Provides the rule to calculate flat discount*
│   │               │       LongTermUserDiscountRule.java   *Provides the rule to calculate Loyal-User discount*
│   │               │       PercentageBasedDiscountRule.java *Interface to denote Percentage Based Discount Rules*
│   │               │       UniversalDiscountRule.java      *Interface to denote NON-Percentage Based Discount Rules*
│   │               │
│   │               ├───service
│   │               │   │   CustomUserDetailsService.java   *Used by Spring security for authentication*
│   │               │   │   ExchangeService.java            *Service interface*
│   │               │   │
│   │               │   └───impl
│   │               │           ExchangeServiceImpl.java    *Service implementation to calulate final bill in the target currency*
│   │               │
│   │               └───utils
│   │                       Util.java                       *Provides Utility methods*
│   │
│   └───resources
│       │   application.properties                          *holds application properties*
│       │   data.sql                                        *contains sql queries for the seed data so the application can be used out of the box*
│       │   log4j2.xml                                      *provides logging configuration*
│       │   schema.sql                                      *contains the query to setup `users` table to be used in authentication`

└───test
    └───java
        └───com
            └───nagarro
                └───assessment
                    ├───controller
                    │       ExchangeControllerTest.java     *Tests to verify the functionality of the API endpoint*
                    │
                    └───service
                            CustomUserDetailsServiceTest.java   *Tests to verify the user authentication with respect to the users in DB*
                            ExchangeServiceTest.java        *Tests to validate the currency conversion rate API call*
```

#### UML 
![alt text](https://github.com/Niklaaus/assessment/blob/master/src/main/resources/uml.png?raw=true)

#### Assumption
The applicaiton assumes that the API endpoint is being used by a client-system which validates the Item category, user category and Customer-relationship tenure at their end and ensure the price of each item is correct as per their catalogue.

## Usage
This application calculates the net payable amount based on the following rules:
<ol>
<li>A percentage-based discount applies depending on the user’s relationship with the store.</li>
<li>The percentage based discounts are not applicable on Grocery items.</li>
<li>A fixed discount of $5 is applied for every $100 in the discounted total amount.</li>
</ol>

The application accepts input in the following format:

```md
{
    "items": [
        {
            "name": "Item 1",
            "category": "ELECTRONICS",
            "amount": 500.0
        },
        {
            "name": "Item 2",
            "category": "GROCERY",
            "amount": 400.0
        }
    ],
    "userType": "EMPLOYEE",
    "customerTenure": 3,
    "originalCurrency": "USD",
    "targetCurrency": "EUR"
}
```

#### Request body explained :
<ul>
<li><b>"items"</b> : list of items where every member has:
    <ul>
    <li><b>name</b> <i> (non-mandatory field) </i>:  name of the product</li>
    <li ><b>category</b><i> (mandatory field) </i>: Category of the item. Allowed values: <b>GROCERY, ELECTRONICS, APPARELS, HOME_DECOR, STATIONARY</b>.</li>
    <li><b>amount </b><i> (mandatory field) </i>: amount of the item </li>
    </li></ul>
<li> <b>"userType"</b> <i> (mandatory field) </i>: Type of the user. Allowed values: <b>EMPLOYEE, AFFILIATE, NA</b>.</li>
<li><b>"customerTenure"</b><i> (mandatory field) </i>: The tenure of customer's relationship with the store in years.</li>
<li> <b> "originalCurrency"</b><i> (mandatory field) </i>:Three letter code for the base currency
</li>
<li><b>"targetCurrency"</b><i> (mandatory field) </i>:Three letter code for the target currency
</li>
</ul>

Sample Request Screenshot:
![alt text](https://github.com/Niklaaus/assessment/blob/master/src/main/resources/request1.png?raw=true)

#### To access the endopoint:
- API URL ->  localhost:8080/api/calculate (change the port if used a different port while running the application in Step 3)
- method -> post
- Authentication : Basic. Make sure to add `Authorization` header with any of the user credentials mentioned in the data.sql. For example : `Authorization: Basic dXNlcjpwYXNzd29yZA==`

Sample authentication header screenshot:
![alt text](https://github.com/Niklaaus/assessment/blob/master/src/main/resources/auth1.png?raw=true)

#### Response :
- Successfull response will be in the format:
```md
  {
    "amount": 640.283,
    "currency": "EUR"
}
```
Sample response screenshot:
![alt text](https://github.com/Niklaaus/assessment/blob/master/src/main/resources/response1.png?raw=true)

## Example Scenario:
For a user who is an employee and has a bill of $500 for non-grocery items and $400 for grocery items and wants the final bill in EUR :
 - 30% discount is applied to $500 (as he is an employee); (effective total=$350)
 - No percentage based discount on grocery items; (effective total $350+$400= $750)
 - flat discount of $5 on every $100 in the discounted total;(effective total = $750-((750/100)*5) = $715 )
 - At the time of writing, USD to EUR conversion rate is <b>0.8955</b>. So final bill amount in EUR = 715*0.8955 = <b>EUR 640.283</b>



## License
CC0 1.0 Universal (CC0 1.0) Public Domain Dedication

## Contributing
If you'd like to contribute to this project:
<ol>
 <li>Fork the repository.</li>
 <li>Create a new branch (git checkout -b feature/your-feature).</li>
 <li>Commit your changes (git commit -am 'Add some feature').</li>
 <li>Push to the branch (git push origin feature/your-feature).</li>
 <li>Create a new Pull Request.</li>
</ol>

## Contact
For any issues or suggestions, please feel free to contact:
<br>Email: saurabh.srivastav@gmail.com</br>
