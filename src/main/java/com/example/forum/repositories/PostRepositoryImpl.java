package com.example.forum.repositories;

import com.example.forum.exceptions.EntityNotFoundException;
import com.example.forum.filters.PostsFilterOptions;
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
public class PostRepositoryImpl implements PostRepository {
    private final SessionFactory sessionFactory;

    private final UserRepository userRepository;

    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory, UserRepository userRepository) {
        this.sessionFactory = sessionFactory;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> getAll(PostsFilterOptions postsFilterOptions) {
        try (Session session = sessionFactory.openSession()) {
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            postsFilterOptions.getTitle().ifPresent(value -> {
                filters.add("title like :title");
                params.put("title", String.format("%%%s%%", value));
            });

            postsFilterOptions.getKeyword().ifPresent(value -> {
                filters.add("content like :content");
                params.put("content", String.format("%%%s%%",value));
            });

            postsFilterOptions.getCreatedBy().ifPresent(value -> {
                if(value.isEmpty()){
                    return;
                }
                try {
                    User user = userRepository.getByUsername(value);
                    filters.add("createdBy = :created_by");
                    params.put("created_by", user);
                    //TODO check is this the right exception
                } catch (EntityNotFoundException e){
                    throw new EntityNotFoundException("Posts","creator",value);
                }
            });

            StringBuilder queryString = new StringBuilder("from Post");
            if (!filters.isEmpty()) {
                queryString
                        .append(" where ")
                        .append(String.join(" and ", filters));
            }
            queryString.append(generateOrderBy(postsFilterOptions));

            Query<Post> query = session.createQuery(queryString.toString(), Post.class);
            query.setProperties(params);

            List<Post> result = query.list();
            if (result.isEmpty()){
                throw new EntityNotFoundException("Posts","these","filters");
            }
            return result;
        }
    }

    @Override
    public List<Post> getTopTenCommented() {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery(
                    "SELECT c.post FROM Comment c group by c.post Order by count(c.post) DESC LIMIT 10"
                    , Post.class);
            List<Post> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("posts");
            }

            return result;
        }
    }


    @Override
    public List<Post> getTenMostRecent() {
        try (Session session = sessionFactory.openSession()){
            Query<Post> query = session.createQuery("from Post order by dateAndTimeOfCreation desc LIMIT 10", Post.class);
            List<Post> result = query.list();
            if (result.isEmpty()){
                throw new EntityNotFoundException("posts");
            }
            return result;
        }
    }

    @Override
    public Post get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.get(Post.class, id);
            if (post == null) {
                throw new EntityNotFoundException("Post", id);
            }
            return post;
        }
    }


    @Override
    public Post get(String title) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post where title = :title", Post.class);
            query.setParameter("title", title);

            List<Post> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("Post", "title", title);
            }

            return result.get(0);
        }
    }


    @Override
    public void create(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
            Post postToDelete = get(id);
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.remove(postToDelete);
                session.getTransaction().commit();
        }
    }



    private String generateOrderBy(PostsFilterOptions postFilterOptions) {
        if (postFilterOptions.getSortBy().isEmpty()) {
            return "";
        }

        String orderBy = "";
        switch (postFilterOptions.getSortBy().get()) {
            case "title":
                orderBy = "title";
                break;
            case "createdBy":
                orderBy = "createdBy";
                break;
            default:
                return "";
        }

        orderBy = String.format(" order by %s", orderBy);

        if (postFilterOptions.getSortOrder().isPresent() && postFilterOptions.getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format("%s desc", orderBy);
        }

        return orderBy;
    }
}
