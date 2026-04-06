import { useForm } from "react-hook-form";

const PasswordForm = ({ nextStep }) => {
  const { register, handleSubmit, watch, formState: { errors } } = useForm();

  const password = watch("password");

  return (
    <form onSubmit={handleSubmit(nextStep)}>

      <h2>Create Password</h2>

      <input
        type="password"
        placeholder="Password"
        {...register("password", {
          required: true,
          minLength: 8
        })}
      />

      {errors.password && (
        <p>Password must be at least 8 characters</p>
      )}

      <input
        type="password"
        placeholder="Confirm Password"
        {...register("confirmPassword", {
          validate: (value) =>
            value === password || "Passwords do not match"
        })}
      />

      {errors.confirmPassword && (
        <p>{errors.confirmPassword.message}</p>
      )}

      <button type="submit" className="create-da-btn">
        Create Account
      </button>

    </form>
  );
};

export default PasswordForm;