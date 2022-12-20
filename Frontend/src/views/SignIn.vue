<script>
import LoginService from '../services/AuthenticationService';
import { store } from '../stores/User.js';
export default {
  name: 'SignIn',
  data() {
    return {
      requiredUser: {
        email: '',
        password: '',
      },
      animationField: {
        email: false,
        password: false,
      },
      errorMessage: false,
      showPassword: true,
    };
  },
  methods: {
    goToSignup() {
      this.$router.push({ name: 'SignUp' });
    },
    inputAnimation(field) {
      this.requiredUser[field] == ''
        ? (this.animationField[field] = false)
        : (this.animationField[field] = true);
    },
    toggleShowPassword() {
      this.showPassword = !this.showPassword;
    },
    print() {
      // this.$swal('Hello Vue world!!!');
      var i = 'email';
      console.log(this.requiredUser[i]);
      console.log(this.requiredUser);
    },
    async PostData() {
      const res = await LoginService.logInUser(this.requiredUser);
      if (res.status == 200) {
        var data = await res.json();
        console.log(data);
        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('accessTokenExp', new Date(data.accessTokenExp));
        localStorage.setItem('refreshToken', data.refreshToken);
        localStorage.setItem('user', JSON.stringify(data.user));
        // store.setDataUser(data.user);
        // console.log(store.user);
        this.$swal
          .fire({
            title: 'Good job!',
            text: 'You clicked the button!',
            icon: 'success',
          })
          .then(function () {
            location.reload();
          });

        this.$router.push({ path: '/', name: 'Home' });
      } else if (res.status == 500) {
        this.$swal.fire(
          'The Internet?',
          'That thing is still around?',
          'question'
        );
      } else {
        this.errorMessage = true;
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Incorrect email or password.',
        });
      }
    },
    resetErrorMessage() {
      if (this.errorMessage) this.errorMessage = false;
    },
  },
};
</script>

<template>
  <section class="grid place-items-center h-screen bg-gray-100">
    <div
      class="grid grid-cols-1 lg:grid-cols-2 max-w-5xl rounded-lg bg-white drop-shadow-2xl divide-x"
    >
      <div class="rounded-l-lg p-10 hidden lg:block">
        <img src="../assets/bgCreate.png" alt="" />
      </div>
      <div
        class="flex flex-col justify-center items-center h-screen md:h-full w-screen md:w-full"
      >
        <!-- form Sign in -->
        <div class="px-12 m-4 my-8">
          <h1 class="font-bold text-3xl text-blue-700">Sign In</h1>
          <!-- email -->
          <form
            action=""
            class="flex flex-col mt-8"
            @submit="PostData()"
            onsubmit="return false;"
          >
            <label class="mb-4">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.email }"
              >
                Email
              </p>
              <input
                type="email"
                class="input p-2 border-2 rounded-xl w-full"
                maxlength="50"
                pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"
                v-model.trim="requiredUser.email"
                @keydown="resetErrorMessage()"
                @focusin="animationField.email = true"
                @focusout="inputAnimation('email')"
                required
              />
            </label>
            <!-- password -->
            <label class="mb-2">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.password }"
              >
                Password
              </p>
              <input
                id="password"
                :type="showPassword ? 'password' : 'text'"
                class="input p-2 border-2 rounded-xl w-full"
                v-model.trim="requiredUser.password"
                @keydown="resetErrorMessage()"
                @focusin="animationField.password = true"
                @focusout="inputAnimation('password')"
                required
              />
              <img
                v-if="!showPassword"
                src="../assets/svg/eye.svg"
                alt="eye"
                class="absolute top-1/2 right-3 -translate-y-1/2"
                @click="toggleShowPassword()"
              />
              <img
                v-else
                src="../assets/svg/eye-slash.svg"
                alt="eye-slash"
                class="absolute top-1/2 right-3 -translate-y-1/2"
                @click="toggleShowPassword()"
              />
            </label>
            <!-- <div class="flex gap-4 ml-3">
              <input type="checkbox" value="lsRememberMe" id="rememberMe" />
              <label
                for="rememberMe"
                class="text-md min-w-full font-sans font-medium"
                >Remember me</label
              >
            </div> -->
            <div class="text-red-500 text-sm flex justify-center">
              &nbsp;
              <p v-show="errorMessage">Incorrect email or password.</p>
            </div>
            <button
              class="bg-blue-700 text-md rounded-xl text-white py-2 px-[120px] hover:scale-105 duration-300 mt-1 formInput"
              type="submit"
            >
              Login
            </button>
          </form>

          <!-- <div class="mt-6 grid grid-cols-3 items-center text-gray-400">
            <hr class="border-gray-400" />
            <p class="text-center text-sm">OR</p>
            <hr class="border-gray-400" />
          </div>

          <button
            class="bg-white border py-2 px-4 w-full rounded-xl mt-5 flex justify-center items-center md:text-md hover:scale-105 duration-300 text-blue-700"
          >
            <img
              src="../assets/microsoft-teams.svg"
              alt="microsoft teams"
              width="25px"
              class="mr-2"
            />
            Login with Microsoft Team
          </button> -->
          
          <div
            class="mt-4 text-sm flex justify-between items-center text-blue-700"
          >
            <p>Don't have an account?</p>
            <button class="underline text-blue" @click="goToSignup()">
              Sign Up
            </button>
          </div>
        </div>
        <!-- End form Sign in -->
      </div>
    </div>
  </section>
</template>

<style scoped>
label {
  display: block;
  position: relative;
}

input {
  width: 100%;
  padding: 10px;
  padding-left: 18px;
  background: transparent;
  border: none;
  outline: none;
  border: 1px solid #ccc;
  border-radius: 15px;
}

input:focus + .line-box .line {
  width: 100%;
}

.label-txt {
  position: absolute;
  top: 0.6em;
  left: 20px;
  padding: 1px;
  letter-spacing: 1px;
  color: rgb(120, 120, 120);
  transition: ease 0.3s;
}

.label-active {
  background: white;
  top: -0.8em;
  font-size: 0.8em;
}
</style>
