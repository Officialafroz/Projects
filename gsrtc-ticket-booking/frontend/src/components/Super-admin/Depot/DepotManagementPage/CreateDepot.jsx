import { useState } from "react";

const CreateDepot = ({ onCreate }) => {
  const [formData, setFormData] = useState({
    depotName: "",
    city: "",
    email: "",
    password: "",
    address: ""
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    onCreate(formData); // ✅ pass data to parent

    // optional: reset form
    setFormData({
      depotName: "",
      city: "",
      email: "",
      password: "",
      address: ""
    });
  };

  return (
    <div className="create-depot-container">
      <h2>Create Depot</h2>

      <form className="create-form" onSubmit={handleSubmit}>
        <input
          type="text"
          name="depotName"
          placeholder="Depot Name"
          value={formData.depotName}
          onChange={handleChange}
          required
        />

        <input
          type="text"
          name="city"
          placeholder="City"
          value={formData.city}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
        />

        <input
          name="address"
          placeholder="Address"
          value={formData.address}
          onChange={handleChange}
          required
        />

        <button type="submit">
          Save
        </button>
      </form>
    </div>
  );
};

export default CreateDepot;