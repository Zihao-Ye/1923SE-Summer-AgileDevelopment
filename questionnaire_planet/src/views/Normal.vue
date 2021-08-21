
<template>
<div class="whole">
    <h1>{{title}}</h1>
    <v-btn text color="teal accent-4" @click="titleReveal=true">
        编辑标题
    </v-btn>
    </br>
    <div v-for="(item,index) in problems">
      <div>
        问题{{index+1}}--{{item.name}}
      </div>
      </br>
        <div v-if="problems[index].type==='选择题'">
            <div v-for="(it,i) in problems[index].options">
              选项{{i+1}}--{{it}}
            </div>
        </div>
        
    <div>
        <v-btn text color="teal accent-4" @click="alterProblem(index)">
            编辑问题
        </v-btn>    
        <v-btn text color="teal accent-4" @click="deleteProblem(index)">
            删除问题
        </v-btn>    
    </div>  
      </br></br>
    </div>


    <v-btn
        text
        color="teal accent-4"
        @click="reveal = true"
    >
        添加问题
    </v-btn>

    <v-card v-if="titleReveal"
    class="transition-fast-in-fast-out v-card--reveal"
    style="position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        height: 350px;
        width: 600px;"
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
    v-if="reveal"
    class="transition-fast-in-fast-out v-card--reveal"
    style="position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        height: 650px;
        width: 400px;"
    >
    <v-select
          v-model="problem.type" 
          :items="problemTypes"
          filled
          label="题型"
          dense
        ></v-select>
    <div v-if="problem.type === '选择题' ">
        <v-text-field
            v-model="problem.name"
            label="问题"
            required
        ></v-text-field>
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
  
      
</template>

<script>
  export default {
    data: () => ({
      reveal: false,
      titleReveal:false,
      title:"",
      problem:{
        name:"",
        type:"",
        options:[],
      },
      option:"",
      problems:[],
      problemTypes:["选择题","填空题"],
      alter:0,
      optionDialog:false,
      optionDialog2:false,
      optionDialog3:false,
      problemDialog:false
    }),
    methods: {
      finishProblem() {
          
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
                this.reveal=false
                this.problem={
                  name:"",
                  type:"",
                  options:[],
                }
                this.option=""
          }
          
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
          console.log(this.option)
          
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
    }
  }
</script>

<style>

.whole {
  margin-right: auto;
  margin-left: auto;
  width:65%;
  border-left: 1px solid #000; 
  border-right: 1px solid #000;
  box-sizing: border-box;/*为元素指定的任何内边距和边框都将在已设定的宽度和高度内进行绘制*/
  min-height: 100%;
  padding-bottom: 300px;
}
</style>