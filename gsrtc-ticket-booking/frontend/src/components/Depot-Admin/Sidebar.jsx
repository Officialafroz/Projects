import { NavLink, useNavigate } from "react-router-dom";
import "../styles/Sidebar.css";
import { useContext } from "react";
import { AppContext } from "../../store/AppContext";
import BookingAccordion from "./BookingAccordian";
import DepotAccordian from "../Super-admin/Depot/DepotAccrodian";
import { logout } from "../../api/Logout";
import PricingAccordian from "../Super-admin/Pricing/PricingAccordian";
import EducationalAccordion from "./EducationalAccordian";

const Sidebar = () => {
  const { authUser, setAuthUser } = useContext(AppContext);
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      const res = await logout(authUser.depotId);
      setAuthUser(null);
      alert(res.data);
      navigate("/bus-booking");
    } catch (error) {
      console.error("Logout failed:", error);
      alert("Failed to logout. Please try again.");
    }
  };

  return (
    <div className="sidebar">
      <div className="top">
        <h2 className="sidebar-title">GSRTC Admin</h2>
        <nav className="sidebar-nav">
          {authUser.role === "DEPOT_ADMIN" ? (
            <>
              <NavLink to="/admin/dashboard" className="sidebar-link">Dashboard</NavLink>
              <NavLink to="/admin/bus-management" className="sidebar-link">Bus Management</NavLink>
              <NavLink to="/admin/route-management" className="sidebar-link">Route Management</NavLink>
              <NavLink to="/admin/schedule-management" className="sidebar-link">Schedule Management</NavLink>
              <BookingAccordion />
              <EducationalAccordion />
              <NavLink to="/admin/transaction-management" className="sidebar-link">Transaction Management</NavLink>
              <NavLink to="/admin/staff-management" className="sidebar-link">Staff Management</NavLink>
              <NavLink to="/admin/user-management" className="sidebar-link">User Management</NavLink>
            </>
          ) : (
            <>
              <NavLink to="/super-admin/dashboard" className="sidebar-link">Dashboard</NavLink>
              <DepotAccordian />
              <NavLink to="/super-admin/depot-admin-management" className="sidebar-link">Depot Admin Management</NavLink>
              <NavLink to="/super-admin/pricing" className="sidebar-link">Pricing</NavLink>
              <PricingAccordian />
              <NavLink to="/super-admin/Reports" className="sidebar-link">Reports</NavLink>
            </>
          )}
        </nav>
      </div>
      <div className="sidebar-footer">
        <button className="logout-btn" onClick={handleLogout}>Logout</button>
      </div>
    </div>
  );
};

export default Sidebar;