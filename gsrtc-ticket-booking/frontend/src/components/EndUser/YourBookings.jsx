import { useState, useEffect, useContext } from "react";
import "../styles/YourBookings.css";
import { AppContext } from "../../store/AppContext";

const YourBookings = () => {
  const { authUser, ticketData, fetchTickets } = useContext(AppContext);
  const [filter, setFilter] = useState("");

  // Load tickets once using user email
  useEffect(() => {
    const email = authUser?.email;
    if (email) fetchTickets(email);
  }, []);

  console.log(ticketData);

  // Filter bookings based on search
  const filteredBookings = ticketData.filter((b) => {
    const search = filter.toLowerCase();
    return (
      b.route?.toLowerCase().includes(search) ||
      b.tripCode?.toLowerCase().includes(search) ||
      b.classType?.toLowerCase().includes(search) ||
      b.journeyDate?.includes(search)
    );
  });

  return (
    <div className="yb-container">
      {/* Search Bar */}
      <div className="yb-search">
        <input
          type="text"
          placeholder="Search by tripcode, route, date or class..."
          value={filter}
          onChange={(e) => setFilter(e.target.value)}
        />
      </div>

      {/* Ticket List */}
      <div className="yb-list">
        {ticketData.length > 0 ? (
          ticketData.map((b, index) => (
            <div key={index} className="yb-card">

              {/* Header */}
              <div className="yb-header">
                <div>
                  <h3>{b.route}</h3>
                  <p className="yb-date">{b.journeyDate}</p>
                </div>
                <span className="yb-chip">{b.classType}</span>
              </div>

              {/* Trip Info */}
              <div className="yb-block">
                <p><strong>Trip Code:</strong> {b.tripCode}</p>
                <p><strong>PNR:</strong> {b.pnr}</p>
                <p><strong>Boarding Time:</strong> {b.boardingTime ?? "—"}</p>
                <p><strong>Seats:</strong> {b.seats.join(", ")}</p>
                <p><strong>Passengers:</strong> {b.passengers.join(", ")}</p>
              </div>

              {/* Fare */}
              <div className="yb-fare-box">
                <h4 className="f-summary">Fare Summary</h4>
                <p className="yb-total">₹ {b.totalFare}</p>
              </div>
            </div>
          ))
        ) : (
          <p className="yb-empty">No bookings found.</p>
        )}
      </div>
    </div>
  );
};

export default YourBookings;
