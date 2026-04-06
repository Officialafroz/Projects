import { useContext, useState } from "react";
import SeatLayout from "./SeatLayout.jsx";
import PassengerModal from "./PassengerModal.jsx";
import PaymentModal from "./PaymentModal.jsx";
import SuccessModal from "./SuccessModal.jsx";
import "../../styles/SeatSelectionModal.css";
import "../../styles/BookingModal.css";
import PaymentSummary from "./PaymentSummary.jsx";
import { AppContext } from "../../../store/AppContext.jsx";

const SeatSelectionModal = ({ stops, isOpen, onClose, bus }) => {
  const { setPassengers } = useContext(AppContext);

  const [selectedSeats, setSelectedSeats] = useState([]);
  const [showPassengerModal, setShowPassengerModal] = useState(false);
  const [showPaymentModal, setShowPaymentModal] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [passengerData, setPassengerData] = useState([]);
  const [processingPayment, setProcessingPayment] = useState(false);

  const seatPrice = bus?.seatRate;

  if (!isOpen) return null;

  const toggleSeat = (seat) => {
    setSelectedSeats((prev) =>
      prev.includes(seat)
        ? prev.filter((s) => s !== seat)
        : [...prev, seat]
    );
  };

  const openPassengerModal = () => {
    if (selectedSeats.length === 0) return;
    setShowPassengerModal(true);
  };

  const handlePassengerSubmit = (data) => {
    setPassengers(data);
    setPassengerData(data);
    setShowPassengerModal(false);
    setShowPaymentModal(true);
  };

  const baseFare = selectedSeats.length * seatPrice;
  const gst = baseFare * 0.05;
  const reservationFee = 5;
  const total = baseFare + gst + reservationFee;

  const handlePayment = () => {
    setProcessingPayment(true);

    setTimeout(() => {
      setProcessingPayment(false);
      setShowPaymentModal(false);
      setShowSuccessModal(true);

      setTimeout(() => {
        setShowSuccessModal(false);
        setSelectedSeats([]);
        onClose();
      }, 5000);
    }, 1500);
  };

  return (
    <div className="modal-overlay">
      <div className="modal-container large">

        <div className="modal-header">
          <button onClick={onClose} className="close-btn  ">✕</button>
        </div>

        {/* MAIN BODY */}
        <div className="seat-layout-wrapper">

          {/* LEFT - SEATS */}
          <div className="seat-section">
            <SeatLayout
              selectedSeats={selectedSeats}
              toggleSeat={toggleSeat}
            />
          </div>

          {/* RIGHT - RESERVED SPACE */}
          {/* <div className="side-section">
            <h3>Trip Summary</h3>
            <p>Selected Seats: {selectedSeats.join(", ") || "None"}</p>
            <p>Fare per Seat: ₹{seatPrice}</p>
          </div> */}
        </div>

        {/* BOTTOM ACTION */}
        <div className="bottom-action">
          <button
            className="primary-btn"
            disabled={selectedSeats.length === 0}
            onClick={openPassengerModal}
          >
            Add Passenger Details ({selectedSeats.length})
          </button>
        </div>

      </div>

      {/* PASSENGER MODAL */}
      {showPassengerModal && (
        <PassengerModal
          seats={selectedSeats}
          stops={stops}
          onCancel={() => setShowPassengerModal(false)}
          onConfirm={handlePassengerSubmit}
        />
      )}

      {/* PAYMENT MODAL */}
      {showPaymentModal && (
        <PaymentModal
          baseFare={baseFare}
          gst={gst}
          reservationFee={reservationFee}
          totalAmount={total}
          onCancel={() => setShowPaymentModal(false)}
          onPay={handlePayment}
          loading={processingPayment}
        />

        // <PaymentSummary total={total} />
      )}

      {/* SUCCESS MODAL */}
      {showSuccessModal && (
        <SuccessModal />
      )}
    </div>
  );
};

export default SeatSelectionModal;