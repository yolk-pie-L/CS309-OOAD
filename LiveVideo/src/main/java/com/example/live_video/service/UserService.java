package com.example.live_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.live_video.entity.User;
import com.example.live_video.entity.UserType;
import com.example.live_video.exception.MyException;
import com.example.live_video.exception.SQLMailConflictException;
import com.example.live_video.exception.SQLUsernameConflictException;
import com.example.live_video.exception.SQLUserNotFoundException;

public interface UserService extends IService<User> {

    /**
     * Register the user into database. If success, it will return true.
     *
     * @param user
     * @return boolean
     * @throws MyException
     * @throws SQLUsernameConflictException If there's another user has the same name, it
     *                                      will throw SQLUsernameConflictException.
     * @throws SQLMailConflictException     If there's another user has the same mail, it will throw
     *                                      SQLMailConflictException
     */
    public Boolean register(User user) throws MyException;

    /**
     * Return true if the user has the same password with the record in database
     *
     * @param userName
     * @param password
     * @return boolean
     * @throws MyException
     * @throws SQLUserNotFoundException
     */
    public Boolean compareUserPassword(String userName, String password) throws SQLUserNotFoundException;

    /**
     * @param userName
     * @return
     * @throws MyException
     * @throws SQLUserNotFoundException
     */
    public Boolean removeUser(String userName) throws SQLUserNotFoundException;

    /**
     * Get the id of the user in the database. If the user not in the database, it will return null
     * @param userName name of the user
     * @return userId
     */
    public Long getUserId(String userName);

    /**
     * Get all information about the user through userName
     * @param userName
     * @return User instance
     */
    public User getUser(String userName);

    public UserType getUserType(String userName);

    public Long getUserAccount(String userName);

    public Boolean updateUser(User user);

    public User verifyUser(String mail);

    /**
     *
     * @param userName, password
     * @return the type of user: Student, Teacher, Administrator. Return null if no user exists.
     * @throws MyException
     */
    public String login(String userName, String password) throws MyException;
}
