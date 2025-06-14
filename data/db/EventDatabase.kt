package com.mohsin.eventcompanion.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SessionEntity::class, SpeakerEntity::class], version = 1)
abstract class EventDatabase : RoomDatabase() {

    abstract fun sessionDao(): SessionDao
    abstract fun speakerDao(): SpeakerDao

    companion object {
        @Volatile private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
