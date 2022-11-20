A shipping company wants to let the customers to register in their system with username and a list of addresses, where the company to ship their packages to.
Step 1: Create a web service that allows users to register. Build an endpoint that takes 2 parameters : username and a list of zip codes (we use only zip codes instead of a full address).Example request to be sent:
```json
{
  "username": "Jhon Doe",
  "zipCodes": [
    1111,
    2222,
    3333
  ]
}
```
Step 2: Extend the endpoint by implementing data persistence . Store username and list of zip codes into the database. Each time one and the same user(with the same username) registers , store a new entry in the database. We want to keep each entry from each registration for a single user for audit purposes.
Tip: You may chose to use any database (relation, NoSQL, in-memory DB) and build the appropriate DB structure (tables, columns) according the data you are persisting.
Step 3a: Insert multiple test users (also create more than one entry for one and the same user) in the DB so that you have enough test data to test the next steps.
Step 3b: Create an endpoint to fetch data from DB by given username. It should return the username and the list of all zip codes related to this username (from all registrations).
Step 4: Create an endpoint to return the list of addresses for a given user from the latest registration for this user.
Step 5: Extend the endpoint from step 1 by implementing a call to the following service https://api.zippopotam.us/us/33162    to get additional information about the address by given zip code.
The service returns information about the address by given zip code. E.g for zip code=33162 it returns:
```json
{"post code": "33162", "country": "United States", "country abbreviation": "US", "places": [{"place name": "Miami", "longitude": "-80.183", "state": "Florida", "state abbreviation": "FL", "latitude": "25.9286"}]}
```
Call the service with a valid zip code (you can get a valid zip code from here https://www.unitedstateszipcodes.org/ ) then parse the response and  store the place name, longitude and latitude in the DB along with the zip code.
