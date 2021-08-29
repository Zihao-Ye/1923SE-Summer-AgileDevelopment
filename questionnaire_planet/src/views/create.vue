<template>
  <v-app id="inspire">
    <v-main style="background-color:#DCDCDC;">
      <v-container>
      </br>
      </br>
      </br>
      </br>
      <div class="center">
          <el-row type="flex" justify="center" :gutter="10">
          <el-col span="6">
            <v-card height=250 @click="jump(1)">
              <div class="icon1" style="color:#6495ED">
                  <i class="iconfont icon-wenjuan" style="font-size:120px"></i>                
              <p class="type">普通问卷</p>
              </div>
            </v-card>
          </el-col>
          <el-col span="6">
            <v-card height=250 @click="jump(2)">
              <div class="icon1"style="color:#48D1CC">
                  <i class="iconfont icon-menu_tpgl" style="font-size:120px"></i>                
              <p class="type">投票问卷</p>
              </div>
            </v-card>
          </el-col>
          <el-col span="6">
            <v-card height=250 @click="jump(3)">
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
            <v-card height=250 @click="jump(4)">
              <div class="icon1" style="color:#FF0000">
                  <i class="iconfont icon-kaoshi" style="font-size:120px"></i>                
              <p class="type">考试问卷</p>
              </div>
            </v-card>
          </el-col>
          <el-col span="6">
            <v-card height=250 @click="jump(5)">
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
