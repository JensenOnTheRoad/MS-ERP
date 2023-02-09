package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import pers.jd.entity.Material;
import pers.jd.entity.PurchaseSub;
import pers.jd.entity.PurchaseSub;
import pers.jd.mapper.PurchaseSubMapper;
import pers.jd.mapper.PurchaseSubMapper;
import pers.jd.service.IPurchaseSubService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 采购子单 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Service
public class PurchaseSubServiceImpl extends ServiceImpl<PurchaseSubMapper, PurchaseSub> implements IPurchaseSubService {

    @Resource
    PurchaseSubMapper purchaseSubMapper;

    /**
     * 分页查询
     */
    public List<PurchaseSub> getAllPaging(Page<PurchaseSub> page, QueryWrapper<PurchaseSub> queryWrapper) {

        Page<PurchaseSub> paging = purchaseSubMapper.selectPage(page, queryWrapper);
        List<PurchaseSub> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }

    /**
     * 根据主单查询副单集
     */
    public List<PurchaseSub> getSubsById(Integer id) {
        QueryWrapper<PurchaseSub> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*").eq("purchase_main_id", id);
        List<PurchaseSub> result = purchaseSubMapper.selectList(queryWrapper);
        return result;
    }
}
