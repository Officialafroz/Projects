import { api } from "./BaseConfig.jsx";

export const verifyInstitute = (code) => {
  return api.post(`/api/end-user/institute/verify/${code}`);
}
