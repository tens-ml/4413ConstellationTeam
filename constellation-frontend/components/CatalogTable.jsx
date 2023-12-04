import { useRouter } from "next/router";
import Button from "./Button";

const CatalogTable = ({ data, className }) => {
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
                need to add this
              </td>
              <td className="text-center px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                need to add this
              </td>
            </tr>
          ))}
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
