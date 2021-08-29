<template>
  <v-app id="inspire">
  <!---
    <v-app-bar
      app
      color="black"
      flat
    >
      <v-container class="py-0 fill-height">
        <v-avatar
          class="mr-10"
          color="grey darken-1"
          size="32"
        ></v-avatar>

        
        <v-spacer></v-spacer>

        
      </v-container>
    </v-app-bar>
-->
    <v-main style="background-color:#e6f0f9;">
      <v-container>
      
        <v-row>
          <v-col cols="auto">
            <v-sheet rounded="lg">
              <v-list color="transparent">
                

                <v-list-item>
                <v-btn text color="#2196F3" @click="addProblem(1)">
                  <i class="el-icon-circle-check"></i>  添加单选题
                </v-btn>
                </v-list-item>
                <v-list-item>
                <v-btn text color="#2196F3" @click="addProblem(2)">
                  <i class="el-icon-crop"></i>  添加多选题
                </v-btn>
                </v-list-item>
                <v-list-item>
                <v-btn text color="#2196F3" @click="addProblem(3)">
                  <i class="el-icon-edit-outline"></i>  添加填空题
                </v-btn>
                </v-list-item>
                <v-list-item>
                <v-btn text color="#2196F3" @click="addProblem(5)">
                  <i class="el-icon-location"></i>  添加定位题
                </v-btn>
                </v-list-item>

                <v-divider class="my-2"></v-divider>

                
                <v-list-item>
                <v-btn color="#C2DFFF" @click="save">
                  <i class="el-icon-back"></i>  保存并返回
                </v-btn>
                </v-list-item>
              </v-list>
            </v-sheet>
          </v-col>
          

          <v-col>
          
            <v-card
              id="naire"
              style="overflow-y:scroll;"
              min-height="80vh"
              max-height="98vh"
              rounded="lg"
            >
              <div style="width:80%;margin: auto;height:100%
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;">
                
              <!--  -->
              <v-text-field
                    v-model="title"
                    label="标题"
                    required
                ></v-text-field>
              <v-textarea
                name="input-7-4"
                label="问卷介绍"
                v-model="desciption"
                ></v-textarea>
                <v-row>
                  <v-col>
                    <v-switch v-model="haveEnd" class="ma-2" label="添加结束时间" @click="endChange"></v-switch>
                  </v-col>
                  <v-col v-if="haveEnd">
                
                    <el-date-picker
                      v-model="end"
                        type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        value-format="yyyy-MM-dd HH:mm"
                      start-placeholder="开始日期"
                      :default-time="['12:00:00']"
                    ></el-date-picker>
   <!--<el-date-picker
      v-model="endT"
      type="date"
      placeholder="选择日期"
      format="yyyy 年 MM 月 dd 日"
      value-format="yyyy-MM-dd">
    </el-date-picker>-->
                  </v-col>
                </v-row>
                
              <draggable v-model="problems"  chosenClass="chosen" forceFallback="false" animation="400" filter=".nodrag" @start="onStart" @end="onEnd">
                <transition-group>
                    <div v-for="(item,index) in problems" :key="item.no">
                    <div class="bord">
                        <div style="font-weight:900">
                        <div v-if="item.must" style="color:red;display:inline">
                            *
                        </div>
                        问题{{index+1}}--{{problems[index].name}}
                        <div v-if="item.type==1">(单选题)</div>
                        <div v-else-if="item.type==2">(多选题)</div>
                        <div v-else-if="item.type==3">(填空题)</div>
                        <div v-else-if="item.type==4">(评分题)</div>
                        <div v-else-if="item.type==5">(定位题)</div>
                        </div>
                        {{problems[index].desciption}}
                        </br>
                        <div v-if="problems[index].type===1">
                          <v-row v-for="(it,i) in problems[index].options" :key="i">
                            <v-col>
                            <v-radio
                                :key="i"
                                :label="problems[index].options[i].content"
                                readonly
                            ></v-radio>
                            </v-col>
                          </v-row>
                        </div>
                        <div v-if="problems[index].type===2">
                          <v-row v-for="(it,i) in problems[index].options" :key="i">
                            <v-col>
                                  <el-checkbox
                                      style="display:block;zoom:120%"
                                      :key="i"
                                      :label="problems[index].options[i].content"
                                      readonly
                                  ></el-checkbox>
                            </v-col>
                          </v-row>
                        </div>
                        <div v-if="problems[index].type===3">
                            <v-text-field
                                v-model="problems[index].answer"
                                label="答案"
                                required
                            ></v-text-field>
                        </div>
                        <div v-if="problems[index].type===4">
                          <v-row no-gutters style="align-items: center;">
                            <v-col>{{problems[index].lowDesc}}</v-col>
                            <v-col cols="6">
                            <el-rate
                              class="nodrag"
                              :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                              :max="problems[index].max">
                            </el-rate>
                            </v-col>
                            <v-col>{{problems[index].highDesc}}</v-col>
                          </v-row>
                            
                        </div>
                    <v-row no-gutters>
                      <v-col md="1">
                        <v-btn color="#FFCBA4" class="nodrag"  @click="alterProblem(index)">
                            编辑问题
                        </v-btn>    
                      </v-col>
                      <v-col md="1" offset-md="1">
                        <v-btn color="#FAAFBE" class="nodrag"  @click="deleteProblem(index)">
                            删除问题
                        </v-btn>    
                      </v-col>
                    </v-row>  
                    </br>
                    
                    
                    </div>
                    </br>
                    </div>
                    
                </transition-group>
              </draggable> 
              <v-text-field
                  v-model="endMess"
                  label="提交问卷后结束页信息"
                  filled
                  required
              ></v-text-field>
              </div>
            </v-card>
          </v-col>
        </v-row>

      <el-dialog :visible.sync="choose" :show-close="false" class="dialog">
        <v-card
        v-if="reveal==1 || reveal==2"
        style="overflow-y:scroll;"
        flat
        >
            <div style="font-weight:900;text-align: center;" v-if="reveal==1">单选题</div>
            <div style="font-weight:900;text-align: center;" v-if="reveal==2">多选题</div>
            <v-text-field
                v-model="problems[alter].name"
                label="问题"
                required
            ></v-text-field>
            <v-textarea
            solo
            name="input-7-4"
            label="问题描述"
            v-model="problems[alter].desciption"
            ></v-textarea>
            <v-row align="center" no-gutters>
              <v-col md="4">
                <v-switch v-model="problems[alter].must" class="ma-2" label="必做题"></v-switch>
              </v-col>
            
              
            </v-row>
            <div v-for="(item,index) in problems[alter].options">
              <v-row style="display: flex;align-items: center;">
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                    v-model="item.content"
                    label="选项"
                    required
                ></v-text-field>
              </v-col>
              <v-col>
                <v-btn color="#FAAFBE"  @click="deleteOption(index)">
                删除选项
                </v-btn>
              </v-col>
              </v-row>
                
            </div>
            <v-card-actions class="pt-0">
              <v-row no-gutters>
                <v-col md="4">
                  <v-btn
                  color="#C2DFFF"
                    @click="addOption">
                    添加选项
                    </v-btn>
                </v-col>
                <v-col>
                  <v-btn
                  color="#ADDFFF"
                    @click="finishProblem">
                    完成问题
                    </v-btn>
                </v-col>
              </v-row>
                
                
            </v-card-actions>
        </v-card>
      </el-dialog>   

      <el-dialog :visible.sync="fill" :show-close="false" class="dialog">
        <v-card
            v-if="reveal==3"
            flat
            >
            <div style="font-weight:900;text-align: center;" v-if="reveal==3">填空题</div>
            <v-text-field
                v-model="problems[alter].name"
                label="问题"
                required
            ></v-text-field>
            <v-textarea
                solo
                name="input-7-4"
                label="问题描述"
                v-model="problems[alter].desciption"
            ></v-textarea>
            <v-switch v-model="problems[alter].must" class="ma-2" label="必做题"></v-switch>
            <v-card-actions class="pt-0">
                <v-btn
                @click="finishProblem">
                完成问题
                </v-btn>
            </v-card-actions>
        </v-card>
      </el-dialog>   

      <el-dialog :visible.sync="rate" :show-close="false" class="dialog">
        <v-card
            v-if="reveal==4"
            flat
            >
            <div style="font-weight:900;text-align: center;" v-if="reveal==4">评分题</div>
            <v-text-field
                v-model="problems[alter].name"
                label="问题"
                required
            ></v-text-field>
            <v-textarea
                solo
                name="input-7-4"
                label="问题描述"
                v-model="problems[alter].desciption"
            ></v-textarea>
            <v-switch v-model="problems[alter].must" class="ma-2" label="必做题"></v-switch>
            <v-row>
                <v-col sm="2">
                <v-select
                  :items="[2,3,4,5,6,7,8,9,10]"
                  v-model="problems[alter].max"
                  label="最大值"
                ></v-select>
                </v-col>
                <v-col md="3">
                  <v-text-field
                    v-model="problems[alter].lowDesc"
                    label="最低评分描述"
                    required
                ></v-text-field>
                </v-col>
                <v-col md="3">
                  <v-text-field
                    v-model="problems[alter].highDesc"
                    label="最高评分描述"
                    required
                ></v-text-field>
                </v-col>
            </v-row>
            
            <v-card-actions class="pt-0">
                <v-btn
                color="#ADDFFF"
                @click="finishProblem">
                完成问题
                </v-btn>
            </v-card-actions>
        </v-card>
        </el-dialog>   

        <el-dialog :visible.sync="position" :show-close="false" class="dialog">
        <v-card
            v-if="reveal==5"
            flat
            >
            <div style="font-weight:900;text-align: center;" v-if="reveal==5">定位题</div>
            <v-text-field
                v-model="problems[alter].name"
                label="问题"
                required
            ></v-text-field>
            <v-textarea
                solo
                name="input-7-4"
                label="问题描述"
                v-model="problems[alter].desciption"
            ></v-textarea>
            <v-switch v-model="problems[alter].must" class="ma-2" label="必做题"></v-switch>
            <v-card-actions class="pt-0">
                <v-btn
                color="#ADDFFF"
                @click="finishProblem">
                完成问题
                </v-btn>
            </v-card-actions>
        </v-card>
      </el-dialog>   

      </v-container>
    </v-main>
  </v-app>
</template>

<script>
  import draggable from 'vuedraggable'
  import star from './Star.vue'
  export default {
    components: {
      draggable,
      star,
            },
    watch:{
      problems:{
        handler(){
          let i
          for(i=0;i<this.problems.length;i++){
              this.problems[i].no=i+1
          }
        },
        deep:true
      }
    },
    created() {
      this.load()
    },
    data() {
      return {
          //可删
      links: [
        'Dashboard',
        'Messages',
        'Profile',
        'Updates',
      ],
       
      position:false,
      choose:false,
      fill:false,
      rate:false,
      desciption:"",
      questionnaireNote:"",
      start:"0000-00-00 00:00",
      end:"0000-00-00 00:00:00",
      haveEnd:false,
      userID:this.$store.state.userID,
      qid:0,
      no:1,
      reveal:0,
      titleReveal:false,
      title:"",
      endMess:"",
      endT:'',
    //   problem:{
    //     id:0,
    //     no:1,
    //     name:"",
    //     type:0,
    //     options:[],
    //     must:false,
    //     multi:1,
    //     max:100,
    //     desciption:"",
    //   },
      option:{
        id:0,
        content:""
      },
      // deloptions:[],
      problems:[],
      alter:-1,
      optionDialog:false,
      optionDialog2:false,
      optionDialog3:false,
      problemDialog:false
      } 
    },
    methods:{
        //开始拖拽事件
      onStart(){
        this.drag=true;
      },
      //拖拽结束事件
       onEnd() {
       this.drag=false;
      },
      addProblem(i) {
        let addProblem={
            name:"",
            type:i,
            no:this.problems.length+1,
            options:[],
            max:5,
            lowDesc:"",
            highDesc:"",
            must:false,
            alter2:false,
            scoreId:"",
        }
        
        this.$http({
          method: "post",
          url: "/addQuestion",
          params: {
            questionKind:addProblem.type,
            questionNo:addProblem.no,
            questionnaireID:this.qid
          },
        })
          .then((res) => {
            if (res.data.success) {
              addProblem.id=res.data.recentQuestion.questionContentID
              this.$set(this.problems,this.problems.length,addProblem)
              let x=document.querySelector("#naire")
              setTimeout(function() {x.scrollTo(0,10000); }, 100)
            }
          })
          .catch((err) => {
            console.log(err);
          });
        
      },
      alterProblem(i) {
        this.reveal=this.problems[i].type
        this.alter=i
        switch (this.problems[i].type) {
          case 1:
            this.choose=true
            break;
          case 2:
            this.choose=true
            break;
          case 3:
            this.fill=true
            break;
          case 4:
            this.rate=true
            break;
          case 5:
            this.position=true
            break;
          default:
            break;
        }
        //   this.alter=1
        //   this.problem=JSON.parse( JSON.stringify(this.problems[i]) )
        //   this.reveal=this.problem.type
      },
      finishProblem() {
        let m=0
        if(this.problems[this.alter].must){
          m=1
        }
        this.$http({
          method:"post",
          url:"/editQuestion",
          params:{
            questionContent:this.problems[this.alter].name,
            questionContentID:this.problems[this.alter].id,
            questionNote:this.problems[this.alter].no,
            questionScore:0,
            requireSig:m
          },
        })
          .then((res) => {
            if(res.data.success){
              if(this.problems[this.alter].type==4){
                if(this.problems[this.alter].alter2==false){
                  this.$http({
                  method: "post",
                  url: "/setScore",
                  params: {
                    endWord:this.problems[this.alter].highDesc,
                    startWord:this.problems[this.alter].lowDesc,
                    maxScore:this.problems[this.alter].max,
                    questionContentID:this.problems[this.alter].id,
                  },
                })
                  .then((res) => {
                    if (res.data.success) {
                      this.problems[this.alter].scoreId=res.data.recentScoreQuestion.scoreQuestionID
                      this.problems[this.alter].alter2=true
                    }
                  })
                  .catch((err) => {
                    console.log(err);
                  });
                } else if(this.problems[this.alter].alter2){
                  this.$http({
                  method: "post",
                  url: "/editScore",
                  params: {
                    endWord:this.problems[this.alter].highDesc,
                    startWord:this.problems[this.alter].lowDesc,
                    maxScore:this.problems[this.alter].max,
                    scoreQuestionID:this.problems[this.alter].scoreId,
                  },
                })
                  .then((res) => {
                    if (res.data.success) {
                    }
                  })
                  .catch((err) => {
                    console.log(err);
                  });
                }
                
              }
              if(this.problems[this.alter].type==1 || this.problems[this.alter].type==2){
                let i
                for(i=0;i<this.problems[this.alter].options.length;i++){
                  this.$http({
                    method: "post",
                    url: "/editOption",
                    params: {
                      isAnswer:0,
                      leftVolume:0,
                      optionContent:this.problems[this.alter].options[i].content,
                      optionKind:1,
                      optionNo:i+1,
                      questionOptionID:this.problems[this.alter].options[i].id
                    },
                  })
                    .then((res) => {
                      if (res.data.success) {
                        this.$http({
                          method: "get",
                          url: "/showQuestionOptions",
                          params: {
                            questionContentID:this.problems[this.alter].id
                          },
                        })
                          .then((res) => {
                            console.log(res.data)
                            if (res.data.success) {
                            }
                          })
                          .catch((err) => {
                            console.log(err);
                          });
                      }
                    })
                    .catch((err) => {
                      console.log(err);
                    });
                }
                
              }
              this.reveal=0
              this.choose=false
              this.fill=false
              this.rate=false
              this.position=false
            }
          })
          .catch((err) => {
            console.log(err)
          })
          
      },
      addOption() {
          //add接口 普通1报名2考试3     编辑界面处理添加和删除，完成问题时传所有选项并修改
          let op={
              no:this.problems[this.alter].options.length+1,
              content:""
          }
          this.$http({
            method: "post",
            url: "/setOptions",
            params: {
              isAnswer:0,
              leftVolume:0,
              optionContent:op.content,
              optionKind:1,
              optionNo:op.no,
              questionContentID:this.problems[this.alter].id
            },
          })
            .then((res) => {
              console.log(res.data)
              if (res.data.success) {
                op.id=res.data.recentQuestionOption.questionOptionID
                console.log(op)
                this.$set(this.problems[this.alter].options,this.problems[this.alter].options.length,op)
                let x=document.querySelector(".dialog")
                setTimeout(function() {x.scrollTo(0,10000); }, 100)
              }
              
            })
            .catch((err) => {
              console.log(err);
            });

          

          
      },
    //   finishOption() {
    //       this.$set(this.problems[this.alter].options,this.problems[this.alter].options.length,this.option)
    //       this.option={
    //         id:0,
    //         content:""
    //         }
    //       let x=document.querySelector(".card2")
    //       setTimeout(function() {x.scrollTo(0,10000); }, 100)
    //   },
      deleteProblem(i) {
        this.$http({
          method: "post",
          url: "/delQuestion",
          params: {
            questionContentID:this.problems[i].id,
          },
        })
          .then((res) => {
            if (res.data.success) {
              this.problems.splice(i,1)
            }
          })
          .catch((err) => {
            console.log(err);
          });
      },
      deleteOption(i) {
          //delete接口
          this.$http({
            method: "post",
            url: "/delQuestionOption",
            params: {
              questionOptionID:this.problems[this.alter].options[i].id
            },
          })
            .then((res) => {
              if (res.data.success) {
                this.problems[this.alter].options.splice(i,1)
                let j
                for(j=0;j<this.problems[this.alter].options.length;j++){
                  this.problems[this.alter].options[j].no=j+1
                  this.$http({
                    method: "post",
                    url: "/editOption",
                    params: {
                      isAnswer:0,
                      leftVolume:0,
                      optionContent:this.problems[this.alter].options[j].content,
                      optionKind:1,
                      optionNo:this.problems[this.alter].options[j].no,
                      questionOptionID:this.problems[this.alter].options[j].id
                    },
                  })
                    .then((res) => {
                      if (res.data.success) {
                        
                      }
                    })
                    .catch((err) => {
                      console.log(err);
                    });
                }
              }
            })
            .catch((err) => {
              console.log(err);
            });
      },
      endChange() {
        if(this.haveEnd){
          let date=new Date()
          const Y = date.getFullYear() + '-';
          const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
          const D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
          const h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
          const m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes())+ ':';
          const s = '00'; // 秒
          this.end= Y + M + D + h + m+s;
        } else{
          this.end="0000-00-00 00:00:00"
        }
      },
      back() {
        window.location.href="/QuestionnaireManage"
      },
      loadPro(j,li){
        if(j==li.length){
          return
        } else{
            let p={
            id:li[j].questionContentID,
            no:j+1,
            name:li[j].questionContent,
            type:li[j].questionKind,
            desciption:li[j].questionNote,
            options:[],
            scoreId:0,
            max:5,
            lowDesc:"",
            highDesc:"",
          }
          this.$set(this.problems,j,p)
          console.log(j)
          if(this.problems[j].type==1||this.problems[j].type==2){
            console.log(j)
            this.$http({
                method: "get",
                url: "/showQuestionOptions",
                params: {
                  questionContentID:this.problems[j].id
                },
              })
                .then((res) => {
                  console.log(res.data)
                  if (res.data.success) {
                    let opli=res.data.questionOptionList
                    let k
                    for(k=0;k<opli.length;k++){
                      let op={
                        id:opli[k].questionOptionID,
                        no:opli[k].optionNo,
                        content:opli[k].optionContent,
                        isAnswer:opli[k].isAnswer,
                        optionKind:opli[k].optionKind,
                      }
                      console.log(j)
                      this.$set(this.problems[j].options,k,op)
                    }
                  }
                })
                .catch((err) => {
                  console.log(err);
                });
          }
          if(this.problems[j].type==4){
          this.$http({
            method: "get",
            url: "/showScoreQuestion",
            params: {
              questionContentID:this.problems[j].id
            },
          })
            .then((res) => {
              if (res.data.success) {
                let score=res.data.scoreQuestion
                this.problems[j].scoreId=score.scoreQuestionID
                this.problems[j].max=score.maxScore
                this.problems[j].lowDesc=score.startWord
                this.problems[j].highDesc=score.endWord
              }
            })
            .catch((err) => {
              console.log(err);
            });
          }
          this.loadPro(j+1,li)
        }
        
      },
      load() {
        if(this.$route.params.id!=0&&this.$route.params.id!=undefined){
          this.$http({
            method: "get",
            url: "/getOriginQuestionnaireID",
            params: {
              encryptQuestionnaireID:this.$route.params.id
            },
          })
            .then((res) => {
              if (res.data.success) {
                this.qid=res.data.originQuestionnaireID
                this.$http({
                  method:"get",
                  url:"/showQuestionnaireInfo",
                  params:{
                    questionnaireID:this.qid
                  },
                })
                  .then((res) => {
                    console.log(res.data)
                    if(res.data.success){
                        let qn=res.data.questionnaire
                        this.qid=qn.questionnaireID
                        this.title=qn.title
                        this.isPrivate=qn.isPrivate
                        this.desciption=qn.questionnaireNote
                        let timestamp=qn.endTime
                        console.log("t")
                        console.log(timestamp)
                        let date
                        if(timestamp!=null){
                          this.haveEnd=true
                         date=new Date(timestamp)
                        const Y = date.getFullYear() + '-';
                        const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                        const D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
                        const h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
                        const m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes())+ ':';
                        const s = '00'; // 秒
                        this.end= Y + M + D + h + m+s;
                        } 
                        
                        console.log(this.end)
                        this.endMess=qn.endMessage
                        let li=res.data.questionList
                        let j
                        this.loadPro(0,li)
                      
                    }
                  })
                  .catch((err) => {
                    console.log(err)
                  })
              }
            })
            .catch((err) => {
              console.log(err);
            });
          
        } else{
          this.$http({
          method:"post",
          url:"/createCovidQuestionnaire",
          params:{
            kind:5,
            userID:this.userID
          },
        })
          .then((res) => {
            console.log(res.data)
            if(res.data.success){
              this.qid=res.data.recentQuestionnaire.questionnaireID
              this.desciption=res.data.recentQuestionnaire.questionnaireNote
              this.endMess=res.data.recentQuestionnaire.endMessage
              this.title=res.data.recentQuestionnaire.title
              this.$http({
                  method:"get",
                  url:"/showQuestionnaireInfo",
                  params:{
                    questionnaireID:this.qid
                  },
                })
                  .then((res) => {
                    if(res.data.success){
                        let li=res.data.questionList
                        let j
                        this.loadPro(0,li)
                    }
                  })
                  .catch((err) => {
                    console.log(err)
                  })
            }
          })
          .catch((err) => {
            console.log(err)
          })
        }
        
      },
      
      save() {

      if(!this.haveEnd){
        this.end="0000-00-00 00:00:00"
      }else{
        this.end=this.end.slice(0,16)
        this.end+=':00'
      }
        this.$http({
          method: "post",
          url: "/editQuestionnaire",
          params: {
            endMessage:this.endMess,
            endTime:this.end,
            questionnaireID:this.qid,
            questionnaireNote:this.desciption,
            title:this.title
          },
        })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              this.$message.success("保存成功！");
              let i
              if(this.problems.length==0){
                window.location.href="/QuestionnaireManage"
              }else{
                for(i=0;i<this.problems.length;i++){
          this.$http({
          method: "post",
          url: "/rankQuestion",
          params: {
            questionContentID:this.problems[i].id,
            questionNo:i+1
          },
        })
          .then((res) => {
            
            if (res.data.success) {
        window.location.href="/QuestionnaireManage"
            }
          })
          .catch((err) => {
            console.log(err);
          });
        }
              }
        
            }
          })
          .catch((err) => {
            console.log(err);
          });
      },
      
    }
  }
</script>
