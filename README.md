# Date Scrapper

This Java application is a Date Scrapper that retrieves dates from an input text file based on dates pattern defined in the class DateMatcher.  
The dates patters are actully hard coded and could be retrieved from an external resource.

## Compiling and Running the application

Or an executable jar can be generated using Maven :  
Move to the project directory ```org.mehdi.datescrapper``` and run  
```mvn clean compile assembly:single```.  
A jar file ```datescrapper-jar-with-dependencies.jar``` will be generated in ```/target``` subdirectory.  
Move to ```target``` and run :  
```java -cp .\datescrapper-jar-with-dependencies.jar org.mehdi.datescrapper.App```  
The path of the file to scrap and the result will be displayed in the terminal. 