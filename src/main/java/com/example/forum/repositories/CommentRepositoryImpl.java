package com.example.forum.repositories;

import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.CommentFilterOptions;
import com.example.forum.models.Comment;
import com.example.forum.models.Post;
import com.example.forum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final SessionFactory sessionFactory;

    @Autowired
    public CommentRepositoryImpl(UserRepository userRepository, PostRepository postRepository, SessionFactory sessionFactory) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Comment> getAll(CommentFilterOptions commentFilterOptions) {
        try (Session session = sessionFactory.openSession()) {

            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            commentFilterOptions.getPost().ifPresent(value -> {

                Post post = postRepository.get(value);
                filters.add("post = :post_id");
                params.put("post_id", post);
            });

            commentFilterOptions.getKeyword().ifPresent(value -> {
                filters.add("comment like :comment");
                params.put("comment", String.format("%%%s%%", value.toLowerCase()));
            });

            commentFilterOptions.getCreatedBy().ifPresent(value -> {
                User user = userRepository.getByUsername(value);
                filters.add("createdBy = :created_by");
                params.put("created_by", user);

            });
            StringBuilder queryString = new StringBuilder("from Comment");
            if (!filters.isEmpty()) {
                queryString
                        .append(" where ")
                        .append(String.join(" and ", filters));
            }

            Query<Comment> query = session.createQuery(queryString.toString(), Comment.class);
            query.setProperties(params);

            List<Comment> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("Comments", "these", "filters");
            }
            return result;
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
        ) {
            Comment comment = session.get(Comment.class, id);
            if (comment == null) {
                throw new EntityNotFoundException("Comment", id);
            }
            return comment;

        }
    }

}
