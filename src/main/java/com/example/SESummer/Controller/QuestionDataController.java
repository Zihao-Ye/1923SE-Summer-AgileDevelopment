package com.example.SESummer.Controller;

import com.example.SESummer.Service.QuestionDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "问卷数据变化相关接口")
public class QuestionDataController {
    @Autowired
    private QuestionDataService questionDataService;

    @PostMapping("/addRecycleVolume")
    @ApiOperation("增加问卷回收量的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "Integer",required = true)
    })
    public Map<String,Object> addRecycleVolume(@RequestParam Integer questionnaireID){
        Map<String,Object> map = new HashMap<>();
        try {
            questionDataService.addRecycleVolume(questionnaireID);
            map.put("success",true);
        }
        catch (Exception e){
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/addVoteVolume")
    @ApiOperation("增加选项选择数的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionOptionID",value = "选项ID",dataType = "Integer",required = true)
    })
    public Map<String,Object> addVoteVolume(@RequestParam Integer questionOptionID){
        Map<String,Object> map = new HashMap<>();
        try {
            questionDataService.addVoteVolume(questionOptionID);
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }
    @PostMapping("/reduceLeftVolume")
    @ApiOperation("减少选项剩余数的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionOptionID",value = "选项ID",dataType = "Integer",required = true)
    })
    public Map<String,Object> reduceLeftVolume(@RequestParam Integer questionOptionID){
        Map<String,Object> map = new HashMap<>();
        try {
            questionDataService.reduceLeftVolume(questionOptionID);
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

}
