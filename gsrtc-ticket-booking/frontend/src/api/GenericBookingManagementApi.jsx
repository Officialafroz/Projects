import {api} from "./BaseConfig"

const API_BASE = "/api/depot/booking-management";

export const fetchBookings = (id, filters) => {
  const depotId = id;
  let url = `${API_BASE}/list`;
  let params = { depotId };

  // 1️⃣ Date only
  if (filters.date) {
    url = `${API_BASE}/filter/date`;
    params.date = filters.date;
  }

  // 2️⃣ Trip Code only
  else if (filters.search && filters.search.length >= 6) {
    url = `${API_BASE}/filter/trip-code`;
    params.tripCode = filters.search;
  }

  // 3️⃣ Route + Class + Revenue
  else if (filters.search || filters.classType || filters.revenue) {
    url = `${API_BASE}/filter/route/class-type/revenue`;
    params.route = filters.search || "";
    params.classType = filters.classType || "";
    params.revenue = filters.revenue || "";
  }

  return api.get(url, { params });
};