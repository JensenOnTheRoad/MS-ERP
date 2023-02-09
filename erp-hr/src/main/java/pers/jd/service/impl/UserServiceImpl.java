package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.User;
import pers.jd.mapper.UserMapper;
import pers.jd.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    UserMapper userMapper;

    /**
     * 分页查询
     */
    public List<User> getAllPaging(Page<User> page, QueryWrapper<User> queryWrapper) {


        Page<User> paging = userMapper.selectPage(page, queryWrapper);
        List<User> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }

}
