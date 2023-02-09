package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.Warehouse;
import pers.jd.mapper.WarehouseMapper;
import pers.jd.service.IWarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-13
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

    @Resource
    WarehouseMapper warehouseMapper;

    /**
     * 分页查询
     */
    public List<Warehouse> getAllPaging(Page<Warehouse> page, QueryWrapper<Warehouse> queryWrapper) {

        Page<Warehouse> paging = warehouseMapper.selectPage(page, queryWrapper);
        List<Warehouse> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
