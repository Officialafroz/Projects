import { useState, useContext } from "react";
import "../../styles/PaymentSummary.css";
import PaymentConfirmModal from "./PaymentConfirmModal";
import { AppContext } from "../../../store/AppContext";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const PaymentSummary = ({ totalAmount }) => {
  const { passengers, tripCode, setTripCode, authUser, busSearch } = useContext(AppContext);
  const [showConfirm, setShowConfirm] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const navigate = useNavigate();

  const handlePaymentClick = () => {
    setShowConfirm(true);
  };

  const handleConfirmPayment = async () => {
    try {
      setShowConfirm(false);

      const bookingRes = await axios.post("/api/end-user/booking", {
        email: authUser.email,
        tripCode,
        journeyDate: busSearch.journeyDate,
        totalFare: totalAmount
      });

      const pnr = bookingRes.data;

      await axios.post("/api/end-user/passenger", passengers, {
        params: { tripCode, pnr }
      });

      const payRes = await axios.post("/api/end-user/payment/pay", null, {
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
    <div className="payment-summary">
      <h3>Payment Summary</h3>

      {passengers.map((p, index) => (
        <p key={index}>
          {p.fullName} (Seat {p.seat}) – ₹{p.fare}
        </p>
      ))}

      <hr />

      {!isSuccess ? (
        <>
          <h4>Total Fare: ₹{totalAmount}</h4>
          <button className="pay-btn" onClick={handlePaymentClick}>
            Pay ₹{totalAmount}
          </button>
        </>
      ) : (
        <div className="payment-success">
          <h4>✅ Payment Successful!</h4>
          <p>Your booking has been confirmed.</p>
        </div>

      )}

      {/* Confirmation Modal */}
      <PaymentConfirmModal
        isOpen={showConfirm}
        onClose={() => setShowConfirm(false)}
        onConfirm={handleConfirmPayment}
      />
    </div>
  );
};

export default PaymentSummary;
