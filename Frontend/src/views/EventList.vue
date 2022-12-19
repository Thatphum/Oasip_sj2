<script>
import EventDataService from '../services/EventDataService';
import Event from '../components/Event.vue';
import IncomingEvent from '../components/IncomingEvent.vue';
export default {
  name: 'Event-List',
  components: {
    Event,
    IncomingEvent,
  },
  data() {
    return {
      date: new Date(),
      events: null,
    };
  },
  async beforeMount() {
    await this.listEvents();
  },
  methods: {
    print() {
      console.log(this.date);
    },
    async listEvents() {
      const res = await EventDataService.retrieveAllEvent();
      this.events = await res.json();
    },
    async deleteEvent(id) {
      const res = await EventDataService.deleteEvent(id);
      if (res.status === 200) {
        this.events = this.events.filter((value) => value.id !== id);
        this.$swal.fire('Saved!', '', 'success');
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error to delete event!',
        });
      }
    },
    confirmDeleteEvent(id) {
      this.$swal
        .fire({
          title: 'Do you want to delete the event?',
          showCancelButton: true,
          confirmButtonText: 'Yes',
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.deleteEvent(id);
          }
        });
    },
  },
};
</script>
<template>
  <section class="p-10 md:p-24 bg-gray-100">
    <div class="lg:flex lg:justify-center">
      <div class="flex flex-col lg:flex-row gap-8 md:mt-0 mt-12">
        <div class="flex flex-col gap-8 order-last lg:order-first">
          <!-- <IncomingEvent /> -->
          <div class="container min-h-screen" id="tableEvent">
            <!-- Responsive Table Section -->
            <table class="responsive-table">
              <!-- Responsive Table Header Section -->
              <thead class="hidden lg:block bg-slate-300">
                <tr class="grid grid-cols-9 text-center py-3">
                  <th class="col-span-2">Date & Time</th>
                  <th class="col-span-3">Name</th>
                  <th>Duration</th>
                  <th class="col-span-2">Category</th>
                  <th class="col-span-1"></th>
                </tr>
              </thead>
              <!-- Responsive Table Body Section -->
              <tbody>
                <div v-for="event in events" v-if="events">
                  <Event
                    :event="event"
                    @deleteEvent="confirmDeleteEvent($event)"
                  />
                </div>
                <div v-else class="row-span-full text-center">
                  No Scheduled Events
                </div>
              </tbody>
            </table>
          </div>
        </div>
        <div id="filterEvent" class="block md:flex justify-center lg:h-screen">
          <div>
            <div
              class="flex flex-col justify-center items-center gap-4 p-6 bg-white rounded-lg transition-all duration-500 ease h-min hover:h-max"
            >
              <div
                class="text-xl focus:outline-none focus:border-sky-500 focus:ring-sky-500"
              >
                Filter
              </div>
              <!-- <div class="pt-2 relative mx-auto text-gray-600">
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
                  required
                  name="category"
                  id="category"
                  class="border-2 border-gray-300 bg-white h-10 w-full px-4 rounded-lg text-sm focus:outline-none focus:border-sky-500 focus:ring-sky-500"
                  placeholder="Category"
                >
                  <option value="0" selected>None</option>
                  <option :value="1">Upcoming Events</option>
                  <option :value="2">Past Events</option>
                  <option :value="3">Select Day Events</option>
                </select>
              </div>
              <DatePicker
                v-model="date"
                color="blue"
                title-position="left"
                is-expanded
              />
            </div>
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
