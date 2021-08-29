package com.example.SESummer.Controller;

import com.example.SESummer.Entity.*;
import com.example.SESummer.Service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

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
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private WebSocketServer webSocketServer;

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
            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            if(isSubmit==0){
                QuestionnaireSubmit questionnaireSubmit=new QuestionnaireSubmit();
                questionnaireSubmit.setUserID(userID);
                questionnaireSubmit.setQuestionnaireID(questionnaireID);
                questionnaireSubmit.setIsSubmit(isSubmit);
                questionnaireSubmit.setSubmitTime(timestamp);
                questionnaireSubmitService.addQuestionnaireSubmit(questionnaireSubmit);
                map.put("success",true);
            }
            else{
                //找到所有选项
                List<UserChooseQuestion> userChooseQuestionList=userChooseQuestionService.getAllChooseRecordOfQuestionnaireByUserIDAndQuestionnaireID(userID,questionnaireID);
                //找到所有有限额的选项
                List<QuestionOption> questionOptionList = new ArrayList<>();
                List<QuestionOption> otherQuestionOptionList=new ArrayList<>();
                for(UserChooseQuestion userChooseQuestion:userChooseQuestionList){
                    Integer questionOptionID=userChooseQuestion.getQuestionOptionID();
                    QuestionOption questionOption=questionnaireService.getQuestionOptionByQuestionOptionID(questionOptionID);
                    if(questionOption.getLeftVolume()>0){
                        questionOptionList.add(questionOption);
                    }
                    else{
                        otherQuestionOptionList.add(questionOption);
                    }
                }
                //循环判断是否已经满了
                int count=0;
                for (QuestionOption questionOption:questionOptionList){
                    if(questionOption.getLeftVolume()> questionOption.getVoteVolume()){
                        count+=1;
                    }
                }
                //满了，直接清除所有记录
                if(count!=questionOptionList.size()){
                    userChooseQuestionService.delRecord(userID,questionnaireID);
                    userCompletionQuestionService.delRecord(userID,questionnaireID);
                    userLocateQuestionService.delRecord(userID,questionnaireID);
                    userScoreQuestionService.delRecord(userID,questionnaireID);
                    map.put("failure",true);
                }
                //没满，插进去
                else {
                    for(QuestionOption questionOption:questionOptionList){
                        Integer volume = questionOption.getVoteVolume()+1;
                        Integer optionID = questionOption.getQuestionOptionID();
                        questionDataService.addVoteVolume(questionOption.getQuestionOptionID());
                        webSocketServer.sendAllMessage(optionID+"#"+volume);
                    }
                    for(QuestionOption questionOption:otherQuestionOptionList){
                        questionDataService.addVoteVolume(questionOption.getQuestionOptionID());
                    }
                    //找到所有评分题
                    List<QuestionContent> questionContentList=questionnaireService.getAllQuestionContentOfQuestionnaireByQuestionnaireID(questionnaireID);
                    for(QuestionContent questionContent:questionContentList){
                        if(questionContent.getQuestionKind()==4){
                            questionDataService.updateAverageScore(questionContent.getQuestionContentID());
                        }
                    }
                    QuestionnaireSubmit questionnaireSubmit=new QuestionnaireSubmit();
                    questionnaireSubmit.setUserID(userID);
                    questionnaireSubmit.setQuestionnaireID(questionnaireID);
                    questionnaireSubmit.setIsSubmit(isSubmit);
                    questionnaireSubmit.setSubmitTime(timestamp);
                    questionnaireSubmitService.addQuestionnaireSubmit(questionnaireSubmit);
                    questionDataService.addRecycleVolume(questionnaireID);
                    map.put("success",true);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @GetMapping("/haveFinish")
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

    @PostMapping("/setTestScore")
    @ApiOperation("自动评分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userID",value = "用户ID",required = true,dataType = "int"),
            @ApiImplicitParam(name = "questionContentID",value = "问题ID",required = true,dataType = "int"),
            @ApiImplicitParam(name = "questionKind",value = "问题种类",required = true,dataType = "int"),
            @ApiImplicitParam(name = "questionScore",value = "问题分数",required = true,dataType = "int"),
            @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",required = true,dataType = "int")
    })
    public Map<String ,Object> setTestScore(@RequestParam Integer userID,@RequestParam Integer questionContentID,@RequestParam Integer questionKind,@RequestParam Integer questionScore,@RequestParam Integer questionnaireID){
        Map<String,Object> map = new HashMap<>();
        try {
            if(questionScore>0){
                TestScoreRecord testScoreRecord=new TestScoreRecord();
                testScoreRecord.setUserID(userID);
                testScoreRecord.setQuestionContentID(questionContentID);
                if(questionKind==3){
                    UserCompletionQuestion userCompletionQuestion=userCompletionQuestionService.getCompletionRecordByID(userID,questionnaireID,questionContentID);
                    if(userCompletionQuestion==null){
                        testScoreRecord.setScore(0);
                    }
                    else{
                        String answer=userCompletionQuestion.getCompletionContent();
                        CompletionQuestion completionQuestion=userCompletionQuestionService.judgeCompletionScore(questionContentID,answer);
                        if(completionQuestion!=null){
                            testScoreRecord.setScore(questionScore);
                        }
                        else{
                            testScoreRecord.setScore(0);
                        }
                    }
                    questionnaireSubmitService.setTestScoreRecord(testScoreRecord);
                    CompletionQuestion completionQuestion1=questionnaireService.getCompletionQuestionByQuestionContentID(questionContentID);
                    map.put("success",true);
                    map.put("score",testScoreRecord.getScore());
                }
                else if(questionKind==1){
                    //获取答案选项，获取填写选项，比较是否一致
                    List<UserChooseQuestion> userChooseQuestionList=userChooseQuestionService.getUserChooseRecordOfQuestion(userID,questionnaireID,questionContentID);
                    List<QuestionOption> questionOptionList=questionnaireService.getAllAnswerOptionOfQuestion(questionContentID);
                    if(userChooseQuestionList.size()!=0){
                        if(Objects.equals(userChooseQuestionList.get(0).getQuestionOptionID(), questionOptionList.get(0).getQuestionOptionID())){
                            testScoreRecord.setScore(questionScore);
                        }
                        else{
                            testScoreRecord.setScore(0);
                        }
                    }
                    else{
                        testScoreRecord.setScore(0);
                    }
                    questionnaireSubmitService.setTestScoreRecord(testScoreRecord);
                    map.put("success",true);
                    map.put("score",testScoreRecord.getScore());
                }
                else if(questionKind==2){
                    //获取答案选项列表，获取填写选项列表，比较是否一致
                    List<UserChooseQuestion> userChooseQuestionList=userChooseQuestionService.getUserChooseRecordOfQuestion(userID,questionnaireID,questionContentID);
                    List<QuestionOption> questionOptionList=questionnaireService.getAllAnswerOptionOfQuestion(questionContentID);
                    if(userChooseQuestionList.size()!=0){
                        int match=0;
                        for(QuestionOption questionOption:questionOptionList){
                            int count=0;
                            for(UserChooseQuestion userChooseQuestion:userChooseQuestionList){
                                if(Objects.equals(userChooseQuestion.getQuestionOptionID(), questionOption.getQuestionOptionID())){
                                    match+=1;
                                    break;
                                }
                                else{
                                    count+=1;
                                }
                            }
                            if(count==userChooseQuestionList.size()){
                                testScoreRecord.setScore(0);
                                break;
                            }
                        }
                        if(match==userChooseQuestionList.size()){
                            testScoreRecord.setScore(questionScore);
                        }
                    }
                    else{
                        testScoreRecord.setScore(0);
                    }
                    questionnaireSubmitService.setTestScoreRecord(testScoreRecord);
                    map.put("success",true);
                    map.put("score",testScoreRecord.getScore());
                }
            }
            else {
                map.put("success",true);
                map.put("message","该问题不是有分数的题");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @GetMapping("/getAllCompletionRecord")
    @ApiOperation("获取填空题的所有填空情况")
    @ApiImplicitParam(name = "questionContentID",value = "问题ID",required = true,dataType = "int")
    public Map<String,Object> getAllCompletionRecord(@RequestParam Integer questionContentID){
        Map<String,Object> map=new HashMap<>();
        try {
            List<UserCompletionQuestion> userCompletionQuestionList=userCompletionQuestionService.getAllCompletionRecordOfQuestion(questionContentID);
            map.put("success",true);
            map.put("userCompletionQuestionList",userCompletionQuestionList);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @GetMapping("/getAllLocateRecord")
    @ApiOperation("获取定位题的所有填空情况")
    @ApiImplicitParam(name = "questionContentID",value = "问题ID",required = true,dataType = "int")
    public Map<String,Object> getAllLocateRecord(@RequestParam Integer questionContentID){
        Map<String,Object> map=new HashMap<>();
        try {
            List<UserLocateQuestion> userLocateQuestionList=userLocateQuestionService.getAllLocateRecordOfQuestion(questionContentID);
            map.put("success",true);
            map.put("userLocateQuestionList",userLocateQuestionList);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @GetMapping("/getScoreRecordDistribute")
    @ApiOperation("获取评分题分数分布")
    @ApiImplicitParam(name = "questionContentID",value = "问题ID",required = true,dataType = "int")
    public Map<String,Object> getScoreRecordDistribute(@RequestParam Integer questionContentID){
        Map<String,Object> map=new HashMap<>();
        try {
            ScoreQuestion scoreQuestion=questionnaireService.getScoreQuestionByQuestionContentID(questionContentID);
            Integer score=scoreQuestion.getMaxScore();
            List<ScoreData> distributeList=new ArrayList<>();
            for(int i=1;i<=score;i++){
                ScoreData scoreData=new ScoreData(i,userScoreQuestionService.sumOfScore(questionContentID,i));
                distributeList.add(scoreData);
            }
            map.put("success",true);
            map.put("distributeList",distributeList);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @GetMapping("/getTestData")
    @ApiOperation("获取考试题得分情况")
    @ApiImplicitParam(name = "questionContentID",value = "问题ID",required = true,dataType = "int")
    public Map<String,Object> getTestData(@RequestParam Integer questionContentID){
        Map<String,Object> map=new HashMap<>();
        try {
            Double average=questionnaireSubmitService.getQuestionAverage(questionContentID);
            Integer total=questionnaireSubmitService.getQuestionTotal(questionContentID);
            Integer rightCounts=questionnaireSubmitService.getRightCounts(questionContentID);
            Double accuracy= (double) (rightCounts / total);
            map.put("success",true);
            map.put("average",average);
            map.put("rightCounts",rightCounts);
            map.put("accuracy",accuracy);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }
}
