import { useState } from "react";
import "../../styles/Policy.css";

const defaultPolicy = `Cancellation Charges:
0-1 Day cancellation charges 25 % of basic fare.
2-5 Days cancellation charges 20 % of basic fare.
6-60 Days cancellation charges 15 % of basic fare.
No Refunds are applicable for Current Booking Transactions.
No cancellation will be allowed (Advance Booking / Current Booking) after departure of bus from original place.
Refund will be processed within 7 to 21 working days.`;

const CancellationPolicyPage = () => {
  const [policyText, setPolicyText] = useState(defaultPolicy);
  const [isEditing, setIsEditing] = useState(false);

  const handleSave = () => {
    // 🔥 Replace with API call
    // await updateCancellationPolicy(policyText);

    alert("Cancellation policy updated successfully");
    setIsEditing(false);
  };

  return (
    <div className="policy-container">

      <h2>Cancellation Policy Management</h2>

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

export default CancellationPolicyPage;