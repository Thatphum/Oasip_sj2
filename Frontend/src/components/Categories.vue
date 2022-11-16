<script setup>
import router from '../router'

// export default {
//   name: 'Categories',
//   props: {
//     categories: {
//       type: Object,
//       required: true,
//     },
//   },
//   methods: {
//     setDetail() {
//       setTimeout(() => {
//         router.push({ name: 'CategoryDetail', params: { id: prop.mask.id } })
//       }, 500)
//     },
//     getBorder(id) {
//       return borderCategory[id - 1]
//     },
//   },
// }

const prop = defineProps({
  mask: Object,
})

const setDetail = () => {
  setTimeout(() => {
    router.push({ name: 'Category', params: { id: prop.mask.id } })
  }, 500)
}

const getBorder = (id) => {
  return borderCategory[id - 1]
}

const borderCategory = [
  'border-red-500',
  'border-blue-500',
  'border-yellow-500',
  'border-green-500',
  'border-orange-500',
]

</script>

<template>
  <div
    class="max-w-6xl h-60 md:h-28 shadow-lg rounded overflow-hidden m-4 sm:flex bg-white rounded-xl border-l-8 border-r-8 shadow-lg shadow-black/50 cursor-pointer scale-100 hover:scale-105 transition duration-700"
    :class="getBorder(prop.mask.id)"
    @click="setDetail"
  >
    <div
      class="flex flex-col w-1/5 justify-center items-center border-r-2 border-gray-200 px-4"
    >
      <p class="font-extrabold text-xl text-gray-900">
        {{ prop.mask.eventCategoryName }}
      </p>
    </div>
    <!-- category -->
    <div
      class="flex flex-col w-3/4 justify-center items-start border-r-2 border-gray-200 px-4"
    >
      <div
        class="font-extrabold text-xl text-gray-900"
        v-if="prop.mask.eventCategoryDescription != null"
      >
        {{ prop.mask.eventCategoryDescription }}
      </div>
      <div class="font-extrabold text-xl text-gray-900" v-else>
        No Descriptions
      </div>
      <!-- <p class="text-sm text-gray-600">klatsch.duration</p> -->
    </div>
    <!-- name -->
    <div class="flex flex-col justify-center border-r-2 border-gray-200 px-4">
      <p class="font-extrabold text-xl text-gray-900">
        {{ prop.mask.eventCategoryDuration }}
        Minutes
      </p>
    </div>
    <!-- button -->
    <div class="flex flex-col justify-center items-center px-4">
      <div class="flex">
        <button
          type="button"
          class="flex items-center justify-center px-2 py-2 rounded-full inline-block text-black hover:text-red-700"
          @click.stop.prevent="setDetail()"
        >
          Detail
        </button>
      </div>
    </div>
  </div>
  <!-- <div
    class="max-w-2xl px-8 py-4 mx-auto bg-white rounded-lg shadow-md transition hover:bg-gray-200 cursor-pointer border-8"
    @click="setDetail()"
    :class="getBorder(prop.mask.id)"
  >
    <div class="mt-2">
      <h1
        href="https://stackdiary.com/"
        class="text-2xl font-bold text-gray-700 text-black/80"
      >
        {{ prop.mask.eventCategoryName }}
      </h1>
      <p
        class="mt-2 text-black/40"
        v-if="prop.mask.eventCategoryDescription.length > 0"
      >
        {{ prop.mask.eventCategoryDescription }}
      </p>
      <p v-else class="mt-2 text-gray-900 dark:text-gray-300">
        No Descriptions
      </p>
    </div>
    <div class="flex items-center justify-between mt-4">
      <button
        class="text-blue-600 dark:text-blue-400 hover:underline"
        @click.stop.prevent="setDetail()"
      >
        Read more ⟶
      </button>
      <div class="flex items-center">
        <span
          class="inline-block rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2 text-white"
          :class="getBgColor(prop.mask.id)"
          >{{ prop.mask.eventCategoryDuration }} Minutes</span
        >
      </div>
    </div>
  </div> -->
</template>

<style scoped>
.tags {
  /* display: flex;
  flex-direction: row;
  flex-wrap: wrap; */
  /* justify-content: flex-start; */
  align-items: center;
  margin-bottom: 1rem;
}
.tag {
  color: #fff;
  padding: 0.5rem 1rem;
  border-radius: 1rem;
}
</style>
