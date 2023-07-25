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

//write toasts


//to create event ids
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
      msg: '',
      time:null,
      date: null,       // To store the selected date from the Calendar
      title: '',        // To store the title from the InputText
      description: '',  // To store the description from the InputText
      presenter: ''
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
      // Prepare the data to be sent to the backend
      const eventData = {
        id:getRandomInt(1, 100),
        date: this.date,
        time: this.time,
        title: this.title,
        description: this.description,
        //presenter: this.presenter
        startTime: "2023-07-20T14:30:00",
        endTime: "2023-07-20T16:00:00"
      };

      // Replace '/api/events' with the appropriate backend API endpoint
      fetch("http://localhost:8080/api/events/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(eventData)
      })
          .then((response) => {
            if (response.ok) {
              // Handle success, e.g., show a success message
              console.log("Event data submitted successfully!");
              // Reset the form inputs
              this.date = null;
              this.time = null;
              this.title = '';
              this.description = '';
              this.presenter = '';
              this.fetchAllEvents();
            } else {
              // Handle errors, e.g., show an error message
              console.error("Error submitting event data.");
            }
          })
          .catch((error) => {
            console.error("Error submitting event data:", error);
          });

    },
    fetchAllEvents() {
      // Replace '/api/events' with the appropriate backend API endpoint
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
  // mounted() {
  //   fetch("/api/events/hello")
  //       .then((response) => response.text())
  //       .then((data) => {
  //         this.msg = data;
  //       });
}
</script>

<template>
  <!--h1 class="green">{{ msg }}</h1-->
  <header>
    <div class="wrapper">
      <form @submit.prevent="handleSubmit">
        <div class="inputs">
          <p class="font-size:20">Create an event</p>
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
      <Timeline :value="events"  layout= "vertical" align="alternate" class="customized-timeline">
        <template #marker="slotProps">
              <span class="flex w-2rem h-2rem align-items-center justify-content-center text-white border-circle z-1 shadow-1" :style="{ backgroundColor: slotProps.item.color }">
<!-- THIS IS THE ICON <i :class="slotProps.item.icon"></i>-->
              </span>
        </template>
        <template #content="slotProps">
          <Card class="custom-card-width">
            <template #title>
              {{ slotProps.item.title}}
              <Button
                  icon="pi pi-times"
                  class="p-button-rounded p-button-sm"
                  @click="deleteEvent(slotProps.item.id)"
              ></Button>
            </template>
            <template #subtitle>
              {{ slotProps.item.date}}
            </template>
            <template #content>
              <!--img v-if="slotProps.item.image" :src="`https://primefaces.org/cdn/primevue/images/product/${slotProps.item.image}`" :alt="slotProps.item.name" width="200" class="shadow-1" /-->
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
