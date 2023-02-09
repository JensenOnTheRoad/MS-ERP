package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.Material;
import pers.jd.mapper.MaterialMapper;
import pers.jd.service.IMaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {
    @Resource
    MaterialMapper materialMapper;

    /**
     * 分页查询
     */
    public List<Material> getAllPaging(Page<Material> page, QueryWrapper<Material> queryWrapper) {

        Page<Material> paging = materialMapper.selectPage(page, queryWrapper);
        List<Material> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
