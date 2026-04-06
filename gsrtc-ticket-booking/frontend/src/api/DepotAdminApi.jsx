import { api } from "./BaseConfig";

const API_BASE = "/api/central/depot-admin";

// pagination list
export const fetchDepotAdmins = (page, size) => {
  return api.post(`${API_BASE}/list?pageNo=${page}&pageSize=${size}`);
};

// create
export const createDepotAdmin = (data) => {
  return api.post("/api/depot-admin/register", data);
};

export const inviteDepotAdmin = (data) => {
  return api.post(`${API_BASE}/invite`, data);
};

// update
export const updateDepotAdmin = (id, email, phoneNumber) => {
  return api.put(`${API_BASE}/update/${id}`, null, { params: { email, phoneNumber } });
};

// search
export const searchDepotAdmins = (keyword, page, size) => {
  return api.post(
    `${API_BASE}/search?keyword=${keyword}&page=${page}&size=${size}`
  );
};

// delete
export const deleteDepotAdmin = (id) => {
  return api.delete(`${API_BASE}/delete/${id}`);
};

export const sendCredentials = (id, email, password) => {
  return api.post(`${API_BASE}/credentials/${id}`, {}, {
    params: { email, password }
  });
};