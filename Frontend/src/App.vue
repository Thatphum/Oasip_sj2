<template>
  <div class="bg-gray-200 flex">
    <div class="flex-grow bg-white">
      <div class="fixed w-full top-0 z-50">
        <Navbar />
      </div>
      <!-- content -->
      <main class="w-full">
        <router-view></router-view>
      </main>
    </div>
  </div>
  <Footer />
</template>

<script>
import Navbar from './components/Navbar.vue';
import Footer from './components/Footer.vue';
export default {
  name: 'App',
  components: {
    Navbar,
    Footer,
  },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Lato:wght@700&display=swap');
body {
  font-family: 'Lato', sans-serif;
}
</style>
