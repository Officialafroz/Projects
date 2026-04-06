import { api } from "./BaseConfig"

export const pay = (data) => {
  return api.post("/api/end-user/payment", data);
}
