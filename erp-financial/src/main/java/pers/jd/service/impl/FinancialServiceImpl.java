package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.Financial;
import pers.jd.mapper.FinancialMapper;
import pers.jd.service.IFinancialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 财务流水单 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-16
 */
@Service
public class FinancialServiceImpl extends ServiceImpl<FinancialMapper, Financial> implements IFinancialService {
    @Resource
    FinancialMapper financialMapper;

    public List<Financial> getAllPaging(Page<Financial> page, QueryWrapper<Financial> queryWrapper) {
        Page<Financial> paging = financialMapper.selectPage(page, queryWrapper);
        List<Financial> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
