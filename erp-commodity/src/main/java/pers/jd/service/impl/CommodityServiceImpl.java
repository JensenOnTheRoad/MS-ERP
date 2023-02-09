package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.Commodity;
import pers.jd.mapper.CommodityMapper;
import pers.jd.service.ICommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-16
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {
    @Resource
    CommodityMapper commodityMapper;

    public List<Commodity> getAllPaging(Page<Commodity> page, QueryWrapper<Commodity> queryWrapper) {
        Page<Commodity> paging = commodityMapper.selectPage(page, queryWrapper);
        List<Commodity> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;

    }
}
