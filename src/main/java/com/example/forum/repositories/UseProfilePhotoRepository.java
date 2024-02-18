package com.example.forum.repositories;

import com.example.forum.models.User;
import com.example.forum.models.UserProfilePhoto;

public interface UseProfilePhotoRepository {

    void upload(UserProfilePhoto userProfilePhoto);
    void update(UserProfilePhoto userProfilePhoto);
    void delete(int id);
    UserProfilePhoto get(User user);
    UserProfilePhoto get(int id);


}
