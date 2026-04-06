import { api } from "./BaseConfig";

const API_BASE = "/api/depot/passenger-management";

export const loadPassengerList = (depotId) => {
  return api.get(`${API_BASE}/list`, { params: { depotId } });
};

export const filterByPnrOrPassengerReference = (pnr, passRef) => {
  return api.get(`${API_BASE}/filter/pnr/pass-ref`, {
    params: { pnr, passRef }
  });
};

export const filterByBusNumberOrTripCodeOrTotalSeats = (busNumber, tripCode, totalSeats) => {
  return api.get(`${API_BASE}/filter/bus-number/trip-code/total-seats`, {
    params: { busNumber, tripCode, totalSeats }
  });
};

export const filterByRouteAndBusTypeAndRevenue = (route, classType, revenue) => {
  return api.get(`${API_BASE}/filter/route/class/revenue`, {
    params: { route, classType, revenue }
  });
};