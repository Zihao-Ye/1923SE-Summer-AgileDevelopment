<template>
<div class="container1">
  <div class="left-group">
    <v-btn text color="#2196F3" @click="addProblem(1)">
          添加单选题
      </v-btn>
    <v-btn text color="#2196F3" @click="addProblem(2)">
          添加多选题
      </v-btn>
      <v-btn text color="#2196F3" @click="addProblem(3)">
          添加填空题
      </v-btn>
      <v-btn text color="#2196F3" @click="addProblem(4)">
          添加评分题
      </v-btn>
      <v-btn text color="#2196F3" @click="save1">
          保存
      </v-btn>
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
  <div class="whole">
    <div class="ques">
      </br>
      <h1>{{title}}</h1>
      <v-btn text color="#2196F3" @click="titleReveal=true">
          编辑标题
      </v-btn>
<!-- 
<v-text-field
            v-model="problem.name"
            label="问题"
            required
        ></v-text-field>
 -->

      </br>
      <div style="font-size: 15px;">问卷介绍:{{desciption}}</div>


      </br>
      </br>
      <draggable v-model="problems"  chosenClass="chosen" forceFallback="false" animation="400" filter=".nodrag" @start="onStart" @end="onEnd">
      <transition-group>
        <div v-for="(item,index) in problems" :key="item.no">
          <div class="bord">
            <div style="font-weight:900">
              问题{{index+1}}--{{problems[index].name}}
            </div>
            {{problems[index].desciption}}
            </br>
              <div v-if="problems[index].type===1">
                  <v-radio-group v-model="problems[index].answer">
                  <v-radio
                      v-for="(it,i) in problems[index].options"
                      :key="i"
                      :label="problems[index].options[i].content"
                      :value="i"
                  ></v-radio>
                  </v-radio-group>
              </div>
              <div v-if="problems[index].type===2">
                  <v-radio-group v-model="problems[index].answer">
                  <v-checkbox
                      v-for="(it,i) in problems[index].options"
                      :key="i"
                      :label="problems[index].options[i].content"
                      :value="i"
                  ></v-checkbox>
                  </v-radio-group>
              </div>
              <div v-if="problems[index].type===4">
                <v-col cols="12">
                  <v-slider
                    class="nodrag"
                    :max="problems[index].max"
                    :thumb-size="24"
                    thumb-label="always"
                  ></v-slider>
                </v-col>
              </div>
              <div v-if="problems[index].type===3">
                <v-text-field
                      v-model="problems[index].answer"
                      label="答案"
                      required
                  ></v-text-field>
              </div>
          <div>
              <v-btn class="nodrag" text color="#2196F3" @click="alterProblem(index)">
                  编辑问题
              </v-btn>    
              <v-btn class="nodrag" text color="#2196F3" @click="deleteProblem(index)">
                  删除问题
              </v-btn>    
          </div>  
          
          
          </div>
        </br>
        </div>
        
      </transition-group>
  </draggable> 
      

      <v-card v-if="titleReveal"
      class="card"
      
      >
        <v-text-field
              v-model="title"
              label="标题"
              required
          ></v-text-field>
          <v-btn text color="#2196F3" @click="titleReveal=false">
              完成
          </v-btn>

      </v-card>

      <v-card
      v-if="reveal==1 || reveal==2"
      class="card2"
      >
        <v-text-field
            v-model="problem.name"
            label="问题"
            required
        ></v-text-field>
        <v-textarea
          solo
          name="input-7-4"
          label="问题描述"
          v-model="problem.desciption"
        ></v-textarea>
          <v-row align="center" justify="space-around">
          <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
          <v-row v-if="reveal==4">
            <v-col cols="12" sm="6" md="3">
              <v-text-field
                v-model="problem.multi"
                label="最大选择次数"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-row>
        <div v-for="(item,index) in problem.options">
            {{item.content}}
            <v-btn text color="#2196F3" @click="deleteOption(index)">
              删除选项
            </v-btn>
        </div>
        <v-text-field
                        v-model="option.content"
                        label="添加选项"
                    ></v-text-field>
        <v-card-actions class="pt-0">
            <v-btn
            text
            color="#2196F3"
            @click="finishOption">
            完成选项
            </v-btn>
            <v-btn
            text
            color="#2196F3"
            @click="finishProblem">
            完成问题
            </v-btn>
        </v-card-actions>
        <v-btn
          text
          color="#2196F3"
          @click="cancel">
          取消
        </v-btn>
      </v-card>

      <v-card
        v-if="reveal==3"
        class="card2"
        >
          <v-text-field
              v-model="problem.name"
              label="问题"
              required
          ></v-text-field>
          <v-textarea
            solo
            name="input-7-4"
            label="问题描述"
            v-model="problem.desciption"
          ></v-textarea>
          <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
          <v-card-actions class="pt-0">
              <v-btn
              text
              color="#2196F3"
              @click="finishProblem">
              完成问题
              </v-btn>
          </v-card-actions>
          <v-btn
            text
            color="#2196F3"
            @click="cancel">
            取消
        </v-btn>
      </v-card>

      <v-card
        v-if="reveal==4"
        class="card2"
        >
          <v-text-field
              v-model="problem.name"
              label="问题"
              required
          ></v-text-field>
          <v-textarea
            solo
            name="input-7-4"
            label="问题描述"
            v-model="problem.desciption"
          ></v-textarea>
          <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
          <v-row>
            <v-col cols="12" sm="6" md="3">
              <v-text-field
                v-model="problem.max"
                label="最大值"
              ></v-text-field>
            </v-col>
          </v-row>
          
          <v-card-actions class="pt-0">
              <v-btn
              text
              color="#2196F3"
              @click="finishProblem">
              完成问题
              </v-btn>
          </v-card-actions>
        <v-btn
          text
          color="#2196F3"
          @click="cancel">
          取消
        </v-btn>
      </v-card>

      <v-card
      v-if="reveal==9"
      class="card2"
      >
        <v-text-field
          v-model="title1"
          label="问卷标题"
          required
        ></v-text-field>
        <v-textarea
          solo
          name="input-7-4"
          label="问卷说明"
          v-model="questionnaireNote"
        ></v-textarea>
        <!-- <v-text-field
          v-model="start"
          label="开始时间"
          required
        ></v-text-field>
        <v-text-field
          v-model="end"
          label="结束时间"
          required
        ></v-text-field> -->
        <v-btn
          text
          color="#2196F3"
          @click="save0">
          保存
        </v-btn>
      </v-card>

      <v-dialog v-model="optionDialog"  width="500">
        <v-card>
          <v-card-title class="text-h5 grey lighten-2">
            选项重复
          </v-card-title>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="optionDialog = false"
            >
              ok
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="optionDialog2"  width="500">
        <v-card>
          <v-card-title class="text-h5 grey lighten-2">
            选项为空
          </v-card-title>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="optionDialog2 = false"
            >
              ok
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="optionDialog3"  width="500">
        <v-card>
          <v-card-title class="text-h5 grey lighten-2">
            没有选项
          </v-card-title>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="optionDialog3 = false"
            >
              ok
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="problemDialog"  width="500">
        <v-card>
          <v-card-title class="text-h5 grey lighten-2">
            题目为空
          </v-card-title>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              color="primary"
              text
              @click="problemDialog = false"
            >
              ok
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </div>
  <!-- <v-btn
    text
    color="#2196F3"
    @click="save">
    保存问卷
  </v-btn> -->
</div>    
</template>

<script>
  import draggable from 'vuedraggable'
  import {formatDate} from '../common/date.js';
  export default {
    components: {
      draggable,
            },
    filters: {
      formatDate(time) {
        // time = time * 1000
        let date = new Date(time)
        console.log(new Date(time))
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    data() {
      return {
      desciption:"",
      questionnaireNote:"",
      start:"0000-00-00 00:00",
      end:"0000-00-00 00:00",
      userID:this.$store.state.userID,
      qid:0,
      no:1,
      reveal:9,
      titleReveal:false,
      title:"",
      title1:"",
      problem:{
        id:0,
        no:1,
        name:"",
        type:0,
        options:[],
        must:false,
        multi:1,
        max:100,
        desciption:"",
      },
      option:{
        id:0,
        content:""
      },
      // deloptions:[],
      problems:[],
      alter:0,
      alertId:0,
      optionDialog:false,
      optionDialog2:false,
      optionDialog3:false,
      problemDialog:false
      } 
    },
    watch:{
      problems:{
        handler(){
          console.log(this.problems)
        },
        deep:true
      }
    },
    created() {
      this.load()
    },
    methods: {
      finishProblem() {
          if(this.problem.name.length==0) {
              this.problemDialog=true
          }else if((this.problem.type==1 || this.problem.type==2) && this.problem.options.length==0){
              this.optionDialog3=true
          }else {
              let m=0
              if(this.problem.must){
                m=1
              }
              this.$http({
                  method: "post",
                  url: "/editQuestion",
                  params: {
                    questionContent:this.problem.name,
                    questionContentID:this.problem.id,
                    questionNote:this.problem.desciption,
                    requireSig:m,
                    },
                  })
                  .then((res) => {
                    console.log("传问题信息")
                    console.log(res.data)
                    if (res.data.success) {
                  //   }
                  // })
                  // .catch((err) => {
                  //   console.log(err);
                  // });
                  console.log(this.problem.type)
              if(this.problem.type==1||this.problem.type==2){

                let j
                for(j=0;j<this.problems.length;j++){
                  if(this.problems[j].id==this.problem.id){
                    break;
                  }
                }
                let i=j
                let k
                for(j=0;j<this.problems[i].options.length;j++){
                  for(k=0;k<this.problem.options.length;k++){
                    if(this.problem.options[k].content==this.problems[i].options[j].content)
                    break
                  }
                   
                  if(k==this.problem.options.length){
                    console.log(this.problems[i].options[j])
                    this.$http({
                    method: "post",
                    url: "/delQuestionOption",
                    params: {
                      questionOptionID :this.problems[i].options[j].id
                      },
                    })
                    .then((res) => {
                      console.log("删除选项")
                      console.log(res.data)
                      if (res.data.success) {
                      }
                    })
                    .catch((err) => {
                      console.log(err);
                    });
                  }
                  
                }
                for(j=0;j<this.problem.options.length;j++){
                  for(k=0;k<this.problems[i].options.length;k++){
                    if(this.problem.options[j].content==this.problems[i].options[k].content)
                    break
                  }
                  if(k==this.problems[i].options.length){
                    this.$http({
                    method: "post",
                    url: "/setOptions",
                    params: {
                      //传选择题
                      leftVolume:this.problem.multi,
                      optionContent:this.problem.options[j].content,
                      questionContentID:this.problem.id,
                      questionKind:this.problem.type,
                      },
                    })
                    .then((res) => {
                      console.log("传选项")
                      console.log(res.data)
                      if (res.data.success) {
                        this.$http({
                          method:"get",
                          url:"/showQuestionOptions",
                          params:{
                            questionContentID:this.problem.id
                          },
                        })
                          .then((res) => {
                            console.log("问卷信息")
                            console.log(res.data)
                            if(res.data.success){
                              let li=res.data.questionOptionList
                              console.log(this.problem)
                              let index
                              for(index=0;index<this.problem.options.length;index++){
                                this.problem.options[index].id=li[index].questionOptionID
                              }
                              
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
                  }
                }




                // let j
                // for(j=0;j<this.problem.options.length;j++){
                //   if(this.problem.options[j].id==0 && this.deloptions.indexOf(this.problem.options[j])==-1){
                //     this.$http({
                //     method: "post",
                //     url: "/setOptions",
                //     params: {
                //       //传选择题
                //       leftVolume:this.problem.multi,
                //       optionContent:this.problem.options[j].content,
                //       questionContentID:this.problem.id,
                //       questionKind:this.problem.type,
                //       },
                //     })
                //     .then((res) => {
                //       console.log("传选项")
                //       console.log(res.data)
                //       if (res.data.success) {
                //         if(this.problem.options[j].id>0 && this.deloptions.indexOf(this.problem.options[j])>-1){
                //     this.$http({
                //     method: "post",
                //     url: "/delQuestionOption",
                //     params: {
                //       questionOptionID :this.problem.options[i].id
                //       },
                //     })
                //     .then((res) => {
                //       console.log("删除选项")
                //       console.log(res.data)
                //       if (res.data.success) {
                //         this.$http({
                //   method:"get",
                //   url:"/showQuestionOptions",
                //   params:{
                //     questionContentID:this.problem.id
                //   },
                // })
                //   .then((res) => {
                //     console.log(res.data)
                //     if(res.data.success){
                //       let li=res.data.questionOptionList
                //       let j
                //       for(j=0;j<li.length;j++){
                //         if(this.problem.options[j].id==0)
                //         this.question.options[j].id=li[li.length-1-j].questionOptionID
                //       }
                //     }
                //   })
                //   .catch((err) => {
                //     console.log(err)
                //   })
                //   console.log('选项')
                //   console.log(this.problem.options)
                //       }
                //     })
                //     .catch((err) => {
                //       console.log(err);
                //     });
                //   }
                //       }
                //     })
                //     .catch((err) => {
                //       console.log(err);
                //     });
                //   }
                // }
                
              }


              if(this.problem.type==4){
                this.$http({
                  method: "post",
                  url: "/setScore",
                  params: {
                    maxScore:this.problem.max,
                    questionContentID:this.problem.id,
                  },
                })
                  .then((res) => {
                    console.log("传评分")
                    console.log(res.data)
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
              clearTimeout(this.timer);  //清除延迟执行 
 
              this.timer = setTimeout(()=>{   //设置延迟执行
                  if(this.alter==0){
                this.problems.push(this.problem)
                
            } else if(this.alter==1){
                this.alter=0
                this.problems[this.alertId]=JSON.parse( JSON.stringify(this.problem) )
            }
                this.reveal=0
                this.problem={
                  id:0,
                  no:0,
                  name:"",
                  type:0,
                  options:[],
                  must:false,
                  multi:1,
                  max:100,
                  desciption:"",
                }
                this.option={
                  id:0,
                  content:""
                }
                console.log(this.problems)
              },2000);
              
          }
        // console.log(this.problems)
      },
      finishOption() {
          if(this.option.content.length==0){
              this.optionDialog2=true
          }else {
                // if(this.problem.options.indexOf(this.option)==-1){
                // this.$set(this.problem.options,this.problem.options.length,this.option)
                this.problem.options.push(JSON.parse( JSON.stringify(this.option) ))
                this.option={
                id:0,
                content:""
                }
            // } else{
            //     this.optionDialog=true
            // }
          }
          
      },
      deleteOption(i) {
        // deloptions.push(this.problem.options[i].id)
          // this.$http({
          //   method: "post",
          //   url: "/delQuestionOption",
          //   params: {
          //     questionOptionID :this.problem.options[i].id
          //     },
          //   })
          //   .then((res) => {
          //     console.log("删除选项")
          //     console.log(res.data)
          //     if (res.data.success) {
          //     }
          //   })
          //   .catch((err) => {
          //     console.log(err);
          //   });
          this.problem.options.splice(i,1)
      },
      deleteProblem(i) {
          this.$http({
            method: "post",
            url: "/delQuestion",
            params: {
              questionContentID :this.problems[i].id
              },
            })
            .then((res) => {
              console.log("删除题目")
              console.log(res.data)
              if (res.data.success) {
          this.problems.splice(i,1)
              }
            })
            .catch((err) => {
              console.log(err);
            });
      },
      alterProblem(i) {
          this.alter=1
          this.alertId=i
          this.problem=JSON.parse( JSON.stringify(this.problems[i]) )
          this.reveal=this.problem.type
          
      },
      addProblem(i) {
        this.problem.type=i
        this.problem.no=this.problems.length+1
        this.$http({
          method: "post",
          url: "/addQuestion",
          params: {
            questionKind:this.problem.type,
            questionNo:this.problem.no,
            questionnaireID:this.qid
          },
        })
          .then((res) => {
            console.log("传问题")
            console.log(res.data)
            if (res.data.success) {
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
                    let li=res.data.questionList
                    this.problem.id=li[li.length-1].questionContentID;
                    console.log(this.problem.id)
                    let p=JSON.parse( JSON.stringify(this.problem) )
                    this.problems.push(p)
                    console.log(this.problems[0].id)
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
        
      },
      //开始拖拽事件
      onStart(){
        this.drag=true;
      },
      //拖拽结束事件
       onEnd() {
       this.drag=false;
      },
      cancel() {
        this.reveal=0;
        this.problem={
          id:0,
          no:0,
          name:"",
          type:0,
          options:[],
          must:false,
          multi:1,
          max:100,
          desciption:"",
        };
        this.option={
          id:0,
          content:""
          }
      },
      save0() {
        console.log(this)
        this.$http({
          method: "post",
          url: "/createQuestionnaire",
          params: {
            title:this.title1,
            userID:this.userID,
            questionnaireNote:this.questionnaireNote,
            isPrivate:0,
            kind:1,
            questionPwd :0,
            startTime:this.start,
            endTime:this.end
          },
        })
          .then((res) => {
            console.log("传基础信息")
            console.log(res.data)
            if (res.data.success) {
              this.$http({
                method:"get",
                url:"/notRubbish",
                params:{
                },
              })
                .then((res) => {
                  console.log(res.data)
                  if(res.data.success){
                    let li=res.data.questionnaireList
                    this.qid=li[0].questionnaireID
                  }
                })
                .catch((err) => {
                  console.log(err)
                })
                this.title=this.title1
                this.desciption=this.questionnaireNote
                this.reveal=0
            }
          })
          .catch((err) => {
            console.log(err);
          });
        
      },
      load() {
        if(this.$route.params.id!=0){
          this.reveal=0
          this.qid=this.$route.params.id
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
              let li=res.data.questionList
              let j
              for(j=0;j<li.length;j++){
                this.problem={
                  id:li[j].questionContentID,
                  no:j+1,
                  name:li[j].questionContent,
                  type:li[j].questionKind,
                  multi:li[j].requireSig,
                  desciption:li[j].questionNote,
                  options:[],
                }
                this.problems.push(JSON.parse( JSON.stringify(this.problem) ))
              }
              console.log(1)
              console.log(this.problems)
              for(j=0;j<this.problems.length;j++){
                console.log("j")
                console.log(j)
                console.log(this.problems[j])
                if(this.problems[j].type==1 || this.problems[j].type==2){
                  console.log(2)
              console.log(this.problems)
                  this.$http({
                          method:"get",
                          url:"/showQuestionOptions",
                          params:{
                            questionContentID:this.problems[j].id
                          },
                        })
                          .then((res) => {
                            console.log("问卷信息")
                            console.log(res.data)
                            if(res.data.success){
                              let li=res.data.questionOptionList
                              console.log(this.problems[j])
                              let index
                              
                              for(index=0;index<li.length;index++){
                                let op={
                                  id:li[index].questionOptionID,
                                  content:li[index].optionContent
                                }
                                console.log(index)
                                console.log(j)
                                // this.problems[index].options.push(JSON.parse( JSON.stringify(op) ))
                                this.$set(this.problems[j-1].options,this.problems[j-1].options.length,op)
                              }
                              this.problem={
        id:0,
        no:1,
        name:"",
        type:0,
        options:[],
        must:false,
        multi:1,
        max:100,
        desciption:"",
      }
                              
                              }
                            
                          })
                          .catch((err) => {
                            console.log(err)
                          })
                }
              }
              console.log(this.problems)
            }
          })
          .catch((err) => {
            console.log(err)
          })
        }
      },
      save1() {
        let i
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
            }
          })
          .catch((err) => {
            console.log(err);
          });
        }
        
      }
      // save() {
      //   let i
      //   for(i=0;i<this.problems.length;i++){
      //     this.$http({
      //     method: "post",
      //     url: "/addQuestion",
      //     data: {
      //       questionKind:this.problems[i].type,
      //       questionNo:this.problems[i].no,
      //       questionnaireID:this.qid
      //     },
      //   })
      //     .then((res) => {
      //       console.log("传问题")
      //       console.log(res.data)
      //       if (res.data.success) {
      //         alert("保存成功")
      //       }
      //     })
      //     .catch((err) => {
      //       console.log(err);
      //     });
      //     if(this.problems[i].type==1||this.problems[i].type==4){
      //       let j
      //       for(j=0;j<this.problems[i].options.length;j++){
      //         this.$http({
      //         method: "post",
      //         url: "/setOptions",
      //         data: {

      //           //传选择题
      //           leftVolume:this.problems[i].multi,
      //           optionContent:this.problems[i].options[j],
      //           questionNo:this.problems[i].no,
      //           questionKind:this.problems[i].type,
      //           questionnaireID:this.qid
      //           },
      //         })
      //         .then((res) => {
      //           console.log("传选项")
      //           console.log(res.data)
      //           if (res.data.success) {
      //             alert("保存成功")
      //           }
      //         })
      //         .catch((err) => {
      //           console.log(err);
      //         });
      //       }
      //     }
      //     if(this.problems[i].type==3){
      //       this.$http({
      //         method: "post",
      //         url: "/setScore",
      //         data: {
      //           maxScore:this.problems[i].max,
      //           questionContentID:this.problems[i].no,
      //         },
      //       })
      //         .then((res) => {
      //           console.log("传评分")
      //           console.log(res.data)
      //           if (res.data.success) {
      //             alert("保存成功")
      //           }
      //         })
      //         .catch((err) => {
      //           console.log(err);
      //         });
      //     }
      //   }
      // }
  }
}
</script>

<style>
.card1 {
  position: absolute;
  margin:10px;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        height: 350px;
        width: 600px;
}
.card2 {
  position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        height: 650px;
        width: 400px;
}
.ques {
  margin-right: auto;
  margin-left: auto;
  width:80%;
}
.whole {
  flex: 1;
  width:80%;
  border-left: 1px solid #000; 
  border-right: 1px solid #000;
  box-sizing: border-box;/*为元素指定的任何内边距和边框都将在已设定的宽度和高度内进行绘制*/
  min-height: 100%;
  padding-bottom: 300px;
  overflow-y:scroll;
  overflow-x:hidden;
  height:100%
}
.line{
  float:right;
  width: 100%;
  height: 1px;
  margin-top: -0.5em;
  background:#d4c4c4;
  position: relative;
  text-align: center;
}
.item {
  padding: 6px;
  background-color: #fdfdfd;
  border: solid 1px #eee;
  margin-bottom: 10px;
  cursor: move;
} 
/*选中样式*/
.chosen {
  border: solid 1px #3089dc !important;
}
.nodrag{

}
.bord{
  border-style: dotted
}
.container1{
    position: absolute;
    width: 100%;
    display: flex;
    height:100%;  
    font-size: 20px;
}
.left-group,.right-group{
    display: flex;
    width:10%;
    flex-direction: column;
}
.mid-group{
    flex: 1;
    text-align: center;
}

</style>