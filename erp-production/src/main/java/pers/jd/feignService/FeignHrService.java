package pers.jd.feignService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import pers.jd.feignService.dto.User;

@RestController
@FeignClient("ERP-HR")
public interface FeignHrService {


    @RequestMapping(value = "user/getOne", method = RequestMethod.GET)
    User getOne(@RequestParam(value = "id") long id);
}
