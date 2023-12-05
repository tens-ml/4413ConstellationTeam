import Button from "@/components/Button";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import Title from "@/components/Title";
import { useSession } from "next-auth/react";
import { useRouter } from "next/router";
import { useState } from "react";
import useSWR from "swr";

const fetcher = (url) => fetch(url).then((res) => res.json());
const WinnerView = ({ item }) => {
  const [expedite, setExpedite] = useState(false);

  const totalCost =
    item?.highestBid + item.shippingPrice + (expedite ? item.expeditePrice : 0);
  return (
    <>
      <Title className="mb-4">Auction Ended</Title>
      <p className="text-gray-700 font-medium">
        <b>Item Name:</b> {item.name}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Item Description:</b> {item.description}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Shipping Cost:</b> {item.shippingPrice}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Winning Price:</b> {item.highestBid}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Highest Bidder:</b> {item.highestBidderId}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Expedite Cost:</b> {item.expeditePrice}
      </p>
      <div class="mt-4 flex flex-col items-center ">
        <div className="flex space-x-2">
          <input
            id="expedite"
            type="checkbox"
            class="form-checkbox h-5 w-5 text-gray-600 rounded border-gray-300 focus:border-gray-300 focus:ring focus:ring-offset-0 focus:ring-gray-500 focus:ring-opacity-50"
            checked={expedite}
            onChange={(e) => setExpedite(e.target.checked)}
          />
          <label htmlFor="expedite" class="text-gray-700 font-medium">
            Expedited Shipping
          </label>
        </div>
      </div>
      <Button className="mt-4">Pay Now (${totalCost})</Button>
    </>
  );
};

const LoserView = ({ winner }) => {
  return (
    <>
      <Title className="mb-4">Auction Ended</Title>
      <p>You are not the winner, {} is the winner</p>
    </>
  );
};

const AuctionEnded = () => {
  const { data: session } = useSession();
  const user = session?.user;

  const router = useRouter();
  const { itemId } = router.query;

  const {
    data: item,
    error,
    isLoading,
  } = useSWR(
    () => (itemId ? `${process.env.GATEWAY_URL}/items/${itemId}` : null),
    fetcher
  );

  const PageContent = () => {
    if (isLoading) {
      return <p>Loading...</p>;
    }
    if (error) {
      return <p>Failed to load</p>;
    }
    if (!item) {
      return <p>Item not found</p>;
    }
    if (user?.id === item.highestBidderId) {
      return <WinnerView item={item} />;
    } else {
      return <LoserView />;
    }
  };

  return (
    <Shell>
      <Paper width="400px" className="mt-8 flex flex-col">
        <PageContent />
      </Paper>
    </Shell>
  );
};

export default AuctionEnded;
