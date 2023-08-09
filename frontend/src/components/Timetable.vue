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
import {useLocalStorageStore} from "@/storage";

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

    getEventCardClass(eventType) {
      if (eventType === "Conference") {
        return "event-type-a";
      } else if (eventType === "Meeting") {
        return "event-type-b";
      } else if (eventType === "Activity") {
        return "event-type-c";
      } else {
        return ""; // Default class if no match
      }
    },
    getEventIconClass(eventType) {
      if (eventType === "Conference") {
        return "pi pi-comments";
      } else if (eventType === "Meeting") {
        return "pi pi-users";
      } else if (eventType === "Activity") {
        return "pi pi-th-large";
      } else {
        return ""; // Default class if no match
      }
    },

    async deleteEvent(eventId) {
      //fetch pinia
      const store = useLocalStorageStore();
      const username = store.getUsername();
      const password = store.getPassword();



      const credentials = `${username}:${password}`;
      const encodedCredentials = btoa(credentials);
      try {
        const response = await fetch(`http://localhost:8080/api/events/event/${eventId}`, {
          method: "DELETE",
          headers: {
            Authorization: `Basic ${encodedCredentials}`,
          }
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
  <div class="scrollable-container">
  <div class="card">
    <Timeline :value="events.reverse()"  layout= "vertical" align="alternate" class="customized-timeline">
      <template #marker="slotProps">
              <span :class="getEventCardClass(slotProps.item.eventType)"   class="flex w-2rem h-2rem align-items-center justify-content-center text-white border-circle z-1 shadow-1">
                <i  :class="getEventIconClass(slotProps.item.eventType)"></i>
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
            {{slotProps.item.eventType}}
            <br>
            {{slotProps.item.presenter}}
            {{slotProps.item.displayTime}}
            <br>
            <div class="location-info">
            <div v-if="slotProps.item.location!=null">
            <i v-if="slotProps.item.location.toString().trim().startsWith('Online')" class="pi pi-globe"></i>
            <i v-else-if="slotProps.item.location.toString().trim().startsWith('At the office')" class="pi pi-briefcase"></i>
            <i v-else-if="slotProps.item.location.toString().trim().startsWith('At the conference room')" class="pi pi-building"></i>
              <i v-else-if="slotProps.item.location.toString().trim() !== ''" class="pi pi-map-marker"></i>
            </div>
              {{ slotProps.item.location}}</div>
          </template>
          <template #content >
            <p>
              {{ slotProps.item.description}}
            </p>
            <br>
            <div v-if="slotProps.item.fileName" class="download-group">
              <i class="pi pi-link"></i>
            <a  :href="'http://localhost:8080/api/events/files/download/' + slotProps.item.fileName" download>Download File</a>
            </div>
          </template>
        </Card>
      </template>
    </Timeline>
  </div>
  </div>
</template>

<style lang="scss" scoped>
.event-type-a{
  background-color: rgba(145, 170, 250);
}
.event-type-b{
  background-color: rgb(174, 246, 86);
}
.event-type-c{
  background-color: rgb(250, 185, 7);
}


.scrollable-container {
  padding:40px;
  height: 700px;
  overflow: auto;
}

.download-group * {
  color: #8a5bf5;
  display: inline;
  padding:5px;
}

.custom-card-width .p-card .p-card-title{
  display: flex;
}
.location-info *{
  display: inline;
  padding:3px;
}
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

    .p-card .p-card-title{
      font-size: 1.5rem;
      font-weight: 700;
      display: flex;
      flex-wrap: wrap;
      margin-bottom: 0.5rem;
      justify-content: space-between;
    }


    .p-card .p-card-title *{
      display: flex;
      flex-wrap:wrap;
    }

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
    .p-card .p-card-title{
      font-size: 1.5rem;
      font-weight: 700;
      display: flex;
      flex-wrap: wrap;
      margin-bottom: 0.5rem;
      justify-content: space-between;
      align-items: baseline;
    }

    .p-card .p-card-title *{
      display: flex;
      flex-wrap:wrap;
    }
    .p-timeline-event:nth-child(even) {
      width:700px;
      height:210px;

      .p-timeline-event-content {

      }
    }
  }
}

</style>
