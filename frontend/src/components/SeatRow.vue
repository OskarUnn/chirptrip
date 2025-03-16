<script setup lang="ts">
import type { Seat } from '@/types/Seat'

const props = defineProps<{
  row: number
  columns: string[]
  seats: Seat[]
  selectedSeats: Seat[]
}>()

const emit = defineEmits<{
  (e: 'select', seat: Seat | null): void
}>()

function getSeatClass(seat: Seat | undefined) {
  if (!seat) return { 'seat-button': true }
  return {
    'seat-button': true,
    occupied: seat.occupied,
    selected: props.selectedSeats.some((s) => s.id === seat.id),
  }
}
</script>

<template>
  <div class="seat-row">
    <div class="row-number">{{ row }}</div>
    <template v-for="column in columns" :key="`${row}${column}`">
      <div v-if="column === 'C'" class="aisle"></div>
      <button
        :class="getSeatClass(seats.find((s) => s.column === column))"
        @click="emit('select', seats.find((s) => s.column === column) ?? null)"
      >
        {{ column }}
      </button>
    </template>
  </div>
</template>

<style scoped>
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
</style>
