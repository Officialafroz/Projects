import { useContext, useState } from "react";
import { AppContext } from "../../../store/AppContext";
import { useNavigate } from "react-router-dom";
import { api } from "../../../api/BaseConfig";

const PaymentModal = ({ baseFare, gst, reservationFee, totalAmount, onCancel, onPay, loading }) => {
  const { passengers, tripCode, authUser, busSearch } = useContext(AppContext);
  const [showConfirm, setShowConfirm] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const navigate = useNavigate();

  const handlePaymentClick = () => {
    setShowConfirm(true);
  };

  const handleConfirmPayment = async () => {
    try {
      // setShowConfirm(false);

      console.log(tripCode);

      const bookingRes = await api.post("/api/end-user/booking", {
        email: authUser.email,
        tripCode,
        journeyDate: busSearch.journeyDate,
        totalFare: totalAmount
      });

      const pnr = bookingRes.data;

      console.log(passengers);

      const passengerRes = await api.post("/api/end-user/passenger/save", passengers, {
        params: { tripCode, pnr }
      });

      console.log(passengerRes.data);

      const payRes = await api.post("/api/end-user/payment/pay", null, {
        params: { totalAmount, pnr }
      });

      console.log(payRes);

      setTimeout(() => {
        setIsSuccess(true);
      }, 200);

      // setTripCode("");    // resetting may cause re-render issues
      navigate("/bus-booking");

    } catch (err) {
      console.error("Payment failed:", err);
    }
  };


  return (
    <div className="sub-modal">
      <div className="sub-modal-content small">
        <h2>Fare Breakdown</h2>

        <div className="fare-row">
          <span>Seats Fare</span>
          <span>₹{baseFare.toFixed(2)}</span>
        </div>

        <div className="fare-row">
          <span>GST (5%)</span>
          <span>₹{gst.toFixed(2)}</span>
        </div>

        <div className="fare-row">
          <span>Reservation Fee</span>
          <span>₹{reservationFee.toFixed(2)}</span>
        </div>

        <hr />

        <div className="fare-row total">
          <span>Total</span>
          <span>₹{totalAmount.toFixed(2)}</span>
        </div>

        <div className="modal-footer">
          <button onClick={onCancel} disabled={loading}>
            Cancel
          </button>
          <button
            className="primary-btn"
            onClick={handleConfirmPayment}
            disabled={loading}
          >
            {loading ? "Processing..." : `Pay ₹${totalAmount.toFixed(2)}`}
          </button>
        </div>
      </div>
    </div>
  );
}

export default PaymentModal;