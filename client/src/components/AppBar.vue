<template>
  <v-card
    class="mx-auto my-5"
    max-width="448"
  >
      <v-app-bar
        color="primary"
        density="comfortable"
        height="80"
      >
        <template v-slot:prepend>
          <v-app-bar-nav-icon></v-app-bar-nav-icon>
        </template>

        <v-app-bar-title>Shop here</v-app-bar-title>

        <template v-slot:append>
          <v-btn icon="mdi-dots-vertical"></v-btn>
        </template>
      </v-app-bar>

  </v-card>
</template>

<script setup lang="ts">

</script>
