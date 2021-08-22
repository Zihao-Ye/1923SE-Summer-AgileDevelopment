<template>
  <v-card class="mx-auto" max-width="1000" elevation="10">
      <h1 class="text-center">问卷</h1>
    <!--单选必做题-->
    <v-card
        class="mx-auto"
        max-width="800"
        min-height=auto
        flat
    >
      <v-card-title>
        问题一
      </v-card-title>
      <v-card-subtitle class="orange--text">
        单选 * 必做
      </v-card-subtitle>
      <v-container>
      <v-radio-group v-model="model0" mandatory>
        <v-radio
            v-for="(option,n) in options"
            :key="n"
            :label="option.title"
            :value="n"
        ></v-radio>
      </v-radio-group>
      </v-container>
    </v-card>
    <!--单选非必做题-->
    <v-card
        class="mx-auto"
        max-width="800"
        flat
    >
      <v-card-title>
        问题二
      </v-card-title>
      <v-card-subtitle class="blue-grey--text">
        单选 * 非必做
      </v-card-subtitle>
      <v-container>
        <v-radio-group v-model="model1" >
          <v-radio
              v-for="(option,n) in options"
              :key="n"
              :label="option.title"
              :value="n"
          ></v-radio>
        </v-radio-group>
      </v-container>
    </v-card>
    <!--多选必做题-->
    <v-card
        class="mx-auto"
        max-width="800"
        flat
    >
      <v-card-title>
        问题三
      </v-card-title>
      <v-card-subtitle class="orange--text">
        多选 * 必做
      </v-card-subtitle>
      <v-container>
        <el-checkbox-group v-model="model2" :min="1" >
          <el-checkbox
              v-for="(option,n) in options"
              :key="n"
              :label="option.title"
              border
          ></el-checkbox>
        </el-checkbox-group>
      </v-container>
    </v-card>
    <!--多选非必做题-->
    <v-card
        class="mx-auto"
        max-width="800"
        flat
    >
      <v-card-title>
        问题四
      </v-card-title>
      <v-card-subtitle class="blue-grey--text">
        多选 * 非必做
      </v-card-subtitle>
      <v-container>
        <el-checkbox-group v-model="model3">
        <el-checkbox
            v-for="(option,n) in options"
            :key="n"
            :label="option.title"
            border
        ></el-checkbox>
      </el-checkbox-group>
      </v-container>
    </v-card>
    <!--填空必做题-->
    <v-card
        class="mx-auto"
        max-width="800"
        flat
    >
      <v-card-title>
        问题五
      </v-card-title>
      <v-card-subtitle class="orange--text">
        填空 * 必做
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
    </v-card>
    <!--填空非必做题-->
    <v-card
        class="mx-auto"
        max-width="800"
        flat
    >
      <v-card-title>
        问题六
      </v-card-title>
      <v-card-subtitle class="blue-grey--text">
        填空 * 非必做
      </v-card-subtitle>
      <v-container>
        <v-text-field
            v-model="text[1]"
            label="填空"
            outlined
        ></v-text-field>
      </v-container>
    </v-card>
    <!--评分必做题-->
    <v-card
        class="mx-auto"
        max-width="800"
        flat
    >
      <v-card-title>
        问题七
      </v-card-title>
      <v-card-subtitle class="orange--text">
        评分 * 必做
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
    </v-card>
    <!--评分非必做题-->
    <v-card
        class="mx-auto"
        max-width="800"
        flat
    >
      <v-card-title>
        问题八
      </v-card-title>
      <v-card-subtitle class="blue-grey--text">
        评分 * 非必做
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
    </v-card>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    items: [
      '选项1',
      '选项2',
      '选项3',
    ],
    questions:[],
    options:[
      {
        title:"选项1",
      },
      {
        title:"选项2",
      },
      {
        title:"选项3",
      },
      {
        title:"选项4",
      },
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
}
</script>

<style scoped>

</style>