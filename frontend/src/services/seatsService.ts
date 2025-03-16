import axios from 'axios'
import type { Seat } from '@/types/Seat'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

export const fetchFlightSeats = async (flightId: string): Promise<Seat[]> => {
  try {
    const response = await axios.get<Seat[]>(`${API_BASE_URL}/seats/${flightId}`)
    return response.data
  } catch (error) {
    console.error('Error fetching seats:', error)
    return []
  }
}

export const bookSeats = async (seats: Seat[]): Promise<boolean> => {
  try {
    await axios.post(`${API_BASE_URL}/seats/`, seats)
    return true
  } catch (error) {
    console.error('Error booking seats:', error)
    return false
  }
}
