<script setup>
import HelloWorld from './components/HelloWorld.vue'
import TheWelcome from './components/TheWelcome.vue'
import Calendar from 'primevue/calendar';
import InputText from 'primevue/inputtext';
import 'primevue/resources/themes/lara-light-purple/theme.css'; // import the dark-blue theme

</script>

<template>
  <header>
    <div class="wrapper">
      <div class="inputs">
        <p class="font-size:20">Create an event</p>
        <Calendar v-model="date" showIcon />
      <InputText v-model="value2" type="text" placeholder="Title" />
      <InputText v-model="value2" type="text" placeholder="Description"/>
      <InputText v-model="value2" type="text" placeholder="Presenter" />
      </div>
    </div>
  </header>

  <main>
    <TheWelcome />
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
