package br.com.socketchat.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import br.com.socketchat.model.User;
import br.com.socketchat.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final LiveData<User> userLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        userLiveData = userRepository.getSingleUser();
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void insert(final User user) {
        userRepository.insert(user);
    }

    public void update(final User user) {
        userRepository.update(user);
    }

    public void delete(final User user) {
        userRepository.delete(user);
    }
}
