package com.example.cse110_project.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RouteEntry.class}, version = 2, exportSchema = false)
public abstract class RouteEntryDatabase extends RoomDatabase {
    public static final String DB_NAME = "routes.db";

    public static RouteEntryDatabase INSTANCE = null;
    public static boolean DEBUG_DATABASE = false;
    public abstract RouteEntryDAO getRouteEntryDAO();

    //Get permanent database.
    public static RouteEntryDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            if(DEBUG_DATABASE) {
                INSTANCE = Room.inMemoryDatabaseBuilder(context, RouteEntryDatabase.class)
                        .allowMainThreadQueries().build();
            } else {
                INSTANCE = Room.databaseBuilder(context, RouteEntryDatabase.class,DB_NAME)
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }

    public static void closeDateBase() {
        INSTANCE.close();
        INSTANCE = null;
    }


}
