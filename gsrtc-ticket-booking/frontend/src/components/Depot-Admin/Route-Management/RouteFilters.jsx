const RouteFilters = ({ setSearchTerm, setFilterClass }) => {
  return (
    <div className="route-filters">
      <input
        type="text"
        placeholder="Search route..."
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <select onChange={(e) => setFilterClass(e.target.value)}>
        <option value="">All Classes</option>
        <option value="Express">Express</option>
        <option value="Premium">Premium</option>
        <option value="Sleeper">Sleeper</option>
        <option value="AC">AC</option>
      </select>
      <button type="submit">Search</button>
    </div>
  );
};

export default RouteFilters;
