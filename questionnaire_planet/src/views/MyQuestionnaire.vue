<template>
  <v-container fluid>
    <v-data-iterator
      :items="items"
      :items-per-page.sync="itemsPerPage"
      :page.sync="page"
      :search="search"
      :sort-by="sortBy.toLowerCase()"
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
            :key="item.title"
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
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'count' }">
                    已回收数量:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'count' }"
                  >
                    {{ item.count }}
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'buildTime' }">
                    创建时间:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'buildTime' }"
                  >
                    {{ item.buildTime }}
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'firstBeginTime' }">
                    第一次发布时间:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'firstBeginTime' }"
                  >
                    {{ item.firstBeginTime }}
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-content :class="{ 'blue--text': sortBy === 'lastBeginTime' }">
                    最后一次发布时间:
                  </v-list-item-content>
                  <v-list-item-content
                    class="align-end"
                    :class="{ 'blue--text': sortBy === 'lastBeginTime' }"
                  >
                    {{ item.lastBeginTime }}
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
                    {{ item.status }}
                  </v-list-item-content>
                </v-list-item>

                <v-divider></v-divider>

                <v-list-item>
                    <v-btn v-if='!isOpening' color="primary">
                        开启问卷
                        <i class="el-icon-video-play"></i>
                    </v-btn>
                    <v-btn v-else color="error">
                        停止问卷
                        <i class="el-icon-video-pause"></i>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn tile color="success"><v-icon left>mdi-pencil</v-icon>编辑问卷</v-btn>
                </v-list-item>
                <v-list-item>
                    <v-btn :loading="loading3" 
                           :disabled="loading3" 
                           color="blue-grey" 
                           class="ma-2 white--text" 
                           @click="loader = 'loading3'">
                           导出问卷                               
                           <v-icon right dark>mdi-cloud-upload</v-icon>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn depressed>
                        复制问卷
                        <i class="el-icon-document-copy"></i>
                        </v-btn>
                </v-list-item>
                <v-list-item>
                    <v-btn color="error">
                        删除问卷
                        <i class="el-icon-delete-solid"></i>
                    </v-btn>
                    <v-spacer></v-spacer>
                    <v-btn class="ma-2"
                           :loading="loading"
                           :disabled="loading"
                            color="secondary"
                            @click="loader = 'loading'">
                            预览问卷
                            <i class="el-icon-view"></i>
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
  export default {
    data () {
      return {
        isOpening: false,
        itemsPerPageArray: [4, 8, 12],
        search: '',
        filter: {},
        sortDesc: false,
        page: 1,
        itemsPerPage: 4,
        sortBy: 'title',
        sortkeys: [
          'title',
          'count',
          'firstBeginTime',
          'lastBeginTime',
          'buildTime',
        ],
        keys: [
          'title',
          'count',
          'firstBeginTime',
          'lastBeginTime',
          'buildTime',
          'status',
        ],
        items: [
          {
            title: 'Frozen Yogurt',
            count: 156,
            firstBeginTime: '2021-08-03 18:00:11',
            lastBeginTime: '2021-08-03 18:00:11',
            buildTime: '2021-08-03 18:00:11',
            status:'收集中'
          },
          {
            title: 'Arozen Yogurt',
            count: 154,
            firstBeginTime: '2021-08-02 18:00:11',
            lastBeginTime: '2021-08-02 18:00:11',
            buildTime: '2021-08-02 18:00:11',
            status:'收集中'
          },
          {
            title: 'Brozen Yogurt',
            count: 159,
            firstBeginTime: '2021-08-01 18:00:11',
            lastBeginTime: '2021-08-01 18:00:11',
            buildTime: '2021-08-01 18:00:11',
            status:'收集中'
          },
          {
            title: 'Frozen Yogurt',
            count: 159,
            firstBeginTime: '2021-08-03 18:00',
            lastBeginTime: '2021-08-03 18:00',
            buildTime: '2021-08-03 18:00',
            status:'收集中'
          },
          {
            title: 'Frozen Yogurt',
            count: 159,
            firstBeginTime: '2021-08-03 18:00',
            lastBeginTime: '2021-08-03 18:00',
            buildTime: '2021-08-03 18:00',
            status:'收集中'
          },
          {
            title: 'Frozen Yogurt',
            count: 159,
            firstBeginTime: '2021-08-03 18:00',
            lastBeginTime: '2021-08-03 18:00',
            buildTime: '2021-08-03 18:00',
            status:'收集中'
          },
          {
            title: 'Frozen Yogurt',
            count: 159,
            firstBeginTime: '2021-08-03 18:00',
            lastBeginTime: '2021-08-03 18:00',
            buildTime: '2021-08-03 18:00',
            status:'收集中'
          },
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
      numberOfPages () {
        return Math.ceil(this.items.length / this.itemsPerPage)
      },
      filteredKeys () {
        return this.keys.filter(key => key !== 'title')
      },
    },
    methods: {
      nextPage () {
        if (this.page + 1 <= this.numberOfPages) this.page += 1
      },
      formerPage () {
        if (this.page - 1 >= 1) this.page -= 1
      },
      updateItemsPerPage (number) {
        this.itemsPerPage = number
      },
    },
  }
</script>