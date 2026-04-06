import { fetchWithRefresh } from "./RequestWrapper"
import { api } from "./BaseConfig"

export const fetchCurrentUser = async () => {
 const res = await fetchWithRefresh(() =>
    api.get("/api/auth/me", { withCredentials: true })
  );

  return res.data;
}