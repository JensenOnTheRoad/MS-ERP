package pers.jd.feignService;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pers.jd.entity.ResultSet;
import pers.jd.feignService.dto.StorageRecord;
import pers.jd.feignService.dto.Warehouse;


@RestController
@FeignClient("ERP-WAREHOUSE")
public interface FeignWarehouseService {

    @PostMapping("storage-record/insert")
    ResultSet storage_record(@RequestBody StorageRecord record);


    @RequestMapping(value = "warehouse/getOne", method = RequestMethod.GET)
    Warehouse getOne(@RequestParam(value = "id") long id);
}
