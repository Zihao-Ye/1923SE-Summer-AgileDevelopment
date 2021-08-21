package com.example.SESummer.Controller;

import com.example.SESummer.Entity.QuestionOption;
import com.example.SESummer.Entity.Questionnaire;
import com.example.SESummer.Entity.QuestionnaireContent;
import com.example.SESummer.Service.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "问卷相关接口")
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionNaireService;

    @PostMapping("/createQuestionnaire")
    @ApiOperation("问卷操作-创建问卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "标题",required = true),
            @ApiImplicitParam(name = "kind",value = "问卷类型",required = true),
            @ApiImplicitParam(name = "questionPwd",value = "问卷密码",required = true),
            @ApiImplicitParam(name = "userID",value = "创建者ID",required = true)
    })
    public Map<String,Object> createQuestionnaire(@RequestParam String title,@RequestParam String questionPwd,@RequestParam Integer kind,@RequestParam Integer userID){
        Map<String,Object> map = new HashMap<>();
        Timestamp createTime=new Timestamp(System.currentTimeMillis());
        try {
            Questionnaire questionnaire=new Questionnaire();
            questionnaire.setTitle(title);
            questionnaire.setKind(kind);
            questionnaire.setCreateTime(createTime);
            questionnaire.setQuestionPwd(questionPwd);
            questionnaire.setMasterID(userID);
            questionNaireService.createQuestionnaire(questionnaire);
            map.put("success",true);
            map.put("message","创建问卷成功");
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","创建问卷失败");
        }
        return map;
    }

    @PostMapping("/AddQuestion")
    @ApiOperation("问卷操作-添加问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireID",value = "问题序号",required = true),
            @ApiImplicitParam(name = "questionKind",value = "问题类型",required = true),
            @ApiImplicitParam(name = "requireSig",value = "是否必答",required = true),
            @ApiImplicitParam(name = "questionContent",value = "问题题干",required = true)

    })
    public Map<String,Object> addQuestion(@RequestParam Integer questionnaireID,@RequestParam Integer questionKind,@RequestParam Integer requireSig,@RequestParam String questionContent){
        Map<String,Object> map = new HashMap<>();
        try {
            QuestionnaireContent questionnaireContent=new QuestionnaireContent();
            questionnaireContent .setQuestionnaireID(questionnaireID);
            questionnaireContent.setQuestionContent(questionContent);
            questionnaireContent.setQuestionKind(questionKind);
            questionnaireContent.setRequireSig(requireSig);
            questionNaireService.addQuestion(questionnaireContent);
            map.put("success",true);
            map.put("message","添加问题成功");
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","添加问题失败");
        }
        return map;
    }

    @PostMapping("/SetOptions")
    @ApiOperation("问卷操作-设置选项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireContentID",value = "问题序号",required = true),
            @ApiImplicitParam(name = "optionContent",value = "选项内容",required = true),
    })
    public Map<String,Object> setOptions(@RequestParam Integer questionnaireContentID,@RequestParam String optionContent){
        Map<String,Object> map = new HashMap<>();
        try {
            QuestionOption questionOption=new QuestionOption();
            questionOption.setQuestionnaireContentID(questionnaireContentID);
            questionOption.setOptionContent(optionContent);
            questionNaireService.setOptions(questionOption);
            map.put("success",true);
            map.put("message","添加选项成功");
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("message","添加选项失败");
        }
        return map;
    }
}
