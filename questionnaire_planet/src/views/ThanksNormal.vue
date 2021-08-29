<template>
  <v-card class="mx-auto thankCard" max-width="1000" elevation="10" rounded height="400" color="#708090">
    <div class="text-center submitSuccess">
      <svg t="1630221703883" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2219" width="200" height="200"><path d="M493.2 748.4v80.7h79.2l229.5-236-79.3-80.8zM862.3 501.7l-47.7-48.6c-8.3-8.5-21-8.5-29.3 0l-37.6 38.3 79.2 80.8 35.3-40.6c8.2-8.5 8.2-21.6 0.1-29.9z" p-id="2220" fill="#FF9100"></path><path d="M425.6 780.4h-139c-19.9 0-36.1-17.2-36.1-38.3V256.5c0-21.1 16.2-38.3 36.1-38.3h434.8c19.9 0 36.1 17.2 36.1 38.3v123.6c0 16.7 13.1 30.3 29.2 30.3s29.2-13.6 29.2-30.3V256.5c0-54.5-42.4-98.9-94.4-98.9H286.6c-52.1 0-94.4 44.4-94.4 98.9v485.6c0 54.6 42.4 98.9 94.4 98.9h139c16.1 0 29.2-13.6 29.2-30.3s-13.1-30.3-29.2-30.3z" p-id="2221" fill="#FF9100"></path><path d="M688 329.4c0-16.7-13.1-30.3-29.2-30.3H346.1c-16.1 0-29.2 13.6-29.2 30.3 0 16.7 13.1 30.3 29.2 30.3h312.7c16.1 0 29.2-13.6 29.2-30.3zM468.6 550.9H346.1c-16.1 0-29.2 13.6-29.2 30.3 0 16.7 13.1 30.3 29.2 30.3h122.5c16.1 0 29.2-13.6 29.2-30.3 0-16.7-13.1-30.3-29.2-30.3zM346.1 420.8c-16.1 0-29.2 13.6-29.2 30.3 0 16.7 13.1 30.3 29.2 30.3h215.8c16.1 0 29.2-13.6 29.2-30.3 0-16.7-13.1-30.3-29.2-30.3H346.1z" p-id="2222" fill="#FF9100"></path></svg>
      <p class="thank">
        {{message}}
      </p>
    </div>
  </v-card>
</template>

<script>
export default {
  data(){
    return{
      message:"填写成功"
    }
  },
  methods:{
    getQuestionnaire(questionnaireID) {
      this.$http({
        method: "get",
        url: "/showQuestionnaireInfo",
        params: {
          questionnaireID:this.$route.params.id
        },
      })
          .then((res) => {
            console.log(res.data)
            if (res.data.success) {
              this.message=res.data.questionnaire.endMessage
            }
          })
          .catch((err) => {
            console.log(err);
          });
    },
  },
  created() {
    this.getQuestionnaire()
  }
}
</script>

<style scoped>
.thank{
  font-size: 50px;
  color: #FF9100;
}
.submitSuccess{
  position: relative;
  top: 20%;
}
.thankCard{
  top:25%
}
.end{
  background-color: #F8F8FF;
}
</style>