<script>
export default {
  data() {
    return {
      classColorValue: [
        'bg-red-500',
        'bg-blue-500',
        'bg-yellow-500',
        'bg-green-500',
        'bg-orange-500',
      ],
    };
  },
  props: {
    event: {
      type: Object,
      requird: true,
    },
  },
  methods: {
    formatTime(dateTime) {
      var date = new Date(dateTime).toLocaleString('th-TH');
      return date.slice(-8, -3);
    },
    formatDate(date) {
      var date = new Date(date);
      return date;
    },
    eventDetatil() {
      this.$router.push({ name: 'Detail', params: { id: this.event.id } });
    },
  },
};
</script>

<template>
  <!-- {{ event }} -->
  <tr
    class="grid grid-cols-4 lg:grid-cols-9 border-b-2 text-center items-center auto-cols-auto text-lg hover:cursor-pointer hover:bg-gray-100"
    @click="eventDetatil()"
  >
    <td
      class="py-2 lg:py-4 col-span-3 md:col-span-2 flex md:flex-col justify-center items-center -order-2 md:order-none"
    >
      <p class="font-extrabold text-md text-gray-900">
        {{ formatDate(event.eventStartTime).getDate() }} /
        {{
          formatDate(event.eventStartTime).toLocaleString('en-US', {
            month: 'short',
          })
        }}
        /
        {{ formatDate(event.eventStartTime).getFullYear() }}
      </p>
      <p class="text-md md:text-sm text-gray-600 ml-2 md:ml-0">
        {{ formatTime(event.eventStartTime) }}
      </p>
    </td>
    <td
      class="py-1 lg:py-4 flex w-full break-all col-span-4 md:col-span-2 lg:col-span-3 justify-center"
    >
      {{ event.bookingName }}
      <!-- {{
        event.bookingName.length < 40
          ? event.bookingName
          : event.bookingName.substring(0, 40 - event.bookingName.length - 1) +
            ' ...'
      }} -->
    </td>
    <td class="lg:py-4 -order-1 md:order-none">{{ event.eventDuration }} m</td>
    <td class="lg:py-4 col-span-4 md:col-span-2 py-2">
      <p
        class="font-extrabold text-gray-900 tag text-center"
        :class="classColorValue[event.eventCategoryId - 1]"
      >
        {{ event.eventCategoryEventCategoryName }}
      </p>
    </td>
    <td class="lg:py-4 col-span-1 md:block hidden">
      <div class="flex justify-center gap-4">
        <button @click.stop.prevent="this.$emit('deleteEvent', event.id)">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="currentColor"
            class="bi bi-trash-fill fill-red-500"
            viewBox="0 0 16 16"
          >
            <path
              d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"
            />
          </svg>
        </button>
        <button @click.stop.prevent="eventDetatil()">
          <img
            src="../assets/svg/three-dots.svg"
            alt="More detail"
            width="23"
          />
        </button>
      </div>
    </td>
  </tr>
</template>

<style scoped>
.tag {
  color: #fff;
  margin: 0 1rem;
  padding: 0.4rem 0.75rem;
  border-radius: 1rem;
}
</style>
