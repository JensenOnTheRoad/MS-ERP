package pers.jd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.Objects;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pers.jd.entity.ResultSet;
import pers.jd.entity.User;
import pers.jd.entity.dto.LoginDTO;
import pers.jd.service.impl.UserServiceImpl;

@RestController
@CrossOrigin
@RequestMapping(value = "admin")
public class AdminController {

  @Resource
  UserServiceImpl userService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResultSet<Object> login(@RequestBody final LoginDTO login) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    // 条件拼接
    queryWrapper.eq("username", login.username).eq("password", login.password);
    User user = userService.getOne(queryWrapper);

    ResultSet<Object> resultSet = new ResultSet<>();

    if (Objects.nonNull(user)) {
      resultSet.setReturnCode(1);
      resultSet.setReturnMessage("操作成功");
      user.setPassword("");
      resultSet.setData(user);
    } else {
      resultSet.setReturnCode(0);
      resultSet.setReturnMessage("操作失败");
    }

    return resultSet;
  }


}
