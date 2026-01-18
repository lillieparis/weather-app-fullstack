<script setup>
  import {ref} from "vue";
  import axios from "axios";

  const usernameInput = ref('')

  const passwordInput = ref('')

  const latInput = ref()

  const lonInput = ref()



  const emit = defineEmits(['login'])

  const validateAccount = async () => {
    //validates that the username is unique and password is longer than 8 chars

    //if yes, create account aka add username, password, lon, lat to db
    //lon lat are optional

    //brings user to login page
    try {
      const response = await axios.get(`http://localhost:9191/users/username/${usernameInput.value}`)

      if(response.data === null || Object.keys(response.data).length === 0){
        await axios.post(`http://localhost:9191/users/create-account`, {
          username: `${usernameInput.value}`,
          password: `${passwordInput.value}`,
          userSavedLat: `${latInput.value}`,
          userSavedLon: `${lonInput.value}`
        })



        if (passwordInput.value.length < 8) {
          alert("Your password must be 8 characters long")
          passwordInput.value ="";
          return;
        }
        login()
      }
      else{
        alert("This username is already taken");
      }

    }
    catch (error) {
      alert("This username is already taken");
    }

  }


  const login = () =>{

    emit('login')
  }

  const backToLogin =() =>{
    emit('backToLogin')
  }






</script>

<template>
  <div class="create-account-wrapper">
    <p>Username:</p>
    <input class="username-input" v-model="usernameInput">
    <p>Password:</p>
    <input class="password-input" v-model="passwordInput">
    <p>Latitude:</p>
    <input class="lat-input" v-model="latInput">
    <p>Longitude:</p>
    <input class="lon-input" v-model="lonInput">



    <div class="create-account-button">
    <button @click="validateAccount()">Create Account</button>
    </div>


    <p class="button-header" >Already Have An Account?</p>
    <div class="back-to-login-button">

    <button @click="backToLogin()">Back To Login</button>
    </div>
  </div>

</template>

<style scoped>
.button-header{
  margin-top: 15px;

}

.back-to-login-button{
  position: relative;
  left: 10%;
  border-style: solid;
  border-color: #787b7e;
  border-radius: 10px;
  width: 70%;
  padding: 3px;
  text-align: center;
  margin-top: 3px;
}

.create-account-button{
  position: relative;
  left: 10%;
  border-style: solid;
  border-color: #787b7e;
  border-radius: 10px;
  width: 70%;
  padding: 3px;
  text-align: center;
  margin-top: 15px;
}

.username-input{
  border-style: solid;
  border-color: #787b7e;
  border-radius: 10px;
  color: #787b7e;
  padding: 5px;
}

.password-input{
  border-style: solid;
  border-color: #787b7e;
  border-radius: 10px;
  color: #787b7e;
  padding: 5px;
}

.lat-input{
  border-style: solid;
  border-color: #787b7e;
  border-radius: 10px;
  color: #787b7e;
  padding: 5px;
}

.lon-input{
  border-style: solid;
  border-color: #787b7e;
  border-radius: 10px;
  color: #787b7e;
  padding: 5px;
}

</style>
