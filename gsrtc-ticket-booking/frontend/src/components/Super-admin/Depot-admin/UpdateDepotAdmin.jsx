import { useState } from "react";
import { updateDepotAdmin } from "../../../api/DepotAdminApi.jsx";

const UpdateDepotAdmin = ({ admin }) => {

  const [editing, setEditing] = useState(false);

  const [form, setForm] = useState({
    email: admin.email,
    phoneNumber: admin.phoneNumber
  });

  const handleUpdate = () => {

    updateDepotAdmin(admin.id, form.email, form.phoneNumber).then(() => {
      alert("Updated Successfully");
      setEditing(false);
    });
  };

  if (!editing) {
    return (
      <button onClick={() => setEditing(true)}>
        Edit
      </button>
    );
  }

  return (
    <div>

      <input
        value={form.email}
        onChange={(e) =>
          setForm({ ...form, email: e.target.value })
        }
      />

      <input
        value={form.phoneNumber}
        onChange={(e) =>
          setForm({ ...form, phoneNumber: e.target.value })
        }
      />

      <button onClick={handleUpdate}>
        Save
      </button>

    </div>
  );
};

export default UpdateDepotAdmin;