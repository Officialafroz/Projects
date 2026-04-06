import { useEffect, useState } from "react";
import { getCustomDepotObj, getDepotNames } from "../../../api/BusDepotApi";
import { inviteDepotAdmin } from "../../../api/DepotAdminApi";

const InviteCard = () => {
  const [form, setForm] = useState({
    email: "",
    depotId: ""
  });

  const [depots, setDepots] = useState([]);

  useEffect(() => {
    fetchDepots();
  }, []);

  const fetchDepots = async () => {
    try {
      const res = await getCustomDepotObj();
      setDepots(res.data);
      console.log(res.data)
    } catch (err) {
      console.error("Failed to fetch depots");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await inviteDepotAdmin(form);

      alert("Invite link sent successfully");

      setForm({
        email: "",
        depotId: ""
      });

    } catch (err) {
      alert("Failed to send invite");
    }
  };

  return (
    <div className="invite-card">

      <h3>Generate depot admin invite link</h3>

      <form onSubmit={handleSubmit} className="invite-form">

        <input
          type="email"
          placeholder="Depot Admin Email"
          value={form.email}
          onChange={(e) =>
            setForm({ ...form, email: e.target.value })
          }
          required
        />

        <select
          value={form.depotId}
          onChange={(e) =>
            setForm({ ...form, depotId: e.target.value })
          }
          required
        >
          <option value="">Select Depot</option>

          {depots.map((depot, idx) => (
            <option key={idx} value={depot.depotId}>
              {depot.depotName}
            </option>
          ))}

        </select>

        <button type="submit">
          Send Invite
        </button>

      </form>

    </div>
  )
}

export default InviteCard;