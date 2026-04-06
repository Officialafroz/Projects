import React, { useContext, useEffect, useState } from "react";
import "../../styles/SeatLayout.css";
import { MdEventSeat } from "react-icons/md";
import axios from "axios";
import { AppContext } from "../../../store/AppContext";

const SeatLayout = ({ selectedSeats, toggleSeat }) => {
  const { tripCode } = useContext(AppContext);
  let totalSeats = axios.get("/api/end-user/seats", { params: { tripCode } }).data;

  const [bookedSeats, setBookedSeats] = useState([]);

  useEffect(() => {
    if (!tripCode) return;

    const fetchBookedSeats = async () => {
      const res = await axios.get("/api/end-user/seats/map", {
        params: { tripCode }
      });

      setBookedSeats(res.data); // array of booked seat numbers
      console.log(res.data);
    };

    fetchBookedSeats();
  }, [tripCode]);

  const seats = Array.from({ length: 46 }, (_, i) => i + 1);


  const isBooked = (seat) => bookedSeats.includes(seat);

  return (
    <div className="seat-layout">
      {seats.map((seat) => (
        <div
          key={seat}
          className={`seat 
            ${selectedSeats.includes(seat) ? "selected" : ""}
            ${isBooked(seat) ? "booked" : ""}
          `}
          onClick={() => {
            if (!isBooked(seat)) toggleSeat(seat);
          }}
        >
          <MdEventSeat />
          {seat}
        </div>
      ))}
    </div>
  );
};

export default SeatLayout;
