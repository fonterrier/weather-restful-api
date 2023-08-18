# weather-restful-api

Spring Boot app that fronts the OpenWeatherMap service.

This project exposes an API GET endpoint, which calls the OpenWeatherMap weather service (http://samples.openweathermap.org/data/2.5/weather) to retrieve the current weather at a city, country location. This app stores the data from openweathermap.org into an H2 DB, so future calls to the app can query the data from H2 instead of the API. Future work will implement other advantages of fronting, such as enforcing API rate limits.

## How to use

1. Ensure Java 11 and Maven 3 are installed on your machine, and that you have an OpenWeatherMap account set up.
2. Edit application.properties with your OpenWeatherMap API key (replacing `placeholder1` in the line `com.example.weather.openweather.api.keys=placeholder1`e.g., `com.example.weather.openweather.api.keys=73f2c8324c01109095cffad7687abcde
   `)
3. Launch the app from the command line, by navigating to this folder and running `mvn clean spring-boot:run`
4. If you do not have Maven 3 installed, you can alternately run the app by executing `mvnw clean spring-boot:run` (that will use the Maven wrapper that comes with the project)
5. The Spring Boot app will start up on localhost:8080
6. Navigate to your preferred API tool (web browser, curl, Postman) and make a GET request to `http://localhost:8080/weather?city=Sydney&country=AUS` (e.g., enter this URL in your web browser). The OpenAPI 3 spec is defined in src/main/resources/api-spec.yaml. Change the location parameters to the city, country you are interested in based on the spec.
7. The app should return the weather at the location, e.g., `{"description":"clear sky"}`