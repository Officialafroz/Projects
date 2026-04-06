import { useState } from "react";
import styles from "./BusFilter.module.css";

const BusFilter = ({ onFilterChange }) => {
  const [filters, setFilters] = useState({
    time: "",
    date: "",
    classType: "",
    fareRange: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    const newFilters = { ...filters, [name]: value };
    setFilters(newFilters);
    onFilterChange(newFilters);
  };

  return (
    <div className={styles.filterContainer}>
      <div className={styles.filterGroup}>
        <label>Time</label>
        <select name="time" value={filters.time} onChange={handleChange}>
          <option value="">All</option>
          <option value="morning">Morning (6AM - 12PM)</option>
          <option value="afternoon">Afternoon (12PM - 6PM)</option>
          <option value="evening">Evening (6PM - 12AM)</option>
          <option value="night">Night (12AM - 6AM)</option>
        </select>
      </div>

      <div className={styles.filterGroup}>
        <label>Date</label>
        <input type="date" name="date" value={filters.date} onChange={handleChange} />
      </div>

      <div className={styles.filterGroup}>
        <label>Class</label>
        <select name="classType" value={filters.classType} onChange={handleChange}>
          <option value="">All</option>
          <option value="express">Express</option>
          <option value="sleeper">Sleeper</option>
          <option value="luxury">Luxury</option>
          <option value="ac">AC</option>
        </select>
      </div>

      <div className={styles.filterGroup}>
        <label>Fare</label>
        <select name="fareRange" value={filters.fareRange} onChange={handleChange}>
          <option value="">All</option>
          <option value="0-500">₹0 - ₹500</option>
          <option value="500-1000">₹500 - ₹1000</option>
          <option value="1000-2000">₹1000 - ₹2000</option>
          <option value="2000+">₹2000+</option>
        </select>
      </div>
    </div>
  );
};

export default BusFilter;
