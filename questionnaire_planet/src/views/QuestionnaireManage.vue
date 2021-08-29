<template>
    <div class="home-container">
        <el-container>
            <el-container>
                <el-aside class="asidePosition" width="280px">
                    <el-card>
                        <svg t="1630120164825" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1271" width="64" height="64"><path d="M808.832 478.592c-42.794667 57.685333-94.762667 117.504-153.770667 176.469333-219.989333 220.032-451.584 342.613333-540.16 254.037334-52.736-52.778667-32.938667-147.968 36.053334-266.112A384 384 0 0 1 643.029333 150.954667c118.101333-68.992 213.333333-88.789333 266.069334-36.053334 51.114667 51.157333 31.872 149.930667-35.84 266.666667a384.128 384.128 0 0 1-312.32 511.36 42.666667 42.666667 0 0 1-10.752-84.693333 298.794667 298.794667 0 0 0 258.645333-329.642667zM776.96 374.186667a298.666667 298.666667 0 1 0-402.901333 402.901333c70.314667-45.781333 147.072-108.757333 220.629333-182.314667 73.557333-73.557333 136.533333-150.272 182.272-220.586666zM289.536 825.088a384.469333 384.469333 0 0 1-90.581333-90.624c-28.8 60.714667-35.114667 102.869333-23.722667 114.304 10.837333 10.794667 52.650667 5.546667 114.304-23.68zM734.464 198.954667a386.176 386.176 0 0 1 90.581333 90.624c29.226667-61.653333 34.517333-103.509333 23.722667-114.346667-11.434667-11.392-53.589333-5.12-114.346667 23.722667z" p-id="1272" fill="#1296db"></path></svg>
                        <h3 class="blue--text" style="font-size: 30px;">问卷星球</h3>
                        <el-button type="primary" @click="createBlankQuestionnaire">新建问卷</el-button>
                        <div class="menu-view">
                            <div v-for="menu in menuList" :key="menu.route"
                                 :class="defaultActiveMenu==menu.route?'menu-item-active menu-item':'menu-item'"
                                 @click="menuClickHandle(menu)"
                            >
                                <i :class="menu.icon"></i>
                                <span class="homemenuClass">{{ menu.name }}</span>
                                <v-container></v-container>
                            </div>
                            <div class="avatarposition"><avatar ></avatar></div>
                        </div>
                    
                    </el-card>
                </el-aside>
                <el-container>
                    <el-main>
                        <!-- <div>{{defaultActiveMenu}}</div> -->
                        <router-view></router-view>
                    </el-main>
                </el-container>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import Avatar from '../components/Avatar.vue'

export default {
    name: 'NewIndex',
    components: {Avatar},
    data() {
        return {
            defaultActiveMenu: '',
            menuList: [
                {
                    route: '/QuestionnaireManage',
                    name: '我的问卷',
                    icon: 'el-icon-user-solid'
                },
                {
                    route: '/QuestionnaireManage/Recycle',
                    name: '回收站',
                    icon: 'el-icon-delete-solid'
                }
            ]
        }
    },
    computed: {
    },
    created() {
        this.defaultActiveMenu = this.$route.path
    },
    methods: {
        menuClickHandle(menu) {
            this.$router.replace({path: menu.route})
            this.defaultActiveMenu = menu.route
        },
        createBlankQuestionnaire() {
          this.$router.push({path:'/create'})
            console.log('新建成功')
        },
    }
}
</script>

<style lang="scss" scoped>
$menuActiveText: #409eff;

.home-container {
    background-color: rgba(247, 247, 247, 90);
    display: flex;
    height: 100%;
    width: 100%;
    flex-direction: column;
}
.header-container {
    display: flex;
    justify-content: space-between;
    background-color: #fff;
    line-height: 38px;
    font-size: 30px;
    height: 38px;
    min-width: 1024px;
    .header-logo-img {
        width: 120px;
        height: 35px;
        float: left;
        margin-left: 55px;
    }
    .right-header {
        display: flex;
        flex-direction: row;
        > * {
            margin: 0 20px;
        }
    }
    .user-avatar {
        width: 35px;
        height: 35px;
        border-radius: 100px;
        cursor: pointer;
    }
}
.menu-view {
    margin-top: 50px;
    height: 501px;
    background-color: rgba(255, 255, 255, 100);
    padding: 20px;
    text-align: center;
    .menu-item-active {
        color: $menuActiveText !important;
    }
    .menu-item {
        color: #333;
        font-size: 16px;
        text-align: left;
        line-height: 25px;
        &:hover {
            cursor: pointer;
            color: $menuActiveText;
        }
        & .fas:hover {
            color: $menuActiveText;
        }
        .fas {
            display: inline-block;
            width: 20px;
            height: 20px;
            margin: 5px;
        }
        span {
            margin-left: 10px;
        }
    }
}
.banner-container {
    height: 60px;
}
.el-aside {
    padding: 0;
    margin: 0;
    .el-card {
        height: 100%;
        ::v-deep .el-card__body {
            display: flex !important;
            flex-direction: column !important;
            justify-content: center !important;
            align-items: center !important;
        }
        .el-button {
            width: 80%;
            margin-top: 20px;
        }
    }
}
.user-person-menu {
    .nick-name {
        height: 16px;
        color: rgba(70, 70, 70, 86);
        font-size: 14px;
        line-height: 16px;
        text-align: left;
    }
    .person-menu-item {
        color: rgba(70, 70, 70, 86);
        font-size: 14px;
        text-align: left;
        &:hover {
            cursor: pointer;
            color: $menuActiveText;
        }
    }
    .el-divider {
        margin: 5px 0;
    }
    .person-menu-divider {
        background-color: rgba(210, 210, 210, 78);
        border: 1px solid rgba(210, 210, 210, 78);
    }
}
.about-container {
    text-align: center;
    .fa-user {
        font-size: 19px;
        color: rgba(172, 172, 172, 100);
        margin: 1px;
    }
}
.avatarposition{
    margin-top: 100%;
}
.asidePosition{
    margin-top: 0%;
}
.homemenuClass{
    font-size: 18px;
}

</style>
