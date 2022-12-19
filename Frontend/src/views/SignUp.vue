<script>
import UserDataService from '../services/UserDataService';
import { store } from '../stores/User.js';
export default {
  name: 'SignUp',
  data() {
    return {
      requiredUser: {
        name: '',
        email: '',
        password: '',
        role: '',
      },
      confirmPassword: '',
      showPassword: false,
      showConfirmPassword: false,
      animationField: {
        name: false,
        email: false,
        password: false,
        confirm: false,
      },
    };
  },
  methods: {
    goToSignIn() {
      this.$router.push({ path: '/signin', name: 'SignIn' });
    },
    inputAnimation(field) {
      if (field != 'confirm') {
        this.requiredUser[field] == ''
          ? (this.animationField[field] = false)
          : (this.animationField[field] = true);
      } else {
        this.confirmPassword == ''
          ? (this.animationField['confirm'] = false)
          : (this.animationField['confirm'] = true);
      }
    },
    resetValue() {
      Object.keys(this.requiredUser).forEach((key) => {
        this.requiredUser[key] = '';
      });
      console.log(this.requiredUser);
    },
    print() {
      console.log(this.requiredUser);
    },
    async submitForm() {
      if (this.requiredUser.password == this.confirmPassword) {
        const res = await UserDataService.createUser(this.requiredUser);
        if (res.status == 200) {
          var data = await res.json();
          console.log(data);
          // localStorage.setItem('accessToken', data.accessToken);
          // localStorage.setItem('accessTokenExp', data.accessTokenExp);
          // localStorage.setItem('refreshToken', data.refreshToken);
          // localStorage.setItem('user', JSON.stringify(data.user));
          // store.setDataUser(data.user);
          // console.log(store.user);
          this.$swal.fire({
            title: 'Good job!',
            text: 'You clicked the button!',
            icon: 'success',
          });
          // .then(function () {
          //   location.reload();
          // });

          // this.$router.push({ path: '/', name: 'Home' });
        } else if (res.status == 500) {
          this.$swal.fire(
            'The Internet?',
            'That thing is still around?',
            'question'
          );
        } else {
          this.$swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'This name or email are already used.',
          });
        }
      } else {
        // text message error
      }
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
        <!-- form Sign up -->
        <div class="px-2 m-4 my-8">
          <h1 class="font-bold text-3xl text-blue-700">Sign Up</h1>
          <form
            action=""
            class="flex flex-col mt-6"
            @submit="submitForm()"
            onsubmit="return false;"
          >
            <label class="mb-3">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.name }"
              >
                Name
              </p>
              <input
                type="text"
                class="input p-2 border-2 rounded-xl w-full"
                maxlength="100"
                v-model.trim="requiredUser.name"
                @focusin="animationField.name = true"
                @focusout="inputAnimation('name')"
                required
              />
            </label>
            <label class="mb-3">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.email }"
              >
                Email
              </p>
              <input
                type="text"
                class="input p-2 border-2 rounded-xl w-full"
                maxlength="50"
                pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"
                v-model.trim="requiredUser.email"
                @focusin="animationField.email = true"
                @focusout="inputAnimation('email')"
                required
              />
            </label>

            <label class="mb-3">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.password }"
              >
                Password
              </p>
              <input
                id="password"
                :type="this.showPassword ? 'text' : 'password'"
                pattern=".{8,15}"
                class="input p-2 border-2 rounded-xl w-full"
                v-model.trim="requiredUser.password"
                @focusin="animationField.password = true"
                @focusout="inputAnimation('password')"
                required
              />
              <img
                src="../assets/svg/eye.svg"
                alt="eye"
                class="absolute top-1/2 right-3 -translate-y-1/2"
                v-if="!showPassword"
                @click="showPassword = !showPassword"
              />
              <img
                v-else
                src="../assets/svg/eye-slash.svg"
                alt="eye-slash"
                class="absolute top-1/2 right-3 -translate-y-1/2"
                @click="showPassword = !showPassword"
              />
            </label>
            <label class="mb-3">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.confirm }"
              >
                Confirm Password
              </p>
              <input
                id="confirmPassword"
                :type="showConfirmPassword ? 'text' : 'password'"
                pattern=".{8,15}"
                class="input p-2 border-2 rounded-xl w-full"
                v-model.trim="confirmPassword"
                @focusin="animationField.confirm = true"
                @focusout="inputAnimation('confirm')"
                required
              />
              <img
                src="../assets/svg/eye.svg"
                alt="eye"
                class="absolute top-1/2 right-3 -translate-y-1/2"
                v-if="!showConfirmPassword"
                @click="showConfirmPassword = !showConfirmPassword"
              />
              <img
                v-else
                src="../assets/svg/eye-slash.svg"
                alt="eye-slash"
                class="absolute top-1/2 right-3 -translate-y-1/2"
                @click="showConfirmPassword = !showConfirmPassword"
              />
            </label>
            <div>
              <label for="">
                <p class="label-txt label-active z-10">Role</p>
                <div class="select">
                  <select name="role" id="role" v-model="requiredUser.role">
                    <option disabled selected value>Select Role</option>
                    <option>admin</option>
                    <option>lecturer</option>
                    <option>student</option>
                  </select>
                </div>
              </label>
            </div>
            <div class="text-red-500 text-sm flex justify-center">
              &nbsp;
              <!-- <p v-show="errorMessage">
                Password and confirm password does not match
              </p> -->
            </div>
            <button
              class="bg-blue-700 text-md rounded-xl text-white py-2 hover:scale-105 duration-300 mt-2 formInput px-28"
            >
              Sign up
            </button>
          </form>
          <hr class="border-gray-400 mt-6" />
          <div
            class="mt-6 text-sm flex justify-between items-center text-blue-700"
          >
            <p>Have an account?</p>
            <button class="underline text-blue" @click="goToSignIn()">
              Log in
            </button>
          </div>
        </div>
        <!-- End form Sign up -->
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

select {
  -webkit-appearance: none;
  -moz-appearance: none;
  -ms-appearance: none;
  appearance: none;
  outline: 0;
  box-shadow: none;
  border: 1 !important;
  background: #fff;
  background-image: none;
  flex: 1;
  padding: 0 1.5em;
  color: rgb(120, 120, 120);
  cursor: pointer;
  font-size: 1em;
}
select::-ms-expand {
  display: none;
}
.select {
  position: relative;
  display: flex;
  width: 20em;
  height: 3em;
  line-height: 3;
  overflow: hidden;
  border-radius: 0.25em;
  border: 1px solid #ccc;
  border-radius: 15px;
}
.select::after {
  content: '\25BC';
  font-size: 12px;
  position: absolute;
  top: 6px;
  right: 4px;
  padding: 0 1em;
  color: rgb(120, 120, 120);
  cursor: pointer;
  pointer-events: none;
  transition: 0.25s all ease;
}
.select:hover::after {
  color: #23b499;
}
</style>
