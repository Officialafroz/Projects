import { useState } from "react";
import { IoIosArrowDown } from "react-icons/io";
import { NavLink } from "react-router-dom";

const PricingAccordian = () => {
  const [open, setOpen] = useState(false);

  return (
    <div className="sidebar-section">

      {/* Parent */}
      <div
        className="sidebar-parent"
        onClick={() => setOpen(!open)}
      >
        <span>Depot</span>
        <span className={`arrow ${open ? "rotate" : ""}`}><IoIosArrowDown /></span>
      </div>

      {/* Accordion Children */}
      {open && (
        <div className="sidebar-children">

          <NavLink
            to="/super-admin/reservation-policies"
            className="sidebar-link"
          >
            Reservation
          </NavLink>

          <NavLink
            to="/super-admin/cancellation-policies"
            className="sidebar-link"
          >
            Cancellation
          </NavLink>
        </div>
      )}
    </div>
  );
};

export default PricingAccordian;