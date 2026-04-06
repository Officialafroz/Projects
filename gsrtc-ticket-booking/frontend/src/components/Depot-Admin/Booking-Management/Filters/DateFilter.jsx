const DateFilter = ({ setFilters }) => {
  const formatDate = (date) => {
    const [y, m, d] = date.split("-");
    return `${d}-${m}-${y}`; // DD-MM-YYYY
  }

  return (
    <input
      type="date"
      onChange={(e) =>
        setFilters({
          date: e.target.value,
          search: "",
          classType: "",
          revenue: ""
        })
      }
    />
  );
};

export default DateFilter;