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
    <v-main class="grey lighten-3">
      <v-container>
      
        <v-row>
          <v-col cols="auto">
            <v-sheet rounded="lg">
              <v-list color="transparent">
                

                <v-list-item>
                <v-btn text color="#2196F3" @click="addProblem(1)">
                    添加单选题
                </v-btn>
                </v-list-item>
                <v-list-item>
                <v-btn text color="#2196F3" @click="addProblem(2)">
                    添加多选题
                </v-btn>
                </v-list-item>
                <v-list-item>
                <v-btn text color="#2196F3" @click="addProblem(3)">
                    添加填空题
                </v-btn>
                </v-list-item>
                <v-list-item>
                <v-btn text color="#2196F3" @click="addProblem(4)">
                    添加评分题
                </v-btn>
                </v-list-item>

                <v-divider class="my-2"></v-divider>

                <v-list-item>
                <v-btn text color="#2196F3" @click="save1">
                    保存
                </v-btn>
                </v-list-item>
              </v-list>
            </v-sheet>
          </v-col>
          

          <v-col>
          
            <v-card
              id="naire"
              style="overflow-y:scroll;"
              min-height="70vh"
              max-height="90vh"
              rounded="lg"
            >
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
                        <div v-else-if="item.type==2">(多选题，最大选择次数为{{problems[index].multi}})</div>
                        <div v-else-if="item.type==3">(填空题)</div>
                        <div v-else-if="item.type==4">(评分题)</div>
                        </div>
                        {{problems[index].desciption}}
                        </br>
                        <div v-if="problems[index].type===1">
                            <v-radio-group>
                            <v-radio
                                v-for="(it,i) in problems[index].options"
                                :key="i"
                                :label="problems[index].options[i].content"
                                readonly
                            ></v-radio>
                            </v-radio-group>
                        </div>
                        <div v-if="problems[index].type===2">
                            <v-radio-group>
                            <v-row no-gutters>
                              <v-col v-for="(it,i) in problems[index].options" :key="i" offset-mid="1">
                                <v-checkbox
                                
                                    :key="i"
                                    :label="problems[index].options[i].content"
                                    :value="i"
                                    readonly
                                ></v-checkbox>
                              </v-col>
                            </v-row>
                            </v-radio-group>
                        </div>
                        <div v-if="problems[index].type===3">
                            <v-text-field
                                v-model="problems[index].answer"
                                label="答案"
                                required
                            ></v-text-field>
                        </div>
                        <div v-if="problems[index].type===4">
                        <!--
                            <v-col cols="12">
                            <v-slider
                                class="nodrag"
                                :max="problems[index].max"
                                :thumb-size="24"
                                thumb-label="always"
                            ></v-slider>
                            </v-col>-->
                            <star :n="problems[index].max"></star>
                        </div>
                    <v-row no-gutters>
                      <v-col md="1">
                        <v-btn class="nodrag"  @click="alterProblem(index)">
                            编辑问题
                        </v-btn>    
                      </v-col>
                      <v-col md="1" offset-md="1">
                        <v-btn class="nodrag"  @click="deleteProblem(index)">
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
            </v-card>
          </v-col>
        </v-row>


        <v-card
        v-if="reveal==1 || reveal==2"
        style="overflow-y:scroll;"
        class="card2"
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
                <v-btn  @click="deleteOption(index)">
                删除选项
                </v-btn>
              </v-col>
              </v-row>
                
            </div>
            <v-card-actions class="pt-0">
              <v-row no-gutters>
                <v-col md="4">
                  <v-btn
                    @click="addOption">
                    添加选项
                    </v-btn>
                </v-col>
                <v-col>
                  <v-btn
                    @click="finishProblem">
                    完成问题
                    </v-btn>
                </v-col>
              </v-row>
                
                
            </v-card-actions>
        </v-card>

        <v-card
            v-if="reveal==3"
            class="card2"
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

        <v-card
            v-if="reveal==4"
            class="card2"
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
                <v-text-field
                    v-model="problems[alter].max"
                    label="最大值"
                ></v-text-field>
                </v-col>
            </v-row>
            
            <v-card-actions class="pt-0">
                <v-btn
                @click="finishProblem">
                完成问题
                </v-btn>
            </v-card-actions>
        </v-card>
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
          console.log(this.problems)
          let i
          for(i=0;i<this.problems.length;i++){
              this.problems[i].no=i+1
          }
        },
        deep:true
      }
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
       


      desciption:"",
      questionnaireNote:"",
      start:"0000-00-00 00:00",
      end:"0000-00-00 00:00",
      userID:this.$store.state.userID,
      qid:0,
      no:1,
      reveal:0,
      titleReveal:false,
      title:"",
      title1:"",
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
            type:i,
            no:this.problems.length+1,
            options:[],
            max:5
        }
        if(i==2){
            addProblem.multi=2
        }
        this.$set(this.problems,this.problems.length,addProblem)
        let x=document.querySelector("#naire")
        setTimeout(function() {x.scrollTo(0,10000); }, 100)
        
        // var x=document.getElementById("naire")
        // x.scrollToView(false)
        // this.problem.type=i
        // this.problem.no=this.problems.length+1
        // this.$http({
        //   method: "post",
        //   url: "/addQuestion",
        //   params: {
        //     questionKind:this.problem.type,
        //     questionNo:this.problem.no,
        //     questionnaireID:this.qid
        //   },
        // })
        //   .then((res) => {
        //     console.log("传问题")
        //     console.log(res.data)
        //     if (res.data.success) {
        //       this.$http({
        //         method:"get",
        //         url:"/showQuestionnaireInfo",
        //         params:{
        //           questionnaireID:this.qid
        //         },
        //       })
        //         .then((res) => {
        //           console.log(res.data)
        //           if(res.data.success){
                    // let li=res.data.questionList
                    // this.problem.id=li[li.length-1].questionContentID;
                    // let p=JSON.parse( JSON.stringify(this.problem) )
                    // this.problems.push(p)
        //           }
        //         })
        //         .catch((err) => {
        //           console.log(err)
        //         })
        //     }
        //   })
        //   .catch((err) => {
        //     console.log(err);
        //   });
        
      },
      alterProblem(i) {
        this.reveal=this.problems[i].type
        this.alter=i
        //   this.alter=1
        //   this.problem=JSON.parse( JSON.stringify(this.problems[i]) )
        //   this.reveal=this.problem.type
      },
      finishProblem() {
          this.reveal=0
      },
      addOption() {
          let op={
              no:this.problems[this.alter].options.length+1,
              content:""
          }
          this.$set(this.problems[this.alter].options,this.problems[this.alter].options.length,op)
          let x=document.querySelector(".card2")
          setTimeout(function() {x.scrollTo(0,10000); }, 100)
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
          this.problems.splice(i,1)
      },
      deleteOption(i) {
          this.problems[this.alter].options.splice(i,1)
      }
    }
  }
</script>

<style>
.bord{
  border-style: solid
}
.chosen {
  border: solid 1px #3089dc !important;
}
.scoll {
  overflow-y: auto;
  overflow-x: hidden;
  height: 90%;
  width: 100%;
}
.card2 {
  position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        height:600px;
        width: 800px;
}
</style>