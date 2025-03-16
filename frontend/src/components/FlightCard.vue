<script setup lang="ts">
import { defineProps } from 'vue'
import type { Flight } from '@/types/Flight'

defineProps<{
  flight: Flight
  hideSource?: boolean
}>()

function formatDuration(seconds: number) {
  const hours = Math.floor(seconds / 3600)
  const minutes = Math.floor((seconds % 3600) / 60)
  return `${hours}h ${minutes}m`
}

function formatPrice(price: number) {
  return new Intl.NumberFormat('et-EE', {
    style: 'currency',
    currency: 'EUR',
  }).format(price)
}
</script>

<template>
  <div class="flight-card">
    <h2>
      <template v-if="!hideSource">
        {{ flight.sourceCity }}, {{ flight.sourceCountry }} ✈
      </template>
      {{ flight.destinationCity }}, {{ flight.destinationCountry }}
    </h2>
    <p>{{ flight.sourceIata }} ✈ {{ flight.destinationIata }}</p>
    <p>
      <strong>Departure:</strong>
      {{ new Date(flight.departureTime * 1000).toLocaleString('et-EE') }}
    </p>
    <p>
      <strong>Duration:</strong>
      {{ formatDuration(flight.duration) }}
    </p>
    <p>
      <strong>Price:</strong>
      {{ formatPrice(flight.price) }}
    </p>
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
