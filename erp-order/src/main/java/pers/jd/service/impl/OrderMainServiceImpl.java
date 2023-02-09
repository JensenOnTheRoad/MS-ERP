package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.OrderMain;
import pers.jd.mapper.OrderMainMapper;
import pers.jd.service.IOrderMainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 主订单 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-20
 */
@Service
public class OrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements IOrderMainService {
    @Resource
    OrderMainMapper orderMainMapper;

    /**
     * 分页查询
     */
    public List<OrderMain> getAllPaging(Page<OrderMain> page, QueryWrapper<OrderMain> queryWrapper) {


        Page<OrderMain> paging = orderMainMapper.selectPage(page, queryWrapper);
        List<OrderMain> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }

}
