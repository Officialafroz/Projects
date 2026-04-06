const BookingFilters = ({ setFilters }) => {
  return (
    <div className="booking-filters">

      <select
        onChange={(e) =>
          setFilters(prev => ({
            ...prev,
            classType: e.target.value,
            date: ""
          }))
        }
      >
        <option value="">All Classes</option>
        <option value="express">Express</option>
        <option value="luxury">Luxury</option>
        <option value="sleeper">Sleeper</option>
      </select>

      <select
        onChange={(e) =>
          setFilters(prev => ({
            ...prev,
            revenue: e.target.value,
            date: ""
          }))
        }
      >
        <option value="">All Revenue</option>
        <option value="high">High (&gt; ₹20,000)</option>
        <option value="low">Low (≤ ₹20,000)</option>
      </select>

    </div>
  );
};

export default BookingFilters;
