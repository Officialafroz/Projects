import { api } from './BaseConfig';

export const loginDA = (email, password) => {
  return api.post("/api/auth/login/verify/credentials",
    { email, password });
}
