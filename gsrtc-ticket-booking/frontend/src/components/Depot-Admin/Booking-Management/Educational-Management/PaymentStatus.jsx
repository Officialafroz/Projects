const PaymentStatus = ({ payment }) => {

  if(!payment) return null;

  return (
    <div className="panel-card">

      <h3>Payment Status</h3>

      <p>Total: ₹{payment.total}</p>
      <p>Paid: ₹{payment.paid}</p>
      <p>Remaining: ₹{payment.remaining}</p>

    </div>
  );
};

export default PaymentStatus;