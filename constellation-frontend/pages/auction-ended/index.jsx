import Button from "@/components/Button";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import Title from "@/components/Title";
import { getSession } from "next-auth/react";
import { useRouter } from "next/router";
import { useState } from "react";

const mockPurchaseInfo = {
  name: "test",
  description: "test",
  shipCost: 10,
  bidPrice: 100,
  highestBidder: 123,
  expediteCost: 2,
};

const WinnerView = ({ purchaseInfo = {} }) => {
  const [expedite, setExpedite] = useState(false);
  const router = useRouter();

  const totalCost =
    purchaseInfo.shipCost +
    purchaseInfo.bidPrice +
    (expedite ? purchaseInfo.expediteCost : 0);

  return (
    <>
      <Title className="mb-4">Auction Ended</Title>
      <p className="text-gray-700 font-medium">
        <b>Item Name:</b> {purchaseInfo.name}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Item Description:</b> {purchaseInfo.description}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Shipping Cost:</b> {purchaseInfo.shipCost}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Bid Price:</b> {purchaseInfo.bidPrice}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Highest Bidder:</b> {purchaseInfo.highestBidder}
      </p>
      <p className="text-gray-700 font-medium">
        <b>Expedite Cost:</b> {purchaseInfo.expediteCost}
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
      <Button className="mt-2" onClick={() => router.back()}>
        Back
      </Button>
    </>
  );
};

const LoserView = () => {
  return (
    <>
      <Title className="mb-4">Auction Ended</Title>
      <p>You are not the winner</p>
    </>
  );
};

const AuctionEnded = ({ user }) => {
  const isWinner = true;

  return (
    <Shell>
      <Paper width="400px" className="mt-8 flex flex-col">
        {isWinner ? (
          <WinnerView purchaseInfo={mockPurchaseInfo} />
        ) : (
          <LoserView />
        )}
      </Paper>
    </Shell>
  );
};

export async function getServerSideProps(context) {
  const session = await getSession(context);
  const user = session?.user;
  const itemId = context.params?.itemId || -1;
  const winnnerId = -1;

  console.log(itemId);

  if (!user) {
    return {
      redirect: {
        destination: "/",
        permanent: false,
      },
    };
  }

  return {
    props: { user, itemId },
  };
}

export default AuctionEnded;
