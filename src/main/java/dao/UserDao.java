package dao;

import model.User;

import java.util.List;

public interface UserDao {
    List<User> list(int count);
    List<User> listAll();
    User create(User user);
    User update(User user);
    void delete(Long id);
    User find(Long id);
}
