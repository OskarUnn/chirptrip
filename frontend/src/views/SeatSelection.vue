<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { Flight } from '@/types/Flight'
import type { Seat } from '@/types/Seat'
import { fetchFlightSeats, bookSeats } from '@/services/seatsService'
import { fetchFlightById } from '@/services/flightService'
import SeatMap from '@/components/SeatMap.vue'

const route = useRoute()
const router = useRouter()
const flight = ref<Flight | null>(null)
const seats = ref<Seat[]>([])
const selectedSeats = ref<Seat[]>([])
const isLoading = ref(false)

const totalPrice = computed(() => {
  if (!flight.value || !selectedSeats.value.length) return 0
  return flight.value.price * selectedSeats.value.length
})

onMounted(async () => {
  const flightId = route.params.flightId as string
  isLoading.value = true

  // Fetch both flight details and seats
  flight.value = await fetchFlightById(flightId)
  seats.value = await fetchFlightSeats(flightId)

  isLoading.value = false
})

function selectSeat(seat: Seat | null) {
  if (!seat || seat.occupied) return

  const existingIndex = selectedSeats.value.findIndex((s) => s.id === seat.id)
  if (existingIndex >= 0) {
    selectedSeats.value.splice(existingIndex, 1)
  } else {
    selectedSeats.value.push(seat)
  }
}

async function confirmSelection() {
  if (!selectedSeats.value.length) return

  try {
    const bookedSeats = await bookSeats(selectedSeats.value)
    if (bookedSeats) {
      // Navigate to success page with booked seats data using query params
      router.push({
        name: 'booking-success',
        query: {
          bookedSeats: JSON.stringify(bookedSeats),
          flight: JSON.stringify(flight.value),
        },
      })
    } else {
      console.error('Failed to book seats')
    }
  } catch (error) {
    console.error('Error during seat booking:', error)
  }
}
</script>

<template>
  <div class="seat-selection">
    <h1>Select Your Seats</h1>

    <div v-if="isLoading" class="loading">Loading seats...</div>
    <div v-else class="cabin-layout">
      <SeatMap :seats="seats" :selected-seats="selectedSeats" @select="selectSeat" />

      <div class="legend">
        <div class="legend-item">
          <div class="seat-button"></div>
          <span>Available</span>
        </div>
        <div class="legend-item">
          <div class="seat-button occupied"></div>
          <span>Occupied</span>
        </div>
        <div class="legend-item">
          <div class="seat-button selected"></div>
          <span>Selected</span>
        </div>
      </div>

      <div v-if="selectedSeats.length" class="selection-info">
        <h3>
          Selected Seats:
          <span v-for="seat in selectedSeats" :key="seat.id">
            {{ seat.row }}{{ seat.column }}
          </span>
        </h3>
        <p class="total-price">Total Price: €{{ totalPrice.toFixed(2) }}</p>
        <button class="confirm-button" @click="confirmSelection">Confirm Selection</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.seat-selection {
  padding: 2rem;
  max-width: 1000px;
  margin: 0 auto;
}

.cabin-layout {
  background: var(--bg-white);
  border: 1px solid var(--color-border);
  border-radius: 12px;
  padding: 2rem;
  margin-top: 2rem;
}

.seat-map {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 2rem;
}

.seat-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.row-number {
  width: 2rem;
  text-align: right;
  color: var(--text-muted);
}

.aisle {
  width: 2rem;
}

.seat-button {
  width: 2.5rem;
  height: 2.5rem;
  border: 1px solid var(--color-border);
  border-radius: 4px;
  background: var(--bg-white);
  cursor: pointer;
  transition: all 0.3s ease;
}

.seat-button:hover:not(.occupied) {
  background: var(--primary-light);
  color: white;
}

.seat-button.occupied {
  background: var(--text-muted);
  cursor: not-allowed;
}

.seat-button.selected {
  background: var(--primary-blue);
  color: white;
}

.legend {
  display: flex;
  gap: 2rem;
  justify-content: center;
  margin-bottom: 2rem;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.legend-item .seat-button {
  cursor: default;
}

.legend-item span {
  color: var(--text-dark);
}

.selection-info {
  text-align: center;
  padding: 1rem;
  border-top: 1px solid var(--color-border);
}

.selection-info h3 {
  color: var(--text-dark);
  margin-bottom: 1rem;
}

.confirm-button {
  background: var(--primary-blue);
  color: white;
  border: none;
  border-radius: 6px;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 1rem;
}

.confirm-button:hover {
  background: var(--primary-dark);
}

.total-price {
  font-size: 1.2rem;
  font-weight: 600;
  color: var(--primary-blue);
  margin: 1rem 0;
}

.selection-info span {
  background: var(--primary-light);
  color: white;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  margin: 0 0.3rem;
}
</style>
