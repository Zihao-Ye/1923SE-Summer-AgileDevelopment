<template>
  <div id= 'pdfDom'>
    <div>
      <v-card class="mx-auto" width="1000" elevation="10">
        <h1 class="text-center" style="padding-top: 40px">{{questionnaire.title}}</h1>
        <p class="text-center" style="width: 800px;padding-left: 150px">{{questionnaire.questionnaireNote}}</p>
        <h3 class="text-center" style="padding-top: 40px">已回收：{{questionnaire.recycleVolume}}份</h3>
        <v-container>
          <v-row>
            <v-spacer></v-spacer>
            <v-btn color="#546E7A" text @click="getDataUrl">
              导出数据
              <i class="el-icon-upload"></i>
            </v-btn>
          </v-row>
        </v-container>
        <v-card
            v-for="(question,i) in questions"
            :key="i"
            class="mx-auto"
            max-width="800"
            min-height=auto
            flat
        >
          <!--单选必做题-->
          <template v-if="question.questionKind===1 && question.requireSig===1">
            <v-card-title>
              <v-row v-if="questionnaire.kind===4">
                <p class="red--text"> * </p>
                {{question.questionNo}}. {{question.questionContent}}({{question.questionScore}}分)
              </v-row>
              <v-row v-else>
                <p class="red--text"> * </p>
                {{question.questionNo}}. {{question.questionContent}}
              </v-row>
            </v-card-title>
            <v-card-subtitle>
              {{question.questionNote}}
            </v-card-subtitle>
            <v-container>
              <v-radio-group v-model="radioModel[question.questionNo]">
                <v-radio
                    disabled
                    v-for="(option,n) in options[question.questionNo]"
                    :key="n"
                    :label="leftPerson(option)"
                    :value="n"
                    @change="radioAnswer[question.questionNo]=option;requirePlus(question)"
                ></v-radio>
              </v-radio-group>
            </v-container>

            <!--单选必做题数据分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <template>
                <v-tabs v-model="tab">
                  <v-tab>选项选择人数小计表</v-tab>
                  <v-tab @click="zhuCharts(question.questionNo+'01',question)">柱状图</v-tab>
                  <v-tab @click="bingCharts(question.questionNo+'02',question)">饼图</v-tab>
                  <v-tab @click="zheCharts(question.questionNo+'03',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab">
                  <v-tab-item>
                    <v-card class="tableCard" width="600" elevation="8">
                      <v-data-table
                          :headers="choosenHeaders"
                          :items="options[question.questionNo]"
                          :sort-desc="[false, true]"
                          multi-sort
                          class="elevation-1"
                          hide-default-footer>
                      </v-data-table>
                    </v-card>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'01'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'02'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'03'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                </v-tabs-items>
              </template>
            </v-card>
          </template>
          <!--单选非必做题-->
          <template v-else-if="question.questionKind===1 && question.requireSig===0">
            <v-card-title v-if="questionnaire.Kind===4">
              {{question.questionNo}}. {{question.questionContent}}({{question.questionScore}}分)
            </v-card-title>
            <v-card-title>
              {{question.questionNo}}. {{question.questionContent}}
            </v-card-title>
            <v-card-subtitle>
              {{question.questionNote}}
            </v-card-subtitle>
            <v-container>
              <v-radio-group v-model="radioModel[question.questionNo]" >
                <v-radio
                    disabled
                    v-for="(option,n) in options[question.questionNo]"
                    :key="n"
                    :label="option.optionContent"
                    :value="n"
                    @change="radioAnswer[question.questionNo]=option"
                ></v-radio>
              </v-radio-group>
            </v-container>
            <!--单选非必做题数据分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <template>
                <v-tabs v-model="tab">
                  <v-tab>选项选择人数小计表</v-tab>
                  <v-tab @click="zhuCharts(question.questionNo+'01',question)">柱状图</v-tab>
                  <v-tab @click="bingCharts(question.questionNo+'02',question)">饼图</v-tab>
                  <v-tab @click="zheCharts(question.questionNo+'03',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab">
                  <v-tab-item>
                    <v-card class="tableCard" width="600" elevation="8">
                      <v-data-table
                          :headers="choosenHeaders"
                          :items="options[question.questionNo]"
                          :sort-desc="[false, true]"
                          multi-sort
                          class="elevation-1"
                          hide-default-footer>
                      </v-data-table>
                    </v-card>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'01'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'02'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'03'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                </v-tabs-items>
              </template>
            </v-card>
          </template>
          <!--多选必做题-->
          <template v-else-if="question.questionKind===2 && question.requireSig===1">
            <v-card-title>
              <v-row v-if="questionnaire.kind===4">
                <p class="red--text"> * </p>
                {{question.questionNo}}. {{question.questionContent}}({{question.questionScore}}分)
                <p class="grey--text">[多选题]</p>
              </v-row>
              <v-row v-else>
                <p class="red--text"> * </p>
                {{question.questionNo}}. {{question.questionContent}}
                <p class="grey--text">[多选题]</p>
              </v-row>
            </v-card-title>
            <v-card-subtitle>
              {{question.questionNote}}
            </v-card-subtitle>
            <v-container>
              <el-checkbox-group v-model="checkboxModel[question.questionNo]" >
                <el-checkbox
                    disabled
                    v-for="(option,n) in options[question.questionNo]"
                    :key="n"
                    :label="option.optionContent"
                    @change="checkboxAnswer(option);requirePlus(question)"
                    style="display:block;zoom:120%"
                ></el-checkbox>
              </el-checkbox-group>
            </v-container>
            <!--多选必做题数据分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <template>
                <v-tabs v-model="tab">
                  <v-tab>选项选择人数小计表</v-tab>
                  <v-tab @click="zhuCharts(question.questionNo+'01',question)">柱状图</v-tab>
                  <v-tab @click="bingCharts(question.questionNo+'02',question)">饼图</v-tab>
                  <v-tab @click="zheCharts(question.questionNo+'03',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab">
                  <v-tab-item>
                    <v-card class="tableCard" width="600" elevation="8">
                      <v-data-table
                          :headers="choosenHeaders"
                          :items="options[question.questionNo]"
                          :sort-desc="[false, true]"
                          multi-sort
                          class="elevation-1"
                          hide-default-footer>
                      </v-data-table>
                    </v-card>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'01'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'02'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'03'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                </v-tabs-items>
              </template>
            </v-card>
          </template>
          <!--多选非必做题-->
          <template v-else-if="question.questionKind===2 && question.requireSig===0">
            <v-card-title>
              <v-row v-if="questionnaire.kind===4">
                {{question.questionNo}}. {{question.questionContent}}({{question.questionScore}}分)
                <p class="grey--text">[多选题]</p>
              </v-row>
              <v-row v-else>
                {{question.questionNo}}. {{question.questionContent}}
                <p class="grey--text">[多选题]</p>
              </v-row>
            </v-card-title>
            <v-card-subtitle>
              {{question.questionNote}}
            </v-card-subtitle>
            <v-container>
              <el-checkbox-group v-model="checkboxModel[question.questionNo]">
                <el-checkbox
                    disabled
                    v-for="(option,n) in options[question.questionNo]"
                    :key="n"
                    :label="option.optionContent"
                    @change="checkboxAnswer(option)"
                    style="display:block;zoom:120%"
                ></el-checkbox>
              </el-checkbox-group>
            </v-container>
            <!--多选非必做题数据分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <template>
                <v-tabs v-model="tab">
                  <v-tab>选项选择人数小计表</v-tab>
                  <v-tab @click="zhuCharts(question.questionNo+'01',question)">柱状图</v-tab>
                  <v-tab @click="bingCharts(question.questionNo+'02',question)">饼图</v-tab>
                  <v-tab @click="zheCharts(question.questionNo+'03',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab">
                  <v-tab-item>
                    <v-card class="tableCard" width="600" elevation="8">
                      <v-data-table
                          :headers="choosenHeaders"
                          :items="options[question.questionNo]"
                          :sort-desc="[false, true]"
                          multi-sort
                          class="elevation-1"
                          hide-default-footer>
                      </v-data-table>
                    </v-card>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'01'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'02'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container></v-container>
                    <div class="chartsClass" :id="question.questionNo+'03'" style="width: 600px;height:400px;"></div>
                  </v-tab-item>
                </v-tabs-items>
              </template>
            </v-card>
          </template>
          <v-divider></v-divider>
        </v-card>
        <!-- <div class="text-center" style="padding-top: 30px">
          <v-btn class="ma-2" color="info">
            提交
          </v-btn>
        </div> -->
      </v-card>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import moment from "moment";
export default {
  data: () => ({
    tab:[],
    choosenHeaders: [
      {
        text: '选项',
        align: 'start',
        sortable: false,
        value: 'optionContent',
      },
      { text: '选项选择人数', value: 'voteVolume' },
    ],
    htmlTitle: '页面导出PDF文件名',
    questionnaire:{},//问卷信息
    questions:[],//问卷包含问题列表
    options: {},//各个问题包含的选项列表数组
    radioModel:{},//单选题所选（用于ui）
    radioAnswer:{},//单选题所选（用于上交）
    optionAnswer:{},//多选题所选（用于上交）
    checkboxModel:{},//多选题所选（用于ui）
    userCompletionQuestionList:{},
    userLocateQuestionList:{},
    flag:false,
    text: {},//填空题所填
    textRules:[val => (val || '').length > 0 || '必填题目'],
    score:{},//评分提所选
    scoreRules:[v=> v >1||'必须选择评分'],
    maxScores:{},//评分题实体表
    require:{},
    requireNum:0,
    user:{
      userID:"",
      userName:"",
      userPwd:"visitor"
    },
    fillsuccess:false,
    location:{},//定位题所定位
    now:moment(),
    end:"2021-08-28T12:21:40.000+00:00",
  }),
  methods:{
    //图表实例
    drawcharts (id,kind,question) {
      // 基于准备好的dom，初始化echarts实例
      var that = this;
      if(kind=='line'||kind == 'bar'){
        // var tempText = question.questionContent
        var tempOptionsContent = [];
        var tempOptionsCount = [];
        for (var i=0;i<that.options[question.questionNo].length;i++)
        {
          tempOptionsContent.push(that.options[question.questionNo][i].optionContent);
          tempOptionsCount.push(that.options[question.questionNo][i].voteVolume);
        }
        var myChart = echarts.init(document.getElementById(id));
        // 绘制图表
        myChart.setOption({
          title: {
            text: ''
          },
          tooltip: {},
          legend: {
            data:['选择人数']
          },
          xAxis: {
            axisLabel : {//坐标轴刻度标签的相关设置。
              interval:0,
              // rotate:"45"
            },
            data: tempOptionsContent
          },
          yAxis: {},
          series: [{
            name: '选择人数',
            type: kind,
            data: tempOptionsCount
          }]
        });}
      else if(kind=='pie'){
        var tempOptionsContent2 = [];
        var myChart = echarts.init(document.getElementById(id));
        var tempPieData = [];
        for (var i=0;i<that.options[question.questionNo].length;i++)
        {
          var tempTuple = {value:0,name:''};
          tempTuple.value = that.options[question.questionNo][i].voteVolume;
          tempTuple.name = that.options[question.questionNo][i].optionContent;
          tempPieData.push(tempTuple)
          tempOptionsContent2.push(that.options[question.questionNo][i].optionContent);
        }
        myChart.setOption({
          //提示框组件,鼠标移动上去显示的提示内容
          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。
          },
          //图例
          legend: {
            //图例垂直排列
            orient: 'vertical',
            x: 'left',
            //data中的名字要与series-data中的列名对应，方可点击操控
            data:tempOptionsContent2
          },
          series : [
            {
              name: '选项饼图',
              type: 'pie',    // 设置图表类型为饼图
              radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
              avoidLabelOverlap: false,
              //标签
              label: {
                normal: {
                  show: true,
                  position: 'inside',
                  formatter: '{d}%',//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。{d}数据会根据value值计算百分比

                  textStyle : {
                    align : 'center',
                    baseline : 'middle',
                    fontFamily : '微软雅黑',
                    fontSize : 15,
                    fontWeight : 'bolder'
                  }
                },
              },

              data:tempPieData
            }
          ]
        })
      }
    },
    zhuCharts (id,question) {
      console.log(id)
      var that = this;
      setTimeout(function () {
        that.drawcharts(id,'bar',question)
      },1000);
    },
    bingCharts (id,question) {
      console.log(id)
      var that = this;
      setTimeout(function () {
        that.drawcharts(id,'pie',question)
      },1000);
    },
    zheCharts (id,question) {
      console.log(id)
      var that = this;
      setTimeout(function () {
        that.drawcharts(id,'line',question)
      },1000);
    },
    getQuestionnaire(questionnaireID) {
      this.$http({
        method: "get",
        url: "/showQuestionnaireInfo",
        params: {
          questionnaireID:questionnaireID
        },
      })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              this.htmlTitle = res.data.questionnaire.title
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
                  this.UserCompletionQuestion(question)
                }else if(question.questionKind===5){
                  this.$set(this.location,question.questionNo,"")
                  this.UserLocateQuestion(question)
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
    UserCompletionQuestion(question){
      this.$http({
        method: "get",
        url: "/getAllCompletionRecord",
        params: {
          questionContentID:question.questionContentID
        },
      }).then((res) => {
        console.log(res.data)
        if (res.data.success) {
          console.log(res.data);
          this.$set(this.userCompletionQuestionList,question.questionNo,res.data.userCompletionQuestionList)
        }
      })
          .catch((err) => {
            console.log(err);
          });
    },
    UserLocateQuestion(question){
      this.$http({
        method: "get",
        url: "/getAllLocateRecord",
        params: {
          questionContentID:question.questionContentID
        },
      }).then((res) => {
        console.log(res.data)
        if (res.data.success) {
          console.log(res.data);
          this.$set(this.userLocateQuestionList,question.questionNo,res.data.userLocateQuestionList)
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
              this.$set(this.maxScores,question.questionNo,res.data.scoreQuestion)
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
          questionnaireID:this.questionnaire.questionnaire,
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
          questionnaireID:this.questionnaire.questionnaire,
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
          questionnaireID:this.questionnaire.questionnaire,
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
    getLocation(id) {
      const self = this
      const AMap=window.AMap
      AMap.plugin('AMap.Geolocation', function () {
        var geolocation = new AMap.Geolocation({
          // 是否使用高精度定位，默认：true
          enableHighAccuracy: true,
          // 设置定位超时时间，默认：无穷大
          timeout: 10000,
        })

        geolocation.getCurrentPosition()
        AMap.event.addListener(geolocation, 'complete', onComplete);
        AMap.event.addListener(geolocation, 'error', onError);

        function onComplete(data) {
          console.log(data)
          // data是具体的定位信息
          self.$set(self.location,id,data.formattedAddress)
          //point.push(gpsPoint.lon)
          //self.centerPointer = point;
          //self.getAddress(point);

        }

        function onError(data) {
          // 定位出错
          console.log('定位失败错误：', data);
          // 调用ip定位
          self.getLngLatLocation();
        }
      })
      var GPS = {

        PI: 3.14159265358979324,
        x_pi: 3.14159265358979324 * 3000.0 / 180.0,
        delta: function (lat, lon) {
          var a = 6378245.0; //  a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
          var ee = 0.00669342162296594323; //  ee: 椭球的偏心率。
          var dLat = this.transformLat(lon - 105.0, lat - 35.0);
          var dLon = this.transformLon(lon - 105.0, lat - 35.0);
          var radLat = lat / 180.0 * this.PI;
          var magic = Math.sin(radLat);
          magic = 1 - ee * magic * magic;
          var sqrtMagic = Math.sqrt(magic);
          dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * this.PI);
          dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * this.PI);
          return {
            'lat': dLat,
            'lon': dLon
          };
        },
        //WGS-84 to GCJ-02
        gcj_encrypt: function (wgsLat, wgsLon) {
          if (this.outOfChina(wgsLat, wgsLon))
            return {
              'lat': wgsLat,
              'lon': wgsLon
            };

          var d = this.delta(wgsLat, wgsLon);
          return {
            'lat': wgsLat + d.lat,
            'lon': wgsLon + d.lon
          };

        },
        outOfChina: function (lat, lon) {
          if (lon < 72.004 || lon > 137.8347)
            return true;
          if (lat < 0.8293 || lat > 55.8271)
            return true;
          return false;
        },

        transformLat: function (x, y) {
          var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
          ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
          ret += (20.0 * Math.sin(y * this.PI) + 40.0 * Math.sin(y / 3.0 * this.PI)) * 2.0 / 3.0;
          ret += (160.0 * Math.sin(y / 12.0 * this.PI) + 320 * Math.sin(y * this.PI / 30.0)) * 2.0 / 3.0;
          return ret;
        },

        transformLon: function (x, y) {
          var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
          ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
          ret += (20.0 * Math.sin(x * this.PI) + 40.0 * Math.sin(x / 3.0 * this.PI)) * 2.0 / 3.0;
          ret += (150.0 * Math.sin(x / 12.0 * this.PI) + 300.0 * Math.sin(x / 30.0 * this.PI)) * 2.0 / 3.0;
          return ret;
        }
      };
    },
    getLngLatLocation() {
      const self=this
      const AMap=window.AMap
      AMap.plugin('AMap.CitySearch', function () {
        var citySearch = new AMap.CitySearch();
        citySearch.getLocalCity(function (status, result) {
          if (status === 'complete' && result.info === 'OK') {
            // 查询成功，result即为当前所在城市信息
            console.log('通过ip获取当前城市：', result)
            //逆向地理编码
            AMap.plugin('AMap.Geocoder', function () {
              var geocoder = new AMap.Geocoder({
                // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                city: result.adcode
              })

              var lnglat = result.rectangle.split(';')[0].split(',');
              self.centerPointer=lnglat
              geocoder.getAddress(lnglat, function (status, data) {
                if (status === 'complete' && data.info === 'OK') {
                  // result为对应的地理位置详细信息
                  console.log(data)
                }
              })
            })
          }
        })
      })
    },
    leftPerson(option){
      if(option.leftVolume>0){
        let left=option.leftVolume-option.voteVolume
        return option.optionContent+'\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+"(剩余"+left+"个名额)"
      }else{
        return option.optionContent
      }
    },
    toPdf(){
      if(this.$store.state.isPrint) {
        this.msgSuccess(this.$store.state.isPrint);
        this.getPdf();
        this.$store.commit("setNoPrint");
      }
    },
    PrefixInteger(num, n) {
      return (Array(n).join(0) + num).slice(-n);
    },
    getQuestionnaireID(){
      this.$http({
        method:'get',
        url:'/getOriginQuestionnaireID',
        params:{
          encryptQuestionnaireID:this.$route.params.id
        }
      }).then(res=>{
        console.log(res.data)
        if(res.data.success){
          this.getQuestionnaire(res.data.originQuestionnaireID)
        }
      })
    },
    getDataUrl(){
      this.$http({
        method: "post",
        url: "/DataOutput",
        params: {
          questionnaireID:this.questionnaire.questionnaireID,
          userID:this.$store.state.userID,
        },
      })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              let url="http://39.105.38.175/download/userid-"+this.$store.state.userID+"-"+this.questionnaire.questionnaireID+".xlsx"
              window.open(url)
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
  },
  computed:{
    submitValid() {
      let l = Object.keys(this.require).length
      return l === this.requireNum;
    },
    countDown(){
      return function(endDate) {
        let m1 = this.now
        let m2 = moment(endDate)
        var du = moment.duration(m2 - m1, 'ms'),
            hours = du.get('hours'),
            mins = du.get('minutes'),
            ss = du.get('seconds');
        if(hours<=0 && mins<=0 && ss<=0) {
          return "已超时"
        }else {
          return this.PrefixInteger(hours,2) + ':' + this.PrefixInteger(mins,2) + ':' + this.PrefixInteger(ss,2)
        }
      }
    }
  },
  created() {
    this.getQuestionnaireID()
  },
  mounted() {
    this.toPdf()
    setInterval(()=>{
      this.now = moment()
    },1000)
    var that = this;
    setTimeout(function () {
      that.drawcharts(103,'bar')
    },1000);
    // this.drawcharts()
  }
}
</script>

<style scoped>
.tableCard{
  margin-top: 50px;
  margin-left: 100px;
  margin-right: 100px;
}
.goback{
  top:2%;
  right:2%;
}
#pdfDom{
  display: flex;
  justify-content: center;
  align-items: center;
  background-color:#f8f5f1;
  height: 100%;
}
.timeHint{
  position: fixed;
  right:1%;
  top:50%;
  width: 100px;
  height: 70px;
  margin-top: -40px;
}
.chartsClass{
  margin-left: auto;
  margin-right: auto;
}
</style>