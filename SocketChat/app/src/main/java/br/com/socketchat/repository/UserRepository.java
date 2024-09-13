package br.com.socketchat.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

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

    public void delete(User user) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                userDao.delete(user)
        );
    }

    public LiveData<Boolean> hasUsers() {
        return Transformations.map(userDao.getUserCount(), count -> count > 0);
    }

    public LiveData<User> getSingleUser() {
        return userDao.getSingleUser();
    }


    public LiveData<User> getById(Integer id) {
        return userDao.getById(id);
    }
}
