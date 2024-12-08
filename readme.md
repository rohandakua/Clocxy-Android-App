# NexGen Clock App

### Goal of this App
> to be updated later


## Working of the App

### Basic Overview

This app has mainly three key features 
+ Home Page
+ Alarm Page
+ StopWatch Page

### Working of the Home Page

The Home Page is Controlled by MainScreenViewModel.
It has three features.
1. Current Date and Time 
> This is a Basic feature and this is handled by getDateAndTime() of the MainScreenViewModel.
> In this formatString() is used to make the single digit time to double digit time.

2. Weather Details
> Here we are fetching the data from openweather Api. To make it work follow the steps in the app/src/main/java/com/example/clockappbyrohan/data/online/BaseUrlWeatherApiDemo.kt
> We are fetching the location from the device using fetchLocation(), where we are using the fusedLocationProviderClient to get the location of the device. Using the coordinates we are fetching the weather of the location of the user.
> <br>
> To **Resolve the location when location of the device is turned off** # todo

3. Zen Mode
> Here the user can go to full screen mode where the phone will turn on ***Do Not Disturb*** mode and the Time and Date will be shown in full screen.
> <br>
> We are requesting the user to grant the permission for ***Notification_Policy*** to make sure the app turns on the DND mode automatically. If this permission is not given then DND will not turn on automatically.

### Working of the Stopwatch page

The Stopwatch page is controlled by the StopWatchViewModel.
It has two buttons. Start/Pause button handles the functioning of the stopwatch. Reset button makes the timer to zero time. The working is basic. We are storing the elapsed time in offline storage 
(precisely in StopwatchRepositoryImplementation) and showing it. We are maintaining a flag i.e. ***isRunning*** to make the start/pause button work. There are four usecases which handles the working 
of the stopwatch.

### Working of the Alarm Page



***TODO***

## About sqlite database
Android SQLite Database is an open-source database provided in Android that is used to store data inside the user’s device in the form of a Text file. We can perform many operations on this data such as adding new data, updating, reading, and deleting this data. 
SQLite is an offline database that is locally stored in the user’s device and we do not have to create any connection to connect to this database.  

## Permission used in the app

#### Read_external_storage
This permission is used to access the SQLite database in the android phone to store the alarm's data , as if android is restarted then all the scheduled alarm are removed by the android 
system. So we are storing them in SQLite database and if the device restarts or is rebooted in any case , the previous alarm will be rescheduled again.