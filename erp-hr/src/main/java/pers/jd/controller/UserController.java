package pers.jd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jetbrains.annotations.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import pers.jd.entity.User;
import pers.jd.entity.ResultGetAllPaging;
import pers.jd.entity.ResultSet;
import pers.jd.entity.vo.UserVO;
import pers.jd.service.impl.DepartmentServiceImpl;
import pers.jd.service.impl.UserServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jd
 * @since 2022-03-17
 */
@RestController
@Transactional
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    UserServiceImpl userService;
    @Resource
    DepartmentServiceImpl departmentService;
    // region 基本的增删改查操作

    /**
     * 插入/更新-单个数据
     * 前端必须以json格式传入
     *
     * @param m 若m.id为0就为插入新的数据,反之则为更新相应数据。
     * @return resultSet
     */
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    public ResultSet<Object> insertOrUpdate(@RequestBody User m) {

        ResultSet<Object> rs = new ResultSet<>();

        if (userService.saveOrUpdate(m)) {
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
        if (userService.removeById(id)) {
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
    public User getOne(@RequestParam(value = "id") long id) {
        return userService.getById(id);
    }

    /**
     * 查询-全部列表
     *
     * @return data
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<User> getAll() {
        return userService.list();
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

        Page<User> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("price", 312312);

        List<User> dataList = userService.getAllPaging(page, queryWrapper);
        long countTotal = userService.count();
        result.setDataList(transToVo(dataList));
        result.setCountTotal(countTotal);
        result.setCountCurrent(dataList.size());
        return result;
    }

    private @Nullable List transToVo(List<User> dataList) {
        List<UserVO> list = new ArrayList<>();
        for (User u : dataList) {
            UserVO userVO = new UserVO();
            userVO.setId(u.getId());
            userVO.setUsername(u.getUsername());
            userVO.setDepartmentId(departmentService.getById(u.getDepartmentId()).getName());
            userVO.setLastloginTime(u.getLastloginTime());
            userVO.setRegisterTime(u.getRegisterTime());
            userVO.setPassword(u.getPassword());

            if (!"admin".equals(u.getUsername()))
                list.add(userVO);
        }
        return list;
    }

    /**
     * 模糊查询
     *
     * @return
     */
    @RequestMapping(value = "/fuzzySearch", method = RequestMethod.GET)
    public ResultGetAllPaging fuzzySearch(@RequestParam(value = "") String searchKey) {
        ResultGetAllPaging result = new ResultGetAllPaging();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 条件拼接
        queryWrapper.like("id", searchKey).or().like("name", searchKey);
        List<User> dataList = userService.list(queryWrapper);

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
    public ResultSet<Object> insertOrUpdateBatch(@RequestBody List<User> m_list) {

        ResultSet<Object> resultSet = new ResultSet<>();

        if (userService.saveOrUpdateBatch(m_list)) {
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
