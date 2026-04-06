import {
  IoArrowBack,
  IoLocationOutline,
  IoBusOutline,
  IoCalendarOutline,
  IoCallOutline,
  IoBusinessOutline,
  IoBedOutline,
} from "react-icons/io5";

/**
 * TripDetailPage
 * In-page detail view rendered when a TripCard is clicked.
 * All fields sourced from the backend TripDetailDTO.
 */
const TripDetailPage = ({ trip, onBack }) => {
  const formatDate = (dt) => {
    if (!dt) return "—";
    return new Date(dt).toLocaleString("en-IN", {
      day: "2-digit",
      month: "short",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  const formatBudget = (b) =>
    b != null ? `₹${Number(b).toLocaleString("en-IN")}` : "—";

  return (
    <div className="td-root">

      {/* ── Back button ── */}
      <button className="td-back" onClick={onBack}>
        <IoArrowBack size={16} />
        Back to Bookings
      </button>

      {/* ── Hero header ── */}
      <div className="td-hero">
        <div className="td-hero__left">
          <span className="td-hero__eyebrow">Trip Request</span>
          <h2 className="td-hero__title">{trip.tripName || "—"}</h2>
          <p className="td-hero__institute">
            <IoBusinessOutline size={14} />
            {trip.instituteName || "—"}
            {trip.instituteCode && (
              <span className="td-hero__code">&nbsp;· {trip.instituteCode}</span>
            )}
          </p>
        </div>
        <div className="td-hero__right">
          <span className={`td-badge td-badge--${trip.tripStatus?.toLowerCase()}`}>
            {trip.tripStatus || "—"}
          </span>
          {trip.instituteType && (
            <span className="td-hero__type">{trip.instituteType}</span>
          )}
        </div>
      </div>

      {/* ── Body ── */}
      <div className="td-body">

        {/* ROUTE */}
        <section className="td-section">
          <div className="td-section__head">
            <IoLocationOutline size={15} />
            <span>Route</span>
          </div>
          <div className="td-route">
            <div className="td-route__stop">
              <div className="td-route__dot td-route__dot--source" />
              <div className="td-route__line" />
              <div className="td-route__info">
                <p className="td-route__label">Source</p>
                <p className="td-route__place">{trip.sourceLocation || "—"}</p>
              </div>
            </div>

            {(trip.destinationLocations || []).map((dest, i, arr) => (
              <div className="td-route__stop" key={i}>
                <div className={`td-route__dot ${i === arr.length - 1 ? "td-route__dot--dest" : ""}`} />
                {i < arr.length - 1 && <div className="td-route__line" />}
                <div className="td-route__info">
                  <p className="td-route__label">
                    {arr.length > 1 ? `Destination ${i + 1}` : "Destination"}
                  </p>
                  <p className="td-route__place">{dest}</p>
                </div>
              </div>
            ))}
          </div>
        </section>

        {/* TRIP DETAILS */}
        <section className="td-section">
          <div className="td-section__head">
            <IoCalendarOutline size={15} />
            <span>Trip Details</span>
          </div>
          <div className="td-grid">
            <InfoTile label="Start Date & Time" value={formatDate(trip.tripStartDatetime)} />
            <InfoTile label="Duration" value={trip.tripDays != null ? `${trip.tripDays} Day${trip.tripDays !== 1 ? "s" : ""}` : "—"} />
            <InfoTile label="Bus Class" value={trip.busClass || "—"} />
            <InfoTile label="Hotel Required" value={trip.hotelRequired === true ? "Yes" : trip.hotelRequired === false ? "No" : "—"} />
          </div>
        </section>

        {/* CAPACITY & BUDGET */}
        <section className="td-section">
          <div className="td-section__head">
            <IoBusOutline size={15} />
            <span>Capacity &amp; Budget</span>
          </div>
          <div className="td-grid">
            <InfoTile label="Requested Buses" value={trip.requestedBuses ?? "—"} highlight />
            <InfoTile label="Total Persons" value={trip.totalPersons ?? "—"} highlight />
            <InfoTile label="Budget" value={formatBudget(trip.budget)} highlight accent />
          </div>
        </section>

        {/* HOTELS — only when hotelRequired and list exists */}
        {trip.hotelRequired && (trip.hotels?.length ?? 0) > 0 && (
          <section className="td-section">
            <div className="td-section__head">
              <IoBedOutline size={15} />
              <span>Hotels</span>
            </div>
            <div className="td-tags">
              {trip.hotels.map((h, i) => (
                <span className="td-tag" key={i}>{h}</span>
              ))}
            </div>
          </section>
        )}

        {/* EMERGENCY CONTACT */}
        {trip.emergencyContact && (
          <section className="td-section td-section--slim">
            <div className="td-section__head">
              <IoCallOutline size={15} />
              <span>Emergency Contact</span>
            </div>
            <a className="td-contact" href={`tel:${trip.emergencyContact}`}>
              {trip.emergencyContact}
            </a>
          </section>
        )}

      </div>
    </div>
  );
};

/* ── Reusable info tile ── */
const InfoTile = ({ label, value, highlight, accent }) => (
  <div className={`td-tile${highlight ? " td-tile--hl" : ""}${accent ? " td-tile--accent" : ""}`}>
    <p className="td-tile__label">{label}</p>
    <p className="td-tile__value">{value}</p>
  </div>
);

export default TripDetailPage;