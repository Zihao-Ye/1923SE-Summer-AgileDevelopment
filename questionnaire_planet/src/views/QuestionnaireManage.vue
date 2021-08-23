<template>
    <div class="home-container">
        <el-container>
            <el-header class="header-container" height="38">
                <div>管理问卷页面</div>
            </el-header>
            <el-container>
                <el-aside width="280px">
                    <el-card>
                        <el-button type="primary" @click="createBlankQuestionnaire">新建问卷</el-button>
                        <div class="menu-view">
                            <div v-for="menu in menuList" :key="menu.route"
                                 :class="defaultActiveMenu==menu.route?'menu-item-active menu-item':'menu-item'"
                                 @click="menuClickHandle(menu)"
                            >
                                <i :class="menu.icon"></i>
                                <span>{{ menu.name }}</span>
                            </div>
                        </div>
                        <avatar></avatar>
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
    margin-top: 20px;
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

</style>
