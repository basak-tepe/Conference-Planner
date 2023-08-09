<template>
  <form @submit.prevent="handleSubmit">
    <div class="inputs">
      <p class="create-an-event-text">Create an event</p>
      <Calendar v-model="date" placeholder="Date"  showIcon />
      <Calendar id="calendar-timeonly" placeholder="Time" v-model="time" timeOnly />
      <Dropdown showClear v-model="selectedLocation" editable :options="locations" optionLabel="name" optionValue="value" placeholder="Select a location"/>

      <div class="flex flex-wrap gap-3">
        <div class="flex align-items-center">
          <RadioButton v-model="selectedEventType" inputId="ingredient1" name="selectedEventType" value="Meeting" />
          <label for="ingredient1" class="ml-2">Meeting</label>
        </div>
        <div class="flex align-items-center">
          <RadioButton v-model="selectedEventType" inputId="ingredient2" name="selectedEventType" value="Conference" />
          <label for="ingredient2" class="ml-2">Conference</label>
        </div>
        <div class="flex align-items-center">
          <RadioButton v-model="selectedEventType" inputId="ingredient3" name="selectedEventType" value="Activity" />
          <label for="ingredient3" class="ml-2">Activity</label>
        </div>
      </div>


      <InputText v-model="title" type="text" placeholder="Title" />
      <InputText v-model="description" type="text" placeholder="Description"/>
      <InputText v-model="presenter" type="text" placeholder="Presenter" />
      <label for="file-upload" class="custom-file-upload">
        {{ selectedFileName || 'Click to select file' }}
        <i class="pi pi-arrow-circle-up"></i>
      </label>
      <input id="file-upload" class="fileButton" type="file" ref="fileInput" @change="handleFileChange" accept="image/*" multiple />
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
import RadioButton from 'primevue/radiobutton';
import Dropdown from "primevue/dropdown";
import { useLocalStorageStore } from '../storage'
import axios from "axios";

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
    FileUpload,
    Dropdown,
    RadioButton
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
      selectedFiles: [],
      selectedFileName: '',
      selectedLocation: null,
      selectedEventType: null,

      locations: [ // Define the locations list as an array of objects
        { name: "Online", value: "Online" },
        { name: "At the office",value: "At the office"  },
        { name: "At the conference room",value: "At the conference room"  },
        // Add more locations as needed
      ]
    }
  },

  methods:{
    clearFileInput() {
      this.$refs.fileInput.value = ''; // Clear the file input value
      this.selectedFileName = ''; // Reset the selected file name
    },

    onAdvancedUpload(event) {
      this.handleFileChange(event);
    },
    handleFileChange(event) {
      this.selectedFiles = event.target.files;
      // Update the selectedFileName with the name of the first selected file
      if (this.selectedFiles.length > 0) {
        this.selectedFileName = this.selectedFiles[0].name;
      } else {
        this.selectedFileName = ''; // No file selected, reset the name
      }
      // Store the selected files in the selectedFiles data property
      this.selectedFiles = event.target.files;
    },

    async handleSubmit() {

      // Create a FormData object to store the files and other form data
      const formData = new FormData();

      // Append the selected files to the FormData object
      for (let i = 0; i < this.selectedFiles.length; i++) {
        const file = this.selectedFiles[i];
        formData.append('file', file);
      }

      // Make a POST request to your backend API to upload the files
      const url = 'http://localhost:8080/api/events/files/add';
      axios.post(url, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
          .then(response => {
            // Handle the server response if needed
            console.log('Files uploaded successfully!');
          })
          .catch(error => {
            // Handle the error if the upload fails
            console.error('File upload failed:', error);
          });




      //continue by submitting the event as well
      //prepare id
      const randomId = getRandomInt(1, 100);

      // Parse the date and time strings to JavaScript Date objects
      const dateObject = new Date(this.date);
      const timeObject = new Date(this.time);

      // Extract the date components
      const year = dateObject.getFullYear();
      const month = dateObject.getMonth();
      const day = dateObject.getDate();

      // Extract the time components
      const hours = timeObject.getHours();
      const minutes = timeObject.getMinutes();
      const seconds = timeObject.getSeconds();

      // Create a new Date object with the extracted date and time components
      const startTimeObject = new Date(year, month, day, hours, minutes, seconds);

      // Convert the startTimeObject to ISO string format

      console.log(startTimeObject.toISOString()); // Output: "2023-07-02T11:07:19.000Z"



      //human-readable format for displaying
      //Format the date and time components into "dd.mm.yyyy hh:mm" format
      this.displayedDateTime = `${day.toString().padStart(2, '0')}.${(month+1).toString().padStart(2, '0')}.${year} ${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;

      console.log(this.displayedDateTime); // Output: "20.07.2023 14:30"


      // Prepare the data to be sent to the backend
      const eventData = {
        id: randomId,
        date: this.date,
        time: this.time,
        title: this.title,
        description: this.description,
        presenter: this.presenter,
        startTime: startTimeObject.toISOString(),
        displayTime: this.displayedDateTime,
        fileName: this.selectedFileName,
        location : this.selectedLocation,
        eventType: this.selectedEventType,
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
              this.selectedFiles = [];
              this.selectedFileName = '';
              this.selectedLocation = null;
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


.fileButton {
  display: inline-block;
  padding: 8px 20px;
  background-color: #b89cf9;
  color: #fff;
  border-radius: 4px;
  cursor: pointer;
}
input[type="file"] {
  display: none;
}

.custom-file-upload {
  display: inline-block;
  padding: 8px 20px;
  background-color: #b89cf9;
  color: #fff;
  border-radius: 4px;
  cursor: pointer;
  text-align: center; /* Center the text */
  font-weight: bold; /* Make the text bold */
  font-size: 16px; /* Set the font size to 16px*/

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
