<template>
  <v-container fluid id = 'pdfDom'>
    <v-data-iterator
      :items="items"
      :items-per-page.sync="itemsPerPage"
      :page.sync="page"
      :search="search"
      :sort-by="sortBy"
      :sort-desc="sortDesc"
      hide-default-footer
    >
      <template v-slot:header>
        <v-toolbar
          dark
          color="blue darken-3"
          class="mb-1"
        >
          <v-text-field
            v-model="search"
            clearable
            flat
            solo-inverted
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="Search"
          ></v-text-field>
          <template v-if="$vuetify.breakpoint.mdAndUp">
            <v-spacer></v-spacer>
            <v-select
              v-model="sortBy"
              flat
              solo-inverted
              hide-details
              :items="sortkeys"
              prepend-inner-icon="mdi-magnify"
              label="Sort by"
            ></v-select>
            <v-spacer></v-spacer>
            <v-btn-toggle
              v-model="sortDesc"
              mandatory
            >
              <v-btn
                large
                depressed
                color="blue"
                :value="false"
              >
                <v-icon>mdi-arrow-up</v-icon>
              </v-btn>
              <v-btn
                large
                depressed
                color="blue"
                :value="true"
              >
                <v-icon>mdi-arrow-down</v-icon>
              </v-btn>
            </v-btn-toggle>
          </template>
        </v-toolbar>
      </template>

      <template v-slot:default="props">
        <v-row>
          <v-col
            v-for="item in props.items"
            :key="item.questionnaireID"
            cols="12"
            sm="6"
            md="4"
            lg="3"
          >
            <v-card>
              <v-card-title class="subheading font-weight-bold">
                {{ item.title }}
              </v-card-title>

              <v-divider></v-divider>

              <v-list dense>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'recycleVolume' }">
                    已回收数量:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'recycleVolume' }"
                  >
                    {{ item.recycleVolume }}
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'createTime' }">
                    创建时间:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'createTime' }"
                  >
                    {{ item.createTime | formatDate }}
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'startTime' }">
                    第一次发布时间:
                  </v-list-item-content>
                  <v-list-item-content
                    v-if="item.havePublish"
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'startTime' }"
                  >
                    {{ item.startTime | formatDate }}
                  </v-list-item-content>
                  <v-list-item-content v-else class="align-end" :class="{ 'blue--text': sortBy === 'startTime' }">
                    未发布
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'endTime' }">
                    最后一次发布时间:
                  </v-list-item-content>
                  <v-list-item-content
                     v-if="item.havePublish"
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'endTime' }"
                  >
                    {{ item.endTime | formatDate }}
                  </v-list-item-content>
                  <v-list-item-content v-else class="align-end" :class="{ 'blue--text': sortBy === 'endTime' }">
                    未发布
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'status' }">
                    状态:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'status' }"
                  >
                    {{ questionnaireStatus(item) }}
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'kind' }">
                    问卷类型:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'kind' }"
                  >
                    {{ questionnaireKind(item) }}
                  </v-list-item-content>
                </v-list-item>

                <v-divider></v-divider>

                <v-list-item>
                    <v-btn text v-if='!item.isVisitable' @click="openQuestionnaire(item)" color="primary">
                        开启问卷
                        <i class="el-icon-video-play"></i>
                    </v-btn>
                    <v-btn text v-else color="error" @click="closeQuestionnaire(item.questionnaireID)">
                        停止问卷
                        <i class="el-icon-video-pause"></i>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn text color="#00796B">编辑问卷<i class="el-icon-edit"></i></v-btn>
                </v-list-item>
                <v-list-item>
                    <v-btn @click="printQuestionnaire(item.questionnaireID)" color="#546E7A" text >
                           导出问卷                               
                           <i class="el-icon-upload"></i>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn text @click="copyQuestionnaire(item.questionnaireID)" color="#000000">
                        复制问卷
                        <i class="el-icon-document-copy"></i>
                        </v-btn>
                </v-list-item>
                <v-list-item>
                    <v-btn text color="error" @click="deleteQuestionnaire(item.questionnaireID)">
                        删除问卷
                        <i class="el-icon-delete-solid"></i>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn :to="'/preview/' + item.questionnaireID" text color="#FBC02D">
                            预览问卷
                            <i class="el-icon-view"></i>
                            </v-btn>

                </v-list-item>
                <v-list-item>
      <v-dialog
        transition="dialog-bottom-transition"
        max-width="600"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="#546E7A" text
            v-bind="attrs"
            v-on="on"
          >分享链接<i class="el-icon-link"></i></v-btn>
        </template>
        <template v-slot:default="dialog">
          <v-card>
            <v-toolbar
              color="primary"
              dark
            >{{item.title}}————问卷分享链接</v-toolbar>
            <v-card-text>
              <div  class="pa-5 justify-center">问卷填写链接：{{questionnaireURL(item)}}
                <!-- <el-button v-clipboard:copy="questionnaireURL(item)"
                                           v-clipboard:error="()=>{this.msgError('复制失败')}"
                                           v-clipboard:success="()=>{this.msgSuccess('复制成功')}" type="primary"
                                >
                                    复制链接
                                </el-button> -->
              </div>
              <div>
                <vue-qr :text="questionnaireURL(item)" 
                      :callback="qrCodeGenSuccess()"
                      :margin="20" 
                      colorDark="#000" 
                      colorLight="#fff" 
                      :size="200">
                </vue-qr>
                <!-- <div style="text-align: center;">
                            <el-link type="primary" @click="()=>{
                                this.downloadFile('qrcode.png',this.qrCodeUrl)
                            }"
                            >
                                下载分享二维码
                            </el-link>
                        </div> -->
              </div>
            </v-card-text>
            <v-card-actions class="justify-end">
              <v-btn
                text
                @click="dialog.value = false"
              >返回</v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>
                    <v-spacer></v-spacer>
                    <v-btn text color="#B388FF" :to="'/normalAnalyse/' + item.questionnaireID">
                        数据分析
                        <i class="el-icon-s-data"></i>
                        </v-btn>
                </v-list-item>
              </v-list>
            </v-card>
          </v-col>
        </v-row>
      </template>

      <template v-slot:footer>
        <v-row
          class="mt-2"
          align="center"
          justify="center"
        >
          <span class="grey--text">每页问卷数</span>
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                dark
                text
                color="primary"
                class="ml-2"
                v-bind="attrs"
                v-on="on"
              >
                {{ itemsPerPage }}
                <v-icon>mdi-chevron-down</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item
                v-for="(number, index) in itemsPerPageArray"
                :key="index"
                @click="updateItemsPerPage(number)"
              >
                <v-list-item-title>{{ number }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>

          <v-spacer></v-spacer>

          <span
            class="mr-4
            grey--text"
          >
            Page {{ page }} of {{ numberOfPages }}
          </span>
          <v-btn
            fab
            dark
            color="blue darken-3"
            class="mr-1"
            @click="formerPage"
          >
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
          <v-btn
            fab
            dark
            color="blue darken-3"
            class="ml-1"
            @click="nextPage"
          >
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </v-row>
      </template>
    </v-data-iterator>
  </v-container>
</template>

<script>
import vueQr from 'vue-qr';
import {formatDate} from '../common/date.js';
  export default {
    components:{vueQr},
    filters: {
      formatDate(time) {
        // time = time * 1000
        let date = new Date(time)
        console.log(new Date(time))
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    data () {
      return {
        htmlTitle: '测试PDF文件名',
        qrCodeUrl: '',
        isOpening: false,
        itemsPerPageArray: [4, 8, 12],
        search: '',
        filter: {},
        sortDesc: false,
        page: 1,
        itemsPerPage: 4,
        sortBy: '',
        sortkeys: [
          'title',
          'recycleVolume',
          'createTime',
        ],
        keys: [
          'title',
          'recycleVolume',
          'startTime',
          'endTime',
          'createTime',
          'status',
          'kind'
        ],
        items: []
          
      }
    },
    computed: {
      numberOfPages () {
        return Math.ceil(this.items.length / this.itemsPerPage)
      },
      filteredKeys () {
        return this.keys.filter(key => key !== 'title')
      },
    },
    created() {
        this.showMyQuestionnaire();
    },

    methods: {
      printQuestionnaire (id) {
        //直接调用$router.push 实现携带参数的跳转
        this.$store.commit("setIsPrint");
        this.$router.push({
          path: `/preview/${id}`,
        })
      },
      qrCodeGenSuccess(dataUrl) {
            this.qrCodeUrl = dataUrl;
            console.log(dataUrl)
        },
      questionnaireURL (item) {
        return `http://39.105.38.175/fillQuestionnaire/${item.questionnaireID}`
      },
      questionnaireStatus (item) {
        if(item.havePublish == 0) return '未发布';
        else if(item.havePublish == 1 && item.isVisitable == 0) return '已暂停收集'
        else return '收集中'
      },
      questionnaireKind (item) {
        if(item.kind == 1 ) return '普通问卷';
        else if(item.kind == 2) return '投票问卷';
        else if(item.kind == 3) return '报名问卷';
        else if(item.kind == 4) return '考试问卷';
      },
      showMyQuestionnaire() {
      this.$http({
        method: "get",
        url: "/notRubbish",
      }).then((res) => {
          this.items = res.data.questionnaireList;
        }).catch((err) => {
          console.log(err);
      });
    },
      nextPage () {
        if (this.page + 1 <= this.numberOfPages) this.page += 1
      },
      formerPage () {
        if (this.page - 1 >= 1) this.page -= 1
      },
      updateItemsPerPage (number) {
        this.itemsPerPage = number
      },
      openQuestionnaire (item) {
        if(item.havePublish == 1){
        this.$http({
        method: "post",
        url: "/openQuestionnaire",
        params: {
          questionnaireID:item.questionnaireID
        },
      })
        .then((res) => {
          if (res.data.success) {
            this.msgSuccess('开启成功');
            this.showMyQuestionnaire();
          } 
        })
        .catch((err) => {
          console.log(err);
        });
      }
      else if(item.havePublish == 0){
        this.$http({
        method: "post",
        url: "/publishQuestionnaire",
        params: {
          questionnaireID:item.questionnaireID
        },
      })
        .then((res) => {
          if (res.data.success) {
            this.msgSuccess('发布成功');
            this.showMyQuestionnaire();
          } 
        })
        .catch((err) => {
          console.log(err);
        });
      }
      },
      copyQuestionnaire (questionnaireID) {
        this.$http({
        method: "post",
        url: "/copyQuestionnaire",
        params: {
          questionnaireID:questionnaireID
        },
      })
        .then((res) => {
          if (res.data.success) {
            this.msgSuccess('复制成功');
            this.showMyQuestionnaire();
          } 
        })
        .catch((err) => {
          this.msgSuccess('复制失败');
          console.log(err);
        });
      },
      closeQuestionnaire (questionnaireID) {
        this.$http({
        method: "post",
        url: "/closeQuestionnaire",
        params: {
          questionnaireID:questionnaireID
        },
      })
        .then((res) => {
          if (res.data.success) {
            this.msgSuccess('停止成功');
            this.showMyQuestionnaire();
          } 
        })
        .catch((err) => {
          console.log(err);
        });
      },
      deleteQuestionnaire (questionnaireID) {
        this.$http({
        method: "post",
        url: "/setRubbish",
        params: {
          questionnaireID:questionnaireID
        },
      })
        .then((res) => {
          if (res.data.success) {
            this.msgSuccess('删除成功');
            this.showMyQuestionnaire();
          } 
        })
        .catch((err) => {
          console.log(err);
        });
      },
      downloadFile(fileName, content) {
            let aLink = document.createElement('a')
            let blob = this.base64ToBlob(content) // new Blob([content]);
            let evt = document.createEvent('HTMLEvents')
            evt.initEvent('click', true, true)// initEvent 不加后两个参数在FF下会报错  事件类型，是否冒泡，是否阻止浏览器的默认行为
            aLink.download = fileName
            aLink.href = URL.createObjectURL(blob)
            // aLink.dispatchEvent(evt);
            aLink.click()
        },
        // base64转blob
        base64ToBlob(code) {
            let parts = code.split(';base64,')
            let contentType = parts[0].split(':')[1]
            let raw = window.atob(parts[1])
            let rawLength = raw.length
            let uInt8Array = new Uint8Array(rawLength)
            for (let i = 0; i < rawLength; ++i) {
                uInt8Array[i] = raw.charCodeAt(i)
            }
            return new Blob([uInt8Array], {type: contentType})
        },
    },
  }
</script>