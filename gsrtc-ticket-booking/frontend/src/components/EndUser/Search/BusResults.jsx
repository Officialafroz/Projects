import BusCard from "./BusCard";
import "../../styles/BusResults.css";

const BusResults = ({ results, destination }) => {
  if (results.length === 0) {
    return (
      <div className="no-results">
        <p>No buses found for the selected route.</p>
      </div>
    );
  }

  return (
    <div className="bus-results">
      {results.map((result, index) => (
        <BusCard key={index} result={result} destination={destination} />
      ))}
    </div>
  );
};

export default BusResults;
