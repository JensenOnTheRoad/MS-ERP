package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.ProductionMain;
import pers.jd.mapper.ProductionMainMapper;
import pers.jd.service.IProductionMainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 生产主单 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Service
public class ProductionMainServiceImpl extends ServiceImpl<ProductionMainMapper, ProductionMain> implements IProductionMainService {
    @Resource
    ProductionMainMapper productionMainMapper;

    /**
     * 分页查询
     */
    public List<ProductionMain> getAllPaging(Page<ProductionMain> page, QueryWrapper<ProductionMain> queryWrapper) {

        Page<ProductionMain> paging = productionMainMapper.selectPage(page, queryWrapper);
        List<ProductionMain> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
