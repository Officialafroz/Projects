const TransactionTable = ({ transactions }) => {
  if (!Array.isArray(transactions)) {
    return <p>No transactions found</p>;
  }

  return (
    <table className="transaction-table">
      <thead>
        <tr>
          <th>PNR</th>
          <th>Transaction Ref</th>
          <th>Amount</th>
          <th>Payment Method</th>
          <th>Date</th>
          <th>Status</th>
        </tr>
      </thead>

      <tbody>
        {transactions.map((tx, index) => (
          <tr key={index}>
            <td>{tx.pnr}</td>
            <td>{tx.transactionRef}</td>
            <td>₹{tx.amount}</td>
            <td>{tx.paymentMethod}</td>
            <td>{tx.paymentDate}</td>
            <td>{tx.status}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default TransactionTable;
