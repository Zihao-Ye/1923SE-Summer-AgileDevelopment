<template>
  <v-menu bottom min-width="150px" round offset-y>
    <template v-slot:activator="{ on }">
      <v-btn icon v-on="on">
        <v-avatar  size="40" color="#96CDCD">
            <span>{{$store.state.userID.substr(0,1)}}</span>
        </v-avatar>
      </v-btn>
    </template>
    <v-card>
      <v-list-item-content class="justify-center">
        <div class="mx-auto text-center">
          <v-avatar color="#96CDCD">
              <span>{{$store.state.userID.substr(0,1)}}</span>
          </v-avatar>
          <v-btn depressed rounded text @click="logout"> 退出登录 </v-btn>
        </div>
      </v-list-item-content>
    </v-card>
  </v-menu>
</template>

<script>

export default {

  data:()=>({
    user:{
      username:"zy",
      userImage:"",
      signature:"",
      userID:"123",
    }
  }),

  methods: {
    logout(){
//        this.$store.commit('setLogout')
//        this.$router.push({path:"/"});
      this.$http({
        method:'post',
        url:"/logout",
      }).then(res=>{
        if(res.data.success){
          this.$store.commit('setLogout')
          this.$router.push({path:"/"});
          sessionStorage.clear();
        }
      })

    }
  },
}
</script>