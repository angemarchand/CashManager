<template>
  <div class="mt-8">
    <h1 class="text-center text-title">Easy Shop</h1>
  </div>
  <v-sheet class="mx-auto my-10" width="400">
    <v-card>
      <v-card-title class="text-center my-4 font-weight-bold">Create your account</v-card-title>
      <v-form @submit.prevent="createUser" class="mx-4 my-4">
        <v-text-field
          v-model="firstName"
          :rules="firstNameRules"
          label="First name"
        ></v-text-field>
        <v-text-field
          v-model="lastName"
          :rules="lastNameRules"
          label="Last name"
        ></v-text-field>
        <v-text-field
          type="email"
          v-model="email"
          :rules="emailRules"
          label="Email"
        ></v-text-field>
        <v-text-field
          v-model="password"
          :rules="passwordRules"
          label="Password"
        ></v-text-field>
        <v-row justify="center">
          <v-col cols="6" md="4" sm="6">
            <v-btn color="secondary" class="my-4 " type="submit" rounded size="x-large" block>Submit</v-btn>
          </v-col>
        </v-row>
      </v-form>
    </v-card>
  </v-sheet>
</template>

<script setup lang="ts">

import {ref} from "vue";

const firstName = ref("")
const lastName = ref("")
const email = ref("")
const password = ref("")


const firstNameRules = [
  (v: string) => !!v || "You must enter a first name."
];
const lastNameRules = [
  (v: string) => !!v || "You must enter a last name."
];
const emailRules = [
  (v: string) => !!v || "You must enter an email.",
  (v: string) => /.+@.+\..+/.test(v) || "Email must be valid."
];
const passwordRules = [
  (v: string) => !!v || "You must enter a password.",
  (v: string) => v.length >= 4 || "Password must be at least 4 characters."
];

const createUser = () => {
  console.log(
    firstName.value,
    lastName.value,
    email.value,
    password.value,
  )
}

</script>
