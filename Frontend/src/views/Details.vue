<script>
import EventDataService from '../services/EventDataService.js';
import FileDataService from '../services/FileDataService';
export default {
  name: 'Event-Detail',
  data() {
    return {
      event: {
        id: null,
        bookingName: null,
        bookingEmail: null,
      },
      updateEvent: {
        date: null,
        time: null,
        eventNotes: null,
        eventFile: null,
      },
      dataFile: null,
      nameFile: null,
      editMode: false,
    };
  },
  async beforeMount() {
    await this.getDetailEvent(this.$route.params.id);

    console.log(this.event);
  },
  methods: {
    async getFile() {
      const res = await FileDataService.retrieveFile(
        this.event.id,
        this.event.eventFile
      );

      if (res.status == 200) {
        this.dataFile = new Blob([await res.blob()]);
        // console.log(this.dataFile);
        // console.log('ddasdasd');
      }
    },
    async getDetailEvent(id) {
      const res = await EventDataService.retrieveEvent(id);
      if (res.status == 200) {
        this.event = await res.json();
        if (this.event.eventFile != '') {
          this.nameFile = this.event.eventFile;
          await this.getFile();
        }
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Error to delete event!',
        });
        this.$router.push({ name: 'ListEvent' });
      }
      // console.log(this.event);
    },
    async deleteEvent(id) {
      const res = await EventDataService.deleteEvent(id);
      if (res.status === 200) {
        this.$swal.fire('Saved!', '', 'success');
        this.$router.push({ name: 'ListEvent' });
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
    formatTime(dateTime) {
      var date = new Date(dateTime).toLocaleString('th-TH');
      return date.slice(-8, -3);
    },
    formatDate(date) {
      var date = new Date(date);
      return date;
    },
    minDate() {
      var today = new Date();
      var dd = String(today.getDate()).padStart(2, '0');
      var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
      var yyyy = today.getFullYear();
      today = yyyy + '-' + mm + '-' + dd;
      return today;
    },
    editModeOn() {
      this.editMode = true;
      var inputDate = this.event.eventStartTime.slice(0, 10);
      var inputTime = this.event.eventStartTime.slice(-18, -13);
      this.updateEvent.date = inputDate;
      this.updateEvent.time = inputTime;
      this.updateEvent.eventNotes = this.event.eventNotes;
    },
    downloadFile() {
      // FileDataService.downloadFile(this.event.id, this.event.eventFile);
      var url = window.URL.createObjectURL(this.dataFile);
      var a = document.createElement('a');
      a.href = url;
      a.download = this.event.eventFile;
      document.body.appendChild(a);
      a.click();
      a.remove();
    },
    async submitForm() {
      this.$swal.fire({
        title: 'Auto close alert!',
        html: 'I will close in <b></b> milliseconds.',
        timer: 4000,
        timerProgressBar: true,
      });

      const res = await EventDataService.updateEvent(
        this.event.id,
        this.updateEvent,
        this.dataFile
      );
      if (res.status === 200) {
        this.$swal.fire({
          icon: 'success',
          title: 'Success',
          text: 'Event created!',
        });
        this.editMode = false;
        this.getDetailEvent(this.event.id);
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
          title: 'Please check your event data. Press OK for edit.',
          showCancelButton: true,
          confirmButtonText: 'Yes',
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.submitForm();
          }
        });
    },
    onFileChange(e) {
      var files = e.target.files || e.dataTransfer.files;
      if (!files.length) return;
      // console.log(files[0]);
      this.dataFile = files[0];
      // this.createImage(files[0]);
      this.updateEvent.eventFile = files[0].name;
    },
    deleteFile() {
      this.event.eventFile = '';
      this.dataFile = null;
      this.updateEvent.eventFile = '';
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
              :to="{ name: 'ListEvent' }"
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
        <div class="mt-6 text-base md:text-xl relative">
          <div class="border-b border-gray-400">
            <h1>Name</h1>
            <p class="text-gray-400 break-words text-base">
              {{ event.bookingName }}
            </p>
          </div>
          <div class="border-b border-gray-400 pt-4">
            <h1>Email</h1>
            <p class="text-gray-400 break-words text-base">
              {{ event.bookingEmail }}
            </p>
          </div>
          <div class="border-b border-gray-400 pt-4">
            <h1>Category</h1>
            <p class="text-gray-400 break-words text-base">
              {{ event.eventCategoryEventCategoryName }}
            </p>
          </div>
          <div class="border-b border-gray-400 pt-4">
            <h1>Duration</h1>
            <p class="text-gray-400 break-words text-base">
              {{ event.eventDuration }}
            </p>
          </div>

          <div class="border-b border-gray-400 pt-4 pb-1">
            <h1>Date</h1>
            <p class="text-gray-400 break-words text-base p-2" v-if="!editMode">
              {{ formatDate(event.eventStartTime).getDate() }} /
              {{
                formatDate(event.eventStartTime).toLocaleString('en-US', {
                  month: 'short',
                })
              }}
              /
              {{ formatDate(event.eventStartTime).getFullYear() }}
            </p>
            <div v-else>
              <input
                required
                type="date"
                id="eventDate"
                @change="checkDate()"
                :min="minDate()"
                v-model="updateEvent.date"
                class="text-base p-2 border rounded-lg shadow"
              />
            </div>
          </div>
          <div class="border-b border-gray-400 pt-4 pb-1">
            <h1>Time</h1>
            <p class="text-gray-400 break-words text-base p-2" v-if="!editMode">
              {{ formatTime(event.eventStartTime) }}
            </p>
            <div v-else>
              <input
                required
                type="time"
                id="eventTime"
                @change="checkDate()"
                v-model="updateEvent.time"
                class="text-base p-2 border rounded-lg shadow"
              />
            </div>
          </div>
          <div class="border-b border-gray-400 pt-4">
            <h1>Note</h1>
            <p
              class="text-gray-400 break-words text-xs p-4 pb-6"
              v-if="!editMode"
            >
              {{ event.eventNotes != '' ? event.eventNotes : 'No message' }}
            </p>
            <div v-else class="text-xs">
              <textarea
                v-model="updateEvent.eventNotes"
                maxlength="500"
                rows="2"
                class="shadow appearance-none border rounded-lg w-full my-1 py-1 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              ></textarea>
            </div>
          </div>
          <div class="pt-4">
            <h1>Attachment</h1>
            <div v-if="!editMode">
              <p
                class="text-gray-400 break-words text-base p-4"
                v-if="event.eventFile == ''"
              >
                {{ '-' }}
              </p>
              <p
                v-else
                class="break-words text-base p-4 underline underline-offset-2 text-blue-500 cursor-pointer"
                @click="downloadFile()"
              >
                {{ event.eventFile }}
              </p>
            </div>
            <div v-else>
              <div class="flex items-center" v-if="event.eventFile != ''">
                <p
                  class="break-words text-base p-4 underline underline-offset-2 text-blue-500 cursor-pointer"
                  @click="downloadFile()"
                >
                  {{ event.eventFile }}
                </p>
                <!-- <button
                  class="flex items-center transition ease-in-out hover:scale-110 duration-300 p-1 bg-blue-500 rounded-lg mr-3"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    fill="currentColor"
                    class="bi bi-download fill-white"
                    viewBox="0 0 16 16"
                  >
                    <path
                      d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"
                    />
                    <path
                      d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"
                    />
                  </svg>
                </button> -->
                <button
                  class="flex items-center transition ease-in-out hover:scale-110 duration-300 p-1 bg-red-500 rounded-lg"
                  @click="deleteFile()"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    fill="currentColor"
                    class="bi bi-x fill-white"
                    viewBox="0 0 16 16"
                  >
                    <path
                      d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"
                    />
                  </svg>
                </button>
              </div>

              <input
                class="form-control block text-base font-normal text-gray-700 bg-white p-4"
                type="file"
                id="formFile"
                @change="onFileChange"
                v-else
              />
            </div>
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
              @click="confirmEvent()"
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
              @click="
                editMode = false;
                event.eventFile = nameFile;
              "
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
              @click="confirmDeleteEvent(event.id)"
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
      </div>
    </div>
  </section>
</template>

<style scoped></style>
