package com.example.forum.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profile_photo", schema = "forum", catalog = "")
public class UserProfilePhoto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "profile_photo_id")
    private int profilePhotoId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Basic
    @Column(name = "profile_photo")
    private String profilePhoto;

    public int getProfilePhotoId() {
        return profilePhotoId;
    }

    public void setProfilePhotoId(int profilePhotoId) {
        this.profilePhotoId = profilePhotoId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfilePhoto that = (UserProfilePhoto) o;

        if (profilePhotoId != that.profilePhotoId) return false;
        if (user != that.user) return false;
        if (profilePhoto != null ? !profilePhoto.equals(that.profilePhoto) : that.profilePhoto != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = profilePhotoId;
        result = 31 * result + (profilePhoto != null ? profilePhoto.hashCode() : 0);
        return result;
    }
}
