import axios from 'axios'
import type { Flight } from '@/types/Flight'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

export const fetchFlights = async (): Promise<Flight[]> => {
  try {
    const response = await axios.get<Flight[]>(`${API_BASE_URL}/flight/`)
    return response.data
  } catch (error) {
    console.error('Error fetching flights:', error)
    return []
  }
}
