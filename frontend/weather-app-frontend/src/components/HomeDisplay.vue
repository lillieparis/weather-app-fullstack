<script setup>
import WeatherItem from "@/components/WeatherItem.vue";
import AddLocation from "@/components/AddLocation.vue";
import {ref} from "vue";

const props = defineProps({
  currentUser : {
    type: String,
    required: true
  }
})

const isFahrenheit = ref(true);
</script>

<template>

  <div class="header">
  <h1 class="your-weather-title">Your Weather</h1>

    <div class="toggle">
      <input type="checkbox" id="checkbox" v-model="isFahrenheit" />
      <label for="checkbox"> Fahrenheit</label>
    </div>

    <AddLocation class="add-location"
               :current-user="props.currentUser">
  </AddLocation>
  </div>

  <div class="weather-item">
  <WeatherItem
    :current-user="props.currentUser"
    :isFahrenheit="isFahrenheit"
  >

  </WeatherItem>
  </div>



</template>

<style scoped>
.toggle{
  position: relative;
  left: 33%;
  color: #040457;
  padding: 5px;
}

.header{
  display: flex;
  align-items: center; /* Align items vertically */
  margin-left: -110px;
  width: 100%;
  background-color: white;
  position: fixed;
  margin-bottom: 100px;
  border-bottom: 2px solid #ccc;
  z-index: 100;


}

.your-weather-title{
  padding-left: 20px;
  font-style: normal;
  color: #808586;
  font-size: 3rem;
}





</style>
