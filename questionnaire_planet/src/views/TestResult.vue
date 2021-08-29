<template>
  <div id= 'pdfDom'>
    <div>
      <v-card class="timeHint text-center"
              elevation="12"
              rounded
              color="#607D8B"
              dark
      >
        <h2 style="color: #EFEBE9">总分</h2>
        <h2 style="color: #FF9100">{{totalScore}}</h2>
      </v-card>
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
              <v-row v-if="question.questionScore>0">
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
                    :color="getradiocolor(option)"
                    @change="radioAnswer[question.questionNo]=option;requirePlus(question)"
                ></v-radio>
              </v-radio-group>
            </v-container>

            <!--单选必做题数据分析-->
            <!--单选必做题数据分析——考试数据分析-->
            <v-container v-if="question.questionScore>0"></v-container>
            <v-card v-if="question.questionScore>0">
              <v-row>
                <v-card-title style="color:#FFA726">本题作答情况</v-card-title>
                <v-divider></v-divider>
                <v-card-text>平均分：{{testDataAverage[question.questionNo]}}</v-card-text>
                <v-card-text>正确作答人数：{{testDataRightCounts[question.questionNo]}}</v-card-text>
                <v-card-text>正确率：{{testDataAccuracy[question.questionNo]}}</v-card-text>
                <template v-if="question.questionScore>0">
                  <v-card-text>本题得分：{{questionScore[question.questionNo]}}</v-card-text>
                </template>
              </v-row>
            </v-card>
            <!--单选必做题数据分析——常规分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <template>
                <v-tabs v-model="tab[question.questionNo]">
                  <v-tab>选项选择人数小计表</v-tab>
                  <v-tab @click="zhuCharts(question.questionNo+'01',question)">柱状图</v-tab>
                  <v-tab @click="bingCharts(question.questionNo+'02',question)">饼图</v-tab>
                  <v-tab @click="zheCharts(question.questionNo+'03',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab[question.questionNo]">
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
            <v-card-title v-if="question.questionScore>0">
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
                    :color="getradiocolor(option)"
                    :value="n"
                    @change="radioAnswer[question.questionNo]=option"
                ></v-radio>
              </v-radio-group>
            </v-container>
            <!--单选非必做题数据分析-->
            <!--单选非必做题数据分析——考试数据分析-->
            <v-container v-if="question.questionScore>0"></v-container>
            <v-card v-if="question.questionScore>0">
              <v-row>
                <v-card-title style="color:#FFA726">本题作答情况</v-card-title>
                <v-divider></v-divider>
                <v-card-text>平均分：{{testDataAverage[question.questionNo]}}</v-card-text>
                <v-card-text>正确作答人数：{{testDataRightCounts[question.questionNo]}}</v-card-text>
                <v-card-text>正确率：{{testDataAccuracy[question.questionNo]}}</v-card-text>
                <template v-if="question.questionScore>0">
                  <v-card-text>本题得分：{{questionScore[question.questionNo]}}</v-card-text>
                </template>
              </v-row>
            </v-card>
            <!--单选非必做题数据分析——常规分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <template>
                <v-tabs v-model="tab[question.questionNo]">
                  <v-tab>选项选择人数小计表</v-tab>
                  <v-tab @click="zhuCharts(question.questionNo+'01',question)">柱状图</v-tab>
                  <v-tab @click="bingCharts(question.questionNo+'02',question)">饼图</v-tab>
                  <v-tab @click="zheCharts(question.questionNo+'03',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab[question.questionNo]">
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
              <v-row v-if="question.questionScore>0">
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
                    :class="{ 'blue--text': option.isAnswer ===1 }"
                    @change="checkboxAnswer(option);requirePlus(question)"
                    style="display:block;zoom:120%"
                ></el-checkbox>
              </el-checkbox-group>
            </v-container>
            <!--多选必做题数据分析-->
            <!--多选必做题数据分析——考试数据分析-->
            <v-container v-if="question.questionScore>0"></v-container>
            <v-card v-if="question.questionScore>0">
              <v-row>
                <v-card-title style="color:#FFA726">本题作答情况</v-card-title>
                <v-divider></v-divider>
                <v-card-text>平均分：{{testDataAverage[question.questionNo]}}</v-card-text>
                <v-card-text>正确作答人数：{{testDataRightCounts[question.questionNo]}}</v-card-text>
                <v-card-text>正确率：{{testDataAccuracy[question.questionNo]}}</v-card-text>
                <template v-if="question.questionScore>0">
                  <v-card-text>本题得分：{{questionScore[question.questionNo]}}</v-card-text>
                </template>
              </v-row>
            </v-card>
            <!--多选必做题数据分析——常规分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <template>
                <v-tabs v-model="tab[question.questionNo]">
                  <v-tab>选项选择人数小计表</v-tab>
                  <v-tab @click="zhuCharts(question.questionNo+'01',question)">柱状图</v-tab>
                  <v-tab @click="bingCharts(question.questionNo+'02',question)">饼图</v-tab>
                  <v-tab @click="zheCharts(question.questionNo+'03',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab[question.questionNo]">
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
              <v-row v-if="question.questionScore>0">
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
                    :class="{ 'blue--text': option.isAnswer ===1 }"
                    @change="checkboxAnswer(option)"
                    style="display:block;zoom:120%"
                ></el-checkbox>
              </el-checkbox-group>
            </v-container>
            <!--多选非必做题数据分析——考试数据分析-->
            <v-container v-if="question.questionScore>0"></v-container>
            <v-card v-if="question.questionScore>0">
              <v-row>
                <v-card-title style="color:#FFA726">本题作答情况</v-card-title>
                <v-divider></v-divider>
                <v-card-text>平均分：{{testDataAverage[question.questionNo]}}</v-card-text>
                <v-card-text>正确作答人数：{{testDataRightCounts[question.questionNo]}}</v-card-text>
                <v-card-text>正确率：{{testDataAccuracy[question.questionNo]}}</v-card-text>
                <template v-if="question.questionScore>0">
                  <v-card-text>本题得分：{{questionScore[question.questionNo]}}</v-card-text>
                </template>
              </v-row>
            </v-card>
            <!--多选非必做题数据分析——常规分析-->
            <!--多选非必做题数据分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <template>
                <v-tabs v-model="tab[question.questionNo]">
                  <v-tab>选项选择人数小计表</v-tab>
                  <v-tab @click="zhuCharts(question.questionNo+'01',question)">柱状图</v-tab>
                  <v-tab @click="bingCharts(question.questionNo+'02',question)">饼图</v-tab>
                  <v-tab @click="zheCharts(question.questionNo+'03',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab[question.questionNo]">
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
          <!--填空必做题-->
          <template v-else-if="question.questionKind===3 && question.requireSig===1">
            <v-card-title>
              <v-row v-if="question.questionScore>0">
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
              <v-text-field
                  disabled
                  v-model="text[question.questionNo]"
                  :rules="textRules"
                  label="填空"
                  required
                  outlined
                  @change="requirePlus(question)"
              ></v-text-field>
            </v-container>
            <!--填空必做题数据分析——考试数据分析-->
            <v-container v-if="question.questionScore>0"></v-container>
            <v-card v-if="question.questionScore>0">
              <v-row>
                <v-card-title style="color:#FFA726">本题作答情况</v-card-title>
                <v-divider></v-divider>
                <v-card-text>本题答案：{{completionQuestions[question.questionNo].answer}}</v-card-text>
                <v-card-text>平均分：{{testDataAverage[question.questionNo]}}</v-card-text>
                <v-card-text>正确作答人数：{{testDataRightCounts[question.questionNo]}}</v-card-text>
                <v-card-text>正确率：{{testDataAccuracy[question.questionNo]}}</v-card-text>
                <template v-if="question.questionScore>0">
                  <v-card-text>本题得分：{{questionScore[question.questionNo]}}</v-card-text>
                </template>
              </v-row>
            </v-card>
            <!--填空必做题数据分析——常规分析-->

          </template>
          <!--填空非必做题-->
          <template v-else-if="question.questionKind===3 && question.requireSig===0">
            <v-card-title v-if="question.questionScore>0">
              {{question.questionNo}}. {{question.questionContent}}({{question.questionScore}})
            </v-card-title>
            <v-card-title v-else>
              {{question.questionNo}}. {{question.questionContent}}
            </v-card-title>
            <v-card-subtitle>
              {{question.questionNote}}
            </v-card-subtitle>
            <v-container>
              <v-text-field
                  disabled
                  v-model="text[question.questionNo]"
                  label="填空"
                  outlined
              ></v-text-field>
            </v-container>
            <!--填空非必做题数据分析——考试数据分析-->
            <v-container v-if="question.questionScore>0"></v-container>
            <v-card v-if="question.questionScore>0">
              <v-row>
                <v-card-title style="color:#FFA726">本题作答情况</v-card-title>
                <v-divider></v-divider>
                <v-card-text>本题答案：{{completionQuestions[question.questionNo].answer}}</v-card-text>
                <v-card-text>平均分：{{testDataAverage[question.questionNo]}}</v-card-text>
                <v-card-text>正确作答人数：{{testDataRightCounts[question.questionNo]}}</v-card-text>
                <v-card-text>正确率：{{testDataAccuracy[question.questionNo]}}</v-card-text>
                <template v-if="question.questionScore>0">
                  <v-card-text>本题得分：{{questionScore[question.questionNo]}}</v-card-text>
                </template>
              </v-row>
            </v-card>
            <!--填空非必做题数据分析——常规分析-->

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
                      disabled
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
            <!--评分必做题数据分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <v-card-subtitle style="color:#EF5350">平均分：{{maxScores[question.questionNo].averageScore}}</v-card-subtitle>
              <template>
                <v-tabs v-model="tab[question.questionNo]">
                  <v-tab>评分分值选择人数小计表</v-tab>
                  <v-tab @click="toDrawScoreCharts(question.questionNo+'01','bar',question)">柱状图</v-tab>
                  <v-tab @click="toDrawScoreCharts(question.questionNo+'02','pie',question)">饼图</v-tab>
                  <v-tab @click="toDrawScoreCharts(question.questionNo+'03','line',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab[question.questionNo]">
                  <v-tab-item>
                    <v-card class="scoretableCard" width="600" elevation="8">
                      <v-data-table
                          :headers="scoreHeaders"
                          :items="distributeList[question.questionNo]"
                          :sort-desc="[false, true]"
                          multi-sort
                          items-per-page="5"
                          class="elevation-1"

                          :footer-props="{
                              disableItemsPerPage: true,
                              showFirstLastPage: true,
                              firstIcon: 'mdi-arrow-collapse-left',
                              lastIcon: 'mdi-arrow-collapse-right',
                              prevIcon: 'mdi-minus',
                              nextIcon: 'mdi-plus'
                              }"
                      >
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
                      disabled
                      v-model="score[question.questionNo]"
                      :max="maxScores[question.questionNo].maxScore"
                      :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                      style="zoom:120%"
                  ></el-rate>
                  <h4 >很满意</h4>
                </v-row>
              </v-container>
            </v-card-actions>
            <!--评分非必做题数据分析-->
            <v-container></v-container>
            <v-card style="width: 800px;height:550px;">
              <v-card-title style="color:#6A76AB">视图统计</v-card-title>
              <v-card-subtitle style="color:#EF5350">平均分：{{maxScores[question.questionNo].averageScore}}</v-card-subtitle>
              <template>
                <v-tabs v-model="tab[question.questionNo]">
                  <v-tab>评分分值选择人数小计表</v-tab>
                  <v-tab @click="drawScoreCharts(question.questionNo+'01','bar',question)">柱状图</v-tab>
                  <v-tab @click="drawScoreCharts(question.questionNo+'02','pie',question)">饼图</v-tab>
                  <v-tab @click="drawScoreCharts(question.questionNo+'03','line',question)">折线图</v-tab>
                </v-tabs>
                <v-tabs-items v-model="tab[question.questionNo]">
                  <v-tab-item>
                    <v-card class="scoretableCard" width="600" elevation="8">
                      <v-data-table
                          :headers="scoreHeaders"
                          :items="distributeList[question.questionNo]"
                          :sort-desc="[false, true]"
                          multi-sort
                          items-per-page="5"
                          class="elevation-1"

                          :footer-props="{
                              disableItemsPerPage: true,
                              showFirstLastPage: true,
                              firstIcon: 'mdi-arrow-collapse-left',
                              lastIcon: 'mdi-arrow-collapse-right',
                              prevIcon: 'mdi-minus',
                              nextIcon: 'mdi-plus'
                              }"
                      >
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
          <!--定位必做题-->
          <template v-else-if="question.questionKind===5 && question.requireSig===1">
            <v-card-title>
              <v-row>
                <p class="red--text"> * </p>
                {{question.questionNo}}. {{question.questionContent}}
              </v-row>
            </v-card-title>
            <v-card-subtitle>
              由于测试机型有限，暂只有Windows平台Edge/火狐浏览器可获取定位（为获取准确定位，请给与浏览器定位权限）
            </v-card-subtitle>
            <v-card-subtitle>
              {{question.questionNote}}
            </v-card-subtitle>
            <v-container>
              <v-text-field
                  disabled
                  v-model="location[question.questionNo]"
                  :rules="textRules"
                  label="点击获取定位"
                  required
                  solo
                  @change="requirePlus(question)"
                  readonly
                  @click="getLocation(question.questionNo)"
              ></v-text-field>
            </v-container>
            <v-container>
              <v-card  elevation="8">
                <v-card-title style="color:#6A76AB">定位题用户所填列表</v-card-title>
                <el-table
                    :data="userLocateQuestionList[question.questionNo]"
                    style="width: 100%">
                  <el-table-column fixed label="序号" width="90"   align="center">
                    <template slot-scope="scope">
                      <span>{{(scope.$index + 1)}} </span>
                    </template>
                  </el-table-column>>
                  <el-table-column
                      prop="locate"
                      label="用户定位内容"
                      width="650">
                  </el-table-column>
                </el-table>
              </v-card>
            </v-container>
          </template>
          <!--定位非必做题-->
          <template v-else-if="question.questionKind===5 && question.requireSig===0">
            <v-card-title>
              {{question.questionNo}}. {{question.questionContent}}
            </v-card-title>
            <v-card-subtitle>
              由于测试机型有限，暂只有Windows平台Edge/火狐浏览器可获取定位（为获取准确定位，请给与浏览器定位权限）
            </v-card-subtitle>
            <v-card-subtitle>
              {{question.questionNote}}
            </v-card-subtitle>
            <v-container>
              <v-text-field
                  disabled
                  v-model="location[question.questionNo]"
                  label="获取定位"
                  outlined
                  solo
                  readonly
                  @click="getLocation(question.questionNo)"
              ></v-text-field>
            </v-container>
            <v-container>
              <v-card  elevation="8">
                <v-card-title style="color:#6A76AB">定位题用户所填列表</v-card-title>
                <el-table
                    :data="userLocateQuestionList[question.questionNo]"
                    style="width: 100%">
                  <el-table-column fixed label="序号" width="90"   align="center">
                    <template slot-scope="scope">
                      <span>{{(scope.$index + 1)}} </span>
                    </template>
                  </el-table-column>>
                  <el-table-column
                      prop="locate"
                      label="用户定位内容"
                      width="650">
                  </el-table-column>
                </el-table>
              </v-card>
            </v-container>
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
    tab:{},
    choosenHeaders: [
      {
        text: '选项',
        align: 'start',
        sortable: false,
        value: 'optionContent',
      },
      { text: '选项选择人数', value: 'voteVolume' },
    ],
    scoreHeaders: [
      {
        text: '选项',
        align: 'start',
        sortable: false,
        value: 'index',
      },
      { text: '选项选择人数', value: 'count' },
    ],
    htmlTitle: '页面导出PDF文件名',
    questionnaire:{},//问卷信息
    questions:[],//问卷包含问题列表
    options: {},//各个问题包含的选项列表数组
    radioModel:{},//单选题所选（用于ui）
    radioAnswer:{},//单选题所选（用于上交）
    optionAnswer:{},//多选题所选（用于上交）
    checkboxModel:{},//多选题所选（用于ui）
    userCompletionQuestionList:{},//填空题数据列表
    userLocateQuestionList:{},//定位题数据列表
    distributeList:{},//评分题数据列表
    flag:false,
    completionQuestions:{},
    text: {},//填空题所填
    textRules:[val => (val || '').length > 0 || '必填题目'],
    score:{},//评分提所选
    scoreRules:[v=> v >1||'必须选择评分'],
    maxScores:{},//评分题实体表
    testDataAverage:{},
    testDataRightCounts:{},
    testDataAccuracy:{},
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
    questionScore:{},
    totalScore:0
  }),
  methods:{
    //图表实例(选择题)
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
    //图表实例（评分题）
    drawScoreCharts (id,kind,question) {
      // 基于准备好的dom，初始化echarts实例
      var that = this;
      if(kind=='line'||kind == 'bar'){
        // var tempText = question.questionContent
        var tempOptionsContent = [];
        var tempOptionsCount = [];
        for (var i=0;i<that.distributeList[question.questionNo].length;i++)
        {
          tempOptionsContent.push(that.distributeList[question.questionNo][i].index);
          tempOptionsCount.push(that.distributeList[question.questionNo][i].count);
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
        for (var i=0;i<that.distributeList[question.questionNo].length;i++)
        {
          var tempTuple = {value:0,name:0};
          tempTuple.value = that.distributeList[question.questionNo][i].count;
          tempTuple.name = that.distributeList[question.questionNo][i].index;
          tempPieData.push(tempTuple)
          tempOptionsContent2.push(`${that.distributeList[question.questionNo][i].index}`);
        }
        console.log(tempPieData)
        console.log(tempOptionsContent2)
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
    getradiocolor(option) {
      if(option.isAnswer === 1) return '#42A5F5'
    },
    toDrawScoreCharts (id,kind,question){
      console.log(id)
      var that = this;
      setTimeout(function () {
        that.drawScoreCharts(id,kind,question)
      },1000);
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
              this.requireNum=0
              this.htmlTitle = res.data.questionnaire.title
              this.questionnaire=res.data.questionnaire
              this.questions=res.data.questionList
              if(res.data.questionnaire.kind===4){
                this.questions=this.$store.state.questions
              }
                for(const question of this.questions){
                  if(question.questionScore>0){
                    this.autoScore(question)
                  }
                  if(question.requireSig===1){
                    this.requireNum+=1
                  }
                  if(question.questionKind===1){
                    this.$set(this.radioModel,question.questionNo,null)
                    this.$set(this.tab,question.questionNo,[])
                    this.getOptions(question)
                  }else if(question.questionKind===4){
                    this.$set(this.score,question.questionNo,0)
                    this.$set(this.tab,question.questionNo,[])
                    this.UserScoreQuestion(question)
                    this.getMaxScore(question)
                  }else if(question.questionKind===2){
                    this.$set(this.checkboxModel,question.questionNo,[])
                    this.$set(this.tab,question.questionNo,[])
                    this.getOptions(question)
                  }else if(question.questionKind===3){
                    this.$set(this.text,question.questionNo,"")
                    this.getCompletionQuestion(question)
                    this.UserCompletionQuestion(question)
                  }else if(question.questionKind===5){
                    this.$set(this.location,question.questionNo,"")
                    this.UserLocateQuestion(question)
                  }
                  if(question.questionScore>0){
                    this.getTestData(question)
                  }
                }
              }

          })
          .catch((err) => {
            console.log(err);
          });
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
              this.$set(this.tab,question.questionNo,[])
              this.getOptions(question)
            }else if(question.questionKind===4){
              this.$set(this.score,question.questionNo,0)
              this.$set(this.tab,question.questionNo,[])
              this.UserScoreQuestion(question)
              this.getMaxScore(question)
            }else if(question.questionKind===2){
              this.$set(this.checkboxModel,question.questionNo,[])
              this.$set(this.tab,question.questionNo,[])
              this.getOptions(question)
            }else if(question.questionKind===3){
              this.$set(this.text,question.questionNo,"")
              this.getCompletionQuestion(question)
              this.UserCompletionQuestion(question)
            }else if(question.questionKind===5){
              this.$set(this.location,question.questionNo,"")
              this.UserLocateQuestion(question)
            }
            if(question.questionScore>0){
              this.getTestData(question)
            }
          }
        }
      })
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
    getCompletionQuestion(question){
      this.$http({
        method: "get",
        url: "/showCompletionQuestion",
        params: {
          questionContentID:question.questionContentID
        },
      }).then((res) => {
        console.log(res.data)
        if (res.data.success) {
          this.$set(this.completionQuestions,question.questionNo,res.data.completionQuestion)
        }
      })
          .catch((err) => {
            console.log(err);
          });
    },
    getTestData(question){
      this.$http({
        method: "get",
        url: "/getTestData",
        params: {
          questionContentID:question.questionContentID
        },
      }).then((res) => {
        console.log(res.data)
        if (res.data.success) {
          this.$set(this.testDataAverage,question.questionNo,res.data.average);
          this.$set(this.testDataRightCounts,question.questionNo,res.data.rightCounts);
          this.$set(this.testDataAccuracy,question.questionNo,res.data.accuracy);
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
    UserScoreQuestion(question){
      this.$http({
        method: "get",
        url: "/getScoreRecordDistribute",
        params: {
          questionContentID:question.questionContentID
        },
      }).then((res) => {
        console.log(res.data)
        if (res.data.success) {
          console.log(res.data);
          this.$set(this.distributeList,question.questionNo,res.data.distributeList)
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
    autoScore(question){
      this.$http({
        method:'post',
        url:'/setTestScore',
        params:{
          questionContentID:question.questionContentID,
          questionKind:question.questionKind,
          questionScore:question.questionScore,
          questionnaireID:this.questionnaire.questionnaireID,
          userID:this.$store.state.userID
        }
      }).then(res=>{
        console.log(res.data)
        if(res.data.success){
          this.$set(this.questionScore,question.questionNo,res.data.score)
          this.totalScore+=res.data.score
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
    that.radioModel=that.$store.state.answer.radioAnswer
    that.checkboxModel=that.$store.state.answer.optionAnswer
    that.text=that.$store.state.answer.text
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
.scoretableCard{
  margin-top:10px;
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
.timeHint{
  position: fixed;
  right:1%;
  top:50%;
  width: 100px;
  height: 70px;
  margin-top: -40px;
}
</style>