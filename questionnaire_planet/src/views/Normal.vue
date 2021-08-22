
<template>
<div class="container">
  <div class="left-group">
    <v-btn text color="teal accent-4" @click="reveal=1">
          添加单选题
      </v-btn>
      <v-btn text color="teal accent-4" @click="reveal=2">
          添加填空题
      </v-btn>
      <v-btn text color="teal accent-4" @click="reveal=3">
          添加评分题
      </v-btn>
  </div>
  <div class="whole">
    <div class="ques">
      </br>
      <h1>{{title}}</h1>
      <v-btn text color="teal accent-4" @click="titleReveal=true">
          编辑标题
      </v-btn>
      </br>

      </br>
      </br>
      <draggable v-model="problems"  chosenClass="chosen" forceFallback="false" animation="400" filter=".nodrag" @start="onStart" @end="onEnd">
      <transition-group>
        <div v-for="(item,index) in problems" :key="item.id">
          <div class="bord">
            <div style="font-weight:900">
              问题{{index+1}}--{{item.name}}
            </div>
            </br>
              <div v-if="problems[index].type==='单选题'">
                  <v-radio-group v-model="problems[index].answer">
                  <v-radio
                      v-for="(it,i) in problems[index].options"
                      :key="i"
                      :label="problems[index].options[i]"
                      :value="n"
                  ></v-radio>
                  </v-radio-group>
              </div>
              <div v-if="problems[index].type==='评分题'">
                <v-col cols="12">
                  <v-slider
                    class="nodrag"
                    v-model="slider"
                    :max="problems[index].max"
                    :min="problems[index].min"
                    :thumb-size="24"
                    thumb-label="always"
                  ></v-slider>
                </v-col>
              </div>
              <div v-if="problems[index].type==='填空题'">
                <v-text-field
                      v-model="problems[index].answer"
                      label="答案"
                      required
                  ></v-text-field>
              </div>
          <div>
              <v-btn text color="teal accent-4" @click="alterProblem(index)">
                  编辑问题
              </v-btn>    
              <v-btn text color="teal accent-4" @click="deleteProblem(index)">
                  删除问题
              </v-btn>    
          </div>  
          
          
          </div>
        </br>
        </div>
        
      </transition-group>
  </draggable> 
      


      <v-btn
          text
          color="teal accent-4"
          @click="reveal = true"
      >
          添加问题
      </v-btn>

      
      <v-card v-if="titleReveal"
      class="card"
      
      >
        <v-text-field
              v-model="title"
              label="标题"
              required
          ></v-text-field>
          <v-btn text color="teal accent-4" @click="titleReveal=false">
              完成
          </v-btn>

      </v-card>

      <v-card
      v-if="reveal==1"
      class="card2"
      >
        <v-text-field
            v-model="problem.name"
            label="问题"
            required
        ></v-text-field>
          <v-row align="center" justify="space-around">
          <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
          <v-switch v-model="problem.multi" class="ma-2" label="多选题"></v-switch>
        </v-row>
        <div v-for="(item,index) in problem.options">
            {{item}}
            <v-btn text color="teal accent-4" @click="deleteOption(index)">
              删除选项
            </v-btn>
        </div>
        <v-text-field
                        v-model="option"
                        label="添加选项"
                    ></v-text-field>
        <v-card-actions class="pt-0">
            <v-btn
            text
            color="teal accent-4"
            @click="finishOption">
            完成选项
            </v-btn>
            <v-btn
            text
            color="teal accent-4"
            @click="finishProblem">
            完成问题
            </v-btn>
        </v-card-actions>
        <v-btn
          text
          color="teal accent-4"
          @click="reveal=false">
          取消
        </v-btn>
      </v-card>

      <v-card
        v-if="reveal==2"
        class="card2"
        >
          <v-text-field
              v-model="problem.name"
              label="问题"
              required
          ></v-text-field>
          <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
          <v-card-actions class="pt-0">
              <v-btn
              text
              color="teal accent-4"
              @click="finishProblem">
              完成问题
              </v-btn>
          </v-card-actions>
          <v-btn
            text
            color="teal accent-4"
            @click="reveal=false">
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
          <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
          <v-row>
            <v-col cols="12" sm="6" md="3">
            <v-text-field
              v-model="problem.min"
              label="最小值"
            ></v-text-field>
            </v-col>
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
              color="teal accent-4"
              @click="finishProblem">
              完成问题
              </v-btn>
          </v-card-actions>
        <v-btn
          text
          color="teal accent-4"
          @click="reveal=false">
          取消
        </v-btn>
      </v-card>
  <!--
        <div v-if="problem.type === '选择题' ">
          <v-text-field
              v-model="problem.name"
              label="问题"
              required
          ></v-text-field>
          <v-row align="center" justify="space-around">
            <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
            <v-switch v-model="problem.multi" class="ma-2" label="多选题"></v-switch>
          </v-row>
          <div v-for="(item,index) in problem.options">
              {{item}}
              <v-btn text color="teal accent-4" @click="deleteOption(index)">
                删除选项
              </v-btn>
          </div>
          <v-text-field
                          v-model="option"
                          label="添加选项"
                      ></v-text-field>
          <v-card-actions class="pt-0">
              <v-btn
              text
              color="teal accent-4"
              @click="finishOption">
              完成选项
              </v-btn>
              <v-btn
              text
              color="teal accent-4"
              @click="finishProblem">
              完成问题
              </v-btn>
          </v-card-actions>
      </div>
        <div v-else-if="problem.type === '填空题' ">
          <v-text-field
              v-model="problem.name"
              label="问题"
              required
          ></v-text-field>
          <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
          <v-card-actions class="pt-0">
              <v-btn
              text
              color="teal accent-4"
              @click="finishProblem">
              完成问题
              </v-btn>
          </v-card-actions>
        </div>
        <div v-else-if="problem.type === '评分题' ">
          <v-text-field
              v-model="problem.name"
              label="问题"
              required
          ></v-text-field>
          <v-switch v-model="problem.must" class="ma-2" label="必做题"></v-switch>
          <v-row>
            <v-col cols="12" sm="6" md="3">
            <v-text-field
              v-model="problem.min"
              label="最小值"
            ></v-text-field>
            </v-col>
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
              color="teal accent-4"
              @click="finishProblem">
              完成问题
              </v-btn>
          </v-card-actions>
        </div>
        <v-btn
        text
        color="teal accent-4"
        @click="reveal=false">
        取消
        </v-btn>
  -->

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
  <div class="right-group"></div>
</div>    
</template>

<script>
  import draggable from 'vuedraggable'
  export default {
    components: {
      draggable,
            },
    data() {
      return {
      id:1,
      reveal:0,
      titleReveal:false,
      title:"",
      problem:{
        id:1,
        name:"",
        type:"",
        options:[],
        must:false,
        multi:false,
        min:0,
        max:100,
      },
      option:"",
      problems:[],
      problemTypes:["选择题","填空题","评分题"],
      alter:0,
      optionDialog:false,
      optionDialog2:false,
      optionDialog3:false,
      problemDialog:false
      } 
    },
    methods: {
      finishProblem() {
          switch (this.reveal) {
            case 1:
              this.problem.type='单选题'
              break;
            case 2:
              this.problem.type='填空题'
              break;
            case 3:
              this.problem.type='评分题'
              break;
            default:
              break;
          }
          if(this.problem.name.length==0) {
              this.problemDialog=true
          }else if(this.problem.type=="选择题" && this.problem.options.length==0){
              this.optionDialog3=true
          }else {
                if(this.alter==0){
                this.problems.push(this.problem)
                
            } else if(this.alter==1){
                this.alter=0
            }
                this.reveal=0
                this.id=this.id+1
                this.problem={
                  id:this.id,
                  name:"",
                  type:"",
                  options:[],
                  must:false,
                  multi:false,
                  min:0,
                  max:100,
                }
                this.option=""
          }
        console.log(this.problems)
      },
      finishOption() {
          if(this.option.length==0){
              this.optionDialog2=true
          }else {
                if(this.problem.options.indexOf(this.option)==-1){
                this.problem.options.push(this.option)
                this.option="";
            } else{
                this.optionDialog=true
            }
          }
          console.log(this)
          
      },
      deleteOption(i) {
          this.problem.options.splice(i,1)
      },
      deleteProblem(i) {
          this.problems.splice(i,1)
      },
      alterProblem(i) {
          this.alter=1
          this.problem=this.problems[i]
          this.reveal=true
      },
      //开始拖拽事件
      onStart(){
        this.drag=true;
      },
      //拖拽结束事件
       onEnd() {
       this.drag=false;
    }
  }
}
</script>

<style>
.card {
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
.container{
    position: absolute;
    width: 100%;
    display: flex;
    height:100%;  
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