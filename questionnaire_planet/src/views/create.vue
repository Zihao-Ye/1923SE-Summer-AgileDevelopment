<template>
  <v-app id="inspire">
  <v-app-bar
      app
      color="white"
      flat
    >
      <svg t="1630120164825" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1271" width="64" height="64"><path d="M808.832 478.592c-42.794667 57.685333-94.762667 117.504-153.770667 176.469333-219.989333 220.032-451.584 342.613333-540.16 254.037334-52.736-52.778667-32.938667-147.968 36.053334-266.112A384 384 0 0 1 643.029333 150.954667c118.101333-68.992 213.333333-88.789333 266.069334-36.053334 51.114667 51.157333 31.872 149.930667-35.84 266.666667a384.128 384.128 0 0 1-312.32 511.36 42.666667 42.666667 0 0 1-10.752-84.693333 298.794667 298.794667 0 0 0 258.645333-329.642667zM776.96 374.186667a298.666667 298.666667 0 1 0-402.901333 402.901333c70.314667-45.781333 147.072-108.757333 220.629333-182.314667 73.557333-73.557333 136.533333-150.272 182.272-220.586666zM289.536 825.088a384.469333 384.469333 0 0 1-90.581333-90.624c-28.8 60.714667-35.114667 102.869333-23.722667 114.304 10.837333 10.794667 52.650667 5.546667 114.304-23.68zM734.464 198.954667a386.176 386.176 0 0 1 90.581333 90.624c29.226667-61.653333 34.517333-103.509333 23.722667-114.346667-11.434667-11.392-53.589333-5.12-114.346667 23.722667z" p-id="1272" fill="#1296db"></path></svg>
                        <h3 class="blue--text" style="font-size: 30px;">问卷星球</h3>
                        

        <v-spacer></v-spacer>

      </v-container>
    </v-app-bar>
    <v-main style="background-color:#DCDCDC;">
      <v-container>
      <div class="center">
          <el-row type="flex" justify="center" :gutter="10">
          <el-col span="6">
            <v-card height=250 @click="jump(1)" :elevation="ele1" @mouseover="over(1)" @mouseleave="leave(1)">
              <div class="icon1" style="color:#6495ED">
                  <i class="iconfont icon-wenjuan" style="font-size:120px"></i>                
              <p class="type">普通问卷</p>
              </div>
            </v-card>
          </el-col>
          <el-col span="6">
            <v-card height=250 @click="jump(2)" :elevation="ele2" @mouseover="over(2)" @mouseleave="leave(2)">
              <div class="icon1"style="color:#48D1CC">
                  <i class="iconfont icon-menu_tpgl" style="font-size:120px"></i>                
              <p class="type">投票问卷</p>
              </div>
            </v-card>
          </el-col>
          <el-col span="6">
            <v-card height=250 @click="jump(3)" :elevation="ele3" @mouseover="over(3)" @mouseleave="leave(3)">
              <div class="icon1" style="color:#FF7F50">
                  <i class="iconfont icon-baomingbeifen2" style="font-size:120px"></i>                
              <p class="type">报名问卷</p>
              </div>
            </v-card>
          </el-col>
        </el-row>
        </br>
        <el-row  type="flex" justify="center" :gutter="10">
          <el-col  span="6">
            <v-card height=250 @click="jump(4)" :elevation="ele4" @mouseover="over(4)" @mouseleave="leave(4)">
              <div class="icon1" style="color:#FF0000">
                  <i class="iconfont icon-kaoshi" style="font-size:120px"></i>                
              <p class="type">考试问卷</p>
              </div>
            </v-card>
          </el-col>
          <el-col span="6">
            <v-card height=250 @click="jump(5)" :elevation="ele5" @mouseover="over(5)" @mouseleave="leave(5)">
              <div class="icon1" style="color:#228B22">
                  <i class="iconfont icon-yiqingguanli" style="font-size:120px"></i>                
              <p class="type">疫情打卡问卷</p>
              </div>
            </v-card>
          </el-col>
        </el-row>
      </div>
        
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
  export default {
    data () {
      return{
        ele1:0,
        ele2:0,
        ele3:0,
        ele4:0,
        ele5:0,
      }
    },
    methods:{
        jump(i) {
            switch (i) {
                case 1:
                    window.location.href="/normal";
                    break;
                case 2:
                    window.location.href="/vote";
                    break;
                case 3:
                    window.location.href="/sign";
                    break;
                case 4:
                    window.location.href="/exam";
                    break;
                case 5:
                    window.location.href="/covid";
                    break;
            
                default:
                    break;
            }
        },
        over(i){
          switch (i) {
            case 1:
              this.ele1=10
              break;
            case 2:
              this.ele2=10
              break;
            case 3:
              this.ele3=10
              break;
            case 4:
              this.ele4=10
              break;
            case 5:
              this.ele5=10
              break;
            default:
              break;
          }
          
        },
        leave(i){
          switch (i) {
            case 1:
              this.ele1=0
              break;
            case 2:
              this.ele2=0
              break;
            case 3:
              this.ele3=0
              break;
            case 4:
              this.ele4=0
              break;
            case 5:
              this.ele5=0
              break;
            default:
              break;
          }
        }
    }
  }
</script>

<style>
.icon1{
  width:45%;
    margin: auto;
    height:100%;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}
.center{
  width:75%;
    margin: auto;
    height:100%;
    position: absolute;
    left: 0;
    right: 0;
}
.type{
  font-size:20px;
  text-align: center;
}
</style>
