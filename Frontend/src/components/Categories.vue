<script>
import a from '../assets/categories/1.png';
import b from '../assets/categories/2.png';
import c from '../assets/categories/3.png';
import d from '../assets/categories/4.png';
import e from '../assets/categories/5.png';
export default {
  data() {
    return {
      bgCategory: [
        'bg-red-500',
        'bg-blue-500',
        'bg-yellow-500',
        'bg-green-500',
        'bg-orange-500',
      ],
      imgBg: [this.imageA, this.imageB, this.imageC, this.imageD, this.imageE],
      imageA: a,
      imageB: b,
      imageC: c,
      imageD: d,
      imageE: e,
    };
  },
  props: {
    category: {
      type: Object,
      requird: true,
    },
  },
  methods: {
    getBorder(id) {
      return this.borderCategory[id - 1];
    },
    getBgColor(id) {
      return this.bgCategory[id - 1];
    },
    categoryDetatil() {
      this.$router.push({
        name: 'CategoryDetail',
        params: { id: this.category.id },
      });
    },
    getImage() {
      if (this.category.id == 1) {
        return this.imageA;
      } else if (this.category.id == 2) {
        return this.imageB;
      } else if (this.category.id == 3) {
        return this.imageC;
      } else if (this.category.id == 4) {
        return this.imageD;
      } else if (this.category.id == 5) {
        return this.imageE;
      }
    },
  },
};
</script>

<template>
  <section
    class="bg-white rounded-lg shadow-lg overflow-hidden flex-1 flex flex-col h-full w-full hover:cursor-pointer"
    @click="categoryDetatil()"
  >
    <div class="bg-cover h-48 flex place-content-center">
      <img v-bind:src="getImage()" class="w-[150px] h-auto" />
    </div>
    <div
      class="p-4 flex-1 flex flex-col text-white"
      :class="getBgColor(category.id)"
    >
      <h3 class="mb-4 text-xl">{{ category.eventCategoryName }}</h3>
      <div class="mb-4 text-grey-darker text-sm flex-1">
        <p v-if="category.eventCategoryDescription">
          {{ category.eventCategoryDescription }}
        </p>
        <p v-else>No Descriptions</p>
      </div>
      <div
        class="flex justify-between items-center border-t border-grey-light pt-2"
      >
        <p
          class="text-xs text-grey hover:text-red uppercase no-underline tracking-wide"
        >
          {{ category.eventCategoryDuration }} minute
        </p>
        <div @click.stop.prevent="categoryDetatil()">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="currentColor"
            class="bi bi-arrow-right-square-fill"
            viewBox="0 0 16 16"
          >
            <path
              d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2v12zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1z"
            />
          </svg>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.tag {
  color: #fff;
  margin: 0 1rem;
  padding: 0.4rem 0.75rem;
  border-radius: 1rem;
}
</style>
