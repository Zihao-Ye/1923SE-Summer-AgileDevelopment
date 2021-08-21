
<template>
  <div id="cesiumViewer"></div>
</template>
<script>
export default {
  name: "Map",
  components: {},
  data() {
    return {};
  },
  methods: {
    initcesiumViewer() {

      var Cesium=this.Cesium
      this.Cesium.Ion.defaultAccessToken =
          "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIxMjg3MmQyYS1mMjc0LTQwYzgtYjViNy1hODI5MTYxOTE4ZjAiLCJpZCI6NjQ3ODMsImlhdCI6MTYyOTUxMzY1NX0.NB2ZLSAiPnGXMaBQAlHIDsaxr750CupD7hmK8Xlmvoo";
      var viewer = new this.Cesium.Viewer("cesiumViewer", {
        // imageryProvider: earth, //地图数据
        animation: false, //是否显示动画控件，左下角仪表
        baseLayerPicker: false, //是否显示图层选择控件,是否显示geocoder小器件，右上角查询按钮
        geocoder: false, //是否显示地名查找控件
        timeline: false, //是否显示时间线控件
        sceneModePicker: false, //是否显示投影方式控件,//是否显示3D/2D选择器
        navigationHelpButton: false, //是否显示帮助信息控件
        infoBox: false, //是否显示点击要素之后显示的信息
        fullscreenButton:false, //是否显示全屏按钮
        selectionIndicator: false, //是否显示选中指示框
        // imageryProviderViewModels: [img_arcgis_yxdt, img_arcgis_jcdt, img_tdt_sl, img_tdt_yx],//可供BaseLayerPicker选择的图像图层ProviderViewModel数组
        // selectedImageryProviderViewModel: img_arcgis_jcdt,//当前地形图层的显示模型，仅baseLayerPicker设为true有意义
        // scene3DOnly: true, //如果设置为true，则所有几何图形以3D模式绘制以节约GPU资源
         homeButton: false, //是否显示Home按钮


      });

      viewer.scene.globe.enableLighting = true//必须开启光照效果，
      viewer._cesiumWidget._creditContainer.style.display = "none";
      viewer.terrainProvider = new Cesium.createWorldTerrain({
        requestVertexNormals: true,
        requestWaterMask: true
      });
    }
  },
  mounted() {
    this.initcesiumViewer();
  }
};
</script>
<style scoped>
#cesiumViewer {
  height: 1500px;
  width: 100%;
  margin: 0 auto;
}
.cesium-widget-credits{
  display:none !important;
}
</style>
