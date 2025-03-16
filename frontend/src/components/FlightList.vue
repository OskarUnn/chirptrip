<script setup lang="ts">
import { ref, onMounted, computed, defineExpose } from 'vue'
import type { Flight } from '@/types/Flight'
import { fetchFlights } from '@/services/flightService'
import FlightCard from '@/components/FlightCard.vue'

const allFlights = ref<Flight[]>([])
const filters = ref({
  source: '',
  destination: '',
  maxPrice: null as number | null,
})

const isLoading = ref(false)

const filteredFlights = computed(() => allFlights.value)

const loadFlights = async () => {
  isLoading.value = true
  allFlights.value = await fetchFlights(filters.value)
  isLoading.value = false
}

const updateFilters = (newFilters: typeof filters.value) => {
  filters.value = newFilters
  loadFlights()
}

onMounted(loadFlights)

defineExpose({ updateFilters })
</script>

<template>
  <div class="flight-list">
    <h1>Available Flights ({{ filteredFlights.length }})</h1>
    <div v-if="isLoading" class="loading">Loading flights...</div>
    <div v-else-if="filteredFlights.length" class="flight-grid">
      <FlightCard
        v-for="flight in filteredFlights"
        :key="flight.id"
        :flight="flight"
        :hide-source="!!filters.source"
      />
    </div>
    <p v-else class="no-results">No flights found</p>
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

.loading,
.no-results {
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
