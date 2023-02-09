package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.User;
import pers.jd.entity.UserDetail;
import pers.jd.mapper.UserDetailMapper;
import pers.jd.service.IUserDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户明细表 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-17
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements IUserDetailService {
    @Resource
    UserDetailMapper userDetailMapper;

    public List<UserDetail> getAllPaging(Page<UserDetail> page, QueryWrapper<UserDetail> queryWrapper) {

        Page<UserDetail> paging = userDetailMapper.selectPage(page, queryWrapper);
        List<UserDetail> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
