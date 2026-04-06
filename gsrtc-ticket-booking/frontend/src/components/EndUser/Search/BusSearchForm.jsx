import { useContext, useState } from "react";
import "../../styles/BusSearchForm.css";
import BusResults from "./BusResults";
import axios from "axios";
import { AppContext } from "../../../store/AppContext";

const BusSearchForm = () => {
  const { setBusSearch } = useContext(AppContext);
  const [form, setForm] = useState({
    from: "",
    to: "",
    date: "",
    passengers: 1,
    singleLady: false,
    handicap: false,
  });

  const [results, setResults] = useState([]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handlePassengerChange = (delta) => {
    setForm((prev) => ({
      ...prev,
      passengers: Math.min(6, Math.max(1, prev.passengers + delta)),
    }));
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await axios.get("/api/searchBus/results", {
      params: {
        journeyDate: form.date,
        source: form.from,
        destination: form.to,
        requestedSeats: form.passengers
      }
    });

    console.log(res.data);

    setBusSearch({
      source: form.from,
      destination: form.to,
      journeyDate: form.date,
      requestSeats: form.passengers
    })

    setResults(res.data);
  };

  return (
    <>
      <div className="booking-wrapper">
        <form className="booking-container" onSubmit={handleSubmit}>

          <div className="fields-row">
            <div className="booking-field">
              <span className="field-label">FROM</span>
              <input
                type="text"
                name="from"
                placeholder="Source City"
                value={form.from}
                onChange={handleChange}
                required
              />
            </div>

            <div className="divider" />

            <div className="booking-field">
              <span className="field-label">TO</span>
              <input
                type="text"
                name="to"
                placeholder="Destination City"
                value={form.to}
                onChange={handleChange}
                required
              />
            </div>

            <div className="divider" />

            <div className="booking-field">
              <span className="field-label">DEPARTURE</span>
              <input
                type="date"
                name="date"
                value={form.date}
                onChange={handleChange}
                required
              />
            </div>

            <div className="divider" />

            <div className="booking-field passenger-field">
              <span className="field-label">PASSENGERS</span>
              <div className="passenger-control">
                <button
                  type="button"
                  disabled={form.passengers <= 1}
                  onClick={() => handlePassengerChange(-1)}
                >
                  -
                </button>
                <span>{form.passengers}</span>
                <button
                  type="button"
                  disabled={form.passengers >= 6}
                  onClick={() => handlePassengerChange(1)}
                >
                  +
                </button>
              </div>
            </div>
          </div>

          {/* Floating Button */}
          <button type="submit" className="search-btn-floating">
            Search Buses
          </button>
        </form>
      </div>

      <BusResults results={results} destination={form.to} />
    </>
  );
};

export default BusSearchForm;
