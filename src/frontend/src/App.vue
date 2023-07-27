<script>
import { ref } from "vue";
import 'primevue/resources/themes/lara-light-purple/theme.css' // import the dark-blue theme
import 'primeicons/primeicons.css';
import 'primeicons/primeicons.css';
import "/node_modules/primeflex/primeflex.css"
import EventForm from "@/components/EventForm.vue";
import Timetable from "@/components/Timetable.vue";
//import Toast from 'primevue/toast';

/**
 * add toasts - optional
 * authentication bearer
 */

export default {

  name:"App",
  components:{
    EventForm,
    Timetable
  }
}
</script>

<template>
  <header>
    <div class="wrapper">
      <event-form></event-form>
    </div>
  </header>

  <main>
  <Timetable></Timetable>
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
}

</style>
