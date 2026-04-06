// AdminLogin.jsx
import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../api/BaseConfig.jsx"
import "../components/styles/AuthForm.css";
import axios from "axios";
import { AppContext } from "../store/AppContext";
import AdminPages from "./AdminPages";
import { fetchCurrentUser } from "../api/AuthUser.jsx";
import { loginDA } from "../api/LoginApi.jsx";

const AdminLogin = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { authUser, setAuthUser } = useContext(AppContext);

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const verifyRes = await loginDA(email, password);

      fetchCurrentUser().then(
        user => {
          setAuthUser(user);
          console.log(user);
        }
      ).catch(() => setAuthUser(null));
      // navigate("/");
    } catch (err) {
      console.error(err);
      alert(err);
    }
  };

  return (
    <form className="login-form" onSubmit={handleLogin}>
      <h2>Admin Login</h2>
      <input
        type="email"
        placeholder="Email"
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setPassword(e.target.value)}
      />
      <button type="submit">Login</button>
    </form>
  );
};

export default AdminLogin;
