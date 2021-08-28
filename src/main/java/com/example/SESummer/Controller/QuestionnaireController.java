package com.example.SESummer.Controller;

import com.example.SESummer.Entity.*;
import com.example.SESummer.Service.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Api(tags = "问卷相关接口")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping("/showQuestionnaireInfo")
    @ApiOperation("预览问卷-问卷信息与问卷的题目信息")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int")
    public Map<String, Object> showQuestionnaireInfo(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            Questionnaire questionnaire = questionnaireService.getQuestionnaireByQuestionnaireID(questionnaireID);
            List<QuestionContent> questionList = questionnaireService.getAllQuestionContentOfQuestionnaireByQuestionnaireID(questionnaireID);
            map.put("success", true);
            map.put("questionnaire", questionnaire);
            map.put("questionList", questionList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/editQuestionnaire")
    @ApiOperation("编辑问卷-修改问卷信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endTime",value = "截止时间",required = true,dataType = "String"),
            @ApiImplicitParam(name = "questionnaireNote", value = "问卷说明", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endMessage",value = "结束页信息",required = true,dataType = "String")
    })
    public Map<String, Object> editQuestionnaire(@RequestParam Integer questionnaireID, @RequestParam String title,@RequestParam String endTime, @RequestParam String questionnaireNote,@RequestParam String endMessage) {
        Map<String, Object> map = new HashMap<>();
        try {
            Timestamp EndTime=Timestamp.valueOf(endTime);
            questionnaireService.editQuestionnaire(questionnaireID, title, EndTime, questionnaireNote,endMessage);
            map.put("success", false);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/createQuestionnaire")
    @ApiOperation("问卷操作-创建问卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kind", value = "问卷类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userID", value = "创建者ID", required = true, dataType = "int")
    })
    public Map<String, Object> createQuestionnaire(@RequestParam Integer kind, @RequestParam Integer userID) {
        Map<String, Object> map = new HashMap<>();
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        try {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setTitle("普通问卷");
            questionnaire.setQuestionnaireNote("为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们，我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！");
            questionnaire.setKind(kind);
            questionnaire.setCreateTime(createTime);
            questionnaire.setMasterID(userID);
            questionnaire.setEndMessage("问卷到此结束，感谢您的参与！");
            questionnaireService.createQuestionnaire(questionnaire);
            Questionnaire recentQuestionnaire=questionnaireService.getRecentQuestionnaireCreateByUserID(userID);
            String encryptQuestionnaireID= UUID.randomUUID()+ DigestUtils.md5DigestAsHex(String.valueOf(recentQuestionnaire.getQuestionnaireID()).getBytes(StandardCharsets.UTF_8));
            recentQuestionnaire.setEncryptQuestionnaireID(encryptQuestionnaireID);
            questionnaireService.editEncryptQuestionnaireID(recentQuestionnaire.getQuestionnaireID(),encryptQuestionnaireID);
            map.put("success", true);
            map.put("recentQuestionnaire",recentQuestionnaire);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/createVoteQuestionnaire")
    @ApiOperation("创建投票问卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kind", value = "问卷类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userID", value = "创建者ID", required = true, dataType = "int")
    })
    public Map<String, Object> createVoteQuestionnaire(@RequestParam Integer kind, @RequestParam Integer userID) {
        Map<String, Object> map = new HashMap<>();
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        try {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setTitle("投票问卷");
            questionnaire.setQuestionnaireNote("为了给您提供更好的服务，希望您能抽出几分钟时间，将您的感受和建议告诉我们，我们非常重视每位用户的宝贵意见，期待您的参与！现在我们就马上开始吧！");
            questionnaire.setKind(kind);
            questionnaire.setCreateTime(createTime);
            questionnaire.setMasterID(userID);
            questionnaire.setEndMessage("问卷到此结束，感谢您的参与！");
            questionnaireService.createQuestionnaire(questionnaire);
            Questionnaire recentQuestionnaire=questionnaireService.getRecentQuestionnaireCreateByUserID(userID);
            String encryptQuestionnaireID= UUID.randomUUID()+ DigestUtils.md5DigestAsHex(String.valueOf(recentQuestionnaire.getQuestionnaireID()).getBytes(StandardCharsets.UTF_8));
            recentQuestionnaire.setEncryptQuestionnaireID(encryptQuestionnaireID);
            questionnaireService.editEncryptQuestionnaireID(recentQuestionnaire.getQuestionnaireID(),encryptQuestionnaireID);
            //todo

            map.put("success", true);
            map.put("recentQuestionnaire",recentQuestionnaire);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/addQuestion")
    @ApiOperation("创建问卷-添加问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "questionKind", value = "问题类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "questionNo", value = "题号", required = true, dataType = "int")
    })
    public Map<String, Object> addQuestion(@RequestParam Integer questionnaireID, @RequestParam Integer questionKind, @RequestParam Integer questionNo) {
        Map<String, Object> map = new HashMap<>();
        try {
            QuestionContent questionInfo = new QuestionContent();
            questionInfo.setQuestionnaireID(questionnaireID);
            questionInfo.setQuestionKind(questionKind);
            questionInfo.setQuestionNo(questionNo);
            questionnaireService.addQuestion(questionInfo);
            QuestionContent recentQuestion=questionnaireService.getRecentQuestionByQuestionnaireID(questionnaireID);
            map.put("success", true);
            map.put("recentQuestion",recentQuestion);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/editQuestion")
    @ApiOperation("编辑问卷-修改问题信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "requireSig", value = "是否必答", required = true, dataType = "int"),
            @ApiImplicitParam(name = "questionContent", value = "问题题干", required = true, dataType = "String"),
            @ApiImplicitParam(name = "questionNote", value = "题目备注", required = true, dataType = "String"),
            @ApiImplicitParam(name = "questionScore",value = "题目分数",required = true,dataType = "int")
    })
    public Map<String, Object> editQuestion(@RequestParam Integer questionContentID, @RequestParam Integer requireSig, @RequestParam String questionContent, @RequestParam String questionNote,@RequestParam Integer questionScore) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.editQuestion(questionContentID, requireSig, questionContent, questionNote,questionScore);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/delQuestion")
    @ApiOperation("编辑问卷-删除题目")
    @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int")
    public Map<String, Object> delQuestion(@RequestParam Integer questionContentID) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.delQuestionOptionRelatedToQuestion(questionContentID);
            questionnaireService.delScoreQuestionRelatedToQuestion(questionContentID);
            questionnaireService.delCompletionQuestionRelatedToQuestion(questionContentID);
            questionnaireService.delQuestion(questionContentID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @GetMapping("/showQuestionOptions")
    @ApiOperation("预览问卷-选择题的选项")
    @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int")
    public Map<String, Object> showQuestionOptions(@RequestParam Integer questionContentID) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<QuestionOption> questionOptionList = questionnaireService.getAllQuestionOptionOfQuestionByQuestionContentID(questionContentID);
            map.put("success", true);
            map.put("questionOptionList", questionOptionList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/setOptions")
    @ApiOperation("添加问题-添加选择题选项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "optionKind", value = "选项种类", required = true, dataType = "int"),
            @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "optionContent", value = "选项内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "leftVolume", value = "最大选择次数", required = true, dataType = "int"),
            @ApiImplicitParam(name = "isAnswer",value = "是否是答案",required = true,dataType = "int"),
            @ApiImplicitParam(name = "optionNo",value = "选项序号",required = true,dataType = "int")
    })
    public Map<String, Object> setOptions(@RequestParam Integer optionKind, @RequestParam Integer questionContentID, @RequestParam String optionContent, @RequestParam Integer leftVolume,@RequestParam Integer isAnswer,@RequestParam Integer optionNo) {
        Map<String, Object> map = new HashMap<>();
        try {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setQuestionContentID(questionContentID);
            questionOption.setOptionContent(optionContent);
            questionOption.setLeftVolume(leftVolume);
            questionOption.setOptionKind(optionKind);
            questionOption.setOptionNo(optionNo);
            questionOption.setIsAnswer(isAnswer);
            questionnaireService.setOptions(questionOption);
            QuestionOption recentQuestionOption=questionnaireService.getRecentQuestionOptionByQuestionContentIDAndOptionNo(questionContentID,optionNo);
            map.put("success", true);
            map.put("recentQuestionOption",recentQuestionOption);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/editOption")
    @ApiOperation("编辑问卷-修改选项信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionOptionID",value = "选项ID",required = true,dataType = "int"),
            @ApiImplicitParam(name = "optionKind",value = "选项类型",required = true,dataType = "int"),
            @ApiImplicitParam(name = "optionContent",value = "选项内容",required = true,dataType = "String"),
            @ApiImplicitParam(name = "leftVolume",value = "最大选择数",required = true,dataType = "int"),
            @ApiImplicitParam(name = "isAnswer",value = "是否设置为答案",required = true,dataType = "int"),
            @ApiImplicitParam(name = "optionNo",value = "选项序号",required = true,dataType = "int")
    })
    public Map<String ,Object> editOption(@RequestParam Integer questionOptionID,@RequestParam Integer optionKind, @RequestParam String optionContent, @RequestParam Integer leftVolume,@RequestParam Integer isAnswer,@RequestParam Integer optionNo){
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.editOption(questionOptionID,optionKind,optionContent,leftVolume,isAnswer,optionNo);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/delQuestionOption")
    @ApiOperation("编辑问卷-删除题目选项")
    @ApiImplicitParam(name = "questionOptionID", value = "选项ID", required = true, dataType = "int")
    public Map<String, Object> delQuestionOption(@RequestParam Integer questionOptionID) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.delQuestionOption(questionOptionID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @GetMapping("/showScoreQuestion")
    @ApiOperation("预览问卷-评分题的分数上限")
    @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int")
    public Map<String, Object> showScoreQuestion(@RequestParam Integer questionContentID){
        Map<String, Object> map = new HashMap<>();
        try {
            ScoreQuestion scoreQuestion = questionnaireService.getScoreQuestionByQuestionContentID(questionContentID);
            map.put("success", true);
            map.put("scoreQuestion", scoreQuestion);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/setScore")
    @ApiOperation("添加问题-添加评分题分数上限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "maxScore", value = "评分上限", required = true, dataType = "int"),
            @ApiImplicitParam(name = "startWord",value = "评分最低项",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endWord",value = "评分最高项",required = true,dataType = "String")
    })
    public Map<String, Object> setScore(@RequestParam Integer questionContentID, @RequestParam Integer maxScore,@RequestParam String startWord,@RequestParam String endWord) {
        Map<String, Object> map = new HashMap<>();
        try {
            ScoreQuestion scoreQuestion = new ScoreQuestion();
            scoreQuestion.setQuestionContentID(questionContentID);
            scoreQuestion.setMaxScore(maxScore);
            scoreQuestion.setStartWord(startWord);
            scoreQuestion.setEndWord(endWord);
            questionnaireService.setScore(scoreQuestion);
            ScoreQuestion recentScoreQuestion=questionnaireService.getRecentScoreQuestionByQuestionContentID(questionContentID);
            map.put("success", true);
            map.put("recentScoreQuestion",recentScoreQuestion);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/editScore")
    @ApiOperation("编辑问卷-修改评分题信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scoreQuestionID", value = "评分信息ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "maxScore", value = "评分上限", required = true, dataType = "int"),
            @ApiImplicitParam(name = "startWord",value = "评分最低项",required = true,dataType = "String"),
            @ApiImplicitParam(name = "endWord",value = "评分最高项",required = true,dataType = "String")
    })
    public Map<String,Object> editScore(@RequestParam Integer scoreQuestionID, @RequestParam Integer maxScore,@RequestParam String startWord,@RequestParam String endWord){
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.editScore(scoreQuestionID,maxScore,startWord,endWord);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/setCompletionAnswer")
    @ApiOperation("添加问题-设置考试填空题答案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionContentID",value = "题目ID",required = true,dataType = "int"),
            @ApiImplicitParam(name = "answer",value = "填空题答案",required = true,dataType = "String")
    })
    public Map<String,Object> setCompletionAnswer(@RequestParam Integer questionContentID,@RequestParam String answer){
        Map<String, Object> map = new HashMap<>();
        try {
            CompletionQuestion completionQuestion=new CompletionQuestion();
            completionQuestion.setQuestionContentID(questionContentID);
            completionQuestion.setAnswer(answer);
            questionnaireService.setCompletionAnswer(completionQuestion);
            CompletionQuestion recentCompletionQuestion=questionnaireService.getRecentCompletionQuestionByQuestionContentID(questionContentID);
            map.put("success",true);
            map.put("recentCompletionQuestion",recentCompletionQuestion);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/editCompletion")
    @ApiOperation("编辑问卷-修改考试填空题答案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "completionQuestionID",value = "填空题答案ID",required = true,dataType = "int"),
            @ApiImplicitParam(name = "answer",value = "答案",required = true,dataType = "String")
    })
    public Map<String,Object> editCompletion(@RequestParam Integer completionQuestionID,@RequestParam String answer){
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.editCompletion(completionQuestionID,answer);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/rankQuestion")
    @ApiOperation("问题排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "questionNo", value = "问题序号", required = true, dataType = "int")
    })
    public Map<String, Object> rankQuestion(@RequestParam Integer questionContentID, @RequestParam Integer questionNo) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.rankQuestion(questionContentID, questionNo);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @GetMapping("/notRubbish")
    @ApiOperation("问卷列表")
    public Map<String, Object> searchQuestionnaire(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        try {
            List<Questionnaire> questionnaireList = questionnaireService.getQuestionnaireListNotRubbishByUserID(userID);
            map.put("success", true);
            map.put("questionnaireList", questionnaireList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @GetMapping("/rubbish")
    @ApiOperation("回收站")
    public Map<String, Object> getRubbish(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        try {
            List<Questionnaire> rubbishList = questionnaireService.getQuestionnaireListIsRubbishByUserID(userID);
            map.put("success", true);
            map.put("rubbishList", rubbishList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/setRubbish")
    @ApiOperation("问卷收入回收站")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int")
    public Map<String, Object> setRubbish(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.setRubbish(questionnaireID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/recoverRubbish")
    @ApiOperation("问卷移出回收站")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int")
    public Map<String, Object> recoverRubbish(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.recoverRubbish(questionnaireID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/delRubbish")
    @ApiOperation("彻底删除问卷")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int")
    public Map<String,Object> delRubbish(@RequestParam Integer questionnaireID){
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.delRubbish(questionnaireID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/publishQuestionnaire")
    @ApiOperation("问卷操作-发布问卷")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷编号", required = true, dataType = "int")
    public Map<String, Object> publishQuestionnaire(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.publishQuestionnaire(questionnaireID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/openQuestionnaire")
    @ApiOperation("问卷操作-开启问卷")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷编号", required = true, dataType = "int")
    public Map<String, Object> openQuestionnaire(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionnaireService.openQuestionnaire(questionnaireID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/closeQuestionnaire")
    @ApiOperation("问卷操作-关闭问卷")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷编号", required = true, dataType = "int")
    public Map<String, Object> closeQuestionnaire(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            Timestamp endTime = new Timestamp(System.currentTimeMillis());
            questionnaireService.closeQuestionnaire(questionnaireID, endTime);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/copyQuestionnaire")
    @ApiOperation("问卷操作-复制问卷")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int")
    public Map<String, Object> copyQuestionnaire(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            Questionnaire questionnaire = questionnaireService.getQuestionnaireByQuestionnaireID(questionnaireID);
            List<QuestionContent> questionList = questionnaireService.getAllQuestionContentOfQuestionnaireByQuestionnaireID(questionnaireID);
            Integer userID=questionnaire.getMasterID();
            String title=questionnaire.getTitle();
            Timestamp endTime=questionnaire.getEndTime();
            Timestamp createTime=new Timestamp(System.currentTimeMillis());
            questionnaire.setCreateTime(createTime);
            questionnaire.setEndTime(endTime);
            questionnaire.setTitle(title+"-副本");
            questionnaireService.copyQuestionnaire(questionnaire);
            Questionnaire recentQuestionnaire=questionnaireService.getRecentQuestionnaireByUserID(userID);
            String encryptQuestionnaireID= UUID.randomUUID()+ DigestUtils.md5DigestAsHex(String.valueOf(recentQuestionnaire.getQuestionnaireID()).getBytes(StandardCharsets.UTF_8));
            Integer newQuestionnaireID=recentQuestionnaire.getQuestionnaireID();
            questionnaireService.editEncryptQuestionnaireID(newQuestionnaireID,encryptQuestionnaireID);
            if(questionList.size()!=0){
                for (QuestionContent question:questionList){
                    Integer kind=question.getQuestionKind();
                    Integer questionContentID=question.getQuestionContentID();
                    question.setQuestionnaireID(newQuestionnaireID);
                    questionnaireService.copyQuestion(question);
                    QuestionContent recentQuestion=questionnaireService.getRecentQuestionByQuestionnaireID(newQuestionnaireID);
                    Integer newQuestionContentID=recentQuestion.getQuestionContentID();
                    if(kind==4){//评分题
                        ScoreQuestion scoreQuestion=questionnaireService.getScoreQuestionByQuestionContentID(questionContentID);
                        scoreQuestion.setQuestionContentID(newQuestionContentID);
                        questionnaireService.setScore(scoreQuestion);
                    }
                    else if(kind==1 || kind==2){//选择题
                        List<QuestionOption> questionOptionList=questionnaireService.getAllQuestionOptionOfQuestionByQuestionContentID(questionContentID);
                        if(questionOptionList.size()!=0){
                            for(QuestionOption questionOption:questionOptionList){
                                questionOption.setQuestionContentID(newQuestionContentID);
                                questionnaireService.setOptions(questionOption);
                            }
                        }
                    }
                    else if(kind==3) {//填空题
                        CompletionQuestion completionQuestion=questionnaireService.getCompletionQuestionByQuestionContentID(questionContentID);
                        if(completionQuestion!=null){
                            completionQuestion.setQuestionContentID(newQuestionContentID);
                            questionnaireService.setCompletionAnswer(completionQuestion);
                        }
                    }
                }
            }
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/resetEncryptQuestionnaireID")
    @ApiOperation("重设链接")
    @ApiImplicitParam(name = "questionnaireID",value = "问卷ID",required = true,dataType = "int")
    public Map<String ,Object> resetEncryptQuestionnaireID(@RequestParam Integer questionnaireID){
        Map<String,Object> map=new HashMap<>();
        try {
            Questionnaire questionnaire=questionnaireService.getQuestionnaireByQuestionnaireID(questionnaireID);
            String encryptQuestionnaireID= UUID.randomUUID()+ DigestUtils.md5DigestAsHex(String.valueOf(questionnaire.getQuestionnaireID()).getBytes(StandardCharsets.UTF_8));
            questionnaireService.editEncryptQuestionnaireID(questionnaireID,encryptQuestionnaireID);
            map.put("encryptQuestionnaireID",encryptQuestionnaireID);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @GetMapping("/getOriginQuestionnaireID")
    @ApiOperation("根据加密ID获取原ID")
    @ApiImplicitParam(name = "encryptQuestionnaireID",value = "加密后的ID",required = true,dataType = "String")
    public Map<String,Object> getOriginQuestionnaireID(@RequestParam String encryptQuestionnaireID){
        Map<String,Object> map=new HashMap<>();
        try {
            Integer originQuestionnaireID=questionnaireService.getOriginQuestionnaireID(encryptQuestionnaireID);
            map.put("success",true);
            map.put("originQuestionnaireID",originQuestionnaireID);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

}