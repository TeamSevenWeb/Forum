package com.example.forum.repositories;

import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final SessionFactory sessionFactory;
    @Autowired
    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Comment> getAll(){
        try (
                Session session = sessionFactory.openSession()
        ){
            Query<Comment> query = session.createQuery("from Comment",Comment.class);
            if (query.list().isEmpty()){
                throw new EntityNotFoundException("comments");
            }
            return query.list();
        }
    }
    @Override
    public void create(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(comment);
            session.getTransaction().commit();
        }
    }

    @Override
    public Comment update(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(comment);
            session.getTransaction().commit();
        }
        return comment;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(getById(id));
            session.getTransaction().commit();
        }
    }

@Override
    public Comment getById(int id) {
        try (
                Session session = sessionFactory.openSession()
        ){
            Comment comment = session.get(Comment.class,id);
            if (comment==null){
                throw new EntityNotFoundException("Comment",id);
            }
            return comment;

        }
    }
}
