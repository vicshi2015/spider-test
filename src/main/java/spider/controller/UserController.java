package spider.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import spider.model.User;
import spider.service.UserService;



/**
 * Created by shiwei on 2017/1/11.
 */
@Controller
public class UserController {

    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public User getUserInfo() {
        User user = userService.getUserInfo();

        if(user != null)
        {
            System.out.println("user.getName():" + user.getName());
            logger.info("user.getAge():" + user.getAge());
        }
        return user;
    }
}
