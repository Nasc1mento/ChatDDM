package br.com.socketchat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import br.com.socketchat.model.User;

@Dao
public interface UserDao extends IDao<User> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> getById(Integer id);

    @Query("SELECT COUNT(*) FROM user")
    LiveData<Integer> getUserCount();

    @Query("SELECT * FROM user LIMIT 1")
    LiveData<User> getSingleUser();
}
