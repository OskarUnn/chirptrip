<script setup lang="ts">
import { ref } from 'vue'

const emit = defineEmits<{
  (
    e: 'filter',
    filters: {
      sourceCity: string
      destinationCity: string
      maxPrice: number | null
    },
  ): void
}>()

const sourceCity = ref('')
const destinationCity = ref('')
const maxPrice = ref<number | null>(null)

function applyFilters() {
  emit('filter', {
    sourceCity: sourceCity.value,
    destinationCity: destinationCity.value,
    maxPrice: maxPrice.value,
  })
}

function resetFilters() {
  sourceCity.value = ''
  destinationCity.value = ''
  maxPrice.value = null
  applyFilters()
}
</script>

<template>
  <div class="flight-filter">
    <h2>Filter Flights</h2>
    <div class="filter-form">
      <div class="filter-group">
        <label for="sourceCity">From City:</label>
        <input
          id="sourceCity"
          v-model="sourceCity"
          type="text"
          placeholder="Enter departure city"
        />
      </div>

      <div class="filter-group">
        <label for="destinationCity">To City:</label>
        <input
          id="destinationCity"
          v-model="destinationCity"
          type="text"
          placeholder="Enter destination city"
        />
      </div>

      <div class="filter-group">
        <label for="maxPrice">Max Price (€):</label>
        <input
          id="maxPrice"
          v-model="maxPrice"
          type="number"
          min="0"
          placeholder="Enter maximum price"
        />
      </div>

      <button class="apply-button" @click="applyFilters">Apply Filters</button>
      <button class="reset-button" @click="resetFilters">Reset Filters</button>
    </div>
  </div>
</template>

<style scoped>
.flight-filter {
  background: var(--bg-white);
  border: 1px solid var(--color-border);
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 2rem;
}

.flight-filter h2 {
  color: var(--primary-blue);
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.filter-form {
  display: grid;
  gap: 1rem;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.filter-group label {
  color: var(--text-muted);
  font-weight: 500;
}

.filter-group input {
  padding: 0.5rem;
  border: 1px solid var(--color-border);
  border-radius: 6px;
  font-size: 1rem;
}

.apply-button {
  background: var(--primary-blue);
  color: white;
  border: none;
  border-radius: 6px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.apply-button:hover {
  background: var(--primary-dark);
}

.reset-button {
  background: var(--primary-light);
  color: white;
  border: none;
  border-radius: 6px;
  padding: 0.5rem 1rem;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.reset-button:hover {
  background: var(--primary-dark);
}

@media (min-width: 768px) {
  .filter-form {
    grid-template-columns: repeat(3, 1fr);
    gap: 1.5rem;
  }

  .apply-button,
  .reset-button {
    grid-column: span 3;
  }
}
</style>
