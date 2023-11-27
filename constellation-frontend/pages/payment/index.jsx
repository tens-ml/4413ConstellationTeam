import Button from "@/components/Button";
import Input from "@/components/Input";
import Paper from "@/components/Paper";
import Shell from "@/components/Shell";

const Payment = () => {
  return (
    <Shell>
      <Paper width="800px" className="mt-8 flex flex-col">
        <p className="mr-16 text-center font-semibold mt-2 font-sans text-3xl text-gray-800">
          Payment
        </p>
        <form className="flex mt-8">
          <div className="grow pr-6 border-r-2 border-gray-300">
            <p className="border-b-2 border-gray-300 text-md text-center">
              Winning Bidder
            </p>
            <div className="flex flex-col mt-4">
              <p className="text-gray-700 font-medium">
                <b>First Name:</b> test name
              </p>
              <p className="text-gray-700 font-medium">
                <b>Last Name</b> test last
              </p>
              <p className="text-gray-700 font-medium">
                <b>Street:</b> 100
              </p>
              <p className="text-gray-700 font-medium">
                <b>Number</b> 10
              </p>
              <p className="text-gray-700 font-medium">
                <b>Province:</b>
              </p>
              <p className="text-gray-700 font-medium">
                <b>Country:</b> Canada
              </p>
              <p className="text-gray-700 font-medium">
                <b>Postal Code:</b> 333
              </p>
              <p className="text-gray-700 font-medium">
                <b>Total Cost:</b> $110
              </p>
            </div>
          </div>
          <div className="grow pl-6">
            <p className="border-b-2 border-gray-300 text-md text-center">
              Credit Card Details
            </p>
            <Input className="mt-4" label="Card Number" required />
            <Input className="mt-4" label="Card Name" required />
            <Input className="mt-4" label="Expiry Date" required />
            <Input className="mt-4" label="CCV" required />

            <Button className="mt-4 w-full">Submit</Button>
            <Button className="mt-1 w-full" onClick={() => router.back()}>
              Back
            </Button>
          </div>
        </form>
      </Paper>
    </Shell>
  );
};

export default Payment;
