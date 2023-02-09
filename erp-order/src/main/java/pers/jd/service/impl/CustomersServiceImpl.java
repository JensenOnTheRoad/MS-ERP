package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.Customers;
import pers.jd.mapper.CustomersMapper;
import pers.jd.service.ICustomersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-20
 */
@Service
public class CustomersServiceImpl extends ServiceImpl<CustomersMapper, Customers> implements ICustomersService {
    @Resource
    CustomersMapper customersMapper;

    /**
     * 分页查询
     */

    public List<Customers> getAllPaging(Page<Customers> page, QueryWrapper<Customers> queryWrapper) {


        Page<Customers> paging = customersMapper.selectPage(page, queryWrapper);
        List<Customers> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
