<template>
  <div>
    经纬度：{{latitude}}{{longitude}}
  </div>
</template>

<script>
export default {
  data() {
    return{
      latitude:"",
      longitude:"",
    }
  },
  mounted() {
    this.getLocation()
  },
  methods:{
    // 获取经纬度信息
    getLocation () {
      const _this = this
      const AMap = window.AMap
      AMap.plugin(['AMap.Geolocation', 'AMap.Geocoder'], function () {
        var geolocation = new AMap.Geolocation({
          // 是否使用高精度定位，默认：true
          enableHighAccuracy: true,
          // 设置定位超时时间，默认：无穷大
          timeout: 10000
        })

        geolocation.getCurrentPosition()
        AMap.event.addListener(geolocation, 'complete', onComplete)
        function onComplete (data) {
          console.log('定位成功纬度信息：', data.position.lat)
          console.log('定位成功经度信息：', data.position.lng)
          _this.latitude = data.position.lat
          _this.longitude = data.position.lng
          // 把经纬度信息保存到VUEx
        }
      })
    }
  }
}
</script>
