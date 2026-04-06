const BookingSearch = ({ setFilters }) => {
  return (
    <input
      type="text"
      placeholder="Search by Trip Code or Route"
      onChange={(e) =>
        setFilters(prev => ({
          ...prev,
          search: e.target.value,
          date: ""   // date is exclusive
        }))
      }
    />
  );
};

export default BookingSearch;
