import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { AppContext } from "../store/AppContext.jsx";
import ".././components/styles/Authform.css";


const UserLogin = () => {
  const { setUser } = useContext(AppContext);
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [showOtpField, setShowOtpField] = useState(false);
  const navigate = useNavigate();

  const handleSendOtp = async (e) => {
    e.preventDefault();
    if (!email.trim()) return alert("Enter your email");

    try {
      const res = await axios.post("/api/auth/login/process-email", { email });
      alert(res.data.message || "OTP sent to your email");
      setShowOtpField(true);
    } catch (err) {
      console.error(err);
      alert("Failed to send OTP. Try again.");
    }
  };

  const handleVerify = async (e) => {
    e.preventDefault();
    if (!otp.trim()) return alert("Enter the OTP");

    try {
      await axios.post("/api/auth/login/verify/otp", { email, otp }, { withCredentials: true });

      // const data = fetchAppUser().data;

      // setUser(data); // better to store full response
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
      <form className="login-form">
        <h2>User Login</h2>

        {!showOtpField ? (
          <>
            <input
              type="email"
              placeholder="Enter email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
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
            <button type="button" onClick={handleVerify}>
              Verify
            </button>
          </>
        )}
        <div className="auth-switch">
          <p>
            New user?{" "}
            <Link to="/signup">Register here</Link>
          </p>
        </div>
      </form>
    </>
  );
};

export default UserLogin;