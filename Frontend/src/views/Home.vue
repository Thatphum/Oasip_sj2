<script>
export default {
  data() {
    return {
      texts: ['Appointment', 'Meeting', 'Consult', 'Advise'],
      currentText: '',
      currentIndex: 0,
    };
  },
  mounted() {
    this.currentText = this.texts[this.currentIndex];
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.texts.length;
      this.currentText = this.texts[this.currentIndex];
    }, 5000);
  },
};
</script>

<template>
  <section class="md:flex border-b-4 pt-16 md:w-full">
    <div class="w-full md:w-1/2 md:py-24">
      <div
        class="container mx-auto flex px-10 md:px-5 py-16 lg:py-24 items-center"
      >
        <div
          class="flex flex-col md:mr-10 lg:mr-20 items-center md:items-end text-left mb-16 md:mb-0"
        >
          <h1
            class="text-4xl md:text-3xl lg:text-6xl mb-6 text-gray-900 w-full md:w-3/4 text-center md:text-left"
          >
            One platform to <br class="hidden md:inline-block" />
            <span
              class="text-blue-700 text-5xl md:text-4xl lg:text-6xl"
              id="words"
              >{{ currentText }}
            </span>
          </h1>
          <p
            class="mb-8 leading-relaxed text-md md:text-lg w-full md:w-3/4 break-keep"
          >
            Appointments with teams, solving your problem, exchaning ideas and
            managing team work precisely - all on the OASIP platform.
          </p>
          <div class="w-full md:w-3/4">
            <div
              class="flex flex-col lg:flex-row justify-center lg:justify-start"
            >
              <router-link
                class="text-white bg-blue-700 border-0 py-2 px-6 rounded-xl text-md md:text-lg"
                :to="{ name: 'CreateEvent' }"
              >
                Start your events
              </router-link>
              <router-link
                class="text-gray-700 py-2 px-6 md:text-lg text-md"
                :to="{ name: 'SignUp' }"
              >
                Sign Up
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="w-full md:w-1/2 bg-slate-100/100">
      <div
        class="container mx-auto flex md:flex-row flex-col items-center justify-center place-content-center h-full"
      >
        <!-- Area of the images -->
        <img src="../assets/coverimg.png" alt="coverimg" class="w-2/3" />
        <!-- End of area -->
      </div>
    </div>
  </section>
  <div
    class="flex flex-col items-center place-content-center m-10 md:m-20 gap-6"
  >
    <div
      class="flex flex-col md:flex-row flex-wrap items-center place-content-center"
    >
      <img
        src="../assets/Programming-1.png"
        alt="cover1"
        class="w-1/2 md:w-1/6 drop-shadow-xl"
      />
      <div class="grid place-content-center ml-4 mt-4 md:mt-0 w-full md:w-1/4">
        <p class="text-center text-xl md:text-3xl">Categories</p>
        <p class="text-left md:text-base break-all">
          You can make appointments with specialists in different categories to
          consult and answer questions about the problems you are facing.
        </p>
        <router-link
          :to="{ name: 'ListCategory' }"
          class="text-black text-center underline hover:text-blue-600"
          >See more...</router-link
        >
      </div>
    </div>
    <div
      class="flex flex-col-reverse md:flex-row items-center place-content-center"
    >
      <div class="grid place-content-center mr-4 mt-4 md:mt-0 w-full md:w-1/4">
        <p class="text-center md:text-4xl">Events</p>
        <p class="text-left md:text-base break-all">
          Make appointments and manage events easily and conveniently.
        </p>
        <router-link
          :to="{ name: 'ListEvent' }"
          class="text-black text-center underline hover:text-blue-600"
          >See more...</router-link
        >
      </div>
      <img
        src="../assets/devices-pana.png"
        alt="cover1"
        class="w-1/2 md:w-1/6 drop-shadow-xl"
      />
    </div>
  </div>
</template>

<style scoped>
#words {
  animation: fadeInOut 5s infinite;
}
@keyframes fadeInOut {
  0% {
    opacity: 0;
  }
  45% {
    opacity: 1;
  }
  55% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
</style>
