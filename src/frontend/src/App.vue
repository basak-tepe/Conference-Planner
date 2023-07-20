<script>
import TheWelcome from './components/TheWelcome.vue'
import Calendar from 'primevue/calendar';
import InputText from 'primevue/inputtext';
import 'primevue/resources/themes/lara-light-purple/theme.css' // import the dark-blue theme
import Button from 'primevue/button';
import 'primeicons/primeicons.css';

export default {
  name:"App",
  components:{
    TheWelcome,
    Calendar,
    InputText,
    Button
  },
  data() {
    return {
      msg: '',
      date: null,       // To store the selected date from the Calendar
      title: '',        // To store the title from the InputText
      description: '',  // To store the description from the InputText
      presenter: ''
    }
  },

  methods:{
    handleSubmit() {
      // Prepare the data to be sent to the backend
      const eventData = {
        id:1000,
        date: this.date,
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
              this.title = '';
              this.description = '';
              this.presenter = '';
            } else {
              // Handle errors, e.g., show an error message
              console.error("Error submitting event data.");
            }
          })
          .catch((error) => {
            console.error("Error submitting event data:", error);
          });
    }
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
        <Calendar v-model="date" showIcon />
        <InputText v-model="title" type="text" placeholder="Title" />
        <InputText v-model="description" type="text" placeholder="Description"/>
        <InputText v-model="presenter" type="text" placeholder="Presenter" />
        <Button label="Submit" type="submit" icon="pi pi-check" iconPos="right" />
      </div>
      </form>
    </div>

  </header>

  <main>
    <TheWelcome/>
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  p {
    font-size: 200%;
  }

  .inputs{
    display: flex;
    flex-direction: column;
    padding-bottom: 30px;
    gap: 25px;
  }
}
</style>
