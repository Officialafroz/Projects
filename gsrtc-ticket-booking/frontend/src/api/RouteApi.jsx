import { api } from "../api/BaseConfig.jsx";

const API_BASE = "/api/depot/route";

export const fetchRouteList = (depotId) => {
  return api.get(`${API_BASE}/list/${depotId}`);
}

export const addRoute = (route, duration) => {
  return api.post(`${API_BASE}`, route, {
    params: { duration }
  });
};