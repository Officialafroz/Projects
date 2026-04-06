import { useState } from "react";
import "../styles/ContactUs.css";

const ContactUs = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    subject: "",
    message: "",
  });

  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.name || !formData.email || !formData.message) {
      alert("Please fill out all required fields.");
      return;
    }
    setSubmitted(true);
  };

  return (
    <div className="contact-container">
      <h2>Contact Us</h2>

      {!submitted ? (
        <form onSubmit={handleSubmit} className="contact-form">
          <label>
            Full Name:
            <input
              type="text"
              name="name"
              placeholder="Enter your name"
              value={formData.name}
              onChange={handleChange}
            />
          </label>

          <label>
            Email Address:
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
            />
          </label>

          <label>
            Subject:
            <input
              type="text"
              name="subject"
              placeholder="Subject (optional)"
              value={formData.subject}
              onChange={handleChange}
            />
          </label>

          <label>
            Message:
            <textarea
              name="message"
              placeholder="Type your message here..."
              rows="5"
              value={formData.message}
              onChange={handleChange}
            />
          </label>

          <button type="submit">Send Message</button>
        </form>
      ) : (
        <div className="contact-success">
          <h3>Thank You!</h3>
          <p>Your message has been successfully sent. Our team will get back to you soon.</p>
        </div>
      )}

      <div className="contact-info">
        <h3>Customer Support</h3>
        <p>📞 1800-233-666-999</p>
        <p>✉️ support@gsrtc.in</p>
        <p>🏢 GSRTC Central Office, Ahmedabad, Gujarat, India</p>
      </div>
    </div>
  );
};

export default ContactUs;
