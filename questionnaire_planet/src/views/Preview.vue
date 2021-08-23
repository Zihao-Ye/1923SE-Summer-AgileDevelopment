<template>

  <div>
  <v-card class="mx-auto" max-width="1000" elevation="10">
    <h1 class="text-center">{{questionnaire.title}}</h1>
    <h3 class="text-center">{{questionnaire.questionnaireNote}}</h3>
    <!--单选必做题-->
    <v-card
        v-for="(question,i) in questions"
        :key="i"
        class="mx-auto"
        max-width="800"
        min-height=auto
        flat
    >
      <template v-if="question.questionKind===1 && question.requireSig===1">
        <v-card-title>
          {{question.questionNo}}.{{question.questionContent}}
        </v-card-title>
        <v-card-subtitle class="orange--text">
          单选 * 必做
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-radio-group v-model="radioModel[question.questionNo]" >
            <v-radio
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="option.optionContent"
                :value="n"
                @change="radioAnswer[question.questionNo]=option"
            ></v-radio>
          </v-radio-group>
        </v-container>
      </template>
      <!--单选非必做题-->
      <template v-else-if="question.questionKind===1 && question.requireSig===0">
        <v-card-title>
          {{question.questionNo}}.{{question.questionContent}}
        </v-card-title>
        <v-card-subtitle class="blue-grey--text">
          单选 * 非必做
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-radio-group v-model="radioModel[question.questionNo]" >
            <v-radio
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="option.optionContent"
                :value="n"
                @change="radioAnswer[question.questionNo]=option"
            ></v-radio>
          </v-radio-group>
        </v-container>
      </template>
      <!--多选必做题-->
      <template v-else-if="question.questionKind===2 && question.requireSig===1">
        <v-card-title>
          {{question.questionNo}}.{{question.questionContent}}
        </v-card-title>
        <v-card-subtitle class="orange--text">
          多选 * 必做
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <el-checkbox-group v-model="checkboxModel[question.questionNo]"  >
            <el-checkbox
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="option.optionContent"
                border
                @change="checkboxAnswer(option)"
            ></el-checkbox>
          </el-checkbox-group>
        </v-container>
      </template>
      <!--多选非必做题-->
      <template v-else-if="question.questionKind===2 && question.requireSig===0">
        <v-card-title>
          {{question.questionNo}}.{{question.questionContent}}
        </v-card-title>
        <v-card-subtitle class="blue-grey--text">
          多选 * 非必做
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <el-checkbox-group v-model="checkboxModel[question.questionNo]">
            <el-checkbox
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="option.optionContent"
                border
                @change="checkboxAnswer(option)"
            ></el-checkbox>
          </el-checkbox-group>
        </v-container>
      </template>
      <!--填空必做题-->
      <template v-else-if="question.questionKind===3 && question.requireSig===1">
        <v-card-title>
          {{question.questionNo}}.{{question.questionContent}}
        </v-card-title>
        <v-card-subtitle class="orange--text">
          填空 * 必做
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-text-field
              v-model="text[question.questionNo]"
              :rules="textRules"
              label="填空"
              required
              outlined
          ></v-text-field>
        </v-container>
      </template>
      <!--填空非必做题-->
      <template v-else-if="question.questionKind===3 && question.requireSig===0">
        <v-card-title>
          {{question.questionNo}}.{{question.questionContent}}
        </v-card-title>
        <v-card-subtitle class="blue-grey--text">
          填空 * 非必做
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-text-field
              v-model="text[question.questionNo]"
              label="填空"
              outlined
          ></v-text-field>
        </v-container>
      </template>
      <!--评分必做题-->
      <template v-else-if="question.questionKind===4 && question.requireSig===1">
        <v-card-title>
          {{question.questionNo}}.{{question.questionContent}}
        </v-card-title>
        <v-card-subtitle class="orange--text">
          评分 * 必做
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-slider
              v-model="score[question.questionNo]"
              :rules="scoreRules"
              color="orange"
              label="分数"
              min="1"
              :max="100"
              thumb-label="always"
          ></v-slider>
        </v-container>
      </template>
      <!--评分非必做题-->
      <template v-else-if="question.questionKind===4 && question.requireSig===0">
        <v-card-title>
          {{question.questionNo}}.{{question.questionContent}}
        </v-card-title>
        <v-card-subtitle class="blue-grey--text">
          评分 * 非必做
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-slider
              v-model="score[question.questionNo]"
              color="green"
              label="分数"
              min="1"
              :max="maxScores[question.questionNo].maxScore"
              thumb-label="always"
          ></v-slider>
        </v-container>
      </template>
    </v-card>
    <div class="text-center">
      <v-btn class="ma-2" color="info" >
        提交
      </v-btn>
    </div>
  </v-card>
    <v-btn
        absolute
        class="goback"
        fab
        dark
        small
        color="primary"
        :to="{path:'/QuestionnaireManage'}"
    >
      <v-icon dark>
        mdi-close
      </v-icon>
    </v-btn>
  </div>
</template>

<script>
export default {
  data: () => ({
    questionnaire:{
      title:"问卷",
      questionnaireNote:"备注"
    },
    questions:[
      {
        questionContent:"问题一",
        questionNote:"备注",
        questionKind:1,
        requireSig:1,
        questionNo:1,
      },
      {
        questionContent:"问题二",
        questionNote:"备注",
        questionKind:1,
        requireSig:0,
        questionNo:2,
      },
      {
        questionContent:"问题三",
        questionNote:"备注",
        questionKind:2,
        requireSig:1,
        questionNo:3,
      },
      {
        questionContent:"问题四",
        questionNote:"备注",
        questionKind:2,
        requireSig:0,
        questionNo:4,
      },
      {
        questionContent:"问题五",
        questionNote:"备注",
        questionKind:3,
        requireSig:1,
        questionNo:5,
      },
      {
        questionContent:"问题六",
        questionNote:"备注",
        questionKind:3,
        requireSig:0,
        questionNo:6,
      },
      {
        questionContent:"问题七",
        questionNote:"备注",
        questionKind:4,
        requireSig:1,
        questionNo:7,
      },
      {
        questionContent:"问题八",
        questionNote:"备注",
        questionKind:4,
        requireSig:0,
        questionNo:8,
      },
    ],
    options: {
      1: [{optionContent: "选项1",questionOptionID:1}, {optionContent: "选项2",questionOptionID:2}, {optionContent: "选项3",questionOptionID:3}, {optionContent: "选项4",questionOptionID:4},],
      2: [{optionContent: "选项1",questionOptionID:5}, {optionContent: "选项2",questionOptionID:6}, {optionContent: "选项3",questionOptionID:7}, {optionContent: "选项4",questionOptionID:8},],
      3: [{optionContent: "选项1",questionOptionID:9}, {optionContent: "选项2",questionOptionID:10}, {optionContent: "选项3",questionOptionID:11}, {optionContent: "选项4",questionOptionID:12},],
      4: [{optionContent: "选项1",questionOptionID:13}, {optionContent: "选项2",questionOptionID:14}, {optionContent: "选项3",questionOptionID:15}, {optionContent: "选项4",questionOptionID:16},]
    },
    radioModel:{
      1:null,
      2:null
    },
    radioAnswer:{},
    optionAnswer:{},
    checkboxModel:{

    },
    flag:false,
    text: {
      5:"",
      6:""
    },
    textRules:[val => (val || '').length > 0 || '必填题目'],
    score:{
      7:0,
      8:0,
    },
    scoreRules:[v=> v >1||'必须选择评分'],
    maxScores:{
      7:{maxScore:100},
      8:{maxScore: 50}
    },

  }),
  methods:{
    getQuestionnaire() {
      this.$http({
        method: "get",
        url: "/showQuestionnaireInfo",
        params: {
          questionnaireID:this.$route.params.id
        },
      })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              this.questionnaire=res.data.questionnaire
              this.questions=res.data.questionList
              for(const question of this.questions){
                if(question.questionKind===1){
                  this.radioModel[question.questionNo]=null
                  this.getOptions(question)
                }else if(question.questionKind===4){
                  this.score[question.questionNo]=0
                  this.getMaxScore(question)
                }else if(question.questionKind===2){
                  this.$set(this.checkboxModel,question.questionNo,[])
                  this.getOptions(question)
                }else if(question.questionKind===3){
                  this.text[question.questionNo]=""
                }
              }
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
    getOptions(question){
      this.$http({
        method: "get",
        url: "/showQuestionOptions",
        params: {
          questionContentID:question.questionContentID
        },
      }).then((res) => {
        console.log(res.data)
        if (res.data.success) {
          this.options[question.questionNo]=res.data.questionOptionList
        }
      })
          .catch((err) => {
            console.log(err);
          });
    },
    getMaxScore(question){
      this.$http({
        method: "get",
        url: "/showScoreQuestion",
        params: {
          questionContentID:question.questionContentID
        },
      })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              this.maxScore[question.questionNo]=res.data.scoreQuestion
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
    checkboxAnswer(option){
      if(this.optionAnswer.hasOwnProperty(option.questionOptionID)){
        delete this.optionAnswer[option.questionOptionID]
      }else{
        this.optionAnswer[option.questionOptionID]=option
      }
    }
  },
  created() {
   this.getQuestionnaire()
  }
}
</script>

<style scoped>
.goback{
  top:2%;
  right:2%;
}
</style>