package com.example.forum.repositories;

import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.exceptions.UserDontHaveAnyException;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where id = :id", User.class);
            query.setParameter("id", id);

            List<User> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", id);
            }

            return result.get(0);
        }
    }

    @Override
    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);

            List<User> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", "username", username);
            }

            return result.get(0);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);

            List<User> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", "email", email);
            }

            return result.get(0);
        }
    }

    @Override
    public User getByFirstName(String firstName) {
        return null;
    }

    @Override
    public List<Post> getUserPosts(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where createdBy = :created_by", Post.class);
            query.setParameter("created_by", user);

            List<Post> result = query.list();
            if (result.isEmpty()) {
                throw new UserDontHaveAnyException("posts");
            }

            return result;
        }
    }

    @Override
    public List<Comment> getUserComments(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<Comment> query = session.createQuery("from Comment where createdBy = :created_by", Comment.class);
            query.setParameter("created_by", user);

            List<Comment> result = query.list();
            if (result.isEmpty()) {
                throw new UserDontHaveAnyException("comments");
            }

            return result;
        }
    }

    @Override
    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void setAdmin() {

    }

    @Override
    public User setBlocked(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public User setUnblocked(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
        return user;
    }
}
