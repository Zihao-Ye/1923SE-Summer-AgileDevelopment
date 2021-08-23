package com.example.SESummer.Controller;

import com.example.SESummer.Entity.QuestionContent;
import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Entity.ScoreQuestion;
import com.example.SESummer.Service.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "问卷相关接口")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionNaireService;

    @PostMapping("/createQuestionnaire")
    @ApiOperation("问卷操作-创建问卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "String"),
            @ApiImplicitParam(name = "questionPwd", value = "问卷密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "kind", value = "问卷类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userID", value = "创建者ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "isPrivate", value = "是否公开", required = true, dataType = "int"),
            @ApiImplicitParam(name = "startTime", value = "问卷开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "问卷结束时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "questionnaireNote", value = "问卷说明", required = true, dataType = "String")
    })
    public Map<String, Object> createQuestionnaire(@RequestParam String title, @RequestParam String questionPwd, @RequestParam Integer kind, @RequestParam Integer userID, @RequestParam Integer isPrivate, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String questionnaireNote) {
        Map<String, Object> map = new HashMap<>();
        Timestamp createTime = new Timestamp(System.currentTimeMillis());

        try {
            Timestamp StartTime = Timestamp.valueOf(startTime);
            Timestamp EndTime = Timestamp.valueOf(endTime);
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setTitle(title);
            questionnaire.setKind(kind);
            questionnaire.setCreateTime(createTime);
            questionnaire.setQuestionPwd(questionPwd);
            questionnaire.setMasterID(userID);
            questionnaire.setStartTime(StartTime);
            questionnaire.setEndTime(EndTime);
            questionnaire.setIsPrivate(isPrivate);
            questionnaire.setQuestionnaireNote(questionnaireNote);
            questionNaireService.createQuestionnaire(questionnaire);
            map.put("success", true);
            map.put("message", "创建问卷成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "创建问卷失败");
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
            questionNaireService.addQuestion(questionInfo);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/setOptions")
    @ApiOperation("添加问题-添加选择题选项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionKind", value = "问题种类", required = true, dataType = "int"),
            @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int"),
            @ApiImplicitParam(name = "optionContent", value = "选项内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "leftVolume", value = "最大选择次数", required = true, dataType = "int")
    })
    public Map<String, Object> setOptions(@RequestParam Integer questionKind, @RequestParam Integer questionnaireContentID, @RequestParam String optionContent, @RequestParam Integer leftVolume) {
        Map<String, Object> map = new HashMap<>();
        try {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setQuestionContentID(questionnaireContentID);
            questionOption.setOptionContent(optionContent);
            questionOption.setLeftVolume(leftVolume);
            questionOption.setQuestionKind(questionKind);
            questionNaireService.setOptions(questionOption);
            map.put("success", true);
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
            @ApiImplicitParam(name = "maxScore", value = "评分上限", required = true, dataType = "int")
    })
    public Map<String, Object> setScore(@RequestParam Integer questionContentID, @RequestParam Integer maxScore) {
        Map<String, Object> map = new HashMap<>();
        try {
            ScoreQuestion scoreQuestion = new ScoreQuestion();
            scoreQuestion.setQuestionContentID(questionContentID);
            scoreQuestion.setMaxScore(maxScore);
            questionNaireService.setScore(scoreQuestion);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
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
            questionNaireService.rankQuestion(questionContentID, questionNo);
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
            List<Questionnaire> questionnaireList = questionNaireService.getQuestionnaireListNotRubbishByUserID(userID);
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
            List<Questionnaire> rubbishList = questionNaireService.getQuestionnaireListIsRubbishByUserID(userID);
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
            questionNaireService.setRubbish(questionnaireID);
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
            questionNaireService.recoverRubbish(questionnaireID);
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
            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            questionNaireService.publishQuestionnaire(questionnaireID, startTime);
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
            questionNaireService.openQuestionnaire(questionnaireID);
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
            questionNaireService.closeQuestionnaire(questionnaireID, endTime);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @GetMapping("/showQuestionnaireInfo")
    @ApiOperation("预览问卷-问卷信息与问卷的题目信息")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int")
    public Map<String, Object> showQuestionnaireInfo(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            Questionnaire questionnaire = questionNaireService.getQuestionnaireByQuestionnaireID(questionnaireID);
            List<QuestionContent> questionList = questionNaireService.getAllQuestionContentOfQuestionnaireByQuestionnaireID(questionnaireID);
            map.put("success", true);
            map.put("questionnaire", questionnaire);
            map.put("questionList", questionList);
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
            List<QuestionOption> questionOptionList = questionNaireService.getAllQuestionOptionOfQuestionByQuestionContentID(questionContentID);
            map.put("success", true);
            map.put("questionOptionList", questionOptionList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @GetMapping("/showScoreQuestion")
    @ApiOperation("预览问卷-评分题的分数上限")
    @ApiImplicitParam(name = "questionContentID", value = "问题ID", required = true, dataType = "int")
    public Map<String, Object> showScoreQuestion(@RequestParam Integer questionContentID) {
        Map<String, Object> map = new HashMap<>();
        try {
            ScoreQuestion scoreQuestion = questionNaireService.getScoreQuestionByQuestionContentID(questionContentID);
            map.put("success", true);
            map.put("scoreQuestion", scoreQuestion);
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
            @ApiImplicitParam(name = "questionPwd", value = "问卷密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "isPrivate", value = "是否公开", required = true, dataType = "int"),
            @ApiImplicitParam(name = "questionnaireNote", value = "问卷说明", required = true, dataType = "String")
    })
    public Map<String, Object> editQuestionnaire(@RequestParam Integer questionnaireID, @RequestParam String title, @RequestParam String questionPwd, @RequestParam Integer isPrivate, @RequestParam String questionnaireNote) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionNaireService.editQuestionnaire(questionnaireID, title, questionPwd, isPrivate, questionnaireNote);
            map.put("success", false);
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
            @ApiImplicitParam(name = "questionNote", value = "题目备注", required = true, dataType = "String")
    })
    public Map<String, Object> editQuestion(@RequestParam Integer questionContentID, @RequestParam Integer requireSig, @RequestParam String questionContent, @RequestParam String questionNote) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionNaireService.editQuestion(questionContentID, requireSig, questionContent, questionNote);
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
            questionNaireService.delQuestionOptionRelatedToQuestion(questionContentID);
            questionNaireService.delQuestion(questionContentID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/delQuestionOption")
    @ApiOperation("编辑问卷-删除题目选项")
    @ApiImplicitParam(name = "questionOptionID", value = "选项ID", required = true, dataType = "int")
    public Map<String, Object> delQuestionOption(@RequestParam Integer questionOptionID) {
        Map<String, Object> map = new HashMap<>();
        try {
            questionNaireService.delQuestionOption(questionOptionID);
            map.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
        }
        return map;
    }

    @PostMapping("/copyQuestionnaire")
    @ApiOperation("复制问卷")
    @ApiImplicitParam(name = "questionnaireID", value = "问卷ID", required = true, dataType = "int")
    public Map<String, Object> copyQuestionnaire(@RequestParam Integer questionnaireID) {
        Map<String, Object> map = new HashMap<>();
        try {
            Questionnaire questionnaire = questionNaireService.getQuestionnaireByQuestionnaireID(questionnaireID);
            List<QuestionContent> questionList = questionNaireService.getAllQuestionContentOfQuestionnaireByQuestionnaireID(questionnaireID);
            Integer userID=questionnaire.getMasterID();
            Timestamp startTime=Timestamp.valueOf("0000-00-00 00:00:00");
            Timestamp endTime=Timestamp.valueOf("0000-00-00 00:00:00");
            Timestamp createTime=new Timestamp(System.currentTimeMillis());
            questionnaire.setCreateTime(createTime);
            questionnaire.setStartTime(startTime);
            questionnaire.setEndTime(endTime);
            questionNaireService.copyQuestionnaire(questionnaire);
            Integer newQuestionnaireID=questionNaireService.getRecentQuestionnaireByUserID(userID).getQuestionnaireID();
            if(questionList.size()!=0){
                for (QuestionContent question:questionList){
                    Integer kind=question.getQuestionKind();
                    Integer questionContentID=question.getQuestionContentID();
                    question.setQuestionnaireID(newQuestionnaireID);
                    questionNaireService.copyQuestion(question);
                    Integer newQuestionContentID=questionNaireService.getRecentQuestionByQuestionnaireID(newQuestionnaireID).getQuestionContentID();
                    if(kind==4){//评分题
                        ScoreQuestion scoreQuestion=questionNaireService.getScoreQuestionByQuestionContentID(questionContentID);
                        scoreQuestion.setQuestionContentID(newQuestionContentID);
                        questionNaireService.setScore(scoreQuestion);
                    }
                    else if(kind==1 || kind==2){//选择题
                        List<QuestionOption> questionOptionList=questionNaireService.getAllQuestionOptionOfQuestionByQuestionContentID(questionContentID);
                        if(questionOptionList.size()!=0){
                            for(QuestionOption questionOption:questionOptionList){
                                questionOption.setQuestionContentID(newQuestionContentID);
                                questionNaireService.setOptions(questionOption);
                            }
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
}