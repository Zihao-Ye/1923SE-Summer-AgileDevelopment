<template>
  <v-app>
    <v-main>
      <earth></earth>
      <div class="text-center">
        <v-btn
            class="btn1"
            color="#dcd0d0"
            absolute
            dark
            x-large
            min-height="80px"
            min-width="180px"
            rounded
            outlined
            :to="{name:'QuestionnaireManage'}"
        >
          管理问卷
        </v-btn>
        <div class="btn3">
          <template v-if="this.$store.state.login">
            <avatar ></avatar>
          </template>
          <template v-else>
            <v-btn
                color="indigo"
                absolute
                dark
                :to="{name:'Login'}"
                outlined
            >
              登陆
            </v-btn>
          </template>
        </div>
        <v-btn
            class="btn2"
            color="#dcd0d0"
            absolute
            dark
            x-large
            min-height="80px"
            min-width="180px"
            rounded
            outlined
        >
          创建问卷
        </v-btn>

      </div>
    </v-main>
  </v-app>
</template>

<script>
  import earth from '../components/Earth.vue'
  import avatar from '../components/Avatar.vue'
  export default {
    components: {
      earth,
      avatar
    },
  }
</script>

<style>
.btn1{
  top:10%;
  left:5%;

}
.btn2{
  top:18%;
  left:5%
}
.btn3{
  position: absolute;
  top:1%;
  right:1%
}
</style>
