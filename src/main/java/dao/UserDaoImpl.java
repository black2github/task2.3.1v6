package dao;

import model.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDaoImpl implements UserDao {
    private static final Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<User> listAll() {
        log.debug("findAll: <- ");
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> list(int count) {
        log.debug("find: <- " + count);
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        return query.getResultList().stream().limit(count).collect(Collectors.toList());
    }

    @Override
    public User create(User user) {
        log.debug("add: <- " + user);
        em.persist(user);
        log.debug("add: -> " + user);
        return user;
    }

    public User update(User user) {
        log.debug("update: <- " + user);
        User usr = em.merge(user);
        log.debug("update: -> " + usr);
        return usr;
    }

    @Override
    public void delete(Long id) {
        log.debug("delete: <- " + id);
        int cnt = em.createQuery("delete from User where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        String status = (cnt == 1) ? "deleted successfully" : "not found";
        log.debug("delete: -> User with id=" + id + " " + status);
    }

    @Transactional(readOnly = true)
    @Override
    public User find(Long id) {
        User usr;

        log.debug("find: <- " + id);
        List<User> usrs = em.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id)
                .getResultList();
        usr = usrs.isEmpty() ? null : usrs.get(0);
        log.debug("find: -> " + usr);
        return usr;
    }
}
