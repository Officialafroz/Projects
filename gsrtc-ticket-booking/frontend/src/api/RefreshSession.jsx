import { api } from "./BaseConfig";

export const refreshSession = async () => {
  try {
    await api.post("/auth/refresh", { withCredentials: true}); // sets new cookies
    return true;
  } catch {
    return false;
  }
};