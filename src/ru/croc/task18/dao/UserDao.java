package ru.croc.task18.dao;

import ru.croc.task18.shopelements.User;

public interface UserDao {
    int findUserId(String userName);
    User findUser(String userName);
}
