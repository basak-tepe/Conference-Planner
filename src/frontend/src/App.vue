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
 * adding files
 * updating events
 * if some is logged in, they should be able to add events
 * if not they should only see the existing ones and be able to download presentation files
 * google bard or bing chat for uploading files
 */

export default {

  name:"App",
  components:{
    EventForm,
    Timetable
  },
  methods:{
    triggerChildMethod(){
      // Access the child component instance using the ref
      const childComponentInstance = this.$refs.childComponentRef;

      // Call the method in the child component
      childComponentInstance.fetchAllEvents();
    }
  }
}
</script>

<template>
  <header>
    <div class="wrapper">
      <event-form @someEvent="triggerChildMethod"></event-form>
    </div>
  </header>

  <main>
    <Timetable ref="childComponentRef"></Timetable>
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
