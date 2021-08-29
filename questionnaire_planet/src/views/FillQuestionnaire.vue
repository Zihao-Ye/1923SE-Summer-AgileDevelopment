<template>
  <div class="fill" style="background-color:#e6f0f9;">
    <template v-if="this.questionnaire.kind===4">
      <v-card class="timeHint text-center"
              elevation="12"
              rounded
              color="#607D8B"
              dark
      >
        <h2 style="color: #EFEBE9">倒计时</h2>
        <h2 style="color: #FF9100">{{countDown(questionnaire.endTime)}}</h2>
      </v-card>
    </template>
  <v-card class="mx-auto" width="1000" elevation="10">
    <h1 class="text-center" style="padding-top: 40px">{{questionnaire.title}}</h1>
    <p class="text-center"  style="width: 800px;padding-left: 150px">{{questionnaire.questionnaireNote}}</p>
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
          <v-row>
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
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="leftPerson(option)"
                :value="n"
                @change="radioAnswer[question.questionNo]=option;requirePlus(question)"
            ></v-radio>
          </v-radio-group>
        </v-container>
      </template>
      <!--单选非必做题-->
      <template v-else-if="question.questionKind===1 && question.requireSig===0">
        <v-card-title>
          {{question.questionNo}}. {{question.questionContent}}
        </v-card-title>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-radio-group v-model="radioModel[question.questionNo]" >
            <v-radio
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="leftPerson(option)"
                :value="n"
                @change="radioAnswer[question.questionNo]=option"
            ></v-radio>
          </v-radio-group>
        </v-container>
      </template>
      <!--多选必做题-->
      <template v-else-if="question.questionKind===2 && question.requireSig===1">
        <v-card-title>
          <v-row>
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
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="leftPerson(option)"
                @change="checkboxAnswer(option);requirePlus(question)"
                style="display:block;zoom:120%"
            ></el-checkbox>
          </el-checkbox-group>
        </v-container>
      </template>
      <!--多选非必做题-->
      <template v-else-if="question.questionKind===2 && question.requireSig===0">
        <v-card-title>
          <v-row>
            <p>&nbsp&nbsp</p>
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
                v-for="(option,n) in options[question.questionNo]"
                :key="n"
                :label="leftPerson(option)"
                @change="checkboxAnswer(option)"
                style="display:block;zoom:120%"
            ></el-checkbox>
          </el-checkbox-group>
        </v-container>
      </template>
      <!--填空必做题-->
      <template v-else-if="question.questionKind===3 && question.requireSig===1">
        <v-card-title>
          <v-row>
            <p class="red--text"> * </p>
            {{question.questionNo}}. {{question.questionContent}}
          </v-row>
        </v-card-title>
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
          {{question.questionNo}}. {{question.questionContent}}
        </v-card-title>
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
          <v-row>
            <p class="red--text"> * </p>
            {{question.questionNo}}. {{question.questionContent}}
          </v-row>
        </v-card-title>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-card-actions class="pa-4">
          <v-container>
            <v-row>
              <h4>很不满意</h4>
              <el-rate
                  v-model="score[question.questionNo]"
                  :rules="scoreRules"
                  :max="maxScores[question.questionNo].maxScore"
                  :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                  @change="requirePlus(question)"
                  style="zoom:120%"
              ></el-rate>
              <h4 >很满意</h4>
            </v-row>
          </v-container>
        </v-card-actions>
      </template>
      <!--评分非必做题-->
      <template v-else-if="question.questionKind===4 && question.requireSig===0">
        <v-card-title>
          {{question.questionNo}}. {{question.questionContent}}
        </v-card-title>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-card-actions class="pa-4">
          <v-container>
            <v-row>
              <h4>很不满意</h4>
              <el-rate
                  v-model="score[question.questionNo]"
                  :max="maxScores[question.questionNo].maxScore"
                  :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                  style="zoom:120%"
              ></el-rate>
              <h4 >很满意</h4>
            </v-row>
          </v-container>
        </v-card-actions>
      </template>
      <!--定位必做题-->
      <template v-else-if="question.questionKind===5 && question.requireSig===1">
        <v-card-title>
          <v-row>
            <p class="red--text"> * </p>
            {{question.questionNo}}. {{question.questionContent}}
          </v-row>
        </v-card-title>
        <v-card-subtitle>
          （为获取准确定位，请给与浏览器定位权限）
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-text-field
              v-model="location[question.questionNo]"
              :rules="textRules"
              label="点击获取定位"
              required
              solo
              @change="requirePlus(question)"
              readonly
              @click="snackbar=true;no=question.questionNo"
          ></v-text-field>
        </v-container>
      </template>
      <!--定位非必做题-->
      <template v-else-if="question.questionKind===5 && question.requireSig===0">
        <v-card-title>
          {{question.questionNo}}. {{question.questionContent}}
        </v-card-title>
        <v-card-subtitle>
          （为获取准确定位，请给与浏览器定位权限）
        </v-card-subtitle>
        <v-card-subtitle>
          {{question.questionNote}}
        </v-card-subtitle>
        <v-container>
          <v-text-field
              v-model="location[question.questionNo]"
              label="获取定位"
              outlined
              solo
              readonly
              @click="snackbar=true;no=question.questionNo"
          ></v-text-field>
        </v-container>
      </template>
      <v-divider></v-divider>
      <v-snackbar
          v-model="snackbar"
          top
      >
        浏览器请求获取你的地址
        <template v-slot:action="{ attrs }">
          <v-btn
              color="orange"
              text
              v-bind="attrs"
              @click="getLocation(no)"
          >
            同意
          </v-btn>
          <v-btn
              color="pink"
              text
              v-bind="attrs"
              @click="snackbar = false"
          >
            拒绝
          </v-btn>
        </template>
      </v-snackbar>
    </v-card>
      <div class="text-center" style="padding-top: 30px">
      <v-btn class="ma-2" color="info" :disabled="!submitValid" @click="submit" >
        提交
      </v-btn>
      </div>
  </v-card>
    <v-dialog
        v-model="dialog"
        persistent
        max-width="200"
    >
      <v-card class="text-center">
      <h3>请先登录</h3>
      <v-btn text color="primary" :to="{name:'Login'}">
        登录
      </v-btn>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import moment from "moment";

export default {
  inject: ["reload"],
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
    timer:null,
    location:{},
    now:moment(),
    end:"2021-08-28T12:21:40.000+00:00",
    dialog:false,
    snackbar:false,
    no:0,
  }),
  methods:{
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
              this.requireNum=0
              this.htmlTitle = res.data.questionnaire.title
              this.questionnaire=res.data.questionnaire
              this.questions=res.data.questionList
                if(res.data.questionnaire.kind===4){
                  this.getTestOrder(this.questionnaire.questionnaireID)
                }else {
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
                    }else if(question.questionKind===5){
                      this.$set(this.location,question.questionNo,"")
                    }
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
              this.$store.commit('setUserID',res.data.user.userID)
              this.$store.commit('setLogin')
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
          questionnaireID:this.questionnaire.questionnaireID,
          userID:this.$store.state.userID
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
          questionnaireID:this.questionnaire.questionnaireID,
          userID:this.$store.state.userID,
          completionContent:content
        },
      })
          .then((res) => {
            console.log("填空题")
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
          questionnaireID:this.questionnaire.questionnaireID,
          userID:this.$store.state.userID,
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
    submitLocate(locate,index){
      const question=this.questions[index]
      console.log(question)
      this.$http({
        method: "post",
        url: "/locate",
        params: {
          questionContentID:question.questionContentID,
          questionnaireID:this.questionnaire.questionnaireID,
          userID:this.$store.state.userID,
          locate:locate
        },
      })
          .then((res) => {
            console.log("定位题")
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
    decreaseVolume(option){
      this.$http({
        method: "post",
        url: "/decreaseVolume",
        params: {
          questionContentID:option.questionContentID,
          questionOptionID:option.questionOptionID,
          userID:this.$store.state.userID
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
      this.$store.commit('setRadioAnswer',this.radioModel)
      this.$store.commit('setOptionAnswer',this.checkboxModel)
      this.$store.commit('setText',this.text)
      this.$store.commit('setQuestions',this.questions)
      for(const index in this.radioAnswer){
        console.log(this.radioAnswer[index])
        let option=this.radioAnswer[index]
        this.submitChoose(option)
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
      for(const index in this.location){
        console.log(this.location[index])
        this.submitLocate(this.location[index],index-1)
      }
      this.timer = setTimeout(() => {

        //设置延迟执行
        this.$http({
          method: "post",
          url: "/submit",
          params: {
            isSubmit:1,
            questionnaireID:this.questionnaire.questionnaireID,
            userID:this.$store.state.userID,
          },
        })
            .then((res) => {
              console.log(res.data)
              if(res.data.success){
                if(this.questionnaire.kind===1||this.questionnaire.kind===3||this.questionnaire.kind===5){
                  this.$router.push(({name:'ThanksNormal',params:{id:this.questionnaire.questionnaireID}}))
                }else if(this.questionnaire.kind===2){
                  this.$router.push({path:'/voteResult/'+this.$route.params.id})
                }else if(this.questionnaire.kind===4){
                  this.$router.push({path:'/testResult/'+this.$route.params.id})
                }
              }else if(res.data.failure){
                window.alert("名额已满")
                this.timer=setTimeout(()=>{
                  this.reload()
                },1000)
              }
            })
            .catch((err) => {
              console.log(err);
            });
      }, 1000);
    },
    getLocation(id) {
      this.snackbar=false
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
          self.getLngLatLocation(id);
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
    getLngLatLocation(id) {
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
              //self.$set(self.location,id,result.city)
              var lnglat = result.rectangle.split(';')[0].split(',');
              self.centerPointer=lnglat
              geocoder.getAddress(lnglat, function (status, data) {
                if (status === 'complete' && data.info === 'OK') {
                  // result为对应的地理位置详细信息
                  console.log(data)
                  let address=data.regeocode.addressComponent.province+data.regeocode.addressComponent.city+data.regeocode.addressComponent.district
                  self.$set(self.location,id,address)
                }
              })
            })
          }
        })
      })
    },
    toPdf(){
      if(this.$store.state.isPrint) {
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
    tologin(){
      this.timer = setTimeout(() => {
        //设置延迟执行
        if((this.questionnaire.kind===2||this.questionnaire.kind===3||this.questionnaire.kind===4)&&this.$store.state.login===false){
          this.dialog=true
        }else if((this.questionnaire.kind===1||this.questionnaire.kind===5)&&this.$store.state.login===false){
          this.getUser()
          this.dialog=false
        }else{
          this.dialog=false
        }
      }, 1000);
    },
    isFill(){
      this.$http({
        method:'get',
        url:'/haveFinish',
        params:{
          questionnaireID:this.questionnaire.questionnaireID,
          userID:this.$store.state.userID
        }
      }).then(res=>{
        console.log(res.data)
        if(res.data.success){
          if(res.data.haveFinish===1){
            this.$router.push({name:'IsFill'})
          }
        }
      })
    },
    initWebSocket: function () {
      // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
      this.websock = new WebSocket("ws://39.105.38.175:8080/api/websocket/"+this.$store.state.userID+"/"+this.questionnaire.questionnaireID);
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen: function () {
      console.log("WebSocket连接成功");
    },
    websocketonerror: function (e) {
      console.log("WebSocket连接发生错误");
    },
    websocketonmessage: function (e) {
      console.log(e.data)
      this.splitMessage(e.data)
    },
    websocketclose: function (e) {
      console.log("connection closed (" + e.code + ")");
    },
    splitMessage(message){
      let messages=message.split('#')
      for(let index in this.options){
        let optionList=this.options[index]
        console.log(optionList)
        for(let i=0;i<optionList.length;i++){
          if(optionList[i].questionOptionID===parseInt(messages[0])){
            console.log(this.options[index][i])
            this.options[index][i].voteVolume=parseInt(messages[1])
          }
        }
      }
    },
    getTestOrder(id){
      this.$http({
        method:'post',
        url:'/randomQuestionNo',
        params:{
          questionnaireID:id,
          userID:this.$store.state.userID
        }
      }).then(res=>{
        console.log(res.data)
        if(res.data.success){
          let questions=this.questions
          for(let i=0;i<questions.length;i++){
            questions[i].questionNo=res.data.testQuestionRankList[i].showNo
          }
          var compare = function (obj1, obj2) {
            var val1 = obj1.questionNo;
            var val2 = obj2.questionNo;
            if (val1 < val2) {
              return -1;
            } else if (val1 > val2) {
              return 1;
            } else {
              return 0;
            }
          }
          questions.sort(compare)
          this.questions=questions
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
            }else if(question.questionKind===5){
              this.$set(this.location,question.questionNo,"")
            }
          }
        }
      })
    }
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
          return "时间到"
          this.submit()
        }else {
          return this.PrefixInteger(hours,2) + ':' + this.PrefixInteger(mins,2) + ':' + this.PrefixInteger(ss,2)
        }
      }
    },
    leftPerson(option){
      return function(option){
        if(option.leftVolume>0){
          let left=option.leftVolume-option.voteVolume
          return option.optionContent+'\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+"(剩余"+left+"个名额)"
        }else{
          return option.optionContent
        }
      }
    },
  },
  created() {
      this.getQuestionnaireID()
  },
  mounted() {

    setInterval(()=>{
      this.now = moment()
    },1000);
    this.tologin();
    this.timer = setTimeout(() => {
      if(this.questionnaire.isVisitable===0){
        this.$router.push({name:'TurnedOff'})
      }
      if(this.$store.state.login){
        this.isFill()
      }
      if(this.questionnaire.kind===3){
        this.initWebSocket()
      }
    }, 1000);
  }
}
</script>

<style scoped>
.fill{
  display: flex;
  justify-content: center;
  align-items: center;
  background-color:#e6f0f9;
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
</style>