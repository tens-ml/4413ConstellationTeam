import { useRouter } from "next/router";
import Button from "./Button";

function getTimeRemaining(targetDateTime) {
  const targetDate = new Date(targetDateTime);
  const now = new Date();

  // Calculate the difference in milliseconds
  const diff = targetDate - now;

  // Check if the target date is in the past
  if (diff < 0) {
    return "Auction ended";
  }

  // Convert milliseconds to days, hours, and minutes
  let remaining = diff;
  const days = Math.floor(remaining / (1000 * 60 * 60 * 24));
  remaining -= days * (1000 * 60 * 60 * 24);
  const hours = Math.floor(remaining / (1000 * 60 * 60));
  remaining -= hours * (1000 * 60 * 60);
  const minutes = Math.floor(remaining / (1000 * 60));

  return `${days} day(s), ${hours} hour(s), and ${minutes} minute(s) remaining`;
}

const CatalogTable = ({ data, className, isLoading }) => {
  const router = useRouter();

  const handlePlaceBid = () => {
    // If available and dutch -> place bid -> make unavailable
    // if not available and dutch -> auction ended page (buy now if available (user id = bid user id), otherwise show sold message)
    // if available and forward -> place bid
    // if not available and forward -> auction ended page (buy now if available (user id = bid user id), otherwise show sold message)
    // forward: every time API is hit we will check if the auction has ended yet and if so, we will update available status.
    const itemAvailable = true;
    const itemId = 1;
    const auctionType = "dutch";

    if (itemAvailable && auctionType === "dutch") {
      return router.push({ pathname: "/place-bid", query: { id: itemId } });
    }
  };
  console.log(data);
  return (
    <div className={`overflow-x-auto ${className}`}>
      <table className="min-w-full bg-gray-100 shadow-md rounded-lg">
        <thead className="bg-gray-200 border-b">
          <tr>
            <th className="w-64">Select</th>
            {[
              "Name",
              "Description",
              "Auction Type",
              "Current Price",
              "Remaining Time",
            ].map((header, index) => (
              <th
                key={index}
                className="text-center px-6 py-3 text-left text-xs font-medium text-gray-600 uppercase tracking-wider"
              >
                {header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data?.map((row, rowIndex) => (
            <tr
              key={rowIndex}
              className={rowIndex % 2 === 0 ? "bg-white" : "bg-gray-50"}
            >
              <td className="text-center">
                <input
                  type="radio"
                  className="h-5 w-5 text-gray-600"
                  name="row-select"
                />
              </td>
              <td className="text-center px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                {row.name}
              </td>
              <td className="text-center px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                {row.description}
              </td>
              <td className="text-center px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                {row.isDutch ? "Dutch" : "Forward"}
              </td>
              <td className="text-center px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                $ {row.highestBid.toFixed(2)}
              </td>
              <td className="text-center px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                {getTimeRemaining(row.auctionEnd)}
              </td>
            </tr>
          ))}
          {!isLoading && data.length === 0 && (
            <tr>
              <td colSpan={6} className="text-center py-4">
                No items found
              </td>
            </tr>
          )}
        </tbody>
        <tfoot className="bg-gray-200 border-b">
          <tr>
            <td>
              <Button className="h-full w-full" onClick={handlePlaceBid}>
                Place Bid
              </Button>
            </td>
            <td colSpan={5}></td>
          </tr>
        </tfoot>
      </table>
    </div>
  );
};

export default CatalogTable;
