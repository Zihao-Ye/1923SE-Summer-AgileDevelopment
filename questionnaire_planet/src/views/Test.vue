<template>
<p>{{message}}</p>
</template>

<script>
export default {
  name: "Test",
  data(){
    return{
      message:""
    }
  },
  mounted() {
    this.$http({
      method:"get",
      url:"/test"
    }).then(res=>{
      this.message=res.data.message
    })
  }
}
</script>

<style scoped>

</style>