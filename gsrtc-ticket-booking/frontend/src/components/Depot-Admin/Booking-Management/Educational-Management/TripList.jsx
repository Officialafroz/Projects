import TripCard from "./TripCard.jsx";

const TripList = ({ trips, onCardClick, onApprove, onReject, onCancel }) => {
  return (
    <div className="trip-grid">
      {trips.map((trip) => (
        <TripCard
          key={trip.tripRequestId}
          trip={trip}
          onClick={() => onCardClick(trip)}
          onApprove={() => onApprove(trip)}
          onReject={() => onReject(trip.tripRequestId)}
          onCancel={() => onCancel(trip.tripRequestId)}
        />
      ))}
    </div>
  );
};

export default TripList;