<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { Flight } from '@/types/Flight'
import { fetchFlights } from '@/services/flightService'
import FlightCard from '@/components/FlightCard.vue'

const flights = ref<Flight[]>([])

const loadFlights = async () => {
  flights.value = await fetchFlights()
}

onMounted(loadFlights)
</script>

<template>
  <div class="flight-list">
    <h1>Available Flights</h1>
    <div v-if="flights.length" class="flight-grid">
      <FlightCard v-for="flight in flights" :key="flight.id" :flight="flight" />
    </div>
    <p v-else class="loading">Loading flights...</p>
  </div>
</template>

<style scoped>
.flight-list {
  padding: 2rem 0;
}

.flight-grid {
  display: grid;
  gap: 1.5rem;
  grid-template-columns: 1fr;
}

.loading {
  text-align: center;
  color: var(--text-muted);
  font-size: 1.2rem;
  padding: 2rem;
}

@media (min-width: 768px) {
  .flight-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1024px) {
  .flight-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>
