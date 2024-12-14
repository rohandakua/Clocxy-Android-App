package com.clocxy.clocxyone.data.offline.alarm

/**
 * this class Week is used to store the days of the week.
 * it will come handy to schedule the alarm based on the days of the week.
 * monday = 1
 * tuesday = 2
 * wednesday = 3
 * thursday = 4
 * friday = 5
 * saturday = 6
 * sunday = 7
 * I am making this singleton class and of every alarm , set the day first by check current day(mon,tues etc) and assigning the same
 */
class Week {
    private var day=0;
    fun setDay(date:Int){
        day=date
    }
    fun incDay(){
        if(day+1==8){
            day=1
        }else{
            day++
        }
    }
    fun getDay():Int{
        return day
    }
    fun toSet(day:Int,alarms: Alarms):Boolean{
        return when (day) {
            1 -> {
                alarms.isMonday
            }
            2 -> {
                alarms.isTuesday
            }
            3 -> {
                alarms.isWednesday
            }
            4 -> {
                alarms.isThursday
            }
            5 -> {
                alarms.isFriday
            }
            6 -> {
                alarms.isSaturday
            }
            else -> {
                alarms.isSunday
            }
        }
    }



}