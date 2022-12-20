<template>
  <footer>
    <div
      class="justify-center items-center bg-blue-600 text-white"
      id="contact"
    >
      <div
        class="flex flex-col md:flex-row justify-center md:justify-between py-4 md:px-20"
      >
        <div class="text-2xl text-center">Jaturalnw</div>
        <ul
          class="flex justify-center gap-2 items-center mt-2 md:mt-0 text-center md:text-white text-gray-300"
        >
          <!-- <li>Home</li>
          <li class="md:hidden block">|</li>
          <li>Events</li>
          <li class="md:hidden block">|</li>
          <li>Categories</li>
          <li class="md:hidden block">|</li>
          <li>Users</li>
          <li class="md:hidden block">|</li> -->

          <li>
            <router-link :to="{ name: 'Home' }"> Home </router-link>
          </li>
          <li>
            <router-link :to="{ name: 'About' }"> Contact Us </router-link>
          </li>
        </ul>
      </div>
      <div class="flex justify-center p-2 border-t text-center break-word">
        © 2021 Copyright: Xiao Jaturalnw
      </div>
    </div>
  </footer>
</template>

<style></style>
