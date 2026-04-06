import { useState } from "react";
import { IoIosArrowDown } from "react-icons/io";
import { NavLink } from "react-router-dom";

const EducationalAccordion = () => {
  const [open, setOpen] = useState(false);

  return (
    <div className="sidebar-section">

      {/* Parent */}
      <div
        className="sidebar-parent"
        onClick={() => setOpen(!open)}
      >
        <span>Educational Trips</span>
        <span className={`arrow ${open ? "rotate" : ""}`}><IoIosArrowDown /></span>
      </div>

      {/* Accordion Children */}
      {open && (
        <div className="sidebar-children">

          <NavLink
            to="/admin/edu-booking-management"
            className="sidebar-link"
          >
            Educational Bookings
          </NavLink>

          {/* <NavLink
            to="/admin/booking-management"
            className="sidebar-link"
          >
            Generic Bookings
          </NavLink>

          <NavLink
            to="/admin/passenger-management"
            className="sidebar-link"
          >
            Passenger Management
          </NavLink> */}

        </div>
      )}
    </div>
  );
};

export default EducationalAccordion;