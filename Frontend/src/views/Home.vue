<script setup></script>

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
            <span class="text-blue-700 text-5xl md:text-4xl lg:text-6xl"
              >Appointment</span
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
    <!-- <div class="w-full md:w-1/2 py-24 bg-slate-100/100"> 
       <div
        class="container mx-auto flex px-16 py-24 md:flex-row flex-col items-center"
      >  -->
        <div
          class="lg:flex-grow md:w-1/2 lg:pl-48 md:pl-20 pr-20 flex flex-col md:items-center md:text-left mb-16 md:mb-0 items-center text-center"
        >        
        <div class="image-fader">
        <img class="bottom" src="../assets/coverimg.jpg" alt="cover_image" />
        <img class="top" src="../assets/coverimg2.jpg" alt="cover_image2" />
      </div>
        </div>
      <!-- </div> -->
    <!-- </div> -->
  </section>
  <section class="bg-blue-500 py-4 px-10">dsadasdas</section>

</template>

<style scoped>
.image-fader{
  margin-top: 150px;
  width: 500px;
  height: 300px;
  position: relative;
}
.image-fader img .top{
  animation-name: imagefade;
  animation-timing-function: ease-in-out;
  animation-iteration-count: infinite;
  animation-duration: 10s;
  animation-direction: alternate;
}

@keyframes imagefade {
  0% {
  opacity:1;
}
45% {
opacity:1;
}
55% {
opacity:0;
}
100% {
opacity:0;
}
}

.image-fader img:nth-of-type(1) {
  animation-delay: 6s;
}
.image-fader img:nth-of-type(2) {
  animation-delay: 4s;
}
.image-fader img:nth-of-type(3) {
  animation-delay: 2s;
}
.image-fader img:nth-of-type(4) {
  animation-delay: 0;
}
</style>
