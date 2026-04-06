const TransactionFilters = ({ filters, setFilters, onSearch }) => {
  return (
    <div className="transaction-filters">

      {/* PNR */}
      <input
        type="text"
        placeholder="PNR"
        value={filters.pnr}
        onChange={(e) => setFilters({ ...filters, pnr: e.target.value })}
      />

      {/* Transaction Ref */}
      <input
        type="text"
        placeholder="Transaction Ref"
        value={filters.transactionRef}
        onChange={(e) =>
          setFilters({ ...filters, transactionRef: e.target.value })
        }
      />

      {/* Date */}
      <input
        type="date"
        value={filters.date}
        onChange={(e) => setFilters({ ...filters, date: e.target.value })}
      />

      {/* Payment Method */}
      <select
        value={filters.paymentMethod}
        onChange={(e) =>
          setFilters({ ...filters, paymentMethod: e.target.value })
        }
      >
        <option value="">All Methods</option>
        <option value="UPI">UPI</option>
        <option value="CARD">Card</option>
        <option value="CASH">Cash</option>
      </select>

      {/* Status */}
      <select
        value={filters.status}
        onChange={(e) => setFilters({ ...filters, status: e.target.value })}
      >
        <option value="">All Status</option>
        <option value="SUCCESS">Success</option>
        <option value="FAILED">Failed</option>
        <option value="PENDING">Pending</option>
      </select>

      <button onClick={onSearch}>Search</button>
    </div>
  );
};

export default TransactionFilters;
