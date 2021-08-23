package com.example.SESummer.Controller;

import com.example.SESummer.Entity.*;
import com.example.SESummer.Service.DataOutputService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Api(tags = "输出输出测试接口")
public class DataOutputController {
    @Autowired
    private DataOutputService dataOutputService;

    @PostMapping("/DataOutput")
    @ApiOperation("导出问卷数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "int",required = true)
    })
    public Map<String,Object> dataOutPut(@RequestParam Integer userID,@RequestParam Integer questionnaireID){
        Map<String,Object> returnMap = new HashMap<>();
        try {
            List<Map<String,Object>> mapList = new ArrayList<>();
            List<QuestionnaireSubmit> submitList = dataOutputService.getUserIDByQuestionnaireID(questionnaireID);
            String filename = "";
            String title = "";
            for(QuestionnaireSubmit submit:submitList){
                Map<String,Object> map = new LinkedHashMap<>();
                int m = 1;
                Integer userid = submit.getUserID();
                Integer questionnaireid = submit.getQuestionnaireID();
                //将用户信息添加到map
                map.put((m++)+":"+"用户名",dataOutputService.getUserNameByUserID(submit.getUserID()));
                //将问卷的信息添加到map
                map.put((m++)+":"+"问卷标题",dataOutputService.getTitleByQuestionnaireID(submit.getQuestionnaireID()));
                //获取该用户对该问卷的信息
                int i = 1;
                List<UserChooseQuestion> chooseList = dataOutputService.getChooseByID(userid,questionnaireid);
                List<UserCompletionQuestion> completionList = dataOutputService.getCompletionByID(userid,questionnaireid);
                List<UserScoreQuestion> scoreList = dataOutputService.getScoreByID(userid,questionnaireid);
                //对选择题进行处理
                for(UserChooseQuestion choose:chooseList){
                    map.put((m++)+":"+"问题"+(i++),dataOutputService.getContentByQuestionID(choose.getQuestionContentID()));
                    List<QuestionOption> options = dataOutputService.getAllOptionsByQuestionID(choose.getQuestionContentID());
                    int j = 1;
                    for (QuestionOption option:options){
                        if(dataOutputService.getOptionByOptionID(userid,option.getQuestionOptionID()).isEmpty()){
                            map.put((m++)+":"+"选项"+(j++)+":"+option.getOptionContent(),0);
                        }
                        else {
                            map.put((m++)+":"+"选项"+(j++)+":"+option.getOptionContent(),1);
                        }
                    }
                }
                //对填空题进行处理
                for(UserCompletionQuestion completion:completionList){
                    map.put((m++)+":"+"问题"+(i++)+":"+dataOutputService.getContentByQuestionID(completion.getQuestionContentID()),completion.getCompletionContent());
                }
                //对评分题进行处理
                for(UserScoreQuestion score:scoreList){
                    map.put((m++)+":"+"问题"+(i++)+":"+dataOutputService.getContentByQuestionID(score.getQuestionContentID()),score.getScore());
                }
                mapList.add(map);
            }
            filename = "userid-"+userID +"-"+String.valueOf(questionnaireID);
            title = String.valueOf(questionnaireID);
            DataOutput dataOutput = DataOutput.getInstance();
            dataOutput.createExcel(mapList,filename,title);
            returnMap.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            returnMap.put("success",false);
        }
        return returnMap;
    }
}
