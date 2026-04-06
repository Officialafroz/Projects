import { api } from "./BaseConfig";

const API_BASE_CENTRAL = "/api/central/bus-depot";
const API_BASE = "/api/depot/bus-depot";

export const getDepots = (page, size) => {
  return api.get(`${API_BASE}/summary/list?page=${page}&size=${size}`);
};

export const getDepotNames = () => {
  return api.get(`${API_BASE}/depot-names`);
}

export const getCustomDepotObj = () => {
  return api.get(`${API_BASE}/list`);
}

export const deleteDepot = (id) => {
  return api.delete(`${API_BASE}/${id}`);
}

export const createDepot = (data) => {
  return api.post(`${API_BASE_CENTRAL}`, data);
}

export const getDepotList = (page, size) => {
  return api.get(`${API_BASE_CENTRAL}/list?page=${page}&size=${size}`);
}

export const update = (id, depotName, address, password) => {
  return api.put(`${API_BASE_CENTRAL}/${id}`, {}, {
    params: { depotName, address, password }
  });
}

export const getByDepotName = (depotName) => {
  return api.get(`${API_BASE_CENTRAL}/${depotName}`);
};