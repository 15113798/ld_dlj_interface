package io.renren.modules.pc.controller;

import io.renren.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("threeIndCon")
public class threeIndController {

    @RequestMapping("/getData")
    public R getData(@RequestParam Map<String, Object> params){

        return R.ok().put("data", null);
    }


    @RequestMapping("/getDisData")
    public R getDisData(@RequestParam Map<String, Object> params){

        return R.ok().put("data", null);
    }






}
