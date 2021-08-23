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
        {{question.questionContent}}
      </v-card-title>
      <v-card-subtitle class="orange--text">
        单选 * 必做
      </v-card-subtitle>
      <v-card-subtitle>
        {{question.questionNote}}
      </v-card-subtitle>
      <v-container>
      <v-radio-group v-model="model0" mandatory>
        <v-radio
            v-for="(option,n) in options[i]"
            :key="n"
            :label="option.optionContent"
            :value="n"
        ></v-radio>
      </v-radio-group>
      </v-container>
      </template>
    <!--单选非必做题-->
      <template v-else-if="question.questionKind===1 && question.requireSig===0">
      <v-card-title>
        {{question.questionContent}}
      </v-card-title>
      <v-card-subtitle class="blue-grey--text">
        单选 * 非必做
      </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
      <v-container>
        <v-radio-group v-model="model1" >
          <v-radio
              v-for="(option,n) in options[i]"
              :key="n"
              :label="option.optionContent"
              :value="n"
          ></v-radio>
        </v-radio-group>
      </v-container>
      </template>
    <!--多选必做题-->
      <template v-else-if="question.questionKind===2 && question.requireSig===1">
      <v-card-title>
        问题三
      </v-card-title>
      <v-card-subtitle class="orange--text">
        多选 * 必做
      </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
      <v-container>
        <el-checkbox-group v-model="model2" :min="1" >
          <el-checkbox
              v-for="(option,n) in options[i]"
              :key="n"
              :label="option.optionContent"
              border
          ></el-checkbox>
        </el-checkbox-group>
      </v-container>
      </template>
    <!--多选非必做题-->
      <template v-else-if="question.questionKind===2 && question.requireSig===0">
      <v-card-title>
        问题四
      </v-card-title>
      <v-card-subtitle class="blue-grey--text">
        多选 * 非必做
      </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
      <v-container>
        <el-checkbox-group v-model="model3">
        <el-checkbox
            v-for="(option,n) in options[i]"
            :key="n"
            :label="option.optionContent"
            border
        ></el-checkbox>
      </el-checkbox-group>
      </v-container>
      </template>
    <!--填空必做题-->
      <template v-else-if="question.questionKind===3 && question.requireSig===1">
      <v-card-title>
        问题五
      </v-card-title>
      <v-card-subtitle class="orange--text">
        填空 * 必做
      </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
      <v-container>
      <v-text-field
          v-model="text[0]"
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
        问题六
      </v-card-title>
      <v-card-subtitle class="blue-grey--text">
        填空 * 非必做
      </v-card-subtitle>
      <v-card-subtitle>
        {{question.questionNote}}
      </v-card-subtitle>
      <v-container>
        <v-text-field
            v-model="text[1]"
            label="填空"
            outlined
        ></v-text-field>
      </v-container>
      </template>
    <!--评分必做题-->
      <template v-else-if="question.questionKind===4 && question.requireSig===1">
      <v-card-title>
        问题七
      </v-card-title>
      <v-card-subtitle class="orange--text">
        评分 * 必做
      </v-card-subtitle>
      <v-card-subtitle>
        {{question.questionNote}}
      </v-card-subtitle>
      <v-container>
        <v-slider
            v-model="score[0]"
            :rules="scoreRules"
            color="orange"
            label="分数"
            min="1"
            max="100"
            thumb-label="always"
        ></v-slider>
      </v-container>
      </template>
    <!--评分非必做题-->
      <template v-else-if="question.questionKind===4 && question.requireSig===0">
      <v-card-title>
        问题八
      </v-card-title>
      <v-card-subtitle class="blue-grey--text">
        评分 * 非必做
      </v-card-subtitle>
      <v-card-subtitle>
        {{question.questionNote}}
      </v-card-subtitle>
      <v-container>
        <v-slider
            v-model="score[1]"
            color="green"
            label="分数"
            min="1"
            max="100"
            thumb-label="always"
        ></v-slider>
      </v-container>
      </template>
    </v-card>
  </v-card>
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
      },
      {
        questionContent:"问题二",
        questionNote:"备注",
        questionKind:1,
        requireSig:0,
      },
      {
        questionContent:"问题三",
        questionNote:"备注",
        questionKind:2,
        requireSig:1,
      },
      {
        questionContent:"问题四",
        questionNote:"备注",
        questionKind:2,
        requireSig:0,
      },
      {
        questionContent:"问题五",
        questionNote:"备注",
        questionKind:3,
        requireSig:1,
      },
      {
        questionContent:"问题六",
        questionNote:"备注",
        questionKind:3,
        requireSig:0,
      },
      {
        questionContent:"问题七",
        questionNote:"备注",
        questionKind:4,
        requireSig:1,
      },
      {
        questionContent:"问题八",
        questionNote:"备注",
        questionKind:4,
        requireSig:0,
      },
    ],
    options:[
      [
        {
          optionContent:"选项1",
        },
        {
          optionContent:"选项2",
        },
        {
          optionContent:"选项3",
        },
        {
          optionContent:"选项4",
        },
      ],
      [
        {
          optionContent:"选项1",
        },
        {
          optionContent:"选项2",
        },
        {
          optionContent:"选项3",
        },
        {
          optionContent:"选项4",
        },
      ],
      [
        {
          optionContent:"选项1",
        },
        {
          optionContent:"选项2",
        },
        {
          optionContent:"选项3",
        },
        {
          optionContent:"选项4",
        },
      ],
      [
        {
          optionContent:"选项1",
        },
        {
          optionContent:"选项2",
        },
        {
          optionContent:"选项3",
        },
        {
          optionContent:"选项4",
        },
      ],
    ],
    model0: null,
    model1:null,
    model2:[],
    model3:[],
    flag:false,
    text:[
      "",
      ""
    ],
    textRules:[val => (val || '').length > 0 || '必填题目'],
    score:[0,0],
    scoreRules:[v=> v >1||'必须选择评分']
  }),
  methods:{
    getQuestionnaire() {
      this.$http({
        method: "get",
        url: "/showQuestionnaireInfo",
        params: {
          userName: this.id,
          userPwd: this.password,
        },
      })
          .then((res) => {
            this.message = res.data.message;
            if (res.data.success) {
              this.$store.commit("setLogin");
              this.$store.commit("setUserID",this.id);
              this.$router.push(({name:'QuestionnaireManage'}))
            }
          })
          .catch((err) => {
            console.log(err);
          });
      this.snackbar = true;
    },
  }
}
</script>

<style scoped>

</style>