import { useNavigate, useSearchParams } from "react-router-dom";
import DepotAdminForm from "./DepotAdminForm";
import { createDepotAdmin } from "../../../../api/DepotAdminApi";
import "../../../styles/CreateDA.css"

const CreateDepotAdmin = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");

  const handleSubmit = async (data) => {
    try {
      const payload = {
        token,
        ...data
      };

      console.log(payload);

      const res = await createDepotAdmin(payload);
      alert(res.data);
      navigate("/");
    } catch (error) {
      alert(error?.response?.data || "Something went wrong");
    }
  };

  return (
    <div className="admin-form-container">
      <DepotAdminForm onSubmit={handleSubmit} />
    </div>
  );
};

export default CreateDepotAdmin;