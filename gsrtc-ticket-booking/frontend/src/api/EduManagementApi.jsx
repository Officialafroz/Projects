import { api } from "./BaseConfig";

const API_BASE = "/api/depot/trip-request";
const API_USER_BASE = "/api/end-user/edu-trip-request";

export const loadTripOverviews = (id) => {
  return api.get(`${API_USER_BASE}/overviews/${id}`);
};

export const loadTripDetail = (id) => {
  return api.get(`${API_USER_BASE}/${id}`);
};

export const updateBudgetAndApprove = (id, budget) => {
  return api.put(`${API_BASE}/${id}`, {}, {
    params: { budget }
  });
}

export const busAssignment = (id, busNumber, driverName, conductorName) => {
  return api.post(`/api/depot/edu-trip-bus/assignment/${id}`, {
    busNumber,
    driverName,
    conductorName
  });
}

export const handleStatus = (id, status) => {
  return api.put(`${API_BASE}/${id}/status`, {}, { params: {status}})
}

export const getUserTrips = (id) => {
  return api.get(`${API_USER_BASE}/list/${id}`)
}
