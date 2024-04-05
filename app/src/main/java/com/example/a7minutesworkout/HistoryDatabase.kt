package com.example.a7minutesworkout

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {
    // now we've to connect the database with dao so we're connecting our database with our dao interface
    abstract fun historyDao(): HistoryDao

    companion object{
        @Volatile
        // volatile, meaning that writes to this field are immediately made visible to other threads.
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null ){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}