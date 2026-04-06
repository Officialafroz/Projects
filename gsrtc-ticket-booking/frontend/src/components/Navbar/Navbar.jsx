import { NavLink, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";
import { useContext } from "react";
import { logout } from "../../api/Logout.jsx";
import ProfileDropdown from "../ProfileDropDown";
import { AppContext } from "../../store/AppContext.jsx";

// Session restore is handled once in AppContent (App.jsx).
// Navbar only reads authUser from context.

const Navbar = () => {
  const navigate = useNavigate();
  const { authUser, setAuthUser } = useContext(AppContext);

  const handleLogout = async () => {
    const res = await logout();
    setAuthUser(null);
    alert(res.data);
    navigate("/");
  };

  return (
    <nav className="navbarr">
      <div className="upper-nav">

        <div className="navbar-logo" onClick={() => navigate("/")}>
          <h2>GSRTC</h2>
        </div>

        {authUser && (
          <div className="lower-nav">
            <ul className="navbar-links">
              <li><NavLink to="/bus-booking" className={({ isActive }) => isActive ? "active" : ""}>Home</NavLink></li>
              <li><NavLink to="/educational-booking">Educational Booking</NavLink></li>
              <li><NavLink to="/your-bookings">Your Bookings</NavLink></li>
              <li><NavLink to="/booking-cancellation">Booking Cancellation</NavLink></li>
              <li><NavLink to="/contact-us">Contact Us</NavLink></li>
              <li><NavLink to="/about-us">About Us</NavLink></li>
            </ul>
          </div>
        )}

        <div className="navbar-actions">
          {!authUser ? (
            <>
              <button onClick={() => navigate("/admin-login")} className="btn btn-outline-primary">Admin Login</button>
              <button onClick={() => navigate("/signin")} className="btn btn-primary">Sign In</button>
              <button onClick={() => navigate("/signup")} className="btn btn-primary">Sign Up</button>
            </>
          ) : (
            <ProfileDropdown authUser={authUser} handleLogout={handleLogout} />
          )}
        </div>

      </div>
    </nav>
  );
};

export default Navbar;