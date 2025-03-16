<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import type { Flight } from '@/types/Flight'
import type { Seat } from '@/types/Seat'
import { fetchFlightSeats, bookSeats } from '@/services/seatsService'
import SeatMap from '@/components/SeatMap.vue'

const route = useRoute()
const flight = ref<Flight | null>(null)
const seats = ref<Seat[]>([])
const selectedSeat = ref<Seat | null>(null)
const isLoading = ref(false)

onMounted(async () => {
  const flightId = route.params.flightId as string
  isLoading.value = true
  seats.value = await fetchFlightSeats(flightId)
  isLoading.value = false
})

function selectSeat(seat: Seat | null) {
  if (seat?.occupied) return
  selectedSeat.value = seat
}

async function confirmSelection() {
  if (!selectedSeat.value) return

  try {
    const success = await bookSeats([selectedSeat.value])
    if (success) {
      // Refresh the seats to update availability
      const flightId = route.params.flightId as string
      seats.value = await fetchFlightSeats(flightId)
      selectedSeat.value = null
    } else {
      // Handle booking failure
      console.error('Failed to book seat')
    }
  } catch (error) {
    console.error('Error during seat booking:', error)
  }
}
</script>

<template>
  <div class="seat-selection">
    <h1>Select Your Seat</h1>

    <div v-if="isLoading" class="loading">Loading seats...</div>
    <div v-else class="cabin-layout">
      <SeatMap :seats="seats" :selected-seat="selectedSeat" @select="selectSeat" />

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

      <div v-if="selectedSeat" class="selection-info">
        <h3>Selected Seat: {{ selectedSeat.row }}{{ selectedSeat.column }}</h3>
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
</style>
