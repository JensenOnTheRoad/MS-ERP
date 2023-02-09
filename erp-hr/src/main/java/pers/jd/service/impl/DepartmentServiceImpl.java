package pers.jd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jd.entity.Department;
import pers.jd.mapper.DepartmentMapper;
import pers.jd.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author jd
 * @since 2022-03-17
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Resource
    DepartmentMapper departmentMapper;

    /**
     * 分页查询
     */
    public List<Department> getAllPaging(Page<Department> page, QueryWrapper<Department> queryWrapper) {

        Page<Department> paging = departmentMapper.selectPage(page, queryWrapper);
        List<Department> pagingData = paging.getRecords();

        pagingData.forEach(System.out::println);
        return pagingData;
    }
}
