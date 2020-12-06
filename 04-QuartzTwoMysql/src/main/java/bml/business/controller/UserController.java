package bml.business.controller;


import bml.business.entity.User;
import bml.business.service.UserService;
import bml.config.BmlResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 边月白
 * @since 2020-11-03
 */
@Controller
public class UserController {

    @Resource
    UserService userService;

    @GetMapping({"/index","/","login"})
    public String index() {
        return "index";
    }

    @GetMapping("admin/user")
    public String user() {
        return "user";
    }

    @GetMapping("admin/quartz")
    public String quartz() {
        return "quartz";
    }

    @ResponseBody
    @PostMapping("/admin/user")
    public BmlResult<Object> userList() {
        Map<String, Object> resultMap = new HashMap<>(100);
        resultMap.put("count",userService.count(null));
        resultMap.put("data",userService.list(null));
        return new BmlResult<>(resultMap,0);
    }


}

