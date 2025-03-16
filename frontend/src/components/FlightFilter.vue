<script setup lang="ts">
import { ref } from 'vue'

const emit = defineEmits<{
  (
    e: 'filter',
    filters: {
      source: string
      destination: string
      maxPrice: number | null
    },
  ): void
}>()

const source = ref('')
const destination = ref('')
const maxPrice = ref<number | null>(null)

function applyFilters() {
  emit('filter', {
    source: source.value,
    destination: destination.value,
    maxPrice: maxPrice.value,
  })
}

function resetFilters() {
  source.value = ''
  destination.value = ''
  maxPrice.value = null
  applyFilters()
}
</script>

<template>
  <div class="flight-filter">
    <h2>Filter Flights</h2>
    <div class="filter-form">
      <div class="filter-group">
        <label for="source">From:</label>
        <input
          id="source"
          v-model="source"
          type="text"
          placeholder="city, country or airport"
          @keyup.enter="applyFilters"
        />
      </div>

      <div class="filter-group">
        <label for="destination">To:</label>
        <input
          id="destination"
          v-model="destination"
          type="text"
          placeholder="city, country or airport"
          @keyup.enter="applyFilters"
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
          @keyup.enter="applyFilters"
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
