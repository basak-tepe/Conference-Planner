<script>
import { ref } from "vue";
import InputText from 'primevue/inputtext';
import 'primevue/resources/themes/lara-light-purple/theme.css' // import the dark-blue theme
import Button from 'primevue/button';
import 'primeicons/primeicons.css';
import "/node_modules/primeflex/primeflex.css"

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },

  components:{
    InputText,
    Button,
  },
  methods: {
    login() {
      /**
       * temporary log in logic
       */

      this.$emit('login');

      // Perform login logic here
      // You can call your API to authenticate the user and obtain the JWT token
      // Handle login success and redirect to a protected route
      //this.$router.push({ name: 'dashboard' }); // Replace 'dashboard' with your protected route name
    },
  },
};
</script>


<template>
            <form @submit.prevent="login">
              <div class="inputs">
              <p class="log-in-text">Log in to add an event</p>
                <InputText v-model="username" type="text" placeholder="Username" class="form-control" id="username" required/>
                <InputText v-model="password" type="password" placeholder="Username" class="form-control" id="password" required/>
              <Button label="Log in" type="submit" icon="pi pi-arrow-circle-right" iconPos="right"/>
              </div>
            </form>
</template>

<style lang="scss" scoped>


/*text*/
.log-in-text{
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