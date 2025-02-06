<template>
  <div class="my-10">
    <h1 class="text-center">Welcome to Easy Shop!</h1>
  </div>
  <v-container fluid>
    <v-row dense>
      <v-col
        v-for="n in 8"
        :key="n"
        cols="6"
      >
        <v-sheet
          color="surface-variant-alt"
          height="96"
        ></v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>
