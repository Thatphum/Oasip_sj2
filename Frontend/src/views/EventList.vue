<script>
import EventDataService from '../services/EventDataService';
import Event from '../components/Event.vue';
import IncomingEvent from '../components/IncomingEvent.vue';
import Filter from '../components/Filter.vue';
export default {
  name: 'Event-List',
  components: {
    Event,
    IncomingEvent,
    Filter,
  },
  data() {
    return {
      events: null,
    };
  },
  async beforeMount() {
    await this.listEvents();
  },
  methods: {
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
    async filterOption(filter) {
      if (filter != false) {
        this.events = [];

        const res = await EventDataService.retreiveAllEventFilter(
          filter.eventCategoryId,
          filter.option,
          filter.dateTime
        );
        this.events = await res.json();
      }
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
                <div
                  v-for="event in events"
                  v-if="events != null ? events.length > 0 : false"
                >
                  <Event
                    :event="event"
                    @deleteEvent="confirmDeleteEvent($event)"
                  />
                </div>
                <div v-else class="row-span-full text-center text-3xl">
                  No Scheduled Events
                </div>
              </tbody>
            </table>
          </div>
        </div>
        <div id="filterEvent" class="block md:flex justify-center lg:h-screen">
          <div>
            <Filter @filter="filterOption($event)" />
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
