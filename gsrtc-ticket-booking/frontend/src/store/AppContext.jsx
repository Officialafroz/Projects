import axios from "axios";
import { createContext, useState } from "react";

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const [buses, setBuses] = useState([]);
  const [routes, setRoutes] = useState([]);
  const [schedules, setSchedules] = useState([]);
  const [user, setUser] = useState(null);
  const [admin, setAdmin] = useState(null);
  const [authUser, setAuthUser] = useState(null);
  const [busSearch, setBusSearch] = useState(null);
  const [passengers, setPassengers] = useState([]);
  const [tripCode, setTripCode] = useState("");
  const [ticketData, setTicketData] = useState([]);

  // ── NEW: tracks whether the initial fetchCurrentUser call has settled ──────
  // Starts true so ProtectedRoute waits before redirecting on page refresh.
  // AppContent sets it to false once fetchCurrentUser resolves or is skipped.
  const [sessionLoading, setSessionLoading] = useState(true);

  const fetchTickets = async (email) => {
    try {
      const res = await axios.get(
        "/api/end-user/ticket/tickets",
        { params: { email } }
      );
      setTicketData(res.data);
    } catch (err) {
      console.error("Failed to load tickets", err);
    }
  };

  return (
    <AppContext.Provider
      value={{
        buses, setBuses,
        routes, setRoutes,
        schedules, setSchedules,
        user, setUser,
        admin, setAdmin,
        authUser, setAuthUser,
        busSearch, setBusSearch,
        passengers, setPassengers,
        tripCode, setTripCode,
        ticketData, setTicketData,
        fetchTickets,
        // ── NEW ──
        sessionLoading,
        setSessionLoading,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};