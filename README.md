# junit_automation
JUnit Automation Project Repository

# Project Structure

- automation: contains base framework
- helpers: contains all helpers (WEB and API helper, and Logs)
- pageobject: contains files from Page Object design
- scripts/web: contains all WEB test cases scripts
- scripts/api: contains all API test cases scripts
- utils: contains utils, like constants

# WEB Scripts:

1. Search:
	- Properly navigation
	- Search word
	- Check results

# API Scripts:

1. CheckBreweries:
	- Getting all breweries and search one
	- Validate brewerie data

# Test cases availables:

1. Search 'heladera'
2. Search 'monitor'
3. Search 'smart tv'
4. Validate 'Lagunitas Brewing Co' brewerie data

# How to run tests
Run from bash: mvn clean test

# Test results
HTML Report will be save in '../target/report.html' when execution finished
