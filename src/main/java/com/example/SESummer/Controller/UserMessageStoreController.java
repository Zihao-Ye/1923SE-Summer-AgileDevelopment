package com.example.SESummer.Controller;

import com.example.SESummer.Entity.*;
import com.example.SESummer.Service.*;
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
@Api(tags = "用户填写情况相关接口")
public class UserMessageStoreController {
    @Autowired
    private UserCompletionQuestionService userCompletionQuestionService;
    @Autowired
    private UserScoreQuestionService userScoreQuestionService;
    @Autowired
    private QuestionnaireSubmitService questionnaireSubmitService;
    @Autowired
    private UserChooseQuestionService userChooseQuestionService;
    @Autowired
    private QuestionDataService questionDataService;
    @Autowired
    private UserLocateQuestionService userLocateQuestionService;

    @PostMapping("/resetChoose")
    @ApiOperation("在提交/保存每个选择题前，先执行删除操作，再执行存储")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionContentID",value = "题目ID",dataType = "int",required = true)
    })
    public Map<String,Object> resetUserChooseOption(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer questionContentID){
        Map<String,Object> map = new HashMap<>();
        try {
            questionDataService.minusVoteVolume(questionnaireID,userID,questionContentID);
            userChooseQuestionService.delChooseRecord(userID,questionnaireID,questionContentID);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/choose")
    @ApiOperation("选择题填写情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionContentID",value = "题目ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionOptionID",value = "选项ID",dataType = "int",required = true)
    })
    public Map<String,Object> storeUserChooseOption(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer questionContentID,@RequestParam Integer questionOptionID){
        Map<String,Object> map = new HashMap<>();
        try {
            UserChooseQuestion userChooseQuestion=new UserChooseQuestion();
            userChooseQuestion.setUserID(userID);
            userChooseQuestion.setQuestionnaireID(questionnaireID);
            userChooseQuestion.setQuestionContentID(questionContentID);
            userChooseQuestion.setQuestionOptionID(questionOptionID);
            userChooseQuestionService.addChooseRecord(userChooseQuestion);
            questionDataService.addVoteVolume(questionOptionID);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/completion")
    @ApiOperation("填空题填写情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionContentID",value = "题目ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "completionContent",value = "填空题内容",dataType = "String",required = true)
    })
    public Map<String,Object> storeUserCompletionQuestion(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer questionContentID,@RequestParam String completionContent){
        Map<String,Object> map = new HashMap<>();
        try {
            if (userCompletionQuestionService.getCompletionRecordByID(userID,questionnaireID,questionContentID) == null){
                UserCompletionQuestion userCompletion = new UserCompletionQuestion();
                userCompletion.setUserID(userID);
                userCompletion.setCompletionContent(completionContent);
                userCompletion.setQuestionnaireID(questionnaireID);
                userCompletion.setQuestionContentID(questionContentID);
                userCompletionQuestionService.addCompletionRecord(userID,questionnaireID,questionContentID,completionContent);
            }
            else{
                userCompletionQuestionService.updateCompletionRecord(userID,questionnaireID,questionContentID,completionContent);
            }
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/score")
    @ApiOperation("评分题填写情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionContentID",value = "题目ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "score",value = "分数",dataType = "int",required = true)
    })
    public Map<String,Object> storeUserScoreQuestion(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer questionContentID,@RequestParam Integer score){
        Map<String,Object> map = new HashMap<>();
        try {
            if (userScoreQuestionService.getScoreRecordByID(userID,questionnaireID,questionContentID) == null){
                UserScoreQuestion userScoreQuestion=new UserScoreQuestion();
                userScoreQuestion.setUserID(userID);
                userScoreQuestion.setQuestionnaireID(questionnaireID);
                userScoreQuestion.setQuestionContentID(questionContentID);
                userScoreQuestion.setScore(score);
                userScoreQuestionService.addScoreRecord(userScoreQuestion);
            }
            else {
                userScoreQuestionService.updateScoreRecord(userID,questionnaireID,questionContentID,score);
            }
            questionDataService.updateAverageScore(questionContentID);
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/locate")
    @ApiOperation("定位题填写情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionContentID",value = "题目ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "locate",value = "位置信息",dataType = "String",required = true)
    })
    public Map<String,Object> storeUserLocateQuestion(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer questionContentID,@RequestParam String locate){
        Map<String,Object> map = new HashMap<>();
        try {
            if (userLocateQuestionService.getLocateRecordByID(userID,questionnaireID,questionContentID) == null){
                UserLocateQuestion userLocateQuestion=new UserLocateQuestion();
                userLocateQuestion.setUserID(userID);
                userLocateQuestion.setQuestionnaireID(questionnaireID);
                userLocateQuestion.setQuestionContentID(questionContentID);
                userLocateQuestion.setLocate(locate);
                userLocateQuestionService.addLocateRecord(userLocateQuestion);
            }
            else {
                userLocateQuestionService.updateLocateRecord(userID,questionnaireID,questionContentID,locate);
            }
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/submit")
    @ApiOperation("用户问卷提交情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",dataType = "int",required = true),
            @ApiImplicitParam(name = "isSubmit",value = "是否提交",dataType = "int",required = true)
    })
    public Map<String,Object> userSubmit(@RequestParam Integer userID,@RequestParam Integer questionnaireID,@RequestParam Integer isSubmit){
        Map<String,Object> map = new HashMap<>();
        try {
            QuestionnaireSubmit questionnaireSubmit=new QuestionnaireSubmit();
            questionnaireSubmit.setUserID(userID);
            questionnaireSubmit.setQuestionnaireID(questionnaireID);
            questionnaireSubmit.setIsSubmit(isSubmit);
            questionnaireSubmitService.addQuestionnaireSubmit(questionnaireSubmit);
            if(isSubmit==1){
                questionDataService.addRecycleVolume(questionnaireID);
            }
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/haveFinish")
    @ApiOperation("是否已填过这个问卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",required = true,dataType = "int"),
            @ApiImplicitParam(name = "userID",value = "用户ID",required = true,dataType = "int")
    })
    public Map<String,Object> haveFinish(@RequestParam Integer questionnaireID,@RequestParam Integer userID){
        Map<String,Object> map = new HashMap<>();
        try {
            Integer haveFinish;
            if(questionnaireSubmitService.haveFinish(questionnaireID,userID)!=null){
                haveFinish=1;
            }
            else if(questionnaireSubmitService.haveFill(questionnaireID,userID)!=null){
                haveFinish=0;
            }
            else{
                haveFinish=-1;
            }
            map.put("success",true);
            map.put("haveFinish",haveFinish);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }
}
