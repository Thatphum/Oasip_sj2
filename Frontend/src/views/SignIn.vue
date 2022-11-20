<script setup>
import { ref } from 'vue';
import LoginService from '../services/AuthenticationService';
const email = ref('');
const password = ref('');

// const submitLogin = async () => {
//   var requiredUser = {
//     email: email.value.trim(),
//     password: password.value,
//   };
//   console.log(requiredUser);
//   const res = await LoginService.logInUser(requiredUser);
//   if (res.status == 400) {
//     alert('A user with the specified email DOES NOT exist');
//   } else if (res.status == 401) {
//     alert('Password NOT Matched');
//   } else {
//     alert('Password Matched');
//   }
// };

const EmailTrim = () => {
  email.value = email.value.trim();
};
</script>

<template>
  <section class="grid place-items-center h-screen bg-slate-100">
    <div
      class="grid grid-cols-1 md:grid-cols-2 max-h-[65%] max-w-[75%] md:max-w-[80%] lg:max-w-[50%] rounded-lg bg-white drop-shadow-2xl divide-x"
    >
      <div class="rounded-l-lg p-10">
        <img src="../assets/bgCreate.png" alt="" />
      </div>
      <div class="flex flex-col justify-center items-center">
        <h1 class="text-center text-4xl pt-7">Sign In</h1>
        <form
          class="mx-auto flex-1 flex flex-col items-center justify-center px-2"
          @submit="submitLogin()"
          onsubmit="return false"
        >
          <div class="text-black w-full">
            <h3 class="text-xl">Email</h3>
            <input
              type="text"
              class="block border border-grey-light w-full p-3 rounded mb-4"
              name="email"
              placeholder="Email"
              @change="EmailTrim()"
              pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"
              v-model="email"
            />
            <h3 class="text-xl">Password</h3>
            <input
              type="password"
              class="block border border-grey-light w-full p-3 rounded mb-4"
              name="password"
              placeholder="Password"
              v-model="password"
            />
            <div class="flex">
              <button
                type="submit"
                class="w-20 text-center py-3 rounded bg-blue-700 text-white hover:bg-blue-800 focus:outline-none my-1 border-solid-black"
              >
                Sign In
              </button>
              <router-link :to="{ path: '/', name: 'Home' }"
                ><button
                  type="cancel"
                  class="w-20 text-center py-3 rounded bg-white text-black hover:bg-slate-300 focus:outline-none my-1 ml-5"
                  @click="cancel()"
                >
                  Cancel
                </button></router-link
              >
            </div>
            <div class="text-grey-dark mt-6">
              Don't have an account?
              <router-link
                class="underline text-blue"
                :to="{ path: '/signUp', name: 'SignUp' }"
              >
                Create your account?
              </router-link>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style></style>
