import { api } from "./BaseConfig";

export const logout = async () => {
  return api.post("/auth/logout");
};