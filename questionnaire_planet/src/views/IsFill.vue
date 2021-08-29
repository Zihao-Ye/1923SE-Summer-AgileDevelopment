<template>
  <v-card class="mx-auto thankCard" max-width="1000" elevation="10" rounded height="400" color="#708090">
    <div class="text-center submitSuccess">
      <svg t="1630222400809" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3767" width="200" height="200"><path d="M877.714286 475.428571l0 402.285714L109.714286 877.714286 109.714286 146.285714l585.142857 0L694.857143 73.142857 73.142857 73.142857c-20.214857 0-36.571429 16.374857-36.571429 36.571429l0 804.571429c0 20.214857 16.356571 36.571429 36.571429 36.571429l841.142857 0c20.214857 0 36.571429-16.356571 36.571429-36.571429L950.857143 475.428571 877.714286 475.428571z" p-id="3768" fill="#0fa0ed"></path><path d="M1024 60.571429C1024 47.3216 1013.249829 36.571429 1000 36.571429c-6.286629 0-11.7504 2.731886-16.071314 6.696229l-471.572114 529.517714L329.142857 390.749257l-127.285029 124.679314 51.856457 0 257.929143 240.786286L1020.642743 72.213943C1022.6432 68.714057 1024 64.8576 1024 60.571429z" p-id="3769" fill="#0fa0ed"></path></svg>
      <p class="thank">
        您已填写过此问卷，请不要重复填写
      </p>
    </div>
  </v-card>
</template>

<script>
export default {

}
</script>

<style scoped>
.thank{
  font-size: 50px;
  color: #0fa0ed;
}
.submitSuccess{
  position: relative;
  top: 20%;
}
.thankCard{
  top:25%
}
</style>