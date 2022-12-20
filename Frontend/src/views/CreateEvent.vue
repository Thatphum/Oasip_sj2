<script>
import { onBeforeMount, ref } from 'vue';
import EventCategoryDataService from '../services/EventCategoryDataService';
import EventDataService from '../services/EventDataService';
export default {
  name: 'SignIn',
  data() {
    return {
      requiredEvent: {
        bookingName: '',
        bookingEmail: '',
        eventCategoryId: '',
        eventDuration: '',
        eventStartTime: '',
        eventNotes: '',
        eventFile: '',
      },
      eventFile: '',
      eventDate: '',
      eventTime: '',
      categories: null,
      animationField: {
        bookingName: false,
        bookingEmail: false,
        eventCategoryId: false,
        eventDuration: false,
        eventNotes: false,
      },
      errorMessage: false,
      showPassword: true,
    };
  },
  async beforeMount() {
    await this.getAllCategory();
  },
  methods: {
    goToSignup() {
      this.$router.push({ name: 'SignUp' });
    },
    inputAnimation(field) {
      this.requiredEvent[field] == ''
        ? (this.animationField[field] = false)
        : (this.animationField[field] = true);
    },
    async getAllCategory() {
      const res = await EventCategoryDataService.retrieveAllCategoryForCreate();
      this.categories = await res.json();
    },
    durationCategory() {
      if (this.requiredEvent.eventCategoryId != '') {
        var x = this.categories.find(
          (value) => value.id == this.requiredEvent.eventCategoryId
        );
        this.requiredEvent.eventDuration = x.eventCategoryDuration;
      } else {
        this.requiredEvent.eventDuration = '';
      }
    },
    checkDate() {
      console.log(this.requiredEvent.eventStartTime);
      if (this.requiredEvent.eventStartTime != '') {
        var selectedDate = new Date(this.requiredEvent.eventStartTime);
        var now = new Date();
        if (selectedDate < now) {
          alert('Date Time must be in the future');
          this.requiredEvent.eventStartTime = '';
        }
      }
    },
    minDate() {
      return new Date()
        .toISOString()
        .slice(0, new Date().toISOString().lastIndexOf(':'));
    },
    getDateM(date) {
      var dd = String(date.getDate()).padStart(2, '0');
      var mm = String(date.getMonth() + 1).padStart(2, '0'); //January is 0!
      var yyyy = date.getFullYear();
      var myDate = yyyy + '-' + mm + '-' + dd;
      return myDate;
    },
    onFileChange(e) {
      var files = e.target.files || e.dataTransfer.files;
      if (!files.length) return;
      // console.log(files[0]);
      this.eventFile = files[0];
      // this.createImage(files[0]);
      this.requiredEvent.eventFile = files[0].name;
    },
    resetForm() {
      this.requiredEvent = {
        bookingName: '',
        bookingEmail: '',
        eventCategoryId: '',
        eventDuration: '',
        eventStartTime: '',
        eventNotes: '',
      };
      this.eventFile = '';
      this.eventDate = '';
      this.eventTime = '';
      this.categories = null;
      this.animationField = {
        bookingName: false,
        bookingEmail: false,
        eventCategoryId: false,
        eventDuration: false,
        eventNotes: false,
      };
    },
    async submitForm() {
      this.$swal.fire({
        title: 'Auto close alert!',
        html: 'I will close in <b></b> milliseconds.',
        timer: 4000,
        timerProgressBar: true,
      });

      const res = await EventDataService.createEvent(
        this.requiredEvent,
        this.eventFile
      );
      if (res.status === 200) {
        this.$swal.fire({
          icon: 'success',
          title: 'Success',
          text: 'Event created!',
        });
        this.resetForm();
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error to create event! is Overlap',
        });
      }
    },
    confirmEvent() {
      this.$swal
        .fire({
          title: 'Please check your event data. Press OK for booking.',
          showCancelButton: true,
          confirmButtonText: 'Yes',
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.submitForm();
          }
        });
    },
  },
};
</script>

<template>
  <section class="grid place-items-center h-screen bg-gray-100">
    <div
      class="grid grid-cols-1 lg:grid-cols-2 max-w-5xl rounded-lg bg-white drop-shadow-2xl divide-x"
    >
      <div class="rounded-l-lg p-10 hidden lg:block">
        <img src="../assets/bgCreate.png" alt="" />
      </div>
      <div
        class="flex flex-col justify-center items-center h-screen md:h-full w-screen md:w-full"
      >
        <!-- form Sign in -->
        <div class="px-12 m-4 my-8">
          <h1 class="font-bold text-3xl text-blue-700">Create Event</h1>
          <!-- Name -->
          <form
            action=""
            class="flex flex-col mt-8"
            @submit="confirmEvent()"
            onsubmit="return false;"
          >
            <label class="mb-4">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.bookingName }"
              >
                Name
              </p>
              <input
                id="name"
                type="text"
                class="input p-2 border-2 rounded-xl w-full"
                maxlength="50"
                v-model.trim="requiredEvent.bookingName"
                @focusin="animationField.bookingName = true"
                @focusout="inputAnimation('bookingName')"
                required
              />
            </label>
            <!-- Email -->
            <label class="mb-4">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.bookingEmail }"
              >
                Email
              </p>
              <input
                id="email"
                type="email"
                class="input p-2 border-2 rounded-xl w-full"
                pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"
                v-model.trim="requiredEvent.bookingEmail"
                @focusin="animationField.bookingEmail = true"
                @focusout="inputAnimation('bookingEmail')"
                required
              />
            </label>
            <!-- Event Category -->
            <label class="mb-4">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.eventCategoryId }"
              >
                Event Category
              </p>

              <select
                required
                name="category"
                id="category"
                @change="durationCategory()"
                v-model="requiredEvent.eventCategoryId"
                @focusin="animationField.eventCategoryId = true"
                @focusout="inputAnimation('eventCategoryId')"
                class="leading-tight"
              >
                <option disabled selected value></option>
                <option
                  v-for="(category, index) in categories"
                  :value="category.id"
                >
                  {{ category.eventCategoryName }}
                </option>
              </select>
            </label>
            <!-- Event Duration -->
            <label class="mb-4">
              <p
                class="label-txt"
                :class="{ 'label-active': requiredEvent.eventCategoryId != '' }"
              >
                Duration
              </p>
              <input
                id="duration"
                type="number"
                class="input p-2 border-2 rounded-xl w-full cursor-not-allowed disabled:bg-slate-100/30 disabled:text-slate-500"
                v-model.trim="requiredEvent.eventDuration"
                @focusin="animationField.eventDuration = true"
                @focusout="inputAnimation('eventDuration')"
                required
                disabled
              />
            </label>
            <!-- Event StartTime -->
            <label class="mb-4">
              <p class="label-txt label-active">Event StartTime</p>
              <div class="flex mt-0.5 gap-1 text-md">
                <input
                  required
                  @change="checkDate()"
                  type="datetime-local"
                  id="eventTime"
                  name="eventTime"
                  :min="minDate()"
                  v-model="requiredEvent.eventStartTime"
                />
              </div>
            </label>
            <!-- Event Note -->
            <label class="mb-4">
              <p
                class="label-txt"
                :class="{ 'label-active': animationField.eventNotes }"
              >
                Event Note
              </p>

              <textarea
                v-model="requiredEvent.eventNotes"
                maxlength="500"
                class="form-control block text-base font-normal"
                @focusin="animationField.eventNotes = true"
                @focusout="inputAnimation('eventNotes')"
              >
              </textarea>
            </label>

            <!-- Event StartTime -->
            <label class="mb-4">
              <p class="label-txt label-active">Event File</p>
              <div
                class="flex mt-0.5 gap-1 text-xs justify-center items-center field-input"
              >
                <input
                  type="file"
                  @change="onFileChange"
                  ref="fileupload"
                  class="mr-2"
                />
                <div @click="$refs.fileupload.value = null">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="32"
                    height="32"
                    fill="currentColor"
                    class="bi bi-x"
                    viewBox="0 0 16 16"
                  >
                    <path
                      d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"
                    />
                  </svg>
                </div>
              </div>
            </label>

            <div class="grid grid-cols-2 gap-2">
              <button
                class="bg-blue-700 text-md rounded-xl w-full text-white py-2 hover:scale-105 duration-300 mt-1 formInput"
                type="submit"
              >
                Submit
              </button>
              <button
                class="bg-blue-700 text-md rounded-xl w-full text-white py-2 hover:scale-105 duration-300 mt-1 formInput"
                @click="resetForm()"
              >
                Cancel
              </button>
            </div>
          </form>
        </div>
        <!-- End form Sign in -->
      </div>
    </div>
  </section>

  <!-- <div
    class="min-w-full flex justify-center pt-10 transition ease-in duration-700"
    :class="{ 'opacity-0': !fade, 'opacity-100': fade }"
  >
    <form
      class="w-full max-w-5xl bg-white grid grid-cols-2 divide-x rounded-xl"
      @submit="submitEvent()"
      onsubmit="return false;"
    >
      <div
        class="place-self-center w-full h-full rounded-l-xl bgimg bg-contain bg-repeat-x bg-center bg-blue-400"
      ></div>
      <div>
        <div name="bookingForm" class="px-5 pt-6 mb-4">
          <div class="mb-4">
            <label
              for="bname"
              class="block text-gray-700 text-lg font-bold mb-2"
              >Booking Name :
            </label>
            <input
              required
              type="text"
              id="bname"
              name="bname"
              v-model="bookingName"
              maxlength="100"
              placeholder="Enter your booking name . . ."
              class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-orange-600"
            />
          </div>
          <div class="mb-6">
            <label
              for="email"
              class="block text-gray-700 text-lg font-bold mb-2"
              >Booking Email :
            </label>
            <input
              required
              type="email"
              id="email"
              name="email"
              maxlength="100"
              pattern="[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$"
              v-model="bookingEmail"
              placeholder="username@example.com"
              class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-orange-600"
            />
          </div>
          <div class="mb-6">
            <label
              for="category"
              class="block text-gray-700 text-lg font-bold mb-2"
              >Event Category :
            </label>
            <select
              required
              name="category"
              id="category"
              @change="durationCategory()"
              v-model="eventCategoryName"
              class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline focus:border-orange-600"
            >
              <option disabled selected value></option>
              <option v-for="(category, index) in categories">
                {{ category.eventCategoryName }}
              </option>
            </select>
          </div>
          <div class="mb-6">
            <label
              for="duration"
              class="block text-gray-700 text-lg font-bold mb-2"
              >Booking Duration :
            </label>
            <input
              required
              type="text"
              id="duration"
              name="duration"
              v-model="duration"
              placeholder="Your event duration"
              class="shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight cursor-not-allowed disabled:bg-slate-200 disabled:text-slate-500 disabled:border-slate-200 disabled:shadow-none"
              disabled
            />
          </div>
          <div class="mb-6">
            <label
              for="notes"
              class="block text-gray-700 text-lg font-bold mb-2"
              >Booking Notes :
            </label>
            <textarea
              v-model="bookingNote"
              maxlength="500"
              placeholder="Enter your description . . ."
              class="form-control block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-gray-300 rounded transition ease-in-out focus:text-gray-700 focus:bg-white focus:border-orange-600 focus:outline-none"
            ></textarea>
          </div>
          <div class="mb-3">
            <label
              for="eventTime"
              class="block text-gray-700 text-lg font-bold mb-2"
              >Start Time Event :
            </label>
            <div class="flex px-10">
              <input
                required
                @change="checkDate()"
                type="date"
                id="eventDate"
                :min="minDate()"
                v-model="eventDate"
                class="focus:border-orange-600 shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline mr-8"
              />
              <input
                required
                @change="checkDate()"
                type="time"
                id="eventTime"
                name="eventTime"
                v-model="eventTime"
                class="focus:border-orange-600 shadow border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              />
            </div>
          </div>
          <button
            class="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white my-5 py-1 px-5 border border-blue-500 hover:border-transparent rounded mr-5"
            @click=""
          >
            Submit
          </button>
          <router-link :to="{ name: 'ListEvent' }"
            ><button
              class="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white my-5 py-1 px-5 border border-blue-500 hover:border-transparent rounded"
              @click=""
            >
              Cancel
            </button></router-link
          >
          <br />
        </div>
      </div>
    </form>
  </div> -->
</template>

<style scoped>
.file-input__input {
  width: 0.1px;
  height: 0.1px;
  opacity: 0;
  overflow: hidden;
  position: absolute;
  z-index: -1;
}

.file-input__label {
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 600;
  color: #fff;
  font-size: 14px;
  padding: 10px 12px;
  background-color: #3e68ff;
  box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.25);
}

.file-input__label svg {
  height: 16px;
  margin-right: 4px;
}

.header {
  color: rgb(120, 120, 120);
}

label {
  display: block;
  position: relative;
}

input[type='text'],
input[type='number'],
input[type='email'],
input[type='date'],
input[type='time'],
input[type='datetime-local'],
select,
textarea,
.field-input {
  width: 100%;
  padding: 10px;
  padding-left: 18px;
  background: transparent;
  border: none;
  outline: none;
  border: 1px solid #ccc;
  border-radius: 15px;
}

input:focus + .line-box .line {
  width: 100%;
}

.label-txt {
  position: absolute;
  top: 0.6em;
  left: 20px;
  padding: 1px;
  letter-spacing: 1px;
  color: rgb(120, 120, 120);
  transition: ease 0.3s;
}

.label-active {
  background: white;
  top: -0.8em;
  font-size: 0.8em;
}
</style>
