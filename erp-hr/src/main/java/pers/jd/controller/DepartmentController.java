package pers.jd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import pers.jd.entity.Department;
import pers.jd.entity.ResultGetAllPaging;
import pers.jd.entity.ResultSet;
import pers.jd.service.impl.DepartmentServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author jd
 * @since 2022-03-17
 */
@RestController
@Transactional
@CrossOrigin
@RequestMapping("/department")
public class DepartmentController {

    @Resource
    private DepartmentServiceImpl departmentService;


    // region 基本的增删改查操作

    /**
     * 插入/更新-单个数据
     * 前端必须以json格式传入
     *
     * @param m 若m.id为0就为插入新的数据,反之则为更新相应数据。
     * @return resultSet
     */
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    public ResultSet<Object> insertOrUpdate(@RequestBody Department m) {

        ResultSet<Object> rs = new ResultSet<>();

        if (departmentService.saveOrUpdate(m)) {
            rs.setReturnCode(1);
            if (m.getId() == 0)
                rs.setReturnMessage("插入成功！");
            else
                rs.setReturnMessage("更新成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("操作失败");
        }
        return rs;
    }

    /**
     * 删除-根据id
     *
     * @param id
     * @return resultSet
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultSet<Object> delete(@RequestParam(value = "id") long id) {
        ResultSet<Object> rs = new ResultSet<>();
        if (departmentService.removeById(id)) {
            rs.setReturnCode(1);
            rs.setReturnMessage("删除成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("操作失败");
        }
        return rs;
    }

    /**
     * 查询-单个数据
     *
     * @param id
     * @return data
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public Department getOne(@RequestParam(value = "id") long id) {
        return departmentService.getById(id);
    }

    /**
     * 查询-全部列表
     *
     * @return data
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Department> getAll() {
        return departmentService.list();
    }

    // endregion


    // region 需要使用QueryWrapper

    /**
     * 查询-数据列表-分页
     *
     * @param pageIndex
     * @param pageSize
     * @return data
     */
    @RequestMapping(value = "/getAllPaging", method = RequestMethod.GET)
    public ResultGetAllPaging getAllPaging(@RequestParam(value = "pageIndex", defaultValue = "0")
                                                   long pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "20")
                                                   long pageSize) {

        ResultGetAllPaging result = new ResultGetAllPaging();

        Page<Department> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("price", 312312);

        List<Department> dataList = departmentService.getAllPaging(page, queryWrapper);
        long countTotal = departmentService.count();
        result.setDataList(dataList);
        result.setCountTotal(countTotal);
        result.setCountCurrent(dataList.size());
        return result;
    }

    /**
     * 模糊查询
     *
     * @return
     */
    @RequestMapping(value = "/fuzzySearch", method = RequestMethod.GET)
    public ResultGetAllPaging fuzzySearch(@RequestParam(value = "") String searchKey) {
        ResultGetAllPaging result = new ResultGetAllPaging();

        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        // 条件拼接
        queryWrapper.like("id", searchKey).or().like("name", searchKey);
        List<Department> dataList = departmentService.list(queryWrapper);

        result.setDataList(dataList);
        result.setCountTotal(dataList.size());
        result.setCountCurrent(dataList.size());
        return result;
    }

    /**
     * @param m_list
     * @return
     */
    @RequestMapping("/insertOrUpdateBatch")
    public ResultSet<Object> insertOrUpdateBatch(@RequestBody List<Department> m_list) {

        ResultSet<Object> resultSet = new ResultSet<>();

        if (departmentService.saveOrUpdateBatch(m_list)) {
            resultSet.setReturnCode(1);
            resultSet.setReturnMessage("操作成功");
        } else {
            resultSet.setReturnCode(0);
            resultSet.setReturnMessage("操作失败");
        }

        return resultSet;

    }

    //endregion
}
