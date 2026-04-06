import { useState, useRef, useEffect } from "react";
import "./styles/ProfileDropdown.css";
import { FaUser, FaEdit, FaWallet, FaCrown, FaHeadset, FaMoon } from "react-icons/fa";

const ProfileDropdown = ({ authUser, handleLogout }) => {
  const [showMenu, setShowMenu] = useState(false);
  const [darkMode, setDarkMode] = useState(false);
  const menuRef = useRef();

  const currentUser = authUser.name;

  // Close when clicked outside
  useEffect(() => {
    const handleClickOutside = (e) => {
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setShowMenu(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <div className="profile-container" ref={menuRef}>
      <div className="profile-header" onClick={() => setShowMenu(!showMenu)}>
        <img
          src={currentUser?.image || "../../assets/profile.jpg"}
          alt="avatar"
          className="profile-avatar"
        />
        <div className="profile-info">
          <span className="profile-name">{currentUser?.name}</span>
          <span className="profile-email">{currentUser?.email}</span>
        </div>
      </div>

      {showMenu && (
        <div className="profile-dropdown">
          <ul>
            <li><FaUser /> Profile</li>
            {/* <li><FaEdit /> Edit Profile</li>
            <li><FaWallet /> Wallet</li>
            <li><FaCrown /> Upgrade Plan</li> */}
            <li><FaHeadset /> Help Center</li>

            {/* <li className="dark-mode-toggle">
              <div>
                <FaMoon /> Dark Mode
              </div>
              <label className="switch">
                <input
                  type="checkbox"
                  checked={darkMode}
                  onChange={() => setDarkMode(!darkMode)}
                />
                <span className="slider"></span>
              </label>
            </li> */}

            <hr />
            <li className="logout" onClick={handleLogout}>
              Logout
            </li>
          </ul>
        </div>
      )}
    </div>
  );
};

export default ProfileDropdown;