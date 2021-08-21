package com.example.SESummer.Controller;

import com.example.SESummer.Entity.QuestionnaireSubmit;
import com.example.SESummer.Entity.UserCompletionQuestion;
import com.example.SESummer.Entity.UserScoreQuestion;
import com.example.SESummer.Service.QuestionSubmitService;
import com.example.SESummer.Service.UserCompletionQuestionService;
import com.example.SESummer.Service.UserScoreQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "用户填写情况相关接口")
@RequestMapping("/question")
public class UserMessageStoreController {
    @Autowired
    private UserCompletionQuestionService userCompletionQuestionService;
    @Autowired
    private UserScoreQuestionService userScoreQuestionService;
    @Autowired
    private QuestionSubmitService questionSubmitService;

    @PostMapping("/completion")
    @ApiOperation("填空题填写情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "questionContentID",value = "题目ID",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "completionContent",value = "填空题内容",dataType = "String")
    })
    public Map<String,Object> userCompletionRecord(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer questionContentID,@RequestParam String completionContent){
        Map<String,Object> map = new HashMap<>();
        UserCompletionQuestion userCompletion = new UserCompletionQuestion();
        userCompletion.setUserID(userID);
        userCompletion.setQuestionnaireID(questionnaireID);
        userCompletion.setQuestionContentID(questionContentID);
        userCompletion.setCompletionContent(completionContent);
        try {

            if (userCompletionQuestionService.getCompletionRecordByID(userID,questionnaireID,questionContentID) == null){
                userCompletionQuestionService.addCompletionRecord(userCompletion);
                map.put("success",true);
                map.put("message","保存成功");
            }
            else{
                userCompletionQuestionService.updateCompletionRecord(userCompletion);
                map.put("success",true);
                map.put("message","保存成功");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","保存失败");
        }
        return map;
    }

    @PostMapping("/score")
    @ApiOperation("评分题填写情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "questionContentID",value = "题目ID",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "score",value = "分数",dataType = "Integer",required = true)
    })
    public Map<String,Object> userScoreQuestion(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer questionContentID,@RequestParam Integer score){
        Map<String,Object> map = new HashMap<>();
        UserScoreQuestion userScoreQuestion=new UserScoreQuestion();
        userScoreQuestion.setUserID(userID);
        userScoreQuestion.setQuestionnaireID(questionnaireID);
        userScoreQuestion.setQuestionContentID(questionContentID);
        userScoreQuestion.setScore(score);
        try {
            if (userScoreQuestionService.getScoreRecordByID(userID,questionnaireID,questionContentID) == null){
                userScoreQuestionService.addScoreRecord(userScoreQuestion);
                map.put("success",true);
                map.put("message","评分成功");
            }
            else {
                userScoreQuestionService.updateScoreRecord(userScoreQuestion);
                map.put("success",true);
                map.put("message","评分成功");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","评分失败");
        }
        return map;
    }

    @PostMapping("/submit")
    @ApiOperation("用户问卷提交情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "isSubmit",value = "是否提交",dataType = "Integer",required = true)
    })
    public Map<String,Object> userSubmit(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer isSubmit){
        Map<String,Object> map = new HashMap<>();
        QuestionnaireSubmit submit = new QuestionnaireSubmit();
        submit.setUserID(userID);
        submit.setQuestionnaireID(questionnaireID);
        submit.setIsSubmit(isSubmit);
        try {
            questionSubmitService.addQuestionSubmit(submit);
            map.put("success",true);
            map.put("message","提交成功");
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","提交失败");
        }
        return map;
    }
}
