<template>
  <v-btn @click="download">
    下载
  </v-btn>
</template>

<script>
export default {
  name: "Test",
  data(){
    return{
      message:""
    }
  },
  methods:{
    download(){
      window.open("http://39.105.38.175/download/1120210823.xlsx")
    }
  }
}
</script>

<style scoped>

</style>