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
            //根据问卷号获取填写者的数据
            List<QuestionnaireSubmit> submitList = dataOutputService.getUserIDByQuestionnaireID(questionnaireID);
            List<QuestionContent> questions = dataOutputService.getQuestionsByQuestionnaireID(questionnaireID);
            String filename;
            String title;
            for(QuestionnaireSubmit submit:submitList){
                Map<String,Object> map = new LinkedHashMap<>();
                Integer userid = submit.getUserID();
                map.put("用户名",dataOutputService.getUserNameByUserID(userid));
                int i = 1;
                for (QuestionContent question:questions){
                    switch (question.getQuestionKind()) {
                        case 1:{
                        }
                        case 2:{
                            String content = dataOutputService.getContentByQuestionID(question.getQuestionContentID());
                            map.put("问题"+(i++)+":"+content,"");
                            List<QuestionOption> options = dataOutputService.getAllOptionsByQuestionID(question.getQuestionContentID());
                            int j = 1;
                            for(QuestionOption option:options){
                                if(dataOutputService.getOptionByOptionID(userid,question.getQuestionContentID(),option.getQuestionOptionID()) == null){
                                    map.put("选项"+(j++)+":"+option.getOptionContent(),"0");
                                }
                                else {
                                    map.put("选项"+(j++)+":"+option.getOptionContent(),"1");
                                }
                            }
                            break;
                        }
                        case 3:{
                            String content = dataOutputService.getContentByQuestionID(question.getQuestionContentID());
                            if (dataOutputService.getCompletionByID(userid,question.getQuestionContentID()) == null){
                                map.put("问题"+(i++)+":"+content,"NA");
                            }
                            else {
                                map.put("问题"+(i++)+":"+content,dataOutputService.getCompletionByID(userid,question.getQuestionContentID()).getCompletionContent());
                            }
                            break;
                        }
                        case 4:{
                            String content = dataOutputService.getContentByQuestionID(question.getQuestionContentID());
                            if (dataOutputService.getScoreByID(userid,question.getQuestionContentID()) == null){
                                map.put("问题"+(i++)+":"+content,"NA");
                            }
                            else {
                                map.put("问题"+(i++)+":"+content,dataOutputService.getScoreByID(userid,question.getQuestionContentID()).getScore());
                            }
                            break;
                        }
                    }
                }
                mapList.add(map);
            }
            filename = "user-"+userID +"-questionnaire-"+ questionnaireID;
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

    @PostMapping("/SingleData")
    @ApiOperation("导出单题数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionContentID",value = "题号",dataType = "int",required = true)
    })
    public Map<String,Object> singleData(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer questionContentID){
        Map<String,Object> retMap = new HashMap<>();
        try {
            List<Map<String,Object>> mapList = new ArrayList<>();
            List<QuestionnaireSubmit> submitList = dataOutputService.getUserIDByQuestionnaireID(questionnaireID);
            QuestionContent question = dataOutputService.getQuestionContentByID(questionContentID);
            for (QuestionnaireSubmit submit:submitList){
                Map<String,Object> map = new LinkedHashMap<>();
                Integer userid = submit.getUserID();
                map.put("用户名",dataOutputService.getUserNameByUserID(userid));
                switch (question.getQuestionKind()){
                    case 1:{
                    }
                    case 2:{
                        String content = dataOutputService.getContentByQuestionID(question.getQuestionContentID());
                        map.put("问题"+":"+content,"");
                        List<QuestionOption> options = dataOutputService.getAllOptionsByQuestionID(question.getQuestionContentID());
                        int j = 1;
                        for(QuestionOption option:options){
                            if(dataOutputService.getOptionByOptionID(userid,question.getQuestionContentID(),option.getQuestionOptionID()) == null){
                                map.put("选项"+(j++)+":"+option.getOptionContent(),"0");
                            }
                            else {
                                map.put("选项"+(j++)+":"+option.getOptionContent(),"1");
                            }
                        }
                        break;
                    }
                    case 3:{
                        String content = dataOutputService.getContentByQuestionID(question.getQuestionContentID());
                        if (dataOutputService.getCompletionByID(userid,question.getQuestionContentID()) == null){
                            map.put("问题"+":"+content,"NA");
                        }
                        else {
                            map.put("问题"+":"+content,dataOutputService.getCompletionByID(userid,question.getQuestionContentID()).getCompletionContent());
                        }
                        break;
                    }
                    case 4:{
                        String content = dataOutputService.getContentByQuestionID(question.getQuestionContentID());
                        if (dataOutputService.getScoreByID(userid,question.getQuestionContentID()) == null){
                            map.put("问题"+":"+content,"NA");
                        }
                        else {
                            map.put("问题"+":"+content,dataOutputService.getScoreByID(userid,question.getQuestionContentID()).getScore());
                        }
                        break;
                    }
                }
                mapList.add(map);
            }
            String filename = "questionnaire-"+ questionnaireID+"-question-"+questionContentID;
            String title = String.valueOf(questionnaireID);
            DataOutput dataOutput = DataOutput.getInstance();
            dataOutput.createExcel(mapList,filename,title);
            retMap.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            retMap.put("success",false);
        }
        return retMap;
    }
}
