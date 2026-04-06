import { useForm } from "react-hook-form";

const DepotAdminForm = ({ onSubmit }) => {
  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm();

  return (
    <form className="da-form" onSubmit={handleSubmit(onSubmit)}>

      <h2 className="form-header">Admin Details</h2>

      <input
        placeholder="Full Name"
        {...register("name", { required: "Name is required" })}
      />
      {errors.name && <p>{errors.name.message}</p>}

      <input
        type="email"
        placeholder="Email"
        {...register("email", { required: "Email is required" })}
      />
      {errors.email && <p>{errors.email.message}</p>}

      <input
        placeholder="Phone Number"
        {...register("phoneNumber", { required: "Phone number is required" })}
      />
      {errors.phoneNumber && <p>{errors.phoneNumber.message}</p>}

      <input
        placeholder="Address"
        {...register("address", { required: "Address is required" })}
      />
      {errors.address && <p>{errors.address.message}</p>}

      <select {...register("gender", { required: "Gender is required" })}>
        <option value="">Select Gender</option>
        <option value="MALE">Male</option>
        <option value="FEMALE">Female</option>
        <option value="OTHER">Other</option>
      </select>
      {errors.gender && <p>{errors.gender.message}</p>}

      <button type="submit" className="create-da-btn">
        Create Depot Admin
      </button>

    </form>
  );
};

export default DepotAdminForm;