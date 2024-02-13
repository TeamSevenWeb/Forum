package com.example.forum.repositories;

import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.models.Reaction;
import com.example.forum.models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository{
    private final SessionFactory sessionFactory;

    public TagRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void create(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(tag);
            session.getTransaction().commit();
        }
    }

    @Override
    public Tag update(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(tag);
            session.getTransaction().commit();
        }
        return tag;
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
    public Tag get(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tag> query = session.createQuery("from Tag where name = :name", Tag.class);
            query.setParameter("name", name);

            List<Tag> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Tag", "name", name);
            }

            return result.get(0);
        }
    }

    @Override
    public Tag get(int id) {
        try (
                Session session = sessionFactory.openSession()
        ){
            Tag tag = session.get(Tag.class,id);
            if (tag==null){
                throw new EntityNotFoundException("Reaction",id);
            }
            return tag;

        }
    }
}
