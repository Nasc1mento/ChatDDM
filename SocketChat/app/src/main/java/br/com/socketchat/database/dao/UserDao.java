package br.com.socketchat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import br.com.socketchat.model.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(User user);

    @Update
    public void update(User user);

    @Query("SELECT * FROM user WHERE id = :id")
    public LiveData<User> getById(Integer id);
}
