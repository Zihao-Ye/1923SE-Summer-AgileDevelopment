package com.example.SESummer.Controller;

import com.example.SESummer.Entity.*;
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
@Api(tags = "WebSocket相关接口")
public class WebSocketController {

    @Autowired
    private QuestionDataService questionDataService;
    @Autowired
    private WebSocketServer webSocketServer;

    @PostMapping("/SocketTest")
    @ApiOperation("减少剩余投票数的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireID",value = "题目ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionOptionID",value = "选项ID",dataType = "int",required = true)
    })
    public Map<String,Object> decreaseVolume(@RequestParam Integer questionnaireID,@RequestParam Integer questionOptionID){
        Map<String ,Object> map = new HashMap<>();
        try {
            QuestionOption option = questionDataService.getQuestionOption(questionOptionID);
            Integer voteVolume = option.getVoteVolume();
            webSocketServer.sendTextMessage(String.valueOf(questionnaireID),questionOptionID+"#"+voteVolume);
            map.put("success",true);
            map.put("message","执行成功");
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","运行时出现异常");

        }
        return map;
    }

}
