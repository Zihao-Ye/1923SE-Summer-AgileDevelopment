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
      centerPointer:"",
      address:""
    }
  },
  methods:{
    getAddress (points) {
      const geocoder = new window.AMap.Geocoder({
        radius: 1000
      })
      geocoder.getAddress(points, (status, result) => {
        if (status === 'complete' && result.regeocode) {
          this.address = result.regeocode.formattedAddress
          // console.log('当前经纬度：' + points)
          // console.log('当前详细地址：' + this.address)
          // 在这里请求周边数据点
          this.getOrundPosition(points[0], points[1])
        }
      })
    },
    getLocation() {
      const self = this
      const AMap=window.AMap
      AMap.plugin('AMap.Geolocation', function () {
        var geolocation = new AMap.Geolocation({
          // 是否使用高精度定位，默认：true
          enableHighAccuracy: true,
          // 设置定位超时时间，默认：无穷大
          timeout: 10000,
        })

        geolocation.getCurrentPosition()
        AMap.event.addListener(geolocation, 'complete', onComplete);
        AMap.event.addListener(geolocation, 'error', onError);

        function onComplete(data) {
          // data是具体的定位信息
          var gpsPoint = GPS.gcj_encrypt(data.position.getLat(), data.position.getLng());

          self.centerPointer = gpsPoint;

          self.getAddress(gpsPoint);

        }

        function onError(data) {
          // 定位出错
          console.log('定位失败错误：', data);
          // 调用ip定位
          self.getLngLatLocation();
        }
      })
      var GPS = {

        PI: 3.14159265358979324,
        x_pi: 3.14159265358979324 * 3000.0 / 180.0,
        delta: function (lat, lon) {
          var a = 6378245.0; //  a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
          var ee = 0.00669342162296594323; //  ee: 椭球的偏心率。
          var dLat = this.transformLat(lon - 105.0, lat - 35.0);
          var dLon = this.transformLon(lon - 105.0, lat - 35.0);
          var radLat = lat / 180.0 * this.PI;
          var magic = Math.sin(radLat);
          magic = 1 - ee * magic * magic;
          var sqrtMagic = Math.sqrt(magic);
          dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * this.PI);
          dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * this.PI);
          return {
            'lat': dLat,
            'lon': dLon
          };
        },
        //WGS-84 to GCJ-02
        gcj_encrypt: function (wgsLat, wgsLon) {
          if (this.outOfChina(wgsLat, wgsLon))
            return {
              'lat': wgsLat,
              'lon': wgsLon
            };

          var d = this.delta(wgsLat, wgsLon);
          return {
            'lat': wgsLat + d.lat,
            'lon': wgsLon + d.lon
          };

        },

        outOfChina: function (lat, lon) {

          if (lon < 72.004 || lon > 137.8347)

            return true;

          if (lat < 0.8293 || lat > 55.8271)

            return true;

          return false;

        },

        transformLat: function (x, y) {

          var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));

          ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;

          ret += (20.0 * Math.sin(y * this.PI) + 40.0 * Math.sin(y / 3.0 * this.PI)) * 2.0 / 3.0;

          ret += (160.0 * Math.sin(y / 12.0 * this.PI) + 320 * Math.sin(y * this.PI / 30.0)) * 2.0 / 3.0;

          return ret;

        },

        transformLon: function (x, y) {

          var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));

          ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;

          ret += (20.0 * Math.sin(x * this.PI) + 40.0 * Math.sin(x / 3.0 * this.PI)) * 2.0 / 3.0;

          ret += (150.0 * Math.sin(x / 12.0 * this.PI) + 300.0 * Math.sin(x / 30.0 * this.PI)) * 2.0 / 3.0;

          return ret;

        }

      };
    },
    getLngLatLocation() {
      const AMap=window.AMap
      AMap.plugin('AMap.CitySearch', function () {
        var citySearch = new AMap.CitySearch();
        citySearch.getLocalCity(function (status, result) {
          if (status === 'complete' && result.info === 'OK') {
            // 查询成功，result即为当前所在城市信息
            console.log('通过ip获取当前城市：', result)
            //逆向地理编码
            AMap.plugin('AMap.Geocoder', function () {
              var geocoder = new AMap.Geocoder({
                // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                city: result.adcode
              })

              var lnglat = result.rectangle.split(';')[0].split(',');
              geocoder.getAddress(lnglat, function (status, data) {
                if (status === 'complete' && data.info === 'OK') {
                  // result为对应的地理位置详细信息
                  console.log(data)
                }
              })
            })
          }
        })
      })
    },
  },

  created () {
    // 此处为调用精确定位之后，调取ip定位，可根据实际情况改写
    this.getLocation()
  }
}
</script>
