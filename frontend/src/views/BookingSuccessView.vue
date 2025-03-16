<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import type { Flight } from '@/types/Flight'
import type { Seat } from '@/types/Seat'

const route = useRoute()
const router = useRouter()

const bookedSeats = route.query.bookedSeats
  ? (JSON.parse(route.query.bookedSeats as string) as Seat[])
  : null
const flight = route.query.flight ? (JSON.parse(route.query.flight as string) as Flight) : null

if (!bookedSeats || !flight) {
  router.replace({ name: 'home' })
}

function formatSeatNumber(seat: Seat): string {
  return `${seat.row}${seat.column}`
}
</script>

<template>
  <div v-if="flight && bookedSeats" class="booking-success">
    <div class="success-card">
      <h1>Booking Successful!</h1>
      <div class="flight-details">
        <h2>Flight Details</h2>
        <p>
          {{ flight.sourceCity }} ({{ flight.sourceIata }}) ✈ {{ flight.destinationCity }} ({{
            flight.destinationIata
          }})
        </p>
        <p>Departure: {{ new Date(flight.departureTime * 1000).toLocaleString() }}</p>
      </div>

      <div class="seats-details">
        <h2>Booked Seats</h2>
        <div class="seat-list">
          <span v-for="seat in bookedSeats" :key="seat.id" class="seat-tag">
            {{ formatSeatNumber(seat) }}
          </span>
        </div>
      </div>

      <router-link :to="{ name: 'home' }" class="home-button"> Return to Home </router-link>
    </div>
  </div>
</template>

<style scoped>
.booking-success {
  padding: 2rem;
  max-width: 800px;
  margin: 0 auto;
}

.success-card {
  background: var(--bg-white);
  border: 1px solid var(--color-border);
  border-radius: 12px;
  padding: 2rem;
  text-align: center;
}

.success-card h1 {
  color: var(--primary-blue);
  margin-bottom: 2rem;
}

.flight-details,
.seats-details {
  margin-bottom: 2rem;
}

.flight-details h2,
.seats-details h2 {
  color: var(--text-dark);
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.flight-details p,
.seats-details p {
  color: var(--text-dark);
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
}

.seat-list {
  display: flex;
  gap: 0.5rem;
  justify-content: center;
  flex-wrap: wrap;
}

.seat-tag {
  background: var(--primary-light);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  font-weight: 600;
}

.home-button {
  display: inline-block;
  background: var(--primary-blue);
  color: white;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  margin-top: 1rem;
  transition: background-color 0.3s;
}

.home-button:hover {
  background: var(--primary-dark);
}
</style>
