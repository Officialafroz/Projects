import React from 'react'
import { Navigate, Route, Routes } from 'react-router-dom'

const UserPages = () => {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/bus-booking" />} />
      <Route path="/bus-booking" element={<Home />} />
      <Route path="/your-bookings" element={<YourBookings />} />
      <Route path="/educational-booking" element={<EducationalTrip />} />
      <Route path="/booking-cancellation" element={<BookingCancellation />} />
      <Route path="/contact-us" element={<ContactUs />} />
      <Route path="/about-us" element={<AboutUs />} />
      <Route path="/signin" element={<UserLogin setUser={setUser} />} />
      <Route path="/signup" element={<SignUp setUser={setUser} />} />
      <Route path="/admin-login" element={<AdminLogin setAdmin={setAdmin} />} />
      <Route path="/profile" element={<UserProfile user={user} />} />
    </Routes>
  )
}

export default UserPages