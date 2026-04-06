import { useState, useEffect, useContext } from "react";
import { AppContext } from "../../../store/AppContext.jsx";
import { getDepotNameList } from "../../../api/DepotApi.jsx";
import { sendTripRequest } from "../../../api/EducationalBookingApi.jsx";

const TripRequestForm = ({ institute, onSubmitSuccess }) => {
  const { authUser } = useContext(AppContext);

  const [formData, setFormData] = useState({
    instituteId:           institute.instituteId,
    depotId:               "",
    tripName:              "",
    sourceLocation:        "",
    destinationLocations:  [""],
    hotels:                [""],
    tripDays:              "",
    requestedBuses:        "",
    totalPersons:          "",
    busClass:              "",
    budget:                "",
    hotelRequired:         false,
    tripStartDatetime:     "",
    emergencyContact:      "",
    userId:                authUser.userId,
  });

  const [depotList,   setDepotList]   = useState([]);
  const [submitting,  setSubmitting]  = useState(false);
  const [error,       setError]       = useState("");

  useEffect(() => {
    const fetchDepots = async () => {
      try {
        const res = await getDepotNameList();
        console.log(res.data);
        setDepotList(res.data);
      } catch (err) { console.error(err); }
    };
    fetchDepots();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((p) => ({ ...p, [name]: value }));
  };

  const handleDynamic = (index, field, value) => {
    const updated = [...formData[field]];
    updated[index] = value;
    setFormData((p) => ({ ...p, [field]: updated }));
  };

  const addField    = (field) => setFormData((p) => ({ ...p, [field]: [...p[field], ""] }));
  const removeField = (index, field) => {
    const updated = [...formData[field]];
    updated.splice(index, 1);
    setFormData((p) => ({ ...p, [field]: updated }));
  };

  const submit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setError("");
    try {
      const payload = {
        ...formData,
        depotId:              Number(formData.depotId),
        tripDays:             Number(formData.tripDays),
        requestedBuses:       Number(formData.requestedBuses),
        totalPersons:         Number(formData.totalPersons),
        budget:               Number(formData.budget),
        destinationLocations: formData.destinationLocations.filter((d) => d.trim()),
        hotels:               formData.hotelRequired
                                ? formData.hotels.filter((h) => h.trim())
                                : [],
        tripStartDatetime:    formData.tripStartDatetime
                                ? formData.tripStartDatetime + ":00"
                                : null,
      };
      await sendTripRequest(payload);
      onSubmitSuccess();
    } catch (err) {
      setError("Submission failed. Please check all fields and try again.");
      console.error(err);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <form className="eb-card eb-form" onSubmit={submit}>

      <p className="eb-form__section-title">Trip Information</p>

      <div className="eb-form__row">
        <div className="eb-form__field">
          <label className="eb-label">Depot</label>
          <select className="eb-input" name="depotId" value={formData.depotId} onChange={handleChange} required>
            <option value="">Select Depot</option>
            {depotList.map((d) => (
              <option key={d.depotId} value={d.depotId}>{d.depotName}</option>
            ))}
          </select>
        </div>
        <div className="eb-form__field">
          <label className="eb-label">Trip Name</label>
          <input className="eb-input" type="text" name="tripName" placeholder="e.g. Science Museum Visit" value={formData.tripName} onChange={handleChange} required />
        </div>
      </div>

      <div className="eb-form__row">
        <div className="eb-form__field">
          <label className="eb-label">Source Location</label>
          <input className="eb-input" type="text" name="sourceLocation" placeholder="Departure city / area" value={formData.sourceLocation} onChange={handleChange} required />
        </div>
        <div className="eb-form__field">
          <label className="eb-label">Trip Start Date &amp; Time</label>
          <input className="eb-input" type="datetime-local" name="tripStartDatetime" value={formData.tripStartDatetime} onChange={handleChange} required />
        </div>
      </div>

      {/* Destinations */}
      <div className="eb-form__field">
        <label className="eb-label">Destination Locations</label>
        {formData.destinationLocations.map((loc, i) => (
          <div className="eb-dynamic-row" key={i}>
            <input className="eb-input" placeholder={`Destination ${i + 1}`} value={loc}
              onChange={(e) => handleDynamic(i, "destinationLocations", e.target.value)} />
            <div className="eb-dynamic-actions">
              {i === formData.destinationLocations.length - 1 && (
                <button type="button" className="eb-dyn-btn eb-dyn-btn--add" onClick={() => addField("destinationLocations")}>+ Add</button>
              )}
              {formData.destinationLocations.length > 1 && (
                <button type="button" className="eb-dyn-btn eb-dyn-btn--remove" onClick={() => removeField(i, "destinationLocations")}>Remove</button>
              )}
            </div>
          </div>
        ))}
      </div>

      <p className="eb-form__section-title">Logistics</p>

      <div className="eb-form__row eb-form__row--three">
        <div className="eb-form__field">
          <label className="eb-label">Trip Days</label>
          <input className="eb-input" type="number" name="tripDays" min="1" placeholder="1" value={formData.tripDays} onChange={handleChange} required />
        </div>
        <div className="eb-form__field">
          <label className="eb-label">Requested Buses</label>
          <input className="eb-input" type="number" name="requestedBuses" min="1" placeholder="1" value={formData.requestedBuses} onChange={handleChange} required />
        </div>
        <div className="eb-form__field">
          <label className="eb-label">Total Persons</label>
          <input className="eb-input" type="number" name="totalPersons" min="1" placeholder="40" value={formData.totalPersons} onChange={handleChange} required />
        </div>
      </div>

      <div className="eb-form__row">
        <div className="eb-form__field">
          <label className="eb-label">Bus Class</label>
          <select className="eb-input" name="busClass" value={formData.busClass} onChange={handleChange} required>
            <option value="">Select Bus Class</option>
            <option value="Express">Express</option>
            <option value="Sleeper">Sleeper</option>
            <option value="Volvo">Volvo</option>
          </select>
        </div>
        <div className="eb-form__field">
          <label className="eb-label">Estimated Budget (₹)</label>
          <input className="eb-input" type="number" name="budget" min="0" placeholder="50000" value={formData.budget} onChange={handleChange} required />
        </div>
      </div>

      <div className="eb-form__field">
        <label className="eb-label">Emergency Contact</label>
        <input className="eb-input" type="text" name="emergencyContact" placeholder="Mobile number" value={formData.emergencyContact} onChange={handleChange} required />
      </div>

      {/* Hotel */}
      <div className="eb-form__field">
        <label className="eb-checkbox-label">
          <input type="checkbox" checked={formData.hotelRequired}
            onChange={(e) => setFormData((p) => ({ ...p, hotelRequired: e.target.checked }))} />
          <span>Hotel Accommodation Required</span>
        </label>
      </div>

      {formData.hotelRequired && (
        <div className="eb-form__field">
          <label className="eb-label">Hotel Names</label>
          {formData.hotels.map((hotel, i) => (
            <div className="eb-dynamic-row" key={i}>
              <input className="eb-input" placeholder={`Hotel ${i + 1}`} value={hotel}
                onChange={(e) => handleDynamic(i, "hotels", e.target.value)} />
              <div className="eb-dynamic-actions">
                {i === formData.hotels.length - 1 && (
                  <button type="button" className="eb-dyn-btn eb-dyn-btn--add" onClick={() => addField("hotels")}>+ Add</button>
                )}
                {formData.hotels.length > 1 && (
                  <button type="button" className="eb-dyn-btn eb-dyn-btn--remove" onClick={() => removeField(i, "hotels")}>Remove</button>
                )}
              </div>
            </div>
          ))}
        </div>
      )}

      {error && <p className="eb-error">{error}</p>}

      <div className="eb-form__submit-row">
        <button type="submit" className="eb-btn eb-btn--primary eb-btn--lg" disabled={submitting}>
          {submitting ? <span className="eb-spinner" /> : "Submit Trip Request"}
        </button>
      </div>

    </form>
  );
};

export default TripRequestForm;