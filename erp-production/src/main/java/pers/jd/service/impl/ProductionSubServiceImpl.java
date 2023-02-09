package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.ProductionSub;
import pers.jd.entity.PurchaseSub;
import pers.jd.mapper.ProductionSubMapper;
import pers.jd.service.IProductionSubService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 生产子单 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Service
public class ProductionSubServiceImpl extends ServiceImpl<ProductionSubMapper, ProductionSub> implements IProductionSubService {

    @Resource
    ProductionSubMapper productionSubMapper;

    /**
     * 分页查询
     */
    public List<ProductionSub> getAllPaging(Page<ProductionSub> page, QueryWrapper<ProductionSub> queryWrapper) {

        Page<ProductionSub> paging = productionSubMapper.selectPage(page, queryWrapper);
        List<ProductionSub> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }

    /**
     * 分页查询
     */
    public List<ProductionSub> getSubsById(Integer id) {
        QueryWrapper<ProductionSub> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*").eq("production_main_id", id);
        List<ProductionSub> result = productionSubMapper.selectList(queryWrapper);
        return result;
    }

}
