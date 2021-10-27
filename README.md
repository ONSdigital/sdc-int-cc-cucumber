# ONS SDC Integrations Contact Centre Cucumber

## Prerequisites

To run the CC Cucumber tests the following services will need to be running:
 - Mock Services 
 - PubSub emulator
 - Redis
 - PostgreSQL
 - CC Service
 - CC UI
 
If you are running locally you can run a test script to verify that they are running:

    $ ./scripts/envCheck.sh
	Mock Services  : ok
	PubSub emulator: ok
	Postgres       : ok
	Redis          : ok
	CC Service     : ok
	CC UI          : ok

## Single test execution

To run a single test use its tag, for example for the T65 test:

    $ ./run.sh -Dcucumber.filter.tags="@SelPostcodeSearch"

## Running locally

To run the Cucumber tests in an easy way, screening out some Selenium noisy logging:

    ./run.sh
    
## Debugging

To help with debugging you can trigger a file saving of the page content on each page transition. 

    ./run.sh -DdumpPageContent=true
    

