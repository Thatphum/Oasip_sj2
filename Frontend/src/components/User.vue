<script>
export default {
  props: {
    user: {
      type: Object,
      requird: true,
    },
  },
  methods: {
    userDetatil() {
      this.$router.push({ name: 'UserDetail', params: { id: this.user.id } });
    },
  },
};
</script>

<template>
  <tr
    class="grid grid-cols-1 lg:grid-cols-9 border-b-2 py-2 text-center items-center auto-cols-auto text-lg hover:cursor-pointer hover:bg-gray-100"
    @click="userDetatil()"
  >
    <td
      class="px-5 py-2 lg:py-4 col-span-3 md:col-span-3 flex md:flex-col justify-center items-center -order-2 md:order-none break-all"
    >
      {{ user.name }}
    </td>
    <td
      class="px-5 py-1 lg:py-4 flex w-full break-all col-span-4 md:col-span-2 lg:col-span-3 justify-center"
    >
      {{ user.email }}
    </td>
    <td
      class="lg:py-4 font-extrabold text-gray-900 tag text-center bg-teal-400"
    >
      {{ user.role }}
    </td>
    <td class="lg:py-4 col-span-2 md:block hidden">
      <div class="flex justify-center gap-4">
        <button @click.stop.prevent="this.$emit('deleteUser', user.id)">
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
        <button @click.stop.prevent="userDetatil()">
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
  padding: 0.1rem 0.75rem;
  border-radius: 1rem;
}
</style>
