<script>
import { ref } from "vue";
import 'primevue/resources/themes/lara-light-purple/theme.css' // import the dark-blue theme
import 'primeicons/primeicons.css';
import "/node_modules/primeflex/primeflex.css"
import EventForm from "@/components/EventForm.vue";
import Timetable from "@/components/Timetable.vue";
import Login from "@/components/Login.vue";
//import Toast from 'primevue/toast';

/**
 * add toasts - optional
 * authentication bearer
 * adding files
 * updating events
 * google bard or bing chat for uploading files
 */

export default {

  name:"App",

  components:{
    EventForm,
    Timetable,
    Login
  },

  data(){
    return {
      isLoggedIn: false,
    }
  },
  methods:{
    triggerChildMethod(){
      // Access the child component instance using the ref
      const childComponentInstance = this.$refs.childComponentRef;

      // Call the method in the child component
      childComponentInstance.fetchAllEvents();
    },
    logIn(){
      this.isLoggedIn = true;
    }
  }
}
</script>

<template>
  <header>
    <div class="wrapper">
      <Login v-if="!isLoggedIn" @login="logIn" ></Login>
      <event-form v-if="isLoggedIn" @someEvent="triggerChildMethod"></event-form>

    </div>
  </header>

  <main>
    <Timetable :isLoggedInProp="isLoggedIn" ref="childComponentRef"></Timetable>
  </main>
</template>

<style lang="scss" scoped>

.fade-in-enter-active,
.fade-out-leave-active {
  transition: opacity 1.0s ease;
}

.fade-in-enter-from,
.fade-out-leave-to {
  opacity: 0;
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
