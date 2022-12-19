<script setup>
        var text = ['Appoitment','Meeting','Consult', 'Advise'];

        textSequence(0);
        function textSequence(i) {

            if (text.length > i) {
                setTimeout(function() {
                    document.getElementById("words").innerHTML = text[i];
                    textSequence(++i);
                }, 5000); // 1 second (in milliseconds)

            } else if (text.length == i) {
                textSequence(0);
            }

        }
</script>

<template>
  <section class="flex flex-col md:flex-row border-b-4 pt-16">
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
              ></span
            >
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
              <button
                class="text-white bg-blue-700 border-0 py-2 px-6 rounded-xl text-md md:text-lg"
              >
                Start your events
              </button>
              <button class="text-gray-700 py-2 px-6 md:text-lg text-md">
                Sign Up
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="w-full md:w-1/2 bg-slate-100/100">
      <div class="container mx-auto flex md:flex-row flex-col items-center">
        <!-- Area of the images -->

        <img src="../assets/coverimg.jpg" alt="" />
        <!-- End of area -->
      </div>
    </div>
  </section>

  <div class="text-black h-60 mt-36 mb-36 flex drop-shadow-xl ml-60">
    <img
      src="../assets/Programming-1.png"
      alt="cover1"
      class="h-full w-auto ml-96 drop-shadow-xl"
    />
    <div class="">
      <p class="text-center text-4xl ml-52">Categories</p>
      <p class="text-center text-xl ml-52">
        You can make appointments with specialists in different <br />categories
        to consult and answer questions about the <br />problems you are
        facing.<br />
      </p>
      <a href="" class="text-black ml-56 underline hover:text-blue-600"
        >See more...</a
      >
    </div>
  </div>
  <div class="text-black h-60 mt-36 mb-36 flex drop-shadow-xl ml-60">
    <div class="">
      <p class="text-center text-4xl ml-96">Events</p>
      <p class="text-center text-xl ml-96">
        Make appointments and manage events easily <br />and conveniently.
      </p>
      <a href="" class="ml-96 text-black underline hover:text-blue-600"
        >See more...</a
      >
    </div>
    <img
      src="../assets/devices-pana.png"
      alt="cover1"
      class="h-full w-auto ml-52 drop-shadow-xl"
    />
  </div>
</template>

<style scoped>

</style>
