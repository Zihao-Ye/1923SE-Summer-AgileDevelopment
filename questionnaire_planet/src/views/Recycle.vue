<template>
<div>
    <v-dialog
      v-model="dialog2"
      width="500"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
         color="error"
          v-bind="attrs"
          v-on="on"
        >
          清空回收站
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
         确定要清空回收站吗？
        </v-card-title>


        <v-card-actions>
            <v-btn
            color="error"
            text
            @click="emptyRubbish()"
          >
            确定
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="dialog2 = false"
          >
            取消
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
<!-- <el-button type="danger" icon="el-icon-delete" @click="emptyRubbish()">清空回收站</el-button> -->
    <div class="rc-bin-container">
        <el-table
            :data="projectList"
            stripe
            border
            highlight-current-row

            style="width: 100%;"
            empty-text="暂无数据"
        >
            <el-table-column
                prop="title"
                show-overflow-tooltip
                align="center"
                label="标题"
                width="360"
            />
            <el-table-column
                prop="recycleVolume"
                align="center"
                label="已收集数"
                width="130"
            />
            <el-table-column align="center" label="创建时间">
                <template slot-scope="scope">
                    <div>{{scope.row.createTime | formatDate}}</div>
                </template>
            </el-table-column>
            <el-table-column align="center" label="截止时间">
                <template slot-scope="scope">
                    <div>{{scope.row.endTime | formatDate}}</div>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <v-btn text color="primary" @click="recoverQuestionnaire(scope.row.questionnaireID)">恢复</v-btn>
                    <v-dialog
      v-model="dialog"
      width="500"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          text color="error"
          v-bind="attrs"
          v-on="on"
        >
          删除
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
         确定要彻底删除该问卷吗？
        </v-card-title>


        <v-card-actions>
            <v-btn
            color="error"
            text
            @click="deleteQuestionnaire(scope.row.questionnaireID)"
          >
            确定删除
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="dialog = false"
          >
            取消
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
                </template>
            </el-table-column>
        </el-table>
        <div class="project-page-view">
            <el-pagination
                v-if="total>10"
                background
                :page-size.sync="queryParams.size"
                :current-page.sync="queryParams.current"
                layout="total, prev, pager, next"
                :total="total"
                @current-change="queryRecycleProjectPage"
            />
        </div>
    </div>
    </div>
</template>
<script>
import {formatDate} from '../common/date.js';
export default {
    filters: {
      formatDate(time) {
        // time = time * 1000
        let date = new Date(time)
        console.log(new Date(time))
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    name: 'RecycleBin',
    data() {
        return {
            dialog2: false,
            dialog: false,
            total: 0,
            projectList: [
                {
                  title: 'Frozen Yogurt',
                  count: 159,
                  firstBeginTime: '2021-08-03 18:00',
                  lastBeginTime: '2021-08-03 18:00',
                  buildTime: '2021-08-03 18:00',
                  status:'收集中'
          },
            ],
        }
    },
    computed: {

    },
    created() {
        this.showRubbishList()
    },
    methods: {
        emptyRubbish () {
            for (var i=0;i<this.projectList.length;i++)
            { 
                this.deleteQuestionnaire(this.projectList[i].questionnaireID)
            }

        },
        showRubbishList() {
             this.$http({
             method: "get",
             url: "/rubbish",
        }).then((res) => {
             this.projectList = res.data.rubbishList;
        }).catch((err) => {
          console.log(err);
        });
      },

       recoverQuestionnaire (questionnaireID) {
        this.$http({
        method: "post",
        url: "/recoverRubbish",
        params: {
          questionnaireID:questionnaireID
        },
      })
        .then((res) => {
          if (res.data.success) {
            this.msgSuccess('恢复成功');
            this.showRubbishList();
          } 
        })
        .catch((err) => {
          console.log(err);
        });
      },
      deleteQuestionnaire (questionnaireID) {
        this.$http({
        method: "post",
        url: "/delRubbish",
        params: {
          questionnaireID:questionnaireID
        },
      })
        .then((res) => {
          if (res.data.success) {
            this.dialog = false;
            this.msgSuccess('已删除');
            this.showRubbishList();
          } 
        })
        .catch((err) => {
          console.log(err);
        });
      },
    }
}
</script>

<style lang="scss" scoped>
.rc-bin-container {
    display: flex;
    width: 100%;
    height: 100%;
    flex-direction: column;
    align-items: center;
    align-content: center;
}
.back-view {
    display: flex;
    width: 80%;
    align-content: flex-start;
    margin: 10px;
}
.project-page-view {
    margin-top: 30px;
}
</style>
