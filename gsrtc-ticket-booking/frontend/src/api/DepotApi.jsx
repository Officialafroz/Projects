import { api } from "./BaseConfig";

const API_BASE = "/api/end-user/bus-depot";

export const getDepots = (page, size) => {
  return api.get(`${API_BASE}/summary/list?page=${page}&size=${size}`);
};

export const getDepotByName = (depotName) => {
  return api.get(`${API_BASE}/${depotName}`);
};

export const getDepotNameList = () => {
  return api.get(`${API_BASE}/list`);
};