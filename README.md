# faker-api-automation-test
Automation Test for Faker API

## Prerequisites

1. **Java 21** - Install and set JAVA_HOME
   ```bash
   java -version  # Should show Java 21
   ```

2. **Maven 3.6+** - Install Maven
   ```bash
   mvn -version  # Should show Maven 3.x
   ```

3. **Internet Connection** - For dependencies and API access

## Test Scenarios

Test scenarios are located in the `test-scenario/` folder.

## How to Run Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=PersonTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=PersonTest#statusCodeTest
```

## View Test Reports

**Report Location**: `test-reports/TestReport_YYYY-MM-DD_HH-mm-ss.html`

**Failure Evidence**: `test-reports/evidence/` (text files for failed tests)

## Test Results

- **Total Tests**: 65
- **Execution Time**: ~2-3 minutes
- **Reports**: Automatically generated in `test-reports/`
