export interface Flight {
  id: number
  sourceIata: string
  sourceCity: string
  sourceCountry: string
  destinationIata: string
  destinationCity: string
  destinationCountry: string
  departureTime: number
  duration: number
  price: number
}
