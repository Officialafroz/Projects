import { api } from "./BaseConfig.jsx";

const API_BASE_GRAPH = "/api/central/dashboard/graphs";


export const getDashboardData = (month, year) => {
  return api.get("/api/central/dashboard", { params: { month, year } });
};

export const getBookingTrends = (year) => {
  return api.get(`${API_BASE_GRAPH}/booking-trends?year=${year}`);
};

export const getRevenueTrends = (year) => {
  return api.get(`${API_BASE_GRAPH}/revenue-trends?year=${year}`);
};

export const getUserGrowth = (year) => {
  return api.get(`${API_BASE_GRAPH}/users-growth?year=${year}`);
};

export const getSeatOccupancy = (year) => {
  return api.get(`${API_BASE_GRAPH}/seat-occupancy?year=${year}`);
};