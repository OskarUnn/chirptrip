<script setup lang="ts">
import { defineProps } from 'vue'
import type { Flight } from '@/types/Flight'

const props = defineProps<{ flight: Flight }>()
</script>

<template>
  <div class="flight-card">
    <h2>{{ flight.sourceCityName }} ✈ {{ flight.destinationCityName }}</h2>
    <p><strong>Departure:</strong> {{ new Date(flight.departureTime).toLocaleString() }}</p>
  </div>
</template>

<style scoped>
.flight-card {
  background: var(--bg-white);
  border: 1px solid var(--color-border);
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  transition: all 0.3s ease;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.flight-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  border-color: var(--color-border-hover);
}

.flight-card h2 {
  color: var(--primary-blue);
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1rem;
}

.flight-card p {
  color: var(--text-muted);
  font-size: 1rem;
}
</style>
