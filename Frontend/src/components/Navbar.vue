<script>
import { store } from '../stores/User';

export default {
  name: 'Navbar',
  data() {
    return {
      slideBar: false,
      username: '',
    };
  },
  mounted() {
    var user = JSON.parse(localStorage.getItem('user'));
    if (user != null) {
      this.username = user.username;
    }
  },
  methods: {
    sideBar() {
      this.slideBar = !this.slideBar;
    },
    closeSidebar() {
      this.slideBar = false;
    },
    chaeckuser() {
      return store.isLogin();
    },
    logoutUrl() {
      store.logout();
      location.reload();
    },
  },
};
</script>

<template>
  <nav class="w-full border-b-2 text-md">
    <div
      class="hidden md:flex items-center bg-black justify-end text-white py-2"
      v-if="!chaeckuser()"
    >
      <ul class="flex gap-6 mx-10 text-sm">
        <li>
          <p>Jaturalnw</p>
        </li>
        <li>
          <p>|</p>
        </li>
        <li>
          <a href="">Join with guest</a>
        </li>
        <li>
          <router-link :to="{ path: '/signin', name: 'SignIn' }">
            Sign In
          </router-link>
        </li>
      </ul>
    </div>
    <div class="flex justify-between items-center py-2 px-6 md:px-10 bg-white">
      <div class="flex gap-4">
        <a href="#">
          <img src="../assets/xiao-white.png" class="w-12 h-auto" alt="" />
        </a>
        <div class="hidden md:flex md:items-center md:w-auto" id="menu">
          <ul class="flex pt-4 md:pt-0">
            <li>
              <router-link
                class="md:p-4 py-2 block hover:text-blue-600"
                :to="{ name: 'Home' }"
              >
                Home
              </router-link>
            </li>
            <li>
              <router-link
                class="md:p-4 py-2 block hover:text-blue-600"
                :to="{ name: 'ListEvent' }"
                >Events
              </router-link>
            </li>
            <li>
              <router-link
                class="md:p-4 py-2 block hover:text-blue-600"
                :to="{ name: 'ListCategory' }"
              >
                Categoires
              </router-link>
            </li>
            <li>
              <router-link
                class="md:p-4 py-2 block hover:text-blue-600"
                :to="{ name: 'ListUsers' }"
              >
                Users
              </router-link>
            </li>
            <li>
              <router-link
                class="md:p-4 py-2 block hover:text-blue-600"
                :to="{ name: 'About' }"
              >
                Contact Us
              </router-link>
            </li>
          </ul>
        </div>
      </div>
      <div class="flex md:hidden text-lg">JATURALNW</div>
      <div class="flex items-center gap-4 text-sm">
        <p class="py-2 px-6 rounded-full hidden lg:flex" v-if="chaeckuser()">
          {{ username }}
        </p>
        <router-link
          v-if="!chaeckuser()"
          class="bg-blue-600 text-white py-2 px-6 rounded-full hidden md:flex hover:scale-105 duration-300"
          :to="{ name: 'SignUp' }"
        >
          Sign Up
        </router-link>
        <button
          v-else
          class="bg-blue-600 text-white py-2 px-6 rounded-full hidden md:flex hover:scale-105 duration-300"
          @click="logoutUrl()"
        >
          Sign Out
        </button>

        <button class="rounded-full flex lg:hidden">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            id="menu-button"
            class="w-6 h-auto cursor-pointer lg:hidden block"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            @click="sideBar()"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M4 6h16M4 12h16M4 18h16"
            />
          </svg>
        </button>
      </div>
    </div>
    <aside
      class="transform text-black top-0 left-0 w-3/5 bg-white fixed h-screen overflow-auto ease-in-out transition-all duration-300 z-50 translate-x-0"
      :class="{
        '-translate-x-full': !slideBar,
      }"
    >
      <div class="pt-10 px-5 text-md">
        <div class="flex flex-col divide-y-2">
          <a href="#" class="py-3" @click="closeSidebar()"> Home </a>
          <a href="#aboutme" class="py-3" @click="closeSidebar()"> About Me </a>
          <a href="#myskill" class="py-3" @click="closeSidebar()"> Skill </a>
          <a href="#experience" class="py-3" @click="closeSidebar()">
            Experience
          </a>
          <a href="#certificate" class="py-3" @click="closeSidebar()">
            Certificate
          </a>
          <a href="#contact" class="py-3" @click="closeSidebar()"> Contact </a>
        </div>
        <div class="flex justify-center pt-14" @click="closeSidebar()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="40"
            height="40"
            fill="currentColor"
            class="bi bi-x-circle"
            viewBox="0 0 16 16"
          >
            <path
              d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"
            />
            <path
              d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"
            />
          </svg>
        </div>
      </div>
    </aside>
    <aside
      class="top-0 right-0 w-full fixed h-screen bg-zinc-100/30 transition ease-in opacity-100 duration-700 z-20"
      :class="{ hidden: !slideBar }"
      @click="closeSidebar()"
    ></aside>
  </nav>
</template>

<style scoped></style>
