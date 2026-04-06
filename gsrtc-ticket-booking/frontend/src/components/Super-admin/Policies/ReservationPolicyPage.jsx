import { useState } from "react";
import "../../styles/Policy.css";

const defaultPolicy = `Reservation Charges:
From 16-01-2014 GSRTC had reduced reservation charges for Luxury, Sleeper, AC and Volvo services.
Reservation Charges for online & mobile booking is Rs. 5/-.
Reservation Charges are not applicable for Tickets booked under Current Booking Mode.
Services will be available for 60 days advance booking. Trip sheet will be prepared before 30 minutes of departure time.
Passengers are allowed to book the tickets till the preparation of the trip sheet.
Passengers are allowed to do Current Booking Transactions after the printing of the Trip Sheets and Seats are not assured for Tickets booked under Current Booking Mode with no extra reservation charges.`;

const ReservationPolicyPage = () => {
  const [policyText, setPolicyText] = useState(defaultPolicy);
  const [isEditing, setIsEditing] = useState(false);

  const handleSave = () => {
    // 🔥 Replace with API call
    // await updateReservationPolicy(policyText);

    alert("Policy updated successfully");
    setIsEditing(false);
  };

  return (
    <div className="policy-container">

      <h2>Reservation Policy Management</h2>

      <div className="policy-card">
        {isEditing ? (
          <>
            <textarea
              value={policyText}
              onChange={(e) => setPolicyText(e.target.value)}
              rows={10}
            />

            <div className="policy-actions">
              <button className="save-btn" onClick={handleSave}>
                Save
              </button>
              <button
                className="cancel-btn"
                onClick={() => setIsEditing(false)}
              >
                Cancel
              </button>
            </div>
          </>
        ) : (
          <>
            <div className="policy-text">
              {policyText.split("\n").map((line, i) => (
                <p key={i}>{line}</p>
              ))}
            </div>

            <button
              className="edit-btn"
              onClick={() => setIsEditing(true)}
            >
              Edit Policy
            </button>
          </>
        )}
      </div>

    </div>
  );
};

export default ReservationPolicyPage;