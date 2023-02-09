package pers.jd.feignService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.jd.entity.ResultSet;
import pers.jd.feignService.dto.Financial;


@RestController
@FeignClient("ERP-FINANCIAL")
public interface FeignFinancialService {
    @PostMapping("financial/insertOrUpdate")
    ResultSet storage_record(@RequestBody Financial record);
}
