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
        <v-btn
            class="btn3"
            color="indigo"
            absolute
            dark
            :to="{name:'Login'}"
        >
          登陆
        </v-btn>
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
  export default {
    components: {
      earth
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
  top:1%;
  right:1%
}
</style>
