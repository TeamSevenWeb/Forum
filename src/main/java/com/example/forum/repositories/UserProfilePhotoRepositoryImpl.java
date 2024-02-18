package com.example.forum.repositories;

import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Reaction;
import com.example.forum.models.User;
import com.example.forum.models.UserProfilePhoto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserProfilePhotoRepositoryImpl implements UseProfilePhotoRepository{

    private final SessionFactory sessionFactory;
    @Autowired
    public UserProfilePhotoRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void upload(UserProfilePhoto userProfilePhoto) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(userProfilePhoto);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(UserProfilePhoto userProfilePhoto) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(userProfilePhoto);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(get(id));
            session.getTransaction().commit();
        }
    }

    @Override
    public UserProfilePhoto get(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<UserProfilePhoto> query = session.createQuery(
                    "from UserProfilePhoto where user = :user_id", UserProfilePhoto.class
            );
            query.setParameter("user_id", user);

            List<UserProfilePhoto> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("photos");
            }

            return result.get(0);
    }
}
    @Override
    public UserProfilePhoto get(int id) {
        try (
                Session session = sessionFactory.openSession()
        ){
            UserProfilePhoto userProfilePhoto = session.get(UserProfilePhoto.class,id);
            if (userProfilePhoto==null){
                throw new EntityNotFoundException("Photo",id);
            }
            return userProfilePhoto;
    }}}