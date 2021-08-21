<template>
    <div class="rc-bin-container">
        <el-table
            v-if="projectList&&projectList.length>0"
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
            />
            <el-table-column
                prop="count"
                align="center"
                label="已收集数"
            />
            <el-table-column
                align="center"
                prop="buildTime"
                label="创建时间"
            />
            <el-table-column
                align="center"
                prop="firstBeginTime"
                label="第一次发布时间"
            />
            <el-table-column
                align="center"
                prop="lastBeginTime"
                label="最后一次发布时间"
            />
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <v-btn text color="primary" @click="restoreProject(scope.row.key,1)">恢复</v-btn>
                    <el-popconfirm
                        title="确定删除该项目吗？"
                        @confirm="deleteProject(scope.row.key)"
                    >
                        <!-- <v-btn text color="error">删除</v-btn> -->
                        <el-button type="danger" slot="reference" plain>删除</el-button>
                        <!-- <el-button slot="reference"
                                   class="pink-text-btn"
                                   type="text"
                        >
                            删除
                        </el-button> -->
                    </el-popconfirm>
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
        <data-empty v-if="!projectList||projectList.length==0" />
    </div>
</template>
<script>

export default {
    name: 'RecycleBin',
    data() {
        return {
            total: 0,
            queryParams: {
                current: 1,
                size: 10,
                name: '',
                beginDateTime: null,
                endDateTime: null,
                status: null
            },
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
            projectListLoading: true
        }
    },
    computed: {

    },
    created() {
        this.queryRecycleProjectPage()
    },
    methods: {
        restoreProject(key) {
            this.$api.post('/user/project/recycle/restore', {'key': key}).then(res => {
                if (res.data) {
                    this.msgSuccess('恢复成功')
                    this.queryRecycleProjectPage()
                }
            })
        },
        deleteProject(key) {
            this.$api.post('/user/project/recycle/delete', {'key': key}).then(res => {
                if (res.data) {
                    this.msgSuccess('刪除成功')
                    this.queryRecycleProjectPage()
                }
            })
        },
        queryRecycleProjectPage() {
            this.$api.get('/user/project/recycle/page', {
                params: this.queryParams
            }).then(res => {
                let {records, total, size} = res.data
                this.projectList = records
                this.total = total
                this.queryParams.size = size
                this.projectListLoading = false
            })
        }
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
