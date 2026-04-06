import axios from "axios";

export const fetchStaff = async ({
  depotId,
  search,
  role,
  status
}) => {
  return axios.get("/api/depot/staff-management/staff/list", {
    params: {
      depotId
      // search: search || null,   // STF / name / license handled backend
      // role: role || null,
      // status: status || null
    }
  });
};
