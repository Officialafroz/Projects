import { useState } from "react";
import "../styles/BookingCancellation.css";
import { api } from "../../api/BaseConfig";

const BookingCancellation = () => {
  const [pnr, setPnr] = useState("");
  const [foundBooking, setFoundBooking] = useState(null);
  const [cancelledBookings, setCancelledBookings] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [booking, setBooking] = useState([]);

  const handleSearch = async () => {
    try {

      const res = await api.get(`/api/end-user/ticket/search`, { params: { pnr } });
      const resCancel = await api.delete(`/api/end-user/booking/cancel/${pnr}`);

      if (!res.data) {
        alert("No booking found for this PNR.");
        setFoundBooking(null);
        return;
      }

      setBooking(res.data);
      setFoundBooking(res.data);

    } catch (error) {
      console.log(error);
      alert("Invalid PNR or unauthorized access (401).");
      setFoundBooking(null);
    }
  };


  const handleCancel = () => {
    setShowModal(true);
  };

  const confirmCancellation = () => {
    setCancelledBookings([...cancelledBookings, foundBooking]);
    setFoundBooking(null);
    setShowModal(false);
    alert("Booking successfully cancelled!");
  };

  return (
    <div className="booking-cancellation-container">
      <h2>Booking Cancellation</h2>

      <div className="pnr-search">
        <input
          type="text"
          placeholder="Enter your PNR number"
          value={pnr}
          onChange={(e) => setPnr(e.target.value)}
        />
        <button onClick={handleSearch}>Search</button>
      </div>

      {foundBooking && (
        <div className="booking-info">
          <h3>Booking Details</h3>
          <p><strong>Trip Code:</strong> {booking.tripCode}</p>
          <p><strong>Route:</strong> {booking.route}</p>
          <p><strong>PNR:</strong> {booking.pnr}</p>
          <p><strong>Date:</strong> {booking.journeyDate}</p>
          <p><strong>Class:</strong> {booking.classType}</p>
          {/* <p><strong>Passengers:</strong> {foundBooking.passengers.join(", ")}</p> */}
          {/* <p><strong>Seats:</strong> {foundBooking.seats.join(", ")}</p> */}
          <p><strong>Total Fare:</strong> ₹{foundBooking.totalFare}</p>

          <button className="cancel-btn" onClick={handleCancel}>
            Cancel Ticket
          </button>
        </div>
      )}

      {/* Confirmation Modal */}
      {showModal && (
        <div className="modall-overlay">
          <div className="modall">
            <h1></h1>
            <h3>Confirm Cancellation</h3>
            <p>Are you sure you want to cancel this ticket?</p>
            <div className="modall-actions">
              <button onClick={confirmCancellation} className="confirm">
                Yes, Cancel
              </button>
              <button onClick={() => setShowModal(false)} className="cancel">
                No, Go Back
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Cancelled Bookings Record */}
      {cancelledBookings.length > 0 && (
        <div className="cancelled-section">
          <h3>Cancelled Bookings</h3>
          {cancelledBookings.map((b, index) => (
            <div key={index} className="cancelled-card">
              <p><strong>PNR:</strong> {b.pnr}</p>
              <p><strong>Route:</strong> {b.source} ➜ {b.destination}</p>
              <p><strong>Date:</strong> {b.journeyDate}</p>
              <p><strong>Total Fare:</strong> ₹{b.totalFare}</p>
              <p className="cancelled-status">Status: Cancelled</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default BookingCancellation;
