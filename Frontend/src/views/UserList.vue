<script>
import { onBeforeMount, ref } from 'vue';
import router from '../router';
import UserDataService from '../services/UserDataService';
import User from '../components/User.vue';

export default {
  name: 'User-List',
  components: {
    User,
  },
  data() {
    return {
      users: null,
    };
  },
  async beforeMount() {
    await this.listUsers();
  },
  methods: {
    async listUsers() {
      const res = await UserDataService.retrieveAllUser();
      this.users = await res.json();
    },
    async deleteUser(id) {
      const res = await UserDataService.deleteUser(id);
      if (res.status === 200) {
        this.users = this.users.filter((value) => value.id !== id);
        this.$swal.fire('Saved!', '', 'success');
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error to delete user!',
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
  },
};
</script>

<template>
  <section class="p-10 md:p-24 bg-gray-100">
    <div class="lg:flex lg:justify-center">
      <div class="flex flex-col lg:flex-row gap-8">
        <div class="flex flex-col gap-8 order-last lg:order-first">
          <!-- <IncomingEvent /> -->
          <div
            class="container lg:min-w-[1000px] min-h-screen md:mt-0 mt-12"
            id="tableEvent"
          >
            <!-- Responsive Table Section -->
            <table class="responsive-table">
              <!-- Responsive Table Header Section -->
              <thead class="hidden lg:block bg-slate-300">
                <tr class="grid grid-cols-9 text-center py-3">
                  <th class="col-span-3">Name</th>
                  <th class="col-span-3">Email</th>
                  <th>Role</th>
                  <th class="col-span-2">Category</th>
                </tr>
              </thead>
              <!-- Responsive Table Body Section -->
              <tbody>
                <div v-for="user in users" v-if="users">
                  <!-- <Event
                    :event="event"
                    @deleteEvent="confirmDeleteEvent($event)"
                  /> -->
                  <User :user="user" @deleteUser="confirmDeleteUser($event)" />
                </div>
                <div v-else class="row-span-full text-center">
                  No Scheduled Events
                </div>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.container {
  width: 100%;
  max-width: 1200px;
}

/* Responsive Table Style */
.responsive-table {
  background-color: #fefefe;
  border-collapse: collapse;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.02);
  width: 100%;
  overflow: hidden;
}
</style>
