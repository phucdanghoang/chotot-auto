# Chotot Test Automation package
## Installation
## Test stack
```
- Test framework: testNG framework
- Test library: selenium web-driver
- Programming language: Java 8+
- Test report: Extent report
- Plugin: Webdriver-manager plugin
```

## Install requirements
```
- MacOS/Window (Switching chrome driver base on your OS at DriverManager class)
- Install JAVA 11
- Install IntelliJ IDE latest version
- Install Maven (https://www.mkyong.com/maven/how-to-install-maven-in-windows/)(Optional If you run direct by TestNG)

```

## for MAC edit - nano ~./bash_profile
```

export JAVA_HOME=$(/usr/libexec/java_home)
```

## Test case: The scope of login function context:
##### 1/ Verify user login successful with valid data.
##### 2/ Verify user login unsuccessful with invalid data.
##### 3/ Verify error message displays when user input wrong phone number.
##### 4/ Verify error message displays when user input wrong password.

## Running the test
- Step 1: Pull repo to your local machine
- Step 2: Make sure that Java and SDK environment already setup in your local machine which fit your setting suggested above.
- Step 3: Build project to make sure all artifacts/dependencies in maven have downloaded
- Step 4: Execute Maven Command below
```
mvn clean test
```
Note:
- If maven can't build project and run the test the issue maybe in `setting.xml` file on your local `m2repository`. You can run directly by TestNG with file `UITest.xml` on package `src/test/java/com/chotot/test/UITest.xml` (right click on this file to open menu> click on RUN)


## Viewing Test report
- Open file `*/reports/UITest.html` on any browser

## Architecture

    .
    ├── auto-framework            # Core auto
    |   ├── base                  # Contains common wrapped methods: Drivermanager handled, Event listner,...
    |   ├── config                # contains yaml config reader to get config properties
    |   ├── enum                  # Contains constant, enum variable,..
    |   ├── exceptions            # Contains customize's exception
    |   ├── utilities             # Contains common utilies file, helper, kind of util things
    |   |    └── WaitUtil         # Selenium wait customization wrapper
    |   |    └── DirectoryUtil    # Support everything about path, foler is contained in there
    |   |    └── ExtentReport     # Extent report log handler
    |   |    └── ExtentManager    # Extent report configuration setup
    |   | 
    └── chotot-business           # Test
        ├── main
        |   ├── java.com.chotot.test
        |   |   ├── common        # Contains common class support all things related to test 
        |   |   ├── constant      # Contains constant variable for logic test, business
        |   |   ├── hook          # Contains testNG hook, test setup, test runner
        |   |   ├── model         # Data model, dto, business model,...
        |   |   ├── page          # page object model class, page action
        |   |   ├── resources     # environment config
        └── test
            ├── java.com.chotot.test
                ├── LoginTest     # Chotot login automated test implementation

