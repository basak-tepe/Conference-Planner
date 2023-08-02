<script>
import { ref } from "vue";
import 'primevue/resources/themes/lara-light-purple/theme.css' // import the dark-blue theme
import 'primeicons/primeicons.css';
import Timeline from 'primevue/timeline';
import  Card  from 'primevue/card';
import Button from "primevue/button";
import 'primeicons/primeicons.css';
import "/node_modules/primeflex/primeflex.css"
import * as events from "events";

export default {

  name:"Timetable",

  components:{
    Card,
    Timeline,
    Button
  },

  //listening to signals from the parent
  props: {
    isLoggedInProp: {
      type: Boolean,
      required: true,
    },
  },


  data() {
    return {
      events: [],
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

  watch(){
    events:{
      this.fetchAllEvents();
    }
  },
  mounted() {
    this.fetchAllEvents();
  }
}
</script>

<template>
  <div class="card">
    <Timeline :value="events.reverse()"  layout= "vertical" align="alternate" class="customized-timeline">
      <template #marker="slotProps">
              <span class="flex w-2rem h-2rem align-items-center justify-content-center text-white border-circle bg-indigo-100 z-1 shadow-1">
              </span>
      </template>
      <template #content="slotProps">
        <Card class="custom-card-width">
          <template #title>
            {{ slotProps.item.title}}
            <Button v-if="isLoggedInProp"
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
