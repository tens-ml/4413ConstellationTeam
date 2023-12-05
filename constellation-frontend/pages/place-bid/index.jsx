import Button from "@/components/Button";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";
import Title from "@/components/Title";
import { useRouter } from "next/router";

const PlaceBid = () => {
  const item = {
    name: "test name",
    description: "test descript",
    shipCost: 10,
    price: 20,
    bidPrice: 100,
    highestBidder: 123,
    auctionType: "forward",
  };
  const router = useRouter();
  return (
    <Shell>
      <Paper width="400px" className="mt-8 flex flex-col">
        <Title>Place Bid</Title>
        <div className="border border-black p-4 mt-4">
          <p className="text-gray-700 font-medium">
            <b>Item Name:</b> {item.name}
          </p>
          <p className="text-gray-700 font-medium">
            <b>Item Description:</b> {item.description}
          </p>
        </div>
        <div className="border border-black p-4 mt-4">
          {item.auctionType === "dutch" && (
            <p className="text-gray-700 font-medium">
              <b>Buy now price:</b> ${item.price}
            </p>
          )}
          {item.auctionType === "forward" && (
            <>
              <p className="text-gray-700 font-medium">
                <b>Current Price:</b> ${item.price}
              </p>
              <p className="text-gray-700 font-medium">
                <b>Highest Bidder:</b> {item.highestBidder}
              </p>
            </>
          )}
        </div>
        {item.auctionType === "dutch" && (
          <Button className="mt-12">Buy Now</Button>
        )}
        {item.auctionType === "forward" && (
          <>
            <Input className="mt-12" placeholder="Bid Price" required />
            <Button className="mt-4">Place Bid</Button>
            <Button className="mt-2" onClick={() => router.back()}>
              Back
            </Button>
          </>
        )}
      </Paper>
    </Shell>
  );
};

export default PlaceBid;
