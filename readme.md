# Clocxy Android App

### Goal of this App
We want to make the experience of the user simple. Nothing sort of fancy, just clocxy and productivity.<br>
Using clocxy improve your daily routine and improve in life.

## Working of the App

### Overview

This app has mainly three key features 
+ Home Page
+ Alarm Page
+ StopWatch Page

### Working of the Home Page

The Home Page is Controlled by MainScreenViewModel.
It has three features.
> 1. Current Date and Time <br>
>    This is a Basic feature and this is handled by getDateAndTime() of the MainScreenViewModel.
>    In this formatString() is used to make the single digit time to double digit time.

> 2. Weather Details<br>
>    Here we are fetching the data from openweather Api. To make it work follow the steps in the /data/online/BaseUrlWeatherApiDemo.kt
>    We are fetching the location from the device using fetchLocation(), where we are using the fusedLocationProviderClient to get the location of the device. Using the coordinates we are fetching the weather of the location of the user.
>    <br>
>    To **Resolve the location when location of the device is turned off** we are assuming that the user is situated in New Delhi. Other wise we dont have any option to show the weather data.

> 3. Zen Mode<br>
>    Here the user can go to full screen mode where the phone will turn on ***Do Not Disturb*** mode and the Time and Date will be shown in full screen.
>   <br>
>    We are requesting the user to grant the permission for ***Notification_Policy*** to make sure the app turns on the DND mode automatically. If this permission is not given then DND will not turn on automatically.

### Working of the Stopwatch page

The Stopwatch page is controlled by the StopWatchViewModel.
It has two buttons. Start/Pause button handles the functioning of the stopwatch. Reset button makes the timer to zero time. The working is basic. We are storing the elapsed time in offline storage 
(precisely in StopwatchRepositoryImplementation) and showing it. We are maintaining a flag i.e. ***isRunning*** to make the start/pause button work. There are four usecases which handles the working 
of the stopwatch.

### Working of the Alarm Page

The Alarm Page is controlled by the AlarmViewModel. 
User can create new alarm, update the existing alarm, or delete the alarm. We are saving the alarm information on the device itself using ROOM Library. 
The Alarm when triggers create a notification and plays a music for 1 minute. If the notification is swiped away or stop is clicked in the notification or the app is closed will result in stopping of the alarm.
<br>
<br>
User can click on three things
> 1. Create+ 
><br>This is a basic button which clicked takes the user to the AlarmDetailPage. Here user can select time and days of the week when he/she wants to receive a alarm. 
>    User have to click on save to save the alarm , and he/she will be redirected to the AlarmHomePage. If save is not clicked then the alarm will not be saved.

> 2. Individual Alarm
><br>When individual alarm is clicked then the user will be directed to the AlarmDetailPage again and there he/she can update the alarm and click on save to save the alarm.

>3.  Delete a particular Alarm
><br>When a user clicks on the delete icon then it deletes the alarm.

#### Detail working of Alarm Page 
The Main implementation of alarm is done in the data/offline/alarm directory.
AlarmDb, AlarmDbDAO, Alarms is implementation of the Room. They manage the creation and deletion of the alarm .<br> For more on how to create one , visit https://developer.android.com/jetpack/androidx/releases/room
<br><br>We have three BroadCast receiver for this app. 
> 1. AlarmNotificationReceiver <br>
>    This is used to Create the notification for the alarm. It has the implementation for how to stop the ongoing alarm.<br>

> 2. AlarmReceiver <br>
>    This is used when the alarm is triggered to show the notification. It is also responsible for stopping the alarm after 60 seconds and also it makes the notification disappear after 60 seconds.

> 3. RebootBroadCastReceiver <br>
>    This is located in the domain/framework. It is used to reschedule the alarm after the device reboots. As android cancel all the alarm when the device is rebooted so we have to make sure 
>    that all the alarm are rescheduled after that.

<br>
AlarmSchedulerInterfaceImplementation handles all the necessary action to schedule , delete and update the alarm. It has a AlarmManager that is received through hilt. It calls all the actions from 
Room db and AlarmManager to make the alarm work as intended.
<br><br>
MediaPlayerManager in domain/framework is used to make the MediaPlayer work for the alarm. We faced a **Issue** here. We were trying to make the MediaPlayer inject from the hilt. But 
if we were making the hilt give a single instance (@Singelton) of the MediaPlayer then after 1 st alarm it was not working as intended. If we are making it (@Reusable) still it was not working.
So we ended up with making a MediaPlayerManager class to handle this stuff and we injected a single instance of this using hilt.

### Working of Setting page
Setting page is controlled by SettingViewModel. User can set the desired theme using the setting page. There are 20 unique combination. 

## Swipe Page 
The presentation/composableScreens/SwipeableScreens is used to make the swipe gesture work in android. It has 4 screens that are HomePage, StopWatchPage, AlarmHomePage and SettingScreenPage .
To know more about this https://developer.android.com/develop/ui/compose/layouts/pager


## About Room 
Room is one of the Jetpack Architecture Components in Android. This provides an abstract layer over the SQLite Database to save and perform the operations on persistent data locally. 
This is recommended by Google over SQLite Database although the SQLite APIs are more powerful they are fairly low-level, which requires a lot of time and effort to use. 
But Room makes everything easy and clear to create a Database and perform the operations on it.
<br><br>
## About sqlite database
Android SQLite Database is an open-source database provided in Android that is used to store data inside the user’s device in the form of a Text file. We can perform many operations on this data such as adding new data, updating, reading, and deleting this data. 
SQLite is an offline database that is locally stored in the user’s device and we do not have to create any connection to connect to this database.  

## Permission used in Clocxy

#### 1. `android.permission.INTERNET`
- **Use**: Allows the app to access the internet.
- **Why**: Required to fetch weather data from online APIs, synchronize data with remote servers, or perform any other network operations.

#### 2. `android.permission.USE_EXACT_ALARM`
- **Use**: Grants the ability to use exact alarms.
- **Why**: Ensures precise triggering of alarms, which is critical for the alarm clock functionality in your app.

#### 3. `android.permission.RECEIVE_BOOT_COMPLETED`
- **Use**: Allows the app to receive a broadcast when the device finishes booting.
- **Why**: Ensures that alarms are re-registered and functional after the device restarts.

#### 4. `android.permission.ACCESS_COARSE_LOCATION`
- **Use**: Allows access to approximate location.
- **Why**: Helps fetch weather information based on the user’s general area without requiring precise location.

#### 5. `android.permission.ACCESS_FINE_LOCATION`
- **Use**: Allows access to precise location.
- **Why**: Used for providing more accurate weather updates based on the user's exact location.

#### 6. `android.permission.ACCESS_NOTIFICATION_POLICY`
- **Use**: Enables the app to read and modify notification settings.
- **Why**: Used for managing alarm notifications or ensuring the alarm sounds even in Do Not Disturb mode.

#### 7. `android.permission.SCHEDULE_EXACT_ALARM`
- **Use**: Specifically allows scheduling of exact alarms.
- **Why**: Required for alarm scheduling to work as intended with exact timing.

#### 8. `android.permission.READ_EXTERNAL_STORAGE`
- **Use**: Allows reading from external storage.
- **Why**: Enables accessing audio files stored externally, which might be used as custom alarm tones.

#### 9. `android.permission.READ_MEDIA_AUDIO`
- **Use**: Allows reading audio files from storage.
- **Why**: Used for selecting and playing audio files for alarms.

#### 10. `android.permission.POST_NOTIFICATIONS`
- **Use**: Allows the app to post notifications to the user.
- **Why**: Used to show alarm notifications or other relevant updates.


## Issues 

### Issues Faced
> We faced a issue in making the MediaPlayer inject in the AlarmNotificationReceiver. It was injecting a single instance through hilt and was not stopping the alarm 
> as intended. 
> <br> <br>
> To solve this we created a MediaPlayerManager that is injected through hilt (as @Singelton). It manages the mediaplayer and is working fine.

### Issues That Can Occur
> We are using HorizontalPager to make the screens swipeable. This can be deprecated in the coming future.
> <br>
> Checkout for the library versions for future.

## Next Updates :-
We want to create a widget for this app. Widget should show the current time and weather details. If you are interested to develop one then create one and raise a PR. It would be a huge help for us.

## What to do if you want to run this on your phone
You can download the app from google play store or use the guide below to make your own version.

> 1. Check for the versions of the implementation on build.gradle.kts (both) or you can change the versions in libs.versions.toml (in gradle scripts in Android view of the project)
> 2. Update the versions to latest version.
> 3. Check if HorizontalPager is still not deprecated or not.
> 4. Make the BaseUrlWeatherApi.kt in data/online using the guide given there.
> 5. Update the Compile sdk in Android Manifest file.
> 6. Then run the app and woila!!! it should work.

#### Contact 
Email - rohandakuareal@gmail.com