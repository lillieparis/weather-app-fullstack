<script setup>
import {ref} from "vue";
  import axios from "axios";

  const usernameInput = ref( "");

  const passwordInput = ref("");


  const validateLogin = async () => {
    try {
      if(!usernameInput || !passwordInput){
        alert("You must enter a username and a password to login")
      }
      else {
        const response = await axios.get(`http://localhost:9191/users/username/${usernameInput.value}`);
        //call backend to see if login info is valid, returns boolean
        const actualPassword = response.data.password


        if (actualPassword && actualPassword === passwordInput.value) {
          emit('loggedIn', usernameInput.value)
        } else {
          alert("Your login data is incorrect")
          passwordInput.value = "";
          usernameInput.value = "";
        }
      }
    } catch (error) {
        console.error(error)
        alert("This username does not exist. Create an account instead.")
      passwordInput.value = ""
      usernameInput.value = ""
    }
  }

  const createAccount = () => {
    emit('createAccount')
  }

  const emit = defineEmits(['createAccount', 'loggedIn'])

</script>

<template>

  <div class="user-login-page">
    <p>Username:</p>
    <input class="username-input" v-model="usernameInput">
    <p>Password:</p>
    <input class="password-input" v-model="passwordInput" type="password">

    <div class="login-button">
      <button @click="validateLogin()">Login</button>
     </div>
    <div class="create-account-button-wrapper">
      <p>Don't have an account yet?</p>
      <div class="create-account-button">
      <button @click="createAccount">Create Account</button>
      </div>
    </div>

  </div>

</template>

<style scoped>



.login-button{
  position: relative;
  left: 27%;
  border-style: solid;
  border-color: #787b7e;
  border-radius: 10px;
  width: 40%;
  padding: 3px;
  text-align: center;
  margin-top: 15px;
}

.create-account-button-wrapper{

  margin-top: 40px;

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
  margin-top: 5px;
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


</style>
