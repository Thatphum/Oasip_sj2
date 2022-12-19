<script>
import { onBeforeMount, ref } from 'vue';
import { useRoute } from 'vue-router';
import router from '../router';
import UserDataService from '../services/UserDataService';

export default {
  name: 'Event-Detail',
  data() {
    return {
      user: {
        id: null,
        name: null,
        email: null,
      },
      updatUser: {
        name: null,
        email: null,
        role: null,
      },
      editMode: false,
    };
  },
  async beforeMount() {
    await this.getDetailUser(this.$route.params.id);
  },
  methods: {
    print() {
      console.log(this.date);
    },
    async getDetailUser(id) {
      const res = await UserDataService.retrieveUser(id);
      if (res.status == 200) {
        this.user = await res.json();
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error to get user!',
        });
        this.$router.push({ name: 'ListUsers' });
      }
    },
    async deleteUser(id) {
      const res = await UserDataService.deleteUser(id);
      if (res.status === 200) {
        this.$swal.fire('Saved!', '', 'success');
        this.$router.push({ name: 'ListUsers' });
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error to delete event!',
        });
      }
    },
    confirmDeleteUser(id) {
      this.$swal
        .fire({
          title: 'Do you want to delete the user?',
          showCancelButton: true,
          confirmButtonText: 'Yes',
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.deleteUser(id);
          }
        });
    },
    async updateUser() {
      if (this.checkProperties(this.updatUser)) {
        if (
          this.user.name != this.updatUser.name ||
          this.user.email != this.updatUser.email ||
          this.user.role != this.updatUser.role
        ) {
          const res = await UserDataService.updateUser(
            this.user.id,
            this.updatUser
          );
          if (res.status == 200) {
            this.$swal.fire('Saved!', '', 'success');
            await this.getDetailUser(this.$route.params.id);
            this.resetUpdate();
          } else {
            this.$swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'This name or email are already used.',
            });
          }
        } else {
          this.resetUpdate();
        }
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'This name , email or are not null',
        });
      }
    },
    editModeOn() {
      this.editMode = true;
      this.updatUser.name = this.user.name;
      this.updatUser.email = this.user.email;
      this.updatUser.role = this.user.role;
    },
    checkProperties(obj) {
      for (var key in obj) {
        if (obj[key] !== null && obj[key] != '') return true;
      }
      return false;
    },
  },
};
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
              :to="{ name: 'ListUsers' }"
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
          @submit="updateUser()"
          onsubmit="return false;"
        >
          <div class="mt-6 text-base md:text-xl relative">
            <div class="border-b border-gray-400">
              <h1>Name</h1>
              <p
                class="text-gray-400 break-words text-base py-2 px-3"
                v-if="!editMode"
              >
                {{ user.name }}
              </p>
              <div v-else class="text-base">
                <input
                  class="shadow appearance-none border rounded-full w-full my-1 py-1 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="username"
                  type="text"
                  maxlength="100"
                  v-model="updatUser.name"
                />
              </div>
            </div>
            <div class="border-b border-gray-400 pt-4">
              <h1>Email</h1>
              <p
                class="text-gray-400 break-words text-base py-2 px-3"
                v-if="!editMode"
              >
                {{ user.email }}
              </p>
              <div v-else class="text-base">
                <input
                  class="shadow appearance-none border rounded-full w-full my-1 py-1 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="email"
                  type="email"
                  maxlength="100"
                  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"
                  v-model="updatUser.email"
                />
              </div>
            </div>
            <div class="border-b border-gray-400 pt-4">
              <h1>Role</h1>
              <p
                class="text-gray-400 break-words text-base py-2 px-3"
                v-if="!editMode"
              >
                {{ user.role }}
              </p>
              <div class="text-base" v-else>
                <select
                  name="role"
                  id="role"
                  v-model="updatUser.role"
                  class="shadow border rounded-full my-1 py-1 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-orange-600"
                >
                  <option disabled selected value>Select Role</option>
                  <option>student</option>
                  <option>admin</option>
                  <option>lecturer</option>
                </select>
              </div>
            </div>
            <div class="border-b border-gray-400 pt-4">
              <h1>Create On</h1>
              <p class="text-gray-400 break-words text-base py-2 px-3">
                {{ user.createOn }}
              </p>
            </div>
            <div class="border-b border-gray-400 pt-4 pb-1">
              <h1>Update On</h1>
              <p class="text-gray-400 break-words text-base py-2 px-3">
                {{ user.updateOn }}
              </p>
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

              <button
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
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </section>
</template>

<style></style>
