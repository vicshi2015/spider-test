package spider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author shiwei
 * @version $$Id: HelloController, V 0.1 2017/1/13 22:35 shiwei Exp $$
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = true, defaultValue = "World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "hello";
    }
}
