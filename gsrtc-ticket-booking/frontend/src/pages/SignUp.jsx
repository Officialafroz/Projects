// SignUp.jsx
import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../components/styles/AuthForm.css";
import { AppContext } from "../store/AppContext";

const SignUp = () => {
  const {setAuthUser} = useContext(AppContext);

  const [form, setForm] = useState({
    name: "",
    email: "",
    phoneNumber: "",
    gender: "",
  });
  const [otp, setOtp] = useState("");
  const [showOtpField, setShowOtpField] = useState(false);
  const navigate = useNavigate();

  const handleSendOtp = async (e) => {
    e.preventDefault();
    const email = form.email;
    console.log(email);
    if (!email.trim()) return alert("Enter your email");

    try {
      const res = await axios.post("/api/end-user/register/process/email", { email });
      alert(res.data.message || "OTP sent to your email");
      setShowOtpField(true);
    } catch (err) {
      console.error(err);
      alert("Failed to send OTP. Try again.");
    }
  }

  const handleVerify = async (e) => {
    e.preventDefault();
    if (!otp.trim()) return alert("Enter the OTP");

    try {
      const email = form.email;
      const res = await axios.post("/api/end-user/register/verify", { email, otp });
      const userData = res.data;
      if (userData !== "User verified") {
        throw new Error('Invalid or expired OTP');
      }

      await axios.post("/api/end-user", form);

      console.log(userData + ' verification msg.');
      const name = form.name;
      const user = { name, email };
      setAuthUser(user);
      navigate("/");
    } catch (err) {
      console.error(err);
      alert("Invalid or expired OTP");
    }
  };

  // 🚀 Google Login Redirect
  const handleGoogleLogin = () => {
    // Backend OAuth2 endpoint (we'll implement later)
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
  };

  return (
    <>
      <form className="signup-form">
        {!showOtpField ? (
          <>
            <h2>Sign Up</h2>

            {["name", "email", "phoneNumber"].map((f) => (
              <input
                key={f}
                type={f === "email" ? "email" : "text"}
                placeholder={`Enter ${f}`}
                value={form[f]}
                onChange={(e) => setForm({ ...form, [f]: e.target.value })}
              />
            ))}

            <select
              value={form.gender}
              onChange={(e) => setForm({ ...form, gender: e.target.value })}
            >
              <option value="">Select Gender</option>
              <option value="male">Male</option>
              <option value="female">Female</option>
            </select>

            <button onClick={handleSendOtp}>Send OTP</button>

            <div className="divider">
              {/* <span>OR</span> */}
            </div>

            <button
              type="button"
              className="google-btn"
              onClick={handleGoogleLogin}
            >
              <img
                src="https://developers.google.com/identity/images/g-logo.png"
                alt="google"
              />
              Sign in with Google
            </button>

            <div className="auth-switch">
              <p>
                Already have an account?{" "}
                <Link to="/signin">Login in</Link>
              </p>
            </div>
          </>
        ) : (
          <>
            <h2>Enter OTP</h2>
            <input
              type="text"
              placeholder="Enter OTP"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
            />
            <button type="button" onClick={handleVerify}>Verify</button>
          </>
        )}
      </form>
    </>
  );
};

export default SignUp;
