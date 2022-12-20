<script>
import EventCategoryDataService from '../services/EventCategoryDataService';
import Categoires from '../components/Categories.vue';

export default {
  name: 'Category-List',
  components: {
    Categoires,
  },
  data() {
    return {
      categories: null,
    };
  },
  async beforeMount() {
    await this.listCategory();
    // console.log(this.categories);
  },
  methods: {
    async listCategory() {
      const res = await EventCategoryDataService.retrieveAllCategory();
      this.categories = await res.json();
    },
  },
};
</script>

<template>
  <section class="min-h-screen bg-gray-100">
    <div class="w-full flex justify-center transition ease-in-out duration-700">
      <div class="p-20 md:p-24 max-w-[1200px]">
        <!-- card container -->
        <h1 class="text-center py-4 text-2xl">Categoires</h1>
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
