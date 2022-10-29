<script setup>
import { onBeforeMount, ref } from 'vue'
import UserDataService from '../services/UserDataService'
onBeforeMount(async () => {
  // await getAllUser();
  // console.log(users.value);
})

<<<<<<< HEAD
const users = ref()
=======
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
// const getAllUser = async () => {
//   const res = await UserDataService.retrieveAllUser();
//   users.value = await res.json();
// };

<<<<<<< HEAD
const userName = ref('')
const userEmail = ref('')
const userPassword = ref('')
const userRole = ref('')
=======
const userName = ref('');
const userEmail = ref('');
const userPassword = ref('');
const userRole = ref('');
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994

const errorName = ref()
const errorEmail = ref()
const errorPassword = ref()

<<<<<<< HEAD
const submitUser = async () => {
  var newUser = {
    name: userName.value.trim(),
    email: userEmail.value.trim(),
    password: userPassword.value,
    role: userRole.value,
  }
  console.log(newUser)
  const res = await UserDataService.createUser(newUser)
  if (res.status == 400) {
    alert('This name or email are already used.')
  } else {
    reset()
  }
}

const EmailTrim = () => {
  userEmail.value = userEmail.value.trim()
}

const reset = () => {
  userName.value = ''
  userEmail.value = ''
  userRole.value = ''
}

const password = ref()
const confirm_pass = ref()

const checkPassword = () => {
  if (password.value != confirm_pass.value) {
    alert('Password not match.')
  }
}
</script>

<template>
  <div class="bg-grey-lighter mt-20 flex flex-col">
=======
// const checkEmail =

const submitUser = async () => {
  if (password.value == confirm_pass.value) {
    var newUser = {
      name: userName.value.trim(),
      email: userEmail.value.trim(),
      password: password.value,
      role: userRole.value,
    };
    console.log(newUser);
    const res = await UserDataService.createUser(newUser);
    if (res.status == 400) {
      alert('This name or email are already used.');
    } else if (res.status == 403) {
      alert('Access denied');
    } else {
      reset();
    }
  }
};

const reset = () => {
  userName.value = '';
  userEmail.value = '';
  userRole.value = '';
  userPassword.value = '';
  password.value = '';
  confirm_pass.value = '';
  error_message.value = false;
};

const password = ref('');
const confirm_pass = ref('');
const error_message = ref(false);

const EmailTrim = () => {
  userEmail.value = userEmail.value.trim();
};

const checkPassword = () => {
  if (password.value != '' && confirm_pass.value != '') {
    if (password.value != confirm_pass.value) {
      error_message.value = true;
    } else {
      error_message.value = false;
    }
  }
};
</script>

<template>
  <div class="bg-grey-lighter mt-24 flex flex-col">
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
    <form
      class="container max-w-sm mx-auto flex-1 flex flex-col items-center justify-center px-2"
      @submit="submitUser()"
      onsubmit="return false;"
    >
      <div class="bg-white px-5 py-6 rounded shadow-md text-black w-full">
        <h1 class="mb-8 text-3xl text-center">Sign up</h1>
<<<<<<< HEAD
        <label for="nameform">Name :</label>
=======
        <label for="nameform">Name</label>
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
        <input
          required
          id="nameform"
          type="text"
          class="block border border-grey-light w-full p-3 rounded mb-2"
          name="name"
          maxlength="100"
          v-model="userName"
          placeholder="E.g. John Washington"
        />
        <span class="text-red-500 mt-2">{{ errorName }}</span>
<<<<<<< HEAD
        <label for="emailform">Email :</label>
=======
        <label for="emailform">Email</label>
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
        <input
          required
          id="emailform"
          type="text"
          class="block border border-grey-light w-full p-3 rounded mb-2"
          name="email"
          @change="EmailTrim()"
          pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"
          maxlength="50"
          v-model="userEmail"
          placeholder="john@example.com"
        />
        <span class="text-red-500 mt-2">{{ errorEmail }}</span>
<<<<<<< HEAD
        <label for="pwdform">Password : </label>
=======
        <label for="pwdform">Password</label>
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
        <input
          id="pwdform"
          type="password"
          class="block border border-grey-light w-full p-3 rounded mb-2"
          name="password"
<<<<<<< HEAD
          pattern=".{8,14}"
          placeholder="password"
          v-model="password"
        />
        <label for="checkpwd">Confirm Password :</label>
=======
          pattern=".{8,15}"
          placeholder="password"
          v-model="password"
          @change="checkPassword()"
        />
        <label for="checkpwd">Confirm Password</label>
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
        <input
          id="checkpwd"
          type="password"
          class="block border border-grey-light w-full p-3 rounded mb-2"
          name="confirm_password"
          placeholder="Confirm Password"
<<<<<<< HEAD
          pattern=".{8,14}"
          v-model="confirm_pass"
          @change="checkPassword()"          
        />
=======
          pattern=".{8,15}"
          v-model="confirm_pass"
          @change="checkPassword()"
        />

        <span v-show="error_message" class="text-red-600"
          >The password DOES NOT match</span
        >
        <span
          v-show="
            (password.length < 8 || password.length > 14) && password != ''
          "
          class="text-red-600"
          >between 8 - 14 charaters</span
        >

>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
        <div class="mb-6">
          <label
            for="category"
            class="block text-gray-700 text-lg font-bold mb-2"
            >Role :
          </label>
          <select
            name="category"
            id="category"
            v-model="userRole"
            class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-orange-600"
          >
            <option disabled selected value>Select Role</option>
            <option>student</option>
            <option>admin</option>
            <option>lecturer</option>
          </select>
        </div>
        <button
          type="submit"
          class="w-full text-center py-3 rounded bg-green text-black hover:bg-green-dark focus:outline-none my-1"
        >
          Create Account
        </button>
        <router-link :to="{ name: 'ListUsers' }">
          <button
            class="w-full text-center py-3 rounded bg-green text-black hover:bg-green-dark focus:outline-none my-1"
            @click=""
          >
            Cancel
          </button>
        </router-link>
      </div>
<<<<<<< HEAD

      <div class="text-grey-dark mt-6">
        Already have an account?        
        <router-link
            class="no-underline border-b border-blue text-black hover:text-white hover:border-black transition ease-in delay-15" 
            :to="{ path: '/signIn', name: 'SignIn' }"
            > Log in </router-link
          >.
      </div>
=======
>>>>>>> f400c66d970e1d2ec1e78dcd20d97e2f22aa0994
    </form>
  </div>
</template>

<style></style>
