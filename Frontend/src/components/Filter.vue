<script>
import { onBeforeMount, ref } from 'vue';
import EventCategoryDataService from '../services/EventCategoryDataService';
export default {
  data() {
    return {
      categories: null,
      listOption: {
        eventCategoryId: 0,
        option: 0,
        dateTime: '',
      },
      date: new Date(),
    };
  },
  async beforeMount() {
    await this.listCategory();
  },
  methods: {
    async listCategory() {
      const res = await EventCategoryDataService.retrieveAllCategoryForFilter();
      this.categories = await res.json();
    },
    changeOption() {
      if (this.listOption.option == 3 && this.listOption.dateTime == '') {
        return false;
      } else if (parseInt(this.listOption.option) != 3) {
        this.listOption.dateTime = '';
        return this.listOption;
      } else {
        return this.listOption;
      }
    },
    changeDate(number) {
      if (this.listOption.dateTime == '' && number == 0) {
        var dd = String(this.date.getDate()).padStart(2, '0');
        var mm = String(this.date.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = this.date.getFullYear();
        var time = this.date.toLocaleTimeString('th-TH', { hour12: false });
        var today = yyyy + '-' + mm + '-' + dd + 'T' + time;
        this.listOption.dateTime = today;
      } else {
        var dd = String(this.date.getDate()).padStart(2, '0');
        var mm = String(this.date.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = this.date.getFullYear();
        var time = this.date.toLocaleTimeString('th-TH', { hour12: false });
        var today = yyyy + '-' + mm + '-' + dd + 'T' + time;
        this.listOption.dateTime = today;
      }
      this.$emit('filter', this.changeOption());
    },
  },
};
</script>

<template>
  <section>
    <div
      class="flex flex-col justify-center items-center gap-4 p-6 bg-white rounded-lg transition-all duration-500 ease h-min hover:h-max"
    >
      <div
        class="text-xl focus:outline-none focus:border-sky-500 focus:ring-sky-500"
      >
        Filter
      </div>
      <!-- <button @click="print()">dsmalkdkakjdjldlakjmdl</button>
      <div class="pt-2 relative mx-auto text-gray-600">
        <input
          class="border-2 border-gray-300 bg-white h-10 px-5 pr-16 rounded-lg text-sm focus:outline-none focus:border-sky-500 focus:ring-sky-500"
          type="search"
          name="search"
          placeholder="Search"
        />
        <button type="submit" class="absolute right-0 top-0 mt-5 mr-4">
          <svg
            class="text-gray-600 h-4 w-4 fill-current"
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            version="1.1"
            id="Capa_1"
            x="0px"
            y="0px"
            viewBox="0 0 56.966 56.966"
            style="enable-background: new 0 0 56.966 56.966"
            xml:space="preserve"
            width="512px"
            height="512px"
          >
            <path
              d="M55.146,51.887L41.588,37.786c3.486-4.144,5.396-9.358,5.396-14.786c0-12.682-10.318-23-23-23s-23,10.318-23,23  s10.318,23,23,23c4.761,0,9.298-1.436,13.177-4.162l13.661,14.208c0.571,0.593,1.339,0.92,2.162,0.92  c0.779,0,1.518-0.297,2.079-0.837C56.255,54.982,56.293,53.08,55.146,51.887z M23.984,6c9.374,0,17,7.626,17,17s-7.626,17-17,17  s-17-7.626-17-17S14.61,6,23.984,6z"
            />
          </svg>
        </button>
      </div> -->
      <div class="relative mx-auto w-full text-gray-600">
        <select
          name="category"
          id="category"
          @change="$emit('filter', changeOption())"
          v-model="listOption.eventCategoryId"
          class="border-2 border-gray-300 bg-white h-10 w-full px-4 rounded-lg text-sm focus:outline-none focus:border-sky-500 focus:ring-sky-500"
        >
          <option selected="selected" :value="0">All Category</option>
          <option v-for="(category, index) in categories" :value="category.id">
            {{ category.eventCategoryName }}
          </option>
        </select>
      </div>
      <div class="relative mx-auto w-full text-gray-600">
        <select
          name="category"
          id="category"
          @change="$emit('filter', changeOption())"
          class="border-2 border-gray-300 bg-white h-10 w-full px-4 rounded-lg text-sm focus:outline-none focus:border-sky-500 focus:ring-sky-500"
          placeholder="Category"
          v-model="listOption.option"
        >
          <option :value="0" selected>None</option>
          <option :value="1">Upcoming Events</option>
          <option :value="2">Past Events</option>
          <option :value="3">Select Day Events</option>
        </select>
      </div>

      <div v-if="listOption.option == 3" class="hidden md:block">
        <DatePicker
          v-model="date"
          color="blue"
          title-position="left"
          is-expanded
          :change="changeDate(1)"
        />
      </div>

      <input
        v-if="listOption.option == 3"
        type="datetime-local"
        id="eventTime"
        name="eventTime"
        class="block md:hidden border-2 border-gray-300 bg-white h-10 w-full px-4 rounded-lg text-sm focus:outline-none focus:border-sky-500 focus:ring-sky-500"
        v-model="listOption.dateTime"
        :change="changeDate(0)"
      />

      <router-link
        class="text-center text-white border-2 bg-blue-600 border-gray-300 py-2 w-full px-4 rounded-lg text-base focus:outline-none focus:border-sky-500 focus:ring-sky-500"
        :to="{ name: 'CreateEvent' }"
      >
        Add Event
      </router-link>
    </div>
  </section>
  <!-- <div class="w-full flex justify-center">
    <div class="max-w-4xl px-4 py-4 bg-white rounded-2xl">
      <div class="flex">
        <div>
          <label
            for="category"
            class="block text-gray-700 text-lg font-bold mb-2"
            >Event Category :
          </label>
          <select
            required
            name="category"
            id="category"
            @change="$emit('filter', changeOption())"
            v-model="selectCategory"
            class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-orange-600"
          >
            <option selected="selected" value="0">All Category</option>
            <option
              v-for="(category, index) in categories"
              :value="category.id"
            >
              {{ category.eventCategoryName }}
            </option>
          </select>
        </div>
        <div class="ml-4">
          <label
            for="category"
            class="block text-gray-700 text-lg font-bold mb-2"
            >Event Option :
          </label>
          <select
            required
            name="category"
            id="category"
            @change="$emit('filter', changeOption())"
            v-model="listOption.option"
            class="shadow border rounded w-full py-2 px-3 w- text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-orange-600"
          >
            <option selected="selected" value="0">None</option>
            <option :value="1">Upcoming Events</option>
            <option :value="2">Past Events</option>
            <option :value="3">Select Day Events</option>
          </select>
        </div>
        <div class="ml-4">
          <label
            for="category"
            class="block text-gray-700 text-lg font-bold mb-2"
            >Select Day Event :
          </label>
          <input
            @change="$emit('filter', changeOption())"
            type="date"
            id="eventDate"
            v-model="dateTime"
            class="shadow border rounded w-full py-2.5 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-orange-600 disabled:opacity-75 disabled:bg-current/80 disabled:cursor-not-allowed"
            :disabled="listOption.option != 3"
          />
        </div>
        <div>
          <button
            class="flex items-center rounded-full text-md bg-red-500 px-2 py-2 text-white mt-9 ml-4"
            @click="$emit('filter', reset())"
          >
            Reset
          </button>
        </div>
      </div>
    </div>
  </div> -->
</template>

<style scoped>
input:checked + label {
  border-color: black;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1),
    0 4px 6px -2px rgba(0, 0, 0, 0.05);
}
</style>
