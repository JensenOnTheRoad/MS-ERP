package pers.jd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import pers.jd.entity.Financial;
import pers.jd.entity.ResultGetAllPaging;
import pers.jd.entity.ResultSet;
import pers.jd.entity.vo.FinancialVO;
import pers.jd.feignService.FeignHrService;
import pers.jd.service.impl.FinancialServiceImpl;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 财务流水单 前端控制器
 * </p>
 *
 * @author jd
 * @since 2022-03-16
 */
@RestController
@Transactional
@CrossOrigin
@RequestMapping("/financial")
public class FinancialController {
    @Resource
    FinancialServiceImpl financialService;

    @Resource
    FeignHrService feignHrService;

    // region
    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    public ResultSet<Object> audit(@RequestParam(value = "id") long id,
                                   @RequestParam(value = "auditorId") int auditorId) {
        Financial financial = financialService.getById(id);
        financial.setAuditorId(auditorId);
        financial.setAuditStatus(1);
        financial.setAuditTime(LocalDateTime.now());

        ResultSet<Object> rs = new ResultSet<>();
        if (financialService.saveOrUpdate(financial)) {
            rs.setReturnCode(1);
            rs.setReturnMessage("操作成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("操作失败");
        }
        return rs;
    }
    // endregion
    // region 基本的增删改查操作

    /**
     * 插入/更新-单个数据
     * 前端必须以json格式传入
     *
     * @param m 若m.id为0就为插入新的数据,反之则为更新相应数据。
     * @return resultSet
     */
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    public ResultSet<Object> insertOrUpdate(@RequestBody Financial m) {

        ResultSet<Object> rs = new ResultSet<>();

        if (financialService.saveOrUpdate(m)) {
            rs.setReturnCode(1);
            if (m.getId() == 0) {
                rs.setReturnMessage("插入成功！");
            } else {
                rs.setReturnMessage("更新成功！");
            }
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
        if (financialService.removeById(id)) {
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
    public Financial getOne(@RequestParam(value = "id") long id) {
        return financialService.getById(id);
    }

    /**
     * 查询-全部列表
     *
     * @return data
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Financial> getAll() {
        return financialService.list();
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

        Page<Financial> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<Financial> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("price", 312312);

        List<Financial> dataList = financialService.getAllPaging(page, queryWrapper);
        long countTotal = financialService.count();
        result.setDataList(TransToVO(dataList));
        result.setCountTotal(countTotal);
        result.setCountCurrent(dataList.size());
        return result;
    }

    private List<FinancialVO> TransToVO(List<Financial> dataList) {

        ArrayList<FinancialVO> list = new ArrayList<>();
        for (Financial x : dataList) {
            FinancialVO temp = new FinancialVO();
            String username = "";
            if (x.getAuditorId() == null) {
                temp.setAuditorId(username);
            } else {
                username = feignHrService.getOne(x.getAuditorId()).getUsername();
                temp.setAuditorId(username);
            }
            temp.setAuditStatus(x.getAuditStatus());
            temp.setAuditTime(x.getAuditTime());
            temp.setAmount(x.getAmount());
            temp.setId(x.getId());
            temp.setRemark(x.getRemark());
            temp.setTypeId(x.getTypeId());
            temp.setCreationTime(x.getCreationTime());
            temp.setAuditStatus(x.getAuditStatus());
            list.add(temp);
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

        QueryWrapper<Financial> queryWrapper = new QueryWrapper<>();
        // 条件拼接
        queryWrapper.like("id", searchKey).or().like("name", searchKey);
        List<Financial> dataList = financialService.list(queryWrapper);

        result.setDataList(dataList);
        result.setCountTotal(dataList.size());
        result.setCountCurrent(dataList.size());
        return result;
    }

    /**
     * @param mList
     * @return
     */
    @RequestMapping("/insertOrUpdateBatch")
    public ResultSet<Object> insertOrUpdateBatch(@RequestBody List<Financial> mList) {

        ResultSet<Object> resultSet = new ResultSet<>();

        if (financialService.saveOrUpdateBatch(mList)) {
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
