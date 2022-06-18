package com.example.coviddefender.db.covidstatus;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CovidStatus.class}, version = 1, exportSchema = false)
public abstract class CovidStatusRoomDB extends RoomDatabase {
    private static CovidStatusRoomDB INSTANCE;

    private static final int NUMBER_OF_THREADS = 1;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract CovidStatusDao covidStatusDao();

    // Singleton design pattern
    public static CovidStatusRoomDB getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (CovidStatusRoomDB.class){
                if(INSTANCE == null){
                    //instantiate and the SQLite database engine running
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CovidStatusRoomDB.class, "covid_status_table")
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Pre-populate the database table with some dummy
                CovidStatusDao dao = INSTANCE.INSTANCE.covidStatusDao();
                dao.deleteAll();

                CovidStatus covidStatus = new CovidStatus("Jun 18, 2022, 4:26:20 PM", "Low", "Red Zone", "Yes");
                dao.insert(covidStatus);
            });
        }
    };
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
