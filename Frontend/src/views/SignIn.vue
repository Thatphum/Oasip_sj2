<script setup>
<<<<<<< HEAD

=======
import { ref } from 'vue';
import router from '../router';
import LoginService from '../services/AuthenticationService';
const email = ref('');
const password = ref('');

const submitLogin = async () => {
  var requiredUser = {
    email: email.value.trim(),
    password: password.value,
  };
  console.log(requiredUser);
  const res = await LoginService.logInUser(requiredUser);
  var jwt = await res.json();
  console.log(jwt);
  if (res.status == 404) {
    alert('A user with the specified email DOES NOT exist');
  } else if (res.status == 401) {
    alert('Password NOT Matched');
  } else {
    localStorage.setItem('my_tkn', jwt.refreshToken);
    console.log();
    alert('Password Matched');
    router.go(-1);
    // window.location.reload();
  }
};

const EmailTrim = () => {
  email.value = email.value.trim();
};
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
</script>

<template>
  <div class="bg-grey-lighter min-h-screen flex flex-col">
<<<<<<< HEAD
    <div
      class="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2"
=======
    <form
      class="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2"
      @submit="submitLogin()"
      onsubmit="return false;"
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
    >
      <div class="bg-white px-6 py-8 rounded shadow-md text-black w-full">
        <h1 class="mb-8 text-3xl text-center">Sign In</h1>
        <input
          type="text"
          class="block border border-grey-light w-full p-3 rounded mb-4"
          name="email"
          placeholder="Email"
<<<<<<< HEAD
=======
          @change="EmailTrim()"
          pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"
          v-model="email"
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
        />

        <input
          type="password"
          class="block border border-grey-light w-full p-3 rounded mb-4"
          name="password"
          placeholder="Password"
<<<<<<< HEAD
=======
          v-model="password"
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
        />

        <button
          type="submit"
          class="w-full text-center py-3 rounded bg-green text-black hover:bg-green-dark focus:outline-none my-1"
        >
          Log In
        </button>
      </div>

      <div class="text-grey-dark mt-6">
        Don't have an account?
        <router-link
<<<<<<< HEAD
            class="no-underline border-b border-blue text-blue"
            :to="{ path: '/signUp', name: 'SignUp' }"
            > Create yours now. </router-link
          >.
      </div>
    </div>
=======
          class="no-underline border-b border-blue text-blue"
          :to="{ path: '/signUp', name: 'SignUp' }"
        >
          Create yours now. </router-link
        >.
      </div>
    </form>
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
  </div>
</template>

<style></style>
