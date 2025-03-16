<script setup lang="ts">
import { ref } from 'vue'
import FlightList from '../components/FlightList.vue'
import FlightFilter from '../components/FlightFilter.vue'

const flightList = ref<InstanceType<typeof FlightList> | null>(null)
</script>

<template>
  <main>
    <FlightFilter @filter="(filters) => flightList?.updateFilters(filters)" />
    <FlightList ref="flightList" />
  </main>
</template>
