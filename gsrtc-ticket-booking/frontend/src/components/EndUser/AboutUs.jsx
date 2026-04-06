import "../styles/AboutUs.css";

const AboutUs = () => {
  return (
    <div className="about-container">
      <h2>About GSRTC</h2>

      <section className="about-section">
        <p>
          Gujarat State Road Transport Corporation (GSRTC) is one of India’s largest
          and most trusted state transport services, dedicated to providing safe,
          efficient, and affordable public transportation to millions of passengers
          across Gujarat and neighboring states.
        </p>
      </section>

      <section className="about-section">
        <h3>Our Mission</h3>
        <p>
          To deliver seamless and reliable bus services that connect people,
          communities, and cities, promoting sustainable mobility and accessibility
          for all citizens of Gujarat.
        </p>
      </section>

      <section className="about-section">
        <h3>Our Vision</h3>
        <p>
          To become India’s most passenger-friendly and technologically advanced
          transport corporation by continuously innovating in digital booking,
          fleet modernization, and passenger convenience.
        </p>
      </section>

      <section className="about-section">
        <h3>Our Commitment</h3>
        <ul>
          <li>Affordable and transparent fares</li>
          <li>Safety and comfort for all passengers</li>
          <li>Real-time booking and cancellation services</li>
          <li>Eco-friendly initiatives and green buses</li>
        </ul>
      </section>
    </div>
  );
};

export default AboutUs;
