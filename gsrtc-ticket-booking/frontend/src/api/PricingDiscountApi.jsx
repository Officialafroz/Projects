import { api } from "./BaseConfig";

const API_BASE = "/api/central/pricing";

export const getPricing = () => {
  return api.get(`${API_BASE}`);
};

export const updatePricing = (pricing) => {
  return api.put(`${API_BASE}`, {}, {
    params: {
      reservationFee: pricing.reservationFee,
      tollFee: pricing.tollFee,
      gst: pricing.gstPercentage,
    }
  });
};

export const getDiscounts = () => {
  return api.get(`${API_BASE}/discount`);
};

export const createDiscount = (data) => {
  return api.post(`${API_BASE}/discount`, data);
};