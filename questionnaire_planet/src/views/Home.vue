<template>
  <div>
  <div class="home">
      <div class="text-center">
        <v-btn
            class="btn1"
            color="#CFD8DC"
            x-large
            min-height="60px"
            min-width="180px"
            @click="manage"
            depressed
            dark
            outlined
        >
          <p class="use" >开始使用</p>
        </v-btn>
        <v-btn @click="down" class="btn2" fab outlined dark small>
          <v-icon >
            mdi-arrow-down-drop-circle-outline
          </v-icon>
        </v-btn>
        <div class="btn3">
          <template v-if="this.$store.state.login">
            <avatar ></avatar>
          </template>
          <template v-else>
            <v-btn
                color="indigo"
                dark
                :to="{name:'Login'}"
                outlined
            >
              登陆
            </v-btn>
          </template>
        </div>
        <svg t="1629969802896" class="staricon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3008" width="150" height="150"><path d="M808.832 478.592c-42.794667 57.685333-94.762667 117.504-153.770667 176.469333-219.989333 220.032-451.584 342.613333-540.16 254.037334-52.736-52.778667-32.938667-147.968 36.053334-266.112A384 384 0 0 1 643.029333 150.954667c118.101333-68.992 213.333333-88.789333 266.069334-36.053334 51.114667 51.157333 31.872 149.930667-35.84 266.666667a384.128 384.128 0 0 1-312.32 511.36 42.666667 42.666667 0 0 1-10.752-84.693333 298.794667 298.794667 0 0 0 258.645333-329.642667zM776.96 374.186667a298.666667 298.666667 0 1 0-402.901333 402.901333c70.314667-45.781333 147.072-108.757333 220.629333-182.314667 73.557333-73.557333 136.533333-150.272 182.272-220.586666zM289.536 825.088a384.469333 384.469333 0 0 1-90.581333-90.624c-28.8 60.714667-35.114667 102.869333-23.722667 114.304 10.837333 10.794667 52.650667 5.546667 114.304-23.68zM734.464 198.954667a386.176 386.176 0 0 1 90.581333 90.624c29.226667-61.653333 34.517333-103.509333 23.722667-114.346667-11.434667-11.392-53.589333-5.12-114.346667 23.722667z" p-id="3009" fill="#e6e6e6"></path></svg>
        <div style="position: absolute;top:20%;left: 20%">
        <h1 class="white--text" style="font-size: 100px;">问卷星球</h1>
        <h3  style="position: absolute;top:85%;color: #BDBDBD">专注于问卷体验的问卷平台</h3>
      </div>
        </div>
  </div>
    <div class="car">
    <v-carousel
        v-model="model"
        height="100%"
        hide-delimiter-background
        cycle
        interval="4000"
    >
      <v-carousel-item
          v-for="i in 3"
          height="100%"
          :key=i
      >
        <v-sheet height="100%" color="#CFD8DC">
          <div class="text-center" style="padding-top: 15%">
            <p class="text-lg-h2">{{intro[i-1]}}</p>
            <h3>
              {{intro1[i-1]}}
            </h3>
          </div>

        </v-sheet>
      </v-carousel-item>
    </v-carousel>
    </div>
  </div>
</template>

<script>
  import avatar from '../components/Avatar.vue'
  export default {
    data(){
      return{
        model:0,
        intro:[
          "问卷星球，不止问卷",
          "问卷星球，隐私为重",
          "问卷星球，简单高效",
        ],
        intro1:[
          "普通问卷/投票问卷/报名问卷/考试问卷/疫情打卡/\n多种问卷供您选择",
          "我们拥有完善的加密措施，为您提供优秀的隐私保护",
          "我们秉持简单高效的开发理念，不添加任何冗余功能，一切为了您的问卷服务"
        ],
      }
    },
    components: {
      avatar
    },
    methods:{
      manage(){
        if(this.$store.state.login){
          this.$router.push({path:'/QuestionnaireManage'})
        }else{
          window.alert("请先登陆")
        }
      },
      create(){
        if(this.$store.state.login){
          this.$router.push({path: '/normal/0'})
        }else{
          window.alert("请先登陆")
        }
      },
      down(){
        window.scrollTo({
          top:1000,
          behavior:"smooth"
        })
      }
    }
  }
</script>

<style>
.btn1{
  position: absolute;
  top:40%;
  left:11%;

}
.btn2{
  position: absolute;
  top:52%;
  left:50%
}
.btn3{
  position: absolute;
  top:1%;
  right:1%
}
::-webkit-scrollbar{
  display:none;
}
.home{
  width: 100%;
  height: 100vh;
  background: url(../assets/earth1.jpg) rgba(0, 0, 0, .5) no-repeat center center;
  background-blend-mode: multiply;
}
.use{
  font-size: 28px;
  margin-top: 15px;
}
.car{
  height: 80vh;
}
.staricon{
  position: absolute;
  left: 10%;
  top: 20%;
}

</style>
