import { useContext, useState } from "react";
import SeatSelectionModal from "../Booking/SeatSelectionModal";
import "../../styles/BusCard.css";
import { AppContext } from "../../../store/AppContext";

const BusCard = ({ result }) => {
  const [showModal, setShowModal] = useState(false);
  const { setTripCode } = useContext(AppContext);

  const tripCode = result.tripCode;

  const handleSeatSelect = () => {
    setTripCode(tripCode);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <>
      <div className="bus-card">
        <div className="bus-details">

          <div className="left-cont">
            <h3>{tripCode}</h3>
            <p>{result.classType}</p>
          </div>

          <div className="bus-desc">
            <h3>{result.routeName}</h3>
            <p className="route-via">via</p>
            {result.stops.map((stop, index) => (
              <span key={index}>
                {stop.stopName}
                {index !== result.stops.length - 1 && ", "}
              </span>
            ))}
          </div>

          <p>
            {result.departureTime} - {result.destinationTime}
          </p>

          <p className="fare">
            Fare: ₹{result.seatRate}
          </p>

        </div>

        <button
          className="select-seat-btn"
          onClick={handleSeatSelect}
          disabled={result.vacantSeats === 0}
        >
          {result.vacantSeats === 0
            ? "Sold Out"
            : `${result.vacantSeats} Seats`}
        </button>
      </div>

      {/* Updated Modal Usage */}
      <SeatSelectionModal
        isOpen={showModal}
        onClose={handleCloseModal}
        stops={result.stops}
        bus={result}
      />
    </>
  );
};

export default BusCard;