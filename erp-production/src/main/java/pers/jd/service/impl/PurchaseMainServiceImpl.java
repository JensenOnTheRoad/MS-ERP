package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.PurchaseMain;
import pers.jd.entity.PurchaseMain;
import pers.jd.mapper.PurchaseMainMapper;
import pers.jd.mapper.PurchaseMainMapper;
import pers.jd.service.IPurchaseMainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 采购主单 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Service
public class PurchaseMainServiceImpl extends ServiceImpl<PurchaseMainMapper, PurchaseMain> implements IPurchaseMainService {

    @Resource
    PurchaseMainMapper purchaseMainService;

    /**
     * 分页查询
     */
    public List<PurchaseMain> getAllPaging(Page<PurchaseMain> page, QueryWrapper<PurchaseMain> queryWrapper) {

        Page<PurchaseMain> paging = purchaseMainService.selectPage(page, queryWrapper);
        List<PurchaseMain> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
