package ru.jm.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.jm.spring.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOImplementation implements UserDAO {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<User> getAllUsers() {

        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("from User", User.class);
        List<User> allUsers = query.getResultList();

        return allUsers;
    }

    public User getUserById(int id) {

        EntityManager em = entityManagerFactory.createEntityManager();
        User user = em.find(User.class, id);
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        EntityManager em = entityManagerFactory.createEntityManager();
        User user = em.find(User.class, username);

        return user;
    }


    public void save(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        EntityManager em = entityManagerFactory.createEntityManager();

        User userToBeUpdated = em.find(User.class, id);

        em.getTransaction().begin();
        userToBeUpdated.setFirstName(updatedUser.getFirstName());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setAge(updatedUser.getAge());
        //userToBeUpdated.getUsername();
        userToBeUpdated.getPassword();
        em.getTransaction().commit();

    }

    public void deleteUser(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        TypedQuery<User> query = session.createQuery("delete from User " + "where  id =:userId");
//        query.setParameter("userId", id);
//        query.executeUpdate();
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();

        //users.removeIf(p -> p.getId() == id);
    }

}
