import { refreshSession } from "./RefreshSession";

export const fetchWithRefresh = async (requestFn) => {
  try {
    return await requestFn();
  } catch (error) {
    if (error.response && error.response.status === 401) {
      const refreshed = await refreshSession();

      if (refreshed) {
        return await requestFn(); // retry original request
      }
    }

    throw error;
  }
};