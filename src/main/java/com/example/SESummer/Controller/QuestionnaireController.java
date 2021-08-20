package com.example.SESummer.Controller;

import com.example.SESummer.Service.QuestionnaireServer;
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
    private QuestionnaireServer questionNaireServer;

    @PostMapping("/CreateQuestionnaire")
    @ApiOperation("问卷操作-创建问卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Title",value = "标题",required = true),
            @ApiImplicitParam(name = "CreateTime",value = "创建时间",required = true),
            @ApiImplicitParam(name = "Kind",value = "问卷类型",required = true),
            @ApiImplicitParam(name = "RecycleVolume",value = "回收量",required = true),
            @ApiImplicitParam(name = "QuestionPwd",value = "问卷密码",required = true),
            @ApiImplicitParam(name = "UserID",value = "用户ID",required = true),
    })
    public Map<String,Object> CreateQuestionnaire(@RequestParam String Title,@RequestParam Timestamp CreateTime,@RequestParam String QuestionPwd,@RequestParam Integer Kind,@RequestParam Integer RecycleVolume,@RequestParam Integer UserID){
        Map<String,Object> map = new HashMap<>();
        try {
            questionNaireServer.createQuestionnaire(Title,CreateTime,Kind,RecycleVolume,QuestionPwd,UserID);
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/AddQuestion")
    @ApiOperation("问卷操作-添加问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "QuestionnaireID",value = "问卷序号",required = true),
            @ApiImplicitParam(name = "QuestionID",value = "问题序号",required = true),
            @ApiImplicitParam(name = "QuestionKind",value = "问题类型",required = true),
            @ApiImplicitParam(name = "RequireSig",value = "是否必答",required = true),
            @ApiImplicitParam(name = "QuestionContent",value = "问题题干",required = true)

    })
    public Map<String,Object> addQuestion(@RequestParam Integer QuestionnaireID,@RequestParam Integer QuestionKind,@RequestParam Integer RequireSig,@RequestParam String QuestionContent){
        Map<String,Object> map = new HashMap<>();
        try {
            questionNaireServer.addQuestion(QuestionnaireID,QuestionKind,QuestionContent,0,RequireSig);
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @PostMapping("/SetOptions")
    @ApiOperation("问卷操作-设置选项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "QuestionnaireID",value = "问卷序号",required = true),
            @ApiImplicitParam(name = "QuestionID",value = "问题序号",required = true),
            @ApiImplicitParam(name = "OptionContent",value = "选项内容",required = true),
    })
    public Map<String,Object> setOptions(@RequestParam Integer QuestionnaireID,@RequestParam Integer QuestionID,@RequestParam String OptionContent){
        Map<String,Object> map = new HashMap<>();
        try {
            questionNaireServer.setOptions(QuestionnaireID,QuestionID,OptionContent,0);
            map.put("success",true);
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }
}
