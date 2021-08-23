<template>
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
          <v-radio-group v-model="radioModel[question.questionNo]">
            <v-radio
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="option.optionContent"
                :value="n"
                @change="radioAnswer[question.questionNo]=option;requirePlus(question)"
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
          <el-checkbox-group v-model="checkboxModel[question.questionNo]" >
            <el-checkbox
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="option.optionContent"
                border
                @change="checkboxAnswer(option);requirePlus(question)"
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
              @change="requirePlus(question)"
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
              @change="requirePlus(question)"
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
      <v-btn class="ma-2" color="info" :disabled="!submitValid" @click="submit">
        提交
      </v-btn>
      </div>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    questionnaire:{},
    questions:[],
    options: {},
    radioModel:{},
    radioAnswer:{},
    optionAnswer:{},
    checkboxModel:{},
    flag:false,
    text: {},
    textRules:[val => (val || '').length > 0 || '必填题目'],
    score:{},
    scoreRules:[v=> v >1||'必须选择评分'],
    maxScores:{},
    require:{},
    requireNum:0,
    user:{
      userID:"",
      userName:"",
      userPwd:"visitor"
    },
    fillsuccess:false,
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
              this.requireNum=0
              for(const question of this.questions){
                if(question.requireSig===1){
                  this.requireNum+=1
                }
                if(question.questionKind===1){
                  this.$set(this.radioModel,question.questionNo,null)
                  this.getOptions(question)
                }else if(question.questionKind===4){
                  this.$set(this.score,question.questionNo,0)
                  this.getMaxScore(question)
                }else if(question.questionKind===2){
                  this.$set(this.checkboxModel,question.questionNo,[])
                  this.getOptions(question)
                }else if(question.questionKind===3){
                  this.$set(this.text,question.questionNo,"")
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
          this.$set(this.options,question.questionNo,res.data.questionOptionList)
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
    },
    requirePlus(question){
      console.log("hahaha")
      this.$set(this.require,question.questionNo,1)
    },
    getUser(){
      this.user.userName=Math.random().toString(36).slice(-8)
      this.$http({
        method: "post",
        url: "/fakeRegister",
        params: {
          userName:this.user.userName,
          userPwd:this.user.userPwd
        },
      })
          .then((res) => {
            if (res.data.success) {
              this.fakeLogin()
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
    fakeLogin(){
      this.$http({
        method: "post",
        url: "/login",
        params: {
          userName: this.user.userName,
          userPwd: this.user.userPwd,
        },
      })
          .then((res) => {
            console.log(res.data)
            if(res.data.success){
              this.user.userID=res.data.user.userID
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
    submitChoose(option){
      this.$http({
        method: "post",
        url: "/choose",
        params: {
          questionContentID:option.questionContentID,
          questionOptionID:option.questionOptionID,
          questionnaireID:this.$route.params.id,
          userID:this.user.userID
        },
      })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              this.fillsuccess=true
            }else {
              this.fillsuccess=false
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
    submitCompletion(content,index){
      const question=this.questions[index]
      console.log(question)
      this.$http({
        method: "post",
        url: "/completion",
        params: {
          questionContentID:question.questionContentID,
          questionnaireID:this.$route.params.id,
          userID:this.user.userID,
          completionContent:content
        },
      })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              this.fillsuccess=true
            }else {
              this.fillsuccess=false
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
    submitScore(score,index){
      const question=this.questions[index]
      this.$http({
        method: "post",
        url: "/score",
        params: {
          questionContentID:question.questionContentID,
          questionnaireID:this.$route.params.id,
          userID:this.user.userID,
          score:score
        },
      })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              this.fillsuccess=true
            }else {
              this.fillsuccess=false
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
    submit(){
      for(const index in this.radioAnswer){
        console.log(this.radioAnswer[index])
        this.submitChoose(this.radioAnswer[index])
      }
      for(const index in this.optionAnswer){
        console.log(this.optionAnswer[index])
        this.submitChoose(this.optionAnswer[index])
      }
      for(const index in this.text){
        console.log(this.text[index])
        this.submitCompletion(this.text[index],index-1)
      }
      for(const index in this.score){
        console.log(this.score[index])
        this.submitScore(this.score[index],index-1)
      }
      this.$router.push(({name:'ThanksNormal'}))
    },
  },
  computed:{
    submitValid() {
        let l = Object.keys(this.require).length
        return l === this.requireNum;
      }
  },
  created() {
    this.getQuestionnaire()
    this.getUser()
  }
}
</script>

<style scoped>

</style>