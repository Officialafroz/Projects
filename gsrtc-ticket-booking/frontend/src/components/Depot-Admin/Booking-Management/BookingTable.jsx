const BookingTable = ({ bookings }) => {

  return (
    <table className="booking-table">
      <thead>
        <tr>
          <th>Bus Number</th>
          <th>Route</th>
          <th>Class</th>
          <th>Trip Code</th>
          <th>Total Seats</th>
          <th>Booked Seats</th>
          <th>Remaining Seats</th>
          <th>Revenue (₹)</th>
        </tr>
      </thead>
      <tbody>
        {bookings.map((booking, index) => (
          <tr key={index}>
            <td>{booking.busNumber}</td>
            <td>{booking.route}</td>
            <td>{booking.classType}</td>
            <td>{booking.tripDate}</td>
            <td>{booking.tripCode}</td>
            <td>{booking.bookedSeats}</td>
            <td>{booking.remainingSeats}</td>
            <td>{booking.revenue}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default BookingTable;
