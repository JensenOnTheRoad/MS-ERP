package pers.jd.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import pers.jd.entity.*;
import pers.jd.entity.dto.RequestModelProductionMain;
import pers.jd.entity.vo.ProductionMainAndSub;
import pers.jd.entity.vo.ProductionMainAndSubVO;
import pers.jd.entity.vo.ProductionMainVO;
import pers.jd.entity.vo.ProductionSubVO;
import pers.jd.feignService.FeignCommodityService;
import pers.jd.feignService.FeignFinancialService;
import pers.jd.feignService.FeignHrService;
import pers.jd.feignService.FeignWarehouseService;


import pers.jd.feignService.dto.Financial;
import pers.jd.feignService.dto.StorageRecord;
import pers.jd.service.impl.MaterialServiceImpl;
import pers.jd.service.impl.ProductionMainServiceImpl;
import pers.jd.service.impl.ProductionSubServiceImpl;
import pers.jd.service.impl.PurchaseMainServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 生产主单 前端控制器
 * </p>
 *
 * @author jd
 * @since 2022-01-29
 */
@Transactional
@RestController
@CrossOrigin
@RequestMapping(value = "/production-main")
public class ProductionMainController {

    @Resource
    ProductionMainServiceImpl productionMainService;
    @Resource
    ProductionSubServiceImpl productionSubService;
    @Resource
    PurchaseMainServiceImpl purchaseMainService;
    @Resource
    MaterialServiceImpl materialService;

    @Resource
    FeignWarehouseService feignWarehouseService;
    @Resource
    FeignCommodityService feignCommodityService;
    @Resource
    FeignHrService feignHrService;
    @Resource
    FeignFinancialService feignFinancialService;
    // region

    /**
     * 插入/更新-单个数据
     * 前端必须以json格式传入
     *
     * @param m 若m.id为0就为插入新的数据,反之则为更新相应数据。
     * @return resultSet
     */
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    public ResultSet<Object> insertOrUpdate(@RequestBody ProductionMain m) {

        ResultSet<Object> rs = new ResultSet<>();

        if (productionMainService.saveOrUpdate(m)) {
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


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultSet<Object> insert(@RequestBody final List<RequestModelProductionMain> params) {

        boolean STATUS = false;

        if (params != null) {
            int principalId = 0;
            int warehouseId = 0;
            int commodityId = 0;
            int productionQuantity = 0;
            // 主单
            ProductionMain productionMain = new ProductionMain();

            productionMain.setStatus(1);
            productionMain.setCreationTime(LocalDateTime.now());

            productionMainService.save(productionMain);

            Integer newId = productionMain.getId();

            // 所需物品
            for (RequestModelProductionMain temp : params) {

                int number = temp.number;
                int materialId = temp.requiredMaterialId;
                productionQuantity = temp.productionQuantity;
                commodityId = temp.commodityId;
                principalId = temp.principalId;
                warehouseId = temp.warehouseId;

                ProductionSub productionSub = new ProductionSub();
                productionSub.setMaterialId(materialId);
                productionSub.setQuantify(number);
                productionSub.setProductionMainId(newId);

                productionSubService.save(productionSub);
                STATUS = true;
            }

            // 根据副单，补充主单数据

            productionMain.setPrincipalId(principalId);
            productionMain.setWarehouseId(warehouseId);
            productionMain.setCommodityId(commodityId);
            productionMain.setQuantity(productionQuantity);

            productionMainService.updateById(productionMain);
        }

        ResultSet<Object> rs = new ResultSet<>();
        if (STATUS) {
            rs.setReturnCode(1);
            rs.setReturnMessage("操作成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("操作失败");
        }
        return rs;

    }

    // region
    @RequestMapping(value = "/getAllAndSub", method = RequestMethod.GET)
    public ResultGetAllPaging getAllAndSub(@RequestParam(value = "pageIndex", defaultValue = "0")
                                                   long pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "20")
                                                   long pageSize
//                                            ,
//                                           @RequestParam(value = "startDate", defaultValue = "1971-01-01")
//                                                   String startDate,
//                                           @RequestParam(value = "endDate", defaultValue = "2100-01-01")
//                                                   String endDate,
//                                           @RequestParam(value = "fuzzy", defaultValue = "")
//                                                   String fuzzy
    ) {

        // 分页
        Page<ProductionMain> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<ProductionMain> queryWrapper = new QueryWrapper<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        startDate = "1971-01-01";
//        endDate = "2100-01-01";
//        queryWrapper.between("creation_time", LocalDate.parse(startDate, dtf), LocalDate.parse(endDate, dtf))
//                .or()
//                .between("finish_time", LocalDate.parse(startDate, dtf), LocalDate.parse(endDate, dtf))
//                .or()
//                .like("principal_id", fuzzy);
        Iterable<ProductionMain> list = productionMainService.getAllPaging(page, queryWrapper);

        // 组合数据
        List<ProductionMainAndSub> resultData = new ArrayList<>();
        for (ProductionMain p : list) {
            ProductionMainAndSub temp = new ProductionMainAndSub();
            List<ProductionSub> subs = productionSubService.getSubsById(p.getId());
            temp.productionMain = p;
            temp.productionSubs = subs;
            resultData.add(temp);
        }

        ResultGetAllPaging result = new ResultGetAllPaging();
        long countTotal = productionMainService.count();

        if (resultData.size() > 0) {
            result.setDataList(tansToVO(resultData));
        }
        result.setCountTotal(countTotal);
        result.setCountCurrent(resultData.size());
        return result;
    }

    List<ProductionMainAndSubVO> tansToVO(List<ProductionMainAndSub> params) {
        List<ProductionMainAndSubVO> vo = new ArrayList<>();

        for (ProductionMainAndSub i : params) {
            ProductionMainAndSubVO temp = new ProductionMainAndSubVO();
            ProductionMainVO tempMain = new ProductionMainVO();
            ProductionMain main = i.productionMain;

            tempMain.setId(main.getId());
            tempMain.setCreationTime(main.getCreationTime());
            tempMain.setFinishTime(main.getFinishTime());
            tempMain.setQuantity(main.getQuantity());
            tempMain.setSpentDays(main.getSpentDays());
            tempMain.setStatus(main.getStatus());

            tempMain.setPrincipalId(feignHrService
                    .getOne(main.getPrincipalId())
                    .getUsername());

            tempMain.setWarehouseId(feignWarehouseService
                    .getOne(main.getWarehouseId())
                    .getName());

            tempMain.setCommodityId(feignCommodityService
                    .getOne(main.getCommodityId())
                    .getName());

            List<ProductionSubVO> tempSubs = new ArrayList<>();
            List<ProductionSub> subs = i.productionSubs;
            for (ProductionSub s : subs) {
                ProductionSubVO tempSub = new ProductionSubVO();
                tempSub.setId(s.getId());
                tempSub.setProductionMainId(s.getProductionMainId());
                tempSub.setQuantify(s.getQuantify());
                tempSub.setMaterialId(materialService
                        .getById(s.getMaterialId())
                        .getName());
                tempSubs.add(tempSub);
            }

            temp.productionSubsVO = tempSubs;
            temp.productionMainVO = tempMain;
            vo.add(temp);

        }
        return vo;
    }


    /**
     * 审核-根据id
     *
     * @param id
     * @return resultSet
     */
    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    public ResultSet<Object> audit(@RequestParam(value = "id") int id,
                                   @RequestParam(value = "principal_id") int auditorId) {

        ProductionMain pm = productionMainService.getById(id);
        pm.setStatus(2);
        pm.setFinishTime(LocalDateTime.now());

        LocalDateTime past = pm.getCreationTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(past, now);
        pm.setSpentDays((int) duration.toDays());


        StorageRecord record = new StorageRecord();
        record.setStorageId(pm.getCommodityId());
        record.setStorageType(1);   // 商品1
        record.setQuantity(pm.getQuantity());
        record.setRecordTime(LocalDateTime.now());
        record.setType(0); // 入库0,出库1
        feignWarehouseService.storage_record(record);

        List<ProductionSub> subsById = productionSubService.getSubsById(pm.getId());
        for (ProductionSub i : subsById) {
            StorageRecord record_out = new StorageRecord();
            record_out.setStorageId(i.getMaterialId());
            record_out.setStorageType(0);   // 商品1
            record_out.setQuantity(i.getQuantify());
            record_out.setRecordTime(LocalDateTime.now());
            record_out.setType(1); // 入库0,出库1
            feignWarehouseService.storage_record(record_out);
        }

        Financial financial = new Financial();
        financial.setCreationTime(LocalDateTime.now());
        List<ProductionSub> subs = productionSubService.getSubsById(pm.getId());
        BigDecimal total = new BigDecimal(0);
        for (ProductionSub sub : subs) {
            BigDecimal price = materialService.getById(sub.getMaterialId()).getPrice();
            BigDecimal temp = price.multiply(new BigDecimal(sub.getQuantify()));
            total = total.add(temp);
        }
        financial.setAuditStatus(0);
        financial.setAuditorId(auditorId);
        financial.setTypeId(1);// 0收入，1支出
        financial.setRemark("生产成本");
        financial.setAmount(total.multiply(BigDecimal.valueOf(0.10)));
        financial.setCreationTime(LocalDateTime.now());
        feignFinancialService.storage_record(financial);

        ResultSet<Object> rs = new ResultSet<>();
        if (productionMainService.updateById(pm)) {
            rs.setReturnCode(1);
            rs.setReturnMessage("审核成功！");
        } else {
            rs.setReturnCode(0);
            rs.setReturnMessage("审核失败");
        }
        return rs;
    }

    // endregion

    /**
     * 删除-根据id
     *
     * @param id
     * @return resultSet
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultSet<Object> delete(@RequestParam(value = "id") int id) {

        ResultSet<Object> rs = new ResultSet<>();
        List<ProductionSub> subsById = productionSubService.getSubsById(id);

        if (productionMainService.removeById(id)) {
            subsById.forEach(i -> {
                productionSubService.removeById(i.getId());
            });
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
    public ProductionMain getOne(@RequestParam(value = "id") long id) {
        return productionMainService.getById(id);
    }

    /**
     * 查询-全部列表
     *
     * @return data
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<ProductionMain> getAll() {

        return productionMainService.list();
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

        Page<ProductionMain> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<ProductionMain> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("price", 312312);

        List<ProductionMain> dataList = productionMainService.getAllPaging(page, queryWrapper);
        long countTotal = productionMainService.count();
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

        QueryWrapper<ProductionMain> queryWrapper = new QueryWrapper<>();
        // 条件拼接
        queryWrapper.like("id", searchKey).or().like("name", searchKey);
        List<ProductionMain> dataList = productionMainService.list(queryWrapper);

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
    public ResultSet<Object> insertOrUpdateBatch(@RequestBody List<ProductionMain> m_list) {

        ResultSet<Object> resultSet = new ResultSet<>();

        if (productionMainService.saveOrUpdateBatch(m_list)) {
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
