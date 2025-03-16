import axios from 'axios'
import type { Flight } from '@/types/Flight'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

interface FlightFilters {
  source?: string
  destination?: string
  maxPrice?: number | null
}

export const fetchFlights = async (filters?: FlightFilters): Promise<Flight[]> => {
  try {
    const params = new URLSearchParams()

    if (filters?.source) {
      params.append('source', filters.source)
    }
    if (filters?.destination) {
      params.append('destination', filters.destination)
    }
    if (filters?.maxPrice) {
      params.append('maxPrice', filters.maxPrice.toString())
    }

    const response = await axios.get<Flight[]>(`${API_BASE_URL}/flight/`, { params })
    return response.data
  } catch (error) {
    console.error('Error fetching flights:', error)
    return []
  }
}
