<script setup></script>

<template>
  <section class="min-h-screen">
    <div class="grid place-items-center h-screen min-w-full">
      <div class="w-full flex flex-col items-center">
        <div class="four_zero_four_bg w-full">
          <h1 class="text-center">404</h1>
        </div>
        <div class="contant_box_404 flex flex-col items-center">
          <h3 class="h2">Look like you're lost</h3>

          <p>the page you are looking for not avaible!</p>
          <router-link
            class="link_404 rounded-lg bg-blue-600"
            :to="{ name: 'Home' }"
          >
            Go to Home
          </router-link>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.page_404 {
  padding: 40px 0;
  background: #fff;
  font-family: 'Arvo', serif;
  width: 100%;
}

.page_404 img {
  width: 100%;
}

.four_zero_four_bg {
  background-image: url(https://cdn.dribbble.com/users/285475/screenshots/2083086/dribbble_1.gif);
  height: 400px;
  width: 70%;
  background-position: center;
}

.four_zero_four_bg h1 {
  font-size: 80px;
}

.four_zero_four_bg h3 {
  font-size: 80px;
}

.link_404 {
  color: #fff !important;
  padding: 10px 20px;
  margin: 20px 0;
  display: inline-block;
}
.contant_box_404 {
  margin-top: -50px;
}
</style>
