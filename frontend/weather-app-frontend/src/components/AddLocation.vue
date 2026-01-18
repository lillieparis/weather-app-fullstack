<script setup>
import {ref} from "vue";
import axios from "axios";
import {eventBus} from "@/eventBus.js";

const latitudeInput = ref()
const longitudeInput =ref()

const props = defineProps({
  currentUser : {
    type: String,
    required: true
  }
})


const addLocation =async () => {
  if (!latitudeInput.value || !longitudeInput.value ) {
    alert("You must enter a longitude and latitude to add a location")
  }
  else {

    try {
      const response = await axios.get(`http://localhost:9191/users/username/${props.currentUser}`);

      const requestBody = {
        username: response.data.username,
        password: response.data.password,
        savedCoordinates: [
          {
            latitude: parseFloat(latitudeInput.value),
            longitude: parseFloat(longitudeInput.value)
          }
        ]
      }
      await axios.post(`http://localhost:9191/users/add-location/${response.data.id}`, requestBody)

      //emit to sibling component using eventbus
      eventBus.emit('location-added')

      latitudeInput.value = "";
      longitudeInput.value ="";

    } catch (error) {
      alert("Something went wrong")
      console.error(error)
    }
  }

}

</script>

<template>
  <div class="search-bar-wrapper">
  <input class="input-latitude" v-model="latitudeInput" placeholder="Latitude">
  <input class="input-longitude" v-model="longitudeInput" placeholder="Longitude">

  <button class="add-button" @click="addLocation">Add Location</button>
  </div>

</template>

<style scoped>
.add-button{
  font-size: larger;
  padding:5px;
  border-radius: 10px;
  background-color: #040457;
  color: white;
  border-color: white;

}

.input-longitude{
  border-style: solid;
  border-radius: 10px;
  border-color: #787b7e;
  font-size: larger;
  padding: 5px;
  width: 200px;
  margin-right: 5px;
}

.input-latitude{
  font-size: larger;
  padding: 5px;
  border-style: solid;
  border-radius: 10px;
  border-color: #787b7e;
  width: 200px;
  margin-right: 5px;
}

.search-bar-wrapper{
  position: fixed;
  top: 20px;
  right: 20px;
}

</style>
