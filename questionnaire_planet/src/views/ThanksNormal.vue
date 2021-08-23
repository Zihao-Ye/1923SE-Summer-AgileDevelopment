<template>
  <v-card class="mx-auto thankCard" max-width="1000" elevation="10" rounded height="400">
    <div class="text-center submitSuccess">
      <p class="thank">
        提交成功
      </p>
    </div>
  </v-card>
</template>

<script>
export default {
  name: "Thanks"
}
</script>

<style scoped>
.thank{
  font-size: 50px;
}
.submitSuccess{
  position: relative;
  top: 40%;
}
.thankCard{
  top:25%
}
</style>