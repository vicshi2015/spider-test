package spider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spider.mapper.UserMapper;
import spider.model.User;

/**
 * Created by shiwei on 2017/1/11.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserInfo() {
        User user = userMapper.findUserInfo();

        return user;
    }
}
