package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao ) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listAll() {
        return userDao.listAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> list(int count) {
        log.debug("find: <- " + count);
        return userDao.list(count);
    }

    @Override
    public User create(User user) {
        log.debug("add: <- " + user);
        userDao.create(user);
        log.debug("add: -> " + user);
        return user;
    }

    public User update(User user) {
        log.debug("update: <- " + user);

        User usr = userDao.update(user);
        log.debug("update: -> " + usr);
        return usr;
    }

    @Override
    public void delete(User user) {
        log.debug("delete: <- " + user);
        if (user !=null) {
            userDao.delete(user.getId());
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("delete: <- " + id);
        userDao.delete(id);
    }

    @Override
    public User update(long id, User user) {
        log.debug("update: <- id=" + id + ", user=" + user);
        User usr = find(id);
        if (usr != null) {
            usr.setFirstName(user.getFirstName());
            usr.setSecondName(user.getSecondName());
            usr.setAge(user.getAge());
            return userDao.update(usr);
        } else {
            log.warn("update: User with id=" + id + " not found");
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public User find(Long id) {
        log.debug("find: <- " + id);
        User usr = userDao.find(id);
        log.debug("find: -> " + usr);
        return usr;
    }
}
