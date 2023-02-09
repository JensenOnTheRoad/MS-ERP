package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.Financial;
import pers.jd.entity.TypeIo;
import pers.jd.mapper.TypeIoMapper;
import pers.jd.service.ITypeIoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收入支出类型 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-16
 */
@Service
public class TypeIoServiceImpl extends ServiceImpl<TypeIoMapper, TypeIo> implements ITypeIoService {
    @Resource
    TypeIoMapper typeIoMapper;

    public List<TypeIo> getAllPaging(Page<TypeIo> page, QueryWrapper<TypeIo> queryWrapper) {
        Page<TypeIo> paging = typeIoMapper.selectPage(page, queryWrapper);
        List<TypeIo> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
