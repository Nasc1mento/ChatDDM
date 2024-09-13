package br.com.socketchat.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.socketchat.model.User;
import br.com.socketchat.database.dao.UserDao;


@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile AppDatabase dataBase;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context){
        if (dataBase == null){
            synchronized (Database.class){
                if (dataBase == null){
                    dataBase = Room.
                            databaseBuilder(context.getApplicationContext(),AppDatabase.class,"database").build();
                }
            }
        }
        return dataBase;
    }
}
