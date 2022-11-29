<script setup>
// import { onBeforeMount, ref } from 'vue';
// import EventCategoryDataService from '../services/EventCategoryDataService';
// import Categoires from '../components/Categories.vue';
// onBeforeMount(async () => {
//   await listCategory();
//   // console.log(categories.value);
// });
// //Fetch
// const categories = ref([]);
// // List All Category
// const listCategory = async () => {
//   const res = await EventCategoryDataService.retrieveAllCategory();
//   categories.value = await res.json();
// };
// const fade = ref(false);
</script>

<template>
  <div class="header">
    <h1 class="w-full text-3xl font-bold text-center mt-12">
      Event Categories
    </h1>
  </div>
  <div class="grid grid-cols-5 gap-1 m-10 pt-16">
    <div class="main">
      <div class="img">
        <img src="./assets/Server-side.png" />
      </div>
      <div
        class="content bg-orange-500 text-center content-center item-center pt-4"
      >
        <h3 class="text-xl font-bold">Server-side Clinic</h3>
        <p class="font-medium mt-2">30 Minutes</p>
        <button><img src="./assets/info.png" class="h-9 w-9 mt-9" /></button>
      </div>
    </div>
    <!-- card 2 -->
    <div class="main">
      <div class="img">
        <img src="./assets/Server-side.png" />
      </div>
      <div
        class="content bg-orange-500 text-center content-center item-center pt-4"
      >
        <h3 class="text-xl font-bold">Server-side Clinic</h3>
        <p class="font-medium mt-2">30 Minutes</p>
        <button><img src="./assets/info.png" class="h-9 w-9 mt-9" /></button>
      </div>
    </div>
    <!-- card 3 -->
    <div class="main">
      <div class="img">
        <img src="./assets/Server-side.png" />
      </div>
      <div
        class="content bg-orange-500 text-center content-center item-center pt-4"
      >
        <h3 class="text-xl font-bold">Server-side Clinic</h3>
        <p class="font-medium mt-2">30 Minutes</p>
        <button><img src="./assets/info.png" class="h-9 w-9 mt-9" /></button>
      </div>
    </div>
    <!-- card 4 -->
    <div class="main">
      <div class="img">
        <img src="./assets/Server-side.png" />
      </div>
      <div
        class="content bg-orange-500 text-center content-center item-center pt-4"
      >
        <h3 class="text-xl font-bold">Server-side Clinic</h3>
        <p class="font-medium mt-2">30 Minutes</p>
        <button><img src="./assets/info.png" class="h-9 w-9 mt-9" /></button>
      </div>
    </div>
    <!-- card 5 -->
    <div class="main">
      <div class="img">
        <img src="./assets/Server-side.png" />
      </div>
      <div
        class="content bg-orange-500 text-center content-center item-center pt-4"
      >
        <h3 class="text-xl font-bold">Server-side Clinic</h3>
        <p class="font-medium mt-2">30 Minutes</p>
        <button><img src="./assets/info.png" class="h-9 w-9 mt-9" /></button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* .container {
  column-count: 3;
  column-gap: 20px;
  column-fill: balance;
  margin: 20px auto 0;
  padding: 2rem;
}
.container .item {
  display: inline-block;
  margin: 0 0 20px;
  -webkit-column-break-inside: avoid;
  page-break-inside: avoid;
  break-inside: avoid;
  width: 100%;
}
.container .item {
  width: 100%;
  height: auto;
} */
.main {
  width: 270px;
  margin: 2rem auto;
  height: 320px;
  box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2);
  border-radius: 20px;
}

.img {
  height: 150px;
  overflow: hidden;
}

.content {
  border-radius: 0 0 20px 20px;
  height: 170px;
}
</style>
