<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import AppBar from '@/components/AppBar.vue'
</script>

<template>
  <v-app>
    <AppBar />

    v-slot

    <header>
      <nav>
        <!--        <RouterLink to="/">Home</RouterLink>-->
      </nav>
    </header>

    <RouterView />
  </v-app>
</template>
