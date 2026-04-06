import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { AppProvider, AppContext } from "./store/AppContext";
import { useContext } from "react";

import Navbar from "./components/Navbar/Navbar";
import Home from "./pages/Home";
import UserLogin from "./pages/UserLogin";
import SignUp from "./pages/SignUp";
import YourBookings from "./components/EndUser/YourBookings";
import BookingCancellation from "./components/EndUser/BookingCancellation";
import AdminLogin from "./pages/AdminLogin";
import ContactUs from "./components/EndUser/ContactUs";
import AboutUs from "./components/EndUser/AboutUs";
import EducationalBooking from "./components/EndUser/Educational-Booking/EducationalBooking.jsx";
import AdminPages from "./pages/AdminPages";
import CreateDepotAdmin from "./components/Super-admin/Depot-admin/Create/CreateDepotAdmin";

// ─── Fix 4: ProtectedRoute — redirects unauthenticated users to /signin ───────
const ProtectedRoute = ({ children }) => {
  const { authUser } = useContext(AppContext);
  if (!authUser) return <Navigate to="/signin" replace />;
  return children;
};

// ─── Fix 5: RoleGuard — redirects users who lack the required role ────────────
const RoleGuard = ({ allowedRoles, children }) => {
  const { authUser } = useContext(AppContext);
  if (!authUser || !allowedRoles.includes(authUser.role)) {
    return <Navigate to="/bus-booking" replace />;
  }
  return children;
};

// ─── Fix 3: UserLayout — owns Navbar + end-user routes ───────────────────────
const UserLayout = () => (
  <>
    <Navbar />
    <Routes>
      <Route path="/" element={<Navigate to="/bus-booking" replace />} />
      <Route path="/bus-booking" element={<Home />} />
      <Route path="/signin" element={<UserLogin />} />
      <Route path="/signup" element={<SignUp />} /> {/* Fix 2: no more prop drilling */}
      <Route path="/admin-login" element={<AdminLogin />} />
      <Route path="/contact-us" element={<ContactUs />} />
      <Route path="/about-us" element={<AboutUs />} />

      {/* Fix 4: protected routes — require login */}
      <Route
        path="/your-bookings"
        element={
          <ProtectedRoute>
            <YourBookings />
          </ProtectedRoute>
        }
      />
      <Route
        path="/educational-booking"
        element={
          <ProtectedRoute>
            <EducationalBooking />
          </ProtectedRoute>
        }
      />
      <Route
        path="/booking-cancellation"
        element={
          <ProtectedRoute>
            <BookingCancellation />
          </ProtectedRoute>
        }
      />
    </Routes>
  </>
);

// ─── Fix 3: AdminLayout — owns admin routes with role guard ──────────────────
const AdminLayout = () => {
  const { authUser } = useContext(AppContext);
  return (
    <Routes>
      {/* Fix 5: role-guarded admin area */}
      <Route
        path="/*"
        element={
          <RoleGuard allowedRoles={["DEPOT_ADMIN", "SUPER_ADMIN"]}>
            <AdminPages authUser={authUser} />
          </RoleGuard>
        }
      />
      <Route
        path="/depot-admin/register"
        element={
          <RoleGuard allowedRoles={["SUPER_ADMIN"]}>
            <CreateDepotAdmin />
          </RoleGuard>
        }
      />
    </Routes>
  );
};

// ─── Fix 1 & 3: Single Routes block — no more dual <Routes> ──────────────────
const AppContent = () => {
  const { authUser } = useContext(AppContext);

  const isAdmin =
    authUser?.role === "DEPOT_ADMIN" || authUser?.role === "SUPER_ADMIN";

  return isAdmin ? <AdminLayout /> : <UserLayout />;
};

const App = () => (
  <Router>
    <AppProvider>
      <AppContent />
    </AppProvider>
  </Router>
);

export default App;