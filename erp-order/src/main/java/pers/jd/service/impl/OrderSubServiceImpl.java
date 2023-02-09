package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.OrderSub;
import pers.jd.mapper.OrderSubMapper;
import pers.jd.service.IOrderSubService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 子订单 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-20
 */
@Service
public class OrderSubServiceImpl extends ServiceImpl<OrderSubMapper, OrderSub> implements IOrderSubService {

    @Resource
    OrderSubMapper orderSubMapper;

    /**
     * 分页查询
     */
    public List<OrderSub> getAllPaging(Page<OrderSub> page, QueryWrapper<OrderSub> queryWrapper) {

        Page<OrderSub> paging = orderSubMapper.selectPage(page, queryWrapper);
        List<OrderSub> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }

    public List<OrderSub> getSubsById(Integer id) {
        QueryWrapper<OrderSub> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*").eq("order_main_id", id);
        List<OrderSub> result = orderSubMapper.selectList(queryWrapper);
        return result;
    }
}
