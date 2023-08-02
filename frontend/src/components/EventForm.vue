<template>
  <form @submit.prevent="handleSubmit">
    <div class="inputs">
      <p class="create-an-event-text">Create an event</p>
      <Calendar v-model="date" placeholder="Date"  showIcon />
      <Calendar id="calendar-timeonly" placeholder="Time" v-model="time" timeOnly />
      <InputText v-model="title" type="text" placeholder="Title" />
      <InputText v-model="description" type="text" placeholder="Description"/>
      <InputText v-model="presenter" type="text" placeholder="Presenter" />
      <FileUpload name="demo[]" url="./upload.php" @upload="onAdvancedUpload($event)" :multiple="true" accept="image/*" :maxFileSize="1000000">
        <template #empty>
          <p>Drag and drop files to here to upload.</p>
        </template>
      </FileUpload>
      <Button label="Submit" type="submit" icon="pi pi-check" iconPos="right" :disabled="!title"/>

    </div>
  </form>
</template>

<script>
import { ref } from "vue";
import InputText from 'primevue/inputtext';
import 'primevue/resources/themes/lara-light-purple/theme.css' // import the dark-blue theme
import Button from 'primevue/button';
import 'primeicons/primeicons.css';
import Calendar from 'primevue/calendar';
import "/node_modules/primeflex/primeflex.css"
import FileUpload from 'primevue/fileupload';
import { useLocalStorageStore } from '../storage'

//creating random event ID's
function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

export default {

  name:"EventForm",

  components:{
    Calendar,
    InputText,
    Button,
    FileUpload
  },


  data() {
    return {
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
    async handleSubmit() {

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



      //human-readable format for displaying
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


      //obtain username & password from local storage
      const store = useLocalStorageStore();
      const username = store.getUsername();
      const password = store.getPassword();


      console.log(username);
      console.log(password);

      const credentials = `${username}:${password}`;
      const encodedCredentials = btoa(credentials);

      await fetch("http://localhost:8080/api/events/add", {
        //mode: 'no-cors',
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Basic ${encodedCredentials}`,
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
              //send signal to parent
              this.$emit('someEvent');
            }

            else {
              console.error("Error submitting event data 1.");
            }
          })
          .catch((error) => {
            console.error("Error submitting event data2 :", error);
          });

    },
  },
}
</script>

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
}
</style>
