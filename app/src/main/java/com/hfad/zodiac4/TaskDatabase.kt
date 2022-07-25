package com.hfad.zodiac4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.reflect.KParameter

//defines a database
//[Task::class] adds the table defined in the Task data class to the database
//exportSchema tells Room whether to export the database schema into a folder so that you can record its version history
@Database(entities = [Task::class], version=1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase(){
    //defines interface used
    abstract val taskDao: TaskDao

    //creates database and returns an instance for it
    companion object{
        @Volatile
        private var INSTANCE: TaskDatabase? = null


        fun getInstance(context: Context): TaskDatabase{
            return INSTANCE ?:synchronized(this) {
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "tasks_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
private class TaskDatabaseCallback(
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {
override fun onCreate(db: SupportSQLiteDatabase) {
    super.onCreate(db)
    INSTANCE?.let{ database ->
        scope.launch {
            populateDatabase(database.taskDao)
        }
    }

}
suspend fun populateDatabase(taskDao:TaskDao){
    var sign = Task(1,"Aries","Courageous and Energetic.", "Ram","April")
    taskDao.insert(sign)
     sign = Task(2,"Taurus","Known for being reliable, practical, ambitious and sensual.", "Bull","May")
    taskDao.insert(sign)
    sign = Task(3,
        "Gemini",
        "Gemini-born are clever and intellectual.",
        "Twins",
        "June")
    taskDao.insert(sign)
    sign = Task(4,
        "Cancer",
        "Tenacious, loyal and sympathetic.",
        "Crab",
        "July")
    taskDao.insert(sign)
    sign = Task(5,
        "Leo",
        "Warm, action-oriented and driven by the desire to be loved and admired.",
        "Lion",
        "August")
    taskDao.insert(sign)
    sign = Task(6,
        "Virgo",
        "Methodical, meticulous, analytical and mentally astute.",
        "Virgin",
        "September")
    taskDao.insert(sign)
    sign = Task(7,
        "Libra",
        "Librans are famous for maintaining balance and harmony.",
        "Scales",
        "October")
    taskDao.insert(sign)
    sign = Task(8,
        "Scorpio",
        "Strong willed and mysterious.",
        "Scorpion",
        "November")
    taskDao.insert(sign)
    sign = Task(9,
        "Sagittarius",
        "Born adventurers.",
        "Archer",
        "December")
    taskDao.insert(sign)
    sign = Task(10,
        "Capricorn",
        "The most determined sign in the Zodiac.",
        "Goat",
        "January")
    taskDao.insert(sign)
    sign = Task(11,
        "Aquarius",
        "Humanitarians to the core.",
        "Water Bearer",
        "February")
    taskDao.insert(sign)
    sign = Task(12,
        "Pisces",
        "Proverbial dreamers of the Zodiac.",
        "Fish",
        "March")
    taskDao.insert(sign)

}
}
}