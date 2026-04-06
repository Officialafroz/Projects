import { useState } from "react";
import { IoIosArrowDown } from "react-icons/io";
import { NavLink } from "react-router-dom";

const DepotAccordian = () => {
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
            to="/super-admin/bus-depot-management"
            className="sidebar-link"
          >
            Management
          </NavLink>

          <NavLink
            to="/super-admin/depot-overview"
            className="sidebar-link"
          >
            Overviews
          </NavLink>
        </div>
      )}
    </div>
  );
};

export default DepotAccordian;