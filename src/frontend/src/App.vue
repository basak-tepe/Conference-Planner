<script>
import TheWelcome from './components/TheWelcome.vue'
import { ref } from "vue";
import Calendar from 'primevue/calendar';
import InputText from 'primevue/inputtext';
import 'primevue/resources/themes/lara-light-purple/theme.css' // import the dark-blue theme
import Button from 'primevue/button';
import 'primeicons/primeicons.css';
import Timeline from 'primevue/timeline';
import  Card  from 'primevue/card';
import 'primeicons/primeicons.css';
import "/node_modules/primeflex/primeflex.css"
//import Toast from 'primevue/toast';

/**
 * add toasts - optional
 * divide this file into components
 * authentication bearer
 */

//creating random event ID's
function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

export default {

  name:"App",

  components:{
    TheWelcome,
    Calendar,
    InputText,
    Button,
    Card,
    Timeline
  },


  data() {
    return {
      events: [],
      time:null,
      date: null,
      title: '',
      description: '',
      presenter: '',
      startTime: null,
      displayedDateTime: '',
    }
  },

  methods:{
    async deleteEvent(eventId) {
      try {
        const response = await fetch(`http://localhost:8080/api/events/event/${eventId}`, {
          method: "DELETE",
        });
        if (response.ok) {
          // Event deleted successfully on the server, now update the frontend
          this.events = this.events.filter((event) => event.id !== eventId);
          console.log(`Event with ID ${eventId} deleted successfully.`);
        } else {
          console.error(`Error deleting event with ID ${eventId}.`);
        }
      } catch (error) {
        console.error("Error deleting event:", error);
      }
    },
    handleSubmit() {
      // Parse the date and time strings to JavaScript Date objects
      const dateObject = new Date(this.date);
      const timeObject = new Date(this.time);

      // Extract the date components
      const year = dateObject.getFullYear();
      const month = dateObject.getMonth(); //+1?
      const day = dateObject.getDate();

      // Extract the time components
      const hours = timeObject.getHours();
      const minutes = timeObject.getMinutes();
      const seconds = timeObject.getSeconds();

      // Create a new Date object with the extracted date and time components
      const startTimeObject = new Date(year, month - 1, day, hours, minutes, seconds);

      // Convert the startTimeObject to ISO string format

      console.log(startTimeObject.toISOString()); // Output: "2023-07-02T11:07:19.000Z"



      //human readable format for displaying
      //Format the date and time components into "dd.mm.yyyy hh:mm" format
      this.displayedDateTime = `${day.toString().padStart(2, '0')}.${month.toString().padStart(2, '0')}.${year} ${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;

      console.log(this.displayedDateTime); // Output: "20.07.2023 14:30"


      // Prepare the data to be sent to the backend
      const eventData = {
        id:getRandomInt(1, 100),
        date: this.date,
        time: this.time,
        title: this.title,
        description: this.description,
        presenter: this.presenter,
        startTime: startTimeObject.toISOString(),
        displayTime: this.displayedDateTime,
      };


      fetch("http://localhost:8080/api/events/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(eventData)
      })
          .then((response) => {
            if (response.ok) {

              console.log("Event data submitted successfully!");
              // Reset the form inputs
              this.date = null;
              this.time = null;
              this.title = '';
              this.description = '';
              this.presenter = '';
              this.displayedDateTime ='';
              this.fetchAllEvents();
            } else {
              console.error("Error submitting event data.");
            }
          })
          .catch((error) => {
            console.error("Error submitting event data:", error);
          });

    },
    fetchAllEvents() {
      fetch("http://localhost:8080/api/events/events")
          .then((response) => response.json())
          .then((data) => {
            this.events = data; // Update the events data with the fetched data
          })
          .catch((error) => {
            console.error("Error fetching event data:", error);
          });
    },
  },

  mounted() {
    this.fetchAllEvents();
  }
}
</script>

<template>
  <header>
    <div class="wrapper">
      <form @submit.prevent="handleSubmit">
        <div class="inputs">
          <p class="create-an-event-text">Create an event</p>
          <Calendar v-model="date" placeholder="Date"  showIcon />
          <Calendar id="calendar-timeonly" placeholder="Time" v-model="time" timeOnly />
          <InputText v-model="title" type="text" placeholder="Title" />
          <InputText v-model="description" type="text" placeholder="Description"/>
          <InputText v-model="presenter" type="text" placeholder="Presenter" />
          <Button label="Submit" type="submit" icon="pi pi-check" iconPos="right" />
        </div>
      </form>
    </div>

  </header>
  <main>
    <!--    <div class="card flex justify-content-center">-->
    <!--      <Toast />-->
    <!--      <Button label="Show" @click="show()" />-->
    <!--    </div>-->
    <div class="card">
      <Timeline :value="events.reverse()"  layout= "vertical" align="alternate" class="customized-timeline">
        <template #marker="slotProps">
              <span class="flex w-2rem h-2rem align-items-center justify-content-center text-white border-circle z-1 shadow-1" :style="{ backgroundColor: slotProps.item.color }">
              </span>
        </template>
        <template #content="slotProps">
          <Card class="custom-card-width">
            <template #title>
              {{ slotProps.item.title}}
              <Button
                  icon="pi pi-times"
                  class="p-button-rounded p-button-xs"
                  text rounded
                  @click="deleteEvent(slotProps.item.id)"
              ></Button>
            </template>
            <template #subtitle>
              {{slotProps.item.presenter}},
              {{slotProps.item.displayTime}}
            </template>
            <template #content>
              <p>
                {{ slotProps.item.description}}
              </p>
              <Button label="Read more" text></Button>
            </template>
          </Card>
        </template>
      </Timeline>
    </div>
  </main>
</template>

<style lang="scss" scoped>


/*text*/
  .create-an-event-text{
    font-size: 30px;
    color: #6140af;
    letter-spacing: -0.8px;
    word-spacing: 6px;
    font-weight: 600;
    text-decoration: none solid rgb(68, 68, 68);
    font-style: normal;
    font-variant: normal;
    text-transform: none;
  }


/*small screens*/
@media (max-width: 1024px) {

  header {
    line-height: 1.5;
  }
  .inputs {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
  }
  .inputs * {
    flex: 1 1 100%;
  }

  main {
    margin: 80px;
  }

  ::v-deep(.customized-timeline) {
    .p-timeline-event:nth-child(even) {
      flex-direction: row !important;

      .p-timeline-event-content {
        text-align: left !important;
      }
    }

    .p-timeline-event-opposite {
      flex: 0;
    }

    .p-card {
      margin-top: 1rem;
    }
  }
}

/**large screens*/
@media (min-width: 1024px) {


  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  .inputs {
    display: flex;
    flex-direction: column;
    padding-bottom: 30px;
    gap: 25px;
    margin:10px;
  }

  ::v-deep(.customized-timeline) {
    .p-timeline-event:nth-child(even) {
      width:600px;
      height:210px;

      .p-timeline-event-content {

      }
    }

  }

}

</style>
