<script setup>
import axios from "axios";
import {onBeforeMount, onMounted, ref} from "vue";
import {eventBus} from "@/eventBus.js";


const listOfCoordinates = ref();
const weatherDataList = ref([]);

// const drawer = ref(false);



const props = defineProps({
  currentUser : {
    type: String,
    required: true
  },
  isFahrenheit : {
    type: Boolean,
    required: true
  }
})



const fetchAllWeatherData = async () => {
  try {
    const response = await axios.get(`http://localhost:9191/users/saved-locations/${props.currentUser}`)

    console.log(response)

    listOfCoordinates.value = response.data;

    weatherDataList.value = [];

    console.log(listOfCoordinates.value);

    for(let i = 0; i < listOfCoordinates.value.length ; i++) {
      await fetchWeatherData(listOfCoordinates.value[i]);
    }

  } catch (error) {
     alert("Something went wrong in fetchAllWeatherData");
     console.error(error);
  }
}

const fetchWeatherData = async(coordinate) => {
  try {

    const response = await axios.post(`http://localhost:9191/weather/${coordinate.latitude}/${coordinate.longitude}`)
    const weatherActivity = await axios.get(`http://localhost:9191/weather/activity/${response.data.weather}`)

     weatherDataList.value.push({
       locationName: response.data.locationName,
       temperatureF: response.data.temperatureF,
       temperatureC: response.data.temperatureC,
       weatherDescription: response.data.weather,
       image: response.data.image,
       latitude: coordinate.latitude,
       longitude: coordinate.longitude,
       weatherActivity: weatherActivity.data,
       drawer: ref(false)
     })

  } catch (error) {
    alert("Something went wrong in fetchWeather");
    console.error(error);
  }
}



//catch emited event
onMounted(() => {
  eventBus.on('location-added', fetchAllWeatherData);
});

onBeforeMount(() =>{
  fetchAllWeatherData();
})

const deleteMovie = async (latitude, longitude) => {
  try {

    console.log(latitude)
    console.log(longitude)

    await axios.delete(`http://localhost:9191/users/delete-location/${props.currentUser}/${latitude}/${longitude}`);
    await fetchAllWeatherData();

  }catch(error){
    alert("Something went wrong");
  }

}

</script>

<template>

  <div class="whole-thing-wrapped"></div>

  <div class="all-weather-items" v-for="weather in weatherDataList">

    <div class="weather-item-wrapper">
      <h1 class="location">{{weather.locationName}}</h1>
      <button class="delete-button" @click="deleteMovie(weather.latitude,weather.longitude)">-</button>
      <button class="activity-button" @click="weather.drawer = ! weather.drawer">?</button>


      <div class="image-temp-wrapper">

        <div class="image-and-description">
          <img class="image" :src="`https://${weather.image}`">
          <h3 class="description">{{weather.weatherDescription}}</h3>
        </div>
        <div class="fahrenheit-or-celsius">
          <div v-if="props.isFahrenheit">
            <h2 class="temp">{{weather.temperatureF}}°F</h2>
          </div>
          <div v-else>
            <h2 class="temp">{{weather.temperatureC}}°C</h2>
          </div>
        </div>

      </div>



    </div>
    <transition name="slide">
      <div v-if="weather.drawer" class="activity-box">
        <h1>{{weather.weatherActivity}}</h1>
      </div>
    </transition>
   </div>




</template>

<style scoped>
.whole-thing-wrapped{
  margin-top: 100px;

}

.slide-enter-from {
  opacity: 0;
  transform: translateX(0);
}

/* While entering */
.slide-enter-active {
  transition: all 0.3s ease-in-out;
}

/* After entering */
.slide-enter-to {
  opacity: 1;
  transform: translateX(100px); /* Adjust the distance as needed */
}

/* Before the element leaves */
.slide-leave-from {
  opacity: 1;
  transform: translateX(100px);
}

/* While leaving */
.slide-leave-active {
  transition: all 0.3s ease-in-out;
}

/* After leaving */
.slide-leave-to {
  opacity: 0;
  transform: translateX(0);
}

/* Style the activity box */
.activity-box {
  position: absolute;
  background-color: #040457;
  color: white;
  padding: 10px;
  border-radius: 8px;
  margin-top: 20px;
}

.delete-button{
  background-color: #cc3838;
  border-radius: 100%;
  width: 50px;
  height: 50px;
  font-size: 2rem;
  color: white;
  display: flex; /* Enables flexbox for centering */
  align-items: center; /* Centers text vertically */
  justify-content: center; /* Centers text horizontally */
  text-align: center; /* Ensures text alignment */
  border: none;
  position: relative;
  top: 50%;
  left: 105%;
}

.activity-button{
  background-color: #040457;
  border-radius: 100%;
  width: 50px;
  height: 50px;
  font-size: 1.5rem;
  color: white;
  display: flex; /* Enables flexbox for centering */
  align-items: center; /* Centers text vertically */
  justify-content: center; /* Centers text horizontally */
  text-align: center; /* Ensures text alignment */
  border: none;
  position: relative;
  left: 105%;
  margin-top: 5px;
}

.all-weather-items{
  width: 110%;
  border: solid #cad7dc 2px;
  border-radius: 10px;
  margin-bottom: 20px;
  padding:35px;
  background-color: #cad7dc;
  color: white;
}

.image-and-description{
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;

}

.weather-item-wrapper{
  display: flex;
  position: relative;
  flex-direction: column;
  //align-items: center;
  //text-align: center;
  //max-width: 400px;
  width:600px;
  //margin: auto ;
}

.location{
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.image-temp-wrapper{
  margin-top: -100px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 50px;
  margin-right: 50px;
}

.temp{
  margin-top: -1.7rem;
  font-size: 4rem;
  color: white;
}

.image{
  width: 100px;
  height: 100px;
}


.description{
  margin-top: -0.5rem;
  font-size: 1.5rem;
  //text-align: center;
}


</style>
