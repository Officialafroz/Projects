import { useEffect, useState } from "react";
import { getPricing, updatePricing } from "../../../api/PricingDiscountApi.jsx";
import "../../styles/PricingPage.css"

const PricingPage = () => {

  const [pricing, setPricing] = useState({
    reservationFee: "",
    tollFee: "",
    gstPercentage: ""
  });

  useEffect(() => {
    fetchPricing();
  }, []);

  const fetchPricing = async () => {
    const res = await getPricing();
    setPricing(res.data);
  };

  const handleChange = (e) => {
    setPricing({ ...pricing, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      const res = await updatePricing(pricing);
      alert(res.data);
    } catch (err) {
      alert(err);
    }
  };

  return (
    <div className="pricing-page">

      <h2>Pricing Configuration</h2>

      <label>Reservation Fee</label>
      <input
        name="reservationFee"
        value={pricing.reservationFee}
        onChange={handleChange}
      />

      <label>Toll Fee</label>
      <input
        name="tollFee"
        value={pricing.tollFee}
        onChange={handleChange}
      />

      <label>GST %</label>
      <input
        name="gstPercentage"
        value={pricing.gstPercentage}
        onChange={handleChange}
      />

      <button onClick={handleSubmit}>Update</button>

    </div>
  );
};

export default PricingPage;