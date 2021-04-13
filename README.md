# Cucumber Rest-Assured
This project is built with Cucumber, Rest-Assured, Junit. Also used WireMock

## Getting Started
```
git clone https://github.com/yahyakkara/CucumberApiAutomation.git
```


## Executing the tests
```
sh cd <project/path>
mvn clean verify

Run with options
mvn clean verify -Dcucumber.filter.tags="@Book"
```

- Run options
    - *Cucumber Filter*
    -         -Dcucumber.filter.tags="@Book"
    -         -Dcucumber.filter.tags="@Book or @Smoke"
***

## Reporting
- Reports
    -         Report ui :ProjectFolder/target/generated-report/index.html
***

## Tools
- Rest-Assured
- Cucumber BDD
- Junit
- WireMock
- Jackson-Databind
***
