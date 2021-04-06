package ru.jm.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.jm.spring.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<User> getAllUsers() {

        EntityManager em = entityManagerFactory.createEntityManager();
        //TypedQuery<User> query = em.createQuery("from User", User.class);
        TypedQuery<User> query = em.createQuery("select distinct u from User u join fetch u.roles", User.class);
        List<User> allUsers = query.getResultList();

        return allUsers;
    }

    public User getUserById(Long id) {

        EntityManager em = entityManagerFactory.createEntityManager();
        User user = em.find(User.class, id);
        return user;
    }

    @Override
    public User getUserByUsername(String userName) {

        EntityManager em = entityManagerFactory.createEntityManager();
        //TypedQuery<User> query = em.createQuery("from User user where user.username = :userName", User.class);
        TypedQuery<User> query = em.createQuery("select user from User user join fetch user.roles where user.username = :userName", User.class);
        query.setParameter("userName", userName);

        Optional<User> userFromDbByUserName = Optional.of(query.getSingleResult());

        return userFromDbByUserName.orElse(new User());

        //return query.getSingleResult();
    }


    public void save(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void updateUser(Long id, User updatedUser) {
        EntityManager em = entityManagerFactory.createEntityManager();

        User userToBeUpdated = em.find(User.class, id);

        em.getTransaction().begin();

        userToBeUpdated.setFirstName(updatedUser.getFirstName());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setPassword(updatedUser.getPassword());

        em.getTransaction().commit();
    }

    public void deleteUser(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();

    }

}
