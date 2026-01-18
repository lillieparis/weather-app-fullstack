<script setup>

import Login from "@/components/Login.vue";
import CreateAccount from "@/components/CreateAccount.vue";
import HomeDisplay from "@/components/HomeDisplay.vue";

import {ref} from "vue";

const currentUser = ref();

const createAccount = ref(false);

const login = ref(true);

const loggedIn = ref(false);

const handleCreateAccount = () =>{
  createAccount.value = true
  login.value = false
}

const handleNewAccount = () =>{
  createAccount.value = false
  login.value = true
}

const handleSuccessfulLogin = (username) =>{
  loggedIn.value = true
  currentUser.value = username
}

const handleBackToLogin = () => {
  login.value = true
  createAccount.value = false
}


</script>

<template>

  <div class="login-page" v-if="login && !loggedIn">
    <Login
      @create-account="handleCreateAccount()"
      @logged-in="handleSuccessfulLogin">

    </Login>
  </div>

  <div class="create-account-page" v-if="createAccount">
    <CreateAccount
      @login="handleNewAccount()"
      @back-to-login="handleBackToLogin()" >
    </CreateAccount>
  </div>

  <div class="home-page-wrapper" v-if="loggedIn">
     <HomeDisplay
       :current-user="currentUser"
     >
     </HomeDisplay>
  </div>


</template>

<style scoped>

</style>
