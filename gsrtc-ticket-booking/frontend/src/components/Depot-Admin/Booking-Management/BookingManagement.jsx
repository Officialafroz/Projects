import { useContext, useEffect, useState } from "react";
import BookingTable from "./BookingTable";
import BookingFilters from "./Filters/BookingFilters";
import "../../styles/BookingManagement.css";
import { AppContext } from "../../../store/AppContext";
import BookingSearch from "./Filters/BookingSearch";
import DateFilter from "./Filters/DateFilter";
import { fetchBookings } from "../../../api/GenericBookingManagementApi";


const BookingManagement = () => {
  const { authUser } = useContext(AppContext);
  const [bookings, setBookings] = useState([]);
  const [filters, setFilters] = useState({
    date: "",
    search: "",   // tripCode OR route
    classType: "",
    revenue: ""
  });

  useEffect(() => {
    const fetch = async () => {
    const res = await fetchBookings(authUser.depotId, filters);
    setBookings(Array.isArray(res.data) ? res.data : [res.data]);
    }

    fetch();
  }, [filters]);

  return (
    <div className="booking-management">
      <h1>Booking Management</h1>

      <div className="search-filter">
        <DateFilter setFilters={setFilters} />
        <BookingSearch setFilters={setFilters} />
        <BookingFilters setFilters={setFilters} />
      </div>

      <BookingTable bookings={bookings} />
    </div>
  );
};

export default BookingManagement;
