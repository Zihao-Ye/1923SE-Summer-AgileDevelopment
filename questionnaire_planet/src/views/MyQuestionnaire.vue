<template>
  <v-container fluid id = 'pdfDom'>
    <v-data-iterator
      :items="items"
      :items-per-page.sync="itemsPerPage"
      :page.sync="page"
      :search="search"
      :sort-by="funsortBy(sortBy)"
      :sort-desc="sortDesc"
      hide-default-footer
    >
      <template v-slot:header>
        <v-toolbar
          dark
          color="#29B6F6"
          class="mb-1"
        >
        <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
        ></v-img>
      </template>
      <v-icon large color="white">mdi-domain</v-icon>
      <v-toolbar-title>我的问卷</v-toolbar-title>
      <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            clearable
            flat
            solo-inverted
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="搜索"
          ></v-text-field>
          <template v-if="$vuetify.breakpoint.mdAndUp">
            <v-spacer></v-spacer>
            <v-spacer></v-spacer>
            排序：
            <v-select
              style="width:10%"
              v-model="sortBy"
              flat
              solo-inverted
              hide-details
              :items="sortkeys"
              label="排序"
            ></v-select>
            <v-spacer></v-spacer>
            <v-spacer></v-spacer>
            <v-btn-toggle
              v-model="sortDesc"
              mandatory
            >
              <v-btn
                large
                depressed
                color="#29B6F6"
                :value="false"
              >
                <v-icon>mdi-arrow-up</v-icon>
              </v-btn>
              <v-btn
                large
                depressed
                color="#29B6F6"
                :value="true"
              >
                <v-icon>mdi-arrow-down</v-icon>
              </v-btn>
            </v-btn-toggle>
          </template>
          <template v-slot:extension>
        <v-tabs
          v-model="tab"
          align-with-title
        >
          <v-tabs-slider color="yellow"></v-tabs-slider>

          <v-tab
            v-for="item in tabitems"
            :key="item"
          >
            {{ item }}
          </v-tab>
        </v-tabs>
      </template>

        </v-toolbar>
      </template>

      <template v-slot:default="props">
        <v-container>
        <v-row v-for="item in props.items"
            :key="item.questionnaireID">
            <v-col>
            <v-card  class="questionnaireCardClass" elevation="6" v-if="tab == 0 ||questionnaireStatusForTab (item) == tab" width="100%"
            >
              <v-card-title >
                <div class="questionnaireTitle"  :class="{ 'blue--text': sortBy === '标题' }">{{ item.title }}</div>
                <v-chip class="ma-2" text-color="white" :color="statusClass(item)">{{ questionnaireKind(item) }}</v-chip>
                <v-chip class="ma-2" :color="questionnaireStatusClass(item)" outlined>{{ questionnaireStatus(item) }}</v-chip>
                <v-spacer></v-spacer>
                   <div class="titleInfoCreateTime" :class="{ 'blue--text': sortBy === '创建时间' }">创建于：{{ item.createTime | formatDate }}</div>
                   <div class="titleInfoRecycle" :class="{ 'blue--text': sortBy === '已回收数量' }">已回收：{{item.recycleVolume}}份</div>
              </v-card-title>
              <v-divider></v-divider>
              <!-- <v-container></v-container> -->
              <v-list dense>
                <v-list-item>
                  <v-list-item-content
                     v-if="item.havePublish"
                    class="align-end"
                    :class="{ 'blue--text': sortBy === '截止时间' }"
                  >
                    截止时间:{{ item.endTime | formatDate }}
                  </v-list-item-content>
                  <v-list-item-content v-else class="align-end" :class="{ 'blue--text': sortBy === 'endTime' }">
                    未发布
                  </v-list-item-content>
                    <v-btn   text v-if='!item.isVisitable' @click="openQuestionnaire(item)" color="primary">
                        开启
                        <i class="el-icon-video-play"></i>
                    </v-btn>
                    <v-btn  text v-else color="error" @click="closeQuestionnaire(item.questionnaireID)">
                        停止
                        <i class="el-icon-video-pause"></i>
                    </v-btn>
                    <v-btn v-if="!item.havePublish&&item.kind==1" text color="#00796B" :to="{path:`/normal/${item.encryptQuestionnaireID}`}">编辑<i class="el-icon-edit"></i></v-btn>
                    <v-btn v-if="!item.havePublish&&item.kind==2" text color="#00796B" :to="{path:`/vote/${item.encryptQuestionnaireID}`}">编辑<i class="el-icon-edit"></i></v-btn>
                    <v-btn v-if="!item.havePublish&&item.kind==3" text color="#00796B" :to="{path:`/sign/${item.encryptQuestionnaireID}`}">编辑<i class="el-icon-edit"></i></v-btn>
                    <v-btn v-if="!item.havePublish&&item.kind==4" text color="#00796B" :to="{path:`/exam/${item.encryptQuestionnaireID}`}">编辑<i class="el-icon-edit"></i></v-btn>
                    <v-btn v-if="!item.havePublish&&item.kind==5" text color="#00796B" :to="{path:`/covid/${item.encryptQuestionnaireID}`}">编辑<i class="el-icon-edit"></i></v-btn>
                    <v-btn @click="printQuestionnaire(item.encryptQuestionnaireID)" color="#546E7A" text >
                           导出                               
                           <i class="el-icon-upload"></i>
                    </v-btn>
                    <v-btn text @click="copyQuestionnaire(item.questionnaireID)" color="#000000">
                        复制
                        <i class="el-icon-document-copy"></i>
                        </v-btn>
                        <v-btn text color="error" @click="deleteQuestionnaire(item.questionnaireID)">
                        删除
                        <i class="el-icon-delete-solid"></i>
                    </v-btn>
                    <v-btn :to="'/preview/' + item.encryptQuestionnaireID" text color="#FBC02D">
                            预览
                            <i class="el-icon-view"></i>
                            </v-btn>
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
            <v-card-text class="URLcenter-block">
              <div  class="pa-5 justify-center questionnaireURL">问卷填写链接：{{questionnaireURL(item)}}
              </div>
              <v-list dense>
                <v-list-item>
                  <v-list-item-content>
                <vue-qr :text="questionnaireURL(item)" 
                      :callback="qrCodeGenSuccess()"
                      :margin="0" 
                      colorDark="#000" 
                      colorLight="#fff" 
                      :size="250">
                </vue-qr>
                </v-list-item-content>
                <v-list-item-content >
                       <v-col>
                        <v-btn class="fs12" @click="copyCode(item)">
                        复制链接
                        <i class="el-icon-document-copy"></i>
                        </v-btn>
                        
                       </v-col> 
                        <v-col> 
                       <v-btn color="primary">
                        下载二维码
                        <i class="el-icon-download"></i>
                        </v-btn>
                        </v-col> 
                       <v-col> 
                        <v-btn @click="reset(item.questionnaireID)" color="success">
                        重新生成链接和二维码
                        <i class="el-icon-refresh"></i>
                        </v-btn>
                        </v-col> 
                </v-list-item-content>
                </v-list-item>
                <div>
                  
                </div>
              </v-list>
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
            
                    <v-btn text color="#B388FF" :to="'/normalAnalyse/' + item.encryptQuestionnaireID">
                        数据分析
                        <i class="el-icon-s-data"></i>
                        </v-btn>
                </v-list-item>
              </v-list>
            </v-card>
            </v-col>
        </v-row>
        </v-container>
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
        tab:null,
        htmlTitle: '测试PDF文件名',
        qrCodeUrl: '',
        isOpening: false,
        itemsPerPageArray: [4, 8, 12],
        search: '',
        filter: {},
        sortDesc: true,
        page: 1,
        itemsPerPage: 4,
        sortBy: '创建时间',
        tabitems:[
          '所有问卷',
          '未发布',
          '收集中',
          '已停止'
        ],
        sortkeys: [
          '标题',
          '创建时间',
          '已回收数量',
          '截止时间'
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
      //复制网址函数
      copyCode(item) {
        this.msgSuccess(`${this.questionnaireURL(item)}`);
	this.$copyText(`${this.questionnaireURL(item)}`).then(
		        res => {
		          console.log(res)
		          this.msgSuccess("已成功复制，可直接去粘贴");
		        },
		        err => {
		          this.msgSuccess("复制失败");
		        }
		      )
		    },

      funsortBy(sortBy) {
        if(sortBy == '标题' ) return 'title';
        else if(sortBy == '创建时间') return 'createTime';
        else if(sortBy == '已回收数量') return 'recycleVolume';
        else if(sortBy == '截止时间') return 'endTime';
      },
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
        return `http://39.105.38.175/fillQuestionnaire/${item.encryptQuestionnaireID}`
      },
      questionnaireStatusForTab (item) {
        if(item.havePublish == 0) return '1';
        else if(item.havePublish == 1 && item.isVisitable == 0) return '3'
        else return '2'
      },

      questionnaireStatus (item) {
        if(item.havePublish == 0) return '未发布';
        else if(item.havePublish == 1 && item.isVisitable == 0) return '已暂停收集'
        else return '收集中'
      },
      questionnaireStatusClass (item) {
        if(item.havePublish == 0) return '#BDBDBD';
        else if(item.havePublish == 1 && item.isVisitable == 0) return 'red'
        else return 'success'
      },
      
      questionnaireKind (item) {
        if(item.kind == 1 ) return '普通问卷';
        else if(item.kind == 2) return '投票问卷';
        else if(item.kind == 3) return '报名问卷';
        else if(item.kind == 4) return '考试问卷';
        else if(item.kind == 5) return '疫情防控问卷';
      },

      statusClass (item) {
        if(item.kind == 1 ) return 'primary';
        else if(item.kind == 2) return 'secondary';
        else if(item.kind == 3) return 'red';
        else if(item.kind == 4) return 'green';
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
      reset (questionnaireID) {
        this.$http({
        method: "post",
        url: "/resetEncryptQuestionnaireID",
        params: {
          questionnaireID:questionnaireID
        },
      })
        .then((res) => {
          if (res.data.success) {
            this.msgSuccess('刷新成功');
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

<style lang="scss" scoped>
.titleInfoRecycle {
    font-size: 15px;
    margin-right: 2%;
}
.titleInfoCreateTime {
    font-size: 15px;
    margin-right: 5%;
}
.startQuestionnaire{
  margin-right: 15%;
}
.editQuestionnaire{
  margin-right: 12%;
}
.questionnaireTitle{
    font-size: 25px;
    font-weight: bold;
}
.questionnaireCardClass{
  border-bottom-width: 10px;
}
.questionnaireURL{
  font-size: 15px;
}
.URLbutton{
  margin-right: 10%;
}
</style>