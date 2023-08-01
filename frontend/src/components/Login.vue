<script>
import InputText from 'primevue/inputtext';
import 'primevue/resources/themes/lara-light-purple/theme.css' // import the dark-blue theme
import Button from 'primevue/button';
import 'primeicons/primeicons.css';
import "/node_modules/primeflex/primeflex.css"
import { useLocalStorageStore } from '../storage'

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },

  components: {
    InputText,
    Button,
  },
  methods: {
    login() {
      // /**
      //  * temporary log in logic
      //  */
      //
      // this.$emit('login');
      //
      // // Perform login logic here
      // // You can call your API to authenticate the user and obtain the JWT token
      // // Handle login success and redirect to a protected route
      // //this.$router.push({ name: 'dashboard' }); // Replace 'dashboard' with your protected route name


      // Make an API call to the backend to login
      const {username, password} = this;
      const url = "http://localhost:8080/api/events/login";
      const credentials = `${username}:${password}`;
      const encodedCredentials = btoa(credentials);
      const headers = {
        Authorization: `Basic ${encodedCredentials}`,
      };
      fetch(url, {
        method: "POST",
        headers,
      })
          .then((response) => {
            response.json()
            if (response.status === 200) {
              // The login was successful, redirect to the next page
              this.$emit('login');

              //store the data
              const store = useLocalStorageStore()
              store.setUsername(username);
              store.setPassword(password);

            } else {
              // The login failed, show an alert
              alert("Credentials are wrong!");
            }
          });
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
.log-in-text {
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
    margin: 10px;
  }
}
</style>
