<script setup lang="ts">
import type { Seat } from '@/types/Seat'
import SeatRow from '@/components/SeatRow.vue'

const props = defineProps<{
  seats: Seat[]
  selectedSeats: Seat[]
}>()

const emit = defineEmits<{
  (e: 'select', seat: Seat | null): void
}>()

// Calculate unique rows and columns from seats data
const rows = [...new Set(props.seats.map((seat) => seat.row))].sort((a, b) => a - b)
const columns = [...new Set(props.seats.map((seat) => seat.column))].sort()
</script>

<template>
  <div class="seat-map">
    <div v-for="row in rows" :key="row" class="seat-row">
      <SeatRow
        :row="row"
        :columns="columns"
        :seats="seats.filter((s) => s.row === row)"
        :selected-seats="selectedSeats"
        @select="(seat) => emit('select', seat)"
      />
    </div>
  </div>
</template>

<style scoped>
.seat-map {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 2rem;
}
</style>
