package br.com.socketchat.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface IDao <T> {

    @Insert
    void insert(T t);

    @Update
    void update(T t);

    @Delete
    void delete(T t);

}
