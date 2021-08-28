<template>
    <div>
        <div v-for="i in num"  @click="clickStars(i)" style="display:inline-block">
          <img src="~@/assets/on.png" v-if="xing>i">
          <img src="~@/assets/off.png" v-if="xing<=i">
        </div>
    </div>
        
 
</template>
 
<script>
 
export default {
  name: 'star',
  props:{
      n:String
  },
  watch: {
    n(newVal,oldVal){
        this.num=parseInt(newVal)
    }
  },
  data () {
    return {
      num:5,
      xing:0,
    }
  },
  methods:{
    clickStars(i){
      this.xing = i+1
    }
  },
}
</script>

