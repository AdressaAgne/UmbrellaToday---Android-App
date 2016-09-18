# Assignment 1: UI, the Web, persistent storage
* IMT3662  2016
* Prerequisite: completed Assignment 0.
* Weight 20%


## Objective
Become accustomed with developing for mobile devices and Android, using the UI and the web resources.

## Method
Develop a simple application, that has the following characteristics:
* the app has an UI that consists at least with 2 activities and at least 3 different UI widgets, eg. button, text view, and map view. 
* the app should store current state and information in the local database (SQLite) or preferences, so that when the app is loaded for the second time, it remembers the previous state that the user made.
* the app should access a web resources.
* the app should access at least 1 of the build-in sensors, e.g. location sensor.

**Note**, the activities should use relative layout, and it is preferable for you to use fragments, although you can use the activity without fragments for this exercise. 

The app can be anything, and the UI doesn’t need to be polished, or nice-looking. The idea doesn’t need to be original or unique. Implementing the example app below is fine. 

## Additional tasks which will gain additional marks:

* Create JUnit tests for your application.
* Access phone sensors to include changing orientation of the app. Make sure the UI works in portrait and landscape modes.
* Link app to map data showing users current location on the map.

## Example app
The app is only an example, and if you build one like that, you’d score the maximum points, regardless of how crappy the UI looks like. The app loads the location of the user using GPS when the user presses a button, then it shows user location on the map (using Google Maps API and map view component), and then it allow the user to toggle to another activity that displays: the user current location as lat/long data together with the altitude (all taken out from GPS sensor), location in plain english (eg. as a text “You are in Gjovik”), taken up from the Google Location API, and the air temperature (taken up from one of the weather services): 

“You are in Gjovik, currently 256 meters above sea level, and the local temperature is 19 degrees Celcius.”

The app stores historical records of the user location and temperature.


## Some hints
* Remember to read the FAQ.
* Split the app into self-contained components, so that the app works if one of the components is not implemented at all. Modularize. 
* Try to find existing tutorials from Google that provide you with necessary components, and reuse them in your application (example of displaying current user location on the map). Re-use is good. Make sure you describe in the report what have you re-used and where you have found inspiration/code from.
* Make sure you include something that you have done manually yourself (e.g. the weather data fetch for Gjovik), and describe it in your report. 
* Include in the report what was hard (or too hard), and what turned out easy, but you initially thought being hard. 


## Documentation and Submission
Submission in Fronter. Submission is made by filling up the submission form, and uploading a zipped folder called <student number>_ass1 eg: 103366_ass1.zip. 
This file will include three files:

1. A pdf file of the report.
2. A signed APK built for installing.
3. A link to the repository where the code is hosted (preferred), or the folder with the project.

The pdf report file will be 3-5 pages and it should contain:

1. Author name with student ID number.
2. Description of the Application (250 words max).
3. Discussion of the use of stored data when restarting from shutdown.
4. Discussion of the difference between native Apps and web Apps, with reference to the application developed (1 page).
5. Explanation of how the app could be extended (1 page).
