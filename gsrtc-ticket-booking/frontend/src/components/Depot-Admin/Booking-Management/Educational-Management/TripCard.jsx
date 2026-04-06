const TripCard = ({ trip, onClick, onApprove, onReject, onCancel }) => {
  const isApproved = trip.tripStatus === "APPROVED";
  const isRejected = trip.tripStatus === 'REJECTED';

  const handleActionClick = (e, handler) => {
    e.stopPropagation(); // prevent card click when clicking buttons
    handler();
  };

  return (
    <div
      className={`trip-card ${isApproved ? "trip-card--approved" : ""}`}
      onClick={onClick}
    >
      <h4 className="trip-card__title">{trip.tripName}</h4>
      <p className="trip-card__institute">{trip.instituteName}</p>

      <div className="trip-meta">
        <span>{trip.sourceLocation}</span>
        <span>{trip.tripDays} Days</span>
      </div>

      <div className="trip-footer">
        <span className="trip-card__budget">₹{trip.budget}</span>
        <span className={`status status--${trip.tripStatus?.toLowerCase()}`}>
          {trip.tripStatus}
        </span>
      </div>

      {/* Action buttons — hidden when APPROVED */}
      {!isApproved && !isRejected && (
        <div className="trip-actions">
          <button
            className="btn btn--accept"
            onClick={(e) => handleActionClick(e, onApprove)}
          >
            Accept
          </button>

          <button
            className="btn btn--reject"
            onClick={(e) => handleActionClick(e, onReject)}
          >
            Reject
          </button>

          {/* <button
            className="btn btn--cancel"
            onClick={(e) => handleActionClick(e, onCancel)}
          >
            Cancel Request
          </button> */}
        </div>
      )}
    </div>
  );
};

export default TripCard;