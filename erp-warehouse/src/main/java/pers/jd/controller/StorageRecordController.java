package pers.jd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.web.bind.annotation.*;

import pers.jd.entity.ResultGetAllPaging;
import pers.jd.entity.ResultSet;
import pers.jd.entity.StorageRecord;

import pers.jd.entity.vo.StorageRecordVO;
import pers.jd.feignService.FeignCommodityService;
import pers.jd.feignService.FeignMaterialService;
import pers.jd.service.impl.StorageRecordServiceImpl;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 仓储记录表 前端控制器
 * </p>
 *
 * @author jd
 * @since 2022-03-13
 */
@RestController
@CrossOrigin
@RequestMapping("/storage-record")
public class StorageRecordController {


    @Resource
    private StorageRecordServiceImpl storageRecordService;

    @Resource
    private FeignCommodityService feignCommodityService;
    @Resource
    private FeignMaterialService feignMaterialService;
    // region

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultSet<Object> insert(@RequestBody StorageRecord m) {

        m.setRecordTime(LocalDateTime.now());
        ResultSet<Object> rs = new ResultSet<>();

        if (storageRecordService.saveOrUpdate(m)) {
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
    public ResultSet<Object> insertOrUpdate(@RequestBody StorageRecord m) {

        ResultSet<Object> rs = new ResultSet<>();

        if (storageRecordService.saveOrUpdate(m)) {
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
        if (storageRecordService.removeById(id)) {
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
    public StorageRecord getOne(@RequestParam(value = "id") long id) {
        return storageRecordService.getById(id);
    }

    /**
     * 查询-全部列表
     *
     * @return data
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<StorageRecord> getAll() {
        return storageRecordService.list();
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

        Page<StorageRecord> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<StorageRecord> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("price", 312312);

        List<StorageRecord> dataList = storageRecordService.getAllPaging(page, queryWrapper);
        long countTotal = storageRecordService.count();
        result.setDataList(transToVO(dataList));
        result.setCountTotal(countTotal);
        result.setCountCurrent(dataList.size());
        return result;
    }

    List<StorageRecordVO> transToVO(List<StorageRecord> params) {
        List<StorageRecordVO> vo = new ArrayList<>();
        String name = "";
        String typeName = "";
        String type = "";
        for (StorageRecord i : params) {
            StorageRecordVO temp = new StorageRecordVO();
            Integer storageType = i.getStorageType();
            if (storageType == 0) {    //物资
                name = feignMaterialService.getOne(i.getStorageId()).getName();
                typeName = "物资";
            } else if (storageType == 1) {//商品
                name = feignCommodityService.getOne(i.getStorageId()).getName();
                typeName = "商品";
            }

            if (i.getType() == 0) {
                type = "入库";
            } else if (i.getType() == 1) {
                type = "出库";
            }
            temp.setStorageId(name);
            temp.setStorageType(typeName);
            temp.setType(type);
            temp.setRecordTime(i.getRecordTime());
            temp.setQuantity(i.getQuantity());
            temp.setId(i.getId());
            vo.add(temp);
        }
        return vo;

    }

    /**
     * 模糊查询
     *
     * @return
     */
    @RequestMapping(value = "/fuzzySearch", method = RequestMethod.GET)
    public ResultGetAllPaging fuzzySearch(@RequestParam(value = "") String searchKey) {
        ResultGetAllPaging result = new ResultGetAllPaging();

        QueryWrapper<StorageRecord> queryWrapper = new QueryWrapper<>();
        // 条件拼接
        queryWrapper.like("id", searchKey).or().like("name", searchKey);
        List<StorageRecord> dataList = storageRecordService.list(queryWrapper);

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
    public ResultSet<Object> insertOrUpdateBatch(@RequestBody List<StorageRecord> m_list) {

        ResultSet<Object> resultSet = new ResultSet<>();

        if (storageRecordService.saveOrUpdateBatch(m_list)) {
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
