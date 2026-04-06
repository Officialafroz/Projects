import { useState, useEffect } from "react";
import { createPortal } from "react-dom";
import { createBooking } from "../../../api/EducationalBookingApi";
import { pay } from "../../../api/PaymentApi";
import { getUserTrips } from "../../../api/EduManagementApi";

/* ── Status config ── */
const STATUS_META = {
  APPROVED: { label: "Approved", color: "green", emoji: "✅" },
  PENDING: { label: "Pending", color: "amber", emoji: "⏳" },
  UNDER_PROCESS: { label: "Under Process", color: "blue", emoji: "🔄" },
  REJECTED: { label: "Rejected", color: "red", emoji: "❌" },
  CANCELLED: { label: "Cancelled", color: "grey", emoji: "🚫" },
};

const fmt = (b) => b != null ? `₹${Number(b).toLocaleString("en-IN")}` : "—";
const fmtDt = (dt) => dt ? new Date(dt).toLocaleString("en-IN", { day: "2-digit", month: "short", year: "numeric", hour: "2-digit", minute: "2-digit" }) : "—";

/* ================================================================
   PAYMENT MODAL
   ================================================================ */
const PaymentModal = ({ trip, onClose, onSuccess }) => {
  const [method, setMethod] = useState(""); // "CASH" | "UPI"
  const [upiId, setUpiId] = useState("");
  const [paying, setPaying] = useState(false);
  const [error, setError] = useState("");

  const advanceAmount = trip.budget
    ? Number(trip.budget) * 0.5
    : Number(trip.requestDto?.budget ?? 0) * 0.5;

  const handlePay = async () => {
    if (!method) { setError("Please select a payment method."); return; }
    if (method === "UPI" && !upiId.trim()) { setError("Please enter your UPI ID."); return; }

    setPaying(true);
    setError("");
    try {
      // Step 1 — create booking
      const bookingRes = await createBooking(trip.requestId);

      console.log(trip.requestId);

      // Step 2 — initiate payment

      const payObj = {
        requestId: trip.requestId,
        amount: advanceAmount,
        method
      }

      const paymentRes = await pay(payObj)

      console.log(paymentRes);

      // alert(paymentRes.data);

      onSuccess();
    } catch (err) {
      console.log(err);
      setError(err?.response?.data?.message ?? "Payment failed. Please try again.");
    } finally {
      setPaying(false);
    }
  };

  const content = (
    <div className="pm-overlay" onClick={onClose}>
      <div className="pm-box" onClick={(e) => e.stopPropagation()}>

        {/* Header */}
        <div className="pm-header">
          <div>
            <p className="pm-header__label">Advance Payment · 50%</p>
            <p className="pm-header__amount">{fmt(advanceAmount)}</p>
          </div>
          <button className="pm-close" onClick={onClose} aria-label="Close">✕</button>
        </div>

        {/* Trip summary */}
        <div className="pm-summary">
          <span className="pm-summary__name">{trip.tripName}</span>
          <span className="pm-summary__inst">{trip.instituteName}</span>
        </div>

        <div className="pm-divider" />

        {/* Fare breakdown */}
        <div className="pm-fare">
          <div className="pm-fare__row">
            <span>Total Fare</span>
            <span>{fmt(trip.totalFare ?? trip.requestDto?.budget)}</span>
          </div>
          <div className="pm-fare__row pm-fare__row--advance">
            <span>Advance (50%)</span>
            <span className="pm-fare__advance">{fmt(advanceAmount)}</span>
          </div>
          <div className="pm-fare__row pm-fare__row--remaining">
            <span>Remaining (due later)</span>
            <span>{fmt(advanceAmount)}</span>
          </div>
        </div>

        <div className="pm-divider" />

        {/* Payment method */}
        <p className="pm-method-label">Select Payment Method</p>
        <div className="pm-methods">
          <button
            className={`pm-method-btn${method === "CASH" ? " pm-method-btn--active" : ""}`}
            onClick={() => { setMethod("CASH"); setUpiId(""); setError(""); }}
          >
            <span className="pm-method-btn__icon">💵</span>
            <span>Cash</span>
          </button>
          <button
            className={`pm-method-btn${method === "UPI" ? " pm-method-btn--active" : ""}`}
            onClick={() => { setMethod("UPI"); setError(""); }}
          >
            <span className="pm-method-btn__icon">📱</span>
            <span>UPI</span>
          </button>
        </div>

        {method === "UPI" && (
          <div className="pm-upi">
            <label className="pm-upi__label">UPI ID</label>
            <input
              className="pm-upi__input"
              type="text"
              placeholder="yourname@upi"
              value={upiId}
              onChange={(e) => { setUpiId(e.target.value); setError(""); }}
            />
          </div>
        )}

        {method === "CASH" && (
          <p className="pm-cash-note">
            💬 Please visit your nearest GSRTC depot to complete the cash payment.
          </p>
        )}

        {error && <p className="pm-error">{error}</p>}

        <button
          className="pm-pay-btn"
          onClick={handlePay}
          disabled={paying || !method}
        >
          {paying
            ? <span className="eb-spinner" />
            : `Pay ${fmt(advanceAmount)}`}
        </button>

      </div>
    </div>
  );

  return createPortal(content, document.body);
};

/* ================================================================
   TRIP / BOOKING CARD
   ================================================================ */
const TripCard = ({ trip, onPayClick }) => {
  const [expanded, setExpanded] = useState(false);
  const meta = STATUS_META[trip.tripStatus] || { label: trip.tripStatus, color: "grey", emoji: "•" };

  const totalFare = trip.totalFare ?? trip.requestDto?.budget ?? trip.budget;
  const advanceFare = totalFare ? Number(totalFare) * 0.5 : null;
  const remaining = advanceFare;

  return (
    <div className={`eb-tc eb-tc--${meta.color}`}>

      {/* ── Summary row (always visible) ── */}
      <div className="eb-tc__header" onClick={() => setExpanded((v) => !v)}>
        <div className="eb-tc__left">
          <span className="eb-tc__emoji">{meta.emoji}</span>
          <div>
            <p className="eb-tc__name">{trip.tripName}</p>
            <p className="eb-tc__inst">{trip.instituteName}</p>
          </div>
        </div>
        <div className="eb-tc__right">
          <span className={`eb-tc__badge eb-tc__badge--${meta.color}`}>{meta.label}</span>
          <span className="eb-tc__chevron">{expanded ? "▲" : "▼"}</span>
        </div>
      </div>

      {/* ── Fare strip ── */}
      <div className="eb-tc__fare-strip">
        <div className="eb-tc__fare-item">
          <span className="eb-tc__fare-label">Total Fare</span>
          <span className="eb-tc__fare-val">{fmt(totalFare)}</span>
        </div>
        <div className="eb-tc__fare-sep" />
        <div className="eb-tc__fare-item">
          <span className="eb-tc__fare-label">Advance (50%)</span>
          <span className="eb-tc__fare-val eb-tc__fare-val--advance">{fmt(advanceFare)}</span>
        </div>
        <div className="eb-tc__fare-sep" />
        <div className="eb-tc__fare-item">
          <span className="eb-tc__fare-label">Remaining</span>
          <span className="eb-tc__fare-val">{fmt(remaining)}</span>
        </div>

        {/* Payment button — APPROVED only */}
        {trip.tripStatus === "APPROVED" && (
          <button
            className="eb-tc__pay-btn"
            onClick={(e) => { e.stopPropagation(); onPayClick(trip); }}
          >
            Pay {fmt(advanceFare)}
          </button>
        )}
      </div>

      {/* ── Expanded detail ── */}
      {expanded && (
        <div className="eb-tc__detail">
          <div className="eb-tc__detail-grid">
            <DItem label="Source" value={trip.sourceLocation} />
            <DItem label="Destinations" value={(trip.destinationLocations || []).join(", ") || "—"} />
            <DItem label="Start" value={fmtDt(trip.tripStartDatetime)} />
            <DItem label="Duration" value={trip.tripDays ? `${trip.tripDays} Day(s)` : "—"} />
            <DItem label="Bus Class" value={trip.busClass || "—"} />
            <DItem label="Buses" value={trip.requestedBuses ?? "—"} />
            <DItem label="Persons" value={trip.totalPersons ?? "—"} />
            <DItem label="Hotel" value={trip.hotelRequired ? (trip.hotels?.join(", ") || "Yes") : "No"} />
            <DItem label="Emergency" value={trip.emergencyContact || "—"} />
            {trip.pnr && <DItem label="PNR" value={trip.pnr} highlight />}
            {trip.allocatedBuses && <DItem label="Allocated Buses" value={trip.allocatedBuses} />}
            {trip.bookingStatus && <DItem label="Booking Status" value={trip.bookingStatus} />}
          </div>

          {trip.tripStatus === "REJECTED" && (
            <div className="eb-tc__rejected">
              ❌ This request was rejected by GSRTC. You may submit a new request with updated details.
            </div>
          )}
        </div>
      )}
    </div>
  );
};

const DItem = ({ label, value, highlight }) => (
  <div className={`eb-ditem${highlight ? " eb-ditem--hl" : ""}`}>
    <span className="eb-ditem__label">{label}</span>
    <span className="eb-ditem__value">{value}</span>
  </div>
);

/* ================================================================
   MY TRIPS (root)
   ================================================================ */
const MyTrips = ({ userId }) => {
  const [trips, setTrips] = useState([]);
  const [loading, setLoading] = useState(true);
  const [payingTrip, setPayingTrip] = useState(null);
  const [successMsg, setSuccessMsg] = useState("");

  const load = async () => {
    try {
      const res = await getUserTrips(userId);
      console.log(res.data);
      setTrips(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, [userId]);

  const handlePaySuccess = () => {
    setPayingTrip(null);
    setSuccessMsg("Payment successful! Your booking is confirmed.");
    load();
    setTimeout(() => setSuccessMsg(""), 5000);
  };

  if (loading) return <div className="eb-card eb-state">Loading your bookings…</div>;

  if (!trips.length) return (
    <div className="eb-card eb-state eb-state--empty">
      <span className="eb-state__icon">🗂️</span>
      <p className="eb-state__text">No trip requests yet.</p>
      <p className="eb-state__sub">Switch to "New Request" to submit your first educational trip.</p>
    </div>
  );

  return (
    <>
      {successMsg && <div className="eb-success-banner">{successMsg}</div>}

      <div className="eb-trips-list">
        {trips.map((trip, idx) => (
          <TripCard
            key={idx}
            trip={trip}
            onPayClick={setPayingTrip}
          />
        ))}
      </div>

      {payingTrip && (
        <PaymentModal
          trip={payingTrip}
          onClose={() => setPayingTrip(null)}
          onSuccess={handlePaySuccess}
        />
      )}
    </>
  );
};

export default MyTrips;