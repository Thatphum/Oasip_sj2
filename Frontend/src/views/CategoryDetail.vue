<script>
import EventCategoryDataService from '../services/EventCategoryDataService';

export default {
  name: 'Event-Detail',
  data() {
    return {
      category: {
        eventCategoryName: null,
        eventCategoryDescription: null,
        eventCategoryDuration: null,
      },
      updatCategory: {
        eventCategoryName: null,
        eventCategoryDescription: null,
        eventCategoryDuration: null,
      },
      editMode: false,
    };
  },
  async beforeMount() {
    await this.getDetailCategory(this.$route.params.id);
    // console.log(this.category);
  },
  methods: {
    print() {
      console.log(this.date);
    },
    async getDetailCategory(id) {
      const res = await EventCategoryDataService.retrieveCategory(id);
      if (res.status == 200) {
        this.category = await res.json();
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error to get category!',
        });
        this.$router.push({ name: 'ListUsers' });
      }
    },
    async updateCategorySubmit() {
      if (
        this.category.eventCategoryName !=
          this.updatCategory.eventCategoryName ||
        this.category.eventCategoryDescription !=
          this.updatCategory.eventCategoryDescription ||
        this.category.eventCategoryDuration !=
          this.updatCategory.eventCategoryDuration
      ) {
        const res = await EventCategoryDataService.updateEvent(
          this.category.id,
          this.updatCategory
        );
        if (res.status == 200) {
          this.$swal.fire('Saved!', '', 'success');
          await this.getDetailCategory(this.$route.params.id);
          this.editMode = false;
        } else {
          this.$swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'This name or email are already used.',
          });
        }
      } else {
        this.editMode = false;
      }
    },
    editModeOn() {
      this.editMode = true;
      this.updatCategory.eventCategoryName = this.category.eventCategoryName;
      this.updatCategory.eventCategoryDescription =
        this.category.eventCategoryDescription;
      this.updatCategory.eventCategoryDuration =
        this.category.eventCategoryDuration;
    },
    checkProperties(obj) {
      for (var key in obj) {
        if (obj[key] !== null && obj[key] != '') return true;
      }
      return false;
    },
  },
};

// onBeforeMount(async () => {
//   await getDetailCategory(params.id);
//   await listCategory();
//   // console.log(categories.value);

// const errorduration = ref();
// const save = async (categpryid) => {
//   if (
//     editDuration.value > 0 &&
//     editDuration.value <= 480 &&
//     editCategoryName.value != '' &&
//     checkName.value
//   ) {
//     const categorys = {
//       id: categpryid,
//       eventCategoryName: editCategoryName.value,
//       eventCategoryDescription: editCategoryDescription.value,
//       eventCategoryDuration: editDuration.value,
//     };
//     if (
//       categorys.eventCategoryName != category.value.eventCategoryName ||
//       categorys.eventCategoryDescription !=
//         category.value.eventCategoryDescription ||
//       categorys.eventCategoryDuration != category.value.eventCategoryDuration
//     ) {
//       console.log(categpryid);
//       const res = await EventCategoryDataService.updateEvent(
//         categpryid,
//         categorys
//       );
//       if (res.status != 400) {
//         editModeOff();
//         await getDetailCategory(params.id);
//       } else {
//         // console.log('error update');
//         alert('error update' + res.statusText);
//       }
//     } else {
//       editModeOff();
//     }
//   }
// };

// const errorName = ref();
// const categories = ref();
// const checkName = ref(true);
// const listCategory = async () => {
//   const res = await EventCategoryDataService.retrieveAllCategoryForFilter();
//   categories.value = await res.json();
//   categories.value = categories.value.filter((value) => {
//     return !(value.eventCategoryName == category.value.eventCategoryName);
//   });
//   // console.log(categories.value);
// }; //listAllEvent
// const checkCategoryName = async () => {
//   if (editCategoryName.value != '') {
//     editCategoryName.value = editCategoryName.value.replace(/^\s+|\s+$/gm, '');
//     // console.log(editCategoryName.value);
//     // console.log(categories.value);
//     var result = categories.value.filter((value) => {
//       // console.log(value.eventCategoryName.toLowerCase());
//       return (
//         value.eventCategoryName.toLowerCase() ==
//         editCategoryName.value.toLowerCase()
//       );
//     });
//     if (result.length > 0) {
//       errorName.value = 'The eventCategoryName is NOT unique.';
//       checkName.value = false;
//     } else {
//       errorName.value = '';
//       checkName.value = true;
//     }
//   } else {
//     errorName.value = 'The eventCategoryName is not null';
//     checkName.value = false;
//   }
// };
</script>

<template>
  <section class="grid place-items-center h-screen bg-white md:bg-gray-100">
    <div
      class="max-w-[100%] md:max-w-xl h-fit w-full flex justify-center items-center px-10 md:px-20 lg:px-10 pb-10 md:pb-8 pt-10 bg-white rounded-xl md:shadow-2xl"
    >
      <div class="w-full">
        <div class="text-2xl md:text-3xl text-center relative">
          <button class="flex items-center">
            <router-link
              :to="{ name: 'ListCategory' }"
              class="flex items-center absolute left-0 top-0"
            >
              <div class="p-2 bg-blue-500 rounded-full mr-2">
                <svg
                  id="Layer_1"
                  version="1.1"
                  viewBox="0 0 32 32"
                  xml:space="preserve"
                  xmlns="http://www.w3.org/2000/svg"
                  xmlns:xlink="http://www.w3.org/1999/xlink"
                  class="fill-white w-4 md:w-5 h-auto"
                >
                  <path
                    d="M28,14H8.8l4.62-4.62C13.814,8.986,14,8.516,14,8c0-0.984-0.813-2-2-2c-0.531,0-0.994,0.193-1.38,0.58l-7.958,7.958  C2.334,14.866,2,15.271,2,16s0.279,1.08,0.646,1.447l7.974,7.973C11.006,25.807,11.469,26,12,26c1.188,0,2-1.016,2-2  c0-0.516-0.186-0.986-0.58-1.38L8.8,18H28c1.104,0,2-0.896,2-2S29.104,14,28,14z"
                  />
                </svg>
              </div>
            </router-link>
          </button>
          Scheduled details
        </div>
        <form
          action=""
          class="flex flex-col mt-8"
          @submit="updateCategorySubmit()"
          onsubmit="return false;"
        >
          <div class="mt-6 text-base md:text-xl relative">
            <div class="border-b border-gray-400">
              <h1>Category Name</h1>
              <p
                class="text-gray-400 break-words text-base py-2 px-3"
                v-if="!editMode"
              >
                {{ category.eventCategoryName }}
              </p>
              <div v-else class="text-base">
                <input
                  required
                  class="shadow appearance-none border rounded-full w-full my-1 py-1 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="username"
                  type="text"
                  maxlength="100"
                  v-model="updatCategory.eventCategoryName"
                />
              </div>
            </div>
            <div class="border-b border-gray-400 pt-4">
              <h1>Category Description</h1>
              <p
                class="text-gray-400 break-words text-base py-2 px-3 pb-6"
                v-if="!editMode"
              >
                {{
                  category.eventCategoryDescription
                    ? category.eventCategoryDescription
                    : 'No description'
                }}
              </p>
              <div v-else class="text-base">
                <textarea
                  rows="2"
                  class="shadow appearance-none border rounded-lg w-full my-1 py-1 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  maxlength="500"
                  v-model="updatCategory.eventCategoryDescription"
                ></textarea>
              </div>
            </div>
            <div class="border-b border-gray-400 pt-4">
              <h1>Duration</h1>
              <p
                class="text-gray-400 break-words text-base py-2 px-3"
                v-if="!editMode"
              >
                {{ category.eventCategoryDuration }}
              </p>
              <div class="text-base" v-else>
                <input
                  class="shadow appearance-none border rounded-full w-full my-1 py-1 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="username"
                  type="number"
                  max="480"
                  min="1"
                  v-model="updatCategory.eventCategoryDuration"
                />
              </div>
            </div>

            <div class="flex justify-evenly pt-6 text-lg">
              <button
                class="flex items-center transition ease-in-out hover:scale-110 duration-300"
                v-if="!editMode"
                @click="editModeOn()"
              >
                <div class="p-2 bg-blue-500 rounded-full mr-2">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    fill="currentColor"
                    class="bi bi-pencil-square fill-white"
                    viewBox="0 0 16 16"
                  >
                    <path
                      d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"
                    />
                    <path
                      fill-rule="evenodd"
                      d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"
                    />
                  </svg>
                </div>

                Edit
              </button>

              <button
                class="flex items-center text-base transition ease-in-out hover:scale-110 duration-300 bg-green-500 text-white p-1.5 rounded-xl"
                v-if="editMode"
              >
                <div class="rounded-full mr-1">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    fill="currentColor"
                    class="bi bi-clipboard-check-fill fill-white"
                    viewBox="0 0 16 16"
                  >
                    <path
                      d="M6.5 0A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3Zm3 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h3Z"
                    />
                    <path
                      d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1A2.5 2.5 0 0 1 9.5 5h-3A2.5 2.5 0 0 1 4 2.5v-1Zm6.854 7.354-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L7.5 10.793l2.646-2.647a.5.5 0 0 1 .708.708Z"
                    />
                  </svg>
                </div>

                Save
              </button>
              <button
                class="flex items-center text-base transition ease-in-out hover:scale-110 duration-300 bg-red-500 text-white p-1.5 rounded-xl"
                v-if="editMode"
                @click="editMode = false"
              >
                <div class="rounded-full mr-1">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    fill="currentColor"
                    class="bi bi-x-square-fill fill-white"
                    viewBox="0 0 16 16"
                  >
                    <path
                      d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708z"
                    />
                  </svg>
                </div>

                Cancel
              </button>

              <!-- <button
                class="flex items-center transition ease-in-out hover:scale-110 duration-300"
                @click="confirmDeleteUser(user.id)"
              >
                <div class="p-2 bg-red-500 rounded-full mr-2">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    fill="currentColor"
                    class="bi bi-trash-fill fill-white"
                    viewBox="0 0 16 16"
                  >
                    <path
                      d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"
                    />
                  </svg>
                </div>

                Delete
              </button> -->
            </div>
          </div>
        </form>
      </div>
    </div>
  </section>
</template>

<style scoped></style>
