import { useContext, useState } from "react";
import { AppContext } from "../../../store/AppContext";

const PassengerModal = ({ seats, stops, onCancel, onConfirm }) => {
  const { busSearch } = useContext(AppContext);

  const [currentIndex, setCurrentIndex] = useState(0);

  const [passengers, setPassengers] = useState(
    seats.map((seat) => ({
      seat,
      fullName: "",
      age: 0,
      gender: "",
      boardingPoint: "",
      destination: busSearch.destination
    }))
  );

  const currentPassenger = passengers[currentIndex];

  const handleChange = (field, value) => {
    const updated = [...passengers];
    updated[currentIndex][field] = value;
    setPassengers(updated);
  };

  const isCurrentValid =
    currentPassenger.fullName.trim() !== "" &&
    currentPassenger.age > 0 &&
    currentPassenger.gender !== "" &&
    currentPassenger.boardingPoint !== "";

  const handleNext = () => {
    if (!isCurrentValid) return;
    setCurrentIndex((prev) => prev + 1);
  };

  const handleBack = () => {
    setCurrentIndex((prev) => prev - 1);
  };

  const handleConfirm = () => {
    if (!isCurrentValid) return;
    onConfirm(passengers);
  };

  return (
    <div className="sub-modal">
      <div className="sub-modal-content single-passenger">

        <h2>
          Passenger {currentIndex + 1} of {seats.length}
        </h2>

        <p className="seat-indicator">
          Seat Number: <strong>{currentPassenger.seat}</strong>
        </p>

        <div className="single-form">

          <input
            type="text"
            placeholder="Full Name"
            value={currentPassenger.fullName}
            onChange={(e) =>
              handleChange("fullName", e.target.value)
            }
          />

          <input
            type="number"
            placeholder="Age"
            value={currentPassenger.age}
            onChange={(e) =>
              handleChange("age", Number(e.target.value))
            }
          />

          <select
            value={currentPassenger.gender}
            onChange={(e) =>
              handleChange("gender", e.target.value)
            }
          >
            <option value="">Gender</option>
            <option>Male</option>
            <option>Female</option>
            <option>Other</option>
          </select>

          <select
            value={currentPassenger.boardingPoint}
            onChange={(e) =>
              handleChange("boardingPoint", e.target.value)
            }
          >
            <option value="">Boarding Point</option>
            {stops?.map((stop) => (
              <option
                key={stop.stopId}
                value={stop.stopName}
              >
                {stop.stopName}
              </option>
            ))}
          </select>

        </div>

        <div className="modal-footer">

          <button onClick={onCancel}>
            Cancel
          </button>

          {currentIndex > 0 && (
            <button onClick={handleBack}>
              Back
            </button>
          )}

          {currentIndex < seats.length - 1 ? (
            <button
              className="primary-btn"
              disabled={!isCurrentValid}
              onClick={handleNext}
            >
              Next
            </button>
          ) : (
            <button
              className="primary-btn"
              disabled={!isCurrentValid}
              onClick={handleConfirm}
            >
              Confirm & Pay
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default PassengerModal;