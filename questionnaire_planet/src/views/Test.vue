<template>
  <div>
    <h1>测试webSocket</h1>
    <button @click="getWebsocket">点击请求后台数据</button>
  </div>

</template>
<script>
export default {
  created() { // 页面创建生命周期函数
    this.initWebSocket()
  },
  destroyed: function () { // 离开页面生命周期函数
    this.websocketclose();
  },
  methods: {
    initWebSocket: function () {
      // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
      this.websock = new WebSocket("ws://39.105.38.175:8080/api/websocket/ee/1");
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen: function () {
      console.log("WebSocket连接成功");
    },
    websocketonerror: function (e) {
      console.log("WebSocket连接发生错误");
    },
    websocketonmessage: function (e) {
      console.log(e.data);                // console.log(e);
    },
    websocketclose: function (e) {
      console.log("connection closed (" + e.code + ")");
    },
    getWebsocket:function(){
      // 这里只是一个基于axios的ajax请求，你可以换成你的请求格式
      this.$http({
        method:'post',
        url:'/decreaseVolume',
        params:{
          questionContentID:2,
          questionOptionID:2,
          userID:2,
        }
      }).then(res=>{
        console.log(res.data)
      })
    }
  }
}
</script>
<style lang="less" scoped>
</style>
