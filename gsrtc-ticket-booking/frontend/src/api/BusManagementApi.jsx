import { api } from "./BaseConfig";

const API_BASE = "/api/depot/bus";

export const addNewBus = (bus) => {
  return api.post(`${API_BASE}bus`, bus);
};

export const fetchBusList = (depotId) => {
  return api.get(`${API_BASE}/list/${depotId}`);
};

export const deleteBus = (busNumber) => {
  return api.delete(`${API_BASE}/${busNumber}`);
};