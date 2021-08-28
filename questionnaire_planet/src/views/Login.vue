<template>
  <div class="login">
    <div class="card">
      <div class="front" :class="isTop ? 'contain-Before' : ''" id="1">
        <v-container>
          <v-form ref="form" lazy-validation>
            <svg t="1630030079745" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4245" width="200" height="200"><path d="M808.832 478.592c-42.794667 57.685333-94.762667 117.504-153.770667 176.469333-219.989333 220.032-451.584 342.613333-540.16 254.037334-52.736-52.778667-32.938667-147.968 36.053334-266.112A384 384 0 0 1 643.029333 150.954667c118.101333-68.992 213.333333-88.789333 266.069334-36.053334 51.114667 51.157333 31.872 149.930667-35.84 266.666667a384.128 384.128 0 0 1-312.32 511.36 42.666667 42.666667 0 0 1-10.752-84.693333 298.794667 298.794667 0 0 0 258.645333-329.642667zM776.96 374.186667a298.666667 298.666667 0 1 0-402.901333 402.901333c70.314667-45.781333 147.072-108.757333 220.629333-182.314667 73.557333-73.557333 136.533333-150.272 182.272-220.586666zM289.536 825.088a384.469333 384.469333 0 0 1-90.581333-90.624c-28.8 60.714667-35.114667 102.869333-23.722667 114.304 10.837333 10.794667 52.650667 5.546667 114.304-23.68zM734.464 198.954667a386.176 386.176 0 0 1 90.581333 90.624c29.226667-61.653333 34.517333-103.509333 23.722667-114.346667-11.434667-11.392-53.589333-5.12-114.346667 23.722667z" p-id="4246" fill="#515151"></path></svg><br />
            <v-container>
              <v-row justify="center">
                <v-col cols="12" sm="10">
                  <v-text-field
                    v-model="id"
                    :rules="idRules"
                    label="账号"
                    required
                  ></v-text-field>

                  <v-text-field
                    v-model="password"
                    :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="passwordRules"
                    :type="show1 ? 'text' : 'password'"
                    label="密码"
                    required
                    @click:append="show1 = !show1"
                  ></v-text-field>

                  <v-btn :disabled="!valid" class="button" @click="Login" x-large>
                    <p class="login_">登录</p>
                  </v-btn>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </v-container>
        <v-container>
          <v-col justify="center" cols="12">
            <v-btn text color="primary" class="register" @click="toRegister"
              >注册</v-btn
            >
          </v-col>
        </v-container>
      </div>
      <div class="back" :class="isTop ? 'contain-After' : ''" id="2">
        <v-container>
          <v-form ref="form" lazy-validation>
            <svg t="1630030079745" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4245" width="200" height="200"><path d="M808.832 478.592c-42.794667 57.685333-94.762667 117.504-153.770667 176.469333-219.989333 220.032-451.584 342.613333-540.16 254.037334-52.736-52.778667-32.938667-147.968 36.053334-266.112A384 384 0 0 1 643.029333 150.954667c118.101333-68.992 213.333333-88.789333 266.069334-36.053334 51.114667 51.157333 31.872 149.930667-35.84 266.666667a384.128 384.128 0 0 1-312.32 511.36 42.666667 42.666667 0 0 1-10.752-84.693333 298.794667 298.794667 0 0 0 258.645333-329.642667zM776.96 374.186667a298.666667 298.666667 0 1 0-402.901333 402.901333c70.314667-45.781333 147.072-108.757333 220.629333-182.314667 73.557333-73.557333 136.533333-150.272 182.272-220.586666zM289.536 825.088a384.469333 384.469333 0 0 1-90.581333-90.624c-28.8 60.714667-35.114667 102.869333-23.722667 114.304 10.837333 10.794667 52.650667 5.546667 114.304-23.68zM734.464 198.954667a386.176 386.176 0 0 1 90.581333 90.624c29.226667-61.653333 34.517333-103.509333 23.722667-114.346667-11.434667-11.392-53.589333-5.12-114.346667 23.722667z" p-id="4246" fill="#515151"></path></svg><br />
            <v-container>
              <v-row justify="center">
                <v-col cols="12" sm="10">
                  <v-text-field
                    v-model="id"
                    :rules="idRules"
                    label="账号"
                    required
                  ></v-text-field>

                  <v-text-field
                    v-model="password"
                    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                    :rules="passwordRules"
                    :type="show2 ? 'text' : 'password'"
                    label="密码"
                    required
                    @click:append="show2 = !show2"
                  ></v-text-field>

                  <v-text-field
                    v-model="rePassword"
                    :append-icon="show3 ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="show3 ? 'text' : 'password'"
                    :rules="[affirmPass]"
                    label="确认密码"
                    required
                    @click:append="show3 = !show3"
                  ></v-text-field>


                  <v-btn :disabled="!valid" class="button" @click="Register" x-large>
                    <p class="register_">注册</p>
                  </v-btn>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </v-container>
        <v-container>
          <v-row>
            <v-col justify="center" cols="12">
              <v-btn text color="primary" class="return" @click="toLogin"
                >登录</v-btn
              >
            </v-col>
          </v-row>
        </v-container>
      </div>
      <v-snackbar
        v-model="snackbar"
        :timeout="3000"
        color="blue-grey"
        absolute
        rounded="pill"
        top
      >
        {{ message }}
      </v-snackbar>
    </div>
  </div>
</template>

<script>
export default {
  inject: ["reload"],
  data() {
    return {
      valid: true,
      show1: false,
      show2: false,
      show3: false,
      isTop: false,
      timer: null,
      snackbar: false,
      id: "",
      idRules: [(v) => !!v || "请填写账号"],
      pass: "",
      password: "",
      passwordRules: [(v) => !!v || "请填写密码"],
      rePassword: "",
      Email: "",
      emailRules: [
        (v) => !!v || "请填写邮箱",
        (v) => /.+@.+\..+/.test(v) || "邮箱格式不合法",
      ],
      checkbox: false,
      message: "登录成功",
    };
  },
  methods: {
    toRegister() {
      if (!this.isTop) {
        this.isTop = true;
        this.clear();
      }
    },
    toLogin() {
      if (this.isTop) {
        this.isTop = false;
        this.clear();
      }
    },
    Login() {
      this.validate();
      this.$http({
        method: "post",
        url: "/login",
        params: {
          userName: this.id,
          userPwd: this.password,
        },
      })
        .then((res) => {
          this.message = res.data.message;
          if (res.data.success) {
            this.$store.commit("setLogin");
            this.$store.commit("setUserName",this.id);
            this.$store.commit("setUserID",res.data.user.userID)
            this.$router.go(-1)
          }
        })
        .catch((err) => {
          console.log(err);
        });
      this.snackbar = true;
    },
    Register() {
      this.validate();
      this.$http({
        method: "post",
        url: "/register",
        params: {
          userName: this.id,
          userPwd: this.password,
          reUserPwd: this.rePassword,
        },
      })
        .then((res) => {
          console.log(res.data.message);
          this.message = res.data.message;
          this.snackbar = true;
          if (res.data.success) {
            this.$store.commit("setLogin");
            this.timer = setTimeout(() => {
              //设置延迟执行
              this.reload();
            }, 1000);
          } else {
            this.clear();
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    validate() {
      this.$refs.form.validate();
    },
    clear() {
      this.id = "";
      this.password = "";
      //this.message = "";
      this.rePassword = "";
      this.Email = "";
    },
    affirmPass(val) {
      if (val !== this.password) {
        return "两次密码不一致";
      }
      return true;
    },
  },
};
</script>

<style>
.login {
  background-color: #efeeee;
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
#logo {
  position: relative;
  bottom: -20px;
}

.button {
  font-size: 20px;
  color: #596275;
  box-shadow: 9px 9px 18px rgba(0, 0, 0, 0.1),
  -9px -9px 18px rgba(255, 255, 255, 1);
  border-radius: 3rem;
  transition: box-shadow .2s ease-out;
  background-color: #efeeee;
  position: relative;
  top: 10%;
  margin-top: 10px;
  width: 130px;
  height: 55px;
  outline: none;
  border:none;
}
.button:hover {
  font-size: 19px;
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2),
    -1px -1px 2px rgba(255, 255, 255, 0.8);
  border-radius: 3rem;
  transition: box-shadow 0.2s ease-out;
  transition: font-size 0.2s ease-out;
}
.login_ {
  font-size: 20px;
  margin-top: 15px;
}
.register_ {
  font-size: 20px;
  margin-top: 15px;
}
.card {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  height: 650px;
  width: 400px;

  transition: transform 0.8s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}
.card .front,
.card .back {
  position: absolute;
  text-align: center;
  box-shadow: 12px 12px 24px rgba(0, 0, 0, 0.1),
    -12px -12px 24px rgba(255, 255, 255, 1);
  border-radius: 3rem;
  background-color: #efeeee;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  transition: 1s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}
.front,
.back {
  position: absolute;
  transition: 0.3s linear;
  backface-visibility: hidden;
}
.register {
  top: 5px;
}

.turn {
  text-align: right;
}

.back {
  transform: rotateY(-180deg);
}
.front {
  transform: rotateY(0deg);
}
.return {
  top: 40px;
}
.card .contain-Before {
  transform: rotateY(-180deg);
}
.card .contain-After {
  transform: rotateY(0deg);
}
</style>
