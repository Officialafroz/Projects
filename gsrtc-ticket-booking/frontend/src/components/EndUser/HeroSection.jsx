import "../styles/HeroSection.css";
import BusSearchForm from "./Search/BusSearchForm.jsx";

const HeroSection = () => {
  return (
    <section className="hero">
      <div className="overlay">
        <h1>Travel Across Gujarat</h1>
        <p>Book your bus tickets with GSRTC - Safe, Comfortable, Affordable</p>
        <BusSearchForm />
      </div>
    </section>
  );
};

export default HeroSection;
