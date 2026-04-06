import { api } from "./BaseConfig.jsx";

const API_USER_BASE = "/api/end-user/edu-booking";

export const sendTripRequest = (data) => {
  return api.post("/api/end-user/edu-trip-request", data);
}

export const createBooking = (id) => {
  return api.post(`${API_USER_BASE}/${id}`)
}


