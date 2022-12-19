<script setup>
import { onBeforeMount, ref } from 'vue';
import EventCategoryDataService from '../services/EventCategoryDataService';
import Categoires from '../components/Categories.vue';
onBeforeMount(async () => {
  await listCategory();
  // console.log(categories.value);
});
//Fetch
const categories = ref([]);
// List All Category
const listCategory = async () => {
  const res = await EventCategoryDataService.retrieveAllCategory();
  categories.value = await res.json();
};
const fade = ref(false);
</script>

<template>
  <section class="min-h-screen bg-gray-100">
    <div class="w-full flex justify-center transition ease-in-out duration-700">
      <div class="p-10 md:p-24 max-w-[1200px]">
        <!-- card container -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div v-for="category in categories" v-if="categories">
            <Categoires :category="category" />
          </div>
          <div v-else>No Scheduled Events</div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped></style>
