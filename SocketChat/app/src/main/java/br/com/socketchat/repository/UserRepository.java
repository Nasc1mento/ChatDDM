package br.com.socketchat.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import br.com.socketchat.database.AppDatabase;
import br.com.socketchat.database.dao.UserDao;
import br.com.socketchat.model.User;

public class UserRepository {

    private final UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                userDao.insert(user)
        );
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                userDao.update(user)
        );
    }

    public LiveData<User> getById(Integer id) {
        return userDao.getById(id);
    }
}
